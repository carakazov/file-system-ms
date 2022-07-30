package notes.project.filesystem.service;

import java.util.UUID;

import notes.project.filesystem.model.Cluster;
import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.model.Directory;

public interface RecreationService {
    void recreateFile(CreatedFile createdFile);
    void recreateDirectory(Directory directory);
    void recreateCluster(Cluster cluster);
}
