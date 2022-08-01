package notes.project.filesystem.mapper;

import java.util.List;

import notes.project.filesystem.dto.DeleteHistoryResponseDto;
import notes.project.filesystem.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = DeleteHistoryMapper.class)
public interface DeleteHistoryResponseMapper {

    @Mapping(target = "objectTitle", source = "file.title")
    @Mapping(target = "createdDate", source = "file.createdDate")
    @Mapping(target = "currentState", source = "currentState")
    @Mapping(target = "history", source = "history")
    DeleteHistoryResponseDto to(CreatedFile file, List<DeleteHistory> history, EventType currentState);

    @Mapping(target = "objectTitle", source = "directory.title")
    @Mapping(target = "createdDate", source = "directory.createDate")
    @Mapping(target = "currentState", source = "currentState")
    @Mapping(target = "history", source = "history")
    DeleteHistoryResponseDto to(Directory directory, List<DeleteHistory> history, EventType currentState);

    @Mapping(target = "objectTitle", source = "cluster.title")
    @Mapping(target = "createdDate", source = "cluster.createDate")
    @Mapping(target = "currentState", source = "currentState")
    @Mapping(target = "history", source = "history")
    DeleteHistoryResponseDto to(Cluster cluster, List<DeleteHistory> history, EventType currentState);
}
