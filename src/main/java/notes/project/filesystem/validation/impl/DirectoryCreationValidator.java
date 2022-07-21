package notes.project.filesystem.validation.impl;

import notes.project.filesystem.dto.DirectoryCreationRequestDto;
import notes.project.filesystem.exception.ExceptionCode;
import notes.project.filesystem.utils.ValidationHelper;
import notes.project.filesystem.validation.Validator;
import org.springframework.stereotype.Component;

@Component
public class DirectoryCreationValidator implements Validator<DirectoryCreationRequestDto> {
    @Override
    public void validate(DirectoryCreationRequestDto source) {
        ValidationHelper.getInstance()
            .validate(
                source.getDirectoryName(),
                item -> item.length() >= 1 && item.length() <= 125,
                ExceptionCode.INCORRECT_TITLE_LENGTH
            ).throwIfNotValid();
    }
}
