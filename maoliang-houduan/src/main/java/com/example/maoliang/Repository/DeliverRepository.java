package com.example.maoliang.Repository;

import com.example.maoliang.Entity.Address;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DeliverRepository {
    private static JdbcTemplate jdbcTemplate = null;

    public DeliverRepository(JdbcTemplate jdbcTemplate){this.jdbcTemplate=jdbcTemplate;}



    public static List<Address> add(int userid, String address){
        String sql="INSERT INTO MLaddress(owner,address)"+"VALUES(?,?)";
        jdbcTemplate.update(sql,userid,address);


        return null;
    }


    public static List<Address> delete(int userid, String address) {
        String sql = "DELETE FROM MLaddress WHERE owner = ? AND address = ?";
        jdbcTemplate.update(sql, userid, address);
        return null;
    }

    public static List<Address> modify(int userid, String oldaddress,String newaddress ) {
        String sql = "UPDATE MLaddress SET address = ? WHERE owner = ? AND address = ?";
        jdbcTemplate.update(sql, newaddress, userid, oldaddress);
        return null;
    }
}
