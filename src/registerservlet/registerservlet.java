package registerservlet;

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
        String username = request.getParameter("newusername");
        String password = request.getParameter("newpwd");
        String powerstr = request.getParameter("power");
        System.out.println(username+password+powerstr);
        int power = Integer.parseInt(powerstr);//卖家仅能后台注册

        // 创建User对象并设置用户信息
        user user = new user();
        user.setUsername(username);
        user.setPwd(password);
        user.setPower(power);
        
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:D:/maoliang.db");
            if (isUsernameTaken(conn, username)) {
            	System.out.println("existed");
                request.setAttribute("error", "用户名已存在");
                JOptionPane.showMessageDialog(null, "用户名已存在", "提示",JOptionPane.PLAIN_MESSAGE);
                request.getRequestDispatcher("register.jsp").forward(request, response);
            } else {
            	System.out.println("success");
                insertUserIntoDatabase(conn, user);
                request.setAttribute("success", "注册成功，请登录");
                JOptionPane.showMessageDialog(null, "注册成功，请登录", "提示",JOptionPane.PLAIN_MESSAGE);
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
            conn.close();
        } catch (Exception e) {
        	System.out.println("321");
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

    private void insertUserIntoDatabase(Connection conn, user user) throws Exception {
        String sql = "INSERT INTO MLuser (username, pwd, power) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPwd());
            ps.setInt(3, user.getPower());
            ps.executeUpdate();
        }
    }
}
