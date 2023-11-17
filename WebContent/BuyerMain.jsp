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
<!--     <link rel="stylesheet" type="text/css" href="BMain.css"/> -->
    <title>买家购物首页</title>
    <meta charset="UTF-8">
</head>

<style>

*{
    background-color: #FFF9F1;
    
}
body {
    display: block;
}
.left{
    /* 买家导航 */
    width: 287px;
    height:100%;
    background-color: rgba(61, 61, 61, 0.33);
    position: relative;
    float: left;
    align-content: center;
}
.head1{
    background-color: rgba(61, 61, 61, 0);
    position: relative;
    top: 30px;
    left: 70px;
}
.daohang{
    background-color: rgba(0, 0, 0, 0);
    width: 200px;/*格子宽度*/
    position: relative;
    left: 30px;
}
.head2{
    background-color: rgba(61, 61, 61, 0.33);
    text-align: center;
    vertical-align: top;
    font-size:36px;
    color: white;
    height: 100px;/*格子高度*/
}
.head4{
    background-color: rgba(61, 61, 61, 0.33);
    text-align: center;
    height: 100px;/*格子高度*/
}
.head4-1{
    background-color: rgba(61, 61, 61, 0);
    text-decoration: none;
    color: #ffffff;
    font-size:28px;
    font-weight: bold;
}
.head5{
    background-color: rgba(61, 61, 61, 0.33);
    text-align: center;
    vertical-align: bottom;
    height: 450px;/*格子高度*/
}
.head5-1{
    background-color: rgba(61, 61, 61, 0);
    text-decoration: none;
    color: #585655;
    font-size:28px;
    font-weight: bold;
}

/* 商品 */
.right{
    /* 商品显示 */
    width: 1340px;
    height: 100%;
    /* background-color: aquamarine; */
    position: absolute;/*绝对定位*/
    left: 350px; 
    
    float: right;
}
/*商品排列*/
#goods {
  display: grid;
  border:50px;
  grid-template-columns: 25% 25% 25% 25%;
  grid-template-rows: 50% 50% ;
}

/*table.good-list{
    width: 100%;格子宽度
    height: 100%;格子高度
    color: rgb(57, 77, 70);

}
.show-1{
    position: relative;
     top: 30px;
    left: 50px; 
    width: 400px;
    height: 300px;
    text-decoration: none;
    color: rgb(0, 0, 0);
}
.show-2{
    width: 400px;格子宽度
    height: 300px;格子高度
     background-color: #585655; 

}*/
.picture{
    text-align: center;/* 图片居中 */
}
.price{
    font-size:20px;
    /* width: 100px; */
    height: 20px;
}


</style>

<body style="margin: 0px;">
	<div class="left" ><!-- 买家导航 -->
	    <!-- <hr class="head3" color=#FFF2E1 width="230px" size="2px" > -->
	    <table class="daohang">
	    	<img class="head1" src="img/buyer/head.png" alt=""  >	
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
