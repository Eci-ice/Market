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
	        if(rs.next()) {
	        	u=new user();
	        	u.setUserid(rs.getInt(1));
	        	u.setUsername(rs.getString(2));
	        	u.setPwd(rs.getString(3));
	        	u.setPower(rs.getInt(4));
	        	
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
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:D:/maoliang.db");
            PreparedStatement ps = null;
            String sql = "UPDATE MLuser SET pwd =? WHERE username =?" ;
            ps = conn.prepareStatement(sql);
            ps.setString(1,pwd);
            ps.setString(2,username);
            ps.executeUpdate();
            ps.close();
	        conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}

}
