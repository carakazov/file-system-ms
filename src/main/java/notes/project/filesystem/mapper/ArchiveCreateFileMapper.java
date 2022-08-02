package notes.project.filesystem.mapper;

import notes.project.filesystem.dto.ArchiveCreatedFileRequestDto;
import notes.project.filesystem.model.CreatedFile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ArchiveCreateFileMapper {
    @Mapping(target = "fileTitle", source = "title")
    @Mapping(target = "fileExternalId", source = "externalId")
    ArchiveCreatedFileRequestDto to(CreatedFile source);
}
