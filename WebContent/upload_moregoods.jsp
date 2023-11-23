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
            <label>商品描述:</label>
            <input type="text" name="description">
            <span id="descriptionError" style="color: red;"></span>
            <br>
            <label>商品图片:</label>
            <input type="file" name="picture">
             <span id="pictureError" style="color: red;"></span>
            <br>
            <button type="button" onclick="addProduct()">添加商品</button>
        
        <table id="productTable">
            <thead>
                <tr>
                    <th>商品名称</th>
                    <th>价格</th>
                    <th>描述</th>
                    <th>图片</th>
                </tr>
            </thead>
        </table>
         <form id="productForm" action="createmoregoodservlet" method="post">
          	<input type="hidden" id="productListInput" name="productList">
        	
         </form>
         <button onclick="submitProducts()">确认发布所有商品</button>
    </div>

    <script>
        var productList = [];
        function isDuplicateProductName(name) {
            for (var i = 0; i < productList.length; i++) {
                if (productList[i].goodname === name) {
                    return true;
                }
            }
            return false;
        }
        
        function validateChineseCharacters(input) {
            var chineseRegex = /^[\u4E00-\u9FFF]+$/; // 中文字符的正则表达式
            return chineseRegex.test(input);
        }

        function validatePrice(input) {
            var priceRegex = /^\d{1,10}$/; // 1到10位数字的正则表达式
            return priceRegex.test(input);
        }

        function validateDescription(input) {
            return input.trim().length > 0; // 检查输入的描述是否非空
        }
        function validateImageType(file) {
            // 获取文件的扩展名并转换为小写
            var extension = file.name.split('.').pop().toLowerCase();
            return extension === 'jpg' || extension === 'jpeg' || extension === 'png';
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
        var description = document.querySelector("input[name='description']").value;
        var pictureFile = document.querySelector("input[name='picture']").files[0];
            
        if (!validateChineseCharacters(goodname)) {
            document.getElementById("goodnameError").innerHTML = "商品名称只能包含中文字符。";
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
            document.getElementById("pictureError").innerHTML = "照片只能为jpg或png格式。";
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
                picture: pictureUrl
            };

            productList.push(product);
            displayProducts();

            document.querySelector("input[name='goodname']").value = "";
            document.querySelector("input[name='price']").value = "";
            document.querySelector("input[name='description']").value = "";
            document.querySelector("input[name='picture']").value = "";
        }

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

                cell1.innerHTML = product.goodname;
                cell2.innerHTML = product.price;
                cell3.innerHTML = product.description;

                var img = document.createElement("img");
                img.src = product.picture;  // 直接使用product.picture作为图片的URL
                img.width = 100;
                img.height = 100;
                img.style.objectFit = "contain";
                cell4.appendChild(img);
            }
        }

        
        function submitProducts() {
        	 if (productList.length === 0) {
                 alert("请先添加商品信息。");
                 return;
             }
            var productListInput = document.getElementById("productListInput");
            productListInput.value = JSON.stringify(productList);
            console.log(JSON.stringify(productList));
         // 手动提交表单
            var form = document.getElementById("productForm");
            form.submit();
        }
    </script>
</body>
</html>
