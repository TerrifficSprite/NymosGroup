package com.alphabetas.nymosgroup.controller;

import com.alphabetas.nymosgroup.repo.ChatRepo;
import com.alphabetas.nymosgroup.repo.MessagesRepo;
import com.alphabetas.nymosgroup.repo.UserChatRepo;
import com.alphabetas.nymosgroup.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserRepo userRepo;

    @Autowired
    MessagesRepo messagesRepo;

    @Autowired
    ChatRepo chatRepo;

    @Autowired
    UserChatRepo userChatRepo;

    @GetMapping("")
    public String accessAdmin(Model model) {
        model.addAttribute("users", userRepo.findAll());
        model.addAttribute("chats", chatRepo.findAll());
        model.addAttribute("messages", messagesRepo.findAll());
        model.addAttribute("connector", userChatRepo.findAll());
        return "adminPage";
    }
}
