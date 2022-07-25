package notes.project.filesystem.service;

import java.util.UUID;

import notes.project.filesystem.dto.ClusterCreationRequestDto;
import notes.project.filesystem.dto.ClusterCreationResponseDto;
import notes.project.filesystem.model.Cluster;

public interface ClusterService {
    ClusterCreationResponseDto createCluster(ClusterCreationRequestDto request);

    Cluster findByExternalId(UUID externalId);

    void updateClusterLastRequestedTime(Cluster cluster);

    void deleteCluster(UUID externalId);
}
