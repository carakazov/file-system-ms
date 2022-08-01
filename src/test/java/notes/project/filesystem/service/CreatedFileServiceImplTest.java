package notes.project.filesystem.service;

import java.util.Collections;
import java.util.Optional;

import notes.project.filesystem.dto.*;
import notes.project.filesystem.file.FileManager;
import notes.project.filesystem.file.ZipManager;
import notes.project.filesystem.mapper.FileCreationMapper;
import notes.project.filesystem.mapper.ReadFileMapper;
import notes.project.filesystem.mapper.ReplacingHistoryMapper;
import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.model.Directory;
import notes.project.filesystem.model.EventType;
import notes.project.filesystem.model.ReplacingHistory;
import notes.project.filesystem.repository.CreatedFileRepository;
import notes.project.filesystem.service.impl.CreatedFileServiceImpl;
import notes.project.filesystem.utils.ApiUtils;
import notes.project.filesystem.utils.DbUtils;
import notes.project.filesystem.validation.Validator;
import notes.project.filesystem.validation.impl.FileCreationValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static notes.project.filesystem.utils.TestDataConstants.FILE_CONTENT;
import static notes.project.filesystem.utils.TestDataConstants.FILE_EXTERNAL_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreatedFileServiceImplTest {
    @Mock
    private CreatedFileRepository repository;
    @Mock
    private FileManager fileManager;
    @Mock
    private DirectoryService directoryService;
    @Mock
    private ClusterService clusterService;
    @Mock
    private FileCreationValidator fileCreationValidator;
    @Mock
    private DeleteHistoryService deleteHistoryService;
    @Mock
    private ObjectExistingStatusChanger objectExistingStatusChanger;
    @Mock
    private ZipManager zipManager;
    @Mock
    private ReplacingHistoryService replacingHistoryService;
    @Mock
    private ArchiveService archiveService;
    @Mock
    private Validator<UpdateFileRequestDto> updateFileValidator;
    private CreatedFileService service;


    @BeforeEach
    void init() {
        service = new CreatedFileServiceImpl(
            repository,
            fileManager,
            directoryService,
            Mappers.getMapper(FileCreationMapper.class),
            clusterService,
            fileCreationValidator,
            deleteHistoryService,
            zipManager,
            objectExistingStatusChanger,
            Mappers.getMapper(ReadFileMapper.class),
            replacingHistoryService,
            Mappers.getMapper(ReplacingHistoryMapper.class),
            archiveService,
            updateFileValidator,
            null,
            null
        );
    }

    @Test
    void addFileSuccess() {
        when(directoryService.findNotDeletedDirectoryByExternalId(any())).thenReturn(DbUtils.directory());
        when(repository.save(any())).thenReturn(DbUtils.createdFile());
        AddFileRequestDto request = ApiUtils.addFileRequestDto();
        AddFileResponseDto expected = ApiUtils.addFileResponseDto();

        AddFileResponseDto actual = service.addFile(request);

        assertEquals(expected, actual);

        verify(directoryService).findNotDeletedDirectoryByExternalId(request.getDirectoryExternalId());
    }

    @Test
    void deleteCreatedFileSuccess() {
        CreatedFile createdFile = DbUtils.createdFile();
        when(repository.findByExternalIdAndDeletedFalse(any())).thenReturn(Optional.of(createdFile));

        service.deleteCreatedFile(FILE_EXTERNAL_ID);

        verify(deleteHistoryService).createCreatedFileDeleteHistory(createdFile.setDeleted(Boolean.TRUE), EventType.DELETED);
        verify(clusterService).updateClusterLastRequestedTime(createdFile.getDirectory().getCluster());
        verify(zipManager).zipCreatedFile(createdFile);
    }

    @Test
    void readFileSuccess() {
        CreatedFile file = DbUtils.createdFile();
        when(repository.findByExternalIdAndDeletedFalse(any())).thenReturn(Optional.of(file));
        when(fileManager.readFile(any())).thenReturn(FILE_CONTENT);
        ReadCreatedFileDto expected = ApiUtils.readCreatedFileDto();

        ReadCreatedFileDto actual = service.readFile(FILE_EXTERNAL_ID);

        assertEquals(expected, actual);

        verify(repository).findByExternalIdAndDeletedFalse(FILE_EXTERNAL_ID);
        verify(fileManager).readFile(file);
    }


    @Test
    void moveFileSuccess() {
        CreatedFile createdFile = DbUtils.createdFile();
        Directory directory = DbUtils.directoryWithAlternativeExternalId();
        ReplacingHistory replacingHistory = DbUtils.replacingHistory();

        when(repository.findByExternalIdAndDeletedFalse(any())).thenReturn(Optional.of(createdFile));
        when(directoryService.findNotDeletedDirectoryByExternalId(any())).thenReturn(directory);
        when(replacingHistoryService.create(any(), any())).thenReturn(replacingHistory);

        MoveCreatedFileResponseDto expected = ApiUtils.moveCreatedFileResponseDto();

        MoveCreatedFileResponseDto actual = service.moveFile(ApiUtils.moveCreatedFileRequestDto());

        assertEquals(expected, actual);

        verify(repository).findByExternalIdAndDeletedFalse(createdFile.getExternalId());
        verify(directoryService).findNotDeletedDirectoryByExternalId(directory.getExternalId());
        verify(replacingHistoryService).create(createdFile, directory);
        verify(fileManager).moveFile(createdFile, directory);
    }

    @Test
    void updateFileSuccess() {
        CreatedFile createdFile = DbUtils.createdFile();

        when(repository.findByExternalIdAndDeletedFalse(any())).thenReturn(Optional.of(createdFile));

        UpdateFileRequestDto request = ApiUtils.updateFileRequestDto();

        service.updateFile(FILE_EXTERNAL_ID, request);

        verify(updateFileValidator).validate(request);
        verify(repository).findByExternalIdAndDeletedFalse(FILE_EXTERNAL_ID);
        verify(archiveService).create(eq(createdFile), any());
        verify(zipManager).zipFileForUpdate(eq(createdFile), any());
        verify(fileManager).updateFile(createdFile, request.getContent());
    }

    @Test
    void getFileDeletedHistoryWhenHistoryExistsSuccess() {
        CreatedFile createdFile = DbUtils.createdFile();
        DeleteHistoryResponseDto expected = ApiUtils.deleteHistoryCreatedFileResponseDto();

        when(repository.findByExternalId(any())).thenReturn(Optional.of(createdFile));
        when(deleteHistoryService.getCreatedFileDeleteHistory(any())).thenReturn(expected);

        DeleteHistoryResponseDto actual = service.getFileDeleteHistory(createdFile.getExternalId());

        assertEquals(expected, actual);

        verify(repository).findByExternalId(createdFile.getExternalId());
        verify(deleteHistoryService).getCreatedFileDeleteHistory(createdFile);
    }

    @Test
    void getFileDeletedHistoryWhenHistoryDoesNotExistSuccess() {
        CreatedFile createdFile = DbUtils.createdFile();
        DeleteHistoryResponseDto expected = ApiUtils.deleteHistoryCreatedFileResponseDto();
        expected.setCurrentState(EventType.CREATED);
        expected.setHistory(Collections.emptyList());

        when(repository.findByExternalId(any())).thenReturn(Optional.of(createdFile));
        when(deleteHistoryService.getCreatedFileDeleteHistory(any())).thenReturn(expected);

        DeleteHistoryResponseDto actual = service.getFileDeleteHistory(createdFile.getExternalId());

        assertEquals(expected, actual);

        verify(repository).findByExternalId(createdFile.getExternalId());
        verify(deleteHistoryService).getCreatedFileDeleteHistory(createdFile);
    }
}
