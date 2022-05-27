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

    @Query(nativeQuery = true, value = "SELECT u.* FROM data_base_address u WHERE upper(u.title) LIKE upper(concat('%', ?1, '%')) and upper(u.street) LIKE upper(concat('%', ?2, '%')) LIMIT 50")
    List<DataBaseAddress> findByLastnameOrFirstname(String title, String street);

    @Query(nativeQuery = true, value = "SELECT u.* FROM data_base_address u WHERE upper(u.title) LIKE upper(concat('%', ?1, '%')) and upper(u.street) LIKE upper(concat('%', ?2, '%')) and upper(u.number) LIKE upper(concat('%', ?3, '%')) LIMIT 50")
    List<DataBaseAddress> findByLastnameOrFirstname(String title, String street, String number);
}
