<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>发布商品</title>
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
#a{

    width:100%;
    height:200px;
    text-align:center;
}
#b{
    text-align:center;
    color: red;
    font-size: 22px;
    font-family:KaiTi;
}
a{
	text-decoration:none;
}
body {
	background-image:url(img/beijing2.jpg);
	font-family: KaiTi;
	background-size: cover; 
	margin:0px;
	padding:0px;
}

h2{
	text-align:center;
	font-family:KaiTi;
}

.main{
    justify-content: space-between;
    max-width: 500px;
    margin: 50px auto;
    border: 2px solid #00BFFF;
    border-radius: 15px; 
    background-color: white;
    padding: 30px;
    -moz-border-radius: 5px;
    -webkit-border-radius: 5px;
    background: #C0C0C0;
    }
.main h1{
	font-size: 24px;
	font-family:KaiTi;
	text-align:center;
    padding: 8px 0px 16px 10px;
    color:black;
    border-bottom: 30px;
    }
.text1{
     margin-left: 3px;
     }
.main label>span{
     width: 30%;
     display: inline-block;
     text-align: left;
     padding-right: 10px;
     margin-bottom: 10px;
     }
.main input[type="text"],
.main input[type="file"]{
     width: 65%;  /* 调整宽度 */
     padding: 10px;  /* 增加内边距 */
     border: 1px solid #DCDCDC;
     border-radius: 5px; 
}

.main .button
.main button{
    width: 65%;  /* 调整宽度 */
        padding: 10px;  /* 增加内边距 */
        border: 1px solid #DCDCDC;
        border-radius: 5px; 
}
.main button {
    background-color: #FFD700;  /* 黄色按钮 */
}
.main .button:hover{
     color:  #333;
     background-color:  #EBEBEB ;
}
button{
     padding: 8px 24px 8px 24px;
     margin-top: 1px;
     margin-bottom: 8px;
     border: none;
     border-radius: 4px;
     -moz-border-radius: 4px;
     -webkit-border-radius: 4px;
     font-weight: bold;
     text-shadow: 1px 1px 1px #FFE477;
     box-shadow: 1px 1px 1px  #3D3D3D;
     -moz-box-shadow: 1px 1px 1px  #3D3D3D;
     -webkit-box-shadow: 1px 1px 1px  #3D3D3D;
     color: #585858;
     background: #f6ff0a;
      cursor: pointer
}
.container {
    display: flex; /* 使用flex布局 */
}
.left-div, .right-div {
    flex: 1; /* 让两个div均分宽度 */
    padding: 20px; /* 添加一些内边距 */
}
#pictureInput {
    bottom: 0px;
    left: 0%;
    border: none;
}

#imagePreview {
    width: 100%; /* 设置图片宽度为100% */
    object-fit: contain; /* 保证图片始终在边框内且保持其原始的宽高比 */
    display: block;  
    position: relative;
    opacity: 0;
}
.left-div center {
    width: 240px; /* 您可以根据需要调整此值 */
    height: 220px;
    background-color: none;
    position: relative;
    display: flex;
    align-items: center;
    justify-content: center;
}
.submit-button-container {
    margin-bottom: 200px;  /* adjust this value to achieve desired positioning */
    margin-left: 70px;
    padding: 8px 24px 8px 24px;
     margin-bottom: 8px;
     border: none;
     border-radius: 4px;
     -moz-border-radius: 4px;
     -webkit-border-radius: 4px;
     font-weight: bold;
     text-shadow: 1px 1px 1px #FFE477;
     box-shadow: 1px 1px 1px  #3D3D3D;
     -moz-box-shadow: 1px 1px 1px  #3D3D3D;
     -webkit-box-shadow: 1px 1px 1px  #3D3D3D;
     color: #585858;
     background-color: rgb(255, 215, 0);
      cursor: pointer">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
}
</style>
<script>
function showPreview(input) {
    // 获取文件输入框
    var fileInput = input;

    // 检查是否有选择文件
    if (fileInput.files && fileInput.files[0]) {
        // 获取文件信息
        var file = fileInput.files[0];

        // 检查文件类型
        var fileType = file.type;
        if (fileType !== 'image/jpeg' && fileType !== 'image/png') {
            alert('只能上传 JPG 或 PNG 格式的图片');
            // 清空文件输入框的值，防止用户上传不支持的文件类型
            fileInput.value = '';
            return;
        }

        // 检查文件大小，限制为 1MB
        var maxSize = 10*1024 * 1024; // 1MB
        if (file.size > maxSize) {
            alert('图片文件太大，请选择小于 10MB 的图片');
            // 清空文件输入框的值，防止用户上传过大的文件
            fileInput.value = '';
            return;
        }

        // 创建 FileReader 对象
        var reader = new FileReader();

        // 设置文件加载完成的回调函数
        reader.onload = function (e) {
            // 获取预览图片元素
            var imagePreview = document.getElementById('imagePreview');

            // 设置预览图片的源
            imagePreview.src = e.target.result;

            // 设置图片不透明度
            imagePreview.style.opacity = '1';
        };

        // 读取文件内容
        reader.readAsDataURL(file);
    }
}


</script>

<c:if test="${not empty sessionScope.admin }">
<div class="main">
 <h1>请发布商品</h1>
    <form name="myForm" action="creategoodservlet" method="post" onsubmit="return validateForm()">
        <div class="container">
        <div class="left-div" style="height: 300px;"> <!-- 左侧div -->
        	<span style="padding-left: 50px;">&nbsp;&nbsp;商品图片：</span><br><br>
            <center><img id="imagePreview"><br></center>
            <input type="file" name="picture" id="pictureInput" onchange="showPreview(this);">
            <button><a href="show_good.jsp">取消发布</a></button></center>
        </div>

        <div class="right-div"> <!-- 右侧div -->
            <center>
            	<span>商品名称：</span>
            	<input type="text" name="goodname" required="required" placeholder="请输入商品名称">
            	<br><br>
            	 <span id="goodnameError" style="color: red;"></span><br> <!-- 错误信息显示 -->
            	</center>
            <center><span>商品价格：</span><input type="text" name="price" required="required" placeholder="请输入商品价格"><br><br>
            <span id="priceError" style="color: red;"></span>
            </center>
            <center><span>商品库存：</span><input type="text" name="number" required="required" placeholder="1" /><br><br>
            </center>
            <center><span>商品描述：</span><input type="text" name="description" required="required" placeholder="请输入商品描述"><br><br>
            </center>
            <input type="submit" class="submit-button-container" value="确认发布" >
        </div>
    </div>
</form>
</div>
<!-- <script>
	var isDuplicate = ${isDuplicate}; // 获取Java代码中设置的标志值
	if (isDuplicate) {
	    alert("名称错误"); // 弹出提示框
	}
</script> -->

<div id="b">
<c:if test="${not empty requestScope.err }">
<br>err:${requestScope.err}
</div>

</c:if>
</c:if>
<c:if test="${empty sessionScope.admin }">
<div class="else">
您还未登录，请先<a href="index.jsp">登录</a>
</div>
</c:if>


<script>
function validateForm() {
    var goodname = document.forms["myForm"]["goodname"].value;
    var price = document.forms["myForm"]["price"].value;

    var chineseRegex = /^[\u4e00-\u9fa5]+$/; // 中文字符正则表达式
    var numberRegex = /^\d+(\.\d{1,2})?$/; // 数字正则表达式，最多两位小数

    if (!chineseRegex.test(goodname)) {
        document.getElementById('goodnameError').innerHTML = "商品名称只能是中文";
        return false;
    } else {
        document.getElementById('goodnameError').innerHTML = "";
    }
    if (!numberRegex.test(price) || price.length > 10) {
        document.getElementById('priceError').innerHTML = "商品价格必须是数字且不超过十位数";
        return false;
    } else {
        document.getElementById('priceError').innerHTML = "";
    }

    return true;
}
</script>




</body>
</html>