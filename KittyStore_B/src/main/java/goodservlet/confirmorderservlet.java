package goodservlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sql.goodsql;
import sql.ordersql;
import sqlimpl.goodsqlimpl;
import sqlimpl.ordersqlimpl;


public class confirmorderservlet extends HttpServlet {
	
	
	  @Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        doPost(request, response);
	    }

	    @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        int orderid = Integer.parseInt(request.getParameter("orderid"));
	        int state = 0;//未冻结
	        ordersql os = new ordersqlimpl();
	        int goodid=-1;
			try {
				goodid = os.searchid(orderid);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        if (goodid>=0) {
	            	goodsql gs = new goodsqlimpl();
	            	try {
	            		os.modifystate(orderid, 1);
//	            		//如果库存为0，就将商品冻结
//	            		if(returnNumber(goodid)==0) {
//							gs.modifystate(goodid, 1);	            			
//	            		}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		            request.setAttribute("message", "商品成功冻结");
		            request.setAttribute("to","show_goods");
		            request.getRequestDispatcher("success.jsp").forward(request, response);
	         }
		     else {
				    request.setAttribute("err","该商品无法冻结");
				    request.setAttribute("to","show_goods");
				    request.getRequestDispatcher("error.jsp").forward(request,response);
		     }
	    }
	    
		public int returnNumber(int goodid) throws SQLException {
			try {
				Class.forName("org.sqlite.JDBC");
				Connection conn = DriverManager.getConnection("jdbc:sqlite:D:/maoliang.db");
				PreparedStatement ps = null;
				String sql = "select number from MLgood  where goodid = ?";
			    ps = conn.prepareStatement(sql);
			    ps.setInt(1, goodid);
			    ps.executeQuery();
			    ResultSet rs=ps.executeQuery();
			    int num = 0;
	   			if(rs.next()) {
	   				num=rs.getInt(1);
	   				ps.close();
	   	   			conn.close();
	   	   			
	   	         }
	   			conn.close();
	   			return num;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			return 0;
		}

}
