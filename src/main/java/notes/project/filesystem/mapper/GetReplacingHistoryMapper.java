package notes.project.filesystem.mapper;

import java.util.List;

import notes.project.filesystem.dto.ReplacingHistoryDto;
import notes.project.filesystem.model.ReplacingHistory;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", uses = ReplacingHistoryDirectoryMapper.class)
public interface GetReplacingHistoryMapper {
    ReplacingHistoryDto to(ReplacingHistory source);

    List<ReplacingHistoryDto> to(List<ReplacingHistory> source);
}
