package registerservlet;

import javax.naming.spi.DirStateFactory.Result;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.swing.JOptionPane;

import sql.goodsql;
import sql.usersql;
import sqlimpl.goodsqlimpl;
import sqlimpl.usersqlimpl;
import vo.user;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


//后期加入
public class registerservlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String type = request.getParameter("type");
    	String username = request.getParameter("newusername");
        String password = request.getParameter("newpwd");
        String question =  request.getParameter("newquestion");
        String answer = request.getParameter("newanswer");
        String phone = null;
        String address = null;
        int power = Integer.parseInt(type);//卖家仅能后台注册
//    	0为买家
    	if(0==power) {
            phone = request.getParameter("newphone");
            address = request.getParameter("newaddress");
        }

            // 创建User对象并设置用户信息
            user user = new user();
            user.setUsername(username);
            user.setPwd(password);
            user.setPower(power);
            user.setPhone(phone);
            user.setAddress(address);
            user.setQuestion(question);
            user.setAnswer(answer);
            
            usersql us=new usersqlimpl();
                try {
					if (1==us.find(username)) {
						System.out.println("existed");
					    request.setAttribute("error", "用户名已存在");
					    request.getRequestDispatcher("choose_register.jsp").forward(request, response);
					} else {
						System.out.println("success");
					    us.register(user);
					    request.setAttribute("success", "注册成功，请登录");
					    request.getRequestDispatcher("index.jsp").forward(request, response);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    }
   
}
