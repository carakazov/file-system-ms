package notes.project.filesystem.validation.impl;

import java.util.Collections;
import java.util.Map;

import notes.project.filesystem.exception.ExceptionCode;
import notes.project.filesystem.utils.ValidationHelper;
import notes.project.filesystem.validation.Validator;
import notes.project.filesystem.validation.dto.CreateClusterValidationDto;
import org.springframework.stereotype.Component;

@Component
public class CreateClusterValidator implements Validator<CreateClusterValidationDto> {
    @Override
    public void validate(CreateClusterValidationDto source) {
        ValidationHelper.getInstance()
                .validate(
                        source.getClusterExists(),
                        Boolean.FALSE::equals,
                        ExceptionCode.CLUSTER_WITH_SUCH_NAME_ALREADY_EXISTS
                ).throwIfNotValid();
    }
}
