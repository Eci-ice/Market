package registerservlet;

import javax.naming.spi.DirStateFactory.Result;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.swing.JOptionPane;

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
    	if (request.getAttribute("error") == null) {
    	    request.setAttribute("error", "");
    	}

    	if (request.getAttribute("success") == null) {
    	    request.setAttribute("success", "");
    	}
    	String type = request.getParameter("type");
    	String username = request.getParameter("newusername");
        String password = request.getParameter("newpwd");
        String question =  request.getParameter("newquestion");
        String answer = request.getParameter("newanswer");
        String phone = null;
        String address = null;
        int power = Integer.parseInt(type);//卖家仅能后台注册
//    	0为买家
    	if(type.equals("0")) {
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
            
    		try {
    			Class.forName("org.sqlite.JDBC");
    			Connection conn = DriverManager.getConnection("jdbc:sqlite:D:/maoliang.db");
                if (isUsernameTaken(conn, username)) {
                	System.out.println("existed");
                    request.setAttribute("error", "用户名已存在");
                    request.getRequestDispatcher("choose_register.jsp").forward(request, response);
                } else {
                	System.out.println("success");
                    insertUserIntoDatabase(conn, user,type);
                    request.setAttribute("success", "注册成功，请登录");
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
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
    private int returnid(Connection conn, String username) throws SQLException {
    	String sql = "SELECT userid FROM MLuser WHERE username = ?";
    	try (PreparedStatement ps = conn.prepareStatement(sql)) {
    		ps.setString(1, username);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    return id;
                }
            }
    	}
		return 0;
    }

    private void insertUserIntoDatabase(Connection conn, user user, String type) throws Exception {
//    	插入共同信息
		String sql = "INSERT INTO MLuser (username, pwd, power) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPwd());
            ps.setInt(3, user.getPower());
            ps.executeUpdate();
        }
        int id = returnid(conn, user.getUsername());
        sql = "INSERT INTO MLquestion (userid, question, answer) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setString(2, user.getQuestion());
            ps.setString(3, user.getAnswer());
            ps.executeUpdate();
        }
//        如果是买家，插入地址和电话
        if(type.equals("0")) {
        	sql = "INSERT INTO MLinfo (userid, phone, address) VALUES (?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.setString(2, user.getPhone());
                ps.setString(3, user.getAddress());
                ps.executeUpdate();
            }
        }
    }
}
