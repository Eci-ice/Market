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
public class createcartorderservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public createcartorderservlet() {
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
		String  address =u.getAddress();
		String  telephone  =u.getPhone();
		String  buyername  =u.getUsername();
		int orderstate = 0;//状态零：未成功订单
		String[] goodids = request.getParameterValues("goodid[]");
		String[] numbers = request.getParameterValues("number[]");

		goodsql gs = new goodsqlimpl();
		ordersql ors = new ordersqlimpl();

		for (int i = 0; i < goodids.length; i++) {
		    int goodid = Integer.parseInt(goodids[i]);
		    int number = Integer.parseInt(numbers[i]);

		    good g = null;
		    try {
		        g = gs.search(goodid);
		    } catch (SQLException e1) {
		        e1.printStackTrace();
		    }

		    if (g != null) {
		        int owner = g.getOwner(); //要的是卖家id而不是买家

		        order orf = new order();
		        orf.setAddress(address);
		        orf.setTelephone(telephone);
		        orf.setBuyername(buyername);
		        orf.setGoodid(goodid);
		        orf.setNumber(number);
		        orf.setOrderstate(orderstate);
		        orf.setOwner(owner);

		        try {
		            ors.add(orf);
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }
		    else {
			    request.setAttribute("err","请输入正确的商品id");
			    request.setAttribute("to","createorder");
			    request.getRequestDispatcher("error.jsp").forward(request,response);
			    break;
			}
			
		}

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
        
	}

}
