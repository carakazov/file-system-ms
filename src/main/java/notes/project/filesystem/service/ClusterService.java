package notes.project.filesystem.service;

import notes.project.filesystem.dto.ClusterCreationRequestDto;
import notes.project.filesystem.dto.ClusterCreationResponseDto;
import notes.project.filesystem.model.Cluster;

public interface ClusterService {
    ClusterCreationResponseDto createCluster(ClusterCreationRequestDto request);

    Boolean clusterExistsByTitle(String clusterTitle);

    Cluster findByTitle(String clusterTitle);
}
