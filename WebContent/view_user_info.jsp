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
    height:100vh;
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
    height: 100px;/*格子高度*/
    bottom: 0;
    
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
    /* 商品显示 
    width: 1340px;*/
    height: 100vh;
    /* background-color: aquamarine; */
    position: absolute;/*绝对定位*/
    left: 350px; 
    
    float: right;
}


.goods {
    display: flex; /*使用flex布局*/
    flex-wrap: wrap; /*允许元素换行*/
    justify-content: space-between; /*元素之间留有空隙*/
    border: 1px solid #000; /*添加边框*/
}

.show-1 {
    flex: 0 0 30%; /*每个元素占用30%的宽度，这样每行就可以放3个元素*/
    border: 1px solid #000; /*添加边框*/
    margin-bottom: 10px; /*添加底部边距*/
}

.picture {
    text-align: center; /*图片居中*/
}

.price {
    font-size: 20px;
    height: 20px;
}
.pagination {
        display: flex;
        justify-content: center;
        align-items: center;
        margin-bottom: 20px;
    }
    .prev, .next {
    	background-color: rgb(237, 196, 110);
    }
form {
    display: flex; /* 让表单内的元素在同一行显示 */
       width:600px;
    height:45px;
	}
	input[type="text"] {
	    flex-grow: 1; /* 让搜索框占据剩余的空间 */
	}
	input[name="keyword"] {
	    width: 100%;
	    padding: 10px; 
	    border: 1px solid #ccc; 
	    border-radius: 4px;
	}	
	#search_list {
		position: fixed;
		top: 110px;
		left: 350px;
		width: 400px;
		background-color: white;
	}

	
	#search_list div {
	    border-bottom: 1px solid black; 
	}
</style>

<body style="margin: 0px;">
	

	   <c:if test="${not empty sessionScope.admin }">
	   		<c:if test="${sessionScope.admin.power ne '1' }"><!-- 管理员权限不显示以下内容 -->
		    	<div class="left" ><!-- 买家导航 -->
				    <!-- <hr class="head3" color=#FFF2E1 width="230px" size="2px" > -->
				    <table class="daohang">
		
			    	<img class="head1" src="img/buyer/head.png" alt=""  >	
			        <tr>
		            <td class="head4"><a  href="allcartservlet" class="head4-1">我的购物车</a></td>
			        </tr>
			        <tr>
			            <td class="head4"><a  href="showlikeservlet" class="head4-1">我的收藏</a></td>
			        </tr>
			        <tr>
			            <td class="head4"><a  href="userorderservlet?userId=${sessionScope.admin.username}" class="head4-1">历史购买记录</a></td>
		        </tr>
		        <tr>
			            <td class="head4"><a  href="BuyerMain.jsp" class="head4-1">返回主页</a></td>
		        	</tr>
		         <tr >
		            <td class="head5"><a href="quitloginservlet" class="head5-1">退出登录</a></td>
		        </tr>
			       	</table>
				</div>
			</c:if>
		</c:if>
		<c:if test="${empty sessionScope.admin }">
		    <div class="left" ><!-- 买家导航 -->
			    <!-- <hr class="head3" color=#FFF2E1 width="230px" size="2px" > -->
			    <table class="daohang">
		    	<img class="head1" src="img/buyer/head.png" alt=""  >	
		    	<tr>
		            <td class="head2"> 游客</td>
		        </tr>
		        <tr>
		            <td class="head4"><a   class="head4-1">其他功能后登录使用</a></td>
		        </tr>
		        <tr >
		            <td class="head5"><a href="index.jsp" class="head5-1">返回登录</a></td>
		        </tr>
		       	</table>
	
			</div>
	   </c:if>

<c:if test="${not empty sessionScope.admin }">
	<div id="right">
		<div class="container">
			<c:if test="${sessionScope.admin.power eq '1' }"><!-- 管理员权限显示以下内容 -->
				<a href="allcustomservlet">返回</a>
			</c:if>
			<c:if test="${sessionScope.admin.power eq '0' }">
				<a href="BuyerMain.jsp">返回</a>
			</c:if>
    		<center>
				<h2>历史下单记录</h2>
			</center>
			<table style="border-collapse: collapse; width: 80%; box-shadow: 0 2px 4px rgba(0,0,0,0.1);" border="1" align="center" cellpadding="10">
                    <tr>
                        <th>ID</th>
                        <th>地址</th>
                        <th>电话</th>
                        <th>购买人姓名</th>
                        <th>商品ID</th>
                        <th>订单状态</th> <!-- 新增的表头 -->
                    </tr>
                    <c:forEach items="${requestScope.orL}" var="order">
                        <tr>
                            <td>${order.orderid}</td>
                            <td>${order.address}</td>
                            <td>${order.telephone}</td>
                            <td>${order.buyername}</td>
                            <td>${order.goodid}</td>
<td>
                                <c:choose>
                <c:when test="${order.orderstate == -1}">交易取消</c:when>
                <c:when test="${order.orderstate == 0}">等待客户下单</c:when>
                <c:when test="${order.orderstate == 1}">等待商家确认</c:when>
                <c:when test="${order.orderstate == 2}">等待备货确认</c:when>
                <c:when test="${order.orderstate == 3}">等待发货确认</c:when>
                <c:when test="${order.orderstate == 4}">商家已发货，等待交易确认</c:when>
                <c:when test="${order.orderstate > 4}">交易成功</c:when>

            </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </table>


        <!-- 获取当前页码，默认为1 -->
		<c:set var="currentPage" value="${param.page != null ? param.page : '1'}" />
		<%--
		<script>
		    function goToPrevPage() {
		        var currentPage = parseInt('<c:out value="${currentPage}"/>');
		        if (currentPage > 1) {
		            window.location.href = 'orders.jsp?page=' + (currentPage - 1);
		        }
		    }
		
		    function goToNextPage() {
		        var currentPage = parseInt('<c:out value="${currentPage}"/>');
		        if (currentPage < 1) {//上限为1
		            window.location.href = 'orders.jsp?page=' + (currentPage + 1);
		        }
		    }
		</script>
		 --%>
        <div class="pagination">
            <button class="prev">上一页</button>
            <span>${currentPage} / 1</span>
            <button class="next">下一页</button>
        </div>
        <%--
        <div class="left-btn-container">
		    <button class="history-btn"><a href="goods.jsp">取消查看</a></button>
		</div>
        --%>
    </div>

</div>

</c:if>
<c:if test="${empty sessionScope.admin }">
<div class="else">
<span>您还未登录，请先<a href="index.jsp">登录</a></span>
</div>
</c:if>
</html>
