package com.example.maoliang.Repository;

import com.example.maoliang.Entity.Usr;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

//输出为List<Usr>的数据库操作方法
@Repository
public class UsrListRepository {
    private final JdbcTemplate jdbcTemplate;

    public UsrListRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Usr> searchAllUsers() throws SQLException {
        String sql = "SELECT * FROM MLuser";
        try {
            return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Usr.class));
        } catch (
        EmptyResultDataAccessException e) {
            // 如果查询结果为空，返回 null
            return null;
        }
    }


}
