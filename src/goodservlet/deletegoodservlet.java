package goodservlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sql.goodsql;
import sql.ordersql;
import sqlimpl.goodsqlimpl;
import sqlimpl.ordersqlimpl;
import vo.good;
import vo.order;

/**
 * Servlet implementation class deletegoodservlet
 */
public class deletegoodservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int   goodid = Integer.parseInt(request.getParameter("goodid"));
		goodsql gs = new goodsqlimpl();
	    try {
			if ( gs.remove(goodid) > 0) {
				ordersql ors = new ordersqlimpl();
				ors.deletegood(goodid);//删除对应订单
				gs.remove(goodid);
				List<order> orList = null;
				List<good> gList = null;
      			 try {
      				    orList = ors.showall();
      					gList = gs.showall();
      			 } catch (SQLException e) {
      				e.printStackTrace();
      			 }
      			request.setAttribute("orL", orList);
      			session.setAttribute("gL", gList);
			    request.setAttribute("message","商品删除成功");
			    request.setAttribute("to","show_goods");
			 	request.getRequestDispatcher("success.jsp").forward(request,response);
			} else {
			    // 商品不存在或删除失败
			    request.setAttribute("err","商品不存在或删除失败");
			    request.setAttribute("to","show_goods");
			 	request.getRequestDispatcher("error.jsp").forward(request,response);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
