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
                        <h3 @click="navigateTo('MyCatInformationCheck')" class="black-text" style="cursor: pointer;">查看我的猫咪信息</h3>
                        <h3 @click="navigateTo('MyCatInformationAdd')" class="black-text" style="cursor: pointer;">添加我的猫咪信息</h3>
                        <!-- 其他导航项 -->
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--右侧显示栏-->
    <div class="right" style="width:100%;">
        <a href="/#/buyer">
            <img src="~@/assets/img/buyer/arrow.png" alt="" width="40" title="返回商品界面">
        </a>
        <div class="picture-k">
            <div class="media-container" v-for="(mediaFile, index) in selectedProduct.mediaFiles" :key="index"  v-show="mediaFile.isActive">
                <img v-if="!mediaFile.isVideo" :src="mediaFile.url" alt="商品图片" class="product-image" style="width: 415.8px;
        height: 504.9px;">

                <div class="media-navigation">
                    <button class="media-navigation-button prev-button" @click="">＜</button>
                    <button class="media-navigation-button next-button" @click="">＞</button>
                </div>
            </div>

        </div>
        <table class="good-2">
            <tr><td class="goodname">姓名：</td>诺诺</tr>
            <tr><td class="price">年龄：</td>1岁</tr>
            <tr><td class="goodname">性别：</td>妹妹</tr>
            <tr><td class="price">体重：</td>2.5kg</tr>
            <tr><td class="goodname">绝育：</td>是</tr>
            <tr><td class="goodname">疫苗：</td>一针</tr>
            <tr><td class="goodname">品种：</td>中华田园猫</tr>
            <tr><td class="goodname">性格</td>活泼好动</tr>
        </table>
        <div class="media-navigation">
            <button class="media-navigation-button next-button" @click="">＜</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <button class="media-navigation-button next-button" @click="">＞</button>
        </div>

    </div>



</template>

<script>
export default {
    name: "MyCatInformationCheck"
}
</script>

<style scoped>
.else{
    position:absolute;
    top:1040%;
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
.content-right {
    width: 81%;
    height: 600px;
    background-color:rgba(255,250,250,0.5);float: left;
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
/* 商品 */
.right{
    /* 商品显示
    width: 1340px; */
    height: 100vh;
    /* background-color: aquamarine; */
    position: absolute;/*绝对定位*/
    left: 350px;

    float: right;
}
.picture-k {/*图像*/
    width: 462px;
    height: 561px;
    background-repeat: no-repeat;
    background-image: url("~@/assets/img/buyer/Square.png");
    position: relative;
    justify-content: center;
    left: 100px;
    top: 10px;
    background-size: 100% 100%;
    border-style: none;
}
.media-container {
    position: relative;
    width: 100px;
    height: 100px;
    overflow: hidden;
}
.product-image{
    width: 100%;
    height: 100%;
    object-fit: cover; /* 或者 'contain' 根据您的需求 */
}
.media-navigation-button {
    position: absolute; /* 绝对定位相对于它的父元素 .media-container */
    top: 50%; /* 垂直居中 */
    transform: translateY(-50%); /* Y轴方向上向上移动50%的自己的高度，实现完全居中对齐 */
    background: #85C46EB3; /* 透明背景 */
    border: none; /* 无边框 */
    cursor: pointer; /* 手形光标 */
    width: 30px;
    height: 50px;
}
.goodname {
    height: 150px;
    font-size: 55px;
}

.price {
    font-size: 24px;
    width: 300px;
    height: 100px;
}

</style>
