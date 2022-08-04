package notes.project.filesystem.service.integration.impl;

import lombok.RequiredArgsConstructor;
import notes.project.filesystem.dto.integration.ArchiveClusterEventDto;
import notes.project.filesystem.service.integration.DeleteObjectNotificationChannelProducer;
import notes.project.filesystem.service.integration.DeleteObjectNotificationChannelPublisher;
import notes.project.filesystem.service.integration.StreamProcessor;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@EnableBinding(StreamProcessor.class)
public class DeleteObjectNotificationChannelProducerImpl implements DeleteObjectNotificationChannelProducer {
    private final DeleteObjectNotificationChannelPublisher publisher;
    private final StreamProcessor streamProcessor;

    @Override
    public void sendMessageAboutArchivingCluster(ArchiveClusterEventDto messagePayload) {
        Message<ArchiveClusterEventDto> message = MessageBuilder.withPayload(messagePayload).build();
        publisher.publishMessage(streamProcessor.getNotificationOutputChannel(), message);
    }
}
