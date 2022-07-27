package notes.project.filesystem.mapper;

import java.util.List;

import notes.project.filesystem.dto.DirectoryInfoDto;
import notes.project.filesystem.model.Directory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DirectoryInfoMapper {
    @Mapping(target = "creationDate", source = "createDate")
    DirectoryInfoDto to(Directory source);

    List<DirectoryInfoDto> to(List<Directory> source);
}
