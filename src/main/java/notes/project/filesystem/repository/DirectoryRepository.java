package notes.project.filesystem.repository;

import notes.project.filesystem.model.Cluster;
import notes.project.filesystem.model.Directory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectoryRepository extends JpaRepository<Directory, Long> {
    boolean existsByClusterTitleAndTitle(String clusterTitle, String directoryTitle);
}
