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

/**
 * Servlet implementation class allhistorygoodservlet
 */
public class allhistorygoodservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public allhistorygoodservlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	//	System.out.println("yesyes");
		HttpSession session = request.getSession(); 
		goodsql gs=new goodsqlimpl();
		 List<good> gList = null;
		// System.out.println("yesyes");
		 try {
				gList = gs.showhistoryall();
		 } catch (SQLException e) {
			e.printStackTrace();
		 }
		 session.setAttribute("gL", gList);
		 
		 request.getRequestDispatcher("show_historygoods.jsp").forward(request,response);
	}

}
