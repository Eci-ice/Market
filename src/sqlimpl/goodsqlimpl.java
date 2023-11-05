package sqlimpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sql.goodsql;
import vo.good;

public class goodsqlimpl implements goodsql{

    Connection conn = null;

    public Connection getConn() {
        try {
        	Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:D:/maoliang.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void closeConn() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
	@Override
	public void add(good good) throws SQLException {
		try {
			Class.forName("org.sqlite.JDBC");
	         Connection conn = DriverManager.getConnection("jdbc:sqlite:D:/maoliang.db");
	         String sql = "insert into MLgood(goodname,description,price,picture,state,number,kind) values(?,?,?,?,?,?,?)";
	         PreparedStatement ps  = conn.prepareStatement(sql);
	         ps.setString(1, good.getGoodname());
	         ps.setString(2, good.getDescription());
	         ps.setDouble(3, good.getPrice());
	         ps.setString(4, good.getPicture());
	         ps.setInt(5, good.getState());
	         ps.setInt(6, good.getNumber());
	         ps.setString(7, good.getKind());
	         ps.executeUpdate();
	         ps.close();
	         conn.close();
		} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public int remove(int goodid) throws SQLException {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:D:/maoliang.db");
	        PreparedStatement ps = null;
	        String sql = "DELETE FROM MLgood WHERE goodid = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, goodid);
			int rowsDeleted = ps.executeUpdate();
	        ps.close();
	        conn.close();
			return rowsDeleted;
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return 0;
	}

	@Override
	public int unique() throws SQLException {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:D:/maoliang.db");
            String sql = "SELECT * FROM MLgood WHERE state=0";
            PreparedStatement ps = conn.prepareStatement(sql);
   			ResultSet rs=ps.executeQuery();
   			if(rs.next()) {
   		        ps.close();
   		        conn.close();
   	            return 0;
   	         }else {
   	            return 1;
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
	
	@Override
	public void modifystate(int goodid,int tostate) throws SQLException {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:D:/maoliang.db");
            PreparedStatement ps = null;
            String sql = "UPDATE MLgood SET state = ? WHERE goodid = ?";
	        ps = conn.prepareStatement(sql);
	        ps.setInt(1, tostate);
	        ps.setInt(2, goodid);
	        ps.executeUpdate();
	        ps.close();
	        conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	@Override
	public List<good> shownow() throws SQLException {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:D:/maoliang.db");
            PreparedStatement ps = null;
			String sql = "select goodid,goodname,description,price,picture,state,number,kind from MLgood where state=0";
			ps=conn.prepareStatement(sql);
				
			ResultSet rs=ps.executeQuery();
			List<good> gL=new ArrayList<good>();
			good g=null;
			while(rs.next()) {
					g=new good();
					g.setGoodid(rs.getInt(1));
					g.setGoodname(rs.getString(2));
					g.setDescription(rs.getString(3));
					g.setPrice(rs.getDouble(4));
					g.setPicture(rs.getString(5));
					g.setState(rs.getInt(6));
					g.setNumber(rs.getInt(7));
					gL.add(g);
			}
	         ps.close();
	         conn.close();
			return gL;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public good search(int goodid) throws SQLException {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:D:/maoliang.db");
            String sql = "SELECT * FROM MLgood WHERE goodid=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, goodid);
   			ResultSet rs=ps.executeQuery();
   			good g=null;
   			if(rs.next()) {
   				g=new good();
				g.setGoodid(rs.getInt(1));
				g.setGoodname(rs.getString(2));
				g.setDescription(rs.getString(3));
				g.setPrice(rs.getDouble(4));
				g.setPicture(rs.getString(5));
				g.setState(rs.getInt(6));
				g.setNumber(rs.getInt(7));
				ps.close();
		        conn.close();
				return g;
   	         }else {
   	            return null;
   	         }
   		} catch (SQLException e) {
   				// TODO Auto-generated catch block
   				e.printStackTrace();
   		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	



	@Override
	public List<good> showall() throws SQLException {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:D:/maoliang.db");
            PreparedStatement ps = null;
			String sql = "select * from MLgood";
			ps=conn.prepareStatement(sql);
				
			ResultSet rs=ps.executeQuery();
			List<good> gL=new ArrayList<good>();
			good g=null;
			while(rs.next()) {
				g=new good();
				g.setGoodid(rs.getInt(1));
				g.setGoodname(rs.getString(2));
				g.setDescription(rs.getString(3));
				g.setPrice(rs.getDouble(4));
				g.setPicture(rs.getString(5));
				g.setState(rs.getInt(6));
				g.setNumber(rs.getInt(7));
				gL.add(g);
			}
			ps.close();
	        conn.close();
			return gL;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
