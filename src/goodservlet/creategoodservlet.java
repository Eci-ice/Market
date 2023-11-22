package goodservlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import sql.goodsql;
import sqlimpl.goodsqlimpl;
import vo.good;

/**
 * Servlet implementation class creatgoodservlet
 */
public class creategoodservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}


	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			HttpSession session = request.getSession(); 	
			String  goodname = request.getParameter("goodname");
		 	String  description = request.getParameter("description");
		 	String priceStr = request.getParameter("price");
		 	String kind = request.getParameter("kind");
		 	String subkind = request.getParameter("subkind");
		 	double price =0.0;
//		 	检测价格是否为浮点数
		 	try {
		 	    price = Double.parseDouble(priceStr);
		 	    System.out.print(price);
		 	} catch (NumberFormatException e) {
		 	    try {
		 	        price = (double) Integer.parseInt(priceStr);
		 	    } catch (NumberFormatException ex) {
		 	    	System.out.println("error");
  	 	    	    request.getRequestDispatcher("upload_goods.jsp").forward(request,response); 
		 	    }
		 	}
//		 	设置图片
		 	String  picture = request.getParameter("picture");
	        int state = 0;
	        int number = 1;
	        if(null==picture) {
	        	picture="./img/buyer/food-1.png";//设置默认值
	        }
//	        上传商品
	        good g=new good();
	        goodsql gs = new goodsqlimpl();
			good gf = null;
	        try {
	        	//检测商品名称是否唯一
				if(gs.unique(goodname)==1) {
					gf=new good();
					gf.setGoodname(goodname);
					gf.setDescription(description);
					gf.setPrice(price);
					gf.setPicture(picture);
					gf.setState(state);
					gf.setNumber(number);
					gf.setKind(kind);
					gf.setSubkind(subkind);
					gs.add(gf);
					List<good> gList = null;
	       			 try {
	       					gList = gs.showall();
	       			 } catch (SQLException e) {
	       				e.printStackTrace();
	       			 }
	       			session.setAttribute("gL", gList);
	       			System.out.println("pic"+gf.getPicture());
					request.getRequestDispatcher("show_goods.jsp").forward(request,response); 
				}
				else {
					//注释的代码与前端script联系，出现提示框，但是这样操作会引发sqlite多线程锁定
//					boolean isDuplicate = true;
//					request.setAttribute("isDuplicate", isDuplicate);

		 	    	request.setAttribute("err","只能上架一个商品，请先下架当前商品！");
				    request.setAttribute("to","upload_goods");
				    request.getRequestDispatcher("error.jsp").forward(request,response);
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("y78y");
				e.printStackTrace();
			}

		
	}

}
