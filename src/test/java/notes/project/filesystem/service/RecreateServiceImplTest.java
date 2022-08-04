package notes.project.filesystem.service;

import notes.project.filesystem.file.ZipManager;
import notes.project.filesystem.model.Cluster;
import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.model.Directory;
import notes.project.filesystem.model.EventType;
import notes.project.filesystem.service.logic.ClusterService;
import notes.project.filesystem.service.logic.DeleteHistoryService;
import notes.project.filesystem.service.logic.RecreateService;
import notes.project.filesystem.service.logic.impl.RecreateServiceImpl;
import notes.project.filesystem.utils.DbUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RecreateServiceImplTest {
    @Mock
    private DeleteHistoryService deleteHistoryService;
    @Mock
    private ClusterService clusterService;
    @Mock
    private ZipManager zipManager;

    private RecreateService service;

    @BeforeEach
    void init() {
        service = new RecreateServiceImpl(
            deleteHistoryService,
            clusterService,
            zipManager
        );
    }

    @Test
    void recreateFileSuccess() {
        CreatedFile createdFile = DbUtils.createdFile();

        service.recreateFile(createdFile);

        verify(zipManager).recreateFile(createdFile);
        verify(clusterService).updateClusterLastRequestedTime(createdFile.getDirectory().getCluster());
        verify(deleteHistoryService).createCreatedFileDeleteHistory(createdFile, EventType.RECREATED);
    }

    @Test
    void recreateDirectorySuccess() {
        Directory directory = DbUtils.directoryWithFiles();

        service.recreateDirectory(directory);

        verify(zipManager).recreateDirectory(directory);
        verify(clusterService).updateClusterLastRequestedTime(directory.getCluster());
        verify(deleteHistoryService).createDirectoryDeleteHistory(directory, EventType.RECREATED);
    }

    @Test
    void recreateClusterSuccess() {
        Cluster cluster = DbUtils.clusterWithFiles();

        service.recreateCluster(cluster);

        verify(zipManager).recreateCluster(cluster);
        verify(clusterService).updateClusterLastRequestedTime(cluster);
        verify(deleteHistoryService).createClusterDeleteHistory(cluster, EventType.RECREATED);
    }
}
