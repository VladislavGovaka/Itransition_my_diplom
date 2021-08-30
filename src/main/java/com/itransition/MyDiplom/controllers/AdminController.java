package com.itransition.MyDiplom.controllers;


import com.itransition.MyDiplom.entity.CollectionDB;
import com.itransition.MyDiplom.entity.Item;
import com.itransition.MyDiplom.entity.Role;
import com.itransition.MyDiplom.entity.User;
import com.itransition.MyDiplom.service.CollectionService;
import com.itransition.MyDiplom.service.ItemService;
import com.itransition.MyDiplom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Collections;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private CollectionService collectionService;


    @GetMapping("/admin")
    public String getAdminPage(Model model) {                                                                                                                                        //модель это передача чегото в html, model.addAttribute("key" , user.getName)
        model.addAttribute("isUsers", userService.findAll());

        return "admin";
    }

    @PostMapping(value = "/admin", params = "action=setAdmin")
    public String setRole(@RequestParam("id") Long id) {
        userService.changeRole(id);
        return "redirect:/admin";
    }

    @PostMapping(value = "/admin", params = "action=deleteUser")
    public String deleteUser(@RequestParam("id") Long id) {
        if (userService.getAuthenticationUser().getId() == id) {
            return "redirect:/admin";
        } else
            userService.deleteById(id);

        return "redirect:/admin";
    }

}
