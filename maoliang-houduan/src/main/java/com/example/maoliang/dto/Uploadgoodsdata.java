package com.example.maoliang.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class Uploadgoodsdata {
    private String goodname;
    private String description;
    private double price;
    private int number;
    private String kind;
    private String subkind;
}
