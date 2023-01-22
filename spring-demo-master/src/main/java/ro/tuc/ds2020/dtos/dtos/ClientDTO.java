package ro.tuc.ds2020.dtos.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import ro.tuc.ds2020.entities.Client;

import java.util.UUID;

public class ClientDTO extends RepresentationModel<ClientDTO> {

    @Getter
    @Setter
    private UUID id;
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private Client.role role;

    public ClientDTO() {
    }

    public ClientDTO(UUID id, String username, Client.role role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }
}
