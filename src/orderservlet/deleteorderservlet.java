package orderservlet;

import java.io.IOException;
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
import vo.user;

/**
 * Servlet implementation class historygoodservlet
 */
public class deleteorderservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public deleteorderservlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		user u = (user)session.getAttribute("admin");
		int  orderid = Integer.parseInt(request.getParameter("orderid")); 
		ordersql ors=new ordersqlimpl();
		List<order> orList = null;
		 try {
			ors.deleteorder(orderid);
  			orList = ors.showall(u.getUserid());
		 } catch (SQLException e) {
			e.printStackTrace();
		 }
			
		 request.setAttribute("orL", orList);
		 request.getRequestDispatcher("show_userinfo.jsp").forward(request,response);
	}

}
