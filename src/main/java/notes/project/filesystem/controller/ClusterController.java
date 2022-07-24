package notes.project.filesystem.controller;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import notes.project.filesystem.dto.ClusterCreationRequestDto;
import notes.project.filesystem.dto.ClusterCreationResponseDto;
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
    public ClusterCreationResponseDto createCluster(@RequestBody ClusterCreationRequestDto request) {
        return clusterService.createCluster(request);
    }


}
