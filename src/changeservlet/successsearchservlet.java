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

public class successsearchservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();    
		String keyword = request.getParameter("keyword");
	        // 查询数据库
	    goodsql gs = new goodsqlimpl();
	        List<good> goods = null;
	        try {
	        	goods = gs.searchls(keyword);
	        	System.out.print(keyword);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        // 将结果存储在会话中
	        
	        session.setAttribute("sL", goods);
	        // 重定向到search_list.jsp页面
	        response.sendRedirect("search_list.jsp");
	    
	}
	

}