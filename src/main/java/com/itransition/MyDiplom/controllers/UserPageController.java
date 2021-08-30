package com.itransition.MyDiplom.controllers;


import com.itransition.MyDiplom.entity.CollectionDB;
import com.itransition.MyDiplom.entity.Item;
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
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserPageController {

    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private CollectionService collectionService;


    private List<CollectionDB> listCollections = new ArrayList<>();

    private boolean flagSearch = false;


    @GetMapping("/user")
    public String getUserPage(@ModelAttribute("item") Item item,
                              @ModelAttribute("collection") CollectionDB collectionDB, Model model) {                                                                                                                                        //модель это передача чегото в html, model.addAttribute("key" , user.getName)

        model.addAttribute("isAuthentication", userService.isAuthentication());

        if (userService.isAuthentication()) {

            model.addAttribute("user", userService.getAuthenticationUser());
            model.addAttribute("collections", collectionService.findByUser_id(userService.getAuthenticationUser().getId()));
            model.addAttribute("items" , itemService.getListItemUser(userService.getAuthenticationUser().getId()));
            model.addAttribute("searchColl", listCollections);
            model.addAttribute("flagSearch", flagSearch);

        }

        return "userPage";

    }

    @PostMapping(value = "/user", params = "action=addCollection")
    public String addCollection(@ModelAttribute("collection") @Valid CollectionDB collection, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "redirect:/user";
        }
        collectionService.add(collection);  //Изменить new User
        return "redirect:/user";
    }




    @PostMapping(value = "/user", params = "action=deleteCollection")
    public String deleteCollection(@RequestParam("id") Long id) {
        collectionService.deleteByid(id);
        return "redirect:/user";
    }

    @PostMapping(value = "/user", params = "action=searchColl")
    public String showColl(@RequestParam("key") String key,
                           @RequestParam("idUser") Long id) {


        if (key.equals("")) {
            flagSearch = false;

            return "redirect:/user";
        }
        flagSearch = true;

        listCollections = collectionService.searchCollections(key ,id );
        return "redirect:/user";
    }



}
