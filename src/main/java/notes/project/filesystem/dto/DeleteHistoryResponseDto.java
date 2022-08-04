package notes.project.filesystem.dto;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import notes.project.filesystem.model.EventType;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Ответ на запрос истории удаления объекта")
public class DeleteHistoryResponseDto {
    @ApiModelProperty(value = "Название объекта")
    private String objectTitle;
    @ApiModelProperty(value = "Дата создания объекта")
    private LocalDateTime createdDate;
    @ApiModelProperty(value = "Текущее состояние объекта")
    private EventType currentState;
    @ApiModelProperty(value = "Список операций удаления и восстановления")
    private List<DeleteHistoryDto> history;
}
