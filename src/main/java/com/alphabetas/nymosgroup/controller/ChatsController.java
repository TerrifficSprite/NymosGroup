package com.alphabetas.nymosgroup.controller;

import com.alphabetas.nymosgroup.model.Chat;
import com.alphabetas.nymosgroup.model.ClientChat;
import com.alphabetas.nymosgroup.repo.ChatRepo;
import com.alphabetas.nymosgroup.repo.UserChatRepo;
import com.alphabetas.nymosgroup.service.utils.CreateCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/chats")
public class ChatsController {
    @Autowired
    ChatRepo chatRepo;

    @Autowired
    UserChatRepo userChatRepo;

    @GetMapping("/")
    public String chats(){
        return "error";
    }

    @GetMapping("")
    public String chats(Model model){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<ClientChat> clientChatList = userChatRepo.findAllByClient(username);
        model.addAttribute("user", username);
        model.addAttribute("chats", clientChatList);
        model.addAttribute("code", CreateCodeUtil.createInviteCode());
        return "chats";
    }

    @GetMapping("/create")
    public String createChat(Model model){
        return "create";
    }

    @PostMapping("/create")
    public String createChat(String chatName, String chatInviteCode_hidden, Model model){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String chatCode = CreateCodeUtil.createCode(10);
        if(chatName.trim().length() == 0){
            model.addAttribute("empty", true);
            model.addAttribute("code", CreateCodeUtil.createInviteCode());
            return "redirect:/chats?empty";
        }
        System.out.println("Ссилка на чат - " + chatInviteCode_hidden);
        System.out.println("Ім'я чату - " + chatName);
        System.out.println("Код чату - " + chatCode);
        if(chatRepo.findByCode(chatCode) == null &&
                chatRepo.findByChatInvite(chatInviteCode_hidden) == null){
            chatRepo.save(new Chat(chatCode, chatName, chatInviteCode_hidden, 0));
            if(userChatRepo.findByClientAndChat(username, chatCode) == null)
                userChatRepo.save(new ClientChat(chatCode, username, chatName, chatInviteCode_hidden));
        }
        return ("redirect:/chats");
    }

    @PostMapping("/join")
    public String joinChat(String invite){
        invite = invite.trim();
        if(invite.length() < 6)
            return "redirect:/chats?small";
        StringBuilder builder = new StringBuilder();
        for (int i = invite.length()-6; i < invite.length(); i++) {
            builder.append(invite.charAt(i));
        }
        Logger logger = Logger.getLogger(getClass().getName());
        logger.info(builder.toString());

        return "redirect:/invite/" + builder;
    }
}
