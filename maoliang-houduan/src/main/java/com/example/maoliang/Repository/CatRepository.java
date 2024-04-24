package com.example.maoliang.Repository;

import com.example.maoliang.Entity.Cat;
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
import java.util.Optional;

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
    public void save(Cat cat) {
        String sql = "INSERT INTO MLcat (catid, catname, description, catweight, catstate, catage, catkind, owner) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, cat.getCatid(), cat.getCatname(), cat.getDescription(),
                cat.getCatweight(), cat.getCatstate(), cat.getCatage(), cat.getCatkind(), cat.getOwner());
    }

    public void update(Cat cat) {
        String sql = "UPDATE MLcat SET catweight = ?, catstate = ?, catage = ? WHERE catid = ?";
        jdbcTemplate.update(sql, cat.getCatweight(), cat.getCatstate(), cat.getCatage(), cat.getCatid());
    }


    public List<Cat> findByOwner(int ownerId) {
        String sqlQuery = "SELECT * FROM MLcat WHERE owner = ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{ownerId}, (rs, rowNum) -> {
            Cat cat = new Cat();
            cat.setCatid(rs.getInt("catid"));
            cat.setCatname(rs.getString("catname"));
            cat.setDescription(rs.getString("description"));
            cat.setCatweight(rs.getDouble("catweight"));
            cat.setCatstate(rs.getInt("catstate"));
            cat.setCatage(rs.getInt("catage"));
            cat.setCatkind(rs.getString("catkind"));
            cat.setOwner(rs.getInt("owner"));
            return cat;
        });
    }
    public Cat findById(int catid) {
        String query = "SELECT * FROM MLcat WHERE catid = ?";
        return jdbcTemplate.queryForObject(query, new Object[]{catid}, (rs, rowNum) -> {
            Cat cat = new Cat();
            cat.setCatid(rs.getInt("catid"));
            cat.setCatname(rs.getString("catname"));
            cat.setDescription(rs.getString("description"));
            cat.setCatweight(rs.getDouble("catweight"));
            cat.setCatstate(rs.getInt("catstate"));
            cat.setCatage(rs.getInt("catage"));
            cat.setCatkind(rs.getString("catkind"));
            cat.setOwner(rs.getInt("owner"));
            return cat;
        });
    }


}
