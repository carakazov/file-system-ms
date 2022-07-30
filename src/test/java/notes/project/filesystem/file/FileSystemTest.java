package notes.project.filesystem.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import notes.project.filesystem.model.Directory;
import notes.project.filesystem.model.FileResolution;
import org.apache.tomcat.util.http.fileupload.FileUtils;

import static notes.project.filesystem.utils.TestDataConstants.*;
import static notes.project.filesystem.utils.TestDataConstants.ROOT_DIRECTORY_PATH;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class FileSystemTest {
    protected void createFileWithContent() throws IOException {
        Files.createDirectories(RESOLVED_PATH_FOR_CREATE_DIRECTORY);
        Files.createFile(RESOLVED_PATH_FOR_CREATE_FILE);

        try(FileOutputStream fileOutputStream = new FileOutputStream(RESOLVED_PATH_FOR_CREATE_FILE.toString())) {
            byte[] content = FILE_CONTENT.getBytes(StandardCharsets.UTF_8);
            fileOutputStream.write(content);
        }
    }

    protected void createZipFile(Path path) throws IOException {
        Files.createFile(path);
        try(ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(path.toString()))) {
            ZipEntry zipEntry = new ZipEntry(FILE_EXTERNAL_ID_STRING + FileResolution.TXT.getResolution());
            zipOutputStream.putNextEntry(zipEntry);
            zipOutputStream.write(FILE_CONTENT.getBytes(StandardCharsets.UTF_8));
            zipOutputStream.closeEntry();
        }
    }

    protected void createClusterPath(Path path) throws IOException {
        Files.createDirectories(path);
    }

    protected void assertFileCreated(Path path) throws IOException {
        assertTrue(Files.exists(path));
        FileUtils.deleteDirectory(new File(ROOT_DIRECTORY_PATH));
    }

    protected void assertFileDeleted(Path path) throws IOException {
        assertFalse(Files.exists(path));
        FileUtils.deleteDirectory(new File(ROOT_DIRECTORY_PATH));
    }
}
