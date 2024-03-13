package com.example.maoliang.Service;

import com.example.maoliang.Repository.GoodListRepository;
import com.example.maoliang.Repository.GoodRepository;
import com.example.maoliang.Entity.Good;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
