package com.example.maoliang.dto;

import lombok.Data;

@Data
public class CatRequest {
    private int catid;
    private String catname;
    private String description;
    private double catweight;
    private int catstate;
    private int catage;
    private String catkind;
    private int owner;

    // 构造方法
    public CatRequest() {
    }


}
