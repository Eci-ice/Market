<template>
  <!-- 页面头部 BuyerAfterSale 买家 我的售后-->
    <ul class="daohang">
        <li><b>欢迎进入"喵咪美食坊"!</b></li>
        <li style="float:right" @click="handleLogout" class="logout-button">退出登录</li>
    </ul>
  <!-- 左侧导航 -->
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
          <td class="head4">
            <h3 @click="navigateTo('BuyerAfterSale')" class="head4-1" style="cursor: pointer;">我的售后</h3>
          </td>
        </tr>
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
            <table>
                <tr>
                    <th>售后ID</th>
                    <th>购买人ID</th>
                    <th>商品ID</th>
                    <th>title</th>
                    <th>描述</th>
                    <th>图片</th>
                    <th>服务结果</th>
                </tr>
                <tr v-for="afterSale in afterSales" :key="index">
                    <td>{{ afterSale.aftersaleid }}</td>
                    <td>{{ afterSale.userid }}</td>
                    <td>{{ afterSale.goodid }}</td>
                    <td>{{ afterSale.title }}</td>
                    <td>{{ afterSale.description }}</td>
                    <td>
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
                        <button v-if="order.orderstate === 4" class="green-btn"
                                @click="handleOrderAction(order.orderid, 'confirmCompletion')"
                                style="background-color: transparent; color: #f44336; border: 2px solid #f44336; padding: 5px 10px; border-radius: 4px; cursor: pointer;">申请售后</button>
                        <button v-if="order.orderstate >= 0 && order.orderstate < 4" class="red-btn"
                                @click="handleOrderAction(order.orderid, 'cancel')"
                                style="background-color: transparent; color: #f44336; border: 2px solid #f44336; padding: 5px 10px; border-radius: 4px; cursor: pointer;">撤销售后</button>
                        <span v-if="order.orderstate > 4 || order.orderstate === -1">无法操作订单</span>
                    </td>

                    <td>
                        <button @click="submitResult(afterSale.id)">提交</button>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</template>

<script>
import axios from "axios";

export default {
    name: "BuyerAfterSale",
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

                this.goods = goodList.map( good => {
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
        goToPrevPage() {
            // 实现翻页逻辑
            if (this.currentPage > 1) {
                this.currentPage--;
            }
        },
        goToNextPage() {
            // 实现翻页逻辑
            if (this.currentPage < this.totalPages) {
                this.currentPage++;
            }
        },
        // 每次搜索结果变化后重置当前页码
        resetPage() {
            this.currentPage = 1;
        },
        fetchProducts() {
            // 从后端获取产品列表
        }
    },
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
.centered-container {
    display: flex;
    justify-content: center;
    align-items: center;
}
.container {
    font-family: Arial, sans-serif;
    width: 80%;
    margin: 2% auto;
    border: 1px solid #ccc;
    padding: 20px;
    box-shadow: 0 0 10px rgba(0,0,0,0.1);
}

table {
    width: 100%;
    border-collapse: collapse;
    margin-bottom: 20px;
}

th, td {
    border: 1px solid #ccc;
    padding: 10px;

    text-align: left;
}

th {
    background-color: #f2f2f2;
}

img {
    width: 50px;
    height: auto;
}

.pagination {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-bottom: 20px;
}

.history-btn, .prev, .next {
    padding: 10px 20px;
    color: #fff;
    border: none;
    cursor: pointer;
    margin: 0 5px;
}

.history-btn {
    background-color: rgb(237, 137, 108);
    border-radius: 8px;
}

.prev, .next {
    background-color: rgb(237, 196, 110);
}

.history-btn:hover, .prev:hover, .next:hover {
    background-color: #d32f2f;
}

.history-btn a {
    text-decoration: none;
    color: white;
}

.history-btn a:hover {
    text-decoration: none;
}
.left-btn-container {
    margin-right: auto;
    display: flex;
    align-items: center;
}
form {
    display: flex; /* 让表单内的元素在同一行显示 */
    width:600px;
    height:45px;
}

input[type="text"] {
    flex-grow: 1; /* 让搜索框占据剩余的空间 */
}
input[name="keyword"] {
    width: 100%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 4px;
}
#search_list {
    position: fixed;
    top: 140px;
    left: 12%;
    width: 70%;
    background-color: white;
}


#search_list div {
    border-bottom: 1px solid black;
}

.media-container {
    position: relative;
    width: 100px;
    height: 100px;
    overflow: hidden;
}
.media-container img, .media-container video {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    object-fit: contain;
}
.media-container img.active, .media-container video.active {
    display: block;
}
.media-container button {
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    background: rgba(255, 255, 255, 0.7);
}
.media-container .prev-button {
    left: 10px;
}
.media-container .next-button {
    right: 10px;
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