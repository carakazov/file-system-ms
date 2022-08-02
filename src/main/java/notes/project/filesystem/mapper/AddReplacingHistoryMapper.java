package notes.project.filesystem.mapper;

import notes.project.filesystem.dto.MoveCreatedFileResponseDto;
import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.model.Directory;
import notes.project.filesystem.model.ReplacingHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddReplacingHistoryMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "targetDirectory", source = "newDirectory")
    @Mapping(target = "createdFile", source = "file")
    @Mapping(target = "sourceDirectory", source = "file.directory")
    ReplacingHistory to(CreatedFile file, Directory newDirectory);

    @Mapping(target = "createdFileExternalId", source = "createdFile.externalId")
    @Mapping(target = "newDirectoryExternalId", source = "targetDirectory.externalId")
    MoveCreatedFileResponseDto from(ReplacingHistory source);
}
