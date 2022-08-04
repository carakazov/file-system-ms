package notes.project.filesystem.job;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import notes.project.filesystem.config.ApplicationProperties;
import notes.project.filesystem.dto.integration.ArchiveClusterEventDto;
import notes.project.filesystem.model.Cluster;
import notes.project.filesystem.service.integration.DeleteObjectNotificationChannelProducer;
import notes.project.filesystem.service.logic.ClusterService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeleteClusterArchiveJob {
    private final DeleteObjectNotificationChannelProducer producer;
    private final ClusterService clusterService;
    private final ApplicationProperties properties;

    @Scheduled(cron = "@daily")
    @Transactional
    public void doJob() {
        log.info("----------------------------------------");
        log.info("[START DELETE CLUSTER ARCHIVE JOB]");
        List<Cluster> clusters = clusterService.findAllDeleted().stream()
            .filter(this::checkCluster)
            .collect(Collectors.toList());
        if(clusters.isEmpty()) {
            log.info("Clusters to delete not found");
        } else {
            for(Cluster cluster : clusters) {
                ArchiveClusterEventDto event = new ArchiveClusterEventDto(JobEventCode.CLUSTER_IRREVOCABLE_DELETED.getCode(), cluster.getExternalId());
                clusterService.eraseCluster(cluster);
                producer.sendMessageAboutArchivingCluster(event);
                log.info(
                    "Cluster '{}' with external id {} erased at {}. All data deleted now and can't be recreated.",
                    cluster.getTitle(),
                    cluster.getExternalId(),
                    LocalDateTime.now()
                );
            }
        }
        log.info("[FINISH DELETE CLUSTER ARCHIVE JOB]");
        log.info("----------------------------------------");
    }

    private boolean checkCluster(Cluster cluster) {
        long days = Duration.between(cluster.getLastRequestDate(), LocalDateTime.now()).toDays();
        return days >= properties.getDaysBeforeDeleteArchive();
    }
}
