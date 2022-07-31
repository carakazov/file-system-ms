package notes.project.filesystem.service;

import notes.project.filesystem.model.Cluster;
import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.model.Directory;

public interface ObjectExistingStatusChanger {
    void changeClusterExistingStatus(Cluster cluster, Boolean deleted);
    void changeDirectoryExistingStatus(Directory directory, Boolean deleted);
    void changeCreatedFileExistingStatus(CreatedFile createdFile, Boolean deleted);

    void changeClusterExistingStatusOnly(Cluster cluster, Boolean deleted);
    void changeDirectoryExistingStatusOnly(Directory directory, Boolean deleted);
}
