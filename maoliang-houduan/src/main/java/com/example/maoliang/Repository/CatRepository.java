package com.example.maoliang.Repository;

import com.example.maoliang.Entity.Cat;
import com.example.maoliang.Entity.Good;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    public Cat findCatById(int catid) {
        String sql = "SELECT * FROM MLcat WHERE catid = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{catid}, new BeanPropertyRowMapper<>(Cat.class));
    }

    public List<Good> findGoodsByCatAge(int newcatage) {
        String sqlQuery = "SELECT goodid, goodname, price, picture, calorie, catweight FROM MLgood " +
                "WHERE CAST(SUBSTRING(catage, 1, POSITION('-' IN catage) - 1) AS INT) <= :newcatage " +
                "AND CAST(SUBSTRING(catage, POSITION('-' IN catage) + 1) AS INT) >= :newcatage";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("newcatage", newcatage);

        List<Good> goods = new ArrayList<>();

        try {
            namedParameterJdbcTemplate.query(sqlQuery, paramMap, rs -> {
                while (rs.next()) {
                    Good good = new Good();
                  //  System.out.println(rs.getInt("goodid"));
                    good.setGoodid(rs.getInt("goodid"));
                    good.setGoodname(rs.getString("goodname"));
                    good.setPrice(rs.getDouble("price"));
                    good.setPicture(rs.getString("picture"));
                    good.setCalorie(rs.getDouble("calorie"));
                    good.setCatweight(rs.getString("catweight"));
                    goods.add(good);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return goods;
    }

    public List<Good> findGoodsByCatWeight(double newcatweight) {
        String sqlQuery = "SELECT goodid, goodname, price, picture, calorie, catweight FROM MLgood " +
                "WHERE CAST(SUBSTRING(catweight, 1, POSITION('-' IN catweight) - 1) AS DOUBLE) <= :newcatweight " +
                "AND CAST(SUBSTRING(catweight, POSITION('-' IN catweight) + 1) AS DOUBLE) >= :newcatweight";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("newcatweight", newcatweight);

        List<Good> goods = new ArrayList<>();

        try {
            namedParameterJdbcTemplate.query(sqlQuery, paramMap, rs -> {
                while (rs.next()) {
                    Good good = new Good();
                 //   System.out.println(rs.getInt("goodid"));
                    good.setGoodid(rs.getInt("goodid"));
                    good.setGoodname(rs.getString("goodname"));
                    good.setPrice(rs.getDouble("price"));
                    good.setPicture(rs.getString("picture"));
                    good.setCalorie(rs.getDouble("calorie"));
                    good.setCatweight(rs.getString("catweight"));
                    goods.add(good);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return goods;
    }

    public List<Good> findGoodsByCatKind(String catkind) {
        String sqlQuery = "SELECT goodid, goodname, price, picture, calorie, catweight " +
                "FROM MLgood " +
                "WHERE catkind LIKE ?";

        String likePattern = "%" + catkind + "%";

        List<Good> goods = new ArrayList<>();

        try {
            goods = jdbcTemplate.query(sqlQuery, new Object[]{likePattern}, new RowMapper<Good>() {
                @Override
                public Good mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Good good = new Good();
                 //   System.out.println(rs.getInt("goodid"));
                    good.setGoodid(rs.getInt("goodid"));
                    good.setGoodname(rs.getString("goodname"));
                    good.setPrice(rs.getDouble("price"));
                    good.setPicture(rs.getString("picture"));
                    good.setCalorie(rs.getDouble("calorie"));
                    good.setCatweight(rs.getString("catweight"));
                    return good;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


        return goods;
    }
}
