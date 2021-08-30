package com.itransition.MyDiplom.repository;

import com.itransition.MyDiplom.entity.CollectionDB;
import com.itransition.MyDiplom.entity.Comments;
import com.itransition.MyDiplom.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments, Long> {

    public List<Comments> findAllByCollection_Id(Long id);

}
