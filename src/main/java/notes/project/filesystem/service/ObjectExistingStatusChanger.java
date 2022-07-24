package notes.project.filesystem.service;

import notes.project.filesystem.model.Cluster;
import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.model.Directory;

public interface ObjectExistingStatusChanger {
    void changeClusterExistingStatus(Cluster cluster);

    void changeDirectoryExistingStatus(Directory directory);

    void changeCreatedFileExistingStatus(CreatedFile createdFile);
}
