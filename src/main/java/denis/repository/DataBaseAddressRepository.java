package denis.repository;

import denis.model.DataBaseAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface DataBaseAddressRepository extends JpaRepository<DataBaseAddress, Long> {
    @Query(nativeQuery = true, value = "SELECT u.* FROM data_base_address u WHERE upper(u.title) LIKE upper(concat('%', ?1, '%')) LIMIT 50")
    List<DataBaseAddress> findByLastnameOrFirstname(String title);

    List<DataBaseAddress> findByTitleContainingIgnoreCase(String title);

    List<DataBaseAddress> findByStreetContainingIgnoreCase(String title);

    @Query(nativeQuery = true, value = "SELECT u.* FROM data_base_address u WHERE upper(u.title) LIKE upper(concat('%', ?1, '%')) and upper(u.street) LIKE upper(concat('%', ?2, '%')) LIMIT 50")
    List<DataBaseAddress> findByLastnameOrFirstname(String title, String street);

    @Query(nativeQuery = true, value = "SELECT u.* FROM data_base_address u WHERE upper(u.title) LIKE upper(concat('%', ?1, '%')) and upper(u.street) LIKE upper(concat('%', ?2, '%')) and upper(u.number) LIKE upper(concat('%', ?3, '%')) LIMIT 50")
    List<DataBaseAddress> findByLastnameOrFirstname(String title, String street, String number);

    @Query(nativeQuery = true, value = "SELECT DISTINCT title FROM data_base_address where upper(title) like upper(concat('%', ?1, '%')) limit 50")
    List<String> findUniqTitle(String title);

    @Query(nativeQuery = true, value = "SELECT DISTINCT street FROM data_base_address where upper(street) like upper(concat('%', ?1, '%')) limit 50")
    List<String> findUniqStreet(String street);
}
