package ro.tuc.ds2020.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.ConsumptionRecordDTO;
import ro.tuc.ds2020.dtos.DeviceBindDTO;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.entities.ConsumptionRecord;
import ro.tuc.ds2020.services.ConsumptionRecordService;
import ro.tuc.ds2020.services.DeviceService;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin
@RequestMapping(value = "/consumptionRecord")
public class ConsumptionRecordController {

    private final ConsumptionRecordService consumptionRecordService;

    @Autowired
    public ConsumptionRecordController(ConsumptionRecordService consumptionRecordService) {
        this.consumptionRecordService = consumptionRecordService;
    }

    @GetMapping(value = "/device/{id}")
    public ResponseEntity<List<ConsumptionRecordDTO>> getConsumptionRecordsByDeviceId(@PathVariable("id") UUID id) {
        List<ConsumptionRecordDTO> dtos = consumptionRecordService.findConsumptionRecordByDeviceId(id);
        for (ConsumptionRecordDTO dto : dtos) {
            Link consumptionRecordLink = linkTo(methodOn(ConsumptionRecordController.class)
                    .getConsumptionRecord(dto.getId())).withRel("consumptionRecordDetails");
            //dto.add(consumptionRecordLink);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<UUID> insertConsumptionRecord(@Valid @RequestBody ConsumptionRecordDTO consumptionRecordDTO) {
        UUID consumptionRecId = consumptionRecordService.insert(consumptionRecordDTO);
        return new ResponseEntity<>(consumptionRecId, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ConsumptionRecordDTO> getConsumptionRecord(@PathVariable("id") UUID consumptionRecordId) {
        ConsumptionRecordDTO dto = consumptionRecordService.findConsumptionRecordById(consumptionRecordId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping(value = "/fromDay")
    public ResponseEntity<List<ConsumptionRecordDTO>> getConsumptionRecord(@RequestParam ("id") UUID deviceId,
                                                                           @RequestParam ("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date
                                                                     ) {
        List<ConsumptionRecordDTO> dto = consumptionRecordService.findConsumptionRecordsByDate(deviceId,date);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
