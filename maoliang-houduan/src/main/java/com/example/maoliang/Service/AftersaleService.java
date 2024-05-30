package com.example.maoliang.Service;
import com.example.maoliang.Entity.Aftersale;
import com.example.maoliang.Repository.AftersaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AftersaleService {

    @Autowired
    private AftersaleRepository aftersaleRepository;

    // 提交售后申请
    public void submitAftersale(Aftersale aftersale) {
        aftersaleRepository.submitAftersale(aftersale);
    }

    // 获取所有售后申请
    public List<Aftersale> getAllAftersales() {
        return aftersaleRepository.getAllAftersales();
    }

    // 修改处理结果
    public void updateServiceResult(int aftersaleid, String serviceresult) {
        aftersaleRepository.updateServiceResult(aftersaleid, serviceresult);
    }
    // 根据用户ID获取售后信息
    public List<Aftersale> getAftersalesByUserId(int userid) {
        return aftersaleRepository.getAftersalesByUserId(userid);
    }
}
