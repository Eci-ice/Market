<%--
  Created by IntelliJ IDEA.
  User: banana
  Date: 2023/10/23
  Time: 11:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>买家商品主页</title>
    <link rel="stylesheet" type="text/css" href="BShop.css"/>
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
button {
  padding: 5px 10px;
  width: 150px;
  height: 50px;
  margin: 5px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}
.butt-1 {
  /* 购买按钮 */
  position: relative;
  background-color: orange;
  font-size: 20px;
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
	 .media-container {
        position: relative;
        width: 90%;
        height: 90%;
        margin-left:10px;
        margin-top:100px;
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
        width: 30px;
        transform: translateY(-50%);
        background: rgba(133, 196, 110, 0.7);
    }
    .media-container .prev-button {
        left: 10px;
    }
    .media-container .next-button {
        right: 10px;
    }
</style>

<script>
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
</script>
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
        <c:set var="mediaFiles" value="${fn:split(nowg.picture, ',')}" /> <!-- 分割媒体文件路径字符串 -->
		<div class="media-container">
			<button class="prev-button">＜</button>
			<button class="next-button">＞</button>
			<c:forEach var="file" items="${mediaFiles}"> <!-- 遍历媒体文件路径数组 -->
				 <c:choose>
				       <c:when test="${fn:endsWith(file, '.mp4')}"> <!-- 如果文件是MP4视频 -->
				            <video src="${file}" controls width="200"></video>
				       </c:when>
				       <c:otherwise> <!-- 否则，我们假设文件是图片 -->
				            <img src="${file}" alt="" width="200">
				       </c:otherwise>
				</c:choose>
			</c:forEach>
		</div>
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
     <a href="BuyerFillInfo.jsp" alt="" title="购买">
     <button class="butt-1" >购买该商品</button></a>
  <!--非A    <button class="butt-1" >加入愿望单</button> -->
</div>

</body>
</html>
