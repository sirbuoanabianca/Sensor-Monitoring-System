 package ro.tuc.ds2020.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import ro.tuc.ds2020.entities.Device;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.UUID;

public class ConsumptionRecordDTO {

    @Getter
    @Setter
    private UUID id;
    @Getter
    @Setter
    @NotNull
    private UUID device_id;
    @Getter
    @Setter
    @NotNull
    private Timestamp timestamp;

    @Getter
    @Setter
    @NotNull
    private double consumption;


    public ConsumptionRecordDTO() {
    }

    public ConsumptionRecordDTO(UUID id, UUID device_id, Timestamp timestamp, double consumption) {
        this.id = id;
        this.device_id = device_id;
        this.timestamp = timestamp;
        this.consumption = consumption;
    }
}
