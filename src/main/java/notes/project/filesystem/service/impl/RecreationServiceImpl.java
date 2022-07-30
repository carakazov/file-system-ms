package notes.project.filesystem.service.impl;

import java.util.UUID;

import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import notes.project.filesystem.file.ZipManager;
import notes.project.filesystem.model.Cluster;
import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.model.Directory;
import notes.project.filesystem.model.EventType;
import notes.project.filesystem.service.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecreationServiceImpl implements RecreationService {
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
    }

    @Override
    @Transactional
    public void recreateCluster(Cluster cluster) {
        zipManager.recreateCluster(cluster);
        clusterService.updateClusterLastRequestedTime(cluster);
        deleteHistoryService.createClusterDeleteHistory(cluster, EventType.RECREATED);
    }

}
