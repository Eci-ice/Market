package com.example.maoliang.dto;

import lombok.Data;

@Data
public class GoodRecommendData {
    private int goodid;
    private String goodname;
    private double price;
    private String picture;
    private double weight;

    public GoodRecommendData(int goodid, String goodname, double price, String picture, double weight) {
        this.goodid = goodid;
        this.goodname = goodname;
        this.price = price;
        this.picture = picture;
        this.weight = weight;
    }
}