package ro.tuc.ds2020.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rabbitmq.client.*;
import net.minidev.json.JSONObject;
import org.jobrunr.jobs.annotations.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.GreetingController;
import ro.tuc.ds2020.dtos.ConsumptionRecordDTO;
import ro.tuc.ds2020.dtos.NotificationDTO;
import ro.tuc.ds2020.dtos.builders.ConsumptionRecordBuilder;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.repositories.DeviceRepository;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;


@Component
public class RabbitmqService {

    ConnectionFactory connectionFactory = new ConnectionFactory();
    Connection connection;
    Channel channel;

    @Autowired
    ConsumptionRecordService consumptionRecordService;

    @Autowired
    GreetingController greetingController;

    public RabbitmqService(DeviceRepository deviceRepository) throws IOException, TimeoutException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        connectionFactory.setUri("amqps://rfaidbvx:H1Kh0UcL4nLAkFsEecTBiAC7Jbv40hBW@stingray.rmq.cloudamqp.com/rfaidbvx");
        connection = connectionFactory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare("consumption_simulator", false, false, false, null);

        this.deviceRepository = deviceRepository;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitmqService.class);
    private final DeviceRepository deviceRepository;

    @Scheduled(fixedRate = 5000)
    public void consumeQueueEntry() throws Exception {
//        System.exit(1);
        LOGGER.info("The sample job has begun.");
        GetResponse response = channel.basicGet("consumption_simulator", true);

        if (response == null) {
            System.out.println("no message");
        } else {
            System.out.println(response.getBody());

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setDateFormat("yyyy-MM-dd hh:mm:ss");
            Gson gson = gsonBuilder.create();
            JsonObject object = (JsonObject) JsonParser.parseString(new String(response.getBody(), StandardCharsets.UTF_8));
            ConsumptionRecordDTO emp = gson.fromJson(object, ConsumptionRecordDTO.class);

            List<ConsumptionRecordDTO> consumptionRecordDTOListByDay=consumptionRecordService.findConsumptionRecordsByDate(
                    emp.getDevice_id(),new Date(emp.getTimestamp().getTime())
            );

            Optional<Double> _hourCons = consumptionRecordDTOListByDay.stream().filter(
                            consumptionRecord ->
                                    (consumptionRecord.getTimestamp().getHours() == emp.getTimestamp().getHours()))
                    .map(ConsumptionRecordDTO::getConsumption)
                    .reduce(Double::sum);

            double hourCons=0;
            if(_hourCons.isPresent())
                hourCons=_hourCons.get();

            Optional<Device> device = deviceRepository.findById(emp.getDevice_id());

            if(hourCons+emp.getConsumption() > device.get().getMax_consumption()) {
                NotificationDTO notificationDTO = new NotificationDTO();
                notificationDTO.setUser_id(device.get().getUser().getId());
                notificationDTO.setMessage("Exceeded "+device.get().getMax_consumption()+"kWh "+"consumption for device: "+ device.get().getDescription());
                greetingController.greeting(notificationDTO);
            }

            consumptionRecordService.insert(emp);
        }
    }
}
