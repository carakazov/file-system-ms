package notes.project.filesystem.service;

import notes.project.filesystem.dto.ClusterCreationResponseDto;
import notes.project.filesystem.file.FileManager;
import notes.project.filesystem.mapper.ClusterCreationMapper;
import notes.project.filesystem.repository.ClusterRepository;
import notes.project.filesystem.service.impl.ClusterServiceImpl;
import notes.project.filesystem.utils.ApiUtils;
import notes.project.filesystem.utils.DbUtils;
import notes.project.filesystem.utils.TestDataConstants;
import notes.project.filesystem.validation.impl.ClusterCreationValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClusterServiceImplTest {
    @Mock
    private ClusterRepository clusterRepository;
    @Mock
    private FileManager fileManager;
    @Mock
    private ClusterCreationValidator clusterCreationValidator;

    private ClusterService service;

    @BeforeEach
    void init() {
        service = new ClusterServiceImpl(
                clusterRepository,
                fileManager,
                Mappers.getMapper(ClusterCreationMapper.class),
                clusterCreationValidator
        );
    }

    @Test
    void createClusterSuccess() {
        when(clusterRepository.save(any())).thenReturn(DbUtils.cluster());

        ClusterCreationResponseDto expected = ApiUtils.clusterCreationResponseDto();
        ClusterCreationResponseDto actual = service.createCluster(ApiUtils.clusterCreationRequestDto());

        assertEquals(expected, actual);
    }
}
