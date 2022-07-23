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
    private final CreatedFileRepository createdFileRepository;
    private final FileManager fileManager;

    @PostMapping
    public ClusterCreationResponseDto createCluster(@RequestBody ClusterCreationRequestDto request) {return clusterService.createCluster(request);
    }

    @GetMapping("/zipTest")
    public void zipTest() throws Exception {
        Path pathToZip = Path.of("root/test-zip/test.zip");
        Path pathToFile = Path.of("root/test-zip/test.txt");
        Files.createFile(pathToZip);
        ZipEntry entry = new ZipEntry("zipped.txt");
        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream("root/test-zip/test.zip"));
        FileInputStream fileInputStream = new FileInputStream("root/test-zip/test.txt");
        byte[] content = fileInputStream.readAllBytes();
        fileInputStream.close();
        zipOutputStream.putNextEntry(entry);
        zipOutputStream.write(content, 0, content.length);
        zipOutputStream.closeEntry();
        zipOutputStream.close();

        Files.deleteIfExists(pathToFile);
    }

    @GetMapping("/unzipTest")
    public void unzipTest() throws Exception {
        ZipFile zipFile = new ZipFile("root/test-zip/test.zip");
        String filePath = "root/test-zip/";
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while(entries.hasMoreElements()) {
            ZipEntry zipEntry = entries.nextElement();
            if(!zipEntry.isDirectory()) {
                filePath = filePath + zipEntry.getName();
                Files.createFile(Path.of(filePath));
                InputStream inputStream = zipFile.getInputStream(zipEntry);
                byte[] zippedContent = inputStream.readAllBytes();
                FileOutputStream fileOutputStream = new FileOutputStream(filePath);
                fileOutputStream.write(zippedContent);
                inputStream.close();
                fileOutputStream.close();
            }
        }
        zipFile.close();
        Files.deleteIfExists(Path.of("root/test-zip/test.zip"));
    }

    @GetMapping("/{id}")
    public String readFile(@PathVariable Long id) {
        CreatedFile file = createdFileRepository.findById(id).get();
        return fileManager.readFile(file);
    }

}
