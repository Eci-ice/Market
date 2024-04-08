package com.example.maoliang.Repository;

import com.example.maoliang.Entity.Good;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//连接到MLGood的其他数据库操作方法
@Repository
public class CatRepository {
// Spring 的 JdbcTemplate方式
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CatRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void searchGoodsByCatKind(String newcatkind) {
        String sqlQuery = "SELECT * FROM MLgood WHERE catkind LIKE ?";
        String likePattern = "%" + newcatkind + "%";

        try {
            jdbcTemplate.query(sqlQuery, rs -> {
                while (rs.next()) {
                    int goodId = rs.getInt("goodid");
                    String goodName = rs.getString("goodname");
                    // 可以根据需要继续处理其他字段
                    System.out.println("商品 ID: " + goodId + ", 商品名称: " + goodName);
                }
            }, likePattern);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchGoodsByCatAge(int newcatage) {
        String sqlQuery = "SELECT * FROM MLgood WHERE :newcatage BETWEEN CAST(SUBSTRING_INDEX(catage, '-', 1) AS INT) AND CAST(SUBSTRING_INDEX(catage, '-', -1) AS INT)";

        // 构建查询参数
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("newcatage", newcatage);

        try {
            namedParameterJdbcTemplate.query(sqlQuery, paramMap, rs -> {
                while (rs.next()) {
                    int goodId = rs.getInt("goodid");
                    String goodName = rs.getString("goodname");
                    // 可以根据需要继续处理其他字段
                    System.out.println("商品 ID: " + goodId + ", 商品名称: " + goodName);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
