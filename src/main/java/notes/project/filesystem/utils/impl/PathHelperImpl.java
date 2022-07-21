package notes.project.filesystem.utils.impl;

import java.nio.file.Path;

import lombok.RequiredArgsConstructor;
import notes.project.filesystem.config.ApplicationProperties;
import notes.project.filesystem.model.Cluster;
import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.model.Directory;
import notes.project.filesystem.utils.PathHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PathHelperImpl implements PathHelper {
    private final ApplicationProperties properties;

    private static final String FILE_RESOLUTION = ".txt";

    @Override
    public Path createPathToCluster(Cluster cluster) {
        return Path.of(properties.getRoot() + "/" + cluster.getExternalId().toString());
    }

    @Override
    public Path createPathToDirectory(Directory directory) {
        return Path.of(
            createPathToCluster(directory.getCluster()) + "/" + directory.getExternalId().toString()
        );
    }

    @Override
    public Path createPathToFile(CreatedFile file) {
        return Path.of(
            createPathToDirectory(file.getDirectory()) + "/" + file.getExternalId().toString() + FILE_RESOLUTION
        );
    }
}
