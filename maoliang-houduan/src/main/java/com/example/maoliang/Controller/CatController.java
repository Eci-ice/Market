package com.example.maoliang.Controller;


import com.example.maoliang.Controller.utils.Page;
import com.example.maoliang.Controller.utils.Result;
import com.example.maoliang.Entity.Good;
import com.example.maoliang.Entity.Usr;
import com.example.maoliang.Service.CatService;
import com.example.maoliang.Service.GoodService;
import com.example.maoliang.dto.Likedata;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static com.example.maoliang.Controller.utils.Page.*;

@RestController
@RequestMapping( "/cat/*")
public class CatController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CatController.class);
    @Autowired
    private CatService catService;
    @Autowired
    public HttpSession session;

    @RequestMapping("/show-cat/{id}")
    public Result Showcat(@PathVariable int id) {
        return new Result(null, null, null);
    }

    @RequestMapping("/show-recommend")
    public Result Showrecommend(@RequestBody int id) {
        return new Result(null, null, null);
    }

    @RequestMapping("/upload-cat")
    public Result Uploadcat(@RequestBody int id) {
        return new Result(null, null, null);
    }

    @RequestMapping("/modify-cat ")
    public Result Modifycat(@RequestBody int id) {
        return new Result(null, null, null);
    }


}