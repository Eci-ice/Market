<%--
  Created by IntelliJ IDEA.
  User: banana
  Date: 2023/10/22
  Time: 22:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>买家填写购买信息</title>
<!--     <link rel="stylesheet" type="text/css" href="BFillInfo.css"/> -->
</head>
<style>
*{
    background-color: #FFF9F1;

}
/* .goodname{
    text-decoration: none;
    color: rgba(0, 0, 0,1);
    font-size: 60px;
    font-family: "微软雅黑";
}
.price,.state{color: #000;font-size: 30px;} */
.cline{
    /* 竖线 */
    position: absolute;
    top: 30px;
    left: 810px;
}
.good-1{
    position: absolute;
    top: 100px;
    left: 100px;
    border-style: none;
    /* background-color: rgba(121, 115, 115, 0.5); */
    width: 700px;/*格子宽度*/
    height: 600px;/*格子高度*/
}
.goodname{
    height: 100px;/*格子高度*/
    font-size:60px;
}
.price{
    font-size:24px;
    width: 200px;
    height: 50px;
}
.description{
	font-size: 30px;
}
.butt-1{
    /* 取消购买按钮 */
    position: relative;
    top: 100px ;
    left: 200px;
    background-color: orange;
    font-size: 30px;
}
button {
            padding: 5px 10px;
            height:60px;
            margin: 5px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
  }

.buy-imf{
    /* 右侧表单 */
    position: absolute;
    top: 140px;
    left: 890px;
    font-size: 36px;

}

input[type="text"]{
    width: 200px;
    appearance: none;
    /* border: none; */
    border-color: #cccccc;
    outline: none;
    background-color: #FFFFFF;
    padding: 20px;
    font-size: 20px;
    color: #888888;

}
.butt-2{
    position: relative;
    top: 160px;
    left: 150px;
    background-color: #EBC16B;
    color: #fff;
    border: none;
    border-radius: 20px;/*设置了圆角和内边距 */
    padding:25px 55px;
    font-size: 24px;
    cursor: pointer;
    text-decoration: none;
    /* 最后使用cursor属性将光标变成手型，增强按钮的交互性。 */
}
/*.buy-btn{*/
/*    position: relative;*/
/*    !* top: 5px; *!*/
/*    top: 160px;*/
/*    left: 150px;*/
/*    background-color: #EBC16B;*/
/*    color: #fff;*/
/*    border: none;*/
/*    border-radius: 20px;!*设置了圆角和内边距 *!*/
/*    padding:25px 55px;*/
/*    font-size: 30px;*/
/*    cursor: pointer;*/
/*}*/

</style>
<script >
	
	function validateForm() {
	    var buyername = document.getElementsByName("buyername")[0].value;
	    var telephone = document.getElementsByName("telephone")[0].value;
	    var address = document.getElementsByName("address")[0].value;
	    var phoneRegex = /^[0-9]{11}$/;

	    if (!phoneRegex.test(telephone)) {
	        alert("电话号码需要是11位数字！");
	        return false;
	    }

	    if (buyername.length > 10) {
	        alert("用户名不能超过10个字符！");
	        return false;
	    }
	
	    if (address.length > 99) {
	        alert("地址不能超过99个字符！");
	        return false;
	    }
	
	
	    return true;
	}
	
</script>
<body>
<hr class="cline" color=#BBBBBB width=1 size=900>
<div class="left"><!-- 商品介绍 -->
	<!-- 返回shop界面按钮 -->
	    <a href="BuyerShop.jsp"><img src="./img/buyer/arrow.png" alt="" width="40" title="返回商品界面"> </a>
       <table class="good-2"><!-- 商品介绍 -->
        <tr><td colspan="2" class="goodname">
           		 ${nowg.goodname}
        </td></tr>
        <tr>
            <td colspan="2" class="description"><br/>
            ${nowg.description }
            </td>
        </tr>
        <tr>
            <td class="price">总价：</td>
            <td class="price">${nowg.price}</td>
        </tr>
        <tr>
            <td class="price">剩余库存量：</td>
            <td class="price">${nowg.number}</td>
        </tr>
    </table>
    <!-- <a href="BuyerMain.jsp" class="butt-1"><img src="./img/buyer/debuy-button.png" alt="" width="228px" title="不买"></a> -->
      <a href="BuyerMain.jsp" alt="" title="购买"><button class="butt-2">取消购买</button></a> 

</div>

<div class="right">
    <form action="createorderservlet" method="post" name="myform" class="buy-imf" onsubmit="return validateForm()">
		<input type="hidden" name="goodid" value="${nowg.goodid}"/><br/>
        <label class="username">
                   姓名：<input type="text" name="buyername" value="${sessionScope.admin.username}" required="required" ><br><br>
	        电话：<input type="text" name="telephone" value="${sessionScope.admin.phone}" required="required" ><br><br>
	        交易地点：<input type="text" name="address" value="${sessionScope.admin.address}" required="required" ><br><br>
        </label>
       <input type="submit"  class="butt-2" value="确认购买" >
    </form>

</div>
</body>
</html>
