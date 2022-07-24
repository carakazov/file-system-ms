package notes.project.filesystem.service;

import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.model.Directory;

public interface DeleteHistoryService {
    void createDirectoryDeleteHistory(Directory directory);

    void createCreatedFileDeleteHistory(CreatedFile createdFile);
}
