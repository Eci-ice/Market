<template>
  <!-- 页面头部 买家 售后申请处理与查看的页面 -->
    <div class="left" >
        <!-- 页面头部 -->
        <table class="daohang">
            <img class="head1" src="~@/assets/img/buyer/head.png" alt="">
            <tr>
                <td class="head2">{{ getUsername }}</td>
            </tr>
            <tr>
                <td class="head4">
                    <h3 @click="navigateTo('BuyerCart')" class="head4-1" style="cursor: pointer;">我的购物车</h3>
                </td>
            </tr>
            <tr>
                <td class="head4">
                    <h3 @click="navigateTo('BuyerLikes')" class="head4-1" style="cursor: pointer;">我的收藏</h3>
                </td>
            </tr>
            <tr>
                <td class="head4">
                    <h3 @click="navigateTo('buyerHistory')" class="head4-1" style="cursor: pointer;">历史购买记录</h3>
                </td>
            </tr>
            <tr>
                <td class="head4">
                    <h3 @click="navigateTo('BuyerShowRecommend')" class="head4-1" style="cursor: pointer;">展示推荐商品</h3>
                </td>
            </tr>
            <tr>
                <td class="head4">
                    <h3 @click="navigateTo('BuyerAfterSale')" class="head4-1" style="cursor: pointer;">我的售后</h3>
                </td>
            </tr> <!-- 跳转 买家 售后申请处理与查看的页面 -->
            <tr>
                <td class="head4">
                    <h3 @click="navigateTo('BuyerMain')" class="head4-1" style="cursor: pointer;">返回主页</h3>
                </td>
            </tr>
            <tr>
                <td class="head5">
                    <button @click="handleLogout" class="head5-1" style="cursor: pointer;">退出登录</button>
                </td>
            </tr>
        </table>
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
*{
    background-color: #FFF9F1;

}
body {
    display: block;
    background-color: #FFF9F1 !important;
    background-image: none;
    height: 1000px;
}
.left{
    /* 买家导航 */
    width: 287px;
    height:100vh;
    background-color: rgba(61, 61, 61, 0.33);
    position: relative;
    float: left;
    align-content: center;
}
.head1{
    background-color: rgba(61, 61, 61, 0);
    position: relative;
    top: 30px;
    left: 38px;
    z-index: 1;
}
.daohang{
    background-color: rgba(0, 0, 0, 0);
    width: 200px;/*格子宽度*/
    position: relative;
    left: 30px;
}
.head2{
    background-color: rgba(61, 61, 61, 0.33);
    text-align: center;
    vertical-align: top;
    font-size:36px;
    color: white;
    height: 100px;/*格子高度*/
    position: relative;
    z-index: 2;
}
.head4{
    background-color: rgba(61, 61, 61, 0.33);
    text-align: center;
    height: 100px;/*格子高度*/
}
.head4-1{
    background-color: rgba(61, 61, 61, 0);
    text-decoration: none;
    color: #ffffff;
    font-size:28px;
    font-weight: bold;
}
.head5{
    background-color: rgba(61, 61, 61, 0.33);
    text-align: center;
    height: 100px;/*格子高度*/
    bottom: 0;

}
.head5-1{
    background-color: rgba(61, 61, 61, 0);
    text-decoration: none;
    color: #585655;
    font-size:28px;
    font-weight: bold;
    border: none;
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