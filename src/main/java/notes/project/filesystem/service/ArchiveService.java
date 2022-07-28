package notes.project.filesystem.service;

import java.util.UUID;

import notes.project.filesystem.model.CreatedFile;

public interface ArchiveService {
    void create(CreatedFile file, UUID versionFileExternalId);
}
