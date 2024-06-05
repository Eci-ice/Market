<template>
    <div>
        <!-- 页面头部 卖家 售后处理 -->
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
                        <th>售后ID</th>
                        <th>购买人ID</th>
                        <th>商品ID</th>
                        <th>title</th>
                        <th>描述</th>
                        <th>图片</th>
                        <th>处理结果</th>
                    </tr>
                    <tr v-for="(order, index) in paginatedItems" :key="index">
                        <!--paginatedItems这个连接有问题，是后端的吗？-->
                        <td>{{ order.aftersaleid }}</td>
                        <td>{{ order.goodid }}</td>
                        <td>{{ order.goodname }}</td>
                        <td>{{ order.title}}</td>
                        <td>{{ order.description }}</td>
                        <td><!--图-->
                            <div class="media-container">
                                <div v-for="(media, index) in good.mediaFiles" :key="index" v-show="media.isActive">
                                    <img v-if="!isVideo(media)" :src="media.url" alt="商品图片" v-show="media.isActive">
                                    <video v-if="isVideo(media)" :src="media.url" controls v-show="media.isActive"></video>
                                </div>
                            </div>
                            <div>
                                <button @click="showPrevMedia(good)">＜</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <button @click="showNextMedia(good)">＞</button>
                            </div>
                        </td>

                        <td>
                            <button v-if="order.orderstate === 1"
                                    @click="confirmOrder(order.orderid)"
                                    class="confirm-btn">确认订单</button>
                            <button v-else-if="order.orderstate === 2"
                                    @click="confirmOrder(order.orderid)"
                                    class="stock-btn">确认备货</button>
                            <button v-else-if="order.orderstate === 3"
                                    @click="confirmOrder(order.orderid)"
                                    class="shipment-btn">确认发货</button>
                            <button v-else-if="order.orderstate === 4"
                                    class="delivery-btn">发货完成</button>
                            <button v-if=" order.orderstate <= 4 && order.orderstate > 0" @click="cancelOrder(order.orderid)" class="red-btn">取消订单</button>
                            <span v-if="order.orderstate < 0 || order.orderstate > 4">无法操作订单</span>
                        </td>
                        <td>
                            {{ getOrderStatus(order.orderstate) }}
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</template>

<script>
import axios from "axios";

export default {
    name: "AfterSaleTreatment",

    methods: {
        async fetchUsrFromSession() {
            try {
                // 发起 GET 请求到后端接口
                const response = await axios.get('/now-usr');
                // 解析响应数据
                const usr = response.data;
                // 更新组件的 currentUser 数据
                this.currentUser = usr;

                return true;
            } catch (error) {
                console.error('获取用户数据错误:', error);
                return false;
            }
        },
        async fetchgoodListSession() {
            try {
                // 发起 GET 请求获取商品列表
                const goodsResponse = await axios.get('/good/seller-all-historygood-list-control');
                // 解析响应数据
                // console.log('goodList:', goodsResponse);
                const goodList = goodsResponse.data.data;//goodsResponse的数据的data属性
                // 将商品列表添加到 products 中
                // 解析picture属性并添加mediaFiles属性
                //  console.log('goodList:', goodList);

                this.goods = goodList.map(good => {
                    //    console.log('Before trimming:', good.picture); // 添加调试语句
                    const trimmedPicture = good.picture.trim();
                    //     console.log('After trimming:', trimmedPicture); // 添加调试语句
                    const paths = trimmedPicture.split(',');
                    const mediaFiles = paths.map((path, i) => {
                        return {
                            url: path,
                            isActive: i === 0 // 默认第一个是true，其他是false
                        };
                    });
                    return {
                        ...good,
                        mediaFiles,
                        // 保留原始属性
                        goodname: good.goodname.trim(),
                        description: good.description.trim(),
                        price: good.price,
                        number: good.number,
                        kind: good.kind,
                        subkind: good.subkind,
                        createdate: good.createdate
                    };
                });
                console.log('this.goods:', this.goods);
                return true;
            } catch (error) {
                console.error('获取商品列表数据错误:', error);
                return false;
            }
        },
        showPrevMedia(good) {
            const currentIndex = good.mediaFiles.findIndex(media => media.isActive);
            console.log('Current active index:', currentIndex);

            if (currentIndex >= 0) {
                const prevIndex = (currentIndex - 1 + good.mediaFiles.length) % good.mediaFiles.length;
                console.log('Previous index:', prevIndex);
                this.setActiveMedia(good, prevIndex);
            }
        },
        showNextMedia(good) {
            const currentIndex = good.mediaFiles.findIndex(media => media.isActive);
            console.log('Current active index:', currentIndex);

            if (currentIndex >= 0) {
                const nextIndex = (currentIndex + 1) % good.mediaFiles.length;
                console.log('Next index:', nextIndex);
                this.setActiveMedia(good, nextIndex);
            }
        },
        isVideo(media) {
            const isMediaVideo = media.url.endsWith('.mp4');
            console.log(`Is media a video: ${isMediaVideo}`);
            return isMediaVideo;
        },



    }

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
