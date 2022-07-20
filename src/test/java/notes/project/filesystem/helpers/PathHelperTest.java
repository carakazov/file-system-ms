package notes.project.filesystem.helpers;

import notes.project.filesystem.utils.ApplicationPropertiesUtils;
import notes.project.filesystem.utils.PathHelper;
import notes.project.filesystem.utils.TestDataConstants;
import notes.project.filesystem.utils.impl.PathHelperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static notes.project.filesystem.utils.TestDataConstants.*;

class PathHelperTest {
    private PathHelper pathHelper;

    @BeforeEach
    void init() {
        pathHelper = new PathHelperImpl(ApplicationPropertiesUtils.applicationPropertiesForPathHelper());
    }

    @Test
    void createPathToClusterSuccess() {
        String actual = pathHelper.createPathToCluster(CREATE_CLUSTER_TITLE);
        assertEquals(RESOLVED_PATH_FOR_CREATE_CLUSTER, actual);
    }

    @Test
    void createPathToDirectorySuccess() {
        String actual = pathHelper.createPathToDirectory(CREATE_CLUSTER_TITLE, CREATE_DIRECTORY_TITLE);
        assertEquals(RESOLVED_PATH_FORE_CREATE_DIRECTORY, actual);
    }
}
