package notes.project.filesystem.service;

import java.util.Optional;

import javax.validation.Valid;

import io.swagger.annotations.Api;
import notes.project.filesystem.dto.DirectoryCreationRequestDto;
import notes.project.filesystem.dto.DirectoryCreationResponseDto;
import notes.project.filesystem.dto.ReadDirectoryDto;
import notes.project.filesystem.file.FileManager;
import notes.project.filesystem.file.ZipManager;
import notes.project.filesystem.mapper.DirectoryCreationMapper;
import notes.project.filesystem.mapper.ReadDirectoryMapper;
import notes.project.filesystem.model.Directory;
import notes.project.filesystem.repository.DirectoryRepository;
import notes.project.filesystem.service.ClusterService;
import notes.project.filesystem.service.DeleteHistoryService;
import notes.project.filesystem.service.DirectoryService;
import notes.project.filesystem.service.ObjectExistingStatusChanger;
import notes.project.filesystem.service.impl.DirectoryServiceImpl;
import notes.project.filesystem.utils.ApiUtils;
import notes.project.filesystem.utils.DbUtils;
import notes.project.filesystem.utils.TestUtils;
import notes.project.filesystem.validation.BusinessValidator;
import notes.project.filesystem.validation.Validator;
import notes.project.filesystem.validation.impl.DirectoryCreationValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static notes.project.filesystem.utils.TestDataConstants.*;

@ExtendWith(MockitoExtension.class)
class DirectoryServiceImplTest {
    @Mock
    private DirectoryRepository repository;
    @Mock
    private FileManager fileManager;
    @Mock
    private ClusterService clusterService;
    @Mock
    private DirectoryCreationValidator directoryCreationValidator;
    @Mock
    private ZipManager zipManager;
    @Mock
    private DeleteHistoryService deleteHistoryService;
    @Mock
    private ObjectExistingStatusChanger objectExistingStatusChanger;
    @Mock
    private Validator<Directory> deleteDirectoryValidator;
    @Mock
    private BusinessValidator<Directory> readDirectoryValidator;
    private DirectoryService service;

    @BeforeEach
    void init() {
        service = new DirectoryServiceImpl(
            repository,
            Mappers.getMapper(DirectoryCreationMapper.class),
            fileManager,
            clusterService,
            directoryCreationValidator,
            zipManager,
            deleteHistoryService,
            objectExistingStatusChanger,
            deleteDirectoryValidator,
            readDirectoryValidator,
            TestUtils.getComplexMapper(ReadDirectoryMapper.class)
        );
    }

    @Test
    void createDirectorySuccess() {
        when(clusterService.findByExternalId(any())).thenReturn(DbUtils.cluster());
        when(repository.save(any())).thenReturn(DbUtils.directory());
        DirectoryCreationRequestDto request = ApiUtils.directoryCreationRequestDto();

        DirectoryCreationResponseDto expected = ApiUtils.directoryCreationResponseDto();
        DirectoryCreationResponseDto actual = service.createDirectory(request);

        assertEquals(expected, actual);

        verify(clusterService).findByExternalId(request.getClusterExternalId());
    }

    @Test
    void deleteDirectorySuccess() {
        Directory directory = DbUtils.directoryWithFiles();
        when(repository.findByExternalId(any())).thenReturn(Optional.of(directory));

        service.deleteDirectory(directory.getExternalId());

        verify(deleteHistoryService).createDirectoryDeleteHistory(directory.setDeleted(Boolean.TRUE));
        verify(clusterService).updateClusterLastRequestedTime(directory.getCluster());
        verify(zipManager).zipDirectory(directory);
    }

    @Test
    void readDirectorySuccess() {
        Directory directory = DbUtils.directoryWithFiles();
        when(repository.findByExternalId(any())).thenReturn(Optional.of(directory));

        ReadDirectoryDto expected = ApiUtils.readDirectoryDto();

        ReadDirectoryDto actual = service.readDirectory(DIRECTORY_EXTERNAL_ID);

        assertEquals(expected, actual);

        verify(repository).findByExternalId(DIRECTORY_EXTERNAL_ID);
    }
}
