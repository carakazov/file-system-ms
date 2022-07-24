package notes.project.filesystem.service.impl;

import java.util.UUID;
import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import notes.project.filesystem.dto.DirectoryCreationRequestDto;
import notes.project.filesystem.dto.DirectoryCreationResponseDto;
import notes.project.filesystem.exception.ExceptionCode;
import notes.project.filesystem.exception.FileSystemException;
import notes.project.filesystem.exception.ResourceNotFoundException;
import notes.project.filesystem.file.FileManager;
import notes.project.filesystem.file.ZipManager;
import notes.project.filesystem.mapper.DirectoryCreationMapper;
import notes.project.filesystem.model.Cluster;
import notes.project.filesystem.model.Directory;
import notes.project.filesystem.repository.CreatedFileRepository;
import notes.project.filesystem.repository.DirectoryRepository;
import notes.project.filesystem.service.ClusterService;
import notes.project.filesystem.service.CreatedFileService;
import notes.project.filesystem.service.DeleteHistoryService;
import notes.project.filesystem.service.DirectoryService;
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


    @Override
    @Transactional
    public DirectoryCreationResponseDto createDirectory(DirectoryCreationRequestDto request) {
        createDirectoryValidator.validate(request);
        Cluster cluster = clusterService.findByExternalId(request.getClusterExternalId());
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
        Directory directory = findByExternalId(externalId);
        directory.setDeleted(Boolean.TRUE);
        directory.getCreatedFiles().forEach(item -> item.setDeleted(Boolean.TRUE));
        deleteHistoryService.createDirectoryDeleteHistory(directory);
        clusterService.updateClusterLastRequestedTime(directory.getCluster());
        zipManager.zipDirectory(directory);
    }
}
