/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.108
 * Generated at: 2023-04-11 07:53:30 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.wfpan.vo.User;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
      out.write("<title>登录</title>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");

//登录检测
Object cu = session.getAttribute("curUser");
if(null!=cu) {
	User u = (User)cu;

      out.write("\r\n");
      out.write("用户名:");
      out.print(u.getUname());
      out.write("<br/>\r\n");
      out.write("密&nbsp;码:");
      out.print(u.getPwd());
      out.write("<br/>\r\n");
      out.write("<a href=\"quit.jsp\">退出登录</a>\r\n");
} else { 
//这个位置取cookie，去做填充
//得到这个站点存在硬盘上的所有cookie
Cookie[] cks = request.getCookies();
String uname = "";
String upwd = "";
if(null!=cks) {
	for(Cookie c:cks) {
		if("zjgsuusername".equals(c.getName())) {
			uname = c.getValue();
		}
		if("zjgsupwd".equals(c.getName())) {
			upwd = c.getValue();
		}
	}

	//查詢數據庫，判斷一下，用戶名和密碼對不對，直接跳登錄頁面
	if("wfpan".equals(uname)&&"123".equals(upwd)) {
		request.setAttribute("uname", "wfpan");
		request.setAttribute("pwd", "123");
		
		request.getRequestDispatcher("index-handler.jsp").forward(request, response);
		//response.sendRedirect("index-handler.jsp");
		return;
	}

}


      out.write("\r\n");
      out.write("\r\n");
      out.write("<form action=\"index-handler.jsp\">\r\n");
      out.write("用户名:<input type=\"text\" name=\"uname\" value=\"");
      out.print(uname);
      out.write("\"/><br/>\r\n");
      out.write("密&nbsp;码:<input type=\"password\" name=\"pwd\" value=\"");
      out.print(upwd);
      out.write("\"/><br/>\r\n");
      out.write("记住密码：<input type=\"checkbox\" name=\"jzmm\" value=\"1\"><br/>\r\n");
      out.write("<input type=\"submit\" value=\"登录\"/>\r\n");
      out.write("</form>\r\n");
      out.write("\r\n");
} 
      out.write("\r\n");
      out.write("\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
