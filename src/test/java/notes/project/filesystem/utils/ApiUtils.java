package notes.project.filesystem.utils;

import java.util.Collections;

import lombok.experimental.UtilityClass;
import notes.project.filesystem.dto.*;
import notes.project.filesystem.exception.ExceptionCode;
import notes.project.filesystem.exception.FileSystemException;
import notes.project.filesystem.exception.ResourceNotFoundException;
import notes.project.filesystem.exception.ValidationException;
import notes.project.filesystem.model.EventType;
import notes.project.filesystem.validation.dto.ReplaceCreatedFileValidationDto;

import static notes.project.filesystem.utils.TestDataConstants.*;

@UtilityClass
public class ApiUtils {
    public static ClusterCreationRequestDto clusterCreationRequestDto() {
        return new ClusterCreationRequestDto().setClusterTitle(CLUSTER_TITLE);
    }

    public static ClusterCreationResponseDto clusterCreationResponseDto() {
        return new ClusterCreationResponseDto()
                .setTitle(CLUSTER_TITLE)
                .setCreateDate(CREATED_CLUSTER_DATE)
                .setExternalId(CLUSTER_EXTERNAL_ID);
    }

    public static DirectoryCreationResponseDto directoryCreationResponseDto() {
        return new DirectoryCreationResponseDto()
            .setDirectoryName(DIRECTORY_TITLE)
            .setClusterName(CLUSTER_TITLE)
            .setExternalId(DIRECTORY_EXTERNAL_ID)
            .setCreationDate(CREATED_DIRECTORY_TIME);
    }

    public static DirectoryCreationRequestDto directoryCreationRequestDto() {
        return new DirectoryCreationRequestDto()
            .setDirectoryName(DIRECTORY_TITLE)
            .setClusterExternalId(CLUSTER_EXTERNAL_ID);
    }

    public static AddFileRequestDto addFileRequestDto() {
        return new AddFileRequestDto()
            .setDirectoryExternalId(DIRECTORY_EXTERNAL_ID)
            .setTitle(DIRECTORY_TITLE)
            .setContent(FILE_CONTENT);
    }

    public static AddFileResponseDto addFileResponseDto() {
        return new AddFileResponseDto()
            .setTitle(FILE_TITLE)
            .setExternalId(FILE_EXTERNAL_ID)
            .setCreatedDate(CREATED_FILE_CREATED_DATE);
    }

    public static ReadCreatedFileDto readCreatedFileDto() {
        return new ReadCreatedFileDto()
            .setContent(FILE_CONTENT)
            .setTitle(FILE_TITLE)
            .setCreationDate(CREATED_FILE_CREATED_DATE);
    }

    public static ReadDirectoryDto readDirectoryDto() {
        return new ReadDirectoryDto()
            .setTitle(DIRECTORY_TITLE)
            .setExternalId(DIRECTORY_EXTERNAL_ID)
            .setCreationDate(CREATED_DIRECTORY_TIME)
            .setFiles(Collections.singletonList(fileInfoDto()));
    }

    public static FileInfoDto fileInfoDto() {
        return new FileInfoDto()
            .setTitle(FILE_TITLE)
            .setExternalId(FILE_EXTERNAL_ID)
            .setCreationDate(CREATED_FILE_CREATED_DATE);
    }

    public static MoveCreatedFileResponseDto moveCreatedFileResponseDto() {
        return new MoveCreatedFileResponseDto()
            .setCreatedFileExternalId(FILE_EXTERNAL_ID)
            .setNewDirectoryExternalId(ALTERNATIVE_DIRECTORY_EXTERNAL_ID)
            .setReplacingDate(REPLACING_DATE);
    }

    public static MoveCreatedFileRequestDto moveCreatedFileRequestDto() {
        return new MoveCreatedFileRequestDto()
            .setCreatedFileExternalId(FILE_EXTERNAL_ID)
            .setNewDirectoryExternalId(ALTERNATIVE_DIRECTORY_EXTERNAL_ID);
    }

    public static UpdateFileRequestDto updateFileRequestDto() {
        return new UpdateFileRequestDto()
            .setContent(NEW_FILE_CONTENT);
    }

    public static DeleteHistoryResponseDto deleteHistoryClusterResponseDto() {
        return new DeleteHistoryResponseDto()
            .setObjectTitle(CLUSTER_TITLE)
            .setCreatedDate(CREATED_CLUSTER_DATE)
            .setCurrentState(EventType.DELETED)
            .setHistory(Collections.singletonList(deleteHistoryDto()));
    }

    public static DeleteHistoryResponseDto deleteHistoryDirectoryResponseDto() {
        return new DeleteHistoryResponseDto()
            .setObjectTitle(DIRECTORY_TITLE)
            .setCreatedDate(CREATED_DIRECTORY_TIME)
            .setCurrentState(EventType.DELETED)
            .setHistory(Collections.singletonList(deleteHistoryDto()));
    }

    public static DeleteHistoryResponseDto deleteHistoryCreatedFileResponseDto() {
        return new DeleteHistoryResponseDto()
            .setObjectTitle(FILE_TITLE)
            .setCreatedDate(CREATED_FILE_CREATED_DATE)
            .setCurrentState(LAST_EVENT)
            .setHistory(Collections.singletonList(deleteHistoryDto()));
    }

    public static DeleteHistoryDto deleteHistoryDto() {
        return new DeleteHistoryDto()
            .setEvent(LAST_EVENT)
            .setEventDate(DELETE_DATE);
    }

    public static ReplacingHistoryResponseDto replacingHistoryResponseDto() {
        return new ReplacingHistoryResponseDto()
            .setFile(replacingHistoryCreatedFileDto())
            .setHistory(Collections.singletonList(getReplacingHistoryDto()));
    }

    public static ReplacingHistoryCreatedFileDto replacingHistoryCreatedFileDto() {
        return new ReplacingHistoryCreatedFileDto()
            .setFileTitle(FILE_TITLE)
            .setFileExternalId(FILE_EXTERNAL_ID);
    }

    public static ReplacingHistoryDto getReplacingHistoryDto() {
        return new ReplacingHistoryDto()
            .setSourceDirectory(getReplacingHistorySourceDirectoryDto())
            .setTargetDirectory(getReplacingHistoryTargetDirectoryDto())
            .setReplacingDate(REPLACING_DATE);
    }

    public static ReplacingHistoryDirectoryDto getReplacingHistoryTargetDirectoryDto() {
        return new ReplacingHistoryDirectoryDto()
            .setDirectoryTitle(DIRECTORY_TITLE)
            .setDirectoryExternalId(ALTERNATIVE_DIRECTORY_EXTERNAL_ID);
    }

    public static ReplacingHistoryDirectoryDto getReplacingHistorySourceDirectoryDto() {
        return new ReplacingHistoryDirectoryDto()
            .setDirectoryTitle(DIRECTORY_TITLE)
            .setDirectoryExternalId(DIRECTORY_EXTERNAL_ID);
    }

    public static ReplaceCreatedFileValidationDto replaceCreatedFileValidationDto() {
        return new ReplaceCreatedFileValidationDto()
            .setDirectory(DbUtils.directoryWithAlternativeExternalId())
            .setFile(DbUtils.createdFile());
    }

    public static ArchiveHistoryResponseDto archiveHistoryResponseDto() {
        return new ArchiveHistoryResponseDto()
            .setFile(archiveCreatedFileRequestDto())
            .setHistory(Collections.singletonList(archiveDto()));
    }

    public static ArchiveDto archiveDto() {
        return new ArchiveDto()
            .setVersionFileGuid(FILE_VERSION_UUID)
            .setEditedDate(EDITED_DATE);
    }

    public static ArchiveCreatedFileRequestDto archiveCreatedFileRequestDto() {
        return new ArchiveCreatedFileRequestDto()
            .setFileTitle(FILE_TITLE)
            .setFileExternalId(FILE_EXTERNAL_ID);
    }

    public static ErrorDto errorDto() {
        return new ErrorDto()
                .setCode(EXCEPTION_CODE)
                .setMessage(EXCEPTION_MESSAGE);
    }

    public static ValidationErrorDto validationErrorDto() {
        return new ValidationErrorDto(Collections.singletonList(errorDto()));
    }

    public static FileSystemException fileSystemException() {
        return new FileSystemException(ExceptionCode.CREATION_ERROR, "source message");
    }

    public static ResourceNotFoundException resourceNotFoundException() {
        return new ResourceNotFoundException(ExceptionCode.CREATION_ERROR);
    }

    public static ValidationException validationException() {
        ValidationException validationException = new ValidationException();
        validationException.addCode(ExceptionCode.CREATION_ERROR);
        return validationException;
    }
}
