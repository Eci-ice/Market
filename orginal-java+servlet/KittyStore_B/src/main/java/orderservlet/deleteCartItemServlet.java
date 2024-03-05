package orderservlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sqlimpl.goodsqlimpl;
import vo.user;

public class deleteCartItemServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public deleteCartItemServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        user u = (user) session.getAttribute("admin");
        
        if (u != null) {
            try {
                int buyingId = Integer.parseInt(request.getParameter("buyingid"));
                goodsqlimpl goodSqlImpl = new goodsqlimpl();
                
                goodSqlImpl.removeBuyingItem(buyingId);
                response.getWriter().write("购物车商品删除成功");
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "无效的参数格式");
                e.printStackTrace();
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "服务器错误：无法删除购物车商品");
                e.printStackTrace();
            }
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "用户未登录");
        }
    }
}
