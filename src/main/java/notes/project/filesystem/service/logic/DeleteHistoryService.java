package notes.project.filesystem.service.logic;

import notes.project.filesystem.dto.DeleteHistoryResponseDto;
import notes.project.filesystem.model.Cluster;
import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.model.Directory;
import notes.project.filesystem.model.EventType;

public interface DeleteHistoryService {
    void createDirectoryDeleteHistory(Directory directory, EventType eventType);
    void createCreatedFileDeleteHistory(CreatedFile createdFile, EventType eventType);
    void createClusterDeleteHistory(Cluster cluster, EventType eventType);

    DeleteHistoryResponseDto getCreatedFileDeleteHistory(CreatedFile createdFile);
    DeleteHistoryResponseDto getDirectoryDeleteHistory(Directory directory);
    DeleteHistoryResponseDto getClusterDeleteHistory(Cluster cluster);
}
