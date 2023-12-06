<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>卖家登录</title>
<!-- 未登录框 -->
<style type="text/css">
html {
    height: 100%;
}
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

<!-- 背景 -->
<style>
body{
margin:0px;
padding:10px;
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
.cat-background {
    position: absolute;
    top: 25%; /* 调整这个值可以控制图片在页面中的高度位置 */
    left: 50%; /* 这会让图片水平居中 */
    transform: translate(-50%, -50%);
    background-image: url('img/catbackground.png'); /* 请确保路径正确 */
    background-repeat: no-repeat;
    background-size: cover; /* 这会让背景图片覆盖整个元素区域 */
    width: 150px; /* 设置图片显示区域的宽度 */
    height: 150px; /* 设置图片显示区域的高度 */
    z-index: -1; /* 这会让图片在其他内容下方显示 */
}
</style>

<!-- 导航 -->

<style>
#b{
    text-align:center;
    color: red;
}
</style>
<style type="text/css">
ul{
width:100%;
height:80px;
padding:10;
list-style-type:none;
background-color:rgba(255,250,250,0.5);
display:block;
}
li{
float:left;
}
h2{
	padding-left:20px;
	font-family:KaiTi;
}
</style>

<!-- 登录框 -->
<style>
.login{
position:absolute;
top:65%;
left:50%;
transform:translate(-50%,-50%);
width:400px;
padding:30px;
padding-bottom:20px;
background: rgba(224,224,224,.8);
box-sizing:border-box;
box-shadow: 0px 15px 25px rgba(0,0,0,.5);
border-radius:20px;
align-items: center; /* 子元素水平居中 */
}
.login h4{
margin:0 0 30px;
padding:0;
color:＃696969;
text-align:center;
}
.login .input{
position:relative;
}
.login .input input{
width:100%;
padding:10px 0;
font-size:16px;
color:＃696969;
letter-spacing:1px;
margin-bottom:10px;
border:none;
border-bottom:1px solid #fff;
outline:none;
background:transparent;
}
.login .input label{
position:absolute;
top:5px;
left:0px;
padding:10px 0;
font-size:16px;
color:＃696969;
pointer-events:none;
transition:.5s;
}
.login .input input:focus ~ label,
.login .input input:valid ~ label
{
top:-18px;
left:0;
color:#696969;
font-size:14px;
}
/* .login input[type="submit"]{
border:none;
outline:none;
left:60%;
font-size:20px;
color:#000000;
background:#C0C0C0;
padding:10px 20px;
cursor:pointer;
border-radius:10px;
} */
.image-buttons {
    display: flex;
    width:100%;
    flex-direction: column;
    align-items: center;
    justify-content: space-between;
    margin: 0px 0;
}

.image-buttons img {
    width: 350px;  /* 您可以根据需要调整图片大小 */
    height: 40px;
    cursor: pointer;
    transition: 0.3s;

    /* 如果需要，您可以添加一些边框或其他样式 */
    border-radius: 0px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}
.input-image-button {
    width: 350px;
    height: 40px;
}
.butt-1 {
  position: relative;
  border-radius: 10px;
  margin:10px;
  margin-left:20px;
  border:1px;
  width:300px;
  background-color: orange;
  font-size: 22px;
  padding: 10px 20px; /* 调整字体与边框的距离 */
  cursor: pointer; /* 设置悬停时的鼠标样式为手型 */
  color:white;
  text-align: center;
}

</style>


</head>
	<body>
		<ul class="daohang">
			<li><h2>"喵咪美食坊"欢迎您!</h2></li>
			<div class="cat-background"></div>
		</ul>
		<c:if test="${empty sessionScope.admin }">
			<div class="login">
				<form action="loginservlet" method="post">
					<!-- 账号框 -->
					<div class="input">
						<img src="img/account.png" alt="账号" style="height: 25px; vertical-align: middle;"> 
						<input type="text" name="username"><br><br>
					</div>
					<!-- 密码框 -->
					<div class="input">
						<img src="img/password.png" alt="密码" style="height: 25px; vertical-align: middle;"> 
						<input type="password" name="pwd"><br><br>
					</div>
					<!-- 登录注册根据action和value在同一个servlet中检测 -->
					<a href="forgetpassword.jsp" alt="忘记密码" title="忘记密码" >忘记密码</a>
					<div class="image-buttons">
					    <input class="butt-1" type="submit" alt="登录" title="登录" name="action" value="登录" >
					  <!--非基线<a href="choose_register.jsp" alt="注册" title="注册" class="butt-1">注册</a>-->
					    <a href="sellinggoodservlet"alt="游客访问" title="游客访问" class="butt-1">游客访问</a>
					    
					</div>
				</form>
				
			</div>
			<div id="b">
				<c:if test="${not empty requestScope.err}">
					${requestScope.err}
				</c:if>
			</div>
		</c:if>
		
		<c:if test="${not empty sessionScope.admin }">
			<div class="else">您已登录，无需再登录!!!<br>
			<c:if test="${sessionScope.admin.power eq 1}">
				<a href="seller.jsp">返回</a></div>
			</c:if>
			<c:if test="${sessionScope.admin.power eq 0}">
				<a href="BuyerMain.jsp">返回</a></div>
			</c:if>
		</c:if>
	
	</body>
</html>