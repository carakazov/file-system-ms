package notes.project.filesystem.service;

import notes.project.filesystem.dto.DirectoryCreationRequestDto;
import notes.project.filesystem.dto.DirectoryCreationResponseDto;

public interface DirectoryService {
    DirectoryCreationResponseDto createDirectory(DirectoryCreationRequestDto request);
}
