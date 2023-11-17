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
      		"  `kind` char(20))"
      		);
      statement.executeUpdate("DROP TABLE IF EXISTS `MLhistorygood`");
      statement.executeUpdate("CREATE TABLE `MLhistorygood`  (\r\n" + 
        		"  `goodid` INTEGER PRIMARY KEY AUTOINCREMENT,\r\n" + 
        		"  `goodname` char(20) NOT NULL,\r\n" + 
        		"  `description` varchar(100) NOT NULL,\r\n" + 
        		"  `price` double NOT NULL,\r\n" + 
        		"  `picture` char(100) NOT NULL,\r\n" + 
        		"  `number` int NOT NULL,\r\n" + 
        		"  `kind` char(20))"
        		);
      
      statement.executeUpdate("DROP TABLE IF EXISTS `MLorder`");
      statement.executeUpdate("CREATE TABLE `MLorder`  (\r\n" + 
        		"  `orderid` INTEGER PRIMARY KEY AUTOINCREMENT,\r\n" + 
        		"  `address` varchar(99) ,\r\n" + 
        		"  `telephone` varchar(11) ,\r\n" + 
        		"  `buyername` varchar(10) ,\r\n" + 
	        	"  `goodid` int NOT NULL,\r\n" + 
	        	"  `orderstate` int NOT NULL ,\r\n" +
	        	"  FOREIGN KEY (`goodid`) REFERENCES `MLgood` (`goodid`))");

      statement.executeUpdate("DROP TABLE IF EXISTS `MLuser`");
      statement.executeUpdate("CREATE TABLE `MLuser`  (\r\n" + 
      		"  `userid` INTEGER PRIMARY KEY AUTOINCREMENT,\r\n" + 
      		"  `username` char(10) NOT NULL,\r\n" + 
      		"  `pwd` char(15) NOT NULL,\r\n" + 
      		"  `power` int NOT NULL ,\r\n" +  
            "  `question` varchar(50) NOT NULL,\r\n" + 
            "  `answer` varchar(30) NOT NULL )" 
        		) ;
      
      statement.executeUpdate("DROP TABLE IF EXISTS `MLprice`");
      statement.executeUpdate("CREATE TABLE `MLprice`  (\r\n" + 
        "  `id` INTEGER PRIMARY KEY AUTOINCREMENT,\r\n" + 
        "  `goodid` int NOT NULL,\r\n" + 
        "  `price` double NOT NULL,\r\n" + 
        "  `change_time` datetime NOT NULL,\r\n" + 
        "  FOREIGN KEY (`goodid`) REFERENCES `MLgood` (`goodid`))");
      
      
      statement.executeUpdate("DROP TABLE IF EXISTS `MLinfo`");
      statement.executeUpdate("CREATE TABLE `MLinfo`  (\r\n" + 
        "  `userid` INTEGER NOT NULL,\r\n" + 
        "  `phone` char(11) NOT NULL,\r\n" + 
        "  `address` varchar(99) NOT NULL,\r\n" + 
        "  FOREIGN KEY (`userid`) REFERENCES `MLuser` (`userid`))");
      
      statement.executeUpdate("INSERT INTO `MLuser` VALUES (1, '123', '123', 1,'who are you?','Xiaoming')");
      statement.executeUpdate("INSERT INTO `MLuser` VALUES (2, '111', '111', 0,'who are you?','Xiaoming')");
      statement.executeUpdate("INSERT INTO `MLinfo` VALUES (2, '11111111111','zjsu')");
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