<%--
  Created by IntelliJ IDEA.
  User: banana
  Date: 2023/10/18
  Time: 10:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="BMain.css"/>
    <title>买家购物首页</title>
    <meta charset="UTF-8">
</head>
<body style="margin: 0px;">
	<div class="left" style="height:100%"><!-- 买家导航 -->
	    <img class="head1" src="img/buyer/head.png" alt=""  >
	    <!-- <hr class="head3" color=#FFF2E1 width="230px" size="2px" > -->
	    <table class="daohang">
	    	<c:if test="${not empty sessionScope.admin }">
		        <tr>
		            <td class="head2">${sessionScope.admin.username}</td>
		        </tr>
		        <tr>
		            <td class="head4"><a  href="BuyerChanInfo.jsp" class="head4-1">创建订单</a></td>
		        </tr>
		        <tr>
		            <td class="head4"><a  href="BuyerHistory.jsp" class="head4-1">历史购买记录</a></td>
		        </tr>
		        <tr >
		            <td class="head5"><a href="quitloginservlet" class="head5-1">退出登录</a></td>
		        </tr>
		    </c:if>
		    <c:if test="${empty sessionScope.admin }">
		    	<tr>
		            <td class="head2"> 游客</td>
		        </tr>
		        <tr>
		            <td class="head4"><a   class="head4-1">其他功能后登录使用</a></td>
		        </tr>
		        <tr >
		            <td class="head5"><a href="index.jsp" class="head5-1">返回登录</a></td>
		        </tr>
		    </c:if>
	    </table>
	
	</div>
	
	<div class="right" style="height:100%">
<%-- 	    <table class="good-list" style="height:100%">
	        <tr>
	            <td>
		            <c:forEach items="${sessionScope.gL}" var="g">
		                <a href="showgoodservlet?goodid=${g.goodid}" class="show-1">
		                    <table class="show-2">
			                        <tr><td colspan="3" class="picture">
			                            <img src="${g.picture}" alt="" width="174">
			                        </td></tr>
			                        <tr>
			                            <td class="price" style="text-align: center;">${g.goodname}<br>超值价：${g.price}元</td>
			                        </tr>
		                    </table>
		                </a>
		            </c:forEach>
	            </td> 
	        </tr>
	    </table> --%>
	    <div class="goods">
	    	<c:forEach items="${sessionScope.gL}" var="g">
	    		<a href="showgoodservlet?goodid=${g.goodid}" class="show-1">
		    		<div>
		    			<img src="${g.picture}" alt="aa" width="200">
		    			<p>${g.goodname}<br>超值价：${g.price}元</p>
		    		</div>
	    		</a>
	    	</c:forEach>
	    </div>
	
	
	</div>
</body>
</html>
