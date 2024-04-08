package com.example.maoliang.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Cat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int catid;
    private String catname;
    private String description;
    private String catweight;
    private String catstate;
    private String catage;
    private String catkind;
    private int owner;

}
