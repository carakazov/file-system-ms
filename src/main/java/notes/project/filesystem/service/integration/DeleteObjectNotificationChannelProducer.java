package notes.project.filesystem.service.integration;

import notes.project.filesystem.dto.integration.ArchiveClusterEventDto;

public interface DeleteObjectNotificationChannelProducer {
   <T extends ArchiveClusterEventDto> void sendMessageAboutArchivingCluster(T messageEventDto);
}
