package notes.project.filesystem.repository;

import java.util.List;

import notes.project.filesystem.model.Cluster;
import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.model.DeleteHistory;
import notes.project.filesystem.model.Directory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeleteHistoryRepository extends JpaRepository<DeleteHistory, Long> {
    List<DeleteHistory> findByCreatedFile(CreatedFile file);
    List<DeleteHistory> findByDirectory(Directory directory);
    List<DeleteHistory> findByCluster(Cluster cluster);
}

