package notes.project.filesystem.validation.impl;



import notes.project.filesystem.exception.ExceptionCode;
import notes.project.filesystem.utils.ValidationHelper;
import notes.project.filesystem.validation.Validator;
import notes.project.filesystem.validation.dto.DirectoryCreationValidationDto;
import org.springframework.stereotype.Component;

@Component
public class DirectoryCreationValidator implements Validator<DirectoryCreationValidationDto> {
    @Override
    public void validate(DirectoryCreationValidationDto source) {
        ValidationHelper.getInstance()
            .validate(
                source.getClusterExists(),
                Boolean.TRUE::equals,
                ExceptionCode.CLUSTER_DOES_NOT_EXISTS
            )
            .validate(
                source.getDirectoryExists(),
                Boolean.FALSE::equals,
                ExceptionCode.DIRECTORY_EXISTS
            ).throwIfNotValid();
    }
}
