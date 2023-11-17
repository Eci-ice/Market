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
			HttpSession session = request.getSession(); 	
			String  goodname = request.getParameter("goodname");
		 	String  description = request.getParameter("description");
		 	String priceStr = request.getParameter("price");
		 	double price =0.0;
		 	try {
		 	    price = Double.parseDouble(priceStr);
		 	    System.out.print(price);
		 	} catch (NumberFormatException e) {
		 	    try {
		 	        price = (double) Integer.parseInt(priceStr);
		 	    } catch (NumberFormatException ex) {
		 	    	request.setAttribute("err","请输入数字！");
				    request.setAttribute("to","upload_goods");
				    request.getRequestDispatcher("error.jsp").forward(request,response);
				    return;
		 	    }
		 	}
		 	String  picture = request.getParameter("picture");
	        int state = 0;
	        int number = 1;
	        if(null==picture) {
	        	picture="E:\\Wiki\\KittyStoreNew\\src\\main\\webapp\\img\\buyer\\food-1.jpg";//设置默认值
	        }
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
					JOptionPane.showMessageDialog(null, "商品名称重复", "提示",JOptionPane.PLAIN_MESSAGE);
					request.getRequestDispatcher("upload_goods.jsp").forward(request,response); 
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("y78y");
				e.printStackTrace();
			}

		
	}

}