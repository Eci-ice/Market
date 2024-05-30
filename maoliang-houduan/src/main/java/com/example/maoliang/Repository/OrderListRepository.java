package com.example.maoliang.Repository;

import com.example.maoliang.Entity.Good;
import com.example.maoliang.Entity.Order;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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

    public List<Order> searchOrderInformation() {
        String sql = "SELECT * FROM MLorder";
        try {
            return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Order.class));
        } catch (EmptyResultDataAccessException e) {
            // 查询无结果时返回null
            return null;
        }
    }

    public  List<Order>  showbuyerorderinfo(String name) {
//数据库中取得数据不用trim
        String sql = "SELECT * FROM MLorder WHERE orderstate >= 1 and buyername = ?";
        try{
            return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Order.class), name);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public  List<Order>  showbuyerhistoryorderinfo(String name) {
//数据库中取得数据不用trim
        String sql = "SELECT * FROM MLorder WHERE orderstate = -1 and buyername = ?";
        try{
            return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Order.class), name);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }





}