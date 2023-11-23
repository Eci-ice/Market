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

/**
 * Servlet implementation class creatgoodservlet
 */
public class searchgoodservlet extends HttpServlet {
	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        // 获取搜索关键词
	        String keyword = request.getParameter("keyword");
	        // 查询数据库
	        List<String> results = searchDatabase(keyword);
	        // 将结果转换为JSON
	        String json = new Gson().toJson(results);
	        // 返回JSON响应
	        response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");
	        response.getWriter().write(json);
	    }

	    private List<String> searchDatabase(String keyword) {
	        goodsql gs = new goodsqlimpl();
	        try {
	            // 调用search方法获取匹配的商品
	            List<good> goods = gs.searchls(keyword);
	            // 提取商品名称
	            List<String> names = new ArrayList<>();
	            for (good g : goods) {
	                names.add(g.getGoodname());
	            }
	            return names;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            // 如果发生错误，返回一个空列表
	            return new ArrayList<>();
	        }
	    }

}
