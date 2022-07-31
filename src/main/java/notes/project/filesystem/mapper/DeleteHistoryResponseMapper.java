package notes.project.filesystem.mapper;

import java.util.List;

import notes.project.filesystem.dto.DeleteHistoryDto;
import notes.project.filesystem.model.DeleteHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface DeleteHistoryResponseMapper {
    @Mapping(target = "eventDate", source = "date")
    @Mapping(target = "event", source = "event")
    DeleteHistoryDto to(DeleteHistory source);

    List<DeleteHistoryDto> to(List<DeleteHistory> source);
}
