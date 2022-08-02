package notes.project.filesystem.validation.impl;

import notes.project.filesystem.exception.ExceptionCode;
import notes.project.filesystem.utils.ValidationHelper;
import notes.project.filesystem.validation.Validator;
import notes.project.filesystem.validation.dto.ReplaceCreatedFileValidationDto;
import org.springframework.stereotype.Component;

@Component
public class ReplaceCreatedFileValidator implements Validator<ReplaceCreatedFileValidationDto> {
    @Override
    public void validate(ReplaceCreatedFileValidationDto source) {
        ValidationHelper.getInstance()
            .validate(
                source,
                item -> Boolean.FALSE.equals(item.getFile().getDirectory().getExternalId().equals(item.getDirectory().getExternalId())),
                ExceptionCode.SAME_DIRECTORY_REPLACING
            ).throwIfNotValid();
    }
}
