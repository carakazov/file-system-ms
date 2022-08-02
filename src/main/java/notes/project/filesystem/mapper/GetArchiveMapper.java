package notes.project.filesystem.mapper;

import java.util.List;

import notes.project.filesystem.dto.ArchiveDto;
import notes.project.filesystem.model.Archive;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetArchiveMapper {
    ArchiveDto to(Archive source);

    List<ArchiveDto> to(List<Archive> source);
}
