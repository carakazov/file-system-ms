package notes.project.filesystem.utils;

import java.nio.file.Path;

import notes.project.filesystem.model.Cluster;
import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.model.Directory;

public interface PathHelper {
    Path createPathToCluster(Cluster cluster);
    Path createPathToDirectory(Directory directory);
    Path createPathToFile(CreatedFile file);
}
