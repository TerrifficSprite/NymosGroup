//package com.alphabetas.nymosgroup.controller;
//
//import org.springframework.boot.web.servlet.error.ErrorController;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.http.HttpServletRequest;
//
//@Controller
//public class MyErrorController implements ErrorController {
//
//    @RequestMapping("/error")
//    @ResponseBody()
//    public String handleError(HttpServletRequest request, Model model){
//        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
//        model.addAttribute("http_code", status.toString());
//        return "error";
//    }
//}
