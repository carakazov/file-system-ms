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

class PathHelperTest {
    private PathHelper pathHelper;

    @BeforeEach
    void init() {
        pathHelper = new PathHelperImpl(ApplicationPropertiesUtils.applicationPropertiesForPathHelper());
    }

    @Test
    void createPathToCluster() {
        String actual = pathHelper.createPathToCluster(TestDataConstants.CREATE_CLUSTER_TITLE);
        assertEquals(TestDataConstants.RESOLVED_PATH_FOR_CREATE_CLUSTER, actual);
    }
}
