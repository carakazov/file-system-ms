package notes.project.filesystem.utils;

import lombok.experimental.UtilityClass;
import notes.project.filesystem.model.Cluster;

@UtilityClass
public class DbUtils {
    public static Cluster cluster() {
        return new Cluster()
                .setId(TestDataConstants.ID)
                .setTitle(TestDataConstants.CREATE_CLUSTER_TITLE)
                .setExternalId(TestDataConstants.CREATED_CLUSTER_EXTERNAL_ID)
                .setCreateDate(TestDataConstants.CREATED_CLUSTER_DATE)
                .setLastRequestDate(TestDataConstants.CREATED_CLUSTER_DATE);
    }

    public static Cluster clusterForCreation() {
        return new Cluster()
                .setTitle(TestDataConstants.CREATE_CLUSTER_TITLE)
                .setExternalId(TestDataConstants.CREATED_CLUSTER_EXTERNAL_ID);
    }
}
