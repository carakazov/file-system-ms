package notes.project.filesystem.validation.impl;

import notes.project.filesystem.exception.ExceptionCode;
import notes.project.filesystem.model.Directory;
import notes.project.filesystem.utils.ValidationHelper;
import notes.project.filesystem.validation.Validator;
import org.springframework.stereotype.Component;

@Component
public class DeleteDirectoryValidator implements Validator<Directory> {
    @Override
    public void validate(Directory source) {
        ValidationHelper.getInstance().validate(
            source.getDeleted(),
            Boolean.FALSE::equals,
            ExceptionCode.DIRECTORY_ALREADY_DELETED
        ).throwIfNotValid();
    }
}
