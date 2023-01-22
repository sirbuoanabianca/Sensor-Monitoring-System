package ro.tuc.ds2020.dtos;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import ro.tuc.ds2020.entities.Client;

public class ClientDetailsDTO {

    @Getter
    @Setter
    private UUID id;
    @Getter
    @Setter
    @NotNull
    private String username;
    @Getter
    @Setter
    @NotNull
    private String password;
    @Getter
    @Setter
    private Client.role role;

    public ClientDetailsDTO() {
    }

    public ClientDetailsDTO(UUID id, String username, String password, Client.role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
