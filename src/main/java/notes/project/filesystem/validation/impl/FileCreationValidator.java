package notes.project.filesystem.validation.impl;

import liquibase.util.Validate;
import notes.project.filesystem.dto.AddFileRequestDto;
import notes.project.filesystem.exception.ExceptionCode;
import notes.project.filesystem.utils.ValidationHelper;
import notes.project.filesystem.validation.Validator;
import org.springframework.stereotype.Component;

@Component
public class FileCreationValidator implements Validator<AddFileRequestDto> {

    @Override
    public void validate(AddFileRequestDto source) {
        ValidationHelper.getInstance()
            .validate(
                source.getTitle(),
                item -> item.length() >= 1 && item.length() <= 125,
                ExceptionCode.INCORRECT_TITLE_LENGTH
            ).validate(
                source.getContent(),
                item -> item.length() >= 1 && item.length() <= 3064,
                ExceptionCode.INCORRECT_CONTENT_LENGTH
            ).throwIfNotValid();
    }
}
