package com.example.maoliang.Repository;

import com.example.maoliang.Entity.Order;
import com.example.maoliang.Entity.Usr;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//输出为List<Usr>的数据库操作方法
@Repository
public class UsrListRepository {
    private final JdbcTemplate jdbcTemplate;

    public UsrListRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Order> searchUserOrderHistory(int pow, int userid) {
        String sql = null;
        if (pow == 1) {
            sql = "SELECT * FROM MLorder WHERE owner = ?";
            try {
                return jdbcTemplate.query(sql, new Object[]{userid},
                        BeanPropertyRowMapper.newInstance(Order.class));
            } catch (EmptyResultDataAccessException e) {
                // 如果查询结果为空，返回空列表而不是 null
                return new ArrayList<>();
            }
        } else if (pow == 0) {
            try {
                // 获取买家姓名
                String sqlGetName = "SELECT username FROM MLuser WHERE userid = ?";
                String buyername = jdbcTemplate.queryForObject(sqlGetName, String.class, userid);

                // 根据买家姓名查询订单列表
                String sqlOrders = "SELECT * FROM MLorder WHERE buyername = ?";
                return jdbcTemplate.query(sqlOrders, new Object[]{buyername},
                        BeanPropertyRowMapper.newInstance(Order.class));
            } catch (EmptyResultDataAccessException e) {
                // 如果查询结果为空，返回空列表而不是 null
                return new ArrayList<>();
            }
        }
        // 如果 pow 不为 0 或 1，或者其他情况，返回空列表而不是 null
        return new ArrayList<>();
    }


    public List<Usr> searchAllUsers() throws SQLException {
        String sql = "SELECT * FROM MLuser";
        try {
            return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Usr.class));
        } catch (
        EmptyResultDataAccessException e) {
            // 如果查询结s果为空，返回 null
            return null;
        }
    }

    public List<Usr> loadBuyer(){
        String sql = "SELECT * FROM MLuser WHERE power=0";
        try {
//            BeanPropertyRowMapper.newInstance  查询结果映射到Usr的实例
            return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Usr.class));
        } catch (EmptyResultDataAccessException e) {
            // 如果查询结果为空，返回 null
            return null;
        }
    }


}
