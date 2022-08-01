package notes.project.filesystem.mapper;

import java.util.List;

import notes.project.filesystem.dto.DeleteHistoryResponseDto;
import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.model.DeleteHistory;
import notes.project.filesystem.model.EventType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = DeleteHistoryMapper.class)
public interface DeleteHistoryResponseMapper {

    @Mapping(target = "objectTitle", source = "file.title")
    @Mapping(target = "createdDate", source = "file.createdDate")
    @Mapping(target = "currentState", source = "currentState")
    @Mapping(target = "history", source = "history")
    DeleteHistoryResponseDto to(CreatedFile file, List<DeleteHistory> history, EventType currentState);
}
