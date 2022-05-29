package denis.repository;

import denis.model.UserAdress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface UserAdressRepository extends JpaRepository<UserAdress, String> {
    List<UserAdress> findByUserId(Long userId);
}
