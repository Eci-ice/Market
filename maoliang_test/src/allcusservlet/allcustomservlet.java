package allcusservlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sql.ordersql;
import sql.usersql;
import sqlimpl.ordersqlimpl;
import sqlimpl.usersqlimpl;
import vo.order;
import vo.user;

/**
 * Servlet implementation class allcustomservlet
 */
public class allcustomservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public allcustomservlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		System.out.println("我进入了allcustomservlet");
		usersql us = new usersqlimpl();
		 List<user> urList = null;
		 try {
			 urList = us.searchalluser();
		 } catch (SQLException e) {
			e.printStackTrace();
		 }
			
		 request.setAttribute("urL", urList);
		 request.getRequestDispatcher("allusers.jsp").forward(request,response);
	}

}
