package com.example.maoliang.Repository;
import com.example.maoliang.Entity.Aftersale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AftersaleRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 提交售后申请
    public void submitAftersale(Aftersale aftersale) {
        String sql = "INSERT INTO MLaftersale (userid, goodid, title, description, imagepath, serviceresult) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, aftersale.getUserid(), aftersale.getGoodid(), aftersale.getTitle(), aftersale.getDescription(), aftersale.getImagepath(), aftersale.getServiceresult());
    }

    // 获取所有售后申请
    public List<Aftersale> getAllAftersales() {
        String sql = "SELECT * FROM MLaftersale";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Aftersale(
                rs.getInt("aftersaleid"),
                rs.getInt("userid"),
                rs.getInt("goodid"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("imagepath"),
                rs.getString("serviceresult")
        ));
    }

    // 修改处理结果
    public void updateServiceResult(int aftersaleid, String serviceresult) {
        String sql = "UPDATE MLaftersale SET serviceresult = ? WHERE aftersaleid = ?";
        jdbcTemplate.update(sql, serviceresult, aftersaleid);
    }

    // 根据用户ID获取售后信息
    public List<Aftersale> getAftersalesByUserId(int userid) {
        String sql = "SELECT * FROM MLaftersale WHERE userid = ?";
        return jdbcTemplate.query(sql, new Object[]{userid}, (rs, rowNum) -> new Aftersale(
                rs.getInt("aftersaleid"),
                rs.getInt("userid"),
                rs.getInt("goodid"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("imagepath"),
                rs.getString("serviceresult")
        ));
    }
}
