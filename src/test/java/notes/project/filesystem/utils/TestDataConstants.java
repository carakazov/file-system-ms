package notes.project.filesystem.utils;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@UtilityClass
public class TestDataConstants {
    //Common
    public static Long ID = 1L;

    //File manager constants
    public static final String ROOT_DIRECTORY_PATH = "target/root-test";
    public static final String CREATE_CLUSTER_TITLE = "cluster-test";
    public static final String RESOLVED_PATH_FOR_CREATE_CLUSTER = ROOT_DIRECTORY_PATH + "/" + CREATE_CLUSTER_TITLE;

    //Cluster constants
    public static final UUID CREATED_CLUSTER_EXTERNAL_ID = UUID.fromString("3edce674-f3cf-4650-ad89-1bdd44b3f26a");
    public static final LocalDateTime CREATED_CLUSTER_DATE = LocalDateTime.of(2022, 6, 26, 10, 10 ,10 );

    public static final String EXCEPTION_CODE = "clusterCreationError";
    public static final String EXCEPTION_MESSAGE = "exception message";
    public static final Map<String, String> EXCEPTION_MAP = Map.of(EXCEPTION_CODE, EXCEPTION_MESSAGE);
}
