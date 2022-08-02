package notes.project.filesystem.controller;

import java.util.UUID;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import notes.project.filesystem.dto.*;
import notes.project.filesystem.service.CreatedFileService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class CreatedFileController {
    private final CreatedFileService createdFileService;

    @PostMapping
    public AddFileResponseDto createFinal(@RequestBody @Validated AddFileRequestDto request) {
        return createdFileService.addFile(request);
    }

    @DeleteMapping("/{id}")
    public void deleteFile(@PathVariable(name = "id") UUID externalId) {
        createdFileService.deleteCreatedFile(externalId);
    }

    @GetMapping("/{id}")
    public ReadCreatedFileDto readFile(@PathVariable(name = "id") UUID externalId) {
        return createdFileService.readFile(externalId);
    }

    @PutMapping
    public MoveCreatedFileResponseDto moveFile(@RequestBody MoveCreatedFileRequestDto request) {
        return createdFileService.moveFile(request);
    }

    @PutMapping("/{id}")
    public void updateFile(@PathVariable(name = "id") UUID externalId, @RequestBody UpdateFileRequestDto request) {
        createdFileService.updateFile(externalId, request);
    }

    @GetMapping("/{id}/deleteHistory")
    @ApiOperation(value = "Запрос истории удалений и восстановлений файла")
    public DeleteHistoryResponseDto getDeleteHistory(@PathVariable(name = "id") UUID externalId) {
        return createdFileService.getFileDeleteHistory(externalId);
    }

    @GetMapping("/{id}/replacingHistory")
    public ReplacingHistoryResponseDto getReplaсingHistory(@PathVariable(name = "id") UUID externalId) {
        return createdFileService.getReplacingHistory(externalId);
    }
}
