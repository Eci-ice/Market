package unittesting;

import java.sql.SQLException;

import sql.goodsql;
import sql.ordersql;
import sqlimpl.goodsqlimpl;
import sqlimpl.ordersqlimpl;

public class successorderfortesting {
	public String success(int orderid) {
        int state = 1;//冻结
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
            		os.modifystate(orderid, 2);//状态二：售出
					gs.modifystate(goodid, 2);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "fail";
				}
	           return "success";
         }
        else {
		    return "该商品无法售出";
		}
	}

}
