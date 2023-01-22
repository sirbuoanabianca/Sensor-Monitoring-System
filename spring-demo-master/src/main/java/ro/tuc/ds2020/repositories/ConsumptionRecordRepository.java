package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ro.tuc.ds2020.entities.ConsumptionRecord;
import ro.tuc.ds2020.entities.Device;

import java.util.List;
import java.util.UUID;

public interface ConsumptionRecordRepository extends JpaRepository<ConsumptionRecord, UUID> {
    @Query("select c from ConsumptionRecord c where c.device.id = ?1")
    List<ConsumptionRecord> findByDevice_Id(UUID id);




}
