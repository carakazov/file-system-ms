package notes.project.filesystem.service.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import notes.project.filesystem.config.ApplicationConfiguration;
import notes.project.filesystem.dto.integration.ArchiveClusterEventDto;
import notes.project.filesystem.service.integration.impl.DeleteObjectNotificationChannelProducerImpl;
import notes.project.filesystem.utils.ApiUtils;
import notes.project.filesystem.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteObjectNotificationChannelProducerImplTest {
    @Mock
    private DeleteObjectNotificationChannelPublisher publisher;
    @Mock
    private StreamProcessor streamProcessor;
    @Mock
    private MessageChannel messageChannel;
    @Captor
    private ArgumentCaptor<Message<ArchiveClusterEventDto>> captor;

    private DeleteObjectNotificationChannelProducer producer;

    private ObjectMapper objectMapper;

    @BeforeEach
    void init() {
        producer = new DeleteObjectNotificationChannelProducerImpl(publisher, streamProcessor);
        objectMapper = ApplicationConfiguration.objectMapper();
    }

    @Test
    void sendMessageSuccess() throws Exception {
        ArchiveClusterEventDto event = ApiUtils.archiveClusterEventDto();

        when(streamProcessor.getNotificationOutputChannel()).thenReturn(messageChannel);

        producer.sendMessageAboutArchivingCluster(event);

        verify(streamProcessor).getNotificationOutputChannel();
        verify(publisher).publishMessage(eq(messageChannel), captor.capture());

        JSONAssert.assertEquals(
            TestUtils.getClasspathResource("integration/ArchiveEventDto.json"),
            objectMapper.writeValueAsString(captor.getValue().getPayload()),
            false
        );
    }
}
