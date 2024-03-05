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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
		top: 140px;
		left: 12%;
		width: 70%;
		background-color: white;
	}

	
	#search_list div {
	    border-bottom: 1px solid black; 
	}
	
	    .media-container {
        position: relative;
        width: 100px;
        height: 100px;
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
            location.href = "show_historygoods.jsp?currentPage=" + currentPage;
        }
    }

    function goToNextPage() {
        if (currentPage < totalPages) {
            currentPage++;
            console.log(currentPage);
            location.href = "show_historygoods.jsp?currentPage=" + currentPage;
        }
    }
    
    function search() {
        var keyword = document.getElementsByName('keyword')[0].value;
        var kind = document.getElementsByName('search_kind')[0].value;
        var xhr = new XMLHttpRequest();// 使用Ajax发送请求

        xhr.open('POST', 'searchgoodservlet', true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.overrideMimeType("application/json; charset=UTF-8");

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
        xhr.send('keyword=' + encodeURIComponent(keyword) + '&search_kind=' + encodeURIComponent(kind) +'&ishistory=' + encodeURIComponent('0'));
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
</script>

<c:if test="${not empty sessionScope.admin }">

<!-- 模态窗口 -->
<div id="modal" style="display: none; position: fixed; z-index: 1; left: 0; top: 0; width: 100%; height: 100%; overflow: auto; background-color: rgba(0,0,0,0.4);">
    <span class="close-button" style="position: absolute; top: 20px; right: 30px; color: #f1f1f1; font-size: 35px; font-weight: bold; cursor: pointer;">&times;</span>
    <img id="modalImage" style="margin: auto; display: none; width: 80%; max-width: 700px;">
    <video id="modalVideo" controls style="margin: auto; display: none; width: 80%; max-width: 700px;"></video>
</div>

	<div id="a">
		<div class="container">
		    <center>
			<h2>历史商品信息</h2>
		    <form action="successsearchservlet" method="post">
				<input type="text" name="keyword" placeholder="搜索商品"  oninput="search()">&nbsp;&nbsp;&nbsp;
				<select name="search_kind" id="search_kind">
                    <option value="猫咪主粮">猫咪主粮</option>
                    <option value="猫咪零食">猫咪零食</option>
                    <option value="猫咪日用">猫咪日用</option>
                </select><br><br>&nbsp;&nbsp;&nbsp;
				<input type="submit" value="搜索" style="width:130px">
				<input type="hidden" id="ishistory" name="ishistory" value="1">
			</form>
			<div id="search_list"></div>  
			</center>
			<table border="1px" align=center cellspacing="0">
			    <tr>
			    <th>ID</th>
			    <th>名称</th>
			    <th>描述</th>
			    <th>单价</th>
			    <th>展示内容</th>
			    <th>类别</th>
			    <th>子类别</th>
			    <th>创建日期</th>
			    <%--
			    <th>意向人数</th>
			    <th>最终购买人</th>
			    --%>
			    </tr>
					<c:forEach items="${sessionScope.sL}" var="g" begin="${(currentPage-1)*5}" end="${currentPage*5-1}">
				<tr>
				<td>${g.goodid}</td>
				<td>${g.goodname}</td>
				<td>${g.description}</td>
				<td>${g.price}</td>
				<td>
			        <c:set var="mediaFiles" value="${fn:split(g.picture, ',')}" /> <!-- 分割媒体文件路径字符串 -->
			        <div class="media-container">
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
			        </div>
			    </td>
		        <td>${g.kind}</td>
				<td>${g.subkind}</td>
				<td>${g.createdate}</td>
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
</c:if>
<c:if test="${empty sessionScope.admin }">
	<div class="else">
	<span>您还未登录，请先<a href="index.jsp">登录</a></span>
	</div>
</c:if>

</body>
</html>