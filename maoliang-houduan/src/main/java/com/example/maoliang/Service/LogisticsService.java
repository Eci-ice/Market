package com.example.maoliang.Service;

import com.example.maoliang.Entity.MLmail;
import com.example.maoliang.Repository.LogisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogisticsService {

    @Autowired
    private LogisticsRepository logisticsRepository;

    // 提交物流信息
    public void submitLogistics(MLmail mlmail) {
        logisticsRepository.submitLogistics(mlmail);
    }

    // 获取指定订单的物流信息
    public List<MLmail> getLogisticsByOrderId(int orderid) {
        return logisticsRepository.getLogisticsByOrderId(orderid);
    }
    // 更新物流信息
    public void updateLogistics(int orderid, int step, MLmail mlmail) {
        logisticsRepository.updateLogistics(orderid, step, mlmail);
    }
    // 删除物流信息
    public void deleteLogistics(int orderid, int step) {
        logisticsRepository.deleteLogistics(orderid, step);
    }
    // 获取所有物流信息
    public List<MLmail> getAllLogistics() {
        return logisticsRepository.getAllLogistics();
    }

}
