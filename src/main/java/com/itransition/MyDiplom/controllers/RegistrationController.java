package com.itransition.MyDiplom.controllers;


import com.itransition.MyDiplom.entity.User;
import com.itransition.MyDiplom.repository.UserRepository;
import com.itransition.MyDiplom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;


    @GetMapping("/registration")
    public String goToRegistration(@ModelAttribute("user") User user, Model model) {
        List<User> listUser = userService.findAll();
        return "registration";
    }

    @PostMapping("/registration")
    public String goToRegistr(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {

        System.out.println(user);
        if (bindingResult.hasErrors()) {
            return "/registration";
        }
        userService.add(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";

    }
}
