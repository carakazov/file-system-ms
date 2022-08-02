package notes.project.filesystem.repository;

import java.util.List;

import notes.project.filesystem.model.CreatedFile;
import notes.project.filesystem.model.ReplacingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplacingHistoryRepository extends JpaRepository<ReplacingHistory, Long> {
    List<ReplacingHistory> findByCreatedFile(CreatedFile file);
}
