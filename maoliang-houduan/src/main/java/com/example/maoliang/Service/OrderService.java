package com.example.maoliang.Service;


import com.example.maoliang.Entity.Order;
import com.example.maoliang.Repository.OrderListRepository;
import com.example.maoliang.Repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    OrderListRepository orderListRepository;


    public List<Order> showOrderInformation() {
        return orderListRepository.searchOrderInformation();
    }

    public boolean deleteOrder(int orderid, int orderstate) {
        return orderListRepository.deleteOrder(orderid, orderstate);
    }

    public boolean confirmOrder(int orderid, int orderstate) {
        return orderListRepository.confirmOrder(orderid, orderstate);
    }

    public boolean buyerhistoryconfirmOrder(int orderid, int orderstate) {
        return orderListRepository.buyerhistoryconfirmOrder(orderid, orderstate);
    }

    public List<Order> showbuyerorderinfo(int userid) {
        return  orderListRepository.showall(userid);
    }
}
