package com.itransition.MyDiplom.entity;


import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Entity
@Table(name = "items")
public class Item  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String name;
    private String description;
    private Date data;
    private String url;

    @ManyToOne
    @JoinColumn
    private CollectionDB collection;

    public String getFormatTime(){
        SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");
        return formater.format(getData());
    }

}
