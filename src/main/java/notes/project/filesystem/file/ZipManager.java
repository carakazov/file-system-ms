package notes.project.filesystem.file;

import java.util.List;

import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.model.Directory;

public interface ZipManager {
    void zipDirectory(Directory directory);
    void zipCreatedFile(CreatedFile createdFile);
}
