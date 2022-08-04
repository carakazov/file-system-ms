package notes.project.filesystem.service.logic;

import java.util.UUID;

public interface RecreateFacade {
    void recreateFile(UUID fileExternalId);
    void recreateDirectory(UUID directoryExternalId);
    void recreateCluster(UUID clusterExternalId);
}
