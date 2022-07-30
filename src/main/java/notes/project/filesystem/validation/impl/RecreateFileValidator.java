package notes.project.filesystem.validation.impl;

import lombok.RequiredArgsConstructor;
import notes.project.filesystem.config.ApplicationProperties;
import notes.project.filesystem.exception.ExceptionCode;
import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.utils.ValidationHelper;
import notes.project.filesystem.validation.Validator;
import org.springframework.stereotype.Component;

@Component
public class RecreateFileValidator implements Validator<CreatedFile> {

    @Override
    public void validate(CreatedFile source) {
        ValidationHelper.getInstance()
            .validate(
                source.getDeleted(),
                Boolean.TRUE::equals,
                ExceptionCode.FILE_NOT_DELETED
            ).throwIfNotValid();
    }
}
