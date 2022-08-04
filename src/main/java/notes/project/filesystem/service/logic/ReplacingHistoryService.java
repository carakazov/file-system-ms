package notes.project.filesystem.service.logic;

import notes.project.filesystem.dto.ReplacingHistoryResponseDto;
import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.model.Directory;
import notes.project.filesystem.model.ReplacingHistory;

public interface ReplacingHistoryService {
    ReplacingHistory create(CreatedFile createdFile, Directory newDirectory);
    ReplacingHistoryResponseDto getReplacingHistory(CreatedFile createdFile);
}
