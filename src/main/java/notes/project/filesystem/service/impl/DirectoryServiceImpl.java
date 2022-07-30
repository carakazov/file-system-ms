package notes.project.filesystem.service.impl;

import java.util.UUID;
import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import notes.project.filesystem.dto.DirectoryCreationRequestDto;
import notes.project.filesystem.dto.DirectoryCreationResponseDto;
import notes.project.filesystem.dto.ReadDirectoryDto;
import notes.project.filesystem.exception.ExceptionCode;
import notes.project.filesystem.exception.ResourceNotFoundException;
import notes.project.filesystem.file.FileManager;
import notes.project.filesystem.file.ZipManager;
import notes.project.filesystem.mapper.DirectoryCreationMapper;
import notes.project.filesystem.mapper.ReadDirectoryMapper;
import notes.project.filesystem.model.Cluster;
import notes.project.filesystem.model.Directory;
import notes.project.filesystem.model.EventType;
import notes.project.filesystem.repository.DirectoryRepository;
import notes.project.filesystem.service.*;
import notes.project.filesystem.service.ObjectExistingStatusChanger;
import notes.project.filesystem.validation.Validator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DirectoryServiceImpl implements DirectoryService {
    private final DirectoryRepository repository;
    private final DirectoryCreationMapper directoryCreationMapper;
    private final FileManager fileManager;
    private final ClusterService clusterService;
    private final Validator<DirectoryCreationRequestDto> createDirectoryValidator;
    private final ZipManager zipManager;
    private final DeleteHistoryService deleteHistoryService;
    private final ObjectExistingStatusChanger objectExistingStatusChanger;
    private final ReadDirectoryMapper readDirectoryMapper;

    private final static Object LOCK = new Object();

    @Override
    @Transactional
    public DirectoryCreationResponseDto createDirectory(DirectoryCreationRequestDto request) {
        createDirectoryValidator.validate(request);
        Cluster cluster = clusterService.findNotDeletedClusterByExternalId(request.getClusterExternalId());
        Directory directory = directoryCreationMapper.from(request, cluster);
        directory = repository.save(directory);
        clusterService.updateClusterLastRequestedTime(cluster);
        fileManager.createDirectory(directory);
        return directoryCreationMapper.to(directory);
    }

    @Override
    public Directory findByExternalId(UUID externalId) {
        return repository.findByExternalId(externalId)
            .orElseThrow(() -> new ResourceNotFoundException(ExceptionCode.RESOURCE_NOT_FOUND));
    }

    @Override
    @Transactional
    public void deleteDirectory(UUID externalId) {
        Directory directory = findNotDeletedDirectoryByExternalId(externalId);
        deleteHistoryService.createDirectoryDeleteHistory(directory, EventType.DELETED);
        clusterService.updateClusterLastRequestedTime(directory.getCluster());
        synchronized(LOCK) {
            zipManager.zipDirectory(directory);
            objectExistingStatusChanger.changeDirectoryExistingStatus(directory);
        }
    }

    @Override
    @Transactional
    public ReadDirectoryDto readDirectory(UUID externalId) {
        Directory directory = findNotDeletedDirectoryByExternalId(externalId);
        return readDirectoryMapper.to(directory);
    }

    @Override
    public Directory findNotDeletedDirectoryByExternalId(UUID externalId) {
        return repository.findByExternalIdAndDeletedFalse(externalId)
            .orElseThrow(() -> new ResourceNotFoundException(ExceptionCode.RESOURCE_NOT_FOUND));
    }
}
