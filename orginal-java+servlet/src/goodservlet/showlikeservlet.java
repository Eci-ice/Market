package goodservlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sql.goodsql;
import sqlimpl.goodsqlimpl;
import vo.good;
import vo.user;

/**
 * Servlet implementation class showlikeservlet1
 */
public class showlikeservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public showlikeservlet() {
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
    	user u = (user)session.getAttribute("admin");
		goodsql gs=new goodsqlimpl();
		 List<good> gList = null;
		 try {
				gList = gs.showlike(u.getUserid());
		 } catch (SQLException e) {
			e.printStackTrace();
		 }
		 session.setAttribute("likeList", gList);
		 
		 request.getRequestDispatcher("BuyerLike.jsp").forward(request,response);
	}

}
