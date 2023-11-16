package unittesting;

import java.sql.SQLException;
import java.util.List;

import sql.goodsql;
import sql.ordersql;
import sqlimpl.goodsqlimpl;
import sqlimpl.ordersqlimpl;
import vo.good;
import vo.order;

public class deletegoodfortesting {
	public String delete(String goodidStr) {
	    if (goodidStr.trim().isEmpty()) {
	        return "fail1"; // 如果商品ID为空，直接返回"fail1"
	    }

	    try {
	        int goodid = Integer.parseInt(goodidStr);
	        goodsql gs = new goodsqlimpl();
	        try {
	            if (gs.remove(goodid) > 0) {
	                ordersql ors = new ordersqlimpl();
	                ors.deletegood(goodid); // 删除对应订单
	                List<order> orList = null;
	                List<good> gList = null;
	                try {
	                    orList = ors.showall();
	                    gList = gs.showall();
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                }
	                return "success";
	            } else {
	                // 商品不存在或删除失败
	                return "商品不存在";
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return "fail";
	        }
	    } catch (NumberFormatException e) {
	        return "fail1"; // 如果商品ID不能解析为整数，返回"fail1"
	    }
	}


}