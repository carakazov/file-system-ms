package notes.project.filesystem.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import notes.project.filesystem.config.ApplicationProperties;
import org.apache.commons.io.IOUtils;
import org.mapstruct.factory.Mappers;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.ReflectionUtils;

@Slf4j
@UtilityClass
public class TestUtils {
    public static String getClasspathResource(String path, Charset charset) throws IOException {
        return IOUtils.toString(new ClassPathResource(path).getInputStream(), charset);
    }

    public static String getClasspathResource(String path) throws IOException {
        return getClasspathResource(path, StandardCharsets.UTF_8);
    }

    public static <T> T getComplexMapper(Class<T> mapperClass) {
        if(!mapperClass.getSimpleName().contains("Mapper")) {
            return null;
        }

        T mapper = Mappers.getMapper(mapperClass);

        ReflectionUtils.doWithFields(mapper.getClass(), field -> {
            if(ReflectionTestUtils.getField(mapper, field.getName()) == null) {
                try {
                    Class<?> fieldType = field.getType();

                    if(fieldType == ApplicationProperties.class) {
                        ReflectionTestUtils.setField(mapper, field.getName(), ApplicationPropertiesUtils.applicationProperties());
                        return;
                    }

                    Object fieldValue = getComplexMapper(fieldType);
                    if(fieldValue != null) {
                        ReflectionTestUtils.setField(mapper, field.getName(), fieldValue);
                    }
                } catch(RuntimeException e) {
                    log.error("Unable to find instance for field {} of mapper {}", field.getName(), mapper.getClass(), e);
                }
            }
        });

        return mapper;
    }

}
