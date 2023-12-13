package unittesting;

import java.sql.SQLException;

import sql.goodsql;
import sql.ordersql;
import sqlimpl.goodsqlimpl;
import sqlimpl.ordersqlimpl;

public class confirmorderfortesting {
	public String confirm(String orderidStr) {
	    if (orderidStr.trim().isEmpty()) {
	        return "fail1"; // 如果订单ID为空，直接返回"fail"
	    }

	    try {
	        int orderid = Integer.parseInt(orderidStr);
	        int state = 0; // 未冻结
	        ordersql os = new ordersqlimpl();
	        int goodid = -1;
	        try {
	            goodid = os.searchid(orderid);
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return "fail";
	        }
	        if (goodid >= 0) {
	            goodsql gs = new goodsqlimpl();
	            try {
	                os.modifystate(orderid, 1); // 状态一：冻结
	                gs.modifystate(goodid, 1);
	            } catch (SQLException e) {
	                e.printStackTrace();
	                return "fail";
	            }
	            return "success";
	        } else {
	            return "该商品无法冻结"; // 如果无法找到相关的商品，返回"fail1"
	        }
	    } catch (NumberFormatException e) {
	        return "fail2"; // 如果订单ID不能解析为整数，返回"fail1"
	    }
	}


}