<template>
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
            <div class="nav-content">
              <!-- 导航链接 -->
              <h3 @click="navigateTo('UpdatePassword')" class="black-text" style="cursor: pointer;">修改密码</h3>
              <h3 @click="navigateTo('UploadOneGood')" class="black-text" style="cursor: pointer;">发布商品</h3>
              <h3 @click="navigateTo('ShowUserInfo')" class="black-text" style="cursor: pointer;">查看意向订单</h3>
              <h3 @click="navigateTo('UploadMultipleGoods')" class="black-text" style="cursor: pointer;">发布多个商品</h3>
              <h3 @click="navigateTo('ShowHistoryGoods')" class="black-text" style="cursor: pointer;">查看历史商品</h3>
              <h3 @click="navigateTo('ShowGoods')" class="black-text" style="cursor: pointer;">查看全部商品</h3>
              <h3 @click="navigateTo('ShowAllUsers')" class="black-text" style="cursor: pointer;">查看客户信息</h3>
              <h3 @click="navigateTo('AfterSalesTreatment')" class="black-text" style="cursor: pointer;">售后处理</h3>
              <h3 @click="navigateTo('ManageLogistics')" class="black-text" style="cursor: pointer;">物流管理</h3>
              <!-- 其他导航项 -->
            </div>
          </div>
        </div>
      </div>

        <!--右侧方框--->
        <div class="content-right">
            <div class="centered-container">
                <h2>物流管理</h2>
            </div>
            <div class="table-container">
                <table class="logistics-table">
                    <thead>
                    <tr>
                        <th>物流ID</th>
                        <th>商品ID</th>
                        <th>状态</th>
                        <th>地址</th>
                        <th>订单编号(查询号)</th>
                        <th>时间</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="good in paginatedGoods" :key="good.goodid">
                        <td>{{ good.orderid }}</td>
                        <td>{{ good.goodid }}</td>
                        <td>
                            <button @click="changeStatus(good)" :class="getStatusClass(good.step)">{{ getStatusText(good.step) }}</button>
                        </td>
                        <td>{{ good.location }}</td>
                        <td>{{ good.tracking_number }}</td>
                        <td>{{ good.createtime }}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>


</template>

<script>
import axios from "axios";
export default {
    name: "ManageLogistics",
    data() {
        return {
            currentUser: null,
            paginatedGoods: [] // 这里放你的商品数据
        };
    },
    created() {
        this.fetchUsrFromSession();
        this.fetchLogisticsData();
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
                if (!this.isLoggedIn || !this.isSeller) {
                    this.$router.push('/');
                }
            } catch (error) {
                console.error('获取用户数据错误:', error);
            }
        },
        async fetchLogisticsData() {
            try {
                const response = await axios.get('/logistics-data'); // 替换为获取物流数据的实际 API
                this.paginatedGoods = response.data;
            } catch (error) {
                console.error('获取物流数据错误:', error);
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
        },
        changeStatus(good) {
            const statusOrder = ['待发货', '运输中', '待收货', '已收货'];
            let currentIndex = statusOrder.indexOf(good.step);
            if (currentIndex < statusOrder.length - 1) {
                good.step = statusOrder[currentIndex + 1];
            }
        },
        getStatusText(step) {
            return step;
        },
        getStatusClass(step) {
            switch (step) {
                case '待发货':
                    return 'status-pending';
                case '运输中':
                    return 'status-shipping';
                case '待收货':
                    return 'status-waiting';
                case '已收货':
                    return 'status-received';
                default:
                    return '';
            }
        }
    }
}
</script>

<style scoped>
<!-- 左侧 -->

.logout-button {
  color: #f0f0f0; /* 可选：改变文字颜色 */
  cursor: pointer; /* 将鼠标指针改为手形，表示可点击 */
  transition: background-color 0.3s; /* 添加背景颜色过渡效果 */
}
* {
margin: 0;
padding: 0;
}
.content {
width: 100%;
height: 100%;
}
.content-left {
width: 19%;
height:850px;
background-color: #9A9A9A;
float: left;
}
.content-right {
width: 81%;
height: 600px;
background-color:rgba(255,250,250,0.5);float: left;
}
.left-title {
width: 100%;
height: 50px;
}
.left-title > a {
display: block;
width: 100%;
height: 50px;
line-height: 50px;
text-align: center;
color: black;
text-decoration: none;
}
.seg {
height: 1px;
width: 100%;
background-color: black;
}
.nav {
/*上下5,左右0*/
margin: 5px 0;
}
.account{/*账号管理*/
height: 1px;
width: 100%;
background-color: black;
}
.nav-title {
height: 40px;
width: 100%;
color: #FFFFFF;
text-align: center;
line-height: 40px;
cursor: pointer;
}
.nav-content {
width: 100%;
height: 100%;
color: #000000;
display: flex;        /* 新增：使用flex布局 */
justify-content: center;  /* 新增：使内容水平居中 */
align-items: center;  /* 新增：使内容垂直居中 */
flex-direction: column;
}
.nav-info {
color: #000000;
display: flex;        /* 新增：使用flex布局 */
align-items: center;  /* 新增：使内容垂直居中 */
flex-direction: column;
}
.nav-content > a {
display: block;
width: 100%;
height: 30px;
color: white;
text-decoration: none;
text-align: center;
line-height: 30px;
font-size: 13px;
}
.nav-content > a:hover {
color: #FFFFFF;
background-color:#696969;
}
.centered-container {
    display: flex;
    justify-content: center;
    align-items: center;
}

.content {
    display: flex;
}

.content-left {
    width: 19%;
    background-color: #9A9A9A;
    float: left;
}

.content-right {
    width: 81%;
    background-color: rgba(255, 250, 250, 0.5);
    float: left;
}

.else {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 450px;
    padding: 30px;
    background: rgba(224, 224, 224, .8);
    box-sizing: border-box;
    box-shadow: 0px 15px 25px rgba(0, 0, 0, .5);
    border-radius: 16px;
    text-align: center;
    font-family: KaiTi;
    font-size: 26px;
}

a {
    text-decoration: none;
}

.nav {
    margin: 5px 0;
}

.nav-menu {
    margin: 10px;
}

.nav-info {
    display: flex;
    align-items: center;
    flex-direction: column;
}

.username {
    font-size: 14px;
    color: #000;
    margin-left: 110px;
}

.separator-line {
    display: block;
    width: 100%;
    height: 200%;
    margin: 10px 0;
}

.black-text {
    color: white;
}

.nav-content > h3 {
    margin-bottom: 20px;
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

.status-pending {
    background-color: red;
    color: white;
}

.status-shipping {
    background-color: orange;
    color: white;
}

.status-waiting {
    background-color: blue;
    color: white;
}

.status-received {
    background-color: green;
    color: white;
}
</style>