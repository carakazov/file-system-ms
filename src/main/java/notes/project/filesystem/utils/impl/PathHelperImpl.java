package notes.project.filesystem.utils.impl;

import lombok.RequiredArgsConstructor;
import notes.project.filesystem.config.ApplicationProperties;
import notes.project.filesystem.utils.PathHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PathHelperImpl implements PathHelper {
    private final ApplicationProperties properties;

    private static final String FILE_RESOLUTION = ".txt";

    @Override
    public String createPathToCluster(String clusterTile) {
        return properties.getRoot() + "/" + clusterTile;
    }

    @Override
    public String createPathToDirectory(String clusterTitle, String directoryTitle) {
        return createPathToCluster(clusterTitle) + "/" + directoryTitle;
    }

    @Override
    public String createPathToFile(String clusterId, String directoryId, String fileId) {
        return createPathToDirectory(clusterId, directoryId) + "/" + fileId + FILE_RESOLUTION;
    }
}
