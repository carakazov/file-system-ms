package notes.project.filesystem.validation.impl;

import notes.project.filesystem.exception.ExceptionCode;
import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.utils.ValidationHelper;
import notes.project.filesystem.validation.Validator;
import org.springframework.stereotype.Component;

@Component
public class DeleteFileValidator implements Validator<CreatedFile> {
    @Override
    public void validate(CreatedFile source) {
        ValidationHelper.getInstance().validate(
            source.getDeleted(),
            Boolean.FALSE::equals,
            ExceptionCode.FILE_ALREADY_DELETED
        ).throwIfNotValid();
    }
}
