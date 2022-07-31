package notes.project.filesystem.service;

import java.util.UUID;

import notes.project.filesystem.dto.DeleteHistoryResponseDto;

public interface DeleteHistoryFacade {
    DeleteHistoryResponseDto getCreatedFileHistory(UUID fileExternalId);
}
