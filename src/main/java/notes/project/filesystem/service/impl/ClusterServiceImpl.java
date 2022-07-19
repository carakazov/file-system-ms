package notes.project.filesystem.service.impl;

import lombok.RequiredArgsConstructor;
import notes.project.filesystem.dto.ClusterCreationRequestDto;
import notes.project.filesystem.dto.ClusterCreationResponseDto;
import notes.project.filesystem.file.FileManager;
import notes.project.filesystem.mapper.ClusterCreationMapper;
import notes.project.filesystem.model.Cluster;
import notes.project.filesystem.repository.ClusterRepository;
import notes.project.filesystem.service.ClusterService;
import notes.project.filesystem.validation.Validator;
import notes.project.filesystem.validation.dto.CreateClusterValidationDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ClusterServiceImpl implements ClusterService {
    private final ClusterRepository clusterRepository;
    private final FileManager fileManager;
    private final ClusterCreationMapper clusterCreationMapper;
    private final Validator<CreateClusterValidationDto> createClusterValidator;

    @Override
    @Transactional
    public ClusterCreationResponseDto createCluster(ClusterCreationRequestDto request) {
        createClusterValidator.validate(new CreateClusterValidationDto(clusterRepository.existsByTitle(request.getClusterTitle())));
        fileManager.createCluster(request.getClusterTitle());
        Cluster cluster = clusterRepository.save(clusterCreationMapper.from(request));
        return clusterCreationMapper.to(cluster);
    }

    @Override
    public Boolean clusterExistsByTitle(String clusterTitle) {
        return clusterRepository.existsByTitle(clusterTitle);
    }

    @Override
    public Cluster findByTitle(String clusterTitle) {
        return clusterRepository.findByTitle(clusterTitle);
    }
}
