package notes.project.filesystem.validation.impl;

import notes.project.filesystem.dto.UpdateFileRequestDto;
import notes.project.filesystem.exception.ExceptionCode;
import notes.project.filesystem.utils.ValidationHelper;
import notes.project.filesystem.validation.Validator;
import org.springframework.stereotype.Component;

@Component
public class UpdateFileValidator implements Validator<UpdateFileRequestDto> {
    @Override
    public void validate(UpdateFileRequestDto source) {
        ValidationHelper.getInstance()
            .validate(
                source.getContent(),
                item -> item.length() > 0 && item.length() <= 3064,
                ExceptionCode.INCORRECT_CONTENT_LENGTH
            ).throwIfNotValid();
    }
}
