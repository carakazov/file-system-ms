package notes.project.filesystem.service;

import java.util.Optional;

import notes.project.filesystem.dto.ClusterCreationResponseDto;
import notes.project.filesystem.file.FileManager;
import notes.project.filesystem.file.ZipManager;
import notes.project.filesystem.mapper.ClusterCreationMapper;
import notes.project.filesystem.mapper.ReadClusterMapper;
import notes.project.filesystem.model.Cluster;
import notes.project.filesystem.model.EventType;
import notes.project.filesystem.repository.ClusterRepository;
import notes.project.filesystem.service.impl.ClusterServiceImpl;
import notes.project.filesystem.utils.ApiUtils;
import notes.project.filesystem.utils.DbUtils;
import notes.project.filesystem.utils.TestUtils;
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
    @Mock
    private DeleteHistoryService deleteHistoryService;
    @Mock
    private ObjectExistingStatusChanger objectExistingStatusChanger;
    @Mock
    private ZipManager zipManager;


    private ClusterService service;

    @BeforeEach
    void init() {
        service = new ClusterServiceImpl(
            clusterRepository,
            fileManager,
            Mappers.getMapper(ClusterCreationMapper.class),
            clusterCreationValidator,
            deleteHistoryService,
            objectExistingStatusChanger,
            zipManager,
            TestUtils.getComplexMapper(ReadClusterMapper.class)
        );
    }

    @Test
    void createClusterSuccess() {
        when(clusterRepository.save(any())).thenReturn(DbUtils.cluster());

        ClusterCreationResponseDto expected = ApiUtils.clusterCreationResponseDto();
        ClusterCreationResponseDto actual = service.createCluster(ApiUtils.clusterCreationRequestDto());

        assertEquals(expected, actual);
    }

    @Test
    void deleteClusterSuccess() {
        Cluster cluster = DbUtils.clusterWithFiles();
        when(clusterRepository.findByExternalIdAndDeletedFalse(any())).thenReturn(Optional.of(cluster));

        service.deleteCluster(cluster.getExternalId());

        verify(deleteHistoryService).createClusterDeleteHistory(cluster, EventType.DELETED);
        verify(zipManager).zipCluster(cluster);
    }

    @Test
    void readFileSuccess() {
        Cluster cluster = DbUtils.clusterWithFiles();
        when(clusterRepository.findByExternalIdAndDeletedFalse(any())).thenReturn(Optional.of(cluster));

        service.readCluster(cluster.getExternalId());

        verify(clusterRepository).findByExternalIdAndDeletedFalse(cluster.getExternalId());
    }

}
