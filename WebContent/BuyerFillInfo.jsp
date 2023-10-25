<%--
  Created by IntelliJ IDEA.
  User: banana
  Date: 2023/10/22
  Time: 22:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>买家填写购买信息</title>
    <link rel="stylesheet" type="text/css" href="BFI.css"/>
</head>
<body>
<hr class="cline" color=#BBBBBB width=1 size=900>
<div class="left"><!-- 商品介绍 -->
	<!-- 返回shop界面按钮 -->
	    <a href="BuyerShop.jsp"><img src="./img/buyer/arrow.png" alt="" width="81" title="返回商品界面"> </a>
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
    <!-- <a href="BuyerMain.jsp" class="butt-1"><img src="./img/buyer/debuy-button.png" alt="" width="228px" title="不买"></a> -->
      <a href="BuyerMain.jsp" alt="" title="购买"><button class="butt-1" > 取消购买</button></a> 

</div>

<div  >
    <form action="createorderservlet" method="post" name="myform" class="buy-imf">
		<input type="hidden" name="goodid" value="${nowg.goodid}"/><br/>
        <label class="username">
            用户名：<input type="text" name="buyername"><br><br>
        </label>
        电话：<input type="text" name="telephone"><br><br>
        交易地点：<input type="text" name="address" placeholder="默认地址"><br><br>
        </label>
       <!--<input type="button" onclick='location.href=("success.jsp")' value="确认购买" class="butt-2"/>  --> 
       <input type="submit"  class="butt-2" value="确认购买" >
<%--        <input type="submit" value="确认购买" >--%>
<%--        <button class="buy-btn"><a href="success.jsp">确认购买</a></button>--%>

    </form>

</div>
</body>
</html>
