package notes.project.filesystem.controller;

import java.util.UUID;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import notes.project.filesystem.dto.DirectoryCreationRequestDto;
import notes.project.filesystem.dto.DirectoryCreationResponseDto;
import notes.project.filesystem.service.DirectoryService;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/{externalId}")
    @ApiOperation(value = "/{directory/{externalId}}", notes = "Метод по удалению директории")
    public void deleteDirectory(@PathVariable(name = "externalId") @ApiParam(name = "Внешний ID директории") UUID externalId) {
        directoryService.deleteDirectory(externalId);
    }
}
