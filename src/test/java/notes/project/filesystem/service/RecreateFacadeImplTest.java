package notes.project.filesystem.service;

import notes.project.filesystem.model.Cluster;
import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.model.Directory;
import notes.project.filesystem.service.impl.RecreateFacadeImpl;
import notes.project.filesystem.utils.ApiUtils;
import notes.project.filesystem.utils.ApplicationPropertiesUtils;
import notes.project.filesystem.utils.DbUtils;
import notes.project.filesystem.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static notes.project.filesystem.utils.TestDataConstants.*;

@ExtendWith(MockitoExtension.class)
class RecreateFacadeImplTest {
    @Mock
    private RecreateService recreateService;
    @Mock
    private ObjectExistingStatusChanger objectExistingStatusChanger;
    @Mock
    private CreatedFileService createdFileService;
    @Mock
    private DirectoryService directoryService;
    @Mock
    private ClusterService clusterService;
    @Mock
    private Validator<CreatedFile> recreateFileValidator;
    @Mock
    private Validator<Directory> recreateDirectoryValidator;
    @Mock
    private Validator<Cluster> recreateClusterValidator;

    private RecreateFacade facade;

    @BeforeEach
    void init() {
        facade = new RecreateFacadeImpl(
            ApplicationPropertiesUtils.applicationPropertiesForRecreateFacade(),
            recreateService,
            objectExistingStatusChanger,
            createdFileService,
            directoryService,
            clusterService,
            recreateFileValidator,
            recreateDirectoryValidator,
            recreateClusterValidator
        );
    }

    @Test
    void recreateFileSuccessWhenAllPathDeletedSuccess() {
        CreatedFile createdFile = DbUtils.createdFile();
        createdFile.getDirectory().setDeleted(Boolean.TRUE);
        createdFile.getDirectory().getCluster().setDeleted(Boolean.TRUE);

        when(createdFileService.findFileByExternalId(any())).thenReturn(createdFile);

        facade.recreateFile(createdFile.getExternalId());

        verify(createdFileService).findFileByExternalId(createdFile.getExternalId());
        verify(recreateService).recreateCluster(createdFile.getDirectory().getCluster());
        verify(objectExistingStatusChanger).changeClusterExistingStatus(createdFile.getDirectory().getCluster());
    }

    @Test
    void recreateFileSuccessWhenDirectoryDeletedSuccess() {
        CreatedFile createdFile = DbUtils.createdFile();
        createdFile.getDirectory().setDeleted(Boolean.TRUE);

        when(createdFileService.findFileByExternalId(any())).thenReturn(createdFile);

        facade.recreateFile(createdFile.getExternalId());

        verify(createdFileService).findFileByExternalId(createdFile.getExternalId());
        verify(recreateService).recreateDirectory(createdFile.getDirectory());
        verify(objectExistingStatusChanger).changeDirectoryExistingStatus(createdFile.getDirectory());
    }

    @Test
    void recreateFileSuccess() {
        CreatedFile createdFile = DbUtils.createdFile();
        createdFile.setDeleted(Boolean.TRUE);

        when(createdFileService.findFileByExternalId(any())).thenReturn(createdFile);

        facade.recreateFile(createdFile.getExternalId());

        verify(createdFileService).findFileByExternalId(createdFile.getExternalId());
        verify(recreateService).recreateFile(createdFile);
        verify(objectExistingStatusChanger).changeCreatedFileExistingStatus(createdFile);
    }

    @Test
    void recreateDirectoryWhenClusterDeletedSuccess() {
        Directory directory = DbUtils.directory();
        directory.getCluster().setDeleted(Boolean.TRUE);

        when(directoryService.findByExternalId(any())).thenReturn(directory);

        facade.recreateDirectory(directory.getExternalId());

        verify(directoryService).findByExternalId(directory.getExternalId());
        verify(recreateService).recreateCluster(directory.getCluster());
        verify(objectExistingStatusChanger).changeClusterExistingStatus(directory.getCluster());
    }

    @Test
    void recreateDirectorySuccess() {
        Directory directory = DbUtils.directory();
        directory.setDeleted(Boolean.TRUE);

        when(directoryService.findByExternalId(any())).thenReturn(directory);

        facade.recreateDirectory(directory.getExternalId());

        verify(directoryService).findByExternalId(directory.getExternalId());
        verify(recreateService).recreateDirectory(directory);
        verify(objectExistingStatusChanger).changeDirectoryExistingStatus(directory);
    }

    @Test
    void recreateCluster() {
        Cluster cluster = DbUtils.cluster();
        cluster.setDeleted(Boolean.TRUE);

        when(clusterService.findByExternalId(any())).thenReturn(cluster);

        facade.recreateCluster(cluster.getExternalId());

        verify(clusterService).findByExternalId(cluster.getExternalId());
        verify(recreateService).recreateCluster(cluster);
        verify(objectExistingStatusChanger).changeClusterExistingStatus(cluster);
    }
}
