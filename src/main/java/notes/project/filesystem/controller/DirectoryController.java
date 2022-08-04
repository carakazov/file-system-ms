package notes.project.filesystem.controller;

import java.util.UUID;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import notes.project.filesystem.dto.DeleteHistoryResponseDto;
import notes.project.filesystem.dto.DirectoryCreationRequestDto;
import notes.project.filesystem.dto.DirectoryCreationResponseDto;
import notes.project.filesystem.dto.ReadDirectoryDto;
import notes.project.filesystem.service.DirectoryService;
import org.springframework.validation.annotation.Validated;
import notes.project.filesystem.service.logic.DirectoryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/directory")
@Api(value = "Контроллер по управлению директориями")
public class DirectoryController {
    private final DirectoryService directoryService;

    @PostMapping
    @ApiOperation(value = "Метод по созданию директории")
    public DirectoryCreationResponseDto createDirectory(@RequestBody @Validated DirectoryCreationRequestDto request) {
        return  directoryService.createDirectory(request);
    }

    @DeleteMapping("/{externalId}")
    @ApiOperation(value = "Метод по удалению директории")
    public void deleteDirectory(@PathVariable(name = "externalId") @ApiParam(name = "Внешний ID директории") UUID externalId) {
        directoryService.deleteDirectory(externalId);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Метод запроса информации о директории и её содержимом")
    public ReadDirectoryDto readDirectory(@PathVariable(name = "id") @ApiParam(value = "Внешний ID директории") UUID externalId) {
        return directoryService.readDirectory(externalId);
    }

    @GetMapping("/{id}/deleteHistory")
    @ApiOperation(value = "Запрос истории удалений и восстановлений файлов")
    public DeleteHistoryResponseDto getDeleteHistory(@PathVariable(name = "id") @ApiParam(value = "Внешний ID директории") UUID externalId) {
        return directoryService.getDirectoryDeleteHistory(externalId);
    }
}
