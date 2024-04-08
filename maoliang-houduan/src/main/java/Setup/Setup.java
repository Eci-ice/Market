package Setup;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Setup {
	public static void main(String[] args) {
		Connection conn = null;
		try {
			// create a database connection
			Class.forName("org.h2.Driver");
			conn = DriverManager.getConnection("jdbc:h2:D:/maoliang.db", "", "");
			Statement statement = conn.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.

			statement.executeUpdate("DROP TABLE IF EXISTS MLuser");
			statement.executeUpdate("CREATE TABLE MLuser  (" +
					"  userid INTEGER PRIMARY KEY AUTO_INCREMENT," +
					"  username char(10) NOT NULL," +
					"  pwd char(15) NOT NULL," +
					"  power int NOT NULL," +
					"  question varchar(50) NOT NULL," +
					"  answer varchar(30) NOT NULL )");

			statement.executeUpdate("DROP TABLE IF EXISTS MLgood");
			statement.executeUpdate("CREATE TABLE MLgood  (" +
					"  goodid INTEGER PRIMARY KEY AUTO_INCREMENT," +
					"  goodname char(20) NOT NULL," +
					"  description varchar(100) NOT NULL," +
					"  price double NOT NULL," +
					"  picture char(100) NOT NULL," +
					"  state int NOT NULL," +
					"  number int NOT NULL," +
					"  kind varchar(20) NOT NULL," +
					"  subkind varchar(20) NOT NULL," +
					"  owner INTEGER NOT NULL," +
					"  calorie double NOT NULL," +
					"  catkind varchar(100) NOT NULL," +
					"  catage varchar(20)  NOT NULL," +
					"  catweight varchar(20)  NOT NULL," +
					"  FOREIGN KEY (owner) REFERENCES MLuser (userid))");

			statement.executeUpdate("DROP TABLE IF EXISTS MLhistorygood");
			statement.executeUpdate("CREATE TABLE MLhistorygood  (" +
					"  goodid INTEGER NOT NULL," +
					"  goodname char(20) NOT NULL," +
					"  description varchar(100) NOT NULL," +
					"  price double NOT NULL," +
					"  picture char(100) NOT NULL," +
					"  number int NOT NULL," +
					"  kind varchar(20) NOT NULL," +
					"  subkind varchar(20) NOT NULL," +
					"  createdate DATETIME NOT NULL," +
					"  owner INTEGER NOT NULL," +
					"  calorie double NOT NULL," +
					"  catkind varchar(100) NOT NULL," +
					"  catage varchar(20) NOT NULL," +
					"  catweight varchar(20) NOT NULL," +
					"  FOREIGN KEY (owner) REFERENCES MLuser (userid))");

			statement.executeUpdate("DROP TABLE IF EXISTS MLorder");
			statement.executeUpdate("CREATE TABLE MLorder  (" +
					"  orderid INTEGER PRIMARY KEY AUTO_INCREMENT," +
					"  address varchar(99)," +
					"  telephone varchar(11)," +
					"  buyername varchar(10)," +
					"  goodid int NOT NULL," +
					"  number int NOT NULL," +
					"  orderstate int NOT NULL," +
					"  owner INTEGER NOT NULL," +
					"  FOREIGN KEY (owner) REFERENCES MLuser (userid)," +
					"  FOREIGN KEY (goodid) REFERENCES MLgood (goodid))");


			statement.executeUpdate("DROP TABLE IF EXISTS MLprice");
			statement.executeUpdate("CREATE TABLE MLprice  (" +
					"  id INTEGER PRIMARY KEY AUTO_INCREMENT," +
					"  goodid int NOT NULL," +
					"  price double NOT NULL," +
					"  change_time datetime NOT NULL," +
					"  FOREIGN KEY (goodid) REFERENCES MLgood (goodid))");

			statement.executeUpdate("DROP TABLE IF EXISTS MLinfo");
			statement.executeUpdate("CREATE TABLE MLinfo  (" +
					"  userid INTEGER NOT NULL," +
					"  phone char(11) NOT NULL," +
					"  address varchar(99) NOT NULL," +
					"  FOREIGN KEY (userid) REFERENCES MLuser (userid))");

			statement.executeUpdate("DROP TABLE IF EXISTS MLbuying");
			statement.executeUpdate("CREATE TABLE MLbuying  (" +
					"  buyingid INTEGER PRIMARY KEY AUTO_INCREMENT," +
					"  goodid INTEGER NOT NULL," +
					"  number int NOT NULL," +
					"  islike int NOT NULL," +
					"  buyer INTEGER NOT NULL," +
					"  FOREIGN KEY (buyer) REFERENCES MLuser (userid))");


			statement.executeUpdate("DROP TABLE IF EXISTS MLcat");
			statement.executeUpdate("CREATE TABLE MLcat  (" +
					"  catid INTEGER PRIMARY KEY AUTO_INCREMENT," +
					"  catname char(20) NOT NULL," +
					"  description varchar(100) NOT NULL," +
					"  catweight double NOT NULL," +
					"  catstate int NOT NULL," +
					"  catage int NOT NULL," +
					"  catkind varchar(20) NOT NULL," +
					"  owner INTEGER NOT NULL," +
					"  FOREIGN KEY (owner) REFERENCES MLuser (userid))");



			statement.executeUpdate("INSERT INTO MLuser VALUES (1, '123', '123', 1,'who are you?','Xiaoming')");
			statement.executeUpdate("INSERT INTO MLuser VALUES (2, '111', '111', 0,'who are you?','Xiaoming')");
			statement.executeUpdate("INSERT INTO MLinfo VALUES (2, '15911111234','浙江工商大学')");
			statement.executeUpdate("INSERT INTO MLgood(goodname, description, price, picture, state, number, kind, subkind, owner)  VALUES ('猫干粮', '美味猫干粮', 13.9,'./img/food-1.jpg',0,12,'猫咪主粮','猫干粮',1)");
			statement.executeUpdate("INSERT INTO MLgood(goodname, description, price, picture, state, number, kind, subkind, owner)  VALUES ('猫湿粮', '美味猫湿粮', 1.0,'./img/food-2.jpg',0,3,'猫咪主粮','猫湿粮',1)");
			statement.executeUpdate("INSERT INTO MLhistorygood VALUES (1, '猫干粮', '美味猫干粮', 13.9,'./img/food-1.jpg',1,'猫咪主粮','猫干粮','2023-11-11 13:13:13',1)");
			statement.executeUpdate("INSERT INTO MLhistorygood VALUES (2, '猫湿粮', '美味猫湿粮', 9.9,'./img/food-2.jpg',1,'猫湿粮','猫湿粮','2023-11-11 13:13:14',1)");
			statement.executeUpdate("INSERT INTO MLorder VALUES (1, '浙江工商大学','15911111234', '张三',1, 1,1,1)");
			conn.commit();
			System.out.println("Success Setup");
		} catch (SQLException e) {
			// if the error message is "out of memory",
			// it probably means no database file is found
			System.err.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// connection close failed.
				System.err.println(e.getMessage());
			}
		}
	}
}