package notes.project.filesystem.service;

import notes.project.filesystem.mapper.ArchiveMapper;
import notes.project.filesystem.model.Archive;
import notes.project.filesystem.repository.ArchiveRepository;
import notes.project.filesystem.service.impl.ArchiveServiceImpl;
import notes.project.filesystem.utils.DbUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static notes.project.filesystem.utils.TestDataConstants.*;

@ExtendWith(MockitoExtension.class)
class ArchiveServiceImplTest {
    @Mock
    private ArchiveRepository repository;

    private ArchiveService service;

    @Captor
    private ArgumentCaptor<Archive> archiveCaptor;

    @BeforeEach
    void init() {
        service = new ArchiveServiceImpl(
            repository,
            Mappers.getMapper(ArchiveMapper.class)
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
}
