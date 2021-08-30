package com.itransition.MyDiplom.repository;

import com.itransition.MyDiplom.entity.CollectionDB;
import com.itransition.MyDiplom.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollectionRepository
        extends JpaRepository<CollectionDB, Long>
{

    List<CollectionDB> findAll();
    List<CollectionDB> findByUser_id(Long id);
    void deleteById(Integer id);


}
