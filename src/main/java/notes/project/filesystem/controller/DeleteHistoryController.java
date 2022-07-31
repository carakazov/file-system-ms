package notes.project.filesystem.controller;

import java.util.UUID;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import notes.project.filesystem.dto.DeleteHistoryResponseDto;
import notes.project.filesystem.service.DeleteHistoryFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deleteHistory")
@RequiredArgsConstructor
@Api(value = "Контроллер для получения истории удаления и восстановления объектов")
public class DeleteHistoryController {
    private final DeleteHistoryFacade deleteHistoryFacade;

    @GetMapping("/file/{id}")
    public DeleteHistoryResponseDto getCreatedFileHistory(@PathVariable(name = "id")UUID fileExternalId) {
        return deleteHistoryFacade.getCreatedFileHistory(fileExternalId);
    }
}
