package goodservlet;

import java.io.IOException;
import java.sql.Connection;
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
	            		os.modifystate(orderid, 1);//状态一：冻结
						gs.modifystate(goodid, 1);
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

}
