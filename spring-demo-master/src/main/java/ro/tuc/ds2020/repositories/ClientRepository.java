package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ro.tuc.ds2020.entities.Client;

import java.util.List;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {

    @Query("select c from Client c where c.username = ?1")
    Client findByUsername(String username);

    /**
     * Example: JPA generate Query by Field
     */


//    /**
//     * Example: Write Custom Query
//     */
//    @Query(value = "SELECT c " +
//            "FROM Client c " +
//            "WHERE c.username = :username " +
////            "AND c.age >= 60  ")
//            "")
//    Optional<Client> findSeniorsByName(@Param("username") String username);

}
