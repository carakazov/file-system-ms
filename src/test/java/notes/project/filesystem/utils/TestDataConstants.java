package notes.project.filesystem.utils;

import liquibase.pro.packaged.A;
import lombok.experimental.UtilityClass;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@UtilityClass
public class TestDataConstants {
    //Common
    public static Long ID = 1L;
    public static String CREATED_CLUSTER_EXTERNAL_ID_STRING = "3edce674-f3cf-4650-ad89-1bdd44b3f26a";
    public static String CREATED_DIRECTORY_EXTERNAL_ID_STRING = "c139de85-4d96-4f27-8648-8cc86c1286be";
    public static String CREATED_FILE_EXTERNAL_ID_STRING = "2a99b6fe-44f2-4837-bbee-80fbe43f3076";

    //File manager constants
    public static final String ROOT_DIRECTORY_PATH = "target/root";
    public static final String CREATE_CLUSTER_TITLE = "cluster-title";
    public static final String CREATE_DIRECTORY_TITLE = "directory-title";
    public static final String CREATE_FILE_TITLE = "file-title";
    public static final Path RESOLVED_PATH_FOR_CREATE_CLUSTER = Path.of(ROOT_DIRECTORY_PATH + "/" + CREATED_CLUSTER_EXTERNAL_ID_STRING);
    public static final Path RESOLVED_PATH_FOR_CREATE_DIRECTORY = Path.of(RESOLVED_PATH_FOR_CREATE_CLUSTER + "/" + CREATED_DIRECTORY_EXTERNAL_ID_STRING);
    public static final Path RESOLVED_PATH_FOR_CREATE_FILE = Path.of(RESOLVED_PATH_FOR_CREATE_DIRECTORY + "/" + CREATED_FILE_EXTERNAL_ID_STRING + ".txt");

    //Zip manager
    public static final String ARCHIVE_ROOT = "target/archive-root";
    public static final Path ARCHIVE_ROOT_PATH = Path.of(ARCHIVE_ROOT);
    public static final Path ZIPPED_CREATED_FILE_PATH = Path.of(ARCHIVE_ROOT + "/" + CREATED_FILE_EXTERNAL_ID_STRING + ".zip");

    //Cluster constants
    public static final UUID CREATED_CLUSTER_EXTERNAL_ID = UUID.fromString(CREATED_CLUSTER_EXTERNAL_ID_STRING);
    public static final LocalDateTime CREATED_CLUSTER_DATE = LocalDateTime.of(2022, 6, 26, 10, 10 ,10 );

    public static final String EXCEPTION_CODE = "unexpectedErrorWhileCreationOperation";
    public static final String EXCEPTION_MESSAGE = "exception message";
    public static final Map<String, String> EXCEPTION_MAP = Map.of(EXCEPTION_CODE, EXCEPTION_MESSAGE);

    //Directory constants
    public static final UUID DIRECTORY_EXTERNAL_ID = UUID.fromString(CREATED_DIRECTORY_EXTERNAL_ID_STRING);
    public static final LocalDateTime CREATED_DIRECTORY_TIME = LocalDateTime.of(2022, 6, 26, 10, 10 ,10 );

    //Created file constants
    public static final UUID CREATED_FILE_EXTERNAL_ID = UUID.fromString(CREATED_FILE_EXTERNAL_ID_STRING);
    public static final String FILE_CONTENT = "some test file content";
    public static final LocalDateTime CREATED_FILE_CREATED_DATE = LocalDateTime.of(2022, 6, 26, 10, 10 ,10 );

    //Delete history constants
    public static final LocalDateTime DELETE_DATE = LocalDateTime.of(2022,  6, 26, 10, 10, 10);

}
