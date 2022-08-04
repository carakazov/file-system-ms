package notes.project.filesystem.service.logic;

import notes.project.filesystem.model.Cluster;
import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.model.Directory;

public interface RecreateService {
    void recreateFile(CreatedFile createdFile);
    void recreateDirectory(Directory directory);
    void recreateCluster(Cluster cluster);
    void recreateFileWithPath(CreatedFile createdFile);
    void recreateDirectoryWithPath(Directory directory);
}
