package notes.project.filesystem.mapper;


import notes.project.filesystem.dto.ClusterCreationRequestDto;
import notes.project.filesystem.dto.ClusterCreationResponseDto;
import notes.project.filesystem.model.Cluster;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClusterCreationMapper {
    ClusterCreationResponseDto to(Cluster source);

    @Mapping(target = "title", source = "clusterTitle")
    Cluster from(ClusterCreationRequestDto source);
}
