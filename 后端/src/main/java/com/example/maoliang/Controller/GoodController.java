package com.example.maoliang.Controller;


import com.example.maoliang.Controller.utils.Result;
import com.example.maoliang.Service.GoodService;
import com.example.maoliang.Entity.Good;
import com.example.maoliang.Entity.Usr;
import com.example.maoliang.dto.Likedata;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.maoliang.Controller.utils.Page.*;

@RestController
@RequestMapping( "/good/*")
public class GoodController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodController.class);
    @Autowired
    private GoodService goodService;
    @Autowired
    public HttpSession session;

    @RequestMapping("/modify-like-control")
    public Result modifyLike(@RequestBody Likedata likedata) {
//        int userId = currentUsr.getUserid();
//        if (currentUsr == null ) {
//            // 非法用户，跳转到错误页面
//            request.setAttribute("message", "非法用户");
//            return "error";
//        }
        // 根据请求参数执行相应的操作
        int goodId=likedata.getGoodId();
        int userId=likedata.getUserId();
        if ("0".equals(likedata.getIsCancel())) {
            // 收藏商品
            goodService.modifyLike(goodId, userId, 1); // 1 表示要添加收藏
            return new Result(SUCCESS_PAGE,"成功收藏","buyermain");
        } else {
            // 取消收藏
            goodService.modifyLike(goodId, userId, 0); // 0 表示要取消收藏
            return new Result(SUCCESS_PAGE,"成功取消收藏","buyermain");
        }
    }

    @RequestMapping("/buyer-cart-control")
    public Result showBuyerCart() {
        Usr currentUsr = (Usr) session.getAttribute("admin");
        if (currentUsr != null) {
            List<Good> goodsList = goodService.showBuyerAll(currentUsr.getUserid());
            return new Result(SUCCESS_PAGE,"成功取消收藏", goodsList);
        } else {
            return new Result(ERROR_PAGE,"请先登录", "login");
        }
    }

//good相关的servlet;由于该部分过多。可再分其他Controller

}