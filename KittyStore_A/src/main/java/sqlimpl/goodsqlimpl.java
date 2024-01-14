package sqlimpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import sql.goodsql;
import vo.good;

public class goodsqlimpl implements goodsql{

    Connection conn = null;
    
	@Override
	public void add(good good) throws SQLException {
		try {
			Class.forName("org.sqlite.JDBC");
	         Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Eclipse WorkSpace\\DataBase\\maoliang_A.db");
	         String sql = "insert into MLgood(goodname,description,price,picture,state,number,kind,subkind,owner) values(?,?,?,?,?,?,?,?,?)";
	         PreparedStatement ps  = conn.prepareStatement(sql);
	         ps.setString(1, good.getGoodname());
	         ps.setString(2, good.getDescription());
	         ps.setDouble(3, good.getPrice());
	         ps.setString(4, good.getPicture());
	         ps.setInt(5, good.getState());
	         ps.setInt(6, good.getNumber());
	         ps.setString(7, good.getKind());
	         ps.setString(8, good.getSubkind());
	         ps.setInt(9, good.getOwner());
	         ps.executeUpdate();
	         sql = "SELECT * FROM MLgood WHERE goodname=?";//在售
	         ps = conn.prepareStatement(sql);
	         ps.setString(1, good.getGoodname());
	   		 ResultSet rs=ps.executeQuery();
	         int goodid = -1;
	         if (rs.next()) {
	             goodid = rs.getInt(1);//获得刚刚创建的id
	         }
	         ps.close();
	         String sql1 = "insert into MLhistorygood(goodid,goodname,description,price,picture,number,kind,subkind,createdate,owner) values(?,?,?,?,?,?,?,?,?,?)";
	         PreparedStatement ps1  = conn.prepareStatement(sql1);
	         ps1.setInt(1, goodid);
	         ps1.setString(2, good.getGoodname());
	         ps1.setString(3, good.getDescription());
	         ps1.setDouble(4, good.getPrice());
	         ps1.setString(5, good.getPicture());
	         ps1.setInt(6, good.getNumber());
	         ps1.setString(7, good.getKind());
	         ps1.setString(8, good.getSubkind());
	         // 获取当前时间
	         LocalDateTime currentTime = LocalDateTime.now();
	         // 定义日期时间格式
	         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	         // 格式化当前时间
	         String formattedDateTime = currentTime.format(formatter);
	         ps1.setString(9, formattedDateTime);
	         ps1.setInt(10, good.getOwner());
	         ps1.executeUpdate();
	         ps1.close();
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
			Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Eclipse WorkSpace\\DataBase\\maoliang_A.db");
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

	//查找商品名是否唯一
	@Override
	public int unique(String name) throws SQLException {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Eclipse WorkSpace\\DataBase\\maoliang_A.db");
            String sql = "SELECT * FROM MLgood WHERE state=0 and goodname =?";//在售
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
   			ResultSet rs=ps.executeQuery();
   			if(rs.next()) {
   				ps.close();
   	   			conn.close();
	        	return 0;
   	         }else {
   	        	ps.close();
   	   			conn.close();
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
	//查找商品是否唯一
		@Override
		public int oldunique() throws SQLException {
			try {
				Class.forName("org.sqlite.JDBC");
				Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Eclipse WorkSpace\\DataBase\\maoliang_A.db");
	            String sql = "SELECT * FROM MLgood WHERE state=0";
	            PreparedStatement ps = conn.prepareStatement(sql);
	   			ResultSet rs=ps.executeQuery();
	   			if(rs.next()) {
	   				ps.close();
	   	   			conn.close();
	   		        return 0;
	   	         }else {
	   	        	ps.close();
	   	   			conn.close();
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
			Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Eclipse WorkSpace\\DataBase\\maoliang_A.db");
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
			Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Eclipse WorkSpace\\DataBase\\maoliang_A.db");            PreparedStatement ps = null;
			String sql = "select goodid,goodname,description,price,picture,state,number,kind,subkind,owner from MLgood where state=0";
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
					g.setKind(rs.getString(8));
					g.setSubkind(rs.getString(9));
					g.setOwner(rs.getInt(10));
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
			Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Eclipse WorkSpace\\DataBase\\maoliang_A.db");
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
				g.setKind(rs.getString(8));
				g.setSubkind(rs.getString(9));
				g.setOwner(rs.getInt(10));
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
	public List<good> searchls(String keyword,String kind,int power,int userid,int ishistory) throws SQLException {
	    Connection conn = null;
	    PreparedStatement ps = null;

	    try {
	        Class.forName("org.sqlite.JDBC");
	        conn = DriverManager.getConnection("jdbc:sqlite:C:\\Eclipse WorkSpace\\DataBase\\maoliang_A.db");
	        
	        
	        if(1==ishistory) {
	        	String sql = "SELECT * FROM MLhistorygood WHERE goodname LIKE ? AND kind = ? AND  owner = ?";
		        ps = conn.prepareStatement(sql);
		        // 添加 '%' 来匹配任何以关键词开头的商品名称
		        ps.setString(1, keyword + "%");
		        ps.setString(2, kind);
		        ps.setInt(3, userid);
	        }
	        else {
	        	String sql = "SELECT * FROM MLgood WHERE goodname LIKE ? AND kind = ? AND owner = ?";
		        if(0==power) {//买家
		        	sql = "SELECT * FROM MLgood WHERE goodname LIKE ? AND kind= ? AND state = 0";
		        }
		        ps = conn.prepareStatement(sql);
		        // 添加 '%' 来匹配任何以关键词开头的商品名称
		        ps.setString(1, keyword + "%");
		        ps.setString(2, kind);
		        if(1==power) {
		        	ps.setInt(3, userid);
		        }
	        }
	        ResultSet rs = ps.executeQuery();
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
				g.setKind(rs.getString(8));
				g.setSubkind(rs.getString(9));
				g.setOwner(rs.getInt(10));
				gL.add(g);
			}
	        ps.close();
	        conn.close();
	        return gL;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	    return null;
	}

	@Override
	public List<good> showall(int userid) throws SQLException {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Eclipse WorkSpace\\DataBase\\maoliang_A.db");
            PreparedStatement ps = null;
			String sql = "select * from MLgood WHERE owner = ?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, userid);
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
				g.setKind(rs.getString(8));
				g.setSubkind(rs.getString(9));
				g.setOwner(rs.getInt(10));
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
	public List<good> showhistoryall(int userid) throws SQLException {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Eclipse WorkSpace\\DataBase\\maoliang_A.db");
            PreparedStatement ps = null;
			String sql = "select * from MLhistorygood WHERE owner = ?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, userid);
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
				g.setNumber(rs.getInt(6));
				g.setKind(rs.getString(7));
				g.setSubkind(rs.getString(8));
				g.setCreatedate(rs.getString(9));
				g.setOwner(rs.getInt(10));
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
	public good getGoodById(int goodId) throws SQLException {
	    try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Eclipse WorkSpace\\DataBase\\maoliang_A.db");
	        String query = "SELECT * FROM MLgood WHERE goodid = ?";
	        PreparedStatement preparedStatement = conn.prepareStatement(query);
	        preparedStatement.setInt(1, goodId);

	        ResultSet resultSet = preparedStatement.executeQuery();
	        good gf = null;

	        if (resultSet.next()) {
	            gf = new good();
	            gf.setGoodid(resultSet.getInt("goodid"));
	            gf.setGoodname(resultSet.getString("goodname"));
	            gf.setDescription(resultSet.getString("description"));
	            gf.setPrice(resultSet.getDouble("price"));
	            gf.setPicture(resultSet.getString("picture"));
	            gf.setState(resultSet.getInt("state"));
	            gf.setNumber(resultSet.getInt("number"));
	            gf.setKind(resultSet.getString("kind"));
	            gf.setSubkind(resultSet.getString("subkind"));
	            gf.setOwner(resultSet.getInt("owner"));
	            // 根据数据库列名设置相应的属性
	        }

	        resultSet.close();
	        preparedStatement.close();
	        conn.close();

	        return gf;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    return null;
	}


	@Override
	public boolean updateGood(good good) throws SQLException {
	    try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Eclipse WorkSpace\\DataBase\\maoliang_A.db");
	        String updateQuery = "UPDATE MLgood SET goodname = ?, description = ?, price = ?, picture = ?, state = ?, number = ?, kind = ?, subkind = ?, owner = ? WHERE goodid = ?";
	        PreparedStatement preparedStatement = conn.prepareStatement(updateQuery);

	        preparedStatement.setString(1, good.getGoodname());
	        preparedStatement.setString(2, good.getDescription());
	        preparedStatement.setDouble(3, good.getPrice());
	        preparedStatement.setString(4, good.getPicture());
	        preparedStatement.setInt(5, good.getState());
	        preparedStatement.setInt(6, good.getNumber());
	        preparedStatement.setString(7, good.getKind());
	        preparedStatement.setString(8, good.getSubkind());
	        preparedStatement.setInt(9, good.getOwner());
	        preparedStatement.setInt(10, good.getGoodid());

	        int rowsAffected = preparedStatement.executeUpdate();

	        preparedStatement.close();
	        conn.close();

	        return rowsAffected > 0; // 返回更新是否成功
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    return false;
	}

}
