package com.example.maoliang.Repository;

import com.example.maoliang.Entity.BuyerCart;
import com.example.maoliang.Entity.CartItem;
import com.example.maoliang.Entity.Good;
import com.example.maoliang.Entity.GoodList;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

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

        try {
            jdbcTemplate.update(insertGoodSql, good.getGoodname(), good.getDescription(), good.getPrice(),
                    good.getPicture(), good.getState(), good.getNumber(), good.getKind(), good.getSubkind(),
                    good.getOwner());

            // 查询刚插入的商品的id
            Integer goodId = jdbcTemplate.queryForObject("SELECT last_insert_rowid()", Integer.class);

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

//删除商品
    public int remove(int goodId) {
        String sql = "DELETE FROM MLgood WHERE goodid = ?";
        return jdbcTemplate.update(sql, goodId);
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


    public Good getGoodById(int goodId) {
        String sql = "SELECT * FROM MLgood WHERE goodid = ?";
        try{
            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Good.class), goodId);
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
    public int findCart(int goodId, int buyer) {
        String sql = "SELECT buyingid FROM MLbuying WHERE goodid = ? AND buyer = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{goodId, buyer}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            // 如果没有找到结果，则返回-1
            return -1;
        }
    }

    public void addToCart(int goodId, int buyer) {
        String sql = "INSERT INTO MLbuying(goodid, number, islike, buyer) VALUES (?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, goodId, 1, 0, buyer);
        } catch (DataAccessException e) {
            // 如果出现异常，你可以在这里处理
            System.out.println("Error occurred while adding to cart: " + e.getMessage());
        }
    }

    public void modifyBuyNumber(int buyingId, int number) {
        String sql;
        try {
            if (number == -1) { // 自增
                sql = "UPDATE MLbuying SET number = number + 1 WHERE buyingid = ?";
                jdbcTemplate.update(sql, buyingId);
            } else {
                sql = "UPDATE MLbuying SET number = ? WHERE buyingid = ?";
                jdbcTemplate.update(sql, number, buyingId);
            }
        } catch (DataAccessException e) {
            // 如果出现异常，你可以在这里处理
            System.out.println("Error occurred while modifying buy number: " + e.getMessage());
        }
    }

    /////////////////////////////////////////////////////////
    ///////aaaaaaaaaaaaaaaaaaaaaaa
//    public int findCart(int goodId, int buyer) {
//        String sql = "SELECT * FROM MLbuying WHERE goodid = ? AND buyer = ?";
//        try {
//            return jdbcTemplate.queryForObject(sql, new Object[]{goodId, buyer}, Integer.class);
//        } catch (EmptyResultDataAccessException e) {
//            // 如果没有找到结果，则返回-1
//            return -1;
//        }
//    }
//    public void addToCart(int goodId, int buyer) {
//        String sql = "INSERT INTO MLbuying(goodid, number, islike, buyer) VALUES (?, ?, ?, ?)";
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//
//        jdbcTemplate.update((PreparedStatementCreator) con -> {
//            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"}); // Assuming 'id' is the auto-generated key column
//            ps.setInt(1, goodId);
//            ps.setInt(2, 1);
//            ps.setInt(3, 0);
//            ps.setInt(4, buyer);
//            return ps;
//        }, keyHolder);
//    }
///////aaaaaaaaaaaaaaaaaaaaaaa
//public void addToCart(int goodId, int buyer) {
//    String sql = "INSERT INTO MLbuying(goodid, number, islike, buyer) VALUES (?, ?, ?, ?)";
//    KeyHolder keyHolder = new GeneratedKeyHolder();
//
//    try {
//        jdbcTemplate.update((PreparedStatementCreator) con -> {
//            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"}); // Assuming 'id' is the auto-generated key column
//            ps.setInt(1, goodId);
//            ps.setInt(2, 1);
//            ps.setInt(3, 0);
//            ps.setInt(4, buyer);
//            return ps;
//        }, keyHolder);
//    } catch (DataAccessException e) {
//        // 如果出现异常，你可以在这里处理
//        System.out.println("Error occurred while adding to cart: " + e.getMessage());
//    }
//}

//    public void modifyBuyNumber(int buyingId, int number) {
//        String sql;
//        if (number == -1) { // 自增
//            sql = "UPDATE MLbuying SET number = number + 1 WHERE buyingid = ?";
//            jdbcTemplate.update(sql, buyingId);
//        } else {
//            sql = "UPDATE MLbuying SET number = ? WHERE buyingid = ?";
//            jdbcTemplate.update(sql, number, buyingId);
//        }
//    }
    ///////aaaaaaaaaaaaaaaaaaaaaaa
//public void modifyBuyNumber(int buyingId, int number) {
//    String sql;
//    try {
//        if (number == -1) { // 自增
//            sql = "UPDATE MLbuying SET number = number + 1 WHERE buyingid = ?";
//            jdbcTemplate.update(sql, buyingId);
//        } else {
//            sql = "UPDATE MLbuying SET number = ? WHERE buyingid = ?";
//            jdbcTemplate.update(sql, number, buyingId);
//        }
//    } catch (DataAccessException e) {
//        // 如果出现异常，你可以在这里处理
//        System.out.println("Error occurred while modifying buy number: " + e.getMessage());
//    }
//}

    /////////////////////
    public void addToLike(int goodId, int buyer) {
        String checkIfExistsSql = "SELECT COUNT(*) FROM MLbuying WHERE goodid = ? and buyer=?";
        int count = jdbcTemplate.queryForObject(checkIfExistsSql, Integer.class, goodId,buyer);

        if (count > 0) {
            String updateSql = "UPDATE MLbuying SET islike = 1 WHERE goodid = ?";
            jdbcTemplate.update(updateSql, goodId);
        } else {
            String insertSql = "INSERT INTO MLbuying(goodid, number, islike, buyer) VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(insertSql, goodId, 0, 1, buyer);
        }
    }
    public void cancelLike(int goodId,int buyer) {
        String updateSql = "UPDATE MLbuying SET islike = 0 WHERE goodid = ? and buyer=?";
        jdbcTemplate.update(updateSql, goodId,buyer);
    }
    public List<Map<String, Object>> showBuyerCart(int userId) {
        String sql = "SELECT g.GOODID as id , g.GOODNAME as name,g.PRICE as price,g.DESCRIPTION as description,g.KIND as kind,g.PICTURE as mediaFiles,g.NUMBER as maxquantity FROM MLbuying b left join MLGOOD g on b.GOODID=g.GOODID  WHERE b.buyer = ?";
        return jdbcTemplate.queryForList(sql, userId);
    }
//    public List<GoodList> showBuyerAll(int userId) {
//        String sql = "SELECT buying.*, good.goodname, good.price, good.picture, good.state, good.numbermax, ? AS owner " +
//                "FROM MLbuying buying " +
//                "JOIN MLgood good ON buying.goodid = good.goodid " +
//                "WHERE buying.buyer = ?";
//
//        return jdbcTemplate.query(sql, new Object[]{userId, userId}, new BeanPropertyRowMapper<>(GoodList.class));
//    }
//    public List<GoodList> showBuyerAll(int userId) {
//        String sql = "SELECT buying.*, good.goodname, good.price, good.picture, good.state, good.number, ? AS owner " +
//                "FROM MLbuying buying " +
//                "JOIN MLgood good ON buying.goodid = good.goodid " +
//                "WHERE buying.buyer = ?";
//
//        return jdbcTemplate.query(sql, new Object[]{userId, userId}, new BeanPropertyRowMapper<>(GoodList.class));
//    }
//    public BuyerCart showBuyerAll(int userId) {
//        List<CartItem> cartItems = jdbcTemplate.query("SELECT buyingid, goodid, number, islike FROM MLbuying WHERE buyer = ?",
//                new Object[]{userId},
//                (rs, rowNum) -> {
//                    CartItem item = new CartItem();
//                    item.setBuyingId(rs.getInt("buyingid"));
//                    item.setGoodId(rs.getInt("goodid"));
//                    item.setNumber(rs.getInt("number"));
//                    item.setIsLike(rs.getInt("islike"));
//                    return item;
//                });
//
//        BuyerCart cart = new BuyerCart();
//        cart.setBuyer(userId);
//        cart.setCartItems(cartItems);
//
//        return cart;
//    }
public List<Map<String,Object>> showLike(int userId, int islike) {
    String sql = "SELECT good.GOODID, good.goodname, good.price, good.picture, good.state, good.number,good.description FROM MLbuying buying JOIN MLgood good ON buying.goodid = good.goodid WHERE buying.buyer = ? AND buying.islike = ?";
    return jdbcTemplate.queryForList(sql, userId, islike);
}




////////////////////
//    public List<GoodList> showLike(int userId) {
//        String sql = "SELECT buying.*, good.goodname, good.price, good.picture, good.state, good.numbermax, ? AS owner " +
//                "FROM MLbuying buying " +
//                "JOIN MLgood good ON buying.goodid = good.goodid " +
//                "WHERE buying.buyer = ? AND buying.islike = 1";
//
//        return jdbcTemplate.query(sql, new Object[]{userId, userId}, new BeanPropertyRowMapper<>(GoodList.class));
//    }
//public List<GoodList> showLike(int userId) {
//    String sql = "SELECT buying.*, good.goodname, good.price, good.picture, good.state, good.number, ? AS owner " +
//            "FROM MLbuying buying " +
//            "JOIN MLgood good ON buying.goodid = good.goodid " +
//            "WHERE buying.buyer = ? AND buying.islike = 1";
//
//    return jdbcTemplate.query(sql, new Object[]{userId, userId}, new BeanPropertyRowMapper<>(GoodList.class));
//}
//public List<Good> showLike(int userId) {
//    String sql = "SELECT buying.*, good.goodname, good.price, good.picture, good.state, good.number, ? AS owner " +
//            "FROM MLbuying buying " +
//            "JOIN MLgood good ON buying.goodid = good.goodid " +
//            "WHERE buying.buyer = ? AND buying.islike = 1";
//
//    return jdbcTemplate.query(sql, new Object[]{userId, userId}, (rs, rowNum) -> {
//        Good good = new Good();
//        // 设置 Good 对象的字段
//        good.setGoodname(rs.getString("goodname"));
//        good.setPrice(rs.getDouble("price"));
//        good.setPicture(rs.getString("picture"));
//        good.setState(rs.getInt("state"));
//        good.setNumber(rs.getInt("number"));
//        good.setOwner(rs.getInt("owner"));
//        return good;
//    });
//}

    public void removeBuyingItem(int buyingId,int userid ) {
        String sql = "DELETE FROM MLbuying WHERE goodid = ? and buyer = ?";
        jdbcTemplate.update(sql, buyingId,userid);
    }








}
