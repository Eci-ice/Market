package com.example.maoliang.Controller;

import com.example.maoliang.Controller.utils.Result;
import com.example.maoliang.Entity.Usr;
import com.example.maoliang.Repository.UsrRepository;
import com.example.maoliang.Service.GoodService;
import com.example.maoliang.Service.UsrService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

import static com.example.maoliang.Controller.utils.Page.BUYER_PAGE;
import static com.example.maoliang.Controller.utils.Page.ERROR_PAGE;

@RestController
@RequestMapping("/buyer/*")
public class CustomerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UsrController.class);
    @Autowired
    private UsrService usrService;
    @Autowired
    private GoodService goodService;
    @Autowired
    private UsrRepository usrRepository;
    @Autowired
    public HttpSession session;//存储session数据

    @RequestMapping("/allBuyer")
    public Result allBuyerControl() throws SQLException {
        List<Usr> buyerList = usrService.loadBuyer();
        if(buyerList==null){
            return new Result("null","noBuyer",null);
        }else{
            return new Result(BUYER_PAGE,"success",buyerList);
        }

    }
}
