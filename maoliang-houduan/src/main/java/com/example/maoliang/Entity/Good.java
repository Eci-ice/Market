package com.example.maoliang.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Good {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int goodid;
    private String goodname;
    private String description;
    private double price;
    private String picture;
    private int state;
    private int number;
    private String kind;
    private String subkind;
    private LocalDateTime createdate;
    private int owner;
    private double calorie;
    private String catkind;
    private String catweight;
    private String catage;

    private int buyingid;
    private int numbermax;
    private int islike;

    // Getters and Setters


    public int getGoodid() {
        return goodid;
    }

    public void setGoodid(int goodid) {
        this.goodid = goodid;
    }

    public String getGoodname() {
        return goodname;
    }

    public void setGoodname(String goodname) {
        this.goodname = goodname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getSubkind() {
        return subkind;
    }

    public void setSubkind(String subkind) {
        this.subkind = subkind;
    }

    public LocalDateTime getCreatedate() {
        return createdate;
    }

    public void setCreatedate(LocalDateTime createdate) {
        this.createdate = createdate;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public double getCalorie() {
        return calorie;
    }

    public void setCalorie(double calorie) {
        this.calorie = calorie;
    }

    public String getCatkind() {
        return catkind;
    }

    public void setCatkind(String catkind) {
        this.catkind = catkind;
    }

    public String getCatweight() {
        return catweight;
    }

    public void setCatweight(String catweight) {
        this.catweight = catweight;
    }

    public String getCatage() {
        return catage;
    }

    public void setCatage(String catage) {
        this.catage = catage;
    }

    public int getBuyingid() {
        return buyingid;
    }

    public void setBuyingid(int buyingid) {
        this.buyingid = buyingid;
    }

    public int getNumbermax() {
        return numbermax;
    }

    public void setNumbermax(int numbermax) {
        this.numbermax = numbermax;
    }

    public int getIslike() {
        return islike;
    }

    public void setIslike(int islike) {
        this.islike = islike;
    }
}
