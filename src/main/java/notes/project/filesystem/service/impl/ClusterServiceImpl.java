package notes.project.filesystem.service.impl;

import java.lang.module.ResolutionException;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import notes.project.filesystem.dto.ClusterCreationRequestDto;
import notes.project.filesystem.dto.ClusterCreationResponseDto;
import notes.project.filesystem.exception.ExceptionCode;
import notes.project.filesystem.exception.FileSystemException;
import notes.project.filesystem.exception.ResourceNotFoundException;
import notes.project.filesystem.file.FileManager;
import notes.project.filesystem.mapper.ClusterCreationMapper;
import notes.project.filesystem.model.Cluster;
import notes.project.filesystem.repository.ClusterRepository;
import notes.project.filesystem.service.ClusterService;
import notes.project.filesystem.validation.Validator;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ClusterServiceImpl implements ClusterService {
    private final ClusterRepository clusterRepository;
    private final FileManager fileManager;
    private final ClusterCreationMapper clusterCreationMapper;
    private final Validator<ClusterCreationRequestDto> createClusterValidator;

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
}
