package sqlitesetup;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import sql.goodsql;
import sql.ordersql;
import sqlimpl.goodsqlimpl;
import sqlimpl.ordersqlimpl;
import vo.good;
import vo.user;

public class test{
  public static void main(String[] args){
    Connection conn = null;
    try{
      // create a database connection
    	Class.forName("org.sqlite.JDBC");
      conn = DriverManager.getConnection("jdbc:sqlite:maoliang.db");
      Statement statement = conn.createStatement();
      PreparedStatement ps=null;
      
      //show user
//      String sql = "select * from MLuser";
//	  ps = conn.prepareStatement(sql);
//      ResultSet rs = ps.executeQuery();
//    while(rs.next())
//    {
//      // read the result set
//      System.out.println(" userid = " + rs.getString("userid"));
//      System.out.println(" username = " + rs.getInt("username"));
//      System.out.println(" pwd = " + rs.getString("pwd"));
//      System.out.println(" power = " + rs.getString("power"));
//    }
//      
      //show all list
//      String sql = "SELECT name FROM sqlite_master WHERE type='table';";
//      ps = conn.prepareStatement(sql);
//      ResultSet rs = ps.executeQuery();
//      while (rs.next()) {
//          String tableName = rs.getString("name");
//          System.out.println(tableName);
//      }

    //set user
//    PreparedStatement ps = null;
//    String sql = "select userid,username,pwd,power from user where username=?";
//	  ps = conn.prepareStatement(sql);
//    ps.setString(1, "123");
//    ResultSet rs = ps.executeQuery();
//    if(rs.next()) {
//        System.out.println(" userid = " + rs.getInt("userid"));
//        System.out.println(" username = " + rs.getString("username"));
//        System.out.println(" pwd = " + rs.getString("pwd"));
//        System.out.println(" power = " + rs.getString("power"));
//    }
//    
      
      
//	      String sql = "insert into good(goodname,description,price,picture,state,number) values(?,?,?,?,?,?)";
//	      PreparedStatement ps  = conn.prepareStatement(sql);
//	      ps.setString(1, "aa");
//	      ps.setString(2, "23423423");
//	      ps.setDouble(3,2.2);
//	      ps.setString(4, "./22.png");
//	      ps.setInt(5, 1);
//	      ps.setInt(6, 1);
//	      ps.executeUpdate();
//        String sql2 = "select * from good";
//		  PreparedStatement ps=conn.prepareStatement(sql2);
//				
//			ResultSet rs=ps.executeQuery();
//			while(rs.next()) {
//				System.out.println(" goodid = " + rs.getInt("goodid"));
//				System.out.println(" goodname = " + rs.getString("goodname"));
//				System.out.println(" price = " + rs.getDouble("price"));
//				System.out.println(" description = " + rs.getString("description"));
//				System.out.println(" picture = " + rs.getString("picture"));
//				System.out.println(" state = " + rs.getInt("state"));
//				System.out.println(" number = " + rs.getInt("number"));
//			}
      

    //show good
//          String sql2 = "select * from MLgood";
//		  ps=conn.prepareStatement(sql2);
//				
//			ResultSet rs=ps.executeQuery();
//			while(rs.next()) {
//				System.out.println(" goodid = " + rs.getInt("goodid"));
//				System.out.println(" goodname = " + rs.getString("goodname"));
//				System.out.println(" price = " + rs.getDouble("price"));
//				System.out.println(" description = " + rs.getString("description"));
//				System.out.println(" picture = " + rs.getString("picture"));
//				System.out.println(" state = " + rs.getInt("state"));
//				System.out.println(" number = " + rs.getInt("number"));
//			}
//       

      //set good
//	         String sql = "insert into order1(address,telephone,buyername,goodid,orderstate) values(?,?,?,?,?)";
//	         PreparedStatement ps  = conn.prepareStatement(sql);
//	         ps.setString(1, "aaa");
//	         ps.setString(2, "123");
//	         ps.setString(3, "aaa");
//	         ps.setInt(4, 1);
//	         ps.setInt(5, 0);
//	         ps.execute();
    
      
      //show order

//      	   String sql = "select * from order_show";
//	       String sql2 = "select * from order_rear";
//	       ps=conn.prepareStatement(sql);
//	   		ResultSet rs=ps.executeQuery();
//	   		
//	   		ps=conn.prepareStatement(sql2);
//	   		ResultSet rs2=ps.executeQuery();
//	   		while(rs.next() && rs2.next()) {
//	   			System.out.println(" orderid = " + rs.getInt("orderid"));
//	   			System.out.println(" address = " + rs.getString("address"));
//	   			System.out.println(" telephone = " + rs.getString("telephone"));
//	   			System.out.println(" buyername = " + rs.getString("buyername"));
//	   			System.out.println(" orderid = " + rs2.getInt("orderid"));
//	   			System.out.println(" goodid = " + rs2.getInt("goodid"));
//	   			System.out.println(" orderstate = " + rs2.getInt("orderstate"));
//	   		}
      
      
	   		System.out.println("hello");
    }
    catch(SQLException e){
      // if the error message is "out of memory",
      // it probably means no database file is found
      System.err.println(e.getMessage());
    } catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    finally{
      try{
        if(conn != null)
          conn.close();
      }
      catch(SQLException e){
        // connection close failed.
        System.err.println(e.getMessage());
      }
    }
  }
}