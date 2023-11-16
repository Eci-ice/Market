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
	public String create(String  goodname ,String  description,String priceStr,String  picture ) {
		if (goodname.trim().isEmpty() || description.trim().isEmpty() || priceStr.trim().isEmpty() || picture.trim().isEmpty()) {
	        return "fail1"; // 如果任何一个输入为空，直接返回"fail"
	    }
	 // 检查价格是否为数字
	    try {
	        Double price = Double.parseDouble(priceStr);
	    } catch (NumberFormatException e) {
	        return "fail1"; // 如果价格无法解析为Double类型，返回"fail"
	    }
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