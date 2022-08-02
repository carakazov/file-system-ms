package notes.project.filesystem.mapper;

import notes.project.filesystem.dto.ReplacingHistoryDirectoryDto;
import notes.project.filesystem.model.Directory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReplacingHistoryDirectoryMapper {
    @Mapping(target = "directoryTitle", source = "title")
    @Mapping(target = "directoryExternalId", source = "externalId")
    ReplacingHistoryDirectoryDto to(Directory directory);
}
