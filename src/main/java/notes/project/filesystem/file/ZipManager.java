package notes.project.filesystem.file;

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
    void recreateFileWithPath(CreatedFile createdFile);
    void recreateDirectoryWithPath(Directory directory);
    void recreateDirectory(Directory directory);
    void recreateCluster(Cluster cluster);
    void deleteZip(CreatedFile createdFile);
    String readZipFile(UUID versionFileExternalId);
}
