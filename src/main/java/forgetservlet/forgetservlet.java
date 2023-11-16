package forgetservlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
//		method为0，返回用户的密保问题
		if ("0".equals(method)) {
		    try {
    			Class.forName("org.sqlite.JDBC");
    			Connection conn = DriverManager.getConnection("jdbc:sqlite:D:/maoliang.db");
                if (isUsernameTaken(conn, username)==false) {
                	System.out.println("nonono");
                    request.setAttribute("error", "用户名不存在");
                    request.getRequestDispatcher("forgetpassword.jsp").forward(request, response);
                } else {
                	System.out.println(setQuestion(conn,username));
                	request.setAttribute("forgetname", username); 
                	request.setAttribute("forgetquestion", setQuestion(conn,username));
                	request.setAttribute("forgetanswer", setQuestion(conn,username));
                    request.getRequestDispatcher("answerquestion.jsp").forward(request, response);
                }
                conn.close();
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
    			Class.forName("org.sqlite.JDBC");
    			Connection conn = DriverManager.getConnection("jdbc:sqlite:D:/maoliang.db");
    			System.out.println("11111");
                String actualanswer = getAnswer(conn,username);
                System.out.println("actualanswer="+actualanswer);
                if(getanswer.equals(actualanswer)) {
                	request.setAttribute("message", "验证成功，您的密码是："+getPwd(conn, username));
                	request.setAttribute("to", "show_pwd");
                    request.getRequestDispatcher("success.jsp").forward(request, response);
                }
                else {
                	request.setAttribute("err", "密保问题验证失败");
                	request.setAttribute("to", "wrong_answer");
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                }
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
		}
	}
    private boolean isUsernameTaken(Connection conn, String username) throws Exception {
        String sql = "SELECT COUNT(*) FROM MLuser WHERE username = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }
	private String setQuestion(Connection conn, String username) throws Exception {
		String sql = "SELECT question FROM MLuser,MLquestion WHERE username = ? and MLuser.userid=MLquestion.userid";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                	return resultSet.getString(1);
                }
            }
        }
        return null;
	}
	private String getAnswer(Connection conn, String username) throws Exception {
		String sql = "SELECT answer FROM MLuser,MLquestion WHERE username = ? and MLuser.userid=MLquestion.userid";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            System.out.println("SQL Statement: " + ps.toString());
            try (ResultSet resultSet = ps.executeQuery()) {
            	System.out.println("222");
                if (resultSet.next()) {
                	System.out.println("333");
                	return resultSet.getString(1);
                }
            }
        }
        return null;
	}
	private String getPwd(Connection conn, String username) throws Exception {
		String sql = "SELECT pwd FROM MLuser WHERE username = ? ";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                	return resultSet.getString(1);
                }
            }
        }
        return null;
	}

}
