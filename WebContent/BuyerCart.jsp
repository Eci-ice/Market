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
        updateTotalPrice();//刷新
        var modal = document.querySelector('#modal');
        modal.querySelector('.close-button').addEventListener('click', function() {
            modal.style.display = 'none';
        });
        
    };
    
    var isEditing = false;  // 全局变量，用于跟踪编辑状态
    function toggleEdit() {
        isEditing = !isEditing;  // 切换编辑状态
        
        // 更新所有的编辑按钮和删除按钮
        var editallButton = document.getElementById('editall-button');
        var deleteButtons = document.getElementsByClassName('delete-button');
        var numberInputs = document.getElementsByClassName('number-input');
        var incrementButtons = document.getElementsByClassName('increment-button');
        var decrementButtons = document.getElementsByClassName('decrement-button');
//        console.log(editallButton.textContent);

        editallButton.textContent = isEditing ? '查看' : '编辑';
        
        for (var i = 0; i < deleteButtons.length; i++) {
        	
            deleteButtons[i].style.display = isEditing ? 'block' : 'none';
            numberInputs[i].disabled = !isEditing;
            incrementButtons[i].style.display = isEditing ? 'inline' : 'none';
            decrementButtons[i].style.display = isEditing ? 'inline' : 'none';
            if (!isEditing) {
            	var buyingid = numberInputs[i].id.split('-')[2];  // 从id中获取buyingid
                modifyNumber(buyingid, numberInputs[i].value);
                updateTotalPrice();//刷新
            }
	     }
        
	}
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

	    
	    function deleteItem(buyingid) {
	        var xhr = new XMLHttpRequest();
	        xhr.open("POST", "modifycartservlet", true);
	        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	        xhr.onreadystatechange = function() {
	            if (this.readyState == 4 && this.status == 200) {
	                window.location.href = "BuyerCart.jsp";  // 在这里设置跳转的页面
	            }
	        };
	        xhr.send("buyingid=" + buyingid + "&number=0");
	    }
	    
	    var cart = {}; // 购物车

	    function toggleBuy(buyingid,goodid) {
	        var button = document.getElementById('buy-button-' + buyingid);
	        if (button.innerText == '购买') {
	            // 添加到购物车
	            button.innerText = '取消购买';
	            var number = document.getElementById('number-input-' + buyingid).value;
	            cart[goodid] = number;
	        } else {
	            // 从购物车中移除
	            button.innerText = '购买';
	            delete cart[goodid];
	        }
	        updateTotalPrice();
	    }

	    function updateTotalPrice() {
	        var total = 0;
	        for (var buyingid in cart) {
	            var number = cart[buyingid];
	            var price = document.getElementById('price-' + buyingid).innerText;
	            total += number * price;
	        }
	        document.getElementById('total-price').innerText = total;
	    }
	    
	    function addToLike(goodid) {
	    	var url = 'addlikeservlet?goodid=' + goodid+'&iscancel='+0;
	        //console.log(url); // 添加这一行来打印URL
	        window.location.href = url;
	    }
	    function submitOrder() {
	        // 创建一个新的表单数据对象
	        var formData = new FormData();

	        // 遍历购物车中的每一项
	        for (var goodid in cart) {
	            // 将商品ID和数量添加到表单数据对象中
	            formData.append('goodid[]', goodid);
	            formData.append('number[]', cart[goodid]);
	        }

	        // 创建一个新的XHR对象
	        var xhr = new XMLHttpRequest();

	        // 设置XHR对象的回调函数
	        xhr.onload = function() {
	            if (xhr.status >= 200 && xhr.status < 300) {
	                // 请求成功，处理响应数据
	                console.log(xhr.responseText);
	            } else {
	                // 请求失败，打印错误信息
	                console.error(xhr.statusText);
	            }
	        };

	        // 打开一个POST请求
	        xhr.open('POST', '/createcartorderservlet', true);

	        // 发送请求
	        xhr.send(formData);
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
			<h2>我的购物车</h2>
		</center>
		<button id="editall-button" onclick="toggleEdit()">编辑</button>

<table border="1px" align=center cellspacing="0">
    <tr>
        <th>展示内容</th>
        <th>名称</th>
        <th>购买数量</th>
        <th>小计</th>
        <th>操作</th>
    </tr>
    <c:forEach items="${sessionScope.cL}" var="g" begin="${(currentPage-1)*5}" end="${currentPage*5-1}">
    	<c:if test="${g.number > 0}">
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
		            <c:if test="${g.state == 0}">
			            <td>${g.goodname}</td>
			            <td>
			                 <button class="decrement-button" style="display: none;" onclick="decrementNumber('number-input-${g.buyingid}')">-</button>
			                <input type="number" id="number-input-${g.buyingid}" min="1" max="${g.numbermax}" value="${g.number}" class="number-input" onchange="updatePrice('${g.buyingid}',${g.numbermax});" disabled>
			                <button class="increment-button" style="display: none;" onclick="incrementNumber('number-input-${g.buyingid}',${g.numbermax})">+</button>
			            </td>
			            <td id="price-${g.buyingid}" style="display: none;">
			                ${g.price}
			            </td>
			            <td id="total-${g.buyingid}">
			                ${g.price * g.number}
			            </td>
			            <td>
			            	<button onclick="addToLike(${g.goodid})">收藏商品</button>
			                <button class="delete-button" style="display: none;" onclick="deleteItem(${g.buyingid})">删除商品</button>
			                <button id="buy-button-${g.buyingid}" onclick="toggleBuy('${g.buyingid}','${g.goodid}');">购买</button>
                			<input type="hidden" id="buy-number-${g.buyingid}" name="buy-number-${g.buyingid}" value="${g.number}">
			            </td>
		            </c:if>
		            <c:if test="${g.state ne 0}">
			            <td></td>
			            <td></td>
			            <td></td>
			            <td>
			               <button class="delete-button" style="display: none;" onclick="deleteItem(${g.buyingid})">删除商品</button>
			            </td>
		            </c:if>
		        </tr>
		  </c:if>
	</c:forEach>

		</table>
		<div>
		   <h3> 总计:<span id="total-price"></span><h3>
		     <button onclick="submitOrder();">提交订单</button>
		</div>
	    <div class="pagination">
		    <button class="prev" onclick="goToPrevPage()">上一页</button>
		    <span id="page-info">第${currentPage}页 </span>
		    <%-- 当前页码小于总页数时，才显示“下一页”按钮 --%>
		   	<button class="next" onclick="goToNextPage()">下一页</button>
		</div>
	</div>
</body>
</html>
