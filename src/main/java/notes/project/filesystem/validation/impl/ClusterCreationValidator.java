package notes.project.filesystem.validation.impl;

import notes.project.filesystem.dto.ClusterCreationRequestDto;
import notes.project.filesystem.exception.ExceptionCode;
import notes.project.filesystem.utils.ValidationHelper;
import notes.project.filesystem.validation.Validator;
import org.springframework.stereotype.Component;

@Component
public class ClusterCreationValidator implements Validator<ClusterCreationRequestDto> {
    @Override
    public void validate(ClusterCreationRequestDto source) {
        ValidationHelper.getInstance()
            .validate(
                source.getClusterTitle(),
                item -> item.length() >= 1 && item.length() <= 125,
                ExceptionCode.INCORRECT_TITLE_LENGTH
            ).throwIfNotValid();
    }
}
