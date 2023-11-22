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
            <br>
            <label>商品价格:</label>
            <input type="text" name="price">
            <br>
            <label>商品描述:</label>
            <input type="text" name="description">
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
            <input type="file" name="picture">
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
         <form id="productForm" action="createmoregoodservlet" method="post">
          	<input type="hidden" id="productListInput" name="productList">
        	
         </form>
         <button onclick="submitProducts()">确认发布所有商品</button>
    </div>

    <script>
        var productList = [];

        function addProduct() {
            var goodname = document.querySelector("input[name='goodname']").value;
            var price = document.querySelector("input[name='price']").value;
            var description = document.querySelector("input[name='description']").value;
            var pictureFile = document.querySelector("input[name='picture']").files[0];
            var pictureUrl = "";
            var kind = document.querySelector("select[name='kind']").value;
            var subkind = document.querySelector("select[name='subkind']").value;
            if (pictureFile) {
                pictureUrl = URL.createObjectURL(pictureFile);
            }
            var product = {
                goodname: goodname,
                price: price,
                description: description,
                picture: pictureUrl,
                kind: kind,
                subkind: subkind
            };
            var fileExtension = pictureFile ? pictureFile.name.split('.').pop().toLowerCase() : "";

    	    if (isNaN(price)) {
    	        alert("价格需要输入数字！");
    	        return;
    	    }

    	    if (goodname.length > 20) {
    	        alert("商品名称不能超过20个字符！");
    	        return;
    	    }

    	    if (description.length > 100) {
    	        alert("商品描述不能超过100个字符！");
    	        return;
    	    }

    	    if(fileExtension != "png" && fileExtension != "jpg") {
    	        alert("图片只能上传png或jpg格式！");
    	        return;
    	    }


            productList.push(product);
            displayProducts();

            document.querySelector("input[name='goodname']").value = "";
            document.querySelector("input[name='price']").value = "";
            document.querySelector("input[name='description']").value = "";
            document.querySelector("input[name='picture']").value = "";
            document.querySelector("select[name='kind']").value = "";
            document.querySelector("select[name='subkind']").value = "";
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
            var productListInput = document.getElementById("productListInput");
            productListInput.value = JSON.stringify(productList);
            console.log(JSON.stringify(productList));
            
         // 手动提交表单
            var form = document.getElementById("productForm");
            form.submit();
        }
    </script>
    <!-- 这一段script实现二级管理的下拉框功能 -->
    <script>
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
