package ro.tuc.ds2020.services;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.ConsumptionRecordDTO;
import ro.tuc.ds2020.dtos.DeviceBindDTO;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.builders.ConsumptionRecordBuilder;
import ro.tuc.ds2020.dtos.builders.DeviceBuilder;
import ro.tuc.ds2020.entities.Client;
import ro.tuc.ds2020.entities.ConsumptionRecord;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.repositories.ClientRepository;
import ro.tuc.ds2020.repositories.ConsumptionRecordRepository;
import ro.tuc.ds2020.repositories.DeviceRepository;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConsumptionRecordService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumptionRecordService.class);
    private final ConsumptionRecordRepository consumptionRecordRepository;

    private final DeviceRepository deviceRepository;



    public List<ConsumptionRecordDTO> findConsumptionRecordByDeviceId(UUID id) {
        List<ConsumptionRecord> consumptionRecordList = consumptionRecordRepository.findByDevice_Id(id);
        return consumptionRecordList.stream()
                .map(ConsumptionRecordBuilder::toConsumptionRecordDTO)
                .collect(Collectors.toList());
    }

    public ConsumptionRecordDTO findConsumptionRecordById(UUID id) {
        Optional<ConsumptionRecord> consumptionRecordOptional = consumptionRecordRepository.findById(id);
        if (!consumptionRecordOptional.isPresent()) {
            LOGGER.error("Consumption record with id {} was not found in db", id);
            throw new ResourceNotFoundException(ConsumptionRecord.class.getSimpleName() + " with id: " + id);
        }
        return ConsumptionRecordBuilder.toConsumptionRecordDTO(consumptionRecordOptional.get());
    }

    public List<ConsumptionRecordDTO> findConsumptionRecordsByDate(UUID id, Date date) {
        List<ConsumptionRecord> consumptionRecordList  = consumptionRecordRepository.findByDevice_Id(id);
//        if (!consumptionRecordOptional.isPresent()) {
//            LOGGER.error("Consumption record with id {} was not found in db", id);
//            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + id);
//        }

        return consumptionRecordList.stream().filter(
                consumptionRecord ->
                    (consumptionRecord.getTimestamp().toLocalDateTime().toLocalDate()
                            .equals(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())))
                            .map(ConsumptionRecordBuilder::toConsumptionRecordDTO)
                .collect(Collectors.toList());
    }

    public UUID insert(ConsumptionRecordDTO consumptionRecordDTO) {
        ConsumptionRecord consumptionRecord = ConsumptionRecordBuilder.toEntity(consumptionRecordDTO);

        Optional<Device> deviceOptional = deviceRepository.findById(consumptionRecordDTO.getDevice_id());
        if (!deviceOptional.isPresent()) {
            LOGGER.error("Device with id {} was not found in db", consumptionRecordDTO.getDevice_id());
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + consumptionRecordDTO.getDevice_id());
        }

        consumptionRecord.setDevice(deviceOptional.get());
        consumptionRecord = consumptionRecordRepository.save(consumptionRecord);
        LOGGER.debug("Consumption Record with id {} was inserted in db", consumptionRecord.getId());
        return consumptionRecord.getId();
    }

//    public UUID update(DeviceDTO deviceDTO) {
//        Optional<Device> oldDevice = deviceRepository.findById(deviceDTO.getId());
//        if (!oldDevice.isPresent()) {
//            LOGGER.error("Device with id {} was not found in db", deviceDTO.getId());
//            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + deviceDTO.getId());
//        }
//
//            Device newdevice = DeviceBuilder.toEntity(deviceDTO);
//            newdevice = deviceRepository.save(newdevice);
//            LOGGER.debug("Device with id {} was inserted in db", newdevice.getId());
//            return newdevice.getId();
//
//    }
//
//    public UUID delete(UUID id) {
//        Optional<Device> searchedDevice = deviceRepository.findById(id);
//        if (!searchedDevice.isPresent()) {
//            LOGGER.error("Device with id {} was not found in db", id);
//            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + id);
//        }
//
//            deviceRepository.delete(searchedDevice.get());
//
//        return id;
//    }

}
