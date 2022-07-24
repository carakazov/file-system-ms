package notes.project.filesystem.utils;

import java.util.Collections;

import liquibase.pro.packaged.D;
import lombok.experimental.UtilityClass;
import notes.project.filesystem.model.*;

import static notes.project.filesystem.utils.TestDataConstants.*;

@UtilityClass
public class DbUtils {
    public static Cluster cluster() {
        return new Cluster()
                .setId(ID)
                .setTitle(CREATE_CLUSTER_TITLE)
                .setExternalId(CREATED_CLUSTER_EXTERNAL_ID)
                .setCreateDate(CREATED_CLUSTER_DATE)
                .setLastRequestDate(CREATED_CLUSTER_DATE);
    }

    public static Directory directory() {
        return new Directory()
            .setId(ID)
            .setTitle(CREATE_DIRECTORY_TITLE)
            .setExternalId(DIRECTORY_EXTERNAL_ID)
            .setCreateDate(CREATED_DIRECTORY_TIME)
            .setDeleted(Boolean.FALSE)
            .setCluster(cluster());
    }

    public static Directory directoryWithFiles() {
        Directory directory = directory();
        CreatedFile createdFile = createdFile();
        directory.setCreatedFiles(Collections.singletonList(createdFile));
        return directory;
    }

    public static CreatedFile createdFile() {
        return new CreatedFile()
            .setId(ID)
            .setTitle(CREATE_FILE_TITLE)
            .setDeleted(Boolean.FALSE)
            .setExternalId(CREATED_FILE_EXTERNAL_ID)
            .setDirectory(directory());
    }

    public static DeleteHistory deleteDirectoryHistory() {
        return new DeleteHistory()
            .setId(ID)
            .setDirectory(directory())
            .setEvent(EventType.DELETED)
            .setDate(DELETE_DATE);
    }

    public static DeleteHistory deleteCreatedFileHistory() {
        return new DeleteHistory()
            .setId(ID)
            .setCreatedFile(createdFile())
            .setEvent(EventType.DELETED)
            .setDate(DELETE_DATE);
    }
}
