package notes.project.filesystem.utils;

import lombok.experimental.UtilityClass;
import notes.project.filesystem.model.FileResolution;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@UtilityClass
public class TestDataConstants {
    //Common
    public static Long ID = 1L;
    public static Long ID_2 = 2L;
    public static String CLUSTER_EXTERNAL_ID_STRING = "3edce674-f3cf-4650-ad89-1bdd44b3f26a";
    public static String DIRECTORY_EXTERNAL_ID_STRING = "c139de85-4d96-4f27-8648-8cc86c1286be";
    public static String FILE_EXTERNAL_ID_STRING = "2a99b6fe-44f2-4837-bbee-80fbe43f3076";

    //File manager constants
    public static final String ROOT_DIRECTORY_PATH = "target/root";
    public static final String CLUSTER_TITLE = "cluster-title";
    public static final String DIRECTORY_TITLE = "directory-title";
    public static final String FILE_TITLE = "file-title";
    public static final Path RESOLVED_PATH_FOR_CREATE_CLUSTER = Path.of(ROOT_DIRECTORY_PATH + "/" + CLUSTER_EXTERNAL_ID_STRING);
    public static final Path RESOLVED_PATH_FOR_CREATE_DIRECTORY = Path.of(RESOLVED_PATH_FOR_CREATE_CLUSTER + "/" + DIRECTORY_EXTERNAL_ID_STRING);
    public static final Path RESOLVED_PATH_FOR_CREATE_FILE = Path.of(RESOLVED_PATH_FOR_CREATE_DIRECTORY + "/" + FILE_EXTERNAL_ID_STRING + ".txt");

    //Zip manager
    public static final String ARCHIVE_ROOT = "target/archive-root";
    public static final Path ARCHIVE_ROOT_PATH = Path.of(ARCHIVE_ROOT);
    public static final Path ZIPPED_CREATED_FILE_PATH = Path.of(ARCHIVE_ROOT + "/" + FILE_EXTERNAL_ID_STRING + ".zip");

    //Cluster constants
    public static final UUID CLUSTER_EXTERNAL_ID = UUID.fromString(CLUSTER_EXTERNAL_ID_STRING);
    public static final LocalDateTime CREATED_CLUSTER_DATE = LocalDateTime.of(2022, 6, 26, 10, 10 ,10 );

    public static final String EXCEPTION_CODE = "unexpectedErrorWhileCreationOperation";
    public static final String EXCEPTION_MESSAGE = "exception message";
    public static final Map<String, String> EXCEPTION_MAP = Map.of(EXCEPTION_CODE, EXCEPTION_MESSAGE);

    //Directory constants
    public static final UUID DIRECTORY_EXTERNAL_ID = UUID.fromString(DIRECTORY_EXTERNAL_ID_STRING);
    public static final LocalDateTime CREATED_DIRECTORY_TIME = LocalDateTime.of(2022, 6, 26, 10, 10 ,10 );
    public static final String ALTERNATIVE_DIRECTORY_EXTERNAL_ID_STRING = "9816d9b5-5988-4cc9-b6cf-5628ce00648f";
    public static final UUID ALTERNATIVE_DIRECTORY_EXTERNAL_ID = UUID.fromString(ALTERNATIVE_DIRECTORY_EXTERNAL_ID_STRING);
    public static final Path PATH_TO_ALTERNATIVE_DIRECTORY = Path.of(ROOT_DIRECTORY_PATH + "/" + ALTERNATIVE_DIRECTORY_EXTERNAL_ID_STRING);
    public static final Path PATH_TO_REPLACED_FILE = Path.of(PATH_TO_ALTERNATIVE_DIRECTORY + "/" + FILE_EXTERNAL_ID_STRING + FileResolution.TXT.getResolution());

    //Created file constants
    public static final UUID FILE_EXTERNAL_ID = UUID.fromString(FILE_EXTERNAL_ID_STRING);
    public static final String FILE_CONTENT = "some test file content";
    public static final LocalDateTime CREATED_FILE_CREATED_DATE = LocalDateTime.of(2022, 6, 26, 10, 10 ,10 );

    //Delete history constants
    public static final LocalDateTime DELETE_DATE = LocalDateTime.of(2022,  6, 26, 10, 10, 10);

    //Replacing history constants
    public static final LocalDateTime REPLACING_DATE = LocalDateTime.of(2022,  6, 26, 10, 10, 12);

}
