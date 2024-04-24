package com.example.maoliang.Service;

import com.example.maoliang.Entity.Cat;
import com.example.maoliang.Repository.CatRepository;
import com.example.maoliang.Repository.GoodListRepository;
import com.example.maoliang.Repository.GoodRepository;
import com.example.maoliang.Entity.Good;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CatService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CatService.class);

    @Autowired
    private CatRepository catRepository;
    public List<Cat> getCatsByOwnerId(int ownerId) {
        return catRepository.findByOwner(ownerId);
    }
    public void addCat(int catid, String catname, String description, double catweight, int catstate, int catage, String catkind, int owner) {
        Cat cat = new Cat();
        cat.setCatid(catid);
        cat.setCatname(catname);
        cat.setDescription(description);
        cat.setCatweight(catweight);
        cat.setCatstate(catstate);
        cat.setCatage(catage);
        cat.setCatkind(catkind);
        cat.setOwner(owner);
        catRepository.save(cat);
    }
//    public void updateCatInformation(int id, double catweight, int catstate, int catage) {
//        Optional<Cat> optionalCat = catRepository.findById(id);
//        if (optionalCat.isPresent()) {
//            Cat cat = optionalCat.get();
//            cat.setCatweight(catweight);
//            cat.setCatstate(catstate);
//            cat.setCatage(catage);
//            catRepository.save(cat);
//        } else {
//            throw new IllegalArgumentException("猫咪 ID 不存在");
//        }
//    }
public void updateCatInformation(int id, double catweight, int catstate, int catage) {
    Cat cat = catRepository.findById(id);
    if (cat != null) {
        cat.setCatweight(catweight);
        cat.setCatstate(catstate);
        cat.setCatage(catage);
        catRepository.update(cat);
    } else {
        throw new RuntimeException("找不到对应ID的猫咪信息");
    }
}


}
