package com.example.maoliang.Service;

import com.example.maoliang.Entity.*;
import com.example.maoliang.Repository.GoodListRepository;
import com.example.maoliang.Repository.GoodRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GoodService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodService.class);

    @Autowired
    private GoodRepository goodRepository;

    @Autowired
    GoodListRepository goodListRepository;

    public void modifyLike(int goodId, int userId, int nowLike) {
        // 数据库操作
        goodRepository.modifylike(goodId, userId, nowLike); // 根据 nowLike 参数执行相应操作
    }

//    public List<Good> showBuyerAll(int userId) {
//        return goodListRepository.showbuyerall(userId);
//    }
    public List<Good> showNowGoods(){
        return goodListRepository.shownow();
    }

    public List<Good> showAllGoods(int userId) {
        return goodListRepository.showall(userId);
    }



    public int deleteGood(int goodId) {
        return goodRepository.remove(goodId);
    }

    public int findCart(int goodId, int buyer) {
        return goodRepository.findCart(goodId, buyer);
    }
    public void addToCart(int goodId, int buyer) {
        goodRepository.addToCart(goodId, buyer);
    }


    public void modifyBuyNumber(int buyingId, int number) {
       goodRepository.modifyBuyNumber(buyingId, number);
    }

    public void addToLike(int goodId, int buyer) {
        goodRepository.addToLike(goodId, buyer);
    }
    ////////////////////
//    public List<GoodList> showBuyerAll(int userId) {
//        return goodRepository.showBuyerAll(userId);
//    }
//    public BuyerCart showBuyerCart(int userId) {
//        List<CartItem> cartItems = goodRepository.showBuyerAll(userId);
//        BuyerCart buyerCart = new BuyerCart();
//        buyerCart.setBuyer(userId);
//        buyerCart.setCartItems(cartItems);
//        return buyerCart;
//    }


    public List<Map<String, Object>> showBuyerCart(int userId) {
        List<Map<String, Object>> maps = goodRepository.showBuyerCart(userId);
        for (Map<String, Object> map : maps) {
            map.put("quantity",0);
        }
        return maps;
    }
//    public List<GoodList> showLike(int userId) {
//        return goodRepository.showLike(userId);
//    }
//    public List<Good> showLike(int userId) {
//        return goodRepository.showLike(userId);
//    }

    public List<Map<String, Object>> showLike(int userId, int islike) {
        List<Map<String, Object>> list = goodRepository.showLike(userId, islike);
        return list;
    }




    public void cancelLike(int goodId,int userid) {
        goodRepository.cancelLike(goodId,userid);
    }
    public void removeBuyingItem(int buyingId,int userid) {
        goodRepository.removeBuyingItem(buyingId,userid);
    }


}
