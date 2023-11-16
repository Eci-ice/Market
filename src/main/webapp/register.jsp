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
height:80px;
list-style-type:none;
background-color:rgba(255,250,250,0.5);
display:block;
}
li{
float:left;
}
h2{
	display: flex;
    flex-direction: column;
    align-items: center;
	padding-left:20px;
	font-family:Heiti;
}
.registration-title {
            text-align: center;
            font-family: Heiti;
            margin: 0;
            padding: 0;
            font-size: 26px;
            margin-top: 20px;
        }

</style>

<!-- 登录框 -->
<style>
.login{
top: 50%;
left: 50%;
transform: translate(-50%, -50%);
position:absolute;
top:55%;
left:50%;
transform:translate(-50%,-50%);
width:500px;
padding:30px;
padding-bottom:20px;
background: rgba(224,224,224,.8);
box-sizing:border-box;
box-shadow: 0px 15px 25px rgba(0,0,0,.5);
border-radius:20px;
}
.login h3{
margin:0px;
color:＃696969;
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
.login input[type="submit"]{
border:none;
outline:none;
left:60%;
font-size:20px;
color:#000000;
background:#C0C0C0;
padding:10px 20px;
cursor:pointer;
border-radius:10px;
}
.image-buttons {
    display: flex;
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
.title-container {
    display: flex;
    align-items: center;
    justify-content: space-between;
}

.title-container h2 {
    font-family: Heiti;
    margin: 0;
    padding: 0;
    font-size: 26px;
    display:flex;
    margin-top:20px;
    text-align: center;
    justify-content: center;
}



.title-container a {
    text-decoration: none;
    font-family: Heiti;
    font-size: 18px;
    color: black;
    margin-right: 30px;
    margin-top:20px;
    font-weight: bold;
}
</style>
<script>
    var error = '<%= request.getAttribute("error") %>';
    var success = '<%= request.getAttribute("success") %>';

    if (error !== null) {
        alert(error); // 显示错误提示框
    } else if (success !== null && success !== '' && success !== 'null') {
        alert(success); // 显示成功提示框
    }
</script>

</head>
	<body>
	<!-- 设置session值 -->
	<%
		String type=request.getParameter("registeruser");
		session.setAttribute("registeruser", type);
	%>
	<!-- 0为买家 -->
		<c:if test="${sessionScope.registeruser eq 1}">
			<ul class="daohang" >
				<div class="title-container">
				    <h2 class="registration-title">请您注册！</h2>
				    <a href="index.jsp">返回初始页面</a>
				</div>
			</ul>
			<div class="login" style="top:50%;">
				<h2>请您注册卖家账户！</h2>
				<form action="registerservlet?type=1" method="post" required="required">
					<!-- 账号框 -->
					<div class="input">
						<h3>账号</h3>
						<input type="text" name="newusername" required="required">
					</div>
					<!-- 密码框 -->
					<div class="input">
						<h3>密码</h3>
						<input type="password" name="newpwd" required="required">
					</div>
					<div class="input">
						<h3>设置密保问题</h3>
						<input type="text" name="newquestion" required="required">
					</div>
					<div class="input">
						<h3>设置答案</h3>
						<input type="text" name="newanswer" required="required"><br><br>
					</div>
					<!-- 登录注册根据action和value在同一个servlet中检测 -->
					<div class="image-buttons">
					    <input class="input-image-button" type="image" src="img/register.png" alt="登录" title="登录" name="action" value="登录"><br>
					    <br>
					</div>
				</form>
			</div>
			
		</c:if>
		<c:if test="${sessionScope.registeruser eq 0}">
			<ul class="daohang" >
				<div class="title-container">
				    <h2 class="registration-title">请您注册！</h2>
				    <a href="index.jsp">返回初始页面</a>
				</div>
			</ul>
			<div class="login" >
				<h2>请您注册买家账户！</h2>
				<form action="registerservlet?type=0" method="post" required="required">
					<!-- 账号框 -->
					<div class="input">
						<h3>账号</h3>
						<input type="text" name="newusername" required="required">
					</div>
					<!-- 密码框 -->
					<div class="input">
						<h3>密码</h3>
						<input type="password" name="newpwd" required="required">
					</div>
					<!-- 电话框 -->
					<div class="input">
						<h3>联系电话</h3>
						<input type="text" name="newphone" required="required">
					</div>
					<!-- 地址框 -->
					<div class="input">
						<h3>默认收货地址</h3>
						<input type="text" name="newaddress" required="required">
					</div>
					<div class="input">
						<h3>设置密保问题</h3>
						<input type="text" name="newquestion" required="required">
					</div>
					<div class="input">
						<h3>设置答案</h3>
						<input type="text" name="newanswer" required="required"><br><br>
					</div>
					<!-- 登录注册根据action和value在同一个servlet中检测 -->
					<div class="image-buttons">
					    <input class="input-image-button" type="image" src="img/register.png" alt="登录" title="登录" name="action" value="登录"><br>
					    <br>
					</div>
				</form>
			</div>
		</c:if>
	
	</body>
</html>