package notes.project.filesystem.service;

import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.model.Directory;
import notes.project.filesystem.model.ReplacingHistory;

public interface ReplacingHistoryService {
    ReplacingHistory create(CreatedFile createdFile, Directory newDirectory);
}
