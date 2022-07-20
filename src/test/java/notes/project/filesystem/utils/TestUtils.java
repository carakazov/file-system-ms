package notes.project.filesystem.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

@Slf4j
@UtilityClass
public class TestUtils {
    public static String getClasspathResource(String path, Charset charset) throws IOException {
        return IOUtils.toString(new ClassPathResource(path).getInputStream(), charset);
    }

    public static String getClasspathResource(String path) throws IOException {
        return getClasspathResource(path, StandardCharsets.UTF_8);
    }

}
