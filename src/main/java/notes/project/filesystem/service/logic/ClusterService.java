package notes.project.filesystem.service.logic;

import java.util.List;
import java.util.UUID;

import notes.project.filesystem.dto.ClusterCreationRequestDto;
import notes.project.filesystem.dto.ClusterCreationResponseDto;
import notes.project.filesystem.dto.DeleteHistoryResponseDto;
import notes.project.filesystem.dto.ReadClusterDto;
import notes.project.filesystem.model.Cluster;

public interface ClusterService {
    ClusterCreationResponseDto createCluster(ClusterCreationRequestDto request);

    Cluster findByExternalId(UUID externalId);

    void updateClusterLastRequestedTime(Cluster cluster);

    void deleteCluster(UUID externalId);

    ReadClusterDto readCluster(UUID externalId);

    Cluster findNotDeletedClusterByExternalId(UUID externalId);

    DeleteHistoryResponseDto getClusterDeleteHistory(UUID externalId);

    List<Cluster> findAllNotDeleted();

    List<Cluster> findAllDeleted();

    void eraseCluster(Cluster cluster);
}
