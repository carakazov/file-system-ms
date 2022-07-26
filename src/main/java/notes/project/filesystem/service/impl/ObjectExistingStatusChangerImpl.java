package notes.project.filesystem.service.impl;

import javax.transaction.Transactional;

import notes.project.filesystem.model.Cluster;
import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.model.Directory;
import notes.project.filesystem.service.ObjectExistingStatusChanger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class ObjectExistingStatusChangerImpl implements ObjectExistingStatusChanger {
    @Override
    @Transactional
    public void changeClusterExistingStatus(Cluster cluster) {
        cluster.setDeleted(!cluster.getDeleted());
        cluster.getDirectories().forEach(this::changeDirectoryExistingStatus);
    }

    @Override
    @Transactional
    public void changeDirectoryExistingStatus(Directory directory) {
        directory.setDeleted(!directory.getDeleted());
        directory.getCreatedFiles().forEach(this::changeCreatedFileExistingStatus);
    }

    @Override
    @Transactional
    public void changeCreatedFileExistingStatus(CreatedFile createdFile) {
        createdFile.setDeleted(!createdFile.getDeleted());
    }
}
