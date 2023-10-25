<%--
  Created by IntelliJ IDEA.
  User: banana
  Date: 2023/10/23
  Time: 11:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>买家商品主页</title>
    <link rel="stylesheet" type="text/css" href="BS.css"/>
</head>
<body>
<div class="left"><!-- 买家导航 -->
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

<div class="right"><!-- 商品显示 -->
    <!-- 返回浏览界面按钮 -->
    <a href="BuyerMain.jsp"><img src="./img/buyer/arrow.png" alt="" width="81" title="返回浏览界面"> </a>
    <!-- 图片 -->
    <div class="picture-k">
        <div class="test1">
            <img src="${nowg.picture}" alt="" width="422" title="猫粮图片">
        </div>
    </div>
    <table class="good-2"><!-- 商品介绍 -->
        <tr><td colspan="2" class="goodname">
           		 商品名称 :${nowg.goodname}
        </td></tr>
        <tr>
            <td colspan="2" class="description">商品介绍:<br/>
            ${nowg.description }
            </td>
        </tr>
        <tr>
            <td class="price">价格：</td>
            <td class="price">${nowg.price}</td>
        </tr>
        <tr>
            <td class="price">剩余库存量：</td>
            <td class="price">${nowg.number}</td>
        </tr>
    </table>
  <!-- <img src="./img/buyer/aa.png" alt="" width="228" title="go">-->
     <a href="BuyerFillInfo.jsp" alt="" title="购买"><button class="butt-1" >购买该商品</button></a> 
</div>

</body>
</html>
