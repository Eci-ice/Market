package goodservlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * Servlet implementation class findallorderservlet
 */
public class createcartservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public createcartservlet() {
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
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");	
		HttpSession session = request.getSession();
		user u = (user)session.getAttribute("admin");
	 	int goodid = Integer.parseInt(request.getParameter("goodid"));
	 	
	        goodsql gs = new goodsqlimpl();
	        try {
	        	//检测商品id是否存在
				if(gs.uniquecart(goodid,u.getUserid())==1) {
					gs.addtocart(goodid, u.getUserid());
				}
				else {
					gs.modifybuynumber(u.getUserid(), -1);//负一表示自增1
				}
				List<good> gList = null;
				 try {
    					gList = gs.showall(u.getUserid());
    			 } catch (SQLException e) {
    				e.printStackTrace();
    			 }
    			session.setAttribute("cL", gList);
				 request.setAttribute("message","成功添加到购物车");
				 request.setAttribute("to","buyermain");
				request.getRequestDispatcher("success.jsp").forward(request,response); 
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

}
