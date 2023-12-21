package orderservlet;

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
 * Servlet implementation class sellerconfirmorderservlet
 */
public class sellerconfirmorderservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public sellerconfirmorderservlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderid = Integer.parseInt(request.getParameter("orderid"));
        int state = 0;
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
            		int tmp=os.searchstate(orderid);
            		os.modifystate(orderid, tmp+1);
					gs.modifystate(goodid, 1);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            request.setAttribute("message", "操作成功");
	            request.setAttribute("to","show_goods");
	            request.getRequestDispatcher("show_userinfo.jsp").forward(request,response);
         }
	     else {
			    request.setAttribute("err","操作失败");
			    request.setAttribute("to","show_goods");
			    request.getRequestDispatcher("error.jsp").forward(request,response);
	     }
        

    }

}
