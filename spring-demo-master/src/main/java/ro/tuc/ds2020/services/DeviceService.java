package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.ClientDetailsDTO;
import ro.tuc.ds2020.dtos.DeviceBindDTO;
import ro.tuc.ds2020.dtos.builders.DeviceBuilder;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.entities.Client;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.repositories.ClientRepository;
import ro.tuc.ds2020.repositories.DeviceRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DeviceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceService.class);
    private final DeviceRepository deviceRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository, ClientRepository clientRepository) {
        this.deviceRepository = deviceRepository;
        this.clientRepository = clientRepository;
    }

    public List<DeviceDTO> findDevices() {
        List<Device> deviceList = deviceRepository.findAll();
        return deviceList.stream()
                .map(DeviceBuilder::toDeviceDTO)
                .collect(Collectors.toList());
    }

    public DeviceDTO findDeviceById(UUID id) {
        Optional<Device> deviceOptional = deviceRepository.findById(id);
        if (!deviceOptional.isPresent()) {
            LOGGER.error("Device with id {} was not found in db", id);
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + id);
        }
        return DeviceBuilder.toDeviceDTO(deviceOptional.get());
    }

    public UUID insert(DeviceDTO deviceDTO) {
        Device device = DeviceBuilder.toEntity(deviceDTO);
        device = deviceRepository.save(device);
        LOGGER.debug("Device with id {} was inserted in db", device.getId());
        return device.getId();
    }

    public UUID update(DeviceDTO deviceDTO) {
        Optional<Device> oldDevice = deviceRepository.findById(deviceDTO.getId());
        if (!oldDevice.isPresent()) {
            LOGGER.error("Device with id {} was not found in db", deviceDTO.getId());
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + deviceDTO.getId());
        }

            Device newdevice = DeviceBuilder.toEntity(deviceDTO);
            newdevice = deviceRepository.save(newdevice);
            LOGGER.debug("Device with id {} was inserted in db", newdevice.getId());
            return newdevice.getId();

    }

    public UUID delete(UUID id) {
        Optional<Device> searchedDevice = deviceRepository.findById(id);
        if (!searchedDevice.isPresent()) {
            LOGGER.error("Device with id {} was not found in db", id);
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + id);
        }

            deviceRepository.delete(searchedDevice.get());

        return id;
    }

    public UUID bindUser(DeviceBindDTO deviceBindDTO){
        Optional<Client> optionalClient = clientRepository.findById(deviceBindDTO.getUserId());
        if (!optionalClient.isPresent()) {
            LOGGER.error("Device with id {} was not found in db", optionalClient.get().getId());
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + optionalClient.get().getId());
        }

        UUID id = deviceBindDTO.getId();
        Optional<Device> deviceOptional = deviceRepository.findById(id);
        if (!deviceOptional.isPresent()) {
            LOGGER.error("Device with id {} was not found in db", id);
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + id);
        }

        deviceOptional.get().setUser(optionalClient.get());
        deviceRepository.save(deviceOptional.get());
        return id;
    }

    public List<DeviceDTO> findDevicesByUserId(UUID userid) {
        List<Device> deviceList = deviceRepository.findByUser_Id(userid);
        return deviceList.stream()
                .map(DeviceBuilder::toDeviceDTO)
                .collect(Collectors.toList());
    }


}
