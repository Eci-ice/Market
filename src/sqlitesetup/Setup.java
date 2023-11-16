package sqlitesetup;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import vo.good;
import vo.user;

public class Setup{
  public static void main(String[] args){
    Connection conn = null;
    try{
      // create a database connection
    	Class.forName("org.sqlite.JDBC");
      conn = DriverManager.getConnection("jdbc:sqlite:D:/maoliang.db");
      Statement statement = conn.createStatement();
      statement.setQueryTimeout(30);  // set timeout to 30 sec.

      statement.executeUpdate("DROP TABLE IF EXISTS `MLgood`");
      statement.executeUpdate("CREATE TABLE `MLgood`  (\r\n" + 
      		"  `goodid` INTEGER PRIMARY KEY AUTOINCREMENT,\r\n" + 
      		"  `goodname` char(20) NOT NULL,\r\n" + 
      		"  `description` varchar(100) NOT NULL,\r\n" + 
      		"  `price` double NOT NULL,\r\n" + 
      		"  `picture` char(100) NOT NULL,\r\n" + 
      		"  `state` int NOT NULL,\r\n" + 
      		"  `number` int NOT NULL,\r\n" + 
      		"  `kind` char(20) NOT NULL)"
      		);
      
      statement.executeUpdate("DROP TABLE IF EXISTS `MLorder`");
      statement.executeUpdate("CREATE TABLE `MLorder`  (\r\n" + 
        		"  `orderid` INTEGER PRIMARY KEY AUTOINCREMENT,\r\n" + 
        		"  `address` varchar(99) ,\r\n" + 
        		"  `telephone` varchar(20) ,\r\n" + 
        		"  `buyername` varchar(99) ,\r\n" + 
	        	"  `goodid` int NOT NULL,\r\n" + 
	        	"  `orderstate` int NOT NULL ,\r\n" +
	        	"  FOREIGN KEY (`goodid`) REFERENCES `MLgood` (`goodid`))");

      statement.executeUpdate("DROP TABLE IF EXISTS `MLuser`");
      statement.executeUpdate("CREATE TABLE `MLuser`  (\r\n" + 
      		"  `userid` INTEGER PRIMARY KEY AUTOINCREMENT,\r\n" + 
      		"  `username` char(10) NOT NULL,\r\n" + 
      		"  `pwd` char(15) NOT NULL,\r\n" + 
      		"  `power` int NOT NULL )" 
        		) ;
      
      statement.executeUpdate("DROP TABLE IF EXISTS `MLprice`");
      statement.executeUpdate("CREATE TABLE `MLprice`  (\r\n" + 
        "  `id` INTEGER PRIMARY KEY AUTOINCREMENT,\r\n" + 
        "  `goodid` int NOT NULL,\r\n" + 
        "  `price` double NOT NULL,\r\n" + 
        "  `change_time` datetime NOT NULL,\r\n" + 
        "  FOREIGN KEY (`goodid`) REFERENCES `MLgood` (`goodid`))");
   
   // 当MLgood中的价格发生变化时，插入新记录 
//      String sql = "INSERT INTO MLprice (goodid, price, change_time) VALUES (?, ?, ?)";
//      PreparedStatement pstmt = conn.prepareStatement(sql);
//      pstmt.setInt(1, goodid);
//      pstmt.setDouble(2, newPrice);
//      pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
//      pstmt.executeUpdate();
      
//      String sql = "SELECT price FROM MLprice WHERE goodid = ? ORDER BY change_time DESC LIMIT 1";
//      PreparedStatement pstmt = conn.prepareStatement(sql);
//      pstmt.setInt(1, goodid);
//      ResultSet rs = pstmt.executeQuery();
//      if (rs.next()) {
//          double latestPrice = rs.getDouble("price");// 这里是最新价格
//      }
      
      statement.executeUpdate("INSERT INTO `MLuser` VALUES (1, '123', '123', 1)");
      statement.executeUpdate("INSERT INTO `MLuser` VALUES (2, '111', '111', 0)");
      System.out.println("Success Setup");

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