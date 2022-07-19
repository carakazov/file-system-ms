package notes.project.filesystem.mapper;

import notes.project.filesystem.dto.DirectoryCreationRequestDto;
import notes.project.filesystem.dto.DirectoryCreationResponseDto;
import notes.project.filesystem.model.Cluster;
import notes.project.filesystem.model.Directory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DirectoryCreationMapper {
    @Mapping(target = "cluster", source = "cluster")
    @Mapping(target = "title", source = "request.directoryName")
    @Mapping(target = "deleted", constant = "false")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "externalId", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    Directory from(DirectoryCreationRequestDto request, Cluster cluster);

    @Mapping(target = "clusterName", source = "source.cluster.title")
    @Mapping(target = "directoryName", source = "source.title")
    @Mapping(target = "creationDate", source = "createDate")
    DirectoryCreationResponseDto to(Directory source);
}
