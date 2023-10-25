package goodservlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sql.goodsql;
import sql.ordersql;
import sqlimpl.goodsqlimpl;
import sqlimpl.ordersqlimpl;

/**
 * Servlet implementation class wanchengjiaoyi
 */
public class successorderservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public successorderservlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int orderid = Integer.parseInt(request.getParameter("orderid"));
        int state = 1;//冻结
        ordersql os = new ordersqlimpl();
        int goodid=-1;
		try {
			goodid = os.searchid(orderid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (goodid>=0) {
            	goodsql gs = new goodsqlimpl();
            	try {
            		os.deleteorder(orderid);//删除订单
					gs.modifystate(goodid, 2);//状态二：售出
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            request.setAttribute("message", "商品交易成功");
	            request.setAttribute("to","show_goods");
	            request.getRequestDispatcher("success.jsp").forward(request, response);
         }
        else {
		    request.setAttribute("err","该商品无法售出");
		    request.setAttribute("to","show_goods");
		    request.getRequestDispatcher("error.jsp").forward(request,response);
		}
	}

}
