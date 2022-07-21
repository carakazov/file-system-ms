package notes.project.filesystem.file;

import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.model.Directory;

public interface FileManager {
    void createCluster(String title);
    void createDirectory(String clusterTitle, String directoryTitle);
    void createFile(CreatedFile createdFile, String content);
}
