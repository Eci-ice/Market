<template>
    <div>
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
                            <h3 @click="navigateTo('UpdatePassword')" class="black-text" style="cursor: pointer;">修改密码</h3>
                            <h3 @click="navigateTo('UploadOneGood')" class="black-text" style="cursor: pointer;">发布商品</h3>
                            <h3 @click="navigateTo('ShowUserInfo')" class="black-text" style="cursor: pointer;">查看意向订单</h3>
                            <h3 @click="navigateTo('UploadMultipleGoods')" class="black-text" style="cursor: pointer;">发布多个商品</h3>
                            <h3 @click="navigateTo('ShowHistoryGoods')" class="black-text" style="cursor: pointer;">查看历史商品</h3>
                            <h3 @click="navigateTo('ShowGoods')" class="black-text" style="cursor: pointer;">查看全部商品</h3>
                            <h3 @click="navigateTo('ShowAllUsers')" class="black-text" style="cursor: pointer;">查看客户信息</h3>

                            <h3 @click="navigateTo('AfterSalesTreatment')" class="black-text" style="cursor: pointer;">售后处理</h3>
                            <!-- 新增售后处理-->
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 右侧内容区域 -->
        <div class="right">
            <div class="container">
                <div class="centered-container">
                    <h2>售后处理</h2>
                </div>
                <table>
                    <tr>
                        <th>商品名称</th>
                        <th>物流状态</th>
                        <th>客户售后</th>
                        <th>结果提交</th>
                    </tr>
                    <tr v-for="afterSale in afterSales" :key="afterSale.id">
                        <td>{{ afterSale.productName }}</td>
                        <td>已发货</td>
                        <td>
                            <button v-if="!afterSale.afterSaleApplied" class="red-btn" @click="applyForAfterSale(afterSale.id)">无</button>
                            <button v-else class="green-btn" @click="navigateTo('BuyerAfterSaleDetails')">查看</button>
                        </td>
                        <td>
                            <button @click="submitResult(afterSale.id)">提交</button>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</template>

<script>
export default {

};
</script>

<style scoped>
/* 页面头部样式 */
.daohang {
    list-style-type: none;
    background-color: #696969;
    padding: 10px;
    margin: 0;
    overflow: hidden;
}

.daohang li {
    float: left;
}

.daohang li a {
    display: block;
    color: white;
    text-align: center;
    padding: 14px 16px;
    text-decoration: none;
}

.daohang li a:hover {
    background-color: #474747;
}

.logout-button {
    cursor: pointer;
    color: white;
    text-decoration: none;
}

.logout-button:hover {
    background-color: #474747;
}

/* 左侧导航样式 */
.content {
    display: flex;
}

.content-left {
    width: 19%;
    height: 100%;
    background-color: #9A9A9A;
}

.nav-menu {
    padding: 10px;
}

.nav-info {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
}

.user-image {
    width: 50px;
    height: 50px;
    border-radius: 50%;
}

.username {
    font-size: 14px;
    color: #000;
}

.separator-line {
    width: 100%;
    margin: 10px 0;
}

.black-text {
    color: white;
}

.nav-content h3 {
    margin-bottom: 20px;
}

/* 右侧内容区域样式 */
.right {
    width: 75%;
    height: 100%;
}

.container {
    border: 1px solid blue;
    padding: 20px;
}

.container h2 {
    text-align: center;
    margin-bottom: 20px;
}

.container button {
    padding: 10px 20px;
    margin-right: 10px;
    cursor: pointer;
    border: none;
    border-radius: 4px;
}

.container button:last-child {
    margin-right: 0;
}

/* 商品信息表格样式 */
table {
    border-collapse: collapse;
    width: 100%;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

th, td {
    border: solid 1px #efefef;
    padding: 10px;
}

th {
    background-color: #efefef;
}

tr:nth-child(even) {
    background-color: #f2f2f2;
}

/* 按钮样式 */
.red-btn {
    background-color: red;
    color: white;
}

.green-btn {
    background-color: green;
    color: white;
}

/* 商品信息表格样式 */
table {
    border-collapse: collapse;
    width: 100%;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

th, td {
    border: solid 1px #efefef;
    padding: 10px;
}

th {
    background-color: #efefef;
}

tr:nth-child(even) {
    background-color: #f2f2f2;
}


</style>
