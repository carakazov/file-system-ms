package notes.project.filesystem.utils;

import lombok.experimental.UtilityClass;
import notes.project.filesystem.config.ApplicationProperties;

@UtilityClass
public class ApplicationPropertiesUtils {
    public static ApplicationProperties applicationProperties() {
        return new ApplicationProperties();
    }

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

    public static ApplicationProperties applicationPropertiesForRecreateFacade() {
        return new ApplicationProperties()
            .setRecreateFullPath(Boolean.TRUE);
    }

    public static ApplicationProperties applicationPropertiesForArchiveJob() {
        return new ApplicationProperties()
            .setDaysBeforeArchive(1L);
    }

    public static ApplicationProperties applicationPropertiesForNotificationJob() {
        return new ApplicationProperties()
            .setDaysBeforeNotification(3L)
            .setDaysBeforeDeleteArchive(6L);
    }

    public static ApplicationProperties applicationPropertiesForDeleteJob() {
        return new ApplicationProperties()
            .setDaysBeforeDeleteArchive(2L);
    }
}
