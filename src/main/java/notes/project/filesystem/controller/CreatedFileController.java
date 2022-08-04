package notes.project.filesystem.controller;

import java.util.UUID;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import notes.project.filesystem.dto.*;
import notes.project.filesystem.service.CreatedFileService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
@Api(value = "Контроллер по управлению файлами")
public class CreatedFileController {
    private final CreatedFileService createdFileService;

    @PostMapping
    @ApiOperation(value = "Создание файла")
    public AddFileResponseDto createFinal(@RequestBody @Validated AddFileRequestDto request) {
        return createdFileService.addFile(request);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Удаление файла")
    public void deleteFile(@PathVariable(name = "id") @ApiParam(value = "Внешний ID файла") UUID externalId) {
        createdFileService.deleteCreatedFile(externalId);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Чтение файла")
    public ReadCreatedFileDto readFile(@PathVariable(name = "id") @ApiParam(value = "Внешний ID файла") UUID externalId) {
        return createdFileService.readFile(externalId);
    }

    @PutMapping
    @ApiOperation(value = "Перемещение файла между директориями")
    public MoveCreatedFileResponseDto moveFile(@RequestBody @Validated MoveCreatedFileRequestDto request) {
        return createdFileService.moveFile(request);
    }

    @PutMapping("/{id}")
    @ApiModelProperty(value = "Обновление файла")
    public void updateFile(@PathVariable(name = "id") @ApiParam(value = "Внешний ID файла") UUID externalId, @RequestBody @Validated UpdateFileRequestDto request) {
        createdFileService.updateFile(externalId, request);
    }

    @GetMapping("/{id}/deleteHistory")
    @ApiOperation(value = "Запрос истории удалений и восстановлений файла")
    public DeleteHistoryResponseDto getDeleteHistory(@PathVariable(name = "id") UUID externalId) {
        return createdFileService.getFileDeleteHistory(externalId);
    }

    @GetMapping("/{id}/replacingHistory")
    @ApiOperation(value = "Запрос спика перемещений файла")
    public ReplacingHistoryResponseDto getReplacingHistory(@PathVariable(name = "id") @ApiParam(value = "Внешний ID файла") UUID externalId) {
        return createdFileService.getReplacingHistory(externalId);
    }

    @GetMapping("/{id}/archiveHistory")
    @ApiOperation(value = "Получение списка обновления файла")
    public ArchiveHistoryResponseDto getArchiveHistory(@PathVariable(name = "id") @ApiParam(value = "Внешний ID файла") UUID externalId) {
        return createdFileService.getArchiveHistory(externalId);
    }

    @GetMapping("/version/{id}")
    @ApiOperation(value = "Прочтение версии файла")
    public ReadFileArchiveVersionDto readFileVersion(@PathVariable(name = "id") @ApiParam(value = "Внешний ID версии файла") UUID versionFileUuid) {
        return createdFileService.readFileVersion(versionFileUuid);
    }
}
