package ro.tuc.ds2020.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;
import ro.tuc.ds2020.dtos.NotificationDTO;

@Controller
public class GreetingController {

    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public NotificationDTO greeting(NotificationDTO notificationDTO) throws Exception {
        //Thread.sleep(1000); // simulated delay
        sendNotif(notificationDTO);
        return notificationDTO;
    }

    public void sendNotif(NotificationDTO notificationDTO){
        this.template.convertAndSend("/topic/greetings", notificationDTO);
    }

}