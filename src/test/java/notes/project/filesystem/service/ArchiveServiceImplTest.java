package notes.project.filesystem.service;

import java.util.Collections;

import notes.project.filesystem.dto.ArchiveHistoryResponseDto;
import notes.project.filesystem.file.ZipManager;
import notes.project.filesystem.mapper.ArchiveHistoryResponseMapper;
import notes.project.filesystem.mapper.CreateArchiveMapper;
import notes.project.filesystem.model.Archive;
import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.repository.ArchiveRepository;
import notes.project.filesystem.service.impl.ArchiveServiceImpl;
import notes.project.filesystem.utils.ApiUtils;
import notes.project.filesystem.utils.DbUtils;
import notes.project.filesystem.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static notes.project.filesystem.utils.TestDataConstants.FILE_VERSION_UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArchiveServiceImplTest {
    @Mock
    private ArchiveRepository repository;
    @Mock
    private ZipManager zipManager;

    private ArchiveService service;

    @Captor
    private ArgumentCaptor<Archive> archiveCaptor;

    @BeforeEach
    void init() {
        service = new ArchiveServiceImpl(
            repository,
            Mappers.getMapper(CreateArchiveMapper.class),
            TestUtils.getComplexMapper(ArchiveHistoryResponseMapper.class),
            zipManager
        );
    }

    @Test
    void createSuccess() {
        when(repository.save(any())).thenReturn(DbUtils.archive());

        service.create(DbUtils.createdFile(), FILE_VERSION_UUID);

        verify(repository).save(archiveCaptor.capture());

        Archive expected = DbUtils.archive();
        Archive actual = archiveCaptor.getValue();

        assertEquals(expected.getVersionFileGuid(), actual.getVersionFileGuid());
        assertEquals(expected.getCreatedFile(), actual.getCreatedFile());
    }

    @Test
    void getArchiveHistorySuccess() {
        Archive archive = DbUtils.archive();
        CreatedFile createdFile = DbUtils.createdFile();
        ArchiveHistoryResponseDto expected = ApiUtils.archiveHistoryResponseDto();

        when(repository.findAllByCreatedFile(any())).thenReturn(Collections.singletonList(archive));

        ArchiveHistoryResponseDto actual = service.getArchiveHistory(createdFile);

        assertEquals(expected, actual);

        verify(repository).findAllByCreatedFile(createdFile);
    }
}
