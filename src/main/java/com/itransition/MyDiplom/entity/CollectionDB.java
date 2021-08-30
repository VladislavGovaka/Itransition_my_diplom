package com.itransition.MyDiplom.entity;

import lombok.Data;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "Collections")
public class CollectionDB {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private Date data;
    private String url;


    @ManyToOne
    @JoinColumn
    private User user;

    @OneToMany(mappedBy = "collection" , cascade = CascadeType.REMOVE)
    private List<Item> items;


    @OneToMany(mappedBy = "collection" , cascade = CascadeType.REMOVE)
    private List<Comments> comments;



    public String getFormatTime(){
        SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");
        return formater.format(getData());
    }

}
