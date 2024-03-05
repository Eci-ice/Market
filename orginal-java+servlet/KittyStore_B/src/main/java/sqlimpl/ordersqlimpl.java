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
	         String sql = "insert into MLorder(address,telephone,buyername,goodid,number,orderstate,owner) values(?,?,?,?,?,1,?)";
	         PreparedStatement ps  = conn.prepareStatement(sql);
	         ps.setString(1, order.getAddress());
	         ps.setString(2, order.getTelephone());
	         ps.setString(3, order.getBuyername());
	         ps.setInt(4, order.getGoodid());
	         ps.setInt(5, order.getNumber());
	         ps.setInt(6, order.getOwner());
	         ps.executeUpdate();
//	         String sql2 = "Update MLorder set number = number - ? where goodid = ?";
//	         ps  = conn.prepareStatement(sql2);
//	         ps.setInt(1, order.getNumber());
//	         ps.setInt(2, order.getGoodid());
//	         ps.executeUpdate();
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
            String sql = "UPDATE MLorder SET orderstate = ? WHERE orderid = ?";
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

	        String sql = "UPDATE MLorder SET orderstate = -1 WHERE orderid = ?";
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
            String sql = "SELECT goodid FROM MLorder WHERE orderid = ?";
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
	public List<order> showall(int userid) throws SQLException {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:D:/maoliang.db");
            PreparedStatement ps = null;
			String sql = "select * from MLorder WHERE owner = ?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, userid);
			ResultSet rs=ps.executeQuery();
			List<order> orL=new ArrayList<order>();
			order or=null;
			while(rs.next()) {
				or=new order();
				or.setOrderid(rs.getInt(1));
				or.setAddress(rs.getString(2));
				or.setTelephone(rs.getString(3));
				or.setBuyername(rs.getString(4));
				or.setGoodid(rs.getInt(5));
				or.setNumber(rs.getInt(6));
				or.setOrderstate(rs.getInt(7));
				or.setOwner(rs.getInt(8));
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
	public List<order> showall2(String buyername) throws SQLException {
		try {
			System.out.println("name"+buyername);
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:D:/maoliang.db");
            PreparedStatement ps = null;
			String sql = "select * from MLorder WHERE buyername = ?";

			ps=conn.prepareStatement(sql);
			ps.setString(1, buyername);
			
			ResultSet rs=ps.executeQuery();
			List<order> orL=new ArrayList<order>();
			order or=null;
			while(rs.next()) {
				or=new order();
				or.setOrderid(rs.getInt(1));
				or.setAddress(rs.getString(2));
				or.setTelephone(rs.getString(3));
				or.setBuyername(rs.getString(4));
				or.setGoodid(rs.getInt(5));
				or.setNumber(rs.getInt(6));
				or.setOrderstate(rs.getInt(7));
				or.setOwner(rs.getInt(8));
				orL.add(or);
				System.out.println("or:");
				System.out.println(or);
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
	        String sql = "update MLorder set orderstate = 1 WHERE orderid = ?";
			ps = conn.prepareStatement(sql);
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
	@Override
	public int searchstate(int orderid) throws SQLException{
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:D:/maoliang.db");
			PreparedStatement ps = null;
            String sql = "SELECT orderstate FROM MLorder WHERE orderid = ?";
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
	public order getOrderById(int orderId) throws SQLException {
		order resultOrder = null;

	    try {
	        // 加载JDBC驱动
	        Class.forName("org.sqlite.JDBC");
	        // 建立数据库连接
	        Connection conn = DriverManager.getConnection("jdbc:sqlite:D:/maoliang.db");

	        // 准备SQL语句
	        String sql = "SELECT * FROM MLorder WHERE orderid = ?";
	        PreparedStatement ps = conn.prepareStatement(sql);
	        
	        // 设置参数
	        ps.setInt(1, orderId);

	        // 执行查询
	        ResultSet rs = ps.executeQuery();

	        // 如果查询结果存在，创建order对象并填充数据
	        if (rs.next()) {
	            resultOrder = new order();
	            resultOrder.setOrderid(rs.getInt("orderid"));
	            resultOrder.setAddress(rs.getString("address"));
	            resultOrder.setTelephone(rs.getString("telephone"));
	            resultOrder.setBuyername(rs.getString("buyername"));
	            resultOrder.setGoodid(rs.getInt("goodid"));
	            resultOrder.setNumber(rs.getInt("number"));
	            resultOrder.setOrderstate(rs.getInt("orderstate"));
	            resultOrder.setOwner(rs.getInt("owner"));
	            // 更多字段...
	        }

	        // 关闭资源
	        rs.close();
	        ps.close();
	        conn.close();

	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	        // 可以考虑更详细的错误处理
	    }

	    return resultOrder;
	}

}
