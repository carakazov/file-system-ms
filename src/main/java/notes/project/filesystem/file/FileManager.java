package notes.project.filesystem.file;

import notes.project.filesystem.model.*;

public interface FileManager {
    void createCluster(Cluster cluster);
    void createDirectory(Directory directory);
    void createFile(CreatedFile createdFile, String content);
}
