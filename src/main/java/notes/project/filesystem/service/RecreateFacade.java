package notes.project.filesystem.service;

import java.util.UUID;

public interface RecreateFacade {
    void recreateFile(UUID fileExternalId);
    void recreateDirectory(UUID directoryExternalId);
    void recreateCluster(UUID clusterExternalId);
}
