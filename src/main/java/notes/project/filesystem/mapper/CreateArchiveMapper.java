package notes.project.filesystem.mapper;

import java.util.UUID;

import notes.project.filesystem.model.Archive;
import notes.project.filesystem.model.CreatedFile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CreateArchiveMapper {
    @Mapping(target = "versionFileGuid", source = "versionFileExternalId")
    @Mapping(target = "createdFile", source = "file")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "editedDate", ignore = true)
    Archive to(CreatedFile file, UUID versionFileExternalId);
}
