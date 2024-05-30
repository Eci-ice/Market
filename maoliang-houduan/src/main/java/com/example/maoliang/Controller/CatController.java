package com.example.maoliang.Controller;


import com.example.maoliang.Controller.utils.Page;
import com.example.maoliang.Controller.utils.Result;
import com.example.maoliang.Entity.Cat;
import com.example.maoliang.Entity.Good;
import com.example.maoliang.Entity.Usr;
import com.example.maoliang.Service.CatService;
import com.example.maoliang.Service.GoodService;
import com.example.maoliang.dto.CatRequest;
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

    @RequestMapping("/show-cat")
    public Result Showcat(@RequestParam int ownerid) {
        try {
            List<Cat> catList = catService.getCatsByOwnerId(ownerid);
            return new Result("success", "获取猫信息成功", catList);
        } catch (Exception e) {
            LOGGER.error("获取猫信息出错", e);
            return new Result("error", "获取猫信息出错，请稍后重试", null);
        }
    }

    @RequestMapping("/show-recommend/{id}")
    public Result Showrecommend(@PathVariable int id) {
        return new Result(null, null, catService.showRecommend(id));
    }

    public Result uploadCat(@RequestBody CatRequest catRequest) {
        try {
            catService.addCat(catRequest.getCatid(), catRequest.getCatname(), catRequest.getDescription(),
                    catRequest.getCatweight(), catRequest.getCatstate(), catRequest.getCatage(),
                    catRequest.getCatkind(), catRequest.getOwner());
            return new Result("success", "猫咪信息上传成功", null);
        } catch (Exception e) {
            LOGGER.error("上传猫咪信息出错", e);
            return new Result("error", "上传猫咪信息出错，请稍后重试", null);
        }
    }



    @RequestMapping("/modify-cat")
    public Result Modifycat(@RequestParam int id, @RequestParam double catweight, @RequestParam int catstate, @RequestParam int catage) {
        try {
            // 调用 CatService 中的方法来修改猫的信息
            catService.updateCatInformation(id, catweight, catstate, catage);

            return new Result("success", "猫咪信息修改成功", null);
        } catch (Exception e) {
            LOGGER.error("修改猫咪信息出错", e);
            return new Result("error", "修改猫咪信息出错，请稍后重试", null);
        }
    }



}