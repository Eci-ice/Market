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
import vo.user;
/**
 * Servlet implementation class UpdatePriceServlet
 */
public class UpdatePriceServlet extends HttpServlet {
	 private static final long serialVersionUID = 1L;

	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        String newPriceStr = request.getParameter("newPrice");
	        String goodIdStr = request.getParameter("goodId");

	        if (newPriceStr != null && goodIdStr != null) {
	            int newPrice = Integer.parseInt(newPriceStr);
	            int goodId = Integer.parseInt(goodIdStr);

	            goodsql gs = new goodsqlimpl();

	            try {
	                good gf = gs.getGoodById(goodId);

	                if (gf != null) {
	                    gf.setPrice(newPrice);

	                    boolean success = gs.updateGood(gf);


	                    if (success) {
	                    	  HttpSession session = request.getSession();
	                          session.setAttribute("successMessage", "修改价格成功！");
	                        // 发送重定向到 show_goods.jsp 页面
	                        response.sendRedirect("show_goods.jsp");
	                    } else {
	                        response.sendRedirect("failure.jsp");
	                    }
	                } else {
	                    response.sendRedirect("error.jsp");
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	                response.sendRedirect("error.jsp");
	            }
	        } else {
	            response.sendRedirect("error.jsp");
	        }
	    }
	}