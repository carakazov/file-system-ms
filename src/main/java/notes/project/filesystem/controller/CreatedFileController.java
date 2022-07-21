package notes.project.filesystem.controller;

import lombok.RequiredArgsConstructor;
import notes.project.filesystem.dto.AddFileRequestDto;
import notes.project.filesystem.dto.AddFileResponseDto;
import notes.project.filesystem.service.CreatedFileService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class CreatedFileController {
    private final CreatedFileService createdFileService;

    @PostMapping
    public AddFileResponseDto createFinal(@RequestBody AddFileRequestDto request) {
        return createdFileService.addFile(request);
    }
}
