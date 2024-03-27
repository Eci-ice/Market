package com.example.maoliang.Entity;
import lombok.Data;

@Data
public class CartItem {
    private int buyingId;
    private int goodId;
    private int number;
    private int isLike;
    public void setBuyingId(int buyingId) {
        this.buyingId = buyingId;
    }

    public void setGoodId(int goodId) {
        this.goodId = goodId;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setIsLike(int isLike) {
        this.isLike = isLike;
    }
}
