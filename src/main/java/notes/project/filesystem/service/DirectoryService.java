package notes.project.filesystem.service;

import java.util.UUID;

import notes.project.filesystem.dto.DirectoryCreationRequestDto;
import notes.project.filesystem.dto.DirectoryCreationResponseDto;
import notes.project.filesystem.dto.ReadDirectoryDto;
import notes.project.filesystem.model.Directory;

public interface DirectoryService {
    DirectoryCreationResponseDto createDirectory(DirectoryCreationRequestDto request);

    Directory findByExternalId(UUID externalId);

    void deleteDirectory(UUID externalId);

    ReadDirectoryDto readDirectory(UUID externalId);
}
