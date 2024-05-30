package com.example.maoliang.Controller;
import com.example.maoliang.Entity.Aftersale;
import com.example.maoliang.Service.AftersaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aftersale")
public class AftersaleController {

    @Autowired
    private AftersaleService aftersaleService;

    // 提交售后申请
    @PostMapping("/submit")
    public ResponseEntity<String> submitAftersale(@RequestBody Aftersale aftersale) {
        aftersaleService.submitAftersale(aftersale);
        return ResponseEntity.ok("售后申请提交成功");
    }

    // 获取所有售后申请
    @GetMapping("/all")
    public ResponseEntity<List<Aftersale>> getAllAftersales() {
        List<Aftersale> aftersales = aftersaleService.getAllAftersales();
        return ResponseEntity.ok(aftersales);
    }

    // 修改处理结果
    @PutMapping("/update/{aftersaleid}")
    public ResponseEntity<String> updateServiceResult(@PathVariable int aftersaleid, @RequestParam String serviceresult) {
        aftersaleService.updateServiceResult(aftersaleid, serviceresult);
        return ResponseEntity.ok("处理结果修改成功");
    }
    // 买家获取自己的售后信息
    @GetMapping("/buyer/{userid}")
    public ResponseEntity<List<Aftersale>> getAftersalesByUserId(@PathVariable int userid) {
        List<Aftersale> aftersales = aftersaleService.getAftersalesByUserId(userid);
        return ResponseEntity.ok(aftersales);
    }

}
