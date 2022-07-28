package notes.project.filesystem.service.impl;

import java.util.UUID;

import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import notes.project.filesystem.mapper.ArchiveMapper;
import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.repository.ArchiveRepository;
import notes.project.filesystem.service.ArchiveService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArchiveServiceImpl implements ArchiveService {
    private final ArchiveRepository repository;
    private final ArchiveMapper archiveMapper;

    @Override
    @Transactional
    public void create(CreatedFile file, UUID versionFileExternalId) {
        repository.save(archiveMapper.to(file, versionFileExternalId));
    }
}
