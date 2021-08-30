package com.itransition.MyDiplom.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "comments")
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String textComment;
    private String userName;
    private Date dataComment;


    @ManyToOne
    @JoinColumn
    private CollectionDB collection;




}
