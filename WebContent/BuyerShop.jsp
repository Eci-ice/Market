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
    <link rel="stylesheet" type="text/css" href="BShop.css"/>
</head>
<!-- 
<style>
* {
  background-color: #fff9f1;
}
.left {
  /* 买家导航 */
  width: 287px;
  height: 1023px;
  background-color: rgba(61, 61, 61, 0.33);
  position: relative;
  float: left;
}
.head1 {
  background-color: rgba(61, 61, 61, 0);
  position: relative;
  top: 30px;
  left: 70px;
}
.daohang {
  background-color: rgba(0, 0, 0, 0);
  width: 200px; /*格子宽度*/
  height: 600px; /*格子高度*/
  position: relative;
  top: 50px;
  left: 30px;
}
.head2 {
  background-color: rgba(61, 61, 61, 0.33);
  text-align: center;
  vertical-align: top;
  font-size: 36px;
  color: white;
  height: 100px; /*格子高度*/
}
.head4 {
  background-color: rgba(61, 61, 61, 0.33);
  text-align: center;
  height: 100px; /*格子高度*/
}
.head4-1 {
  background-color: rgba(61, 61, 61, 0);
  text-decoration: none;
  color: #ffffff;
  font-size: 28px;
  font-weight: bold;
}
.head5 {
  background-color: rgba(61, 61, 61, 0.33);
  text-align: center;
  vertical-align: bottom;
  height: 450px; /*格子高度*/
}
.head5-1 {
  background-color: rgba(61, 61, 61, 0);
  text-decoration: none;
  color: #585655;
  font-size: 28px;
  font-weight: bold;
}

.right {
  /* 商品显示 */
  width: 1340px;
  height: 1023px;
  /* background-color: aquamarine; */
  position: absolute; /*绝对定位*/
  left: 287px;
  float: right;
}
.picture-k {
  /* 放图片的框 */
  width: 462px;
  height: 561px;
  background-repeat: no-repeat;
  background-image: url("./img/buyer/Square.png");
  position: relative;
  left: 100px;
  top: 10px;
  background-size: 100% 100%;
  border-style: none;
}
.test1 {
  /* 商品图片 */
  width: 400px;
  height: 500px;
  color: #000;
  /* background-image: url("./元件/4.jpg"); */
  background-repeat: no-repeat; /*这里不知道怎么把div默认格式改了????*/
  background-size: cover;
  position: relative;
  top: 20px;
  left: 10px;
  border-style: none;
}
.good-2 {
  position: absolute;
  top: 100px;
  left: 650px;
  float: right;
  border-style: none;
  /* background-color: rgba(121, 115, 115, 0.5); */
  width: 700px; /*格子宽度*/
  height: 600px; /*格子高度*/
}
.goodname {
  height: 150px; /*格子高度*/
  font-size: 55px;
}
.description {
  height: 300px; /*格子高度*/
  background-color: rgba(255, 255, 255, 0.5);
  font-size: 30px;
}
.price {
  font-size: 24px;
  width: 300px;
  height: 100px;
}
.butt-1 {
  /* 购买按钮 */
  position: relative;
  top: 100px;
  left: 200px;
  background-color: orange;
  font-size: 30px;
}
button {
  padding: 5px 10px;
  width: 200px;
  height: 100px;
  margin: 5px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

</style> -->

<body style="margin:0px">
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
    <a href="BuyerMain.jsp"><img src="./img/buyer/arrow.png" alt="" width="40" title="返回浏览界面"> </a>
    <!-- 图片 -->
    <div class="picture-k">
        <div class="test1">
            <img src="${nowg.picture}" alt="" width="100%" title="猫粮图片">
        </div>:
    </div>
    <table class="good-2"><!-- 商品介绍 -->
        <tr><td colspan="2" class="goodname">
           		 ${nowg.goodname}
        </td></tr>
        <tr>
            <td colspan="2" class="description"><br/>
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
