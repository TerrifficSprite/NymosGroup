package com.alphabetas.nymosgroup.controller;

import com.alphabetas.nymosgroup.model.Chat;
import com.alphabetas.nymosgroup.model.ClientChat;
import com.alphabetas.nymosgroup.model.ClientModel;
import com.alphabetas.nymosgroup.repo.*;
import com.alphabetas.nymosgroup.service.UserDetailsServiceImpl;
import com.alphabetas.nymosgroup.service.utils.EncodePasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@Controller
public class MainController {
    // SecurityContextHolder.getContext().getAuthentication();
    // Це тобі точно знадобиться!
    // Завтра треба буде переробити модель юзера, а далі розберешся

    Logger logger = Logger.getLogger("MainController");

    @Autowired
    UserRepo userRepo;

    @Autowired
    MessagesRepo messagesRepo;

    @Autowired
    ChatRepo chatRepo;

    @Autowired
    UserChatRepo userChatRepo;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @GetMapping
    public String home(Model model){
        String auth = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
//        System.out.println(SecurityContextHolder.getContext().getAuthentication().toString());
        if(auth.equals("[ROLE_ANONYMOUS]"))
            return "redirect:/login";
        model.addAttribute("auth", auth.equals("[ROLE_ANONYMOUS]"));
        return "chats";
    }

    @GetMapping("/registration")
    public String register(){
        return "registration";
    }

    @PostMapping("/registration")
    public String register(String username, String password, String password_confirm, Model model){
        if(userRepo.findByUsername(username) != null){
            System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
            model.addAttribute("exist", true);
            model.addAttribute("exist_msg", "Аккаунт з таким ім'ям користувача вже існує!");
            return "registration";
        }

        // Не працює підтвердження пароля. Завтра виправ!
        // Воно працює, ти просто дуже хотів спати і не правильно перевіряв
        if(!password.equals(password_confirm)){
            model.addAttribute("pass_not_confirmed", true);
            return "registration";
        }

        userRepo.saveAndFlush(new ClientModel(username, EncodePasswordUtil.encode(password), "ROLE_USER"));
        if(chatRepo.findByCode("default_chat") == null)
            chatRepo.save(new Chat("default_chat", "Чат для всіх!", "Пасхалка 1 знайдена!", -1));
        userChatRepo.save(new ClientChat("default_chat", username, "Чат для всіх!", "Пасхалка 1 знайдена!"));
        model.addAttribute("registred", true);
        authByUsername(username);
        return "redirect:/chats";
    }

    @GetMapping("/login")
    public String accessALl(Model model){
        if(userRepo.findByUsername("1") == null)
            userRepo.save(new ClientModel(1 + "", EncodePasswordUtil.encode(1 + ""), "ROLE_USER"));
        return "login";
    }

    @PostMapping("/login")
    public String login(String username, String password){
        if(userRepo.findByUsername(username) == null){
            return "redirect:/login?error";
        }
        ClientModel userModel = userRepo.findByUsername(username);

        if(!EncodePasswordUtil.equal(password, userModel.getPassword()) /* Якщо різні*/){
            return "redirect:/login?error";
        }
        // Якшо все добре
        authByUsername(username);

        return "redirect:/chats";
    }

    @GetMapping("/logout")
    public String logout(){
        SecurityContextHolder.clearContext();
        return "redirect:/login";
    }

    private void authByUsername(String username){
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        logger.info("login, POST" + userDetails);
    }

}