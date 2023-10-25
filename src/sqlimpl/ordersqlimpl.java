package sqlimpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sql.ordersql;
import vo.good;
import vo.order;

public class ordersqlimpl implements ordersql{

	@Override
	public void add(order order) throws SQLException {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:D:/maoliang.db");
	         PreparedStatement pstmt = null;
	         String sql = "insert into order_show(address,telephone,buyername) values(?,?,?)";
	         PreparedStatement ps  = conn.prepareStatement(sql);
	         ps.setString(1, order.getAddress());
	         ps.setString(2, order.getTelephone());
	         ps.setString(3, order.getBuyername());
	         ps.executeUpdate();
	         String sql2 = "insert into order_rear(goodid,orderstate) values(?,0)";
	         ps  = conn.prepareStatement(sql2);
	         ps.setInt(1, order.getGoodid());
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
	public void remove(int orderid, String username) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modifystate(int orderid,int tostate) throws SQLException{
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:D:/maoliang.db");
            PreparedStatement ps = null;
            String sql = "UPDATE order_rear SET orderstate = ? WHERE orderid = ?";
	        ps = conn.prepareStatement(sql);
	        ps.setInt(1, tostate);
	        ps.setInt(2, orderid);
	        ps.executeUpdate();
	        ps.close();
	        conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}

	@Override
	public void deletegood(int goodid) throws SQLException {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:D:/maoliang.db");
	        PreparedStatement ps = null;
	        //暂时先不考虑未删掉的order_show
//	        String sql = "SELECT orderid FROM order_rear WHERE goodid = ?";
//	        String sql = "DELETE FROM order_show WHERE orderid = ?";
//            ps = conn.prepareStatement(sql);
//            ps.setInt(1, orderid);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//            	int ans=rs.getInt(1);
//            	ps.close();
//   	         	conn.close();
//	            return ans;
//            }S
	        
	        String sql = "DELETE FROM order_rear WHERE goodid = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, goodid);
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
	public int searchid(int orderid) throws SQLException {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:D:/maoliang.db");
            PreparedStatement ps = null;
            String sql = "SELECT goodid FROM order_rear WHERE orderid = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, orderid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
            	int ans=rs.getInt(1);
            	ps.close();
   	         	conn.close();
	            return ans;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		return -1;
	}

	@Override
	public List<order> showall() throws SQLException {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:D:/maoliang.db");
            PreparedStatement ps = null;
			String sql = "select * from order_show";
			ps =conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			String sql2 = "select * from order_rear";
			ps =conn.prepareStatement(sql2);
			ResultSet rs2=ps.executeQuery();
			List<order> orL=new ArrayList<order>();
			order or=null;
			while(rs.next() && rs2.next()) {
				or=new order();
				or.setOrderid(rs.getInt(1));
				or.setAddress(rs.getString(2));
				or.setTelephone(rs.getString(3));
				or.setBuyername(rs.getString(4));
				or.setGoodid(rs2.getInt(2));
				or.setOrderstate(rs2.getInt(3));
				orL.add(or);
			}
			ps.close();
	         conn.close();
			return orL;
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
	public void deleteorder(int orderid) throws SQLException {
		// TODO Auto-generated method stub
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:D:/maoliang.db");
	        PreparedStatement ps = null;
	        String sql = "DELETE FROM order_show WHERE orderid = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, orderid);
			ps.executeUpdate();
			String sql2 = "DELETE FROM order_rear WHERE orderid = ?";
			ps = conn.prepareStatement(sql2);
			ps.setInt(1, orderid);
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
	

}
