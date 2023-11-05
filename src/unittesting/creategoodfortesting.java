package unittesting;

import java.sql.SQLException;
import java.util.List;

import sql.goodsql;
import sql.ordersql;
import sqlimpl.goodsqlimpl;
import sqlimpl.ordersqlimpl;
import vo.good;
import vo.order;

public class creategoodfortesting {
	public String create(String  goodname ,String  description,String priceStr,String  picture ,String kind) {

	 	Double price = Double.parseDouble(priceStr);
        int state = 0;
        int number = 1;
        good g=new good();
        goodsql gs = new goodsqlimpl();
		good gf = null;
        try {
			if(gs.unique()==1) {
				gf=new good();
				gf.setGoodname(goodname);
				gf.setDescription(description);
				gf.setPrice(price);
				gf.setPicture(picture);
				gf.setState(state);
				gf.setNumber(number);
				gf.setKind(kind);
				gs.add(gf);
				List<good> gList = null;
       			 try {
       					gList = gs.showall();
       			 } catch (SQLException e) {
       				e.printStackTrace();
       				return "fail";
       			 }
       			return "success";
			}else {
				  return "fail";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		}

		
	}

}
