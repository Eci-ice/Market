package unittesting;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;

import sql.goodsql;
import sql.ordersql;
import sql.usersql;
import sqlimpl.goodsqlimpl;
import sqlimpl.ordersqlimpl;
import sqlimpl.usersqlimpl;
import vo.good;
import vo.order;
import vo.user;

public class loginfortesting {
	public String login(String username,String pwd) {
		usersql us = new usersqlimpl();
		user ut = null;
		try {
			ut = us.search(username);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(null!=ut){
             if(pwd.equals(ut.getPwd())){ //密码存在且与数据库的密码一致则跳转
                	if (ut.getPower() == 0) {
                             // 买家权限，进入商品首页
                			 goodsql gs=new goodsqlimpl();
                			 List<good> gList = null;
                			 try {
                					gList = gs.showall();
                			 } catch (SQLException e) {
                				e.printStackTrace();
                			 }
                			 return "success2";
                    } else if (ut.getPower() == 1) {
                             // 卖家权限，进入后台管理/order页面
                        	 ordersql ors=new ordersqlimpl();
                			 List<order> orList = null;
                			 try {
                					orList = ors.showall();
                			 } catch (SQLException e) {
                				e.printStackTrace();
                			 }
                			 return "success1";
                     } else {
                             // 处理其他权限或错误情况
                    	   return "fail1";
                    }      
            }else{
            	return "密码错误！";
            }
        }
		else{
			return "用户名错误或不存在";
		}
	}

}