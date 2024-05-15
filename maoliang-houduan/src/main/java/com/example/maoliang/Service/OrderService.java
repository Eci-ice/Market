package com.example.maoliang.Service;


import com.alipay.easysdk.payment.common.models.AlipayTradeQueryResponse;
import com.example.maoliang.Entity.Order;
import com.example.maoliang.Repository.OrderListRepository;
import com.example.maoliang.Repository.OrderRepository;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import java.util.List;

@Service
public class OrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    OrderListRepository orderListRepository;
    @Autowired
    public HttpSession session;
    public void createOrder(Order order) {
        orderRepository.add(order);
    }

    public List<Order> showOrderInformation() {
        return orderListRepository.searchOrderInformation();
    }


    public void deletegood(int goodid) {
        orderRepository.deletegood(goodid);
    }

    public boolean deleteOrder(int orderid, int orderstate) {
        return orderRepository.deleteOrder(orderid, orderstate);
    }

    public boolean confirmOrder(int orderid, int orderstate) {
        return orderRepository.confirmOrder(orderid, orderstate);
    }

    public boolean buyerhistoryconfirmOrder(int orderid, int orderstate) {
        return orderRepository.buyerhistoryconfirmOrder(orderid, orderstate);
    }

    public List<Order> showbuyerorderinfo(String name) {
        return  orderListRepository.showbuyerorderinfo(name);
    }

    public List<Order> showbuyerhistoryorderinfo(String name) {
        return  orderListRepository.showbuyerhistoryorderinfo(name);
    }

    public String toaliPay(String subject, BigDecimal money) throws Exception {
        String TradeNo=this.generateTradeNo(subject);
        AlipayTradePagePayResponse pay = Factory.Payment.Page().pay("订单id："+subject, TradeNo ,
                String.valueOf(money), "http://localhost:3000/#/buyer-pay");
        session.setAttribute("now-TradeNo", TradeNo);
        String payForm = null;
        if (ResponseChecker.success(pay)) {
            payForm = pay.getBody();
        }
        return payForm;
    }

    private String generateTradeNo(String subject) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS");
        String tradeNo = subject+"_"+LocalDateTime.now(ZoneOffset.of("+8")).format(formatter);
        return tradeNo;
    }
    public String queryOrderAliPaymentStatus(String tradeNo) {
        try {
            AlipayTradeQueryResponse response = Factory.Payment.Common().query(tradeNo);

            if (response != null && "10000".equals(response.code)) {
                System.out.println(response.buyerUserId);
                String tradeStatus = response.tradeStatus;
                if ("TRADE_SUCCESS".equals(tradeStatus)) {
                    // 支付成功
                    return "已支付";
                } else {
                    // 其他状态，例如：WAIT_BUYER_PAY、TRADE_CLOSED 等
                    return "未支付成功！";
                }
            } else {
                // 查询失败或未找到订单
                return "Failed to query order status";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Exception occurred while querying order status";
        }
    }
}
