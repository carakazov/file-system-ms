package notes.project.filesystem.service.logic.impl;

import java.util.UUID;
import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import notes.project.filesystem.config.ApplicationProperties;
import notes.project.filesystem.exception.ExceptionCode;
import notes.project.filesystem.exception.FileSystemException;
import notes.project.filesystem.model.Cluster;
import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.model.Directory;
import notes.project.filesystem.service.logic.*;
import notes.project.filesystem.validation.Validator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecreateFacadeImpl implements RecreateFacade {
    private final ApplicationProperties properties;
    private final RecreateService recreationService;
    private final ObjectExistingStatusChanger objectExistingStatusChanger;
    private final CreatedFileService createdFileService;
    private final DirectoryService directoryService;
    private final ClusterService clusterService;
    private final Validator<CreatedFile> recreateFileValidator;
    private final Validator<Directory> recreateDirectoryValidator;
    private final Validator<Cluster> recreateClusterValidator;

    private static final Object LOCK = new Object();

    @Override
    @Transactional
    public void recreateFile(UUID fileExternalId) {
        CreatedFile file = createdFileService.findFileByExternalId(fileExternalId);
        recreateFileValidator.validate(file);
        proceed(defineObject(file), file);
    }

    @Override
    @Transactional
    public void recreateDirectory(UUID directoryExternalId) {
        Directory directory = directoryService.findByExternalId(directoryExternalId);
        recreateDirectoryValidator.validate(directory);
        proceed(defineObject(directory), directory);
    }

    @Override
    @Transactional
    public void recreateCluster(UUID clusterExternalId) {
        Cluster cluster = clusterService.findByExternalId(clusterExternalId);
        recreateClusterValidator.validate(cluster);
        recreateClusterImpl(cluster);
    }

    private void proceed(RecreationObject object, CreatedFile file) {
        if(RecreationObject.FILE.equals(object)) {
            recreateFileImpl(file);
        } else {
            recreateFileWithPath(file);
        }
    }

    private void proceed(RecreationObject object, Directory directory) {
        if(RecreationObject.DIRECTORY.equals(object)) {
            recreateDirectoryImpl(directory);
        } else {
            recreateDirectoryWithPath(directory);
        }
    }

    private void recreateFileImpl(CreatedFile file) {
        synchronized(LOCK) {
            recreationService.recreateFile(file);
            objectExistingStatusChanger.changeCreatedFileExistingStatus(file, Boolean.FALSE);
        }
    }

    private void recreateDirectoryWithPath(Directory directory) {
        synchronized(LOCK) {
            recreationService.recreateDirectoryWithPath(directory);
            objectExistingStatusChanger.changeClusterExistingStatusOnly(directory.getCluster(), Boolean.FALSE);
            objectExistingStatusChanger.changeDirectoryExistingStatus(directory, Boolean.FALSE);
        }
    }

    private void recreateFileWithPath(CreatedFile file) {
        synchronized(LOCK) {
            recreationService.recreateFileWithPath(file);
            if(Boolean.TRUE.equals(file.getDirectory().getCluster().getDeleted())) {
                objectExistingStatusChanger.changeClusterExistingStatusOnly(file.getDirectory().getCluster(), Boolean.FALSE);
            }
            objectExistingStatusChanger.changeDirectoryExistingStatusOnly(file.getDirectory(), Boolean.FALSE);
            objectExistingStatusChanger.changeCreatedFileExistingStatus(file, Boolean.FALSE);
        }
    }

    private void recreateDirectoryImpl(Directory directory) {
        synchronized(LOCK) {
            recreationService.recreateDirectory(directory);
            objectExistingStatusChanger.changeDirectoryExistingStatus(directory, Boolean.FALSE);
        }
    }

    private void recreateClusterImpl(Cluster cluster) {
        synchronized(LOCK) {
            recreationService.recreateCluster(cluster);
            objectExistingStatusChanger.changeClusterExistingStatus(cluster, Boolean.FALSE);
        }
    }

    private RecreationObject defineObject(CreatedFile createdFile) {
        if(Boolean.FALSE.equals(properties.getRecreateFullPath()) && Boolean.TRUE.equals(createdFile.getDirectory().getDeleted())) {
            throw new FileSystemException(ExceptionCode.PARENT_OBJECT_DELETED);
        } else if(Boolean.TRUE.equals(properties.getRecreateFullPath()) && Boolean.TRUE.equals(createdFile.getDirectory().getDeleted())) {
            return RecreationObject.FILE_WITH_PATH;
        } else {
            return RecreationObject.FILE;
        }
    }

    private RecreationObject defineObject(Directory directory) {
        if(Boolean.FALSE.equals(properties.getRecreateFullPath()) && Boolean.TRUE.equals(directory.getCluster().getDeleted())) {
            throw new FileSystemException(ExceptionCode.PARENT_OBJECT_DELETED);
        } else if(Boolean.TRUE.equals(properties.getRecreateFullPath()) && Boolean.TRUE.equals(directory.getCluster().getDeleted())) {
            return RecreationObject.DIRECTORY_WITH_PATH;
        } else {
            return RecreationObject.DIRECTORY;
        }
    }

    private RecreationObject definePath(Directory directory) {
        if(Boolean.TRUE.equals(directory.getCluster().getDeleted())) {
            return RecreationObject.CLUSTER;
        } else if(Boolean.TRUE.equals(directory.getDeleted())) {
            return RecreationObject.DIRECTORY;
        } else {
            return RecreationObject.FILE;
        }
    }

    private enum RecreationObject {
        FILE,
        FILE_WITH_PATH,
        DIRECTORY,
        DIRECTORY_WITH_PATH,
        CLUSTER
    }

}
