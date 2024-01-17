<%--
  Created by IntelliJ IDEA.
  User: banana
  Date: 2023/10/18
  Time: 10:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
<!--     <link rel="stylesheet" type="text/css" href="BMain.css"/> -->
    <title>全部收藏</title>
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
 .media-container {
        position: relative;
        width: 200px;
        height: 200px;
        overflow: hidden;
    }
    .media-container img, .media-container video {
        display: none;
        width: 100%;
        height: 100%;
        object-fit: contain;
    }
    .media-container img.active, .media-container video.active {
        display: block;
    }
    .media-container button {
        position: absolute;
        top: 50%;
        transform: translateY(-50%);
        background: rgba(255, 255, 255, 0.7);
    }
    .media-container .prev-button {
        left: 10px;
    }
    .media-container .next-button {
        right: 10px;
    }
.sold-out img {
    filter: grayscale(100%);  /* 将图片转换为灰度 */
    opacity: 0.6;  /* 降低图片的透明度 */
}

.overlay {
    position: absolute;  /* 使用绝对定位 */
    top: 0;  /* 从顶部开始定位 */
    left: 0;  /* 从左侧开始定位 */
    width: 100%;  /* 覆盖整个容器的宽度 */
    height: 100%;  /* 覆盖整个容器的高度 */
    background-color: rgba(0, 0, 0, 0.5);  /* 添加一个半透明的黑色背景 */
    color: white;  /* 设置文本颜色为白色 */
    text-align: center;  /* 将文本居中对齐 */
    padding-top: 50%;  /* 将文本垂直居中 */
    font-size: 20px;  /* 设置文本大小 */
    display: none;  /* 默认情况下，不显示蒙版 */
}

.sold-out .overlay {
    display: block;  /* 当商品已售出时，显示蒙版 */
}
tr td, tr th {
    min-width: 150px; 
    text-align: center; 
}

</style>
<%
    int currentPage = 1;
    if (request.getParameter("currentPage") != null) {
        currentPage = Integer.parseInt(request.getParameter("currentPage"));
    }
    request.setAttribute("currentPage", currentPage);
%>
<script >
	var currentPage = ${requestScope.currentPage};
	var totalItems = ${sessionScope.gL.size()}; // 商品总数
	var itemsPerPage = 6; // 每页显示的商品数量
	var totalPages = Math.ceil(totalItems / itemsPerPage); 
	
	function goToPrevPage() {
	    if (currentPage > 1) {
	        currentPage--;
	        location.href = "BuyerSearch.jsp?currentPage=" + currentPage;
	    }
	}
	
	function goToNextPage() {
	    if (currentPage < totalPages) {
	        currentPage++;
	        console.log(currentPage);
	        location.href = "BuyerSearch.jsp?currentPage=" + currentPage;
	    }
	}
	
	window.onload = function() {
        var containers = document.querySelectorAll('.media-container');
        containers.forEach(function(container) {
            var mediaFiles = container.querySelectorAll('img, video');
            var index = 0;

            function updateMedia() {
                mediaFiles.forEach(function(file, i) {
                    file.classList.remove('active');
                    if (i === index) {
                        file.classList.add('active');
                    }
                });
            }

            container.querySelector('.prev-button').addEventListener('click', function() {
                index = (index - 1 + mediaFiles.length) % mediaFiles.length;
                updateMedia();
            });

            container.querySelector('.next-button').addEventListener('click', function() {
                index = (index + 1) % mediaFiles.length;
                updateMedia();
            });

            mediaFiles.forEach(function(file) {
                file.addEventListener('click', function() {
                    var modal = document.querySelector('#modal');
                    var modalImage = modal.querySelector('#modalImage');
                    var modalVideo = modal.querySelector('#modalVideo');
                    if (file.tagName === 'VIDEO') {
                        modalImage.style.display = 'none';
                        modalVideo.src = file.src;
                        modalVideo.style.display = 'block';
                    } else {
                        modalVideo.style.display = 'none';
                        modalImage.src = file.src;
                        modalImage.style.display = 'block';
                    }
                    modal.style.display = 'block';
                });
            });

            updateMedia();
        });

        var modal = document.querySelector('#modal');
        modal.querySelector('.close-button').addEventListener('click', function() {
            modal.style.display = 'none';
        });
    };
	    function incrementNumber(inputId, max) {
	        var input = document.getElementById(inputId);
	        if (input.value < max) {
	            input.value = parseInt(input.value) + 1;
	        } else {
	            alert("购买数量不能超过库存");
	        }
	    }
	    function decrementNumber(inputId) {
	        var input = document.getElementById(inputId);
	        if (input.value > 1) {
	            input.value = parseInt(input.value) - 1;
	        } else {
	            alert("购买数量不能为0");
	        }
	    }
	    
	    function modifyNumber(buyingid, number) {
	        var xhr = new XMLHttpRequest();
	        xhr.open("POST", "modifycartservlet", true);
	        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	        xhr.onreadystatechange = function() {
	            if (this.readyState == 4 && this.status == 200) {
	                window.location.href = "BuyerCart.jsp";  // 在这里设置跳转的页面
	            }
	        };
	        xhr.send("buyingid=" + buyingid + "&number=" + number);
	    }
	    function dislike(goodid) {
	    	var url = 'addlikeservlet?iscancel='+1+'&goodid=' + goodid;
	        console.log(url); // 添加这一行来打印URL
	        window.location.href = url;
	    }



</script>
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
	
	<div class="right">
		<center>
			<h2>我的收藏</h2>
		</center>
	   
<table border="1px" align=center cellspacing="0">
    <tr>
        <th>展示内容</th>
        <th>名称</th>
        <th>描述</th>
        <th>操作</th>
    </tr>
    <c:forEach items="${sessionScope.likeList}" var="g" begin="${(currentPage-1)*5}" end="${currentPage*5-1}">
       <tr>
           <td>
               <c:set var="mediaFiles" value="${fn:split(g.picture, ',')}" /> <!-- 分割媒体文件路径字符串 -->
               <div class="media-container ${g.state != 0 ? 'sold-out' : ''}">
                   <button class="prev-button">＜</button>
                   <button class="next-button">＞</button>
                   <c:forEach var="file" items="${mediaFiles}"> <!-- 遍历媒体文件路径数组 -->
                       <c:choose>
                           <c:when test="${fn:endsWith(file, '.mp4')}"> <!-- 如果文件是MP4视频 -->
                               <video src="${file}" controls></video>
                           </c:when>
                           <c:otherwise> <!-- 否则，我们假设文件是图片 -->
                               <img src="${file}" alt="">
                           </c:otherwise>
                       </c:choose>
                   </c:forEach>
                   <c:if test="${g.state != 0}">
                    <div class="overlay">商品不可购买</div>
                </c:if>
               </div>
           </td>
           <td>${g.goodname}</td>
           <td>
                <button class="decrement-button" style="display: none;" onclick="decrementNumber('number-input-${g.buyingid}')">-</button>
               <input type="number" id="number-input-${g.buyingid}" min="1" max="${g.numbermax}" value="${g.number}" class="number-input" disabled>
               <button class="increment-button" style="display: none;" onclick="incrementNumber('number-input-${g.buyingid}',${g.numbermax})">+</button>
           </td>
           <td>
               <button class="delete-button" onclick="dislike(${g.buyingid})">取消收藏</button>
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
</body>
</html>
