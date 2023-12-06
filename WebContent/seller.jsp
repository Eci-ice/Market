<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name = "viewport"
              content = "width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <meta http-equiv = "X-UA-Compatible" content = "ie=edge">
<title>卖家后台管理</title>
<!-- 未登录框 -->
<style type="text/css">
.else{
position:absolute;
top:1040%;
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
<!-- 导航栏 -->
<style>
body{
margin:0px;
font-family:sans-serif;
background-image: url("img/beijing2.jpg");
background-repeat: no-repeat;
background-size: cover;
background-position: center center;
background-attachment: fixed;
height: 100%;
overflow: auto;
position: relative;
}
.user-info {
    padding: 20px;
    display: block;
    align-items: center;
}
.user-image {
    width: 50px;
    height: 50px;
    background-size: cover;
    border-radius: 50%;
    margin-left: 95px;
    margin-right: 10px;
}
.username {
    font-size: 14px;
    color: #000;
    margin-left: 110px;
}

ul{
width:100%;
height:56px;
padding:10;
list-style-type:none;
background-color:#696969;
display:block;
}
li{
text-align:center;
float:left;
padding: 14px 16px;
}
li a {
  display: block;
  color: white;
  text-align: center;
  text-decoration: none;

}
li a:hover{
color: white;
background-color:#696969;
}
body{ 
margin:0 auto;
padding:0 auto;
}
</style>


<!-- 左侧 -->
<style>
* {
margin: 0;
padding: 0;
}
.content {
width: 100%;
height: 100%;
}
.content-left {
width: 19%;
height:850px;
background-color: #9A9A9A;
float: left;
}
.content-right {
width: 81%;
height: 600px;
background-color:rgba(255,250,250,0.5);
float: left;
}
.left-title {
width: 100%;
height: 50px;
}
.left-title > a {
display: block;
width: 100%;
height: 50px;
line-height: 50px;
text-align: center;
color: black;
text-decoration: none;
            }
.seg {
height: 1px;
width: 100%;
background-color: black;
}
.nav {
/*上下5,左右0*/
margin: 5px 0;
}
.account{/*账号管理*/
height: 1px;
width: 100%;
background-color: black;
}
.nav-title {
height: 40px;
width: 100%;
color: #FFFFFF;
text-align: center;
line-height: 40px;
cursor: pointer;
}
.nav-content {
width: 100%;
height: 100%;
color: #000000;
display: flex;        /* 新增：使用flex布局 */
justify-content: center;  /* 新增：使内容水平居中 */	
align-items: center;  /* 新增：使内容垂直居中 */
flex-direction: column;
}
.nav-info {
color: #000000;
display: flex;        /* 新增：使用flex布局 */
align-items: center;  /* 新增：使内容垂直居中 */
flex-direction: column;
}
.nav-content > a {
display: block;
width: 100%;
height: 30px;
color: white;
text-decoration: none;
text-align: center;
line-height: 30px;
font-size: 13px;
}
.nav-content > a:hover {
color: #FFFFFF;
background-color:#696969;
}

            /*右边内容区*/
.content-right{
font-size: 50px;
line-height: 600px;
text-align: center;
background-color:#D3D3D3;
}
.separator-line {
    display: block;
    width: 100%; /* 或者您需要的宽度 */
    height: 200%; /* 保持图片的纵横比 */
    margin: 10px 0; /* 添加一些边距 */
}
.black-text {
    color: white;
}
.nav-content > h3 {
    margin-bottom: 20px;   /* Increase or decrease this value to adjust spacing */
}

</style>
        
<!-- 商品信息 -->
<style type="text/css">
table, th, td {
border: solid 1px #efefef;
}
table {
width: 100%;
height:40px;
margin: auto;
border-collapse: collapse;
text-align: center;
border-radius: 6px;
}
td,th{
padding: 10px;
}
tr:first-child {
background-color: #efefef;
border-radius: 4px 4px 0 0;
border-bottom: solid 1px #ddd;
}
</style>

<!-- 右边显示具体信息 -->
<style type="text/css">
div.my { 
width: 80%;
height: 850px;
/*border: 1px solid red;*/
float: left;
overflow: hidden;
}
</style>

<script src = "/js/jquery-3.6.1.js"></script>
 <script src = "https://code.jquery.com/jquery-3.2.1.min.js"
                integrity = "sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
                crossorigin = "anonymous"></script>
        <script>
            $(function () {

                $('.nav-menu').each(function () {
                    $(this).children(".nav-content").hide();
                });

                $(".nav-title").each(function () {
                    var navConEle = $(this).parents(".nav-menu").children(".nav-content");
                    $(this).click(function(){
                        if (navConEle.css("display") != "none") {
                            navConEle.hide(300);
                        } else {
                            $(".nav-menu").each(function () {
                                $(this).children(".nav-content").hide(300);
                            });
                            navConEle.show(300);
                        }
                    });
                });
                $(".nav-content>a").each(function () {
                    $(this).click(function () {
                        $(".content-right").html($(this).html());
                    });
                });
            });
        </script>
</head>

<body>
	<c:if test="${not empty sessionScope.admin }"> 
	<ul class="daohang">
	<li><b>欢迎进入"喵咪美食坊"!</b></li>
	<li style="float:right"><a href="quitloginservlet">退出登录	</a></li>
	</ul>
	
	<div class = "content">
        <!--左侧导航栏-->
		<div class = "content-left">
		   
               
			<!--菜单栏导航-->
	    	<div class = "nav">
	    	<!--每一个菜单栏项-->
	        	<div class = "nav-menu">
	            	<!--主标题-->
	            	 <div class="nav-info">
						<div class="user-image" style="background-image: url('img/catbackground.png'); margin-right: 80px;"></div>
						<div class="username" style="margin-right: 100px;">${sessionScope.admin.username}</div>
						<img src="img/line.png" alt="分割线" class="separator-line"> <!-- 新增图片 -->
					</div>
	            	<!--子标题-->
	            	<div class = "nav-content">
	                	<h3><a href = "update_pwd.jsp" target="main" class="black-text">修改密码</a></h3>
		             	<h3><a href = "upload_goods.jsp" target="main" class="black-text">发布商品</a></h3>
		             	<!-- <h3><a href = "show_goods.jsp" target="main" class="black-text">修改商品</a></h3>	 -->
		             	<h3><a href = "allorderservlet" target="main" class="black-text">查看意向订单</a></h3>
		             	<!-- <h3><a href = "history.jsp" target="main" class="black-text">查看历史订单</a></h3> -->
		                <h3><a href = "upload_moregoods.jsp" target="main" class="black-text">发布多个商品</a></h3>	
		             	<h3><a href = "allhistorygoodservlet" target="main" class="black-text">查看历史商品</a></h3>		
		             	<h3><a href = "allgoodservlet" target="main" class="black-text">查看全部商品</a></h3>
	            	</div>
	        	</div>
	    	</div>
		</div>
	    <!--右侧内容区-->
	    <div class="my">
			<iframe name="main" width="100%" height="100%" src="show_goods.jsp" frameborder="0" seamless></iframe>
		</div>
	</div>
	
	
	</c:if>
	<c:if test="${empty sessionScope.admin }">
		<div class="else">
			您还未登录，请先<a href="index.jsp">登录</a>
		</div>
	</c:if> 

</body>
</html>