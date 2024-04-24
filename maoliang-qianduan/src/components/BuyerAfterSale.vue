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
                        <h3 @click="navigateTo('BuyerAfterSale')" class="black-text" style="cursor: pointer;">我的售后</h3><!-- 本页新增 -->
                        <!-- 其他导航项 -->
                    </div>
                </div>
            </div>
        </div>
    </div>
  <!-- 右侧内容区域 -->
    <div class="right" style="width: 75%;">
        <div class="container">
            <router-link :to="{ name: 'BuyerMain' }">
                返回
            </router-link>
            <div class="centered-container">
                <h2>我的售后</h2>
            </div>
            <table style="border-collapse: collapse; width: 100%; box-shadow: 0 2px 4px rgba(0,0,0,0.1);" border="1"
                   align="center" cellpadding="10">
                <tr>
                    <th>商品名称</th>
                    <th>物流状态</th>
                    <th>申请售后</th>
                    <th>售后结果</th>
                </tr>
                <tr v-for="afterSale in afterSales" :key="afterSale.id">
                    <td>{{ afterSale.productName }}</td>
                    <td>{{ afterSale.logisticsStatus }}</td>
                    <td>
                        <button :class="{ 'green-btn': afterSale.afterSaleApplied, 'red-btn': !afterSale.afterSaleApplied }"
                                @click="applyForAfterSale(afterSale.id)">
                            {{ afterSale.afterSaleApplied ? '已申请' : '申请' }}
                        </button>
                    </td>
                    <td>
                        <button :class="{ 'green-btn': afterSale.afterSaleResult === '和解', 'red-btn': afterSale.afterSaleResult === '退货退款' }"
                                @click="handleAfterSaleResult(afterSale.id)">
                            {{ afterSale.afterSaleResult }}
                        </button>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</template>

<script>
export default {
    name: "BuyerAfterSale"
}
</script>

<style scoped>
/* 左+导航侧内容区域样式 */
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