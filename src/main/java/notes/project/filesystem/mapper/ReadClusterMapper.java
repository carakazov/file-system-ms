package notes.project.filesystem.mapper;

import notes.project.filesystem.dto.ReadClusterDto;
import notes.project.filesystem.model.Cluster;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = DirectoryInfoMapper.class)
public interface ReadClusterMapper {
    @Mapping(target = "creationDate", source = "createDate")
    @Mapping(target = "directories", source = "directories")
    ReadClusterDto to(Cluster source);
}
