package sqlimpl;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import sql.usersql;
import vo.user;

public class usersqlimpl implements usersql{

	@Override
	public user search(String username) throws SQLException {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:D:/maoliang.db");
//			// 获取数据库文件的文件名
//			String dbURL = conn.getMetaData().getURL();
//			String fileName = dbURL.substring(dbURL.lastIndexOf(":") + 1);
//			// 使用java.io.File类获取文件的绝对路径
//			File file = new File(fileName);
//			String absolutePath = file.getAbsolutePath();
//			// 打印绝对路径
//			System.out.println("The absolute path is: " + absolutePath);

	        PreparedStatement ps = null;
	        user u=null;
	        
			String sql = "select * from MLuser where username=?";
			//System.out.print(sql);
			ps = conn.prepareStatement(sql);
	        ps.setString(1, username);
	        ResultSet rs = ps.executeQuery();
	        int id=-1;
	        if(rs.next()) {
	        	u=new user(); 
	        	id= rs.getInt(1);
	        	u.setUserid(rs.getInt(1));
	        	u.setUsername(rs.getString(2));
	        	u.setPwd(rs.getString(3));
	        	u.setPower(rs.getInt(4));
	        	u.setQuestion(rs.getString(5));
	        	u.setAnswer(rs.getString(6));
	        	String phone =null;
	        	String address=null;
	        	if(0==rs.getInt(4)) {//买家
		        	sql = "select * from MLinfo where userid=?";
		        	//System.out.print(id);
		            ps = conn.prepareStatement(sql);
			        ps.setInt(1, id);
			        rs = ps.executeQuery();
		            phone = rs.getString(2);
		            address= rs.getString(3);
	        	}
	        	u.setPhone(phone);
	            u.setAddress(address);
	        	ps.close();
		        conn.close();
	            return u;
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		return null;
	}

	@Override
	public void modifypwd(String username,String pwd) throws SQLException {
		try {
//			System.out.println(username+" "+pwd+"11");
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:D:/maoliang.db");
//			System.out.println(username+" "+pwd+"22");
            PreparedStatement ps = null;
            String sql = "UPDATE MLuser SET pwd =? WHERE username =?" ;
            ps = conn.prepareStatement(sql);
            ps.setString(1,pwd);
            ps.setString(2,username);
            ps.executeUpdate();
//            System.out.println(username+" "+pwd+"33");
            ps.close();
	        conn.close();
        } catch (Exception e) {
//        	 System.out.println(username+" "+pwd+"44");
            e.printStackTrace();
        }
		
	}
	@Override
	public int find(String name) throws SQLException {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:D:/maoliang.db");
            String sql = "SELECT * FROM MLuser WHERE username =?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,name);
   			ResultSet rs=ps.executeQuery();
   			if(rs.next()) {
   				return 1;
   	         }else {
   	            return 0;
   	         }
   		} catch (SQLException e) {
   				// TODO Auto-generated catch block
   				e.printStackTrace();
   		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	
	public void register(user user) throws SQLException {
			try {
				Class.forName("org.sqlite.JDBC");
			
			Connection conn = DriverManager.getConnection("jdbc:sqlite:D:/maoliang.db");
//	    	插入共同信息
			String sql = "INSERT INTO MLuser (username, pwd, power,question, answer) VALUES (?, ?, ?,?, ?)";
	        PreparedStatement ps = conn.prepareStatement(sql);
	            ps.setString(1, user.getUsername());
	            ps.setString(2, user.getPwd());
	            ps.setInt(3, user.getPower());
	            ps.setString(4, user.getQuestion());
	            ps.setString(5, user.getAnswer());
	            ps.executeUpdate();
//	        如果是买家，插入地址和电话
	        if(0==user.getPower()) {
	        	sql = "SELECT userid FROM MLuser WHERE username = ?";
	        	ps = conn.prepareStatement(sql);
	        	ps.setString(1, user.getUsername());
	            ResultSet resultSet = ps.executeQuery();
	            int id = resultSet.getInt(1);
	        	sql = "INSERT INTO MLinfo (userid, phone, address) VALUES (?, ?, ?)";
	            ps = conn.prepareStatement(sql);
	            ps.setInt(1, id);
	            ps.setString(2, user.getPhone());
	            ps.setString(3, user.getAddress());
	            ps.executeUpdate();
	        }
	    }catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}

}
