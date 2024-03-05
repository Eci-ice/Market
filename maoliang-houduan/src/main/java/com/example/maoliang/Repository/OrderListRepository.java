package com.example.maoliang.Repository;

import com.example.maoliang.Entity.Order;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

//输出为List<Order>的数据库操作方法
@Repository
public class OrderListRepository {
    private final JdbcTemplate jdbcTemplate;

    public OrderListRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Order> showall(int userid) {
        String sql = "SELECT * FROM MLorder WHERE owner = ?";
        try {
            return jdbcTemplate.query(sql, (rs, rowNum) -> {
                Order or = new Order();
                or.setOrderid(rs.getInt("orderid"));
                or.setAddress(rs.getString("address"));
                or.setTelephone(rs.getString("telephone"));
                or.setBuyername(rs.getString("buyername"));
                or.setGoodid(rs.getInt("goodid"));
                or.setNumber(rs.getInt("number"));
                or.setOrderstate(rs.getInt("orderstate"));
                or.setOwner(rs.getInt("owner"));
                return or;
            }, userid);
        } catch (EmptyResultDataAccessException e) {
            // 查询无结果时返回null
            return null;
        }
    }

    public List<Order> showall2(String buyername) {
        String sql = "SELECT * FROM MLorder WHERE buyername = ?";
        try {
            return jdbcTemplate.query(sql, (rs, rowNum) -> {
                Order or = new Order();
                or.setOrderid(rs.getInt("orderid"));
                or.setAddress(rs.getString("address"));
                or.setTelephone(rs.getString("telephone"));
                or.setBuyername(rs.getString("buyername"));
                or.setGoodid(rs.getInt("goodid"));
                or.setNumber(rs.getInt("number"));
                or.setOrderstate(rs.getInt("orderstate"));
                or.setOwner(rs.getInt("owner"));
                return or;
            }, buyername);
        } catch (
        EmptyResultDataAccessException e) {
            // 查询无结果时返回null
            return null;
        }
    }


}