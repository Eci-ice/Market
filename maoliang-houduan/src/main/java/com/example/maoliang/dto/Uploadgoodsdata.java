package com.example.maoliang.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class Uploadgoodsdata {
    private String goodname;
    private String description;
    private double price;
    private int number;
    private String kind;
    private String subkind;

}
