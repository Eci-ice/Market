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
#preview {
    /* 设置预览窗口的大小和样式 */
    width: 200px;
    height: 200px;
    border: 1px solid #ccc;
    position: relative;
    overflow: hidden;
}

#preview img {
    /* 设置图片的大小和位置 */
    width: 100%;
    height: 100%;
    position: absolute;
    top: 0;
    left: 0;
}

#preview button {
    /* 设置删除按钮的样式和位置 */
    position: absolute;
    top: 0;
    right: 0;
    background: rgba(255, 255, 255, 0.5);
    border: none;

}

</style>
<script>

var allFiles = []; // 全局的文件数组
function validateForm() {
    var price = document.getElementsByName("price")[0].value;
    var number = document.getElementsByName("number")[0].value;
    var goodname = document.getElementsByName("goodname")[0].value;
    var description = document.getElementsByName("description")[0].value;
    var kind = document.getElementsByName("kind")[0].value;
    var subkind = document.getElementsByName("subkind")[0].value;
    var pictureInput = document.getElementsByName("picture")[0].value;
    var fileExtension = pictureInput.split('.').pop().toLowerCase();


    if (isNaN(price)) {
        alert("价格需要输入数字！");
        return false;
    }
    if (isNaN(number)) {
        alert("库存需要输入数字！");
        return false;
    }

    if (goodname.length > 20) {
        alert("商品名称不能超过20个字符！");
        return false;
    }

    if (description.length > 100) {
        alert("商品描述不能超过100个字符！");
        return false;
    }

    // 验证所有文件的扩展名
    for (var i = 0; i < allFiles.length; i++) {
        var fileExtension = allFiles[i].name.split('.').pop().toLowerCase();
        if(fileExtension != "png" && fileExtension != "jpg" && fileExtension != "mp4") {
            alert("图片只能上传png,jpg或mp4格式！");
            return false;
        }
    }
    
    var formData = new FormData();
    formData.append('price', price);
    formData.append('number', number);
    formData.append('goodname', goodname);
    formData.append('description', description);
    formData.append('kind', kind);
    formData.append('subkind', subkind);
    console.log(allFiles.length);
    console.log("aaa");
    for (var i = 0; i < allFiles.length; i++) {
        formData.append('picture[]', allFiles[i]);
    }
    
    // 创建一个新的XMLHttpRequest对象
    var xhr = new XMLHttpRequest();

    // 设置请求完成后的处理函数
    xhr.onload = function() {
        if (xhr.status === 200) {
            // 请求成功，处理响应
            console.log('请求成功');
        } else {
            // 请求失败，处理错误
            console.error('请求失败，状态码：' + xhr.status);
        }
    };

    // 设置请求错误时的处理函数
    xhr.onerror = function() {
        console.error('发生网络错误');
    };

    // 打开并发送请求
    xhr.open('POST', 'creategoodservlet', true);
    xhr.send(formData);
    return true;
}

var imageIndex = 0;

function previewFiles() {
    var files   = document.querySelector('input[type=file]').files;

    // 将新选择的文件添加到全局的文件数组中
    for (var i = 0; i < files.length; i++) {
        allFiles.push(files[i]);
    }
    var preview = document.querySelector('#preview');

    function readAndPreview(file) {
        // 确保 `file.name` 符合我们的扩展名要求
        if ( /\.(jpe?g|png|mp4)$/i.test(file.name) ) {
            var reader = new FileReader();

            reader.addEventListener("load", function () {
                var container = document.createElement("div");
                container.className = "slide"; // 添加类名

                var media;
                if (file.type === "video/mp4") {
                    media = document.createElement("video");
                    media.controls = true;
                } else {
                    media = document.createElement("img");
                }
                media.src = this.result;
                media.style.width = "100%";
                media.style.height = "100%";
                media.style.objectFit = "contain";//适应屏幕
                container.appendChild(media);
                
                imageIndex = preview.children.length;

                // 添加删除按钮
                var removeButton = document.createElement("button");
                removeButton.innerHTML = "X";
                removeButton.addEventListener("click", function(e) {
                    e.target.parentNode.remove();
                    imageIndex = (imageIndex - 1 + preview.children.length) % preview.children.length;
                    updatePreview();//还原
                    updateUploadLabel();
                });
                container.appendChild(removeButton);

                preview.appendChild(container);

                // 更新预览窗口
                updatePreview();
                
                updateUploadLabel();
            }, false);

            reader.readAsDataURL(file);
        }
    }

    if (files) {
        [].forEach.call(files, readAndPreview);
    }
}

function updateUploadLabel() {
    var uploadLabel = document.querySelector('#customUploadButton');
    var fileInput = document.querySelector('#fileInput');
    var preview = document.querySelector('#preview');
    console.log(preview.children.length);
    if (preview.children.length >= 3) { // 每个文件有一个子节点（图片），所以这里检查子节点数量是否大于或等于3
        uploadLabel.innerHTML = "最多上传3个文件";
        uploadLabel.style.color = "red";
        fileInput.disabled = true; // 禁用上传按钮
    } else if (preview.children.length > 0) {
        uploadLabel.innerHTML = "继续上传";
        uploadLabel.style.color = "black";
        fileInput.disabled = false; // 启用上传按钮
    } else {
        uploadLabel.innerHTML = "上传图片/视频";
        uploadLabel.style.color = "black";
        fileInput.disabled = false; // 启用上传按钮
    }
}


function prevImage() {
    var preview = document.querySelector('#preview');
    if (preview.hasChildNodes()) {
    	imageIndex = (imageIndex - 1 + preview.children.length) % preview.children.length;
        updatePreview();
    }
}

function nextImage() {
    var preview = document.querySelector('#preview');
    if (preview.hasChildNodes()) {
    	imageIndex = (imageIndex + 1) % preview.children.length;
        updatePreview();
    }
}

function updatePreview() {
    var preview = document.querySelector('#preview');
    for (var i = 0; i < preview.children.length; i++) {
        if (i == imageIndex) {
            preview.children[i].style.display = "block";
        } else {
            preview.children[i].style.display = "none";
        }
    }
}


</script>

<c:if test="${not empty sessionScope.admin }">
<div class="main">
<h1>请发布商品</h1>
<form action="creategoodservlet" method="post" enctype="multipart/form-data" onsubmit="return validateForm()">
    <div class="container">
        <div class="left-div" style="height: 300px;">
		    <!-- 左侧div -->
		    <!-- 顶部是一个返回按钮 -->
		    <button type="button" onclick="window.history.back()">返回</button>
		    <!-- 中间是一个固定尺寸的预览窗口，用于显示用户上传的图片或视频，最多三个,其中每个图片都支持右上角显示x删除 -->
		    
			<div id="preview">
			    <!-- 预览窗口 -->
			</div>
			<button type="button" id="prevButton" onclick="prevImage()">＜</button>
			<button type="button" id="nextButton" onclick="nextImage()">＞</button>

		    <!-- 底部是一个上传按钮，在上传图片为空的时候时它显示的是“上传图片/视频”，在有内容的时候为“继续上传”-->
		    <input type="file" id="fileInput" name="picture" accept="image/png, image/jpeg, video/mp4" onchange="previewFiles()" multiple style="display: none;" required="required">

			<!-- 添加自定义的上传按钮 -->
			<button type="button" id="customUploadButton" onclick="document.getElementById('fileInput').click()">上传图片/视频</button>
		</div>


        <div class="right-div">
            <!-- 右侧div -->
            <center>
                <span>商品大类：</span>
                <select name="kind" id="kind" required="required" onchange="updateSubcategories()">
                    <option value="猫咪主粮">猫咪主粮</option>
                    <option value="猫咪零食">猫咪零食</option>
                    <option value="猫咪日用">猫咪日用</option>
                </select><br><br>
            </center>
            <center>
                <span>商品子类：</span>
                <select name="subkind" id="subkind" required="required">
                    <!-- 放置在下方script部分 -->
                </select><br><br>
            </center>
            <center>
                <span>商品名称：</span>
                <input type="text" name="goodname" required="required" placeholder="请输入商品名称" ><br><br>
            <span id="goodnameError" style="color: red;"></span>
            </center>
            <center>
                <span>商品价格：</span>
                <input type="text" name="price" required="required" placeholder="请输入商品价格"><br><br>
            <span id="priceError" style="color: red;"></span>
            </center>
            <center>
                <span>商品库存：</span>
                <input type="text" name="number" required="required" placeholder="请输入商品库存" /><br><br>
            <span id="numberError" style="color: red;"></span>
            </center>
            <center>
                <span>商品描述：</span>
                <input type="text" name="description" required="required" placeholder="请输入商品描述"><br><br>
            <span id="descriptionError" style="color: red;"></span>
            </center>
            <input type="submit" class="submit-button-container" value="确认发布">
        </div>
    </div>
</form>
    <script>
    function updateSubcategories() {
        var categorySelect = document.getElementById("kind");
        var subcategorySelect = document.getElementById("subkind");
        subcategorySelect.innerHTML = ""; // Clear existing options

        if (categorySelect.value === "猫咪主粮") {
            addOption(subcategorySelect, "猫干粮");
            addOption(subcategorySelect, "猫湿粮");
        } else if (categorySelect.value === "猫咪零食") {
            addOption(subcategorySelect, "饼干");
            addOption(subcategorySelect, "罐头");
            addOption(subcategorySelect, "猫条");
        } else if (categorySelect.value === "猫咪日用") {
            addOption(subcategorySelect, "猫砂盆");
            addOption(subcategorySelect, "猫小窝");
            addOption(subcategorySelect, "猫沙发");
            addOption(subcategorySelect, "清洁除味");
        }
    }
    function addOption(selectElement, optionValue) {
        var option = document.createElement("option");
        option.value = optionValue;
        option.text = optionValue;
        selectElement.add(option);
    }

        // Call updateSubcategories initially to set up the initial state
        updateSubcategories();
    </script>


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

</body>
</html>