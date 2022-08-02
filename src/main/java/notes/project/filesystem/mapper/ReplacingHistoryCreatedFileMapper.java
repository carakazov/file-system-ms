package notes.project.filesystem.mapper;

import notes.project.filesystem.dto.ReplacingHistoryCreatedFileDto;
import notes.project.filesystem.model.CreatedFile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReplacingHistoryCreatedFileMapper {

    @Mapping(target = "fileTitle", source = "title")
    @Mapping(target = "fileExternalId", source = "externalId")
    ReplacingHistoryCreatedFileDto to(CreatedFile source);
}
