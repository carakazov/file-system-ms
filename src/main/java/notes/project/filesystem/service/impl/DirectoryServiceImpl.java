package notes.project.filesystem.service.impl;

import java.util.UUID;
import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import notes.project.filesystem.dto.DirectoryCreationRequestDto;
import notes.project.filesystem.dto.DirectoryCreationResponseDto;
import notes.project.filesystem.exception.ExceptionCode;
import notes.project.filesystem.exception.FileSystemException;
import notes.project.filesystem.file.FileManager;
import notes.project.filesystem.mapper.DirectoryCreationMapper;
import notes.project.filesystem.model.Directory;
import notes.project.filesystem.repository.DirectoryRepository;
import notes.project.filesystem.service.ClusterService;
import notes.project.filesystem.service.DirectoryService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DirectoryServiceImpl implements DirectoryService {
    private final DirectoryRepository repository;
    private final DirectoryCreationMapper directoryCreationMapper;
    private final FileManager fileManager;
    private final ClusterService clusterService;


    @Override
    @Transactional
    public DirectoryCreationResponseDto createDirectory(DirectoryCreationRequestDto request) {
        Directory directory = directoryCreationMapper.from(request, clusterService.findByExternalId(request.getClusterExternalId()));
        directory = repository.save(directory);
        fileManager.createDirectory(directory);
        return directoryCreationMapper.to(directory);
    }

    @Override
    public Directory findByExternalId(UUID externalId) {
        return repository.findByExternalId(externalId)
            .orElseThrow(() -> new FileSystemException(ExceptionCode.DIRECTORY_DOES_NOT_EXISTS));
    }
}
