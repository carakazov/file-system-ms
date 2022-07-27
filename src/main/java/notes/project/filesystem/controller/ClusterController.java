package notes.project.filesystem.controller;



import java.io.*;
import java.lang.reflect.Field;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import notes.project.filesystem.dto.ClusterCreationRequestDto;
import notes.project.filesystem.dto.ClusterCreationResponseDto;
import notes.project.filesystem.dto.ReadClusterDto;
import notes.project.filesystem.file.FileManager;
import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.repository.CreatedFileRepository;
import notes.project.filesystem.service.ClusterService;
import org.apache.ant.compress.taskdefs.Unzip;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.selectors.FilenameSelector;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cluster")
@Api(value = "Контроллер по управлению кластером")
public class ClusterController {
    private final ClusterService clusterService;

    @PostMapping
    @ApiOperation(value = "Создание кластера")
    public ClusterCreationResponseDto createCluster(@RequestBody ClusterCreationRequestDto request) {
        return clusterService.createCluster(request);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Удаление кластера")
    public void deleteCluster(@PathVariable(name = "id") UUID externalId) {
        clusterService.deleteCluster(externalId);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Чтение кластера")
    public ReadClusterDto readCluster(@PathVariable(name = "id") UUID externalId) {
        return clusterService.readCluster(externalId);
    }

}
