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

    public int getNextOrderId() {
        // 查询已有数据中的最大主键值
        Integer maxOrderId = jdbcTemplate.queryForObject("SELECT MAX(orderid) FROM MLorder", Integer.class);
        return (maxOrderId != null) ? (maxOrderId + 1) : 1; // 如果没有数据，从 1 开始
    }

    public void add(Order order) {
        // 获取下一个订单ID
        int nextOrderId = getNextOrderId();

        // 设置订单ID并执行插入操作
        String sql = "INSERT INTO MLorder(orderid, address, telephone, buyername, goodid, number, orderstate, owner) " +
                "VALUES (?, ?, ?, ?, ?, ?, 1, ?)";
        jdbcTemplate.update(sql, nextOrderId, order.getAddress(), order.getTelephone(),
                order.getBuyername(), order.getGoodid(), order.getNumber(), order.getOwner());

        // 设置 H2 数据库的自增起始值
        jdbcTemplate.execute("ALTER TABLE MLorder ALTER COLUMN orderid RESTART WITH " + (nextOrderId + 1));
    }

    public void modifystate(int orderid, int tostate) {
        String sql = "UPDATE MLorder SET orderstate = ? WHERE orderid = ?";
        jdbcTemplate.update(sql, tostate, orderid);
    }

    public void deletegood(int goodid) {
        String sql = "DELETE FROM MLorder WHERE goodid = ?";
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

    public boolean deleteOrder(int orderid, int orderstate) {

        if (orderstate == -1) {
            String sql = "UPDATE MLorder SET orderstate =? WHERE orderid =?";
            int affectedRows = jdbcTemplate.update(sql, orderstate, orderid);
            return affectedRows > 0; // 如果影响的行数大于0，则表示删除成功
        } else {
            return false;
        }
    }


    public boolean confirmOrder(int orderid, int orderstate) {
        String sql = "UPDATE MLorder SET orderstate =? WHERE orderid= ?";

        int affectedRows = jdbcTemplate.update(sql, orderstate, orderid);
        return affectedRows > 0; // 如果影响的行数大于0，则表示更新成功
    }
    public boolean buyerhistoryconfirmOrder(int orderid, int orderstate) {
        String sql = "UPDATE MLorder SET orderstate =? WHERE orderid= ?";
        int affectedRows = jdbcTemplate.update(sql, orderstate, orderid);
        return affectedRows > 0; // 如果影响的行数大于0，则表示更新成功
    }
}