package com.example.maoliang.Entity;

import javax.persistence.Entity;

@Entity
public class Address {
    private int owner;

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String address;
}
