package com.example.maoliang.Controller;

import com.example.maoliang.Entity.MLmail;
import com.example.maoliang.Service.LogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logistics")
public class LogisticsController {

    @Autowired
    private LogisticsService logisticsService;

    // 提交物流信息
    @PostMapping("/submit")
    public ResponseEntity<String> submitLogistics(@RequestBody MLmail mlmail) {
        logisticsService.submitLogistics(mlmail);
        return ResponseEntity.ok("物流信息提交成功");
    }

    // 获取指定订单的物流信息
    @GetMapping("/order/{orderid}")
    public ResponseEntity<List<MLmail>> getLogisticsByOrderId(@PathVariable int orderid) {
        List<MLmail> logisticsList = logisticsService.getLogisticsByOrderId(orderid);
        return ResponseEntity.ok(logisticsList);
    }
    // 更新物流信息
    @PutMapping("/update/{orderid}/{step}")
    public ResponseEntity<String> updateLogistics(@PathVariable int orderid, @PathVariable int step, @RequestBody MLmail mlmail) {
        logisticsService.updateLogistics(orderid, step, mlmail);
        return ResponseEntity.ok("物流信息更新成功");
    }
    // 删除物流信息
    @DeleteMapping("/delete/{orderid}/{step}")
    public ResponseEntity<String> deleteLogistics(@PathVariable int orderid, @PathVariable int step) {
        logisticsService.deleteLogistics(orderid, step);
        return ResponseEntity.ok("物流信息删除成功");
    }
    // 获取所有物流信息
    @GetMapping("/all")
    public ResponseEntity<List<MLmail>> getAllLogistics() {
        List<MLmail> logisticsList = logisticsService.getAllLogistics();
        return ResponseEntity.ok(logisticsList);
    }
}

