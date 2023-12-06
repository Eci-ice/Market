package changeservlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import sql.usersql;
import sqlimpl.usersqlimpl;

public class changepwdservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 	String sellername = request.getParameter("username");
	        String sellerpwd = request.getParameter("userpwd");
	        System.out.println("test:"+sellername+" "+sellerpwd);
	        String oldpwd = request.getParameter("oldpwd");
	        String newpwd = request.getParameter("newpwd");
	        String newpwd1 = request.getParameter("newpwd1");
	        if(!sellerpwd.equals(oldpwd)){
	            request.setAttribute("err","旧密码错误");
	            request.setAttribute("to","changepwd");
	            request.getRequestDispatcher("error.jsp").forward(request,response);
	        }else{
	            if(oldpwd.equals(newpwd)){
	                request.setAttribute("err","新密码与旧密码一致");
	                request.setAttribute("to","changepwd");
	                request.getRequestDispatcher("error.jsp").forward(request,response);
	            }else{
	                    if(newpwd!=null && newpwd.equals(newpwd1)){
	                    		usersql us = new usersqlimpl();
	                    		try {
									us.modifypwd(sellername, newpwd);
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									request.setAttribute("err","修改失败");
									request.getRequestDispatcher("error.jsp").forward(request,response);
									e.printStackTrace();
								}
	                            request.setAttribute("message","修改密码成功");
	                            request.getRequestDispatcher("success.jsp").forward(request,response);
	                            
	                    }else{
	                        request.setAttribute("err","新密码与确认密码不一致");
	                        request.setAttribute("to","changepwd");
	                        request.getRequestDispatcher("error.jsp").forward(request,response);
	                    }
	                }
	            }
	        }
	    
	}


