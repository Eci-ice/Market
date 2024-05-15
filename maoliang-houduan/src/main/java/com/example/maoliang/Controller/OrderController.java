package com.example.maoliang.Controller;


import com.example.maoliang.Controller.utils.Result;
import com.example.maoliang.Entity.Order;
import com.example.maoliang.Entity.Usr;
import com.example.maoliang.Service.GoodService;
import com.example.maoliang.Service.OrderService;
import com.example.maoliang.dto.OrderData;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import static com.example.maoliang.Controller.utils.Page.SUCCESS_PAGE;
import static com.example.maoliang.Controller.utils.Page.ERROR_PAGE;

@RestController
@RequestMapping("/order/*")
public class OrderController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private OrderService orderService;
    @Autowired
    private GoodService goodService;
    @Autowired
    public HttpSession session;

    @RequestMapping("/create-order")
    public Result createOrder(@RequestBody Order order) {
        // 从 HttpSession 中获取用户信息
        Usr currentUsr = (Usr) session.getAttribute("admin");
        order.setOwner(goodService.showGood(order.getGoodid()).getOwner());
        if (currentUsr == null) {
            // 如果用户未登录，根据前端传递的信息创建订单
            orderService.createOrder(order);
        } else {
            order.setBuyername(currentUsr.getUsername());
            order.setAddress(currentUsr.getAddress());
            order.setTelephone(currentUsr.getPhone());
            orderService.createOrder(order);
            // 如果用户已登录，根据用户信息创建订单

        }
        return new Result(SUCCESS_PAGE, "创建订单成功", null);
    }


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
    public Result showbuyerorderinfoControl(@RequestParam("name") String name) {
        return new Result(SUCCESS_PAGE, "获得订单成功", orderService.showbuyerorderinfo(name));
    }

    @RequestMapping("/showbuyerhistoryorderinfo-control")
    public Result showbuyerhistoryorderinfoControl(@RequestParam("name") String name) {
        return new Result(SUCCESS_PAGE, "获得订单成功", orderService.showbuyerhistoryorderinfo(name));
    }


    @GetMapping("/alipay")
    public String toaliPay(@RequestParam("orderid") String orderid,@RequestParam("money") String money) throws Exception {
        String form = orderService.toaliPay(orderid, new BigDecimal(money));
        return form;
    }

    @GetMapping("/aliPaymentStatus/{tradeNo}")
    public String checkPaymentStatus(@PathVariable String tradeNo) {
        return orderService.queryOrderAliPaymentStatus(tradeNo);
    }

}