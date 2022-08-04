package notes.project.filesystem.job;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
public class CheckClusterLastRequestDateJob {
    private final ClusterService clusterService;
    private final DeleteObjectNotificationChannelProducer producer;
    private final ApplicationProperties properties;

    @Scheduled(cron = "@monthly")
    public void doJob() {
        log.info("----------------------------------------");
        log.info("[START CLUSTER ARCHIVING JOB]");
        List<Cluster> clustersToArchive = clusterService.findAllNotDeleted().stream()
            .filter(this::shouldArchive)
            .collect(Collectors.toList());
        if(clustersToArchive.isEmpty()) {
            log.info("Clusters to archive not found");
        } else {
            for(Cluster cluster : clustersToArchive) {
                clusterService.deleteCluster(cluster.getExternalId());
                producer.sendMessageAboutArchivingCluster(new ArchiveClusterEventDto(JobEventCode.CLUSTER_WAS_ARCHIVED.getCode(), cluster.getExternalId()));
                log.info("Cluster '{}' with external id {} was archived at {}", cluster.getTitle(), cluster.getExternalId(), LocalDateTime.now());
            }
        }
        log.info("[FINISH CLUSTER ARCHIVING JOB]");
        log.info("----------------------------------------");
    }

    public boolean shouldArchive(Cluster cluster) {
        long days = Duration.between(cluster.getLastRequestDate(), LocalDateTime.now()).toDays();
        return days >= properties.getDaysBeforeArchive();
    }
}
