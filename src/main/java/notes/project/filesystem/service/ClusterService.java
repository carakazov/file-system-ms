package notes.project.filesystem.service;

import notes.project.filesystem.dto.ClusterCreationRequestDto;
import notes.project.filesystem.dto.ClusterCreationResponseDto;

public interface ClusterService {
    ClusterCreationResponseDto createCluster(ClusterCreationRequestDto request);
}
