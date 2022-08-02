package notes.project.filesystem.mapper;

import java.util.List;

import notes.project.filesystem.dto.ReplacingHistoryResponseDto;
import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.model.ReplacingHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
    GetReplacingHistoryMapper.class,
    ReplacingHistoryCreatedFileMapper.class
})
public interface ReplacingHistoryResponseMapper {
    @Mapping(target = "file", source = "file")
    @Mapping(target = "history", source = "history")
    ReplacingHistoryResponseDto to(CreatedFile file, List<ReplacingHistory> history);
}
