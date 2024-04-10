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
    private double catweight;
    private int catstate;
    private int catage;
    private String catkind;
    private int owner;

    public int getCatid() {
        return catid;
    }

    public void setCatid(int catid) {
        this.catid = catid;
    }

    public String getCatname() {
        return catname;
    }

    public void setCatname(String catname) {
        this.catname = catname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCatweight() {
        return catweight;
    }

    public void setCatweight(double catweight) {
        this.catweight = catweight;
    }

    public int getCatstate() {
        return catstate;
    }

    public void setCatstate(int catstate) {
        this.catstate = catstate;
    }

    public int getCatage() {
        return catage;
    }

    public void setCatage(int catage) {
        this.catage = catage;
    }

    public String getCatkind() {
        return catkind;
    }

    public void setCatkind(String catkind) {
        this.catkind = catkind;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }
}
