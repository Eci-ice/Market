package com.example.maoliang.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Aftersale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int aftersaleid;
    private int userid;
    private int goodid;
    private String title;
    private String description;
    private String imagepath;
    private String serviceresult;

    // 构造函数，getters 和 setters
    public Aftersale() {}

    public Aftersale(int aftersaleid, int userid, int goodid, String title, String description, String imagepath, String serviceresult) {
        this.aftersaleid = aftersaleid;
        this.userid = userid;
        this.goodid = goodid;
        this.title = title;
        this.description = description;
        this.imagepath = imagepath;
        this.serviceresult = serviceresult;
    }

    public int getAftersaleid() {
        return aftersaleid;
    }

    public void setAftersaleid(int aftersaleid) {
        this.aftersaleid = aftersaleid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getGoodid() {
        return goodid;
    }

    public void setGoodid(int goodid) {
        this.goodid = goodid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public String getServiceresult() {
        return serviceresult;
    }

    public void setServiceresult(String serviceresult) {
        this.serviceresult = serviceresult;
    }
}
