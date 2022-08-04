package notes.project.filesystem.service.integration;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

public interface DeleteObjectNotificationChannelPublisher {
    void publishMessage(MessageChannel channel, Message<?> message);
}
