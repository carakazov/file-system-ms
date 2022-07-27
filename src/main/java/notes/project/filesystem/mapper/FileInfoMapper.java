package notes.project.filesystem.mapper;

import java.util.List;

import notes.project.filesystem.dto.FileInfoDto;
import notes.project.filesystem.model.CreatedFile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FileInfoMapper {
    @Mapping(target = "creationDate", source = "createdDate")
    FileInfoDto to(CreatedFile source);

    List<FileInfoDto> to(List<CreatedFile> source);
}
