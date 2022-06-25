package notes.project.filesystem.utils.impl;

import lombok.RequiredArgsConstructor;
import notes.project.filesystem.config.ApplicationProperties;
import notes.project.filesystem.utils.PathHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PathHelperImpl implements PathHelper {
    private final ApplicationProperties properties;
    @Override
    public String createPathToCluster(String clusterTile) {
        return properties.getRoot() + "/" + clusterTile;
    }
}
