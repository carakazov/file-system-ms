package notes.project.filesystem.validation.impl;

import notes.project.filesystem.exception.ExceptionCode;
import notes.project.filesystem.exception.ResourceNotFoundException;
import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.validation.BusinessValidator;
import notes.project.filesystem.validation.Validator;
import org.springframework.stereotype.Component;

@Component
public class ReadFileValidator implements BusinessValidator<CreatedFile> {
    @Override
    public void validate(CreatedFile target) {
        if(Boolean.TRUE.equals(target.getDeleted())) {
            throw new ResourceNotFoundException(ExceptionCode.RESOURCE_NOT_FOUND);
        }
    }
}
