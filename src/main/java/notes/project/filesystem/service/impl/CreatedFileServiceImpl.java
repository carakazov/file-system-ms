package notes.project.filesystem.service.impl;

import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import notes.project.filesystem.dto.AddFileRequestDto;
import notes.project.filesystem.dto.AddFileResponseDto;
import notes.project.filesystem.file.FileManager;
import notes.project.filesystem.mapper.FileCreationMapper;
import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.model.Directory;
import notes.project.filesystem.repository.CreatedFileRepository;
import notes.project.filesystem.service.ClusterService;
import notes.project.filesystem.service.CreatedFileService;
import notes.project.filesystem.service.DirectoryService;
import notes.project.filesystem.validation.Validator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatedFileServiceImpl implements CreatedFileService {
    private final CreatedFileRepository repository;
    private final FileManager fileManager;
    private final DirectoryService directoryService;
    private final FileCreationMapper fileCreationMapper;
    private final ClusterService clusterService;
    private final Validator<AddFileRequestDto> addFileValidator;

    @Override
    @Transactional
    public AddFileResponseDto addFile(AddFileRequestDto request) {
        addFileValidator.validate(request);
        Directory directory = directoryService.findByExternalId(request.getDirectoryExternalId());
        CreatedFile file = new CreatedFile();
        file.setDirectory(directory);
        file.setTitle(request.getTitle());
        fileManager.createFile(file, request.getContent());
        clusterService.updateClusterLastRequestedTime(directory.getCluster());
        return fileCreationMapper.to(repository.save(file));
    }
}
