package com.example.maoliang.Repository;

import com.example.maoliang.Entity.Good;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

//输出为List<Good>的数据库操作方法
@Repository
public class GoodListRepository {
// Spring 的 JdbcTemplate方式
    private final JdbcTemplate jdbcTemplate;

    public GoodListRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Good> shownow() {
        String sql = "SELECT * FROM MLgood WHERE state = 0";
        try {
            return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Good.class));
        } catch (EmptyResultDataAccessException e) {
            // 如果查询结果为空，返回 null
            return null;
        }
    }


    public List<Good> searchls(String keyword, String kind, int power, int userid, int ishistory) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM ");

        if (ishistory == 1) {
            sqlBuilder.append("MLhistorygood");
            if (power == 1) {
                sqlBuilder.append(" WHERE owner = ?");
            } else {
                sqlBuilder.append(" WHERE kind = ?");
            }

        } else {
            sqlBuilder.append("MLgood WHERE state = 0");
            if (power == 1) {
                sqlBuilder.append(" AND owner = ?");
            } else {
                sqlBuilder.append(" AND kind = ?");
            }

        }

        sqlBuilder.append(" AND goodname LIKE ?");
        String sql = sqlBuilder.toString();
        try {
            return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Good.class), (power == 1) ? userid : kind, "%" + keyword + "%");
        } catch (EmptyResultDataAccessException e) {
            // 如果查询结果为空，返回 null
            return null;
        }
    }


    public List<Good> showall(int userid) {
        String sql = "SELECT * FROM MLgood WHERE owner = ?";
        try {
            return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Good.class), userid);
        } catch (EmptyResultDataAccessException e) {
            // 如果查询结果为空，返回 null
            return null;
        }
    }

    public List<Good> showhistoryall(int userid) {
        String sql = "SELECT * FROM MLhistorygood WHERE owner = ?";
        try {
            return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Good.class), userid);
        } catch (EmptyResultDataAccessException e) {
            // 如果查询结果为空，返回 null
            return null;
        }
    }

    public List<Good> showlike(int userid) {
        String sql = "SELECT b.*, g.price * b.number AS totalprice FROM MLbuying b INNER JOIN MLgood g ON b.goodid = g.goodid WHERE b.buyer = ? AND b.islike = 1";
        try {
            return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Good.class), userid);
        } catch (
        EmptyResultDataAccessException e) {
            // 如果查询结果为空，返回 null
            return null;
        }
    }

    public List<Good> showbuyerall(int userid) {
        String sql = "SELECT b.*, g.price * b.number AS totalprice FROM MLbuying b INNER JOIN MLgood g ON b.goodid = g.goodid WHERE b.buyer = ?";
        try {
            return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Good.class), userid);
        } catch (EmptyResultDataAccessException e) {
            // 如果查询结果为空，返回 null
            return null;
        }
    }

    public List<Map<String, Object>> showBuyerCart(int userId) {
        String sql = "SELECT g.GOODID as id , g.GOODNAME as name,g.PRICE as price,g.DESCRIPTION as description,g.KIND as kind,g.PICTURE as mediaFiles,g.NUMBER as maxquantity FROM MLbuying b left join MLGOOD g on b.GOODID=g.GOODID  WHERE b.buyer = ?";
        return jdbcTemplate.queryForList(sql, userId);
    }
    public List<Map<String,Object>> showLike(int userId, int islike) {
        String sql = "SELECT good.GOODID, good.goodname, good.price, good.picture, good.state, good.number,good.description FROM MLbuying buying JOIN MLgood good ON buying.goodid = good.goodid WHERE buying.buyer = ? AND buying.islike = ?";
        return jdbcTemplate.queryForList(sql, userId, islike);
    }

}
