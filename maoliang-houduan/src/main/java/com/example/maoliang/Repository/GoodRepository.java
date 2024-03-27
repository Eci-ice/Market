package com.example.maoliang.Repository;

import com.example.maoliang.Entity.Good;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//连接到MLGood的其他数据库操作方法
@Repository
public class GoodRepository{
// Spring 的 JdbcTemplate方式
    private final JdbcTemplate jdbcTemplate;

    public GoodRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(Good good) {
        String insertGoodSql = "INSERT INTO MLgood (goodname, description, price, picture, state, number, kind, subkind, owner) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String insertHistorySql = "INSERT INTO MLhistorygood (goodid, goodname, description, price, picture, number, kind, subkind, createdate, owner) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
      //  System.out.println(good);
        try {
            jdbcTemplate.update(insertGoodSql, good.getGoodname(), good.getDescription(), good.getPrice(),
                    good.getPicture(), good.getState(), good.getNumber(), good.getKind(), good.getSubkind(),
                    good.getOwner());

            // 查询刚插入的商品的id 因为是自增，所以是最大
            Integer goodId = jdbcTemplate.queryForObject("SELECT MAX(goodid) FROM MLgood", Integer.class);
         //   System.out.println(goodId+"aa");

            // 获取当前时间
            LocalDateTime currentTime = LocalDateTime.now();
            // 定义日期时间格式
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            // 格式化当前时间
            String formattedDateTime = currentTime.format(formatter);

            jdbcTemplate.update(insertHistorySql, goodId, good.getGoodname(), good.getDescription(),
                    good.getPrice(), good.getPicture(), good.getNumber(), good.getKind(), good.getSubkind(),
                    formattedDateTime, good.getOwner());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void addtocart(int goodid, int buyer) {
        String sql = "INSERT INTO MLbuying(goodid, number, islike, buyer) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, goodid, 1, 0, buyer);
    }


    public void modifylike(int goodid, int buyer, int nowlike) {
        // 查询是否存在对应记录
        String querySql = "SELECT COUNT(*) FROM MLbuying WHERE goodid = ? AND buyer = ?";
        int count = jdbcTemplate.queryForObject(querySql, Integer.class, goodid, buyer);

        if (count > 0) {
            // 存在记录，执行更新操作
            String updateSql = "UPDATE MLbuying SET islike = ? WHERE goodid = ? AND buyer = ?";
            int newIsLike = nowlike == 1 ? 0 : 1;
            jdbcTemplate.update(updateSql, newIsLike, goodid, buyer);
        } else {
            // 不存在记录，执行插入操作
            String insertSql = "INSERT INTO MLbuying(goodid, number, islike, buyer) VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(insertSql, goodid, 0, 1, buyer);
        }
    }


    public void modifybuynumber(int buyingid, int number) {
        String sql = (number == -1) ? "UPDATE MLbuying SET number = number + 1 WHERE buyingid = ?" : "UPDATE MLbuying SET number = ? WHERE buyingid = ?";
        jdbcTemplate.update(sql, (number == -1) ? buyingid : number, buyingid);
    }


    public int remove(int goodid) {
        String sql = "DELETE FROM MLgood WHERE goodid = ?";
        return jdbcTemplate.update(sql, goodid);
    }


    public int unique(String name) {
        String sql = "SELECT COUNT(*) FROM MLgood WHERE state = 0 AND goodname = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, name);
    }


    public int oldunique() {
        String sql = "SELECT COUNT(*) FROM MLgood WHERE state = 0";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public void modifystate(int goodid, int tostate) {
        String sql = "UPDATE MLgood SET state = ? WHERE goodid = ?";
        jdbcTemplate.update(sql, tostate, goodid);
    }

    public Good search(int goodid) {
        String sql = "SELECT * FROM MLgood WHERE goodid = ?";
        try{
            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Good.class), goodid);
        } catch (EmptyResultDataAccessException e) {
            // 如果查询结果为空，返回 null
            return null;
        }
    }


    public boolean updateGood(Good good) {
        String updateQuery = "UPDATE MLgood SET ";
        StringBuilder setClause = new StringBuilder();

        // 构建SET子句
        setClause.append("goodname = ?, description = ?, price = ?, picture = ?, state = ?, number = ?, kind = ?, subkind = ?, owner = ?");
        updateQuery += setClause.toString();

        updateQuery += " WHERE goodid = ?";

        int rowsAffected = jdbcTemplate.update(updateQuery,
                good.getGoodname(),
                good.getDescription(),
                good.getPrice(),
                good.getPicture(),
                good.getState(),
                good.getNumber(),
                good.getKind(),
                good.getSubkind(),
                good.getOwner(),
                good.getGoodid());
        return rowsAffected > 0; // 返回更新是否成功
    }

    public boolean updateGoodNumber(int number, int goodId) {
        String updateQuery = "UPDATE MLgood SET number = number + ? WHERE goodid = ?";
        int rowsAffected = jdbcTemplate.update(updateQuery, number, goodId);
        return rowsAffected > 0; // 返回更新是否成功
    }
}
