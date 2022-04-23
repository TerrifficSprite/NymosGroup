package com.alphabetas.nymosgroup.controller;

import com.alphabetas.nymosgroup.model.Messages;
import com.alphabetas.nymosgroup.repo.ChatRepo;
import com.alphabetas.nymosgroup.repo.MessagesRepo;
import com.alphabetas.nymosgroup.repo.UserChatRepo;
import com.alphabetas.nymosgroup.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.ServletResponseMethodArgumentResolver;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/chat")
public class MessagesController {
    @Autowired
    UserRepo userRepo;

    @Autowired
    MessagesRepo messagesRepo;

    @Autowired
    UserChatRepo connectorRepo;

    @Autowired
    ChatRepo chatRepo;

    @GetMapping("/{chat}")
    public String chat_id(Model model, @PathVariable String chat){
        Logger logger = Logger.getLogger("MsgController.gerMapping(chat)");
        logger.info("model = " + model + ", chat = " + chat);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if(connectorRepo.findByClientAndChat(username, chat) == null)
            return "redirect:/chats?joined";
        List<Messages> list = messagesRepo.findAllByChat(chat);
        String invite = chatRepo.findByCode(chat).getChatInvite();
        model.addAttribute("invite", invite);
        model.addAttribute("list", list);
        model.addAttribute("user", username);
        model.addAttribute("chat", chat);
        model.addAttribute("members", chatRepo.findByCode(chat).getNums_of_joins()+1);
        model.addAttribute("name", connectorRepo.findByClientAndChat(username, chat).getChatName());
        return "chat";
    }

    @PostMapping("/{chat}")
    public String newMessage(String message, @PathVariable String chat){
        Logger logger = Logger.getLogger("MsgController.PostMapping(chat)");
        logger.info("chat = " + chat);
        if(message.trim().length() != 0){
            System.out.println("MessagesController.newMessage");
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            messagesRepo.saveAndFlush(new Messages(chat, message, username));
        }
        return ("redirect:/chat/" + chat);
    }


    private void send(){
    }
}
