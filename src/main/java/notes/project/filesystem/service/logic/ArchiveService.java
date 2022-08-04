package notes.project.filesystem.service.logic;

import java.util.UUID;

import notes.project.filesystem.dto.ArchiveHistoryResponseDto;
import notes.project.filesystem.model.Archive;
import notes.project.filesystem.model.CreatedFile;

public interface ArchiveService {
    void create(CreatedFile file, UUID versionFileExternalId);

    ArchiveHistoryResponseDto getArchiveHistory(CreatedFile file);

    Archive findByVersionFileGuid(UUID versionFileGuid);

    String readFileVersion(UUID fileVersionGuid);
}
