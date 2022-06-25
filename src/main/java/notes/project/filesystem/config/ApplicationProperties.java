package notes.project.filesystem.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@ConfigurationProperties(prefix = "application")
@Component
public class ApplicationProperties {
    private String root;

    private Map<String, String> errorMessages;

    public String getMessage(String messageCode) {
        return errorMessages.get(messageCode);
    }
}
