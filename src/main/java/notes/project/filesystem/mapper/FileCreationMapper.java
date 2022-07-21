package notes.project.filesystem.mapper;

import notes.project.filesystem.dto.AddFileRequestDto;
import notes.project.filesystem.dto.AddFileResponseDto;
import notes.project.filesystem.model.CreatedFile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FileCreationMapper  {
    AddFileResponseDto to(CreatedFile source);
}
