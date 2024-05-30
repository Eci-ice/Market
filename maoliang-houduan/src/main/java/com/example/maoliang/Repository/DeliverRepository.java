package com.example.maoliang.Repository;

import com.example.maoliang.Entity.Address;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DeliverRepository {
    private static JdbcTemplate jdbcTemplate = null;

    public DeliverRepository(JdbcTemplate jdbcTemplate){this.jdbcTemplate=jdbcTemplate;}



    public static List<Address> add(int userid, String address,String default_address){
        String sql="INSERT INTO MLaddress(owner,address,default_address)"+"VALUES(?,?,?)";
        jdbcTemplate.update(sql,userid,address,default_address);
        return null;
    }


    public static List<Address> delete(int userid, String address,String default_address) {
        String sql = "DELETE FROM MLaddress WHERE owner = ? AND address = ? AND default_address=?";
        jdbcTemplate.update(sql, userid, address,default_address);
        return null;
    }

    public static List<Address> modify(int userid,String newaddress ) {
        String sql = "UPDATE MLaddress SET default_address = ? WHERE owner = ? ";
        jdbcTemplate.update(sql, newaddress, userid);
        return null;
    }
}
