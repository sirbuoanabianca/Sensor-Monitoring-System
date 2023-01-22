package ro.tuc.ds2020.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import ro.tuc.ds2020.entities.Client;

import java.util.UUID;

public class LoginDTO extends RepresentationModel<LoginDTO> {

    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String password;

    public LoginDTO() {
    }

    public LoginDTO(String username, String password) {

        this.username = username;
        this.password = password;

    }
}
