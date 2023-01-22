package ro.tuc.ds2020.dtos.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

public class LoginResponseDTO extends RepresentationModel<LoginResponseDTO> {

    @Getter
    @Setter
    private String role;
    @Getter
    @Setter
    private UUID id;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(String role, UUID id) {

        this.role = role;
        this.id = id;

    }
}
