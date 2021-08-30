package com.itransition.MyDiplom.service;

import com.itransition.MyDiplom.entity.CollectionDB;
import com.itransition.MyDiplom.entity.Comments;
import com.itransition.MyDiplom.repository.CollectionRepository;
import com.itransition.MyDiplom.repository.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CollectionService collectionService;





    public void addComment(String text , Long idCollection) {

       CollectionDB collectionDB = collectionService.getCollectionById(idCollection);


        Comments comments = new Comments();
        comments.setTextComment(text);
        comments.setUserName(userService.getAuthenticationUser().getUsername());
        comments.setDataComment(new Date());
        comments.setCollection(collectionDB);

        commentsRepository.save(comments);
    }

    public List<Comments> findAllByCollection_Id(Long id) {

        System.out.println(commentsRepository.findAllByCollection_Id(id).size() + "saf");
        System.out.println(id + " id");
        return commentsRepository.findAllByCollection_Id(id);
    }


}
