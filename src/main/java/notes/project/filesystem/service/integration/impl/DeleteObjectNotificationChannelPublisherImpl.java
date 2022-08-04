package notes.project.filesystem.service.integration.impl;

import lombok.extern.slf4j.Slf4j;
import notes.project.filesystem.exception.ExceptionCode;
import notes.project.filesystem.exception.FileSystemException;
import notes.project.filesystem.service.integration.DeleteObjectNotificationChannelPublisher;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DeleteObjectNotificationChannelPublisherImpl implements DeleteObjectNotificationChannelPublisher {
    @Override
    public void publishMessage(MessageChannel channel, Message<?> message) {
        boolean sent;
        try {
            sent = channel.send(message);
        } catch(RuntimeException exception) {
            log.info("Exception while sending message");
            throw new FileSystemException(ExceptionCode.JOB_BROKER_EXCEPTION, exception.getMessage());
        }
        if(!sent) {
            log.error("Message not send");
            throw new FileSystemException(ExceptionCode.JOB_BROKER_EXCEPTION);
        }
    }
}
