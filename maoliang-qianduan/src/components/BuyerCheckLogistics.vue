<template>
    <body style="margin: 0px;">
    <div v-if="isLoggedIn">
        <div class="left">
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
                        <h3 @click="navigateTo('BuyerPay')" class="head4-1" style="cursor: pointer;">我的订单</h3>
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
                        <h3 @click="navigateTo('BuyerShowCat')" class="head4-1" style="cursor: pointer;">查看我的猫咪信息</h3>
                    </td>
                </tr>
                <tr>
                    <td class="head4">
                        <h3 @click="navigateTo('BuyerUploadCat')" class="head4-1" style="cursor: pointer;">添加我的猫咪信息</h3>
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
        <div class="right">
            <div class="centered-container">
                <h2>查看物流</h2>
            </div>
            <div class="table-container">
                <table class="logistics-table">
                    <thead>
                    <tr>
                        <th>商品名称</th>
                        <th>地址</th>
                        <th>时间</th>
                        <th>状态</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>美味干猫粮</td>
                        <td>浙江下沙宝龙广场17幢菜鸟驿站</td>
                        <td>2024-05-14 13:53:47</td>
                        <td>已签收</td>
                    </tr>
                    <tr>
                        <td>美味干猫粮</td>
                        <td>浙江下沙宝龙广场17幢菜鸟驿站</td>
                        <td>2024-05-12 09:31:21</td>
                        <td>待取件</td>
                    </tr>
                    <tr>
                        <td>美味干猫粮</td>
                        <td>浙江省上虞转运中心</td>
                        <td>2024-05-11 18:04:22</td>
                        <td>派送中</td>
                    </tr>
                    <tr>
                        <td>美味干猫粮</td>
                        <td>浙江省绍兴市诸暨市</td>
                        <td>2024-05-11 10:21:37</td>
                        <td>已揽件</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <div v-else>
        <!-- 用户未登录时显示的内容 -->
        <div class="else">
            <div class="left">
                <!-- 页面头部 -->
                <table class="daohang">
                    <img class="head1" src="~@/assets/img/buyer/head.png" alt="">
                    <tr>
                        <td class="head2">游客</td>
                    </tr>
                    <tr>
                        <td class="head4">
                            <h3 class="head4-1">其他功能后登录使用</h3>
                        </td>
                    </tr>
                    <tr>
                        <td class="head5">
                            <button @click="handleLogout" class="head5-1" style="cursor: pointer;">返回登录</button>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
    </body>
</template>

<script>
import axios from 'axios';
export default {
    name: "BuyerCheckLogistics",
    data() {
        return {
            currentUser: null
        };
    },
    created() {
        this.fetchUsrFromSession();
    },
    computed: {
        isLoggedIn() {
            return !!this.currentUser;
        },
        getUsername() {
            return this.currentUser ? this.currentUser.username : '未登录';
        }
    },
    methods: {
        async fetchUsrFromSession() {
            try {
                const response = await axios.get('/now-usr');
                const usr = response.data;
                this.currentUser = usr;
                return true;
            } catch (error) {
                console.error('获取用户数据错误:', error);
                return false;
            }
        },
        navigateTo(routeName) {
            this.$router.push({ name: routeName });
        },
        async handleLogout() {
            try {
                await axios.get('/logout-control');
                this.$router.push('/');
            } catch (error) {
                console.error('登出失败:', error);
            }
        }
    }

};
</script>

<style scoped>
.centered-container {
    display: flex;
    justify-content: center;
    align-items: center;
}

body {
    display: block;
    background-color: #FFF9F1 !important;
    background-image: none;
    margin: 0;
}

.left {
    width: 287px;
    height: 100vh;
    background-color: rgba(61, 61, 61, 0.33);
    position: relative;
    float: left;
    align-content: center;
}

.head1 {
    background-color: rgba(61, 61, 61, 0);
    position: relative;
    top: 30px;
    left: 38px;
    z-index: 1;
}

.daohang {
    background-color: rgba(0, 0, 0, 0);
    width: 200px;
    position: relative;
    left: 30px;
}

.head2 {
    background-color: rgba(61, 61, 61, 0.33);
    text-align: center;
    vertical-align: top;
    font-size: 36px;
    color: white;
    height: 100px;
    position: relative;
    z-index: 2;
}

.head4 {
    background-color: rgba(61, 61, 61, 0.33);
    text-align: center;
    height: 100px;
}

.head4-1 {
    background-color: rgba(61, 61, 61, 0);
    text-decoration: none;
    color: #ffffff;
    font-size: 28px;
    font-weight: bold;
}

.head5 {
    background-color: rgba(61, 61, 61, 0.33);
    text-align: center;
    height: 100px;
    bottom: 0;
}

.head5-1 {
    background-color: rgba(61, 61, 61, 0);
    text-decoration: none;
    color: #585655;
    font-size: 28px;
    font-weight: bold;
    border: none;
}
/* 右侧内容 */
.right {
    height: 100vh;
    position: absolute;
    left: 350px;
    float: right;
}

.table-container {
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 20px;
}

.logistics-table {
    width: 100%;
    border-collapse: collapse;
    border: 2px solid #BBBBBB;
}

.logistics-table th, .logistics-table td {
    border: 2px solid #BBBBBB;
    padding: 10px;
    text-align: left;
}

.logistics-table th {
    background-color: #82aaf6;
    font-size: 16px;
    font-weight: bold;
}

.logistics-table td {
    background-color: #ffffff;
    font-size: 14px;
}
</style>