package orderservlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sql.ordersql;
import sqlimpl.ordersqlimpl;
import vo.order;
import vo.user;

/**
 * Servlet implementation class userorderservlet
 */
public class userorderservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public userorderservlet() {
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
		System.out.println("我进入了userorderservlet");
		user u = (user)session.getAttribute("admin"); 
		ordersql ors=new ordersqlimpl();
		 List<order> orList = null;
		 System.out.println("u.id:");
		 System.out.println(u.getUserid());
		 try {
				orList = ors.showall2(u.getUserid());
		 } catch (SQLException e) {
			e.printStackTrace();
		 }
			
		 request.setAttribute("orL", orList);
		 request.getRequestDispatcher("BuyerHistory.jsp").forward(request,response);
	}

}
