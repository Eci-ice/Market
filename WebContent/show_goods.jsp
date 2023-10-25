<%@page import="sqlimpl.goodsqlimpl"%>
<%@page import="sql.goodsql"%>
<%@page import="java.util.ArrayList"%>
<%@page import="vo.good"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>历史商品</title>
</head>
<body>
<!-- 未登录框 -->
<style type="text/css">
.else{
position:absolute;
top:40%;
left:50%;
transform:translate(-50%,-50%);
width:450px;
padding:30px;
background: rgba(224,224,224,.8);
box-sizing:border-box;
box-shadow: 0px 15px 25px rgba(0,0,0,.5);
border-radius:16px;
text-align:center;
font-family:KaiTi;
font-size:26px;
}
a{
	text-decoration:none;
}
</style>
<style>
.container {
        font-family: Arial, sans-serif;
        width: 80%;
        margin: 2% auto;
        border: 1px solid #ccc;
        padding: 20px;
        box-shadow: 0 0 10px rgba(0,0,0,0.1);
    }

    table {
        width: 100%;
        border-collapse: collapse;
        margin-bottom: 20px;
    }

    th, td {
        border: 1px solid #ccc;
        padding: 10px;
        text-align: left;
    }

    th {
        background-color: #f2f2f2;
    }

    img {
        width: 50px;
        height: auto;
    }

    .pagination {
        display: flex;
        justify-content: center;
        align-items: center;
        margin-bottom: 20px;
    }

    .history-btn, .prev, .next {
        padding: 10px 20px;
        color: #fff;
        border: none;
        cursor: pointer;
        margin: 0 5px;
    }
    
    .history-btn {
    	background-color: rgb(237, 137, 108);
    	border-radius: 8px;
    }
    
    .prev, .next {
    	background-color: rgb(237, 196, 110);
    }

    .history-btn:hover, .prev:hover, .next:hover {
        background-color: #d32f2f;
    }
    
    .history-btn a {
        text-decoration: none;
        color: white;
    }
    
    .history-btn a:hover {
        text-decoration: none; 
    }
    .left-btn-container {
	    margin-right: auto;
	    display: flex;
	    align-items: center;
	}
</style>
<script>
    // 当前页面的页数
    let currentPage = 1;
    const totalPages = 5; // 总页数，可以根据您的需求进行调整

    document.querySelector('.prev').addEventListener('click', function() {
        if (currentPage > 1) {
            currentPage--;
            updatePage();
        }
    });

    document.querySelector('.next').addEventListener('click', function() {
        if (currentPage < totalPages) {
            currentPage++;
            updatePage();
        }
    });

    function updatePage() {
        // 这里可以进行AJAX调用，从数据库获取相应页数的商品数据，并更新页面
        document.querySelector('.pagination span').textContent = `${currentPage}/${totalPages}`;
    }

    document.querySelector('.history-btn').addEventListener('click', function() {
        window.history.back(); // 返回上一个浏览过的页面
    });
</script>

<c:if test="${not empty sessionScope.admin }">
<div id="a">
<div class="container">
        <center>
	<h2>全部商品信息</h2>
	</center>
	<table border="1px" align=center cellspacing="0">
	    <tr>
	    <th>ID</th>
	    <th>名称</th>
	    <th>描述</th>
	    <th>单价</th>
	    <th>图片</th>
	    <th>状态</th>
	    <th>操作</th>
	    <%--
	    <th>意向人数</th>
	    <th>最终购买人</th>
	    --%>
	    </tr>
		<c:forEach items="${sessionScope.gL}" var="g">
		<tr>
		<td>${g.goodid}</td>
		<td>${g.goodname}</td>
		<td>${g.description}</td>
		<td>${g.price}</td>
		<td>
            <img src="${g.picture}" alt="" width="174">
        </td>
		<td><c:if test="${g.state eq 0}">上架</c:if>
		<c:if test="${g.state eq 1}">冻结</c:if>
		<c:if test="${g.state eq 2}">售出</c:if>
		</td>
		<td><a href="deletegoodservlet?&goodid=${g.goodid}">下架</a>
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

</body>
</html>