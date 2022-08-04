package notes.project.filesystem.service.integration;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
public interface StreamProcessor {
    String NOTIFICATION_CHANNEL = "delete-object-notification-channel";

    @Output(NOTIFICATION_CHANNEL)
    MessageChannel getNotificationOutputChannel();
}
