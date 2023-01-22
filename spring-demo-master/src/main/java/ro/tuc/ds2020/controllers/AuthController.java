package ro.tuc.ds2020.controllers;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.FailedAuthenticationException;
import ro.tuc.ds2020.dtos.LoginDTO;
import ro.tuc.ds2020.dtos.LoginResponseDTO;
import ro.tuc.ds2020.entities.Client;
import ro.tuc.ds2020.services.ClientService;

import javax.validation.Valid;
import java.util.Objects;
import java.util.UUID;


@RestController
@CrossOrigin
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class AuthController {
    private final ClientService clientService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientService.class);


    @PostMapping(value = "/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginDTO loginDTO) {
        Client client=clientService.findClientByUsername(loginDTO.getUsername());

        if(!Objects.equals(client.getUsername(), loginDTO.getUsername()))
         throw new FailedAuthenticationException("failed username");
        if(!Objects.equals(client.getPassword(), loginDTO.getPassword()))
            throw new FailedAuthenticationException("failed password");

        LoginResponseDTO dto=new LoginResponseDTO(client.getUserRole().toString(),client.getId(),client.getUsername());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
