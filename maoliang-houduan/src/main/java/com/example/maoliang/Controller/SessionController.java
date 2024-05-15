package com.example.maoliang.Controller;

import com.example.maoliang.Controller.utils.Result;
import com.example.maoliang.Entity.Good;
import com.example.maoliang.Entity.Usr;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SessionController {

    @Autowired
    public HttpSession session;

    @RequestMapping("/now-usr")
    @ResponseBody
    public Usr getUsrFromSession() {//向前端返回admin
        // 从 session 中获取用户名
        Usr usr = (Usr) session.getAttribute("admin");

        if (usr != null) {
            return usr;
        } else {
            return null;
        }
    }


    @RequestMapping( "/logout-control")
    public void LoginoutControl(){//向前端返回admin
        // 从 session 中获取用户名
        session.invalidate();
    }

    @RequestMapping("/now-TradeNo")
    public String TradeNoStatus() {
        String tradeNo = (String) session.getAttribute("now-TradeNo");
        if (tradeNo != null) {
            return tradeNo;
        } else {
            return null;
        }
    }

    @RequestMapping("/delete-now-TradeNo")
    public void deleteTradeNoStatus() {
        session.removeAttribute("now-TradeNo");
    }

    @RequestMapping("/searchList")
    @ResponseBody
    public List<Good> getSearchListFromSession() {//向前端返回admin
        // 从 session 中获取用户名
        List<Good> sL = (List<Good>) session.getAttribute("sL");

        if (sL != null) {
            return sL;
        } else {
            return null;
        }
    }

    @RequestMapping("/now-good")
    @ResponseBody
    public Good getGoodFromSession() {//向前端返回admin
        // 从 session 中获取用户名
        Good good = (Good) session.getAttribute("now-good");

        if (good != null) {
            return good;
        } else {
            return null;
        }
    }


}
