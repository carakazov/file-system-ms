package notes.project.filesystem.service;

import notes.project.filesystem.dto.DirectoryCreationRequestDto;
import notes.project.filesystem.dto.DirectoryCreationResponseDto;
import notes.project.filesystem.file.FileManager;
import notes.project.filesystem.mapper.DirectoryCreationMapper;
import notes.project.filesystem.repository.DirectoryRepository;
import notes.project.filesystem.service.impl.DirectoryServiceImpl;
import notes.project.filesystem.utils.ApiUtils;
import notes.project.filesystem.utils.DbUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DirectoryServiceTest {
    @Mock
    private DirectoryRepository repository;

    @Mock
    private FileManager fileManager;

    @Mock
    private ClusterService clusterService;

    private DirectoryService service;

    @BeforeEach
    void init() {
        service = new DirectoryServiceImpl(
            repository,
            Mappers.getMapper(DirectoryCreationMapper.class),
            fileManager,
            clusterService
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
        verifyNoMoreInteractions(repository, clusterService);
    }
}
