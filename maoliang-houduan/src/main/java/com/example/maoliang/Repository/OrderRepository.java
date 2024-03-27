package com.example.maoliang.Repository;

import com.example.maoliang.Entity.Order;
import com.example.maoliang.Entity.Usr;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

//连接到MLorder的其他数据库操作方法
@Repository
public class OrderRepository {
    private final JdbcTemplate jdbcTemplate;

    public OrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(Order order) {
        String sql = "INSERT INTO MLorder(address, telephone, buyername, goodid, number, orderstate, owner) " +
                "VALUES (?, ?, ?, ?, ?, 1, ?)";
        jdbcTemplate.update(sql, order.getAddress(), order.getTelephone(), order.getBuyername(),
                order.getGoodid(), order.getNumber(), order.getOwner());
    }

    public void modifystate(int orderid, int tostate) {
        String sql = "UPDATE MLorder SET orderstate = ? WHERE orderid = ?";
        jdbcTemplate.update(sql, tostate, orderid);
    }

    public void deletegood(int goodid) {
        String sql = "UPDATE MLorder SET orderstate = -1 WHERE orderid = ?";
        jdbcTemplate.update(sql, goodid);
    }

    public int searchid(int orderid) {
        String sql = "SELECT goodid FROM MLorder WHERE orderid = ?";
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, orderid);
        } catch (EmptyResultDataAccessException e) {
            // 查询无结果时返回-1
            return -1;
        }
    }

    public void deleteorder(int orderid) {
        String sql = "UPDATE MLorder SET orderstate = 1 WHERE orderid = ?";
        jdbcTemplate.update(sql, orderid);
    }

    public int searchstate(int orderid) {
        String sql = "SELECT orderstate FROM MLorder WHERE orderid = ?";
        try {
             return jdbcTemplate.queryForObject(sql, Integer.class, orderid);
        } catch (EmptyResultDataAccessException e) {
            // 查询无结果时返回-1
            return -1;
        }
    }

    public Order getOrderById(int orderId) {
        String sql = "SELECT * FROM MLorder WHERE orderid = ?";
        try{
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                Order resultOrder = new Order();
                resultOrder.setOrderid(rs.getInt("orderid"));
                resultOrder.setAddress(rs.getString("address"));
                resultOrder.setTelephone(rs.getString("telephone"));
                resultOrder.setBuyername(rs.getString("buyername"));
                resultOrder.setGoodid(rs.getInt("goodid"));
                resultOrder.setNumber(rs.getInt("number"));
                resultOrder.setOrderstate(rs.getInt("orderstate"));
                resultOrder.setOwner(rs.getInt("owner"));
                return resultOrder;
            }, orderId);
        } catch (EmptyResultDataAccessException e) {
            // 查询无结果时返回null
            return null;
        }
    }
}