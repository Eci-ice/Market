package orderservlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
 * Servlet implementation class historygoodservlet
 */
public class deleteorderservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public deleteorderservlet() {
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
//		System.out.println("id="+orderid);
		ordersql ors=new ordersqlimpl();
		List<order> orList = null;
		int goodid=-1;
		
		try {
			ors.modifystate(orderid,-1);
			//查询订单里good的id
  			goodid = ors.searchid(orderid);
  			//根据查询到ord对象
  			order ord = ors.getOrderById(orderid);
  			goodsql gs = new goodsqlimpl();
  			//获取订单的数量
            int number = ord.getNumber();
            good gd = gs.search(goodid); // 获取商品信息
            System.out.println("111+"+gd.getNumber()+"222+"+gd.getState());
            //如果商品是冻结状态
            if(gd.getState()==1) {
            	//解冻商品
            	gd.setState(0);
            	System.out.println("jiejiejie");
            }

            // 获取商品当前库存并更新
            
            if (gd != null) {
            	int tmp=gd.getNumber();
            	System.out.println("update");
            	gd.setNumber(tmp+number);
            	System.out.println(gd.getNumber()+gd.getState());
            }
            gs.updateGood(gd);
            ors.deleteorder(orderid);
            orList = ors.showall(u.getUserid());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("no found");
		}

		 request.setAttribute("orL", orList);
		 request.getRequestDispatcher("show_userinfo.jsp").forward(request,response);
	}
}
