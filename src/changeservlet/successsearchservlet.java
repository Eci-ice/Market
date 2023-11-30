package changeservlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sql.goodsql;
import sql.usersql;
import sqlimpl.goodsqlimpl;
import sqlimpl.usersqlimpl;
import vo.good;
import vo.user;

public class successsearchservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");	
		HttpSession session = request.getSession();    
    	user u = (user)session.getAttribute("admin");
    	int power=0;
    	if(null!=u) {
    		power=u.getPower();
    	}
		String keyword = request.getParameter("keyword");
		String kind = request.getParameter("search_kind");
	        // 查询数据库
	    goodsql gs = new goodsqlimpl();
	        List<good> goods = null;
	        try {
	        	goods = gs.searchls(keyword,kind,power,u.getUserid());
	        	System.out.print(keyword);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        // 将结果存储在会话中
	        
	        session.setAttribute("sL", goods);
	        // 重定向到search_list.jsp页面
	        if(1==power) {
	        	response.sendRedirect("search_list.jsp");
	        }
	        else if(0==power){
		        response.sendRedirect("BuyerSearch.jsp");
	        }
	    
	}
	

}