package com.alphabetas.nymosgroup.controller.websocket;

import com.alphabetas.nymosgroup.model.Messages;
import com.alphabetas.nymosgroup.repo.MessagesRepo;
import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import java.util.logging.Logger;

@Controller
public class WebSocketController {

    @Autowired
    MessagesRepo messagesRepo;

    @MessageMapping("/send_msg")
    @SendTo("/current_chat/chat")
    public Messages get_and_send(Messages msg){
        Logger logger = Logger.getLogger(getClass().getName());
        logger.info(msg.toString());
        Messages new_msg = new Messages(msg.getChat(), msg.getMessage(), msg.getAuthor());
        messagesRepo.save(new_msg);
        return new_msg;
    }
}
