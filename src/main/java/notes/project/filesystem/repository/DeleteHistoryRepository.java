package notes.project.filesystem.repository;

import notes.project.filesystem.model.DeleteHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeleteHistoryRepository extends JpaRepository<DeleteHistory, Long> {
}
