package notes.project.filesystem.config;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@ConfigurationProperties(prefix = "application")
@Component
@Accessors(chain = true)
public class ApplicationProperties {
    private String root;
    private String archiveRoot;
    private Boolean recreateFullPath;

    private Map<String, String> errorMessages;

    public String getMessage(String messageCode) {
        return errorMessages.get(messageCode);
    }
}
