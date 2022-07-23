package notes.project.filesystem.file.impl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import lombok.RequiredArgsConstructor;
import notes.project.filesystem.config.ApplicationProperties;
import notes.project.filesystem.exception.ExceptionCode;
import notes.project.filesystem.exception.FileSystemException;
import notes.project.filesystem.file.FileManager;
import notes.project.filesystem.file.ZipManager;
import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.model.Directory;
import notes.project.filesystem.model.FileResolution;
import notes.project.filesystem.service.CreatedFileService;
import notes.project.filesystem.utils.PathHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ZipManagerImpl implements ZipManager {
    private final ApplicationProperties applicationProperties;
    private final FileManager fileManager;

    @Override
    public synchronized void zipDirectory(Directory directory) {
        String pathToZip = createZipForDirectory(directory);
        try(ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(pathToZip))) {
            for(CreatedFile file : directory.getCreatedFiles()) {
                ZipEntry zipEntry = new ZipEntry(file.getExternalId().toString() + FileResolution.TXT.getResolution());
                zipOutputStream.putNextEntry(zipEntry);
                byte[] content = fileManager.readFile(file).getBytes(StandardCharsets.UTF_8);
                zipOutputStream.write(content);
            }
            fileManager.deleteDirectory(directory);
        } catch(IOException exception) {
            throw new FileSystemException(ExceptionCode.CREATION_ERROR);
        }
    }


    private synchronized String createZipForDirectory(Directory directory)  {
        try {
            Path zipPath = Path.of(applicationProperties.getArchiveRoot() + "/" + directory.getExternalId() + FileResolution.ZIP.getResolution());
            Files.createFile(zipPath);
            return zipPath.toString();
        } catch(IOException exception) {
            throw new FileSystemException(ExceptionCode.CREATION_ERROR, exception.getMessage());
        }
    }
}
