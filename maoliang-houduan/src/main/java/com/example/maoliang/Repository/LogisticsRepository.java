package com.example.maoliang.Repository;

import com.example.maoliang.Entity.MLmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LogisticsRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 提交物流信息
    public void submitLogistics(MLmail mlmail) {
        String sql = "INSERT INTO MLmail (orderid, step, location, tracking_number) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, mlmail.getOrderid(), mlmail.getStep(), mlmail.getLocation(), mlmail.getTrackingNumber());
    }

    // 获取指定订单的物流信息
    public List<MLmail> getLogisticsByOrderId(int orderid) {
        String sql = "SELECT * FROM MLmail WHERE orderid = ? ORDER BY step ASC";
        return jdbcTemplate.query(sql, new Object[]{orderid}, (rs, rowNum) -> new MLmail(
                rs.getInt("orderid"),
                rs.getInt("step"),
                rs.getString("location"),
                rs.getString("tracking_number"),
                rs.getTimestamp("createtime")
        ));
    }
    // 更新物流信息
    public void updateLogistics(int orderid, int step, MLmail mlmail) {
        String sql = "UPDATE MLmail SET location = ?, tracking_number = ? WHERE orderid = ? AND step = ?";
        jdbcTemplate.update(sql, mlmail.getLocation(), mlmail.getTrackingNumber(), orderid, step);
    }
    // 删除物流信息
    public void deleteLogistics(int orderid, int step) {
        String sql = "DELETE FROM MLmail WHERE orderid = ? AND step = ?";
        jdbcTemplate.update(sql, orderid, step);
    }
    // 获取所有物流信息
    public List<MLmail> getAllLogistics() {
        String sql = "SELECT * FROM MLmail ORDER BY orderid ASC, step ASC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new MLmail(
                rs.getInt("orderid"),
                rs.getInt("step"),
                rs.getString("location"),
                rs.getString("tracking_number"),
                rs.getTimestamp("createtime")
        ));
    }


}
