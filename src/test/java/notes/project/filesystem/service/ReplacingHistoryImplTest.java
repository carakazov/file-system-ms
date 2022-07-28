package notes.project.filesystem.service;

import notes.project.filesystem.mapper.ReplacingHistoryMapper;
import notes.project.filesystem.model.ReplacingHistory;
import notes.project.filesystem.repository.ReplacingHistoryRepository;
import notes.project.filesystem.service.impl.ReplacingHistoryServiceImpl;
import notes.project.filesystem.utils.DbUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static notes.project.filesystem.utils.TestDataConstants.*;

@ExtendWith(MockitoExtension.class)
class ReplacingHistoryImplTest {
    @Mock
    private ReplacingHistoryRepository repository;

    private ReplacingHistoryService service;

    @BeforeEach
    void init() {
        service = new ReplacingHistoryServiceImpl(
            repository,
            Mappers.getMapper(ReplacingHistoryMapper.class)
        );
    }

    @Test
    void createSuccess() {
        ReplacingHistory expected = DbUtils.replacingHistory();
        when(repository.save(any())).thenReturn(expected);

        ReplacingHistory actual = service.create(DbUtils.createdFile(), DbUtils.directoryWithAlternativeExternalId());

        assertEquals(expected, actual);
    }
}
