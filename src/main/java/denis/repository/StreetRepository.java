package denis.repository;

import denis.model.Street;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface StreetRepository extends JpaRepository<Street, Long> {
    Optional<Street> findByCityTitleAndTitle(String cityName, String streetName);

    List<Street> findByTitleContainingIgnoreCase(String streetName);

    @Query(value = "select s from Street s join City c on s.city = c where upper(c.title) like upper(concat('%',?1,'%')) and upper(s.title) like upper(concat('%',?2,'%'))")
    List<Street> findCityAndStreet(@Param("cityName") String cityName, @Param("streetName")  String streetName);
}
