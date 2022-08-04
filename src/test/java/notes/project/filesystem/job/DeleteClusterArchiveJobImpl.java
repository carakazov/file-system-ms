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
class DeleteClusterArchiveJobImpl {
    @Mock
    private DeleteObjectNotificationChannelProducer producer;
    @Mock
    private ClusterService clusterService;

    private DeleteClusterArchiveJob job;

    @BeforeEach
    void init() {
        job = new DeleteClusterArchiveJob(
            producer,
            clusterService,
            ApplicationPropertiesUtils.applicationPropertiesForDeleteJob()
        );
    }

    @Test
    void doJobWhenClusterExists() {
        Cluster cluster = DbUtils.cluster();
        cluster.setLastRequestDate(LocalDateTime.now().minusDays(2));

        ArchiveClusterEventDto event = ApiUtils.archiveClusterEventDto();
        event.setEventCode(JobEventCode.CLUSTER_IRREVOCABLE_DELETED.getCode());

        when(clusterService.findAllDeleted()).thenReturn(Collections.singletonList(cluster));

        job.doJob();

        verify(clusterService).findAllDeleted();
        verify(clusterService).eraseCluster(cluster);
        verify(producer).sendMessageAboutArchivingCluster(event);
    }

    @Test
    void doJobWhenClusterDoesNotExist() {
        when(clusterService.findAllDeleted()).thenReturn(Collections.emptyList());

        job.doJob();

        verify(clusterService).findAllDeleted();
        verifyNoMoreInteractions(clusterService);
        verifyNoInteractions(producer);
    }

    @Test
    void doJobWhenClusterToYang() {
        Cluster cluster = DbUtils.cluster();
        cluster.setLastRequestDate(LocalDateTime.now().minusDays(1));

        when(clusterService.findAllDeleted()).thenReturn(Collections.singletonList(cluster));

        job.doJob();;

        verify(clusterService).findAllDeleted();
        verifyNoMoreInteractions(clusterService);
        verifyNoInteractions(producer);
    }

    @Test
    void doJobWhenClusterIsVeryOld() {
        Cluster cluster = DbUtils.cluster();
        cluster.setLastRequestDate(LocalDateTime.now().minusDays(100));

        ArchiveClusterEventDto event = ApiUtils.archiveClusterEventDto();
        event.setEventCode(JobEventCode.CLUSTER_IRREVOCABLE_DELETED.getCode());

        when(clusterService.findAllDeleted()).thenReturn(Collections.singletonList(cluster));

        job.doJob();

        verify(clusterService).findAllDeleted();
        verify(clusterService).eraseCluster(cluster);
        verify(producer).sendMessageAboutArchivingCluster(event);
    }
}
