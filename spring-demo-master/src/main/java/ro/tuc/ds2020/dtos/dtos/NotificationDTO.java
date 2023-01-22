package ro.tuc.ds2020.dtos.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

public class NotificationDTO extends RepresentationModel<NotificationDTO> {

    @Getter
    @Setter
    private UUID user_id;
    @Getter
    @Setter
    private String message;


    public NotificationDTO() {
    }

}
