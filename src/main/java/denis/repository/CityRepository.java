package denis.repository;

import denis.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface CityRepository extends JpaRepository<City, Long> {
    Optional<City> findByTitle(String title);

    List<City> findByTitleContainingIgnoreCase(String cityName);
}
