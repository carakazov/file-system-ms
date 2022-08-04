package notes.project.filesystem.service.integration;

import notes.project.filesystem.exception.FileSystemException;
import notes.project.filesystem.service.integration.impl.DeleteObjectNotificationChannelPublisherImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteObjectNotificationChannelPublisherImplTest {
    private static final Message<String> MESSAGE = new GenericMessage<>("Some message");

    private DeleteObjectNotificationChannelPublisher publisher;


    @Mock
    private MessageChannel channel;

    @BeforeEach
    void init() {
        publisher = new DeleteObjectNotificationChannelPublisherImpl();
    }

    @Test
    void publishMessageSuccess() {
        when(channel.send(any())).thenReturn(Boolean.TRUE);

        publisher.publishMessage(channel, MESSAGE);

        verify(channel).send(MESSAGE);
    }

    @Test
    void publishMessageWhenFatalError() {
        when(channel.send(any())).thenThrow(new RuntimeException());

        assertThrows(
            FileSystemException.class,
            () -> publisher.publishMessage(channel, MESSAGE)
        );
    }

    @Test
    void publicMessageWhenNotFatal() {
        when(channel.send(any())).thenReturn(Boolean.FALSE);

        assertThrows(
            FileSystemException.class,
            () -> publisher.publishMessage(channel, MESSAGE)
        );
    }
}
