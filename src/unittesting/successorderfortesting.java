package unittesting;

import java.sql.SQLException;

import sql.goodsql;
import sql.ordersql;
import sqlimpl.goodsqlimpl;
import sqlimpl.ordersqlimpl;

public class successorderfortesting {
	public String success(String orderid) {
	    if (orderid == null || orderid.isEmpty()) {
	        return "订单ID不能为空";
	    }

	    int orderIdAsInt = 0;
	    try {
	        orderIdAsInt = Integer.parseInt(orderid);
	    } catch (NumberFormatException e) {
	        return "无效的订单ID";
	    }

	    if (orderIdAsInt <= 0) {
	        return "订单ID无效";
	    }

	    int state = 1; // 冻结
	    ordersql os = new ordersqlimpl();
	    int goodid = -1;

	    try {
	        goodid = os.searchid(orderIdAsInt);
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return "fail";
	    }

	    if (goodid >= 0) {
	        goodsql gs = new goodsqlimpl();
	        try {
	            os.modifystate(orderIdAsInt, 2); // 状态二：售出
	            gs.modifystate(goodid, 2);
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return "fail";
	        }
	        return "success";
	    } else {
	        return "该商品无法售出";
	    }
	}


}