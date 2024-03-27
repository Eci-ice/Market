package com.example.maoliang.Controller;


import com.example.maoliang.Controller.utils.Result;
import com.example.maoliang.Entity.Good;
import com.example.maoliang.Entity.Usr;
import com.example.maoliang.Service.GoodService;
import com.example.maoliang.Service.OrderService;
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

import static com.example.maoliang.Controller.utils.Page.SUCCESS_PAGE;
import static com.example.maoliang.Controller.utils.Page.ERROR_PAGE;

@RestController
@RequestMapping("/order/*")
public class OrderController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private OrderService orderService;
    @Autowired
    public HttpSession session;

    @RequestMapping("/showorderinfo-control")
    public Result showOrderInfoControl( ) {
        return new Result(SUCCESS_PAGE, "查看意向订单成功", orderService.showOrderInformation());
    }

    @RequestMapping("/deleteorder-control")
    public Result deleteorderControl(@RequestParam("orderid") int orderid, @RequestParam("orderstate") int orderstate) {
        boolean f = orderService.deleteOrder(orderid, orderstate);
        if (f) {
            return new Result(SUCCESS_PAGE, "取消订单成功", orderService.deleteOrder(orderid, orderstate));
        } else {
            return new Result(ERROR_PAGE, "取消订单失败", orderService.deleteOrder(orderid, orderstate));
        }

    }

    @RequestMapping("/confirmorder-control")
    public Result confirmorderControl(@RequestParam("orderid") int orderid, @RequestParam("orderstate") int orderstate) {
        boolean f = orderService.confirmOrder(orderid, orderstate);
        if (f) {
            return new Result(SUCCESS_PAGE, "确认订单成功", orderService.confirmOrder(orderid, orderstate));

        } else {
            return new Result(ERROR_PAGE, "确认订单失败", orderService.confirmOrder(orderid, orderstate));
        }

    }
    @RequestMapping("/buyerhistoryconfirmorder-control")
    public Result buyerhistoryconfirmOrderControl(@RequestParam("orderid") int orderid, @RequestParam("orderstate") int orderstate) {
        return new Result(SUCCESS_PAGE, "确认订单成功", orderService.buyerhistoryconfirmOrder(orderid, orderstate));
    }
    @RequestMapping("/showbuyerorderinfo-control")
    public Result showbuyerorderinfoControl(@RequestParam("userid") int userid) {
        return new Result(SUCCESS_PAGE, "获得订单成功", orderService.showbuyerorderinfo(userid));
    }

}