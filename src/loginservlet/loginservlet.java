package loginservlet;

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



import sql.goodsql;
import sql.ordersql;
import sql.usersql;
import sqlimpl.goodsqlimpl;
import sqlimpl.ordersqlimpl;
import sqlimpl.usersqlimpl;
import vo.good;
import vo.order;
import vo.user;

/**
 * Servlet implementation class loginservlet
 */
public class loginservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public loginservlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");//账号
        String pwd = request.getParameter("pwd");//密码

		usersql us = new usersqlimpl();
		user ut = null;
		try {
			ut = us.search(username);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(null!=ut){
             if(pwd.equals(ut.getPwd())){ //密码存在且与数据库的密码一致则跳转
                	HttpSession session = request.getSession();
                	session.setAttribute("admin", ut);
                	if (ut.getPower() == 0) {
                             // 买家权限，进入商品首页
                			 goodsql gs=new goodsqlimpl();
                			 List<good> gList = null;
                			 try {
                					gList = gs.shownow();
                			 } catch (SQLException e) {
                				e.printStackTrace();
                			 }
                			 session.setAttribute("gL", gList);
                             request.getRequestDispatcher("BuyerMain.jsp").forward(request, response);
                    } else if (ut.getPower() == 1) {
                             // 卖家权限，进入后台管理all商品页面
	                    	goodsql gs=new goodsqlimpl();
		           			 List<good> gList = null;
		           			 try {
		           					gList = gs.showall();
		           			 } catch (SQLException e) {
		           				e.printStackTrace();
		           			 }
		           			session.setAttribute("gL", gList);
                             request.getRequestDispatcher("seller.jsp").forward(request, response);
                     } else {
                             // 处理其他权限或错误情况
                             request.setAttribute("err", "未知权限");
                             request.getRequestDispatcher("error.jsp").forward(request, response);
                    }      
            }else{
                  request.setAttribute("err","密码错误！！！");
                   request.getRequestDispatcher("error.jsp").forward(request,response);
            }
        }
		else{
            request.setAttribute("err","用户名错误或不存在");
            request.getRequestDispatcher("error.jsp").forward(request,response);
		}
	}

}
