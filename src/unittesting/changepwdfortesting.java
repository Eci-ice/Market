package unittesting;

import java.sql.SQLException;

import sql.goodsql;
import sql.ordersql;
import sql.usersql;
import sqlimpl.goodsqlimpl;
import sqlimpl.ordersqlimpl;
import sqlimpl.usersqlimpl;

public class changepwdfortesting {
	public String change(String sellername,String sellerpwd, String oldpwd,String newpwd,String newpwd1) { 
		if (newpwd.trim().isEmpty() || newpwd1.trim().isEmpty()||sellerpwd.trim().isEmpty() || oldpwd.trim().isEmpty() ) {
	        return "fail1"; // 如果的新密码不能为空！
	    }
        if(!sellerpwd.equals(oldpwd)){
            return "旧密码错误";
        }else{
            if(oldpwd.equals(newpwd)){
            	return "新密码与旧密码一致";
            }else{
                    if(newpwd!=null && newpwd.equals(newpwd1)){
                    		usersql us = new usersqlimpl();
                    		try {
								us.modifypwd(sellername, newpwd);
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								return "fail";
							}
                            return "success";
                            
                    }else{
                    	return "新密码与确认密码不一致";
                    }
                }
          }
     }
}
