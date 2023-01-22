package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ro.tuc.ds2020.entities.Client;
import ro.tuc.ds2020.entities.Device;

import java.util.List;
import java.util.UUID;

public interface DeviceRepository extends JpaRepository<Device, UUID> {
    @Query("select d from Device d where d.user.id = ?1")
    List<Device> findByUser_Id(UUID id);
    /**
     * Example: JPA generate Query by Field
     */



}
