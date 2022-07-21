package notes.project.filesystem.service;

import io.swagger.annotations.Api;
import notes.project.filesystem.dto.AddFileRequestDto;
import notes.project.filesystem.dto.AddFileResponseDto;
import notes.project.filesystem.file.FileManager;
import notes.project.filesystem.mapper.FileCreationMapper;
import notes.project.filesystem.repository.CreatedFileRepository;
import notes.project.filesystem.service.impl.CreatedFileServiceImpl;
import notes.project.filesystem.utils.ApiUtils;
import notes.project.filesystem.utils.DbUtils;
import notes.project.filesystem.validation.impl.FileCreationValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreatedFileServiceImplTest {
    @Mock
    private CreatedFileRepository repository;
    @Mock
    private FileManager fileManager;
    @Mock
    private DirectoryService directoryService;
    @Mock
    private ClusterService clusterService;
    @Mock
    private FileCreationValidator fileCreationValidator;

    private CreatedFileService service;

    @BeforeEach
    void init() {
        service = new CreatedFileServiceImpl(
            repository,
            fileManager,
            directoryService,
            Mappers.getMapper(FileCreationMapper.class),
            clusterService,
            fileCreationValidator
        );
    }

    @Test
    void addFileSuccess() {
        when(directoryService.findByExternalId(any())).thenReturn(DbUtils.directory());
        when(repository.save(any())).thenReturn(DbUtils.createdFile());
        AddFileRequestDto request = ApiUtils.addFileRequestDto();
        AddFileResponseDto expected = ApiUtils.addFileResponseDto();

        AddFileResponseDto actual = service.addFile(request);

        assertEquals(expected, actual);

        verify(directoryService).findByExternalId(request.getDirectoryExternalId());
    }
}
