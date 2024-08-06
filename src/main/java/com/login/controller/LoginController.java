package com.login.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.login.model.Users;
import com.login.service.UserService;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("error", ""); // Ensure error attribute is initialized
        return "login"; // Renders login.html
    }

    @PostMapping("/login_check")
    public String loginSubmit(@RequestParam String uid,
                              @RequestParam String pass,
                              @RequestParam String type,
                              Model model) {
        System.out.println("inside loginSubmit");
        System.out.println("Printing values: "+uid+","+pass+";"+type);

        if (!userService.isUserUnblocked(uid)) {
            model.addAttribute("error", "Sorry, you are not authorized to login. Please contact your manager.");
            return "login"; // Return to login page with error message
        }

        if (userService.authenticate(uid, pass, type)) {
            Users user = userService.findByUsername(uid);
            model.addAttribute("username", user.getUsername());
            model.addAttribute("role", user.getRole());
            
            //return "redirect:/menu"; // Redirect to menu page
            String type1 = type + "menu";
            return type1;
        } else {
            model.addAttribute("error", "OOPS !!! WRONG CREDENTIALS 😞");
            return "login"; // Return to login page with error message
        }
    }

    @GetMapping("/menu")
    public String menu() {
        return "menu"; // Renders menu.html
    }
    
 
}


