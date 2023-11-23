package goodservlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import sql.goodsql;
import sqlimpl.goodsqlimpl;
import vo.good;
import vo.user;

/**
 * Servlet implementation class creategoodservletabc
 */
public class createmoregoodservlet extends HttpServlet {
	  private static final long serialVersionUID = 1L;

	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    	request.setCharacterEncoding("UTF-8");
	    	response.setCharacterEncoding("UTF-8");
	    	HttpSession session = request.getSession();    
	    	user u = (user)session.getAttribute("admin");
	    	String productListStr = request.getParameter("productList");
	    	System.out.print(productListStr);
	    	
	    	Type listType = new TypeToken<ArrayList<good>>(){}.getType();
	    	List<good> productList = new Gson().fromJson(productListStr, listType);

	    	// 创建数据库访问实例
	    	goodsql gs = new goodsqlimpl();

	    	// 处理每个商品信息
	    	for (good good : productList) {
	    		try {
		    	    String goodname = good.getGoodname();
		    	    String description = good.getDescription();
		    	    double price = good.getPrice();
		    	    String picture = good.getPicture();
	
		            int state = 0;
		            int number = 1;
		            String kind = good.getKind();
		            String subkind  = good.getSubkind();
		            int owner = u.getUserid();
		            
		            good gf = new good();
		            if(gs.unique(goodname)==1) {//对于商品名重复仍需修改
		            gf.setGoodname(goodname);
		            gf.setDescription(description);
		            gf.setPrice(price);
		            gf.setPicture(picture);
		            gf.setState(state);
		            gf.setNumber(number);
		            gf.setKind(kind);
		            gf.setSubkind(subkind);
		            gf.setOwner(owner);
		            }
		            // 插入商品信息到数据库
		            
	                gs.add(gf);
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }

	        // 获取所有商品的列表
	        List<good> allGoodsList = null;
	        try {
	            allGoodsList = gs.showall(u.getUserid());
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        // 设置商品列表到会话
	        session.setAttribute("gL", allGoodsList);
//	        session.setAttribute("gL", gList);
	        // 重定向到商品展示页面
//	        response.sendRedirect("show_goods.jsp");
	        request.getRequestDispatcher("show_goods.jsp").forward(request, response);
	    }

}
