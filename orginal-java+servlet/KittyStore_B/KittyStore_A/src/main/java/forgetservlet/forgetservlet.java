package forgetservlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sql.usersql;
import sqlimpl.usersqlimpl;
import vo.user;

/**
 * Servlet implementation class forgetpwdservlet
 */
public class forgetservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public forgetservlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("2222");
		String method = request.getParameter("method");
		String username = (String) request.getParameter("forgetname");
		user u=null;
		usersql us=new usersqlimpl();
//		method为0，返回用户的密保问题
		if ("0".equals(method)) {
		    try {
                if (0==us.find(username)) {
                	System.out.println("nonono");
                    request.setAttribute("error", "用户名不存在");
                    request.getRequestDispatcher("forgetpassword.jsp").forward(request, response);
                } else {
                	u=us.search(username);
//                	System.out.println(setQuestion(username));
                	request.setAttribute("forgetname", username); 
                	request.setAttribute("forgetquestion", u.getQuestion());
                	request.setAttribute("forgetanswer", u.getAnswer());
                    request.getRequestDispatcher("answerquestion.jsp").forward(request, response);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
		} else if ("1".equals(method)){
		    // 验证密保答案
			username = (String) request.getParameter("forname");
			System.out.println("name="+username);
			String getanswer = request.getParameter("getanswer");
			System.out.println("getanswer="+getanswer);
			try {
				u=us.search(username);
    			System.out.println("11111");
                String actualanswer = u.getAnswer();
                System.out.println("actualanswer="+actualanswer);
                if(getanswer.equals(actualanswer)) {
                	request.setAttribute("message", "验证成功，您的密码是："+u.getPwd());
                	request.setAttribute("to", "show_pwd");
                    request.getRequestDispatcher("success.jsp").forward(request, response);
                }
                else {
                	request.setAttribute("err", "密保问题验证失败");
                	request.setAttribute("to", "wrong_answer");
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
		}
	}
    
}
