package notes.project.filesystem.controller;

import lombok.RequiredArgsConstructor;
import notes.project.filesystem.dto.DirectoryCreationRequestDto;
import notes.project.filesystem.dto.DirectoryCreationResponseDto;
import notes.project.filesystem.service.DirectoryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/directory")
public class DirectoryController {
    private final DirectoryService directoryService;

    @PostMapping
    public DirectoryCreationResponseDto createDirectory(@RequestBody DirectoryCreationRequestDto request) {
        return  directoryService.createDirectory(request);
    }
}
