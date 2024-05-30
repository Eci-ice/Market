package com.example.maoliang.Controller;

import com.example.maoliang.Controller.utils.Result;
import com.example.maoliang.Service.DeliverService;
import com.example.maoliang.Service.GoodService;
import com.example.maoliang.Service.OrderService;
import com.sun.net.httpserver.Authenticator;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.example.maoliang.Controller.utils.Page.SUCCESS_PAGE;


@RestController
@RequestMapping( "/deliver/*")
@CrossOrigin
public class DeliverController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeliverController.class);
    @Autowired
    private DeliverService deliverService;
    @Autowired
    public HttpSession session;
    @RequestMapping("/add-shipping-address-control")
    public Result addShppingaddress(@RequestParam("userid") int userid,@RequestParam("address") String address ,@RequestParam("default_address") String default_address){
        return new Result(SUCCESS_PAGE,"添加收货地址成功",deliverService.addAddress(userid,address,default_address));
    }
    @RequestMapping("/delete-shipping-address-control")
    public Result deleteShppingaddress(@RequestParam("userid") int userid,@RequestParam("address") String address,@RequestParam("default_address") String default_address ){
        return new Result(SUCCESS_PAGE,"删除收货地址成功",deliverService.deleteAddress(userid,address,default_address));
    }
    @RequestMapping("/modify-shipping-address-control")
    public Result modifyShppingaddress(@RequestParam("userid") int userid,@RequestParam("default_address") String default_address){
        return new Result(SUCCESS_PAGE,"修改收货地址成功",deliverService.modifyAddress(userid,default_address));
    }



}
