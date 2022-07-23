package notes.project.filesystem.utils;

import lombok.experimental.UtilityClass;
import notes.project.filesystem.config.ApplicationProperties;
import org.junit.jupiter.api.Test;

@UtilityClass
public class ApplicationPropertiesUtils {
    public static ApplicationProperties applicationPropertiesForPathHelper() {
        return new ApplicationProperties()
                .setRoot(TestDataConstants.ROOT_DIRECTORY_PATH);
    }

    public static ApplicationProperties applicationPropertiesForErrorHelper() {
        return new ApplicationProperties()
                .setErrorMessages(TestDataConstants.EXCEPTION_MAP);
    }

    public static ApplicationProperties applicationPropertiesForZipManager() {
        return new ApplicationProperties()
            .setArchiveRoot(TestDataConstants.ARCHIVE_ROOT);
    }
}
