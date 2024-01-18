package orderservlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sql.goodsql;
import sql.ordersql;
import sqlimpl.goodsqlimpl;
import sqlimpl.ordersqlimpl;
import vo.good;
import vo.order;
import vo.user;

/**
 * Servlet implementation class buyerdeleteorderservlet
 */
public class buyerdeleteorderservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public buyerdeleteorderservlet() {
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
		HttpSession session = request.getSession();
		user u = (user)session.getAttribute("admin");
		int  orderid = Integer.parseInt(request.getParameter("orderid")); 
		ordersql ors=new ordersqlimpl();
	    goodsql gs = new goodsqlimpl(); // 使用goodsqlimpl

		List<order> orList = null;
		 try {
			 order ord = ors.getOrderById(orderid);
		        if (ord != null) {
		            int goodid = ord.getGoodid();
		            int number = ord.getNumber();

		            // 获取商品当前库存并更新
		            good gd = gs.search(goodid); // 获取商品信息
		            if (gd != null) {
		                int newNumber = gd.getNumber() + number;
		                gd.setNumber(newNumber); // 更新商品数量
		                gs.updateGood(gd); // 保存更新
		            }
		        }
			ors.deleteorder(orderid);
  			orList = ors.showall(u.getUserid());
		 } catch (SQLException e) {
			e.printStackTrace();
		 }
			
		 request.setAttribute("orL", orList);
		 request.getRequestDispatcher("BuyerMain.jsp").forward(request,response);
	}

}
