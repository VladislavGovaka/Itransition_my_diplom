package com.itransition.MyDiplom.controllers;

import com.itransition.MyDiplom.entity.*;
import com.itransition.MyDiplom.service.CollectionService;
import com.itransition.MyDiplom.service.CommentService;
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
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ItemController {

    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private CollectionService collectionService;

    @Autowired
    private CommentService commentService;


    @Autowired
    private MyCloudinary cloudinary;


    private Long idCollection = 0l;
    private Long idItem = 0l;

    private boolean changeFlagCollection = false;
    private boolean changeFlagItem = false;

    private List<Item> listItem = new ArrayList<>();
    private boolean flagSearch = false;


    @GetMapping("/itemColl")
    public String goToItemColl(@ModelAttribute("item") Item item,
                               @ModelAttribute("collection") CollectionDB collectionDB, Model model) {//модель это передача чегото в html, model.addAttribute("key" , user.getName)

        model.addAttribute("collection", collectionService.getCollectionById(idCollection));
        model.addAttribute("user", userService.getAuthenticationUser());
        model.addAttribute("isAuthentication", userService.isAuthentication());
        model.addAttribute("isAdmin", userService.isAdmin());
        model.addAttribute("items", itemService.findAllByCollectionId(idCollection));
        model.addAttribute("isChangeCollection", changeFlagCollection);
        model.addAttribute("itemOfCollection", itemService.findById(idItem));
        model.addAttribute("isChangeItem", changeFlagItem);
        model.addAttribute("isUserCollection", collectionService.isUserCollection(idCollection));
        model.addAttribute("searchItem", listItem);
        model.addAttribute("flagSearch", flagSearch);
        model.addAttribute("allComments", commentService.findAllByCollection_Id(idCollection));

        List<Comments> c = commentService.findAllByCollection_Id(idCollection);
        System.out.println(c.size() + " sdvsvdsvds sdvsdvsdv");
        idItem = 0l;
        changeFlagItem= false;
        changeFlagCollection = false;




        return "itemColl";
    }

    @PostMapping(value = "/itemColl", params = "action=addItem")
    public String addItem(@ModelAttribute("item") @Valid Item item, BindingResult bindingResult,
                          @RequestParam("id_collection") Long id) {

        if (item.getName().equals("")) {
            return "redirect:/itemColl";
        }
        itemService.add(item, collectionService.getCollectionById(id));

        return "redirect:/itemColl";
    }






    @PostMapping(value = "/itemColl", params = "action=changeCollection")
    public String changeCollection(@RequestParam("name") String name,
                                   @RequestParam("description") String description,
                                   @RequestParam("id") Long id,
                                   @RequestParam("file") MultipartFile file) {
        System.out.println("CHANGE!");
        if (name.equals("")) {
            return "redirect:/itemColl";
        }
        CollectionDB collectionDB = collectionService.getCollectionById(id);
        String nameUser = collectionDB.getUser().getUsername();

        String url = cloudinary.upload(file, nameUser);
        System.out.println(url);
        collectionService.change(id, name, description, url);
        changeFlagCollection = false;

        return "redirect:/itemColl";
    }







    @PostMapping(value = "/itemColl", params = "action=changeItem")
    public String changeItem(@RequestParam("name") String name,
                             @RequestParam("description") String description,
                             @RequestParam("id") Long id,
                             @RequestParam("file") MultipartFile file) {

        if (name.equals("")) {
            return "redirect:/itemColl";
        }
        Item item = itemService.findById(id);
        String nameUser = item.getCollection().getUser().getUsername();
        String url = cloudinary.upload(file, nameUser);
        System.out.println(url);
        itemService.changeItem(id, name, description, url);
        changeFlagItem = false;
        idItem=id;


        return "redirect:/itemColl";
    }






    @PostMapping(value = "/itemColl", params = "action=showCollection")
    public String showCollection(@RequestParam("id") Long id) {
        idCollection = id;
        System.out.println(id);
        return "redirect:/itemColl";
    }





    @PostMapping(value = "/itemColl", params = "action=showItem")
    public String showItem(@RequestParam("id") Long id) {
        idItem = id;
        return "redirect:/itemColl";
    }





    @PostMapping(value = "/itemColl", params = "action=openChange")
    public String openChange() {
        changeFlagCollection = true;

        return "redirect:/itemColl";
    }




    @PostMapping(value = "/itemColl", params = "action=openChangeItem")
    public String openChangeItem(@RequestParam("id") Long id) {
        changeFlagItem = true;
        idItem = id;

        return "redirect:/itemColl";
    }






    @PostMapping(value = "/itemColl", params = "action=search")
    public String showItem(@RequestParam("searchKey") String key,
                            @RequestParam("idColl") Long id) {

        if (key.equals("")) {
            flagSearch = false;

            return "redirect:/itemColl";
        }
        flagSearch = true;


        listItem = itemService.searchItemsByCollectionId(key , id);
        return "redirect:/itemColl";
    }




    @PostMapping(value = "/itemColl", params = "action=deleteItem")
    public String deleteItem(@RequestParam("id") Long id) {
        itemService.deleteItem(id);
        return "redirect:/itemColl";
    }


    @PostMapping(value = "/itemColl", params = "action=addComment")
    public String addComment(@RequestParam("text") String text) {
        commentService.addComment(text , idCollection);
        return "redirect:/itemColl";
    }



}
