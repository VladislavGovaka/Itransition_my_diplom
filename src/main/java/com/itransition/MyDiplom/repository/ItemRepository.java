package com.itransition.MyDiplom.repository;

import com.itransition.MyDiplom.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
