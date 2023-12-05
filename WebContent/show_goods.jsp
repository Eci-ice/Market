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
	form {
    display: flex; /* 让表单内的元素在同一行显示 */
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
		top: 140px;
		left: 12%;
		width: 70%;
		background-color: white;
	}

	
	#search_list div {
	    border-bottom: 1px solid black; 
	}
	
	
	
	
	
	
	
	
    .modal {
        display: none; /* 默认隐藏 */
        position: fixed; /* 固定在页面上 */
        z-index: 1; /* 处于顶层 */
        left: 0;
        top: 0;
        width: 100%; /* 宽度为整个屏幕 */
        height: 100%; /* 高度为整个屏幕 */
        overflow: auto; /* 如果内容过多则启用滚动条 */
        background-color: rgba(0,0,0,0.4); /* 半透明的黑色背景 */
        padding-top: 60px;
    }

   .modal-content {
    background-color: #fff;
    margin: 5% auto;
    padding: 20px;
    border: 1px solid #ddd;
    width: 60%;
    border-radius: 5px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.close {
    color: #aaa;
    float: right;
    font-size: 28px;
    font-weight: bold;
    cursor: pointer;
}

.close:hover {
    color: #000;
    text-decoration: none;
}

    .close:focus {
        color: black;
        text-decoration: none;
        cursor: pointer;
    }
    
    .price-modal-content {
    background-image: url('img/a.jpg'); /* 更改为您自己的图片路径 */
    background-size: cover; /* 调整背景图片大小以填充整个容器 */
    background-position: center; /* 居中显示背景图片 */
    opacity: 1; /* 调整透明度（0.8表示80%的不透明度） */


}
</style>
<%
    int currentPage = 1;
    if (request.getParameter("currentPage") != null) {
        currentPage = Integer.parseInt(request.getParameter("currentPage"));
    }
    request.setAttribute("currentPage", currentPage);
  
%>
<script>
    var currentPage = ${requestScope.currentPage};
    var totalItems = ${sessionScope.gL.size()}; // 商品总数
    var itemsPerPage = 5; // 每页显示的商品数量
    var totalPages = Math.ceil(totalItems / itemsPerPage); 
    
    function goToPrevPage() {
        if (currentPage > 1) {
            currentPage--;
            location.href = "show_goods.jsp?currentPage=" + currentPage;
        }
    }

    function goToNextPage() {
        if (currentPage < totalPages) {
            currentPage++;
            console.log(currentPage);
            location.href = "show_goods.jsp?currentPage=" + currentPage;
        }
    }
    
    function search() {
    	var keyword = document.getElementsByName('keyword')[0].value;
        var xhr = new XMLHttpRequest();// 使用Ajax发送请求
        xhr.open('GET', 'searchgoodservlet?keyword=' + keyword, true);
        xhr.onreadystatechange = function() {
            if (xhr.readyState == 4 && xhr.status == 200) {
                var results= JSON.parse(xhr.responseText);// 当请求成功时，使用返回的数据更新搜索结果列表
                var resultsDiv = document.getElementById('search_list');
                resultsDiv.innerHTML = '';
                for (var i = 0; i < results.length; i++) {
                    var div = document.createElement('div');
                    div.textContent = results[i];
                    resultsDiv.appendChild(div);
                }
            }
        };
        xhr.send();
    }
  



    
    
</script>

<c:if test="${not empty sessionScope.admin }">
<div id="a">
<div class="container">
    <center>
	<h2>全部商品信息</h2>
	 <form action="successsearchservlet" method="post">
		<input type="text" name="keyword" placeholder="搜索商品"  oninput="search()">
		<input type="submit" value="搜索">
	</form>
	<div id="search_list"></div>
	</center>
	<table border="1px" align=center cellspacing="0">
	    <tr>
	    <th>ID</th>
	    <th>名称</th>
	    <th>描述</th>
	    <th>库存</th>
	    <th>单价</th>
	    <th>图片</th>
	    <th>状态</th>
	    <th>操作</th>
	    <th>价格管理</th>
	    <%--
	    <th>意向人数</th>
	    <th>最终购买人</th>
	    --%>
	    </tr>
		<c:forEach items="${sessionScope.gL}" var="g" begin="${(currentPage-1)*5}" end="${currentPage*5-1}">
		<tr>
		<td>${g.goodid}</td>
		<td>${g.goodname}</td>
		<td>${g.description}</td>
		<td <c:if test="${g.number <5}"> style="font-weight: bold; color: red;"</c:if>>${g.number}</td>
		<td>${g.price}</td>
		<td>
            <img src="${g.picture}" alt="" width="174">
        </td>
		<td><c:if test="${g.state eq 0}">上架</c:if>
		<c:if test="${g.state eq 1}">冻结</c:if>
		<c:if test="${g.state eq 2}">售出</c:if>
		
		<td><a href="deletegoodservlet?&goodid=${g.goodid}">下架</a>
		</td>
		<td>
    <a href="#" onclick="openPriceModal(${g.goodid}, '${g.goodname}', ${g.price})">修改价格</a>
</td>
		
	    </tr>
	    </c:forEach>
	</table>
        <div class="pagination">
		    <button class="prev" onclick="goToPrevPage()">上一页</button>
		    <span id="page-info">第${currentPage}页 </span>
		    <%-- 当前页码小于总页数时，才显示“下一页”按钮 --%>
		   	<button class="next" onclick="goToNextPage()">下一页</button>
		</div>
    </div>

</div>


<!-- 修改价格的弹窗 -->
<div id="priceModal" class="modal">
    <div class="modal-content price-modal-content">
        <span class="close" onclick="closePriceModal()">×</span>
        <h2><strong>修改商品价格</strong></h2>
        <!-- 商品信息 -->
        <div class="product-info">
            <p><strong>商品ID:</strong> <span id="goodId"></span></p>
            <p><strong>商品名称:</strong> <span id="goodName"></span></p>
            <p><strong>原价格:</strong> <span id="originalPrice"></span></p>
        </div>
        <!-- 输入新价格 -->
        <label for="newPrice"><strong>修改后价格:</strong></label>
        <input type="text" id="newPrice" placeholder="输入新价格">
        <div id="error-message" style="color: red;"></div>
        
        <!-- 提交修改按钮 -->
        <form id="updatePriceForm" method="POST" action="UpdatePriceServlet">
            <input id="newPriceInput" type="hidden" name="newPrice">
            <input id="goodIdInput" type="hidden" name="goodId">
        </form>
        <button onclick="updatePrice()"><strong>确认修改</strong></button>
    </div>
</div>

<!-- 修改价格弹窗 -->
<%
    HttpSession sessiona = request.getSession();
    String successMessage = (String) sessiona.getAttribute("successMessage");
    if (successMessage != null && !successMessage.isEmpty()) {
%>
    <script>
        // 使用JavaScript显示成功消息的弹窗
        alert("<%= successMessage %>");
    </script>
<%
    // 清除Session中的成功消息属性，以防止在后续页面加载时再次显示
    sessiona.removeAttribute("successMessage");
    }
%>


</c:if>
<c:if test="${empty sessionScope.admin }">
<div class="else">
<span>您还未登录，请先<a href="index.jsp">登录</a></span>
</div>
</c:if>
<script>

//aaaaaaaaaaaaaaaaa
function validatePrice(input) {
    // 正则表达式检查输入是否为数字，最多十位数
    var regex = /^\d{1,10}$/;
    return regex.test(input);
}
function openPriceModal(goodId, goodName, originalPrice) {
 var modal = document.getElementById("priceModal");

 document.getElementById("goodId").textContent = goodId;
 document.getElementById("goodName").textContent = goodName;
 document.getElementById("originalPrice").textContent = originalPrice;

 modal.style.display = "block";
}


 function closePriceModal() {
     // Get the modal element
     var modal = document.getElementById("priceModal");

     // Hide the modal
     modal.style.display = "none";
 }
 
 
 

function updatePrice() {
    var newPrice = document.getElementById("newPrice").value.trim();
    var goodId = document.getElementById("goodId").textContent;
    var errorMessage = document.getElementById("error-message");

    if (validatePrice(newPrice)) {
        errorMessage.textContent = ''; // 清除之前的错误消息
        var newPriceInput = document.getElementById("newPriceInput");
        newPriceInput.value = newPrice; // 更新新价格输入值

        var goodIdInput = document.getElementById("goodIdInput");
        goodIdInput.value = goodId; // 更新商品ID输入值

        var form = document.getElementById("updatePriceForm");
        form.submit(); // 提交表单
        closePriceModal(); // 关闭模态窗口
    } else {
        errorMessage.textContent = '请输入数字且不能超过十位数'; // 显示错误消息
        errorMessage.style.color = 'red'; // 设置错误消息为红色
    }
}



</script>
</body>
</html>