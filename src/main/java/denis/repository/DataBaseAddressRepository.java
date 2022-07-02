package denis.repository;

import denis.model.DataBaseAddress;
import denis.model.Street;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface DataBaseAddressRepository extends JpaRepository<DataBaseAddress, Long> {
    @Query(nativeQuery = true, value = "SELECT u.* FROM data_base_address u WHERE upper(u.title) LIKE upper(concat('%', ?1, '%')) LIMIT 50")
    List<DataBaseAddress> findByLastnameOrFirstname(String title);

    @Query(nativeQuery = true, value = "SELECT u.* FROM data_base_address u WHERE upper(u.title) LIKE upper(concat('%', ?1, '%')) and upper(u.street) LIKE upper(concat('%', ?2, '%')) LIMIT 50")
    List<DataBaseAddress> findByLastnameOrFirstname(String title, String street);

    @Query(nativeQuery = true, value = "SELECT u.* FROM data_base_address u WHERE upper(u.title) LIKE upper(concat('%', ?1, '%')) and upper(u.street) LIKE upper(concat('%', ?2, '%')) and upper(u.number) LIKE upper(concat('%', ?3, '%')) LIMIT 50")
    List<DataBaseAddress> findByLastnameOrFirstname(String title, String street, String number);

    @Query(nativeQuery = true, value = "SELECT DISTINCT title FROM data_base_address where upper(title) like upper(concat('%', ?1, '%')) limit 50")
    List<String> findUniqTitle(String title);

    @Query(nativeQuery = true, value = "SELECT DISTINCT street FROM data_base_address where upper(street) like upper(concat('%', ?1, '%')) limit 50")
    List<String> findUniqStreet(String street);


    @Query(value = "select address from DataBaseAddress address join Street s on address.street = s join City c on s.city = c where address.number = :streetNumber and s.title = :streetName and c.title = :cityName")
    Optional<DataBaseAddress> findByUserData(String streetNumber, String streetName, String cityName);

    @Query(value = """
            select address from DataBaseAddress address 
            join fetch address.street s 
            join fetch s.city c
            where 
            upper(c.title) like %:cityName% and 
            (:streetName is null or upper(s.title) like %:streetName%)  and 
            (:streetNumber is null or upper(address.number) like %:streetNumber%)
            """)
    /*@EntityGraph(attributePaths = {"street", "street.city"})*/
    List<DataBaseAddress> findByCityAndStreet(@Param("cityName") String cityName, @Param("streetName") String streetName, @Param("streetNumber") String streetNumber, Pageable pageable);

}
