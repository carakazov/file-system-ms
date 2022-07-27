package notes.project.filesystem.validation.impl;

import notes.project.filesystem.exception.ExceptionCode;
import notes.project.filesystem.exception.ResourceNotFoundException;
import notes.project.filesystem.model.Directory;
import notes.project.filesystem.validation.BusinessValidator;
import org.springframework.stereotype.Component;

@Component
public class ReadDirectoryValidator implements BusinessValidator<Directory> {
    @Override
    public void validate(Directory source) {
        if(Boolean.TRUE.equals(source.getDeleted())) {
            throw new ResourceNotFoundException(ExceptionCode.DIRECTORY_DOES_NOT_EXIST);
        }
    }
}
