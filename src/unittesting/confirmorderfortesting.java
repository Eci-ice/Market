package unittesting;

import java.sql.SQLException;

import sql.goodsql;
import sql.ordersql;
import sqlimpl.goodsqlimpl;
import sqlimpl.ordersqlimpl;

public class confirmorderfortesting {
	public String confirm(int orderid) {
        int state = 0;//未冻结
        ordersql os = new ordersqlimpl();
        int goodid=-1;
		try {
			goodid = os.searchid(orderid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		}
        if (goodid>=0) {
            	goodsql gs = new goodsqlimpl();
            	try {
            		os.modifystate(orderid, 1);//状态一：冻结
					gs.modifystate(goodid, 1);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "fail";
				}
	            return "success";
         }
	     else {
			return "该商品无法冻结";
	     }
	}

}
