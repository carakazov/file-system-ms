package notes.project.filesystem.service.impl;

import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import notes.project.filesystem.file.ZipManager;
import notes.project.filesystem.model.Cluster;
import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.model.Directory;
import notes.project.filesystem.model.EventType;
import notes.project.filesystem.service.ClusterService;
import notes.project.filesystem.service.DeleteHistoryService;
import notes.project.filesystem.service.RecreateService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecreateServiceImpl implements RecreateService {
    private final DeleteHistoryService deleteHistoryService;
    private final ClusterService clusterService;
    private final ZipManager zipManager;


    @Override
    @Transactional
    public void recreateFile(CreatedFile createdFile) {
        zipManager.recreateFile(createdFile);
        clusterService.updateClusterLastRequestedTime(createdFile.getDirectory().getCluster());
        deleteHistoryService.createCreatedFileDeleteHistory(createdFile, EventType.RECREATED);
    }

    @Override
    @Transactional
    public void recreateDirectory(Directory directory) {
        zipManager.recreateDirectory(directory);
        clusterService.updateClusterLastRequestedTime(directory.getCluster());
        deleteHistoryService.createDirectoryDeleteHistory(directory, EventType.RECREATED);
        directory.getCreatedFiles().forEach(item -> deleteHistoryService.createCreatedFileDeleteHistory(item, EventType.RECREATED));
    }

    @Override
    @Transactional
    public void recreateCluster(Cluster cluster) {
        zipManager.recreateCluster(cluster);
        clusterService.updateClusterLastRequestedTime(cluster);
        deleteHistoryService.createClusterDeleteHistory(cluster, EventType.RECREATED);
        cluster.getDirectories().forEach(item -> {
            deleteHistoryService.createDirectoryDeleteHistory(item, EventType.RECREATED);
            item.getCreatedFiles().forEach(innerItem -> deleteHistoryService.createCreatedFileDeleteHistory(innerItem, EventType.RECREATED));
        });
    }

    @Override
    @Transactional
    public void recreateFileWithPath(CreatedFile createdFile) {
        zipManager.recreateFileWithPath(createdFile);
        clusterService.updateClusterLastRequestedTime(createdFile.getDirectory().getCluster());
        deleteHistoryService.createClusterDeleteHistory(createdFile.getDirectory().getCluster(), EventType.RECREATED);
        deleteHistoryService.createDirectoryDeleteHistory(createdFile.getDirectory(), EventType.RECREATED);
        deleteHistoryService.createCreatedFileDeleteHistory(createdFile, EventType.RECREATED);
    }

    @Override
    @Transactional
    public void recreateDirectoryWithPath(Directory directory) {
        zipManager.recreateDirectoryWithPath(directory);
        clusterService.updateClusterLastRequestedTime(directory.getCluster());
        deleteHistoryService.createClusterDeleteHistory(directory.getCluster(), EventType.RECREATED);
        deleteHistoryService.createDirectoryDeleteHistory(directory, EventType.RECREATED);
        directory.getCreatedFiles().forEach(item -> deleteHistoryService.createCreatedFileDeleteHistory(item, EventType.RECREATED));
    }
}
