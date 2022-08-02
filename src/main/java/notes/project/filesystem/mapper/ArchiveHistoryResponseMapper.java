package notes.project.filesystem.mapper;

import java.util.List;

import notes.project.filesystem.dto.ArchiveHistoryResponseDto;
import notes.project.filesystem.model.Archive;
import notes.project.filesystem.model.CreatedFile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
    ArchiveCreateFileMapper.class,
    GetArchiveMapper.class
})
public interface ArchiveHistoryResponseMapper {
    @Mapping(target = "file", source = "file")
    @Mapping(target = "history", source = "history")
    ArchiveHistoryResponseDto to(CreatedFile file, List<Archive> history);
}
