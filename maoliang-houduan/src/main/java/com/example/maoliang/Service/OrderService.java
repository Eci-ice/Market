package com.example.maoliang.Service;


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

    public void deletegood(int goodid) {
        orderRepository.deletegood(goodid);
    }
}
