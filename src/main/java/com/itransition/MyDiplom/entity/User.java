package com.itransition.MyDiplom.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.*;


@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;



   @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String username;



   @NotEmpty(message ="Email should not be empty")
    @Email
    private String email;



    @Size(min = 2, message = "Name should be between 2 and 30 characters")
    private String password;

    private Date dataRegistration;
    private String activationCode;
    private String url;

    @OneToMany(mappedBy = "user" , cascade = CascadeType.REMOVE)
    private List<CollectionDB>listCollections;


    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))     //джойнит таблизы
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {return true;}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    
    public void changeRole(Role role){                                  //set Role
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        setRoles(roles);
    }
    public Role getRole() {                                             //get Role
        String role = getRoles().toString();
        System.out.println(role);
        if (role.equals("[ADMIN]")) {
            return Role.ADMIN;
        }
        else return Role.USER;
    }

    public boolean isAdmin(){

        if (getRole().equals(Role.ADMIN)) {
            return true;
        }
        else
            return false;
    }

}