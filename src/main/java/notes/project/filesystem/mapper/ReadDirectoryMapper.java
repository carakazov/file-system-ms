package notes.project.filesystem.mapper;

import java.util.List;

import notes.project.filesystem.dto.ReadDirectoryDto;
import notes.project.filesystem.model.Directory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = FileInfoMapper.class)
public interface ReadDirectoryMapper {
    @Mapping(target = "files", source = "source.createdFiles")
    @Mapping(target = "creationDate", source = "createDate")
    ReadDirectoryDto to(Directory source);

    List<ReadDirectoryDto> to(List<Directory> source);
}
