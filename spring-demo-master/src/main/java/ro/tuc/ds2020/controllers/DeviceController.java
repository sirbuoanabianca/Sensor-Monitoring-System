package ro.tuc.ds2020.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.DeviceBindDTO;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.services.DeviceService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin
@RequestMapping(value = "/device")
public class DeviceController {

    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping()
    public ResponseEntity<List<DeviceDTO>> getDevices() {
        List<DeviceDTO> dtos = deviceService.findDevices();
        for (DeviceDTO dto : dtos) {
            Link deviceLink = linkTo(methodOn(DeviceController.class)
                    .getDevice(dto.getId())).withRel("deviceDetails");
            dto.add(deviceLink);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<UUID> insertDevice(@Valid @RequestBody DeviceDTO deviceDTO) {
        UUID deviceID = deviceService.insert(deviceDTO);
        return new ResponseEntity<>(deviceID, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DeviceDTO> getDevice(@PathVariable("id") UUID deviceId) {
        DeviceDTO dto = deviceService.findDeviceById(deviceId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<UUID> UpdateDevice(@RequestBody DeviceDTO deviceDTO) {
        UUID deviceId = deviceService.update(deviceDTO);
        return new ResponseEntity<>(deviceId, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<UUID> deleteDevice(@PathVariable("id") UUID deviceId) {
        UUID deviceID = deviceService.delete(deviceId);
        return new ResponseEntity<>(deviceID, HttpStatus.OK);
    }

    @PostMapping(value = "/bind")
    public ResponseEntity<UUID> bindDeviceToUser(@RequestBody DeviceBindDTO deviceBindDTO){
        return new ResponseEntity<>(deviceService.bindUser(deviceBindDTO), HttpStatus.OK);
    }

    @GetMapping(value = "/client/{id}")
    public ResponseEntity<List<DeviceDTO>> getDeviceByUserId(@PathVariable("id") UUID clientId) {
        List<DeviceDTO> dto = deviceService.findDevicesByUserId(clientId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


}
