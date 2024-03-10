package com.example.maoliang.Controller;

import com.example.maoliang.Controller.utils.Result;
import com.example.maoliang.Entity.Usr;
import com.example.maoliang.Repository.UsrRepository;
import com.example.maoliang.Service.GoodService;
import com.example.maoliang.Service.UsrService;
import com.example.maoliang.dto.AnswerData;
import com.example.maoliang.dto.ForgetPwdData;
import com.example.maoliang.dto.Logindata;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

import static com.example.maoliang.Controller.utils.Page.*;

@RestController
@RequestMapping( "/forget/*")
public class PasswordController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UsrController.class);
    @Autowired
    private UsrService usrService;
    @Autowired
    private GoodService goodService;
    @Autowired
    private UsrRepository usrRepository;
    @Autowired
    public HttpSession session;//存储session数据

    @RequestMapping( "/forgetPwd-control")
    public Result ForgetControl(@RequestBody ForgetPwdData name) {
        String username = name.getUsername();
        try {
            //如果找到用户名
            if (usrService.searchName(username)){
                Usr usr = new Usr();
                usr = usrService.search(username);
                session.setAttribute("forgetUsr",usr);
                System.out.println("Correct Name!");
                return new Result(ANSWER_PAGE,"answer", usr);
            }else{
                LOGGER.error("Wrong Name!");
                System.out.println("Wrong Name!");
                return new Result(ERROR_PAGE,"noName","Wrong Name");
            }
        } catch (SQLException e) {
            LOGGER.error("Error handling find Password", e);
            return new Result(ERROR_PAGE,"error","findPwd");
        }
    }
    @RequestMapping( "/answer-control")
    public Result AnswerControl(@RequestBody AnswerData answerData) {
        String answer = answerData.getAnswer();
        Usr usr = (Usr)session.getAttribute("forgetUsr");
        if (usr.getAnswer().equals(answer)){
            LOGGER.info("Right answer");
            session.removeAttribute("forgetUsr");
            System.out.println("right answer!");
            return new Result("right","rightAnswer", "right answer");
        }else{
            LOGGER.error("Wrong Answer!");
            System.out.println("Wrong answer!");
            return new Result(ERROR_PAGE,"badAnswer","Wrong Answer");
        }
    }
}
