package com.example.maoliang.Service;

import com.example.maoliang.Repository.GoodListRepository;
import com.example.maoliang.Repository.GoodRepository;
import com.example.maoliang.Entity.Good;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Good showGood(int goodid) {
        return goodRepository.search(goodid);
    }

    public List<Good> showNowGoods(){
        return goodListRepository.shownow();
    }

    public List<Good> showAllGoods(int userId) {
        return goodListRepository.showall(userId);
    }

    public List<Good> showAllHistoryGoods(int userId) {
        return goodListRepository.showhistoryall(userId);
    }

    public List<Good> showBuyerAll(int userId) {
        return goodListRepository.showbuyerall(userId);
    }

    public List<Good> showSearchGoods(String keyword,String kind,int power,int userid,int ishistory) {
        return goodListRepository.searchls(keyword,kind,power,userid,ishistory);
    }

    public int isUnique(String name) {
        return goodRepository.unique(name);
    }

    public void add(Good g) {
        goodRepository.add(g);
    }

    public int remove(int goodid) {
        return goodRepository.remove(goodid);
    }

    public boolean updateGood(Good g) {
        return goodRepository.updateGood(g);
    }

    public int findCart(int goodId, int buyer) {
        return goodRepository.findcart(goodId, buyer);
    }
    public void addToCart(int goodId, int buyer) {
        goodRepository.addtocart(goodId, buyer);
    }


    public void modifyBuyNumber(int buyingId, int number) {
        goodRepository.modifybuynumber(buyingId, number);
    }

    public void addToLike(int goodId, int buyer) {
        goodRepository.addtolike(goodId, buyer);
    }

    public List<Map<String, Object>> showBuyerCart(int userId) {
        List<Map<String, Object>> maps = goodListRepository.showBuyerCart(userId);
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
        List<Map<String, Object>> list = goodListRepository.showLike(userId, islike);
        return list;
    }


    public void cancelLike(int goodId,int userid) {
        goodRepository.cancellike(goodId,userid);
    }
    public void removeBuyingItem(int buyingId,int userid) {
        goodRepository.removebuyingitem(buyingId,userid);
    }


}
