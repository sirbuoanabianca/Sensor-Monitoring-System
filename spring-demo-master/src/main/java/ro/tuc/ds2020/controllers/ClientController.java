package ro.tuc.ds2020.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.ClientDTO;
import ro.tuc.ds2020.dtos.ClientDetailsDTO;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.services.ClientService;
import ro.tuc.ds2020.services.DeviceService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin
@RequestMapping(value = "/client")


public class ClientController {

    private final ClientService clientService;
    private final DeviceService deviceService;


    @Autowired
    public ClientController(ClientService clientService,DeviceService deviceService) {
        this.clientService = clientService;
        this.deviceService = deviceService;
    }


    @GetMapping()
    public ResponseEntity<List<ClientDTO>> getClients() {
        List<ClientDTO> dtos = clientService.findClients();
        for (ClientDTO dto : dtos) {
            Link clientLink = linkTo(methodOn(ClientController.class)
                    .getClient(dto.getId())).withRel("clientDetails");
            dto.add(clientLink);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<UUID> insertClient(@Valid @RequestBody ClientDetailsDTO clientDetailsDTO) {
        UUID clientID = clientService.insert(clientDetailsDTO);
        return new ResponseEntity<>(clientID, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ClientDetailsDTO> getClient(@PathVariable("id") UUID clientId) {
        ClientDetailsDTO dto = clientService.findClientById(clientId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<UUID> updateClient(@RequestBody ClientDetailsDTO clientDetailsDTO) {
        UUID clientID = clientService.update(clientDetailsDTO);
        return new ResponseEntity<>(clientID, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<UUID> deleteClient(@PathVariable("id") UUID clientId) {
        UUID clientID = clientService.delete(clientId);
        return new ResponseEntity<>(clientID, HttpStatus.OK);
    }

    @GetMapping(value="/getAlldevices/{id}")
    public ResponseEntity<List<DeviceDTO>> getDevicesByUserId(@PathVariable("id") UUID clientid) {
        List<DeviceDTO> dtos = deviceService.findDevicesByUserId(clientid);
        for (DeviceDTO dto : dtos) {
            Link deviceLink = linkTo(methodOn(DeviceController.class)
                    .getDevice(dto.getId())).withRel("deviceDetails");
            dto.add(deviceLink);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

}
