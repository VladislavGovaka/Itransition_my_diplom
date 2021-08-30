package com.itransition.MyDiplom.repository;

import com.itransition.MyDiplom.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {                 //обертка с методами crud и работа с БД!!!


    User findByUsername(String username);
    Optional<User> findById(Long id);
    void deleteById(Long id);


}
