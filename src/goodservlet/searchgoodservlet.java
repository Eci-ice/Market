package goodservlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import sql.goodsql;
import sqlimpl.goodsqlimpl;
import vo.good;
import vo.user;

/**
 * Servlet implementation class creatgoodservlet
 */
//该servlet返回的是搜索框下拉列表
public class searchgoodservlet extends HttpServlet {
	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        // 获取搜索关键词
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
	        List<String> results = null;
	        try {
	            // 调用search方法获取匹配的商品
	            List<good> goods = gs.searchls(keyword,kind,power,u.getUserid());//卖家仅搜索对应userid商品
	            // 提取商品名称
	            results = new ArrayList<>();
	            for (good g : goods) {
	            	results.add(g.getGoodname());
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        
	        // 将结果转换为JSON
	        String json = new Gson().toJson(results);
	        // 返回JSON响应
	        response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");
	        response.getWriter().write(json);
	    }

}
