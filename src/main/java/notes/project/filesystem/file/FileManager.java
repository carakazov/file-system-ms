package notes.project.filesystem.file;

import notes.project.filesystem.model.Cluster;
import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.model.Directory;

public interface FileManager {
    void createCluster(Cluster cluster);
    void createDirectory(Directory directory);
    void createFile(CreatedFile createdFile, String content);

    String readFile(CreatedFile createdFile);

    void deleteDirectory(Directory directory);
    void deleteFile(CreatedFile createdFile);
    void deleteCluster(Cluster cluster);
    void moveFile(CreatedFile file, Directory newDirectory);
    void updateFile(CreatedFile oldFile, String newContent);
}
