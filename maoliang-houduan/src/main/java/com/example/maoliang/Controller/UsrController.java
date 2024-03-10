package com.example.maoliang.Controller;


import com.example.maoliang.Controller.utils.Result;
import com.example.maoliang.Entity.Good;
import com.example.maoliang.Entity.Usr;
import com.example.maoliang.Repository.UsrRepository;
import com.example.maoliang.Service.GoodService;
import com.example.maoliang.Service.UsrService;
import com.example.maoliang.dto.Errordata;
import com.example.maoliang.dto.Logindata;
import com.example.maoliang.dto.RegisterData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

import static com.example.maoliang.Controller.utils.Page.*;

@RestController
@RequestMapping( "/usr/*")
public class UsrController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UsrController.class);
    @Autowired
    private UsrService usrService;
    @Autowired
    private GoodService goodService;
    @Autowired
    private UsrRepository usrRepository;
    @Autowired
    public HttpSession session;//存储session数据

    @RequestMapping( "/login-control")
    public Result LoginControl(@RequestBody Logindata login//RequestBody用于传输数据
                               //Logindata自命,需要从前端传入的【所有】数据
    ) {
        String username=login.getUsername();
        String pwd=login.getPassword();
        try {
            Usr usr = usrService.search(username);
            if (usr != null && pwd.equals(usr.getPwd().trim())) {//数据库导出字符串有后置空格
                System.out.println(usr.getPower());
                session.setAttribute("admin", usr);
                //System.out.println(session.getAttribute("admin"));
                if (usr.getPower() == 0) {
                    // 买家权限，进入商品首页
                    List<Good> gList = goodService.showNowGoods();
                    System.out.println("hhh");
                    return new Result(BUYER_PAGE,"success", gList);
                } else if (usr.getPower() == 1) {
                    // 卖家权限，进入后台管理all商品页面
                    List<Good> gList = goodService.showAllGoods(usr.getUserid());
                    return new Result(SELLER_PAGE,"success", gList);
                } else {
                    Errordata ed=new Errordata();
                    // 处理其他权限或错误情况
                    return new Result(ERROR_PAGE,"未知权限","login");
                }
            } else {
                return new Result(ERROR_PAGE,"用户名错误或密码错误","login");
            }
        } catch (SQLException e) {
            LOGGER.error("Error handling login", e);
            return new Result(ERROR_PAGE,"登录出错，请稍后重试","login");
        }
    }
    @RequestMapping( "/register-control")
    public Result RegisterControl(@RequestBody RegisterData register) {
        String username = register.getUsername();
        try {
            if (usrService.searchName(username) == false){
                Usr newUsr = new Usr();
                newUsr.setUsername(username);
                newUsr.setPwd(register.getPassword());
                newUsr.setQuestion(register.getQuestion());
                newUsr.setAnswer(register.getAnswer());
                newUsr.setPower(register.getPower());
                //买家与卖家不同设定
                if(register.getPower()==0){
                    newUsr.setAddress(register.getAddress());
                    newUsr.setPhone(register.getPhone());
                }else{
                    newUsr.setAddress(null);
                    newUsr.setPhone(null);
                }
                //注册
                usrRepository.register(newUsr);
                return new Result(LOGIN_PAGE,"success",username);
            }else{
                LOGGER.error("Error handling register");
                return new Result(RENAME_PAGE,"rename","register");
            }
        } catch (SQLException e) {
            LOGGER.error("Error handling register", e);
            return new Result(ERROR_PAGE,"error","register");
        }
    }

    //user相关的servlet
}