package ro.tuc.ds2020.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class DeviceDTO extends RepresentationModel<DeviceDTO> {

    @Getter
    @Setter
    private UUID id;
    @Getter
    @Setter
    @NotNull
    private String description;
    @Getter
    @Setter
    @NotNull
    private String address;
    @Getter
    @Setter
    @NotNull
    private int max_consumption;
    @Getter
    @Setter
    private String usernameOwner;

    public DeviceDTO() {
    }

    public DeviceDTO(UUID id, String description, String address, int max_consumption, String username) {
        this.id = id;
        this.description = description;
        this.address = address;
        this.max_consumption = max_consumption;
        this.usernameOwner = username;
    }
}
