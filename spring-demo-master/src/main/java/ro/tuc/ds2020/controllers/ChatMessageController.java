package ro.tuc.ds2020.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import ro.tuc.ds2020.dtos.ChatMessageDTO;
import ro.tuc.ds2020.dtos.NotificationDTO;

@Controller
public class ChatMessageController {

    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/chat")
    @SendToUser
    public void send(Message<ChatMessageDTO> message) throws Exception {
        switch(message.getPayload().getAction()){
           case "requestAdmin":
               this.template.convertAndSend("/topic/adminRequest",
                       new ChatMessageDTO(
                       "/topic/adminRequest",
                        message.getPayload().getSender(),
                       null,"Your support has been requested by the user: "+message.getPayload().getSender()
                       ));

               System.out.println("request admin");
               break;
           case "sendMessage":
               this.template.convertAndSend("/topic/message/"+message.getPayload().getReceiver(),
                       new ChatMessageDTO(
                               "/topic/sendMessage",
                               message.getPayload().getSender(),
                               message.getPayload().getReceiver(),
                               message.getPayload().getMessage()
                       ));
               System.out.println("send message to " + message.getPayload().getReceiver());
               break;
        }

    }

}