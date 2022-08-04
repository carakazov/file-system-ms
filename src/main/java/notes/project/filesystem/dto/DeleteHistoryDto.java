package notes.project.filesystem.dto;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import notes.project.filesystem.model.EventType;

@Data
@Accessors(chain = true)
@ApiModel(description = "Описание одной записи истории удалений и восстановлений")
public class DeleteHistoryDto {
    @ApiModelProperty(value = "Событие")
    private EventType event;
    @ApiModelProperty(value = "Дата события")
    private LocalDateTime eventDate;
}
