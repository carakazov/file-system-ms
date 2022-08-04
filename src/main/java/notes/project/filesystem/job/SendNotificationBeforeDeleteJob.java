package notes.project.filesystem.job;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import notes.project.filesystem.config.ApplicationProperties;
import notes.project.filesystem.dto.integration.ClusterWillBeDeletedSoonEventDto;
import notes.project.filesystem.model.Cluster;
import notes.project.filesystem.service.integration.DeleteObjectNotificationChannelProducer;
import notes.project.filesystem.service.logic.ClusterService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SendNotificationBeforeDeleteJob {
    private final DeleteObjectNotificationChannelProducer producer;
    private final ClusterService clusterService;
    private final ApplicationProperties properties;

    @Scheduled(cron = "@daily")
    public void doJob() {
        log.info("----------------------------------------");
        log.info("[START NOTIFICATION BEFORE DELETE SENDING JOB]");
        List<Cluster> clusters = clusterService.findAllDeleted().stream()
            .filter(this::shouldSendMessage)
            .collect(Collectors.toList());
        if(clusters.isEmpty()) {
            log.info("Clusters for notification not found");
        } else {
            for(Cluster cluster : clusters) {
                long daysBeforeDelete = properties.getDaysBeforeDeleteArchive() - Duration.between(cluster.getLastRequestDate(), LocalDateTime.now()).toDays();
                ClusterWillBeDeletedSoonEventDto event = new ClusterWillBeDeletedSoonEventDto(
                    JobEventCode.CLUSTER_WILL_BE_DELETED_SOON.getCode(),
                    cluster.getExternalId(),
                    daysBeforeDelete
                );
                producer.sendMessageAboutArchivingCluster(event);
                log.info(
                    "Sent notification for cluster '{}' with external id {}, days before delete: {} at {}",
                    cluster.getTitle(),
                    cluster.getExternalId(),
                    daysBeforeDelete,
                    LocalDateTime.now()
                );
            }
        }
        log.info("[FINISH NOTIFICATION BEFORE DELETE SENDING JOB]");
        log.info("----------------------------------------");
    }

    private boolean shouldSendMessage(Cluster cluster) {
        long days = Duration.between(cluster.getLastRequestDate(), LocalDateTime.now()).toDays();
        return days >= properties.getDaysBeforeNotification() && days < properties.getDaysBeforeDeleteArchive();
    }
}
