package notes.project.filesystem.service.impl;

import java.util.concurrent.locks.Lock;
import javax.transaction.Transactional;

import liquibase.util.Validate;
import lombok.RequiredArgsConstructor;
import notes.project.filesystem.dto.DirectoryCreationRequestDto;
import notes.project.filesystem.dto.DirectoryCreationResponseDto;
import notes.project.filesystem.file.FileManager;
import notes.project.filesystem.mapper.DirectoryCreationMapper;
import notes.project.filesystem.model.Cluster;
import notes.project.filesystem.model.Directory;
import notes.project.filesystem.repository.DirectoryRepository;
import notes.project.filesystem.service.ClusterService;
import notes.project.filesystem.service.DirectoryService;
import notes.project.filesystem.validation.Validator;
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
        fileManager.createDirectory(request.getClusterName(), request.getDirectoryName());
        Directory directory = directoryCreationMapper.from(request, clusterService.findByTitle(request.getClusterName()));
        directory = repository.save(directory);
        return directoryCreationMapper.to(directory);
    }
}
