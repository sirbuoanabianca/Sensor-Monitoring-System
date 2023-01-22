package ro.tuc.ds2020.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class DeviceBindDTO {

    @Getter
    @Setter
    private UUID id;
    @Getter
    @Setter
    @NotNull
    private UUID userId;

    public DeviceBindDTO() {
    }
}
