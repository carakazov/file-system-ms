package notes.project.filesystem.service.impl;

import java.util.UUID;
import javax.transaction.Transactional;

import liquibase.change.core.LoadDataChange;
import lombok.RequiredArgsConstructor;
import notes.project.filesystem.dto.AddFileRequestDto;
import notes.project.filesystem.dto.AddFileResponseDto;
import notes.project.filesystem.exception.ExceptionCode;
import notes.project.filesystem.exception.ResourceNotFoundException;
import notes.project.filesystem.file.FileManager;
import notes.project.filesystem.file.ZipManager;
import notes.project.filesystem.mapper.FileCreationMapper;
import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.model.Directory;
import notes.project.filesystem.repository.CreatedFileRepository;
import notes.project.filesystem.service.*;
import notes.project.filesystem.service.ObjectExistingStatusChanger;
import notes.project.filesystem.validation.Validator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatedFileServiceImpl implements CreatedFileService {
    private final CreatedFileRepository repository;
    private final FileManager fileManager;
    private final DirectoryService directoryService;
    private final FileCreationMapper fileCreationMapper;
    private final ClusterService clusterService;
    private final Validator<AddFileRequestDto> addFileValidator;
    private final DeleteHistoryService deleteHistoryService;
    private final ZipManager zipManager;
    private final ObjectExistingStatusChanger objectExistingStatusChanger;

    private final static Object LOCK = new Object();

    @Override
    @Transactional
    public AddFileResponseDto addFile(AddFileRequestDto request) {
        addFileValidator.validate(request);
        Directory directory = directoryService.findByExternalId(request.getDirectoryExternalId());
        CreatedFile file = new CreatedFile();
        file.setDirectory(directory);
        file.setTitle(request.getTitle());
        fileManager.createFile(file, request.getContent());
        clusterService.updateClusterLastRequestedTime(directory.getCluster());
        return fileCreationMapper.to(repository.save(file));
    }

    @Override
    public CreatedFile findFileByExternalId(UUID externalId) {
        return repository.findByExternalId(externalId)
            .orElseThrow(() -> new ResourceNotFoundException(ExceptionCode.RESOURCE_NOT_FOUND));
    }

    @Override
    @Transactional
    public void deleteCreatedFile(UUID fileExternalId) {
        CreatedFile createdFile = findFileByExternalId(fileExternalId);
        deleteHistoryService.createCreatedFileDeleteHistory(createdFile);
        clusterService.updateClusterLastRequestedTime(createdFile.getDirectory().getCluster());
        synchronized(LOCK) {
            zipManager.zipCreatedFile(createdFile);
            objectExistingStatusChanger.changeCreatedFileExistingStatus(createdFile);
        }
    }
}
