package notes.project.filesystem.job;

import java.time.LocalDateTime;
import java.util.Collections;

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
class SendNotificationBeforeDeleteJobTest {
    @Mock
    private DeleteObjectNotificationChannelProducer producer;
    @Mock
    private ClusterService clusterService;

    private SendNotificationBeforeDeleteJob job;

    @BeforeEach
    void init() {
        job = new SendNotificationBeforeDeleteJob(
            producer,
            clusterService,
            ApplicationPropertiesUtils.applicationPropertiesForNotificationJob()
        );
    }

    @Test
    void doJobSuccessWhenClusterExists() {
        Cluster cluster = DbUtils.cluster();
        cluster.setLastRequestDate(LocalDateTime.now().minusDays(4));
        when(clusterService.findAllDeleted()).thenReturn(Collections.singletonList(cluster));

        job.doJob();

        verify(clusterService).findAllDeleted();
        verify(producer).sendMessageAboutArchivingCluster(ApiUtils.clusterWillBeDeletedSoonEventDto());
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
    void doJobWhenClusterTooYang() {
        Cluster cluster = DbUtils.cluster();
        cluster.setLastRequestDate(LocalDateTime.now());
        when(clusterService.findAllDeleted()).thenReturn(Collections.singletonList(cluster));

        job.doJob();

        verify(clusterService).findAllDeleted();
        verifyNoMoreInteractions(clusterService);
        verifyNoInteractions(producer);
    }

    @Test
    void doJobWhenClusterTooOld() {
        Cluster cluster = DbUtils.cluster();
        cluster.setLastRequestDate(LocalDateTime.now().minusDays(10));
        when(clusterService.findAllDeleted()).thenReturn(Collections.singletonList(cluster));

        job.doJob();

        verify(clusterService).findAllDeleted();
        verifyNoMoreInteractions(clusterService);
        verifyNoInteractions(producer);
    }
}
