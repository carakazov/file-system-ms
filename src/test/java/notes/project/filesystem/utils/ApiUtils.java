package notes.project.filesystem.utils;

import lombok.experimental.UtilityClass;
import notes.project.filesystem.dto.ClusterCreationRequestDto;
import notes.project.filesystem.dto.ClusterCreationResponseDto;
import notes.project.filesystem.dto.ErrorDto;
import notes.project.filesystem.dto.ValidationErrorDto;
import notes.project.filesystem.exception.ExceptionCode;
import notes.project.filesystem.exception.FileSystemException;
import notes.project.filesystem.exception.ValidationException;

import java.util.Collections;

@UtilityClass
public class ApiUtils {
    public static ClusterCreationRequestDto clusterCreationRequestDto() {
        return new ClusterCreationRequestDto().setClusterTitle(TestDataConstants.CREATE_CLUSTER_TITLE);
    }

    public static ClusterCreationResponseDto clusterCreationResponseDto() {
        return new ClusterCreationResponseDto()
                .setTitle(TestDataConstants.CREATE_CLUSTER_TITLE)
                .setCreateDate(TestDataConstants.CREATED_CLUSTER_DATE)
                .setExternalId(TestDataConstants.CREATED_CLUSTER_EXTERNAL_ID);
    }

    public static ErrorDto errorDto() {
        return new ErrorDto()
                .setCode(TestDataConstants.EXCEPTION_CODE)
                .setMessage(TestDataConstants.EXCEPTION_MESSAGE);
    }

    public static ValidationErrorDto validationErrorDto() {
        return new ValidationErrorDto(Collections.singletonList(errorDto()));
    }

    public static FileSystemException fileSystemException() {
        return new FileSystemException(ExceptionCode.CLUSTER_CREATION_ERROR, "source message");
    }

    public static ValidationException validationException() {
        ValidationException validationException = new ValidationException();
        validationException.addCode(ExceptionCode.CLUSTER_CREATION_ERROR);
        return validationException;
    }
}
