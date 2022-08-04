package notes.project.filesystem.job;

import java.time.LocalDateTime;
import java.util.Collections;

import notes.project.filesystem.dto.integration.ArchiveClusterEventDto;
import notes.project.filesystem.model.Cluster;
import notes.project.filesystem.service.integration.DeleteObjectNotificationChannelProducer;
import notes.project.filesystem.service.logic.ClusterService;
import notes.project.filesystem.utils.ApiUtils;
import notes.project.filesystem.utils.ApplicationPropertiesUtils;
import notes.project.filesystem.utils.DbUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CheckClusterLastRequestDateJobTest {
    @Mock
    private ClusterService clusterService;
    @Mock
    private DeleteObjectNotificationChannelProducer producer;

    private CheckClusterLastRequestDateJob job;

    @BeforeEach
    void init() {
        job = new CheckClusterLastRequestDateJob(
            clusterService,
            producer,
            ApplicationPropertiesUtils.applicationPropertiesForArchiveJob()
        );
    }

    @Test
    void doJobSuccessWhenClusterExists() {
        Cluster cluster = DbUtils.cluster();
        ArchiveClusterEventDto event = ApiUtils.archiveClusterEventDto();
        cluster.setLastRequestDate(LocalDateTime.now().minusDays(2));

        when(clusterService.findAllNotDeleted()).thenReturn(Collections.singletonList(cluster));

        job.doJob();

        verify(clusterService).findAllNotDeleted();
        verify(clusterService).deleteCluster(cluster.getExternalId());
        verify(producer).sendMessageAboutArchivingCluster(event);
    }

    @Test
    void doJobWhenClusterDoesNotExist() {
        when(clusterService.findAllNotDeleted()).thenReturn(Collections.emptyList());

        job.doJob();

        verifyNoMoreInteractions(clusterService);
        verifyNoInteractions(producer);
    }
}
