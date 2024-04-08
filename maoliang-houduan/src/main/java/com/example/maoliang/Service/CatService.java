package com.example.maoliang.Service;

import com.example.maoliang.Repository.CatRepository;
import com.example.maoliang.Repository.GoodListRepository;
import com.example.maoliang.Repository.GoodRepository;
import com.example.maoliang.Entity.Good;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CatService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CatService.class);

    @Autowired
    private CatRepository catRepository;


}
