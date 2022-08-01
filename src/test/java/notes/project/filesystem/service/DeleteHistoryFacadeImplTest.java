package notes.project.filesystem.service;

import java.util.Collections;

import notes.project.filesystem.dto.DeleteHistoryResponseDto;
import notes.project.filesystem.model.EventType;
import notes.project.filesystem.service.impl.DeleteHistoryFacadeImpl;
import notes.project.filesystem.utils.ApiUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static notes.project.filesystem.utils.TestDataConstants.FILE_EXTERNAL_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteHistoryFacadeImplTest {
    @Mock
    private CreatedFileService createdFileService;

    private DeleteHistoryFacade facade;

    @BeforeEach
    void init() {
        facade = new DeleteHistoryFacadeImpl(
            createdFileService
        );
    }

    @Test
    void getFileDeleteHistoryWhenHistoryExistsSuccess() {
        DeleteHistoryResponseDto expected = ApiUtils.deleteHistoryCreatedFileResponseDto();

        when(createdFileService.getFileDeleteHistory(any())).thenReturn(expected);

        DeleteHistoryResponseDto actual = facade.getCreatedFileHistory(FILE_EXTERNAL_ID);

        assertEquals(expected, actual);
        verify(createdFileService).getFileDeleteHistory(FILE_EXTERNAL_ID);
    }

    @Test
    void getFileDeleteHistoryWhenHistoryDoesNotExistsSuccess() {
        DeleteHistoryResponseDto expected = ApiUtils.deleteHistoryCreatedFileResponseDto();
        expected.setCurrentState(EventType.CREATED);
        expected.setHistory(Collections.emptyList());

        when(createdFileService.getFileDeleteHistory(any())).thenReturn(expected);

        DeleteHistoryResponseDto actual = facade.getCreatedFileHistory(FILE_EXTERNAL_ID);

        assertEquals(expected, actual);
        verify(createdFileService).getFileDeleteHistory(FILE_EXTERNAL_ID);
    }
}
