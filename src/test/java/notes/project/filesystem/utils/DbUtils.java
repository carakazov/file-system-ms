package notes.project.filesystem.utils;

import java.util.Collections;

import lombok.experimental.UtilityClass;
import notes.project.filesystem.model.*;

import static notes.project.filesystem.utils.TestDataConstants.*;

@UtilityClass
public class DbUtils {
    public static Cluster cluster() {
        return new Cluster()
                .setId(ID)
                .setTitle(CLUSTER_TITLE)
                .setExternalId(CLUSTER_EXTERNAL_ID)
                .setCreateDate(CREATED_CLUSTER_DATE)
                .setLastRequestDate(CREATED_CLUSTER_DATE);
    }

    public static Directory directory() {
        return new Directory()
            .setId(ID)
            .setTitle(DIRECTORY_TITLE)
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
            .setTitle(FILE_TITLE)
            .setDeleted(Boolean.FALSE)
            .setExternalId(FILE_EXTERNAL_ID)
            .setDirectory(directory())
            .setCreatedDate(CREATED_FILE_CREATED_DATE);
    }

    public static DeleteHistory deleteDirectoryHistory() {
        return new DeleteHistory()
            .setId(ID)
            .setDirectory(directory())
            .setEvent(EventType.DELETED)
            .setDate(DELETE_DATE);
    }

    public static Cluster clusterWithFiles() {
        Cluster cluster = cluster();
        Directory directory = directoryWithFiles();
        cluster.setDirectories(Collections.singletonList(directory));
        return cluster;
    }

    public static DeleteHistory deleteCreatedFileHistory() {
        return new DeleteHistory()
            .setId(ID)
            .setCreatedFile(createdFile())
            .setEvent(EventType.DELETED)
            .setDate(DELETE_DATE);
    }

    public static DeleteHistory deleteClusterHistory() {
        return new DeleteHistory()
            .setId(ID)
            .setCluster(cluster())
            .setEvent(EventType.DELETED)
            .setDate(DELETE_DATE);
    }

    public static ReplacingHistory replacingHistory() {
        return new ReplacingHistory()
            .setId(ID)
            .setCreatedFile(createdFile())
            .setSourceDirectory(directory())
            .setTargetDirectory(directoryWithAlternativeExternalId())
            .setReplacingDate(REPLACING_DATE);
    }

    public static Directory directoryWithAlternativeExternalId() {
        return new Directory()
            .setId(ID_2)
            .setExternalId(ALTERNATIVE_DIRECTORY_EXTERNAL_ID)
            .setCluster(cluster())
            .setTitle(DIRECTORY_TITLE)
            .setDeleted(Boolean.FALSE)
            .setCreateDate(CREATED_DIRECTORY_TIME);
    }

    public static Archive archive() {
        return new Archive()
            .setId(ID)
            .setVersionFileGuid(FILE_VERSION_UUID)
            .setCreatedFile(createdFile())
            .setEditedDate(EDITED_DATE);
    }
}
