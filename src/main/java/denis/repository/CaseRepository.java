package denis.repository;

import denis.model.Case;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface CaseRepository extends JpaRepository<Case, Integer> {
    Optional<Case> findByUserId(Long userId);
}
