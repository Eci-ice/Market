package unittesting;

import java.sql.SQLException;
import java.util.List;

import sql.goodsql;
import sql.ordersql;
import sqlimpl.goodsqlimpl;
import sqlimpl.ordersqlimpl;
import vo.good;
import vo.order;

public class createorderfortesting {
	public String create(String  address ,String  telephone,String  buyername,String id) {
	 	int goodid = Integer.parseInt(id);
        int orderstate = 0;//状态零：未成功订单
        goodsql gs=new goodsqlimpl();
        good g=null;
        try {
        	g=new good();
			g=gs.search(goodid);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return "fail1";
		}
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
				ors.add(orf);
				 List<order> orList = null;
				 try {
						orList = ors.showall();
				 } catch (SQLException e) {
					e.printStackTrace();
					return "fail2";
				 }
				 return "success";
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "fail3";
			}
        }
        else {
        	  return "请先下架当前商品，再进行发布";
		}
		
	}

}
