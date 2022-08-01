package notes.project.filesystem.service;

import java.util.Collections;

import notes.project.filesystem.dto.DeleteHistoryResponseDto;
import notes.project.filesystem.mapper.DeleteHistoryResponseMapper;
import notes.project.filesystem.model.*;
import notes.project.filesystem.repository.DeleteHistoryRepository;
import notes.project.filesystem.service.impl.DeleteHistoryServiceImpl;
import notes.project.filesystem.utils.ApiUtils;
import notes.project.filesystem.utils.DbUtils;
import notes.project.filesystem.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteHistoryImplTest {
    @Mock
    private DeleteHistoryRepository repository;

    @Captor
    private ArgumentCaptor<DeleteHistory> deleteHistorySaveCaptor;

    private DeleteHistoryService service;

    @BeforeEach
    void init() {
        service = new DeleteHistoryServiceImpl(
            repository,
            TestUtils.getComplexMapper(DeleteHistoryResponseMapper.class)
        );
    }

    @Test
    void deleteCreatedFileDeleteHistorySuccess() {
        CreatedFile createdFile = DbUtils.createdFile();
        DeleteHistory expected = DbUtils.deleteCreatedFileHistory();

        when(repository.save(any())).thenReturn(expected);

        service.createCreatedFileDeleteHistory(createdFile, EventType.DELETED);

        verify(repository).save(deleteHistorySaveCaptor.capture());

        DeleteHistory actual = deleteHistorySaveCaptor.getValue();

        assertEquals(expected.getCreatedFile(), actual.getCreatedFile());
        assertEquals(expected.getEvent(), actual.getEvent());
    }

    @Test
    void createDirectoryDeleteHistorySuccess() {
        Directory directory = DbUtils.directory();
        DeleteHistory expected = DbUtils.deleteDirectoryHistory();

        when(repository.save(any())).thenReturn(expected);

        service.createDirectoryDeleteHistory(directory, EventType.DELETED);

        verify(repository).save(deleteHistorySaveCaptor.capture());

        DeleteHistory actual = deleteHistorySaveCaptor.getValue();

        assertEquals(expected.getDirectory(), actual.getDirectory());
        assertEquals(expected.getEvent(), actual.getEvent());
    }

    @Test
    void createClusterDeleteHistorySuccess() {
        Cluster cluster = DbUtils.cluster();
        DeleteHistory expected = DbUtils.deleteClusterHistory();

        when(repository.save(any())).thenReturn(expected);

        service.createClusterDeleteHistory(cluster, EventType.DELETED);

        verify(repository).save(deleteHistorySaveCaptor.capture());

        DeleteHistory actual = deleteHistorySaveCaptor.getValue();

        assertEquals(expected.getCluster(), actual.getCluster());
        assertEquals(expected.getEvent(), actual.getEvent());
    }

    @Test
    void getCreateFileDeleteHistoryWhenHistoryExistsSuccess() {
        CreatedFile createdFile = DbUtils.createdFile();
        DeleteHistory deleteHistory = DbUtils.deleteCreatedFileHistory();

        DeleteHistoryResponseDto expected = ApiUtils.deleteHistoryCreatedFileResponseDto();

        when(repository.findByCreatedFile(any())).thenReturn(Collections.singletonList(deleteHistory));

        DeleteHistoryResponseDto actual = service.getCreatedFileDeleteHistory(createdFile);

        assertEquals(expected, actual);

        verify(repository).findByCreatedFile(createdFile);
    }

    @Test
    void getCreatedFileDeleteHistoryWhenHistoryDoesNotExistSuccess() {
        CreatedFile createdFile = DbUtils.createdFile();

        DeleteHistoryResponseDto expected = ApiUtils.deleteHistoryCreatedFileResponseDto();
        expected.setCurrentState(EventType.CREATED);
        expected.setHistory(Collections.EMPTY_LIST);

        when(repository.findByCreatedFile(any())).thenReturn(Collections.EMPTY_LIST);

        DeleteHistoryResponseDto actual = service.getCreatedFileDeleteHistory(createdFile);

        assertEquals(expected, actual);

        verify(repository).findByCreatedFile(createdFile);
    }

    @Test
    void getDirectoryDeleteHistoryWhenHistoryExistsSuccess() {
        Directory directory = DbUtils.directory();

        DeleteHistoryResponseDto expected = ApiUtils.deleteHistoryDirectoryResponseDto();

        when(repository.findByDirectory(any())).thenReturn(Collections.singletonList(DbUtils.deleteDirectoryHistory()));

        DeleteHistoryResponseDto actual = service.getDirectoryDeleteHistory(directory);

        assertEquals(expected, actual);

        verify(repository).findByDirectory(directory);
    }

    @Test
    void getDirectoryDeleteHistoryWhenHistoryDoesNotExistsSuccess() {
        Directory directory = DbUtils.directory();

        DeleteHistoryResponseDto expected = ApiUtils.deleteHistoryDirectoryResponseDto();
        expected.setCurrentState(EventType.CREATED);
        expected.setHistory(Collections.emptyList());

        when(repository.findByDirectory(any())).thenReturn(Collections.emptyList());

        DeleteHistoryResponseDto actual = service.getDirectoryDeleteHistory(directory);

        assertEquals(expected, actual);

        verify(repository).findByDirectory(directory);
    }

    @Test
    void getClusterDeleteHistoryWhenHistoryExistsSuccess() {
        Cluster cluster = DbUtils.cluster();
        DeleteHistory deleteHistory = DbUtils.deleteClusterHistory();

        DeleteHistoryResponseDto expected = ApiUtils.deleteHistoryClusterResponseDto();

        when(repository.findByCluster(any())).thenReturn(Collections.singletonList(deleteHistory));

        DeleteHistoryResponseDto actual = service.getClusterDeleteHistory(cluster);

        assertEquals(expected, actual);

        verify(repository).findByCluster(cluster);
    }

    @Test
    void getClusterDeleteHistoryWhenHistoryDoesNotExitsSuccess() {
        Cluster cluster = DbUtils.cluster();

        DeleteHistoryResponseDto expected = ApiUtils.deleteHistoryClusterResponseDto();
        expected.setCurrentState(EventType.CREATED);
        expected.setHistory(Collections.emptyList());

        when(repository.findByCluster(any())).thenReturn(Collections.emptyList());

        DeleteHistoryResponseDto actual = service.getClusterDeleteHistory(cluster);

        assertEquals(expected, actual);

        verify(repository).findByCluster(cluster);
    }
}
