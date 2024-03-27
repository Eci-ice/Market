package com.example.maoliang.Entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BuyerCart {
    private int buyer; // 购买者ID，可以根据需要修改为String类型
    private List<CartItem> cartItems;
    public void setBuyer(int buyer) {
        this.buyer = buyer;
    }

}
