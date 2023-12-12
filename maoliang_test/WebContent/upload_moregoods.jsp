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
            <input type="file" name="picture" onchange="uploadImage(this)">
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
                    <th>商品类别</th>
                    <th>子类别</th>
                </tr>
            </thead>
        </table>
         <form id="productForm" action="createmoregoodservlet" enctype="multipart/form-data" method="post">
          	<input type="hidden" id="productListInput" name="productList">
        	
         </form>
         <button onclick="submitProducts()">确认发布所有商品</button>
    </div>

    <script>
        var productList = [];
        let uploadedFilenames = [];  // 用于保存上传的文件名
        
        function uploadImage(input) {
            if (input.files && input.files[0]) {
                var formData = new FormData();
                formData.append('file', input.files[0]);

                fetch('uploadimageservlet', {
                    method: 'POST',
                    body: formData
                })
                .then(function(response) {
                    return response.text();
                })
                .then(function(filename) {
                    // 将文件名添加到全局数组中
                    uploadedFilenames.push('./img/' + filename);
                })
                .catch(function(error) {
                    console.error(error);
                });
            }
        }


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
        var kind = document.querySelector("select[name='kind']").value;
        var subkind = document.querySelector("select[name='subkind']").value;

            
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
        
        

            var product = {
                goodname: goodname,
                price: price,
                description: description,
                picture: uploadedFilenames.pop(),
                kind: kind,
                subkind: subkind
            };


            productList.push(product);
            displayProducts();

            document.querySelector("input[name='goodname']").value = "";
            document.querySelector("input[name='price']").value = "";
            document.querySelector("input[name='description']").value = "";
            document.querySelector("input[name='picture']").value = "";

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

                cell1.innerHTML = product.goodname;
                cell2.innerHTML = product.price;
                cell3.innerHTML = product.description;
           
                var img = document.createElement("img");
                img.src = product.picture;  // 直接使用product.picture作为图片的URL
                img.width = 100;
                img.height = 100;
                img.style.objectFit = "contain";
                cell4.appendChild(img);
                
                cell5.innerHTML = product.kind; 
                cell6.innerHTML = product.subkind; 
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

        // Call updateSubcategories initially to set up the initial state
        updateSubcategories();
    </script>
</body>
</html>
