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
	public String delete(int goodid) {
		goodsql gs = new goodsqlimpl();
	    try {
	    	if ( gs.remove(goodid) > 0) {
				ordersql ors = new ordersqlimpl();
				ors.deletegood(goodid);//删除对应订单
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
			    return "商品不存在或删除失败";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		}
	}

}
