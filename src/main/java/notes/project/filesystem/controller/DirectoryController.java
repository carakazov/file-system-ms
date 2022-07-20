package notes.project.filesystem.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "Контроллер по управлению директориями")
public class DirectoryController {
    private final DirectoryService directoryService;

    @PostMapping
    @ApiOperation(value = "/directory", notes = "Метод по созданию директории")
    public DirectoryCreationResponseDto createDirectory(@RequestBody DirectoryCreationRequestDto request) {
        return  directoryService.createDirectory(request);
    }
}
