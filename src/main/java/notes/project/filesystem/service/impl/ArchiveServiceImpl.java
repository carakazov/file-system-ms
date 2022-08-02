package notes.project.filesystem.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import notes.project.filesystem.dto.ArchiveHistoryResponseDto;
import notes.project.filesystem.exception.ExceptionCode;
import notes.project.filesystem.exception.ResourceNotFoundException;
import notes.project.filesystem.file.ZipManager;
import notes.project.filesystem.mapper.ArchiveHistoryResponseMapper;
import notes.project.filesystem.mapper.CreateArchiveMapper;
import notes.project.filesystem.model.Archive;
import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.repository.ArchiveRepository;
import notes.project.filesystem.service.ArchiveService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArchiveServiceImpl implements ArchiveService {
    private final ArchiveRepository repository;
    private final CreateArchiveMapper createArchiveMapper;
    private final ArchiveHistoryResponseMapper archiveHistoryResponseMapper;
    private final ZipManager zipManager;

    private static final Comparator<Archive> ARCHIVE_COMPARATOR = (o1, o2) -> o2.getEditedDate().compareTo(o1.getEditedDate());

    @Override
    @Transactional
    public void create(CreatedFile file, UUID versionFileExternalId) {
        repository.save(createArchiveMapper.to(file, versionFileExternalId));
    }

    @Override
    public ArchiveHistoryResponseDto getArchiveHistory(CreatedFile file) {
        List<Archive> history = repository.findAllByCreatedFile(file);
        history.sort(ARCHIVE_COMPARATOR);
        return archiveHistoryResponseMapper.to(file, history);
    }

    @Override
    public String readFileVersion(UUID fileVersionGuid) {
        Archive archive = repository.findByVersionFileGuid(fileVersionGuid).orElseThrow(() -> new ResourceNotFoundException(ExceptionCode.RESOURCE_NOT_FOUND));
        return zipManager.readZipFile(archive.getVersionFileGuid());
    }
}
