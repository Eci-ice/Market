package Setup;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Setup {
	public static void main(String[] args) {
		Connection conn = null;
		try {
			// create a database connection
			Class.forName("org.h2.Driver");
			conn = DriverManager.getConnection("jdbc:h2:D:/maoliang.db", "", "");
			conn.setAutoCommit(false); // 开始事务
			Statement statement = conn.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.

			// 创建表
			statement.executeUpdate("DROP TABLE IF EXISTS MLuser");
			statement.executeUpdate("CREATE TABLE MLuser (" +
					"  userid INTEGER PRIMARY KEY AUTO_INCREMENT," +
					"  username CHAR(10) NOT NULL," +
					"  pwd CHAR(15) NOT NULL," +
					"  power INT NOT NULL," +
					"  question VARCHAR(50) NOT NULL," +
					"  answer VARCHAR(30) NOT NULL)");

			statement.executeUpdate("DROP TABLE IF EXISTS MLgood");
			statement.executeUpdate("CREATE TABLE MLgood (" +
					"  goodid INTEGER PRIMARY KEY AUTO_INCREMENT," +
					"  goodname CHAR(20) NOT NULL," +
					"  description VARCHAR(100) NOT NULL," +
					"  price DOUBLE NOT NULL," +
					"  picture CHAR(100) NOT NULL," +
					"  state INT NOT NULL," +
					"  number INT NOT NULL," +
					"  kind VARCHAR(20) NOT NULL," +
					"  subkind VARCHAR(20) NOT NULL," +
					"  owner INTEGER NOT NULL," +
					"  calorie DOUBLE NOT NULL," +
					"  catkind VARCHAR(100) NOT NULL," +
					"  catage VARCHAR(20) NOT NULL," +
					"  catweight VARCHAR(20) NOT NULL," +
					"  FOREIGN KEY (owner) REFERENCES MLuser(userid))");

			statement.executeUpdate("DROP TABLE IF EXISTS MLhistorygood");
			statement.executeUpdate("CREATE TABLE MLhistorygood (" +
					"  goodid INTEGER NOT NULL," +
					"  goodname CHAR(20) NOT NULL," +
					"  description VARCHAR(100) NOT NULL," +
					"  price DOUBLE NOT NULL," +
					"  picture CHAR(100) NOT NULL," +
					"  number INT NOT NULL," +
					"  kind VARCHAR(20) NOT NULL," +
					"  subkind VARCHAR(20) NOT NULL," +
					"  createdate DATETIME NOT NULL," +
					"  owner INTEGER NOT NULL," +
					"  calorie DOUBLE NOT NULL," +
					"  catkind VARCHAR(100) NOT NULL," +
					"  catage VARCHAR(20) NOT NULL," +
					"  catweight VARCHAR(20) NOT NULL," +
					"  FOREIGN KEY (owner) REFERENCES MLuser(userid))");

			statement.executeUpdate("DROP TABLE IF EXISTS MLorder");
			statement.executeUpdate("CREATE TABLE MLorder (" +
					"  orderid INTEGER PRIMARY KEY AUTO_INCREMENT," +
					"  address VARCHAR(99)," +
					"  telephone VARCHAR(11)," +
					"  buyername VARCHAR(10)," +
					"  goodid INT NOT NULL," +
					"  number INT NOT NULL," +
					"  orderstate INT NOT NULL," +
					"  owner INTEGER NOT NULL," +
					"  FOREIGN KEY (owner) REFERENCES MLuser(userid)," +
					"  FOREIGN KEY (goodid) REFERENCES MLgood(goodid))");

			statement.executeUpdate("DROP TABLE IF EXISTS MLprice");
			statement.executeUpdate("CREATE TABLE MLprice (" +
					"  id INTEGER PRIMARY KEY AUTO_INCREMENT," +
					"  goodid INT NOT NULL," +
					"  price DOUBLE NOT NULL," +
					"  change_time DATETIME NOT NULL," +
					"  FOREIGN KEY (goodid) REFERENCES MLgood(goodid))");

			statement.executeUpdate("DROP TABLE IF EXISTS MLinfo");
			statement.executeUpdate("CREATE TABLE MLinfo (" +
					"  userid INTEGER NOT NULL," +
					"  phone CHAR(11) NOT NULL," +
					"  address VARCHAR(300) NOT NULL," +
					"  default_address VARCHAR(99) NOT NULL," +
					"  FOREIGN KEY (userid) REFERENCES MLuser(userid))");

			statement.executeUpdate("DROP TABLE IF EXISTS MLbuying");
			statement.executeUpdate("CREATE TABLE MLbuying (" +
					"  buyingid INTEGER PRIMARY KEY AUTO_INCREMENT," +
					"  goodid INTEGER NOT NULL," +
					"  number INT NOT NULL," +
					"  islike INT NOT NULL," +
					"  buyer INTEGER NOT NULL," +
					"  FOREIGN KEY (buyer) REFERENCES MLuser(userid))");

			statement.executeUpdate("DROP TABLE IF EXISTS MLcat");
			statement.executeUpdate("CREATE TABLE MLcat (" +
					"  catid INTEGER PRIMARY KEY AUTO_INCREMENT," +
					"  catname CHAR(20) NOT NULL," +
					"  description VARCHAR(100) NOT NULL," +
					"  catweight DOUBLE NOT NULL," +
					"  catstate INT NOT NULL," +
					"  catage INT NOT NULL," +
					"  catkind VARCHAR(20) NOT NULL," +
					"  owner INTEGER NOT NULL," +
					"  FOREIGN KEY (owner) REFERENCES MLuser(userid))");

			statement.executeUpdate("DROP TABLE IF EXISTS MLmail");
			statement.executeUpdate("CREATE TABLE MLmail (" +
					"  orderid INT NOT NULL," +
					"  step INT NOT NULL," +
					"  location VARCHAR(100) NOT NULL," +
					"  tracking_number CHAR(20) NOT NULL," +
					"  createtime DATETIME DEFAULT CURRENT_TIMESTAMP," +
					"  FOREIGN KEY (orderid) REFERENCES MLorder(orderid))");

			statement.executeUpdate("DROP TABLE IF EXISTS MLaftersale");
			statement.executeUpdate("CREATE TABLE MLaftersale(" +
					"aftersaleid INTEGER PRIMARY KEY AUTO_INCREMENT," +
					"userid INTEGER," +
					"goodid INTEGER," +
					"title varchar(15) NOT NULL," +
					"description varchar(100) NOT NULL," +
					"imagepath varchar(200) NOT NULL," +
					"serviceresult varchar(50) NOT NULL," +
					"FOREIGN KEY (userid) REFERENCES MLuser(userid)," +
					"FOREIGN KEY (goodid) REFERENCES MLgood(goodid))"
			);


			// 插入数据
			statement.executeUpdate("INSERT INTO MLuser(username, pwd, power, question, answer) VALUES ('123', '123', 1, 'who are you?', 'Xiaoming')");
			statement.executeUpdate("INSERT INTO MLuser(username, pwd, power, question, answer) VALUES ('111', '111', 0, 'who are you?', 'Xiaoming')");
			statement.executeUpdate("INSERT INTO MLinfo(userid, phone, address, default_address) VALUES (2, '15911111234', '浙江工商大学', '默认地址')");
			statement.executeUpdate("INSERT INTO MLgood(goodname, description, price, picture, state, number, kind, subkind, owner, calorie, catkind, catweight, catage) " +
					"VALUES ('猫干粮', '美味猫干粮', 13.9, './assets/img/buyer/food-1.jpg', 0, 12, '猫咪主粮', '猫干粮', 1, 3.4, '波斯猫', '1-7', '1-10')");
			statement.executeUpdate("INSERT INTO MLgood(goodname, description, price, picture, state, number, kind, subkind, owner, calorie, catkind, catweight, catage) " +
					"VALUES ('猫湿粮', '美味猫湿粮', 1.0, './assets/img/buyer/food-2.jpg', 0, 3, '猫咪主粮', '猫湿粮', 1, 2.1, '波斯猫,布偶猫', '1-7', '1-10')");
			statement.executeUpdate("INSERT INTO MLhistorygood(goodid, goodname, description, price, picture, number, kind, subkind, createdate, owner, calorie, catkind, catage, catweight) " +
					"VALUES (1, '猫干粮', '美味猫干粮', 13.9, './assets/img/buyer/food-1.jpg', 1, '猫咪主粮', '猫干粮', '2023-11-11 13:13:13', 1, 3.4, '波斯猫', '1-7', '1-10')");
			statement.executeUpdate("INSERT INTO MLhistorygood(goodid, goodname, description, price, picture, number, kind, subkind, createdate, owner, calorie, catkind, catage, catweight) " +
					"VALUES (2, '猫湿粮', '美味猫湿粮', 9.9, './assets/img/buyer/food-2.jpg', 1, '猫湿粮', '猫湿粮', '2023-11-11 13:13:14', 1, 2.1, '波斯猫,布偶猫', '1-7', '1-10')");
			statement.executeUpdate("INSERT INTO MLorder(address, telephone, buyername, goodid, number, orderstate, owner) " +
					"VALUES ('浙江工商大学', '15911111234', '张三', 1, 1, 1, 1)");
			statement.executeUpdate("INSERT INTO MLcat(catname, description, catweight, catstate, catage, catkind, owner) " +
					"VALUES ('嘟嘟可', '可爱的白色布偶猫', 1.2, 3, 1, '布偶猫', 1)");
			statement.executeUpdate("INSERT INTO MLcat(catname, description, catweight, catstate, catage, catkind, owner) " +
					"VALUES ('蹦蹦', '可爱的白色波斯猫', 1.2, 4, 1, '波斯猫', 2)");

			conn.commit(); // 提交事务
			System.out.println("Success Setup");
		} catch (SQLException e) {
			try {
				if (conn != null) {
					conn.rollback(); // 出现异常时回滚事务
				}
			} catch (SQLException ex) {
				System.err.println("Rollback failed: " + ex.getMessage());
			}
			System.err.println("SQL Error: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.err.println(e.getMessage());
			}
		}
	}
}
