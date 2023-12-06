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
 * Servlet implementation class findallorderservlet
 */
public class createorderservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public createorderservlet() {
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
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");	
		HttpSession session = request.getSession();
		user u = (user)session.getAttribute("admin");
		String  address = request.getParameter("address");
	 	String  telephone = request.getParameter("telephone");
	 	String  buyername = request.getParameter("buyername");
	 	int goodid = Integer.parseInt(request.getParameter("goodid"));
        int orderstate = 0;//状态零：未成功订单
        
        goodsql gs=new goodsqlimpl();
        good g=null;
        try {
        	g=new good();
			g=gs.search(goodid);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        int owner=g.getOwner();//要的是卖家id而不是买家
        if(null!= g) {
	        ordersql ors = new ordersqlimpl();
	        order orf = null;
	        try {
				orf=new order();
				orf.setAddress(address);
				orf.setTelephone(telephone);
				orf.setBuyername(buyername);
				orf.setGoodid(goodid);
				orf.setOrderstate(orderstate);
				orf.setOwner(owner);
				ors.add(orf);
				
				 List<order> orList = null;
				 try {
						orList = ors.showall(u.getUserid());
				 } catch (SQLException e) {
					e.printStackTrace();
				 }
					
				 request.setAttribute("orL", orList);
				 request.setAttribute("message","成功创建订单");
				    request.setAttribute("to","buyermain");
				request.getRequestDispatcher("success.jsp").forward(request,response); 
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        else {
		    request.setAttribute("err","请输入正确的商品id");
		    request.setAttribute("to","createorder");
		    request.getRequestDispatcher("error.jsp").forward(request,response);
		}
		
	}

}
