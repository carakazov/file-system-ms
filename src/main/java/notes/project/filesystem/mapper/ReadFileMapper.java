package notes.project.filesystem.mapper;

import notes.project.filesystem.dto.ReadCreatedFileDto;
import notes.project.filesystem.model.CreatedFile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReadFileMapper {
    @Mapping(target = "title", source = "file.title")
    @Mapping(target = "content", source = "content")
    @Mapping(target = "creationDate", source = "file.createdDate")
    ReadCreatedFileDto to(CreatedFile file, String content);
}
