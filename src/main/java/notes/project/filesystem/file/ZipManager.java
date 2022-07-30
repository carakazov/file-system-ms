package notes.project.filesystem.file;

import java.util.List;
import java.util.UUID;

import notes.project.filesystem.model.Cluster;
import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.model.Directory;

public interface ZipManager {
    void zipDirectory(Directory directory);
    void zipCreatedFile(CreatedFile createdFile);
    void zipCluster(Cluster cluster);
    void zipFileForUpdate(CreatedFile createdFile, UUID versionFileExternalId);
    void recreateFile(CreatedFile createdFile);
    void recreateDirectory(Directory directory);
    void recreateCluster(Cluster cluster);
}
