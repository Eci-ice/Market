package registerservlet;

import javax.servlet.*;
import javax.servlet.http.*;



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
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        int power = 0;//卖家仅能后台注册

        // 创建User对象并设置用户信息
        user user = new user();
        user.setUsername(username);
        user.setPwd(password);
        user.setPower(power);
        
        Connection conn;
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:maoliang.db");
            if (isUsernameTaken(conn, username)) {
                request.setAttribute("error", "用户名已存在");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            } else {
                insertUserIntoDatabase(conn, user);
                request.setAttribute("success", "注册成功，请登录");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isUsernameTaken(Connection conn, String username) throws Exception {
        String sql = "SELECT COUNT(*) FROM user WHERE username = ?";
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
        String sql = "INSERT INTO user (username, pwd, power) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPwd());
            ps.setInt(3, user.getPower());
            ps.executeUpdate();
        }
    }
}
