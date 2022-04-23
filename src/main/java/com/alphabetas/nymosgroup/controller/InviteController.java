package com.alphabetas.nymosgroup.controller;

import com.alphabetas.nymosgroup.model.Chat;
import com.alphabetas.nymosgroup.model.ClientChat;
import com.alphabetas.nymosgroup.repo.ChatRepo;
import com.alphabetas.nymosgroup.repo.UserChatRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/invite")
public class InviteController {
    @Autowired
    UserChatRepo userChatRepo;

    @Autowired
    ChatRepo chatRepo;

    @GetMapping("/{invite}")
    public String invite(@PathVariable String invite){
        Chat chat = chatRepo.findByChatInvite("nymos-group.herokuapp.com/invite/" + invite);
        if(chat == null)
            return "redirect:/chats?not_exist";
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if(userChatRepo.findByClientAndChat(username, chat.getCode()) != null)
            return "redirect:/chats";
        userChatRepo.save(new ClientChat(chat.getCode(), username, chat.getChat_name(), chat.getChatInvite()));
        System.out.println("Вхід виконано: " + invite);

        chat.setNums_of_joins(chat.getNums_of_joins()+1);
        chatRepo.deleteById(chat.getId());
        chatRepo.save(chat);

        return "redirect:/chat/" + chat.getCode();
    }
}
