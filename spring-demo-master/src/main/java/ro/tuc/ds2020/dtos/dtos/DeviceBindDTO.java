package ro.tuc.ds2020.dtos.dtos;

import lombok.Getter;
import lombok.Setter;

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
