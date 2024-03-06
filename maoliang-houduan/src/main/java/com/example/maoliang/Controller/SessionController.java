package com.example.maoliang.Controller;

import com.example.maoliang.Controller.utils.Result;
import com.example.maoliang.Entity.Usr;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
}
