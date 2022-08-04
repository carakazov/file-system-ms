package notes.project.filesystem.service;

import java.util.Collections;

import notes.project.filesystem.dto.ReplacingHistoryResponseDto;
import notes.project.filesystem.mapper.AddReplacingHistoryMapper;
import notes.project.filesystem.mapper.ReplacingHistoryResponseMapper;
import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.model.ReplacingHistory;
import notes.project.filesystem.repository.ReplacingHistoryRepository;
import notes.project.filesystem.service.logic.ReplacingHistoryService;
import notes.project.filesystem.service.logic.impl.ReplacingHistoryServiceImpl;
import notes.project.filesystem.utils.ApiUtils;
import notes.project.filesystem.utils.DbUtils;
import notes.project.filesystem.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReplacingHistoryImplTest {
    @Mock
    private ReplacingHistoryRepository repository;

    private ReplacingHistoryService service;

    @BeforeEach
    void init() {
        service = new ReplacingHistoryServiceImpl(
            repository,
            Mappers.getMapper(AddReplacingHistoryMapper.class),
            TestUtils.getComplexMapper(ReplacingHistoryResponseMapper.class)
        );
    }

    @Test
    void createSuccess() {
        ReplacingHistory expected = DbUtils.replacingHistory();
        when(repository.save(any())).thenReturn(expected);

        ReplacingHistory actual = service.create(DbUtils.createdFile(), DbUtils.directoryWithAlternativeExternalId());

        assertEquals(expected, actual);
    }

    @Test
    void getReplacingHistorySuccess() {
        CreatedFile file = DbUtils.createdFile();
        ReplacingHistory history = DbUtils.replacingHistory();
        ReplacingHistoryResponseDto expected = ApiUtils.replacingHistoryResponseDto();

        when(repository.findByCreatedFile(any())).thenReturn(Collections.singletonList(history));

        ReplacingHistoryResponseDto actual = service.getReplacingHistory(file);

        assertEquals(expected, actual);

        verify(repository).findByCreatedFile(file);
    }
}
