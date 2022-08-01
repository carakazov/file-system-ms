package notes.project.filesystem.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import notes.project.filesystem.dto.ClusterCreationRequestDto;
import notes.project.filesystem.dto.ClusterCreationResponseDto;
import notes.project.filesystem.dto.DeleteHistoryResponseDto;
import notes.project.filesystem.dto.ReadClusterDto;
import notes.project.filesystem.exception.ExceptionCode;
import notes.project.filesystem.exception.ResourceNotFoundException;
import notes.project.filesystem.file.FileManager;
import notes.project.filesystem.file.ZipManager;
import notes.project.filesystem.mapper.ClusterCreationMapper;
import notes.project.filesystem.mapper.ReadClusterMapper;
import notes.project.filesystem.model.Cluster;
import notes.project.filesystem.model.EventType;
import notes.project.filesystem.repository.ClusterRepository;
import notes.project.filesystem.service.ClusterService;
import notes.project.filesystem.service.DeleteHistoryService;
import notes.project.filesystem.service.ObjectExistingStatusChanger;
import notes.project.filesystem.validation.Validator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClusterServiceImpl implements ClusterService {
    private final ClusterRepository clusterRepository;
    private final FileManager fileManager;
    private final ClusterCreationMapper clusterCreationMapper;
    private final Validator<ClusterCreationRequestDto> createClusterValidator;
    private final DeleteHistoryService deleteHistoryService;
    private final ObjectExistingStatusChanger objectExistingStatusChanger;
    private final ZipManager zipManager;
    private final ReadClusterMapper readClusterMapper;

    private final static Object LOCK = new Object();

    @Override
    @Transactional
    public ClusterCreationResponseDto createCluster(ClusterCreationRequestDto request) {
        createClusterValidator.validate(request);
        Cluster cluster = clusterRepository.save(clusterCreationMapper.from(request));
        fileManager.createCluster(cluster);
        return clusterCreationMapper.to(cluster);
    }

    @Override
    public Cluster findByExternalId(UUID clusterExternalId) {
        return clusterRepository.findByExternalId(clusterExternalId)
            .orElseThrow(() -> new ResourceNotFoundException(ExceptionCode.RESOURCE_NOT_FOUND));
    }

    @Override
    @Transactional
    public void updateClusterLastRequestedTime(Cluster cluster) {
        cluster.setLastRequestDate(LocalDateTime.now());
    }

    @Override
    @Transactional
    public void deleteCluster(UUID externalId) {
        Cluster cluster = findNotDeletedClusterByExternalId(externalId);
        deleteHistoryService.createClusterDeleteHistory(cluster, EventType.DELETED);
        cluster.getDirectories().forEach(item -> {
            deleteHistoryService.createDirectoryDeleteHistory(item, EventType.DELETED);
            item.getCreatedFiles().forEach(innerItem -> deleteHistoryService.createCreatedFileDeleteHistory(innerItem, EventType.DELETED));
        });
        synchronized(LOCK) {
            zipManager.zipCluster(cluster);
            objectExistingStatusChanger.changeClusterExistingStatus(cluster, Boolean.TRUE);
        }
    }

    @Override
    @Transactional
    public ReadClusterDto readCluster(UUID externalId) {
        Cluster cluster = findNotDeletedClusterByExternalId(externalId);
        return readClusterMapper.to(cluster);
    }

    @Override
    public Cluster findNotDeletedClusterByExternalId(UUID externalId) {
        return clusterRepository.findByExternalIdAndDeletedFalse(externalId)
            .orElseThrow(() -> new ResourceNotFoundException(ExceptionCode.RESOURCE_NOT_FOUND));
    }

    @Override
    public DeleteHistoryResponseDto getClusterDeleteHistory(UUID externalId) {
        Cluster cluster = findByExternalId(externalId);
        return deleteHistoryService.getClusterDeleteHistory(cluster);
    }
}
