package com.itransition.MyDiplom.controllers;

import com.itransition.MyDiplom.entity.CollectionDB;
import com.itransition.MyDiplom.entity.Item;
import com.itransition.MyDiplom.service.CollectionService;
import com.itransition.MyDiplom.service.ItemService;
import com.itransition.MyDiplom.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;


@Controller
public class MainController {
    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private CollectionService collectionService;

    private List<Item> listItem = new ArrayList<>();
    private boolean flagSearch = false;


    @GetMapping("/main")
    public String goToMain(@ModelAttribute("item") Item item,
                           @ModelAttribute("collection") CollectionDB collectionDB, Model model) {//модель это передача чегото в html, model.addAttribute("key" , user.getName)

        model.addAttribute("collections", collectionService.findAll());
        model.addAttribute("user", userService.getAuthenticationUser());
        model.addAttribute("isAuthentication", userService.isAuthentication());
        model.addAttribute("items", itemService.getListItem());
        model.addAttribute("searchItem", listItem);
        model.addAttribute("flagSearch", flagSearch);
        return "main";
    }




    @PostMapping(value = "/main", params = "action=search")
    public String showItem(@RequestParam("searchKey") String key) {

        if (key.equals("")) {
            flagSearch = false;

            return "redirect:/main";
        }
        flagSearch = true;
        System.out.printf("rtur");

        listItem = itemService.searchItems(key);
        return "redirect:/main";
    }


}
