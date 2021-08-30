package com.itransition.MyDiplom.service;


import com.itransition.MyDiplom.entity.CollectionDB;
import com.itransition.MyDiplom.entity.Item;
import com.itransition.MyDiplom.entity.User;
import com.itransition.MyDiplom.repository.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CollectionService {

    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private UserService userService;

    public List<CollectionDB> findByAll() {
        return collectionRepository.findAll();
    }

    public void add(CollectionDB collectionDB) {
        collectionDB.setData(new Date());
        collectionDB.setUser(userService.getAuthenticationUser());
        collectionDB.setUrl("https://socialvk.ru/wp-content/uploads/avatarka-pustaya-vk_21.jpg");
        collectionRepository.save(collectionDB);

    }

    public void deleteByid(Long id) {
        collectionRepository.deleteById(id);
    }


    public void change(Long id, String name, String description, String url) {
        CollectionDB collection = collectionRepository.getById(id);
        collection.setName(name);
        collection.setDescription(description);
        if (url != null) {
            collection.setUrl(url);
        }

        collectionRepository.save(collection);
    }


    public List<CollectionDB> findByUser_id(Long id) {
        return collectionRepository.findByUser_id(id);
    }

    public List<CollectionDB> findAll() {
        return collectionRepository.findAll();

    }

    public CollectionDB getCollectionById(Long id) {
        return collectionRepository.getById(id);
    }

    public boolean isUserCollection(Long id) {
        User userAutification = userService.getAuthenticationUser();
        if (userAutification==null) {
            return false;
        }
        String userCollection = getCollectionById(id).getUser().getUsername();
        if (userAutification.getUsername().equals(userCollection)) {
            return true;
        } else {
            return false;
        }

    }


    private int searchColl(String key, String txt) {


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



    public List<CollectionDB> searchCollections(String key, Long idUser) {



        List<CollectionDB> listCol = new ArrayList<>();

        List<CollectionDB> allCol = findByUser_id(idUser);

        for (CollectionDB coll : allCol) {
            if (searchColl(key, coll.getDescription()) != -1 || searchColl(key, coll.getName()) != -1) {
                listCol.add(coll);
            }
        }
        System.out.println(listCol.size());

        return listCol;

    }



}
