package com.example.maoliang.Controller;

import com.example.maoliang.Controller.utils.Result;
import com.example.maoliang.dto.Logindata;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.example.maoliang.Controller.utils.Page.SELLER_PAGE;

@RestController
public class TestController {

    @RequestMapping("/main-control-test")
    public String login() {
        return "redirect:/";
    }

    @RequestMapping("/login-control-test")
    public Result register(@RequestBody Logindata login,HttpServletRequest request) {
        System.out.println("111");
        System.out.println(login.getUsername());
        System.out.println(login.getPassword());
        System.out.println(request);
        System.out.println(request.getParameter("username"));
        HttpSession session = request.getSession();
        session.setAttribute("username", "username");
        HttpSession session2 = request.getSession();
        System.out.println(session2.getAttribute("username"));
        return new Result(SELLER_PAGE,"success",null);
    }
}
