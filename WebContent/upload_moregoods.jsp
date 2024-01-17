<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>上传多个商品</title>
    <style>
        .container {
            max-width: 600px;
            margin: 0 auto;
            padding: 20px;
            background-color: #f5f5f5;
            border: 1px solid #ddd;
            border-radius: 5px;
        }
        h1 {
            color: #333;
            text-align: center;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        input[type="text"],
        input[type="file"] {
            width: 50%;
            padding: 10px;
            margin: 5px 0;
            border: 1px solid #ccc;
            border-radius: 3px;
        }
        button {
            background-color: #007BFF;
            color: #fff;
            border: none;
            padding: 10px 20px;
            border-radius: 3px;
            cursor: pointer;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        
        th, td {
            padding: 10px;
            text-align: left;
            border: 1px solid #ddd;//添加边距
        }
        
        th {
            background-color: #007BFF;
            color: #fff;
        }
        img {
            max-width: 100px;
            max-height: 100px;
             object-fit: contain;
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
   
</head>
<body>
    <div class="container">
        <h1>上传多个商品</h1>
        
            <label>商品名称:</label>
            <input type="text" name="goodname">
            <span id="goodnameError" style="color: red;"></span>
            <br>
            <label>商品价格:</label>
            <input type="text" name="price">
            <span id="priceError" style="color: red;"></span>
            <br>
            <label>商品数量:</label>
            <input type="text" name="number">
            <span id="numberError" style="color: red;"></span>
            <br>
            <label>商品描述:</label>
            <input type="text" name="description">
            <span id="descriptionError" style="color: red;"></span>
            <label>商品大类：</label>
            <select name="kind" id="kind" required="required" onchange="updateSubcategories()">
                <option value="猫咪主粮">猫咪主粮</option>
                <option value="猫咪零食">猫咪零食</option>
                <option value="猫咪日用">猫咪日用</option>
            </select><br><br>
            <label>商品子类：</label>
            <select name="subkind" id="subkind" required="required">
                <!-- 放置在下方script部分 -->
            </select><br><br>
            <label>商品图片:</label>
            <div id="preview">
			    <!-- 预览窗口 -->
			</div>

		    <!-- 底部是一个上传按钮，在上传图片为空的时候时它显示的是“上传图片/视频”，在有内容的时候为“继续上传”-->
		   <input type="file" id="fileInput" name="picture" accept="image/png, image/jpeg, video/mp4" onchange="previewFiles()" style="display: none;" required="required">

			<!-- 添加自定义的上传按钮 -->
			<button type="button" id="customUploadButton" onclick="document.getElementById('fileInput').click()">上传图片/视频</button>
            <span id="pictureError" style="color: red;"></span>
            <br>
            <button type="button" onclick="addProduct()">添加商品</button>
        
        <table id="productTable">
            <thead>
                <tr>
                    <th>商品名称</th>
                    <th>价格</th>
                    <th>数量</th>
                    <th>描述</th>
                    <th>展示内容</th>
                    <th>商品类别</th>
                    <th>子类别</th>
                </tr>
            </thead>
        </table>
         <form id="productForm" action="createmoregoodservlet"  enctype="multipart/form-data" method="post">
          	<input type="hidden" id="productListInput" name="productList">
         </form>
         <button onclick="submitProducts()">确认发布所有商品</button>
    </div>

    <script>
        var productList = [];

        var allFiles = []; // 全局的文件数组
        function isDuplicateProductName(name) {
            for (var i = 0; i < productList.length; i++) {
                if (productList[i].goodname === name) {
                    return true;
                }
            }
            return false;
        }
        function validateProductName(input) {
            var validCharactersRegex = /^[a-zA-Z0-9\u4e00-\u9fff\s]+$/; // Chinese, English, numbers, and space regex
            return input.trim() !== "" && validCharactersRegex.test(input);
        }
        //function validateChineseCharacters(input) {
        //    var chineseRegex = /^[\u4E00-\u9FFF]+$/; // 中文字符的正则表达式
         //   return chineseRegex.test(input);
       // }
        function validatePrice(input) {
            var priceRegex = /^\d{1,10}$/; // 1到10位数字的正则表达式
            return priceRegex.test(input);
        }
        function validateNumber(input) {
            var numberRegex = /^\d{1,10}$/; // 1到10位数字的正则表达式
            return numberRegex.test(input);
        }
        function validateDescription(input) {
            return input.trim().length > 0; // 检查输入的描述是否非空
        }
        function validateImageType(file) {
            // 获取文件的扩展名并转换为小写
            var extension = file.name.split('.').pop().toLowerCase();
            return extension === 'jpg' || extension === 'jpeg' || extension === 'png'|| extension === 'mp4';
        }
        function validateImageSize(file) {
            // 10MB的字节数
            var maxSize = 10 * 1024 * 1024;
            return file.size <= maxSize;
        }
        
        
        function addProduct() {
            //var goodname = document.querySelector("input[name='goodname']").value;
           // var price = document.querySelector("input[name='price']").value;
            //var description = document.querySelector("input[name='description']").value;
            //var pictureFile = document.querySelector("input[name='picture']").files[0];
            //var pictureUrl = URL.createObjectURL(pictureFile);
            var goodname = document.querySelector("input[name='goodname']").value;
        var price = document.querySelector("input[name='price']").value;
        var number = document.querySelector("input[name='number']").value;
        var description = document.querySelector("input[name='description']").value;
        
        var pictureFile = document.querySelector("input[name='picture']").files[0];
        var kind = document.querySelector("select[name='kind']").value;
        var subkind = document.querySelector("select[name='subkind']").value;

            
        if (!validateProductName(goodname)) {
            document.getElementById("goodnameError").innerHTML = "商品名称只能包含中文、英文、数字和空格。";
            return;
        } else {
            document.getElementById("goodnameError").innerHTML = "";
        }
        if (!validatePrice(price)) {
            document.getElementById("priceError").innerHTML = "商品价格必须为数字且不超过十位数。";
            return;
        } else {
            document.getElementById("priceError").innerHTML = "";
        }
        if (!validateNumber(number)) {
            document.getElementById("numberError").innerHTML = "商品数量必须为数字且不超过十位数。";
            return;
        } else {
            document.getElementById("numberError").innerHTML = "";
        }
        if (!validateDescription(description)) {
            document.getElementById("descriptionError").innerHTML = "商品描述不能为空。";
            return;
        } else {
            document.getElementById("descriptionError").innerHTML = "";
        }
        if (!pictureFile) {
            document.getElementById("pictureError").innerHTML = "请选择要上传的图片。";
            return;
        } else {
            document.getElementById("pictureError").innerHTML = "";
        }
        if (!validateImageType(pictureFile)) {
            document.getElementById("pictureError").innerHTML = "照片只能为jpg,png或mp4格式。";
            return;
        } else {
            document.getElementById("pictureError").innerHTML = "";
        }
        if (!validateImageSize(pictureFile)) {
            document.getElementById("pictureError").innerHTML = "照片大小不得超过10MB。";
            return;
        } else {
            document.getElementById("pictureError").innerHTML = "";
        }
        if (isDuplicateProductName(goodname)) {
            document.getElementById("goodnameError").innerHTML = "商品名称不能重复。";
            return;
        } else {
            document.getElementById("goodnameError").innerHTML = "";
        }
        
        
        
            var pictureUrl = URL.createObjectURL(pictureFile);
            var product = {
                goodname: goodname,
                price: price,
                description: description,
                number: number,
                mediaFile: pictureFile,  // 将当前的文件添加到产品的数据中
                kind: kind,
                subkind: subkind
            };

            productList.push(product);
            displayProducts();

            document.querySelector("input[name='goodname']").value = "";
            document.querySelector("input[name='price']").value = "";
            document.querySelector("input[name='number']").value = "";
            document.querySelector("input[name='description']").value = "";
            document.querySelector("input[name='picture']").value = "";
            document.getElementById('preview').innerHTML = '';
        }
        
			/* 将待添加的内容显示在表格中 */
	    function displayProducts() {
	    var table = document.getElementById("productTable");
	
	    while (table.rows.length > 1) {
	        table.deleteRow(1);
	    }
	
	    for (var i = 0; i < productList.length; i++) {
	        var product = productList[i];
	        var row = table.insertRow(-1);
	
	        var cell1 = row.insertCell(0);
	        var cell2 = row.insertCell(1);
	        var cell3 = row.insertCell(2);
	        var cell4 = row.insertCell(3);
	        var cell5 = row.insertCell(4);
	        var cell6 = row.insertCell(5);
	        var cell7 = row.insertCell(6);
	
	        cell1.innerHTML = product.goodname;
	        cell2.innerHTML = product.price;
	        cell3.innerHTML = product.number;
	        cell4.innerHTML = product.description;
	
	        var mediaContainer = document.createElement('div');
	
	        var file = product.mediaFile;  // 获取当前产品的文件
	        var media;
	        if (file.type === "video/mp4") {
	            media = document.createElement("video");
	            media.controls = true;
	        } else {
	            media = document.createElement("img");
	        }
	        media.src = URL.createObjectURL(file);
	        media.width = 100;
	        media.height = 100;
	        media.style.objectFit = "contain";
	        mediaContainer.appendChild(media);  // 将媒体元素添加到容器中
	
	        cell5.appendChild(mediaContainer);
	
	        cell6.innerHTML = product.kind;
	        cell7.innerHTML = product.subkind;
	    }
	}



        
	    function submitProducts() {
	        if (productList.length === 0) {
	            alert("请先添加商品信息。");
	            return false;
	        }

	        var formData = new FormData();
	        for (var i = 0; i < productList.length; i++) {
	            var product = productList[i];
	            formData.append('goodname[]', product.goodname);
	            formData.append('price[]', product.price);
	            formData.append('description[]', product.description);
	            formData.append('number[]', product.number);
	            formData.append('mediaFile[]', product.mediaFile);
	            formData.append('kind[]', product.kind);
	            formData.append('subkind[]', product.subkind);
	        }
	        console.log(formData);
	        var xhr = new XMLHttpRequest();

	        xhr.onload = function() {
	            if (xhr.status === 200) {
	                try {
	                    var response = JSON.parse(xhr.responseText);
	                    if (response.isUnique) {
	                        window.location.href = 'show_goods.jsp';
	                    } else {
	                        window.location.href = 'error.jsp';
	                    }
	                } catch (e) {
	                    console.error('解析错误，返回的内容：' + xhr.responseText);
	                }
	            } else {
	                console.error('请求失败，状态码：' + xhr.status);
	            }
	        };


	        xhr.onerror = function() {
	            console.error('发生网络错误');
	        };

	        xhr.open('POST', 'createmoregoodservlet', true);
	        xhr.send(formData);

	        event.preventDefault();
	        return true;
	    }


        function updateSubcategories() {
            var categorySelect = document.getElementById("kind");
            var subcategorySelect = document.getElementById("subkind");
            subcategorySelect.innerHTML = ""; // Clear existing options

            if (categorySelect.value === "猫咪主粮") {
                addOption(subcategorySelect, "猫干粮");
                addOption(subcategorySelect, "猫湿粮");
                subcategorySelect.selectedIndex = 0;
            } else if (categorySelect.value === "猫咪零食") {
                addOption(subcategorySelect, "饼干");
                addOption(subcategorySelect, "罐头");
                addOption(subcategorySelect, "猫条");
                subcategorySelect.selectedIndex = 0;
            } else if (categorySelect.value === "猫咪日用") {
                addOption(subcategorySelect, "猫砂盆");
                addOption(subcategorySelect, "猫小窝");
                addOption(subcategorySelect, "猫沙发");
                addOption(subcategorySelect, "清洁除味");
                subcategorySelect.selectedIndex = 0;
            }
        }
        function addOption(selectElement, optionValue) {
            var option = document.createElement("option");
            option.value = optionValue;
            option.text = optionValue;
            selectElement.add(option);
        }

        var imageIndex = 0;


        function previewFiles() {
            var file  = document.querySelector("input[name='picture']").files[0];
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
                        removeButton.className = "close";
                        removeButton.addEventListener("click", function(e) {
                            e.target.parentNode.remove();
                            imageIndex = (imageIndex - 1 + preview.children.length) % preview.children.length;
                            updatePreview();//还原
                            updateUploadLabel();
                        });
                        container.appendChild(removeButton);

                        preview.appendChild(container);

                    }, false);

                    reader.readAsDataURL(file);
                }
            }

            if (file) {
                readAndPreview(file);
            }
        }
        
        function updateUploadLabel() {
            var uploadLabel = document.querySelector('#customUploadButton');
            var fileInput = document.querySelector('#fileInput');
            var preview = document.querySelector('#preview');
            console.log(preview.children.length);
            if (preview.children.length > 0) {
                uploadLabel.innerHTML = "已上传文件";
                uploadLabel.style.color = "red";
                fileInput.disabled = true; // 禁用上传按钮
            } else {
                uploadLabel.innerHTML = "上传图片/视频";
                uploadLabel.style.color = "black";
                fileInput.disabled = false; // 启用上传按钮
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

        // Call updateSubcategories initially to set up the initial state
        updateSubcategories();
    </script>
</body>
</html>