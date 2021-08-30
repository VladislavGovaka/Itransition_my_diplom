package com.itransition.MyDiplom.service;


import com.itransition.MyDiplom.entity.CollectionDB;
import com.itransition.MyDiplom.entity.Item;
import com.itransition.MyDiplom.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    @Autowired
    private CollectionService collectionService;

    @Autowired
    private ItemRepository itemRepository;




    public void add(Item item, CollectionDB collection) {
        item.setData(new Date());
        item.setCollection(collection);
        itemRepository.save(item);

    }


    public List<Item> getListItem() {
        return itemRepository.findAll();
    }

    public List<Item> getListItemUser(Long id) {
        List<CollectionDB> collections = collectionService.findByUser_id(id);
        List<Item> itemList = new ArrayList<>();
        for (CollectionDB collection : collections) {
            for (Item item : collection.getItems()) {
                itemList.add(item);
            }
        }
        System.out.println("servis");
        for (Item item : itemList) {
            System.out.println(item.getName());
        }
        return itemList;
    }

    public void change(Item item) {
        if (findById(item.getId()) != null) {
            itemRepository.save(item);

        }
    }

    public void deleteItem(Long id) {
        if (findById(id) != null) {
            itemRepository.deleteById(id);
        }
    }

    public Item findById(Long id) {
        Optional<Item> item = itemRepository.findById(id);
        return item.orElse(null);
    }

    public List<Item> findAllByCollectionId(Long id) {
        return collectionService.getCollectionById(id).getItems();
    }

    public void changeItem(Long id, String name, String description, String url) {
        Item item = itemRepository.getById(id);
        item.setName(name);
        item.setDescription(description);
        if (url != null) {
            item.setUrl(url);
        }

        itemRepository.save(item);
    }

    private int search(String key, String txt) {


        char[] pattern = key.toCharArray();
        char[] text = txt.toCharArray();

        int patternSize = pattern.length;
        int textSize = text.length;

        int i = 0;

        while ((i + patternSize) <= textSize) {
            int j = 0;
            while (text[i + j] == pattern[j]) {
                j += 1;
                if (j >= patternSize)
                    return i;
            }
            i += 1;
        }
        return -1;
    }


    public List<Item> searchItems(String key) {

        List<Item> listItem = new ArrayList<>();

        List<Item> allItems = getListItem();

        for (Item item : allItems) {
            if (search(key, item.getDescription()) != -1 || search(key, item.getName()) != -1 || search(key, item.getCollection().getName()) != -1) {
                listItem.add(item);
            }

        }
        return listItem;

    }

    public List<Item> searchItemsByCollectionId(String key , Long id) {

        List<Item> listItem = new ArrayList<>();

        List<Item> allItems = findAllByCollectionId(id);

        for (Item item : allItems) {
            if (search(key, item.getDescription()) != -1 || search(key, item.getName()) != -1 || search(key, item.getCollection().getName()) != -1) {
                listItem.add(item);
            }

        }
        return listItem;

    }


}
