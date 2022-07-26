package notes.project.filesystem.controller;

import java.util.UUID;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import notes.project.filesystem.dto.AddFileRequestDto;
import notes.project.filesystem.dto.AddFileResponseDto;
import notes.project.filesystem.dto.ReadCreatedFileDto;
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
}
