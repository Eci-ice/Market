package goodservlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
 * Servlet implementation class addlikeservlet
 */
public class addlikeservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addlikeservlet() {
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
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");	
		HttpSession session = request.getSession();
		user u = (user)session.getAttribute("admin");
		String iscancel = (String) request.getParameter("iscancel");
	 	int goodid = Integer.parseInt(request.getParameter("goodid"));
	 	
	        goodsql gs = new goodsqlimpl();
	    if(iscancel.equals("0")) {
	    	try {
				gs.addtolike(goodid, u.getUserid());
    			request.setAttribute("message","成功收藏");
    			request.setAttribute("to","buyermain");
				request.getRequestDispatcher("success.jsp").forward(request,response); 
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }else {
	    	//取消收藏
	    	try {
				Class.forName("org.sqlite.JDBC");
				Connection conn = DriverManager.getConnection("jdbc:sqlite:D:/maoliang.db");
		        PreparedStatement ps = null;
		        String sql = "update MLbuying set islike =0 where goodid = ?";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, goodid);
				ps.executeUpdate();
		        ps.close();
		        conn.close();
		        request.setAttribute("message","成功取消收藏");
    			request.setAttribute("to","buyermain");
				request.getRequestDispatcher("success.jsp").forward(request,response); 
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	        
	}

}
