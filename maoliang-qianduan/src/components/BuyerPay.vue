<template>
  <!-- 页面头部 -->
    <ul class="daohang">
        <li><b>欢迎进入"喵咪美食坊"!</b></li>
        <li style="float:right" @click="handleLogout" class="logout-button">退出登录</li>
    </ul>
  <!-- 左侧导航 -->
    <div class="content">
        <div class="content-left">
            <!--菜单栏导航-->
            <div class="nav">
                <!--每一个菜单栏项-->
                <div class="nav-menu">
                    <!--主标题-->
                    <div class="nav-info">
                        <div class="user-image" style="left: -40px;">
                            <img src="@/assets/img/catbackground.png" alt="用户头像" style="width: 50px;height: 50px;left: 8.9%;position: absolute;"/>
                        </div>
                        <div class="username" style="margin-right: 100px;">{{ getUsername }}</div>
                        <img src="~@/assets/img/line.png" alt="分割线" class="separator-line"><!-- 新增图片 -->
                    </div>
                    <!--子标题-->
                    <div class="">
                        <!-- 导航链接 -->
                        <h3 @click="navigateTo('BuyerCart')" class="black-text" style="cursor: pointer;">我的购物车</h3>
                        <h3 @click="navigateTo('BuyerLikes')" class="black-text" style="cursor: pointer;">我的收藏</h3>
                        <h3 @click="navigateTo('buyerHistory')" class="black-text" style="cursor: pointer;">历史购买记录</h3>
                        <h3 @click="navigateTo('BuyerShowCat')" class="black-text" style="cursor: pointer;">查看我的猫咪信息</h3>
                        <h3 @click="navigateTo('BuyerUploadCat')" class="black-text" style="cursor: pointer;">添加我的猫咪信息</h3>
                        <h3 @click="navigateTo('BuyerPay')" class="black-text" style="cursor: pointer;">我的订单</h3><!-- 本页新增 -->
                        <!-- 其他导航项 -->
                    </div>
                </div>
            </div>
        </div>
        <!-- 右侧内容区域 -->
        <div class="right" style="width: 75%;">
            <div class="container" style="border: 1px solid blue; padding: 20px;">
                <div style="text-align: center;">
                    <h2>请选择支付方式</h2>
                </div>
                <div style="margin-bottom: 20px;">
                    <label for="orderNumber">订单编号:</label>
                    <input type="text" id="orderNumber" placeholder="请输入订单编号" style="margin-left: 10px;">
                </div>
                <div style="margin-bottom: 20px;">
                    <label for="orderPrice">订单价格:</label>
                    <input type="text" id="orderPrice" placeholder="请输入订单价格" style="margin-left: 10px;">
                </div>
                <div style="display: flex; justify-content: center;">
                    <button style="margin-right: 10px;">银行支付</button>
                    <button style="margin-right: 10px;">信用卡支付</button>
                    <button>第三方支付</button>
                </div>
            </div>
        </div>




    </div>

</template>

<script>
import axios from "axios";
export default {
    name: "BuyerPay"
}
</script>

<style scoped>
.daohang{
    background-color: rgba(0, 0, 0, 0);
    width: 200px;/*格子宽度*/
    position: relative;
    left: 30px;
}
.content {
    width: 100%;
    height: 100%;
}
.content-left {
    width: 19%;
    height:850px;
    background-color: rgba(61, 61, 61, 0.33);
    float: left;
}

.nav {
    /*上下5,左右0*/
    margin: 5px 0;
}

.nav-info {
    color: #000000;
    display: flex;        /* 新增：使用flex布局 */
    align-items: center;  /* 新增：使内容垂直居中 */
    flex-direction: column;
}
.black-text {
    color: white;
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
</style>
<style scoped>
/* 右侧内容区域样式 */
.right {
    /* 商品显示 */
    height: 100vh;
    /* background-color: aquamarine; */
    position: absolute;
    /* 绝对定位 */
    left: 350px;
    float: right;
}

.container {
    border: 1px solid blue;
    padding: 20px;
}

.container h2 {
    text-align: center;
    margin-bottom: 20px;
}

.container label {
    font-weight: bold;
}

.container input[type="text"] {
    width: calc(100% - 100px);
    padding: 10px;
    margin-left: 10px;
    border: 1px solid #ccc;
    border-radius: 4px;
}

.container button {
    padding: 10px 20px;
    margin-right: 10px;
    cursor: pointer;
    border: none;
    background-color: #007bff;
    color: #fff;
    border-radius: 4px;
}

.container button:last-child {
    margin-right: 0;
}
</style>
