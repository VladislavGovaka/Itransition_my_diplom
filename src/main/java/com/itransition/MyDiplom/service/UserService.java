package com.itransition.MyDiplom.service;

import com.itransition.MyDiplom.entity.CollectionDB;
import com.itransition.MyDiplom.entity.Role;
import com.itransition.MyDiplom.entity.User;
import com.itransition.MyDiplom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements UserDetailsService {              //расширяем функционал интерфейса UserRepository

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { //авторегистрация по имени
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public List<User> findAll(){  //показать всех
        return userRepository.findAll();
    }

    public User findById(long id){

        Optional<User> userOptional =  userRepository.findById(id);
        return userOptional.orElse(null);
    }

    public User findByName(String name){
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByUsername(name));
        return userOptional.orElse(null);
    }

    public boolean add(User user) {
        User userFromDb = userRepository.findByUsername(user.getUsername());    //достаем с БД юзера по имени
        if (userFromDb != null) {                                               //проверка на наличие в БД юзера
            return false;
        }

        if (user.getUsername().equals("Admin")) {
            user.setRoles(Collections.singleton(Role.ADMIN));
        }
        else {
            user.setRoles(Collections.singleton(Role.USER));
        }
                                                                                                //ставит роль
        user.setDataRegistration(new Date());
        user.setActivationCode(UUID.randomUUID().toString());                   //ключ для расшифровки пороля
        user.setPassword(passwordEncoder.encode(user.getPassword()));           //шифруем пороль
        userRepository.save(user);
        return true;
    }

    public boolean deleteById(long id){
        User userFromDb = findById(id);
        if (userFromDb != null) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean isAuthentication(){
        User user = getAuthenticationUser();

        if (user == null) {
            return false;
        }
        return true;
    }

    public boolean isAdmin(){
        if(isAuthentication()) {
            String role = "[" + Role.ADMIN + "]";
            String roleAuthentication = getAuthenticationUser().getRoles().toString();
            return role.equals(roleAuthentication);
        }
        return false;
    }

    public User getAuthenticationUser(){                        //получаю юзера по сессии
        User user = null;
        try{
            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        catch (Exception e){
            return null;
        }
        return user;
    }

    public boolean save(User user){
        try {
            if(findByName(user.getUsername())!= null)
                userRepository.save(user);
            return true;
        }
        catch (Exception e){
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
    public void changeRole(Long id) {
        User user = findById(id);
        if (user.getRole()==Role.ADMIN) {
            user.changeRole(Role.USER);
        }
        else user.changeRole(Role.ADMIN);
        userRepository.save(user);
    }




}
