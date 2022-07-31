package notes.project.filesystem.service.impl;

import javax.transaction.Transactional;

import notes.project.filesystem.model.Cluster;
import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.model.Directory;
import notes.project.filesystem.service.ObjectExistingStatusChanger;
import org.springframework.stereotype.Service;

@Service
public class ObjectExistingStatusChangerImpl implements ObjectExistingStatusChanger {
    @Override
    @Transactional
    public void changeClusterExistingStatus(Cluster cluster, Boolean deleted) {
        cluster.setDeleted(deleted);
        cluster.getDirectories().forEach(item -> changeDirectoryExistingStatus(item, deleted));
    }

    @Override
    @Transactional
    public void changeDirectoryExistingStatus(Directory directory, Boolean deleted) {
        directory.setDeleted(deleted);
        directory.getCreatedFiles().forEach(item -> changeCreatedFileExistingStatus(item, deleted));
    }

    @Override
    @Transactional
    public void changeCreatedFileExistingStatus(CreatedFile createdFile, Boolean deleted) {
        createdFile.setDeleted(deleted);
    }
}
