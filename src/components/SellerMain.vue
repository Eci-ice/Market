<template>
  <div v-if="isLoggedIn">
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
            <div class="nav-content">
              <!-- 导航链接 -->
              <h3 @click="navigateTo('UpdatePassword')" class="black-text" style="cursor: pointer;">修改密码</h3>
              <h3 @click="navigateTo('UploadOneGood')" class="black-text" style="cursor: pointer;">发布商品</h3>
              <h3 @click="navigateTo('ShowUserInfo')" class="black-text" style="cursor: pointer;">查看意向订单</h3>
              <h3 @click="navigateTo('UploadMultipleGoods')" class="black-text" style="cursor: pointer;">发布多个商品</h3>
              <h3 @click="navigateTo('ShowHistoryGoods')" class="black-text" style="cursor: pointer;">查看历史商品</h3>
              <h3 @click="navigateTo('ShowGoods')" class="black-text" style="cursor: pointer;">查看全部商品</h3>
              <h3 @click="navigateTo('ShowAllUsers')" class="black-text" style="cursor: pointer;">查看客户信息</h3>
              <!-- 其他导航项 -->
            </div>
          </div>
        </div>
      </div>

      <!-- 右侧内容区域 -->
      <div>
        <!-- 根据当前选择显示不同内容 -->
        <div class="my">
          <router-view></router-view> <!-- 子路由内容将在这里渲染 -->
        </div>
      </div>
    </div>
  </div>
   <div v-else>
    <!-- 用户未登录时显示的内容 -->
    <div class="else">
      您还未登录，请先<a href="/">登录</a>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
    data() {
        return {
          // 您的数据属性
          currentUser :null
        };
    },
    created() {
      this.fetchUsrFromSession();
    },
    computed: {
      isLoggedIn() {
        // 根据当前用户数据判断用户是否登录
        return !!this.currentUser;
      },
      isSeller() {
        // 根据当前用户数据判断用户是否是卖家
        return this.currentUser && this.currentUser.power === 1;
      },
      // 判断用户是否是买家的方法
      isBuyer() {
        // 根据当前用户数据判断用户是否是买家
        return this.currentUser && this.currentUser.power === 0;
      },
      getUsername() {
        // 如果当前用户数据不为空，则返回用户名；否则返回未登录
        return this.currentUser ? this.currentUser.username : '未登录';
      }
    },
    methods: {
        async fetchUsrFromSession() {
          try {
            // 发起 GET 请求到后端接口
            const response = await axios.get('/now-usr');
            // 解析响应数据
            const usr = response.data;
            // 更新组件的 currentUser 数据
            this.currentUser = usr;
            // console.log(this.currentUser);
            // console.log(!this.isLoggedIn);
            // console.log(!this.isSeller);
            // console.log(!this.isLoggedIn || !this.isSeller);
            // console.log("ss");
            if (!this.isLoggedIn || !this.isSeller) {
              this.$router.push('/');
            }
            return true;
          } catch (error) {
            console.error('获取用户数据错误:', error);
            return false;
          }
        },
        async handleLogout() {
        try {
          // 向后端发送登出请求
          await axios.get('/logout-control');
          // 跳转到指定路由
          this.$router.push('/');
        } catch (error) {
          console.error('登出失败:', error);
          // 可选：处理登出失败的情况
        }
      },
        navigateTo(routeName) {
            this.$router.push({ name: routeName });
        },
    },
    beforeMount() {

    },
    mounted() {
      console.log('SellerMain 组件已挂载');
    },
};
</script>



<!-- 未登录框 -->
<style type="text/css" scoped>
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
</style>
<!-- 导航栏 -->
<style scoped>
body{
margin:0px;
font-family:sans-serif;
background-image: url('~@/assets/img/beijing2.jpg');
background-repeat: no-repeat;
background-size: cover;
background-position: center center;
background-attachment: fixed;
height: 100%;
overflow: auto;
position: relative;
}
.user-info {
    padding: 20px;
    display: block;
    align-items: center;
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

ul{
width:100%;
height:56px;
padding:10px;
list-style-type:none;
background-color:#696969;
display:block;
}
li{
text-align:center;
float:left;
padding: 14px 16px;
}
li a {
  display: block;
  color: white;
  text-align: center;
  text-decoration: none;

}
li a:hover{
color: white;
background-color:#696969;
}
body{
margin:0 auto;
padding:0;
}
</style>


<!-- 左侧 -->
<style scoped>
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

            /*右边内容区*/
.content-right{
font-size: 50px;
line-height: 600px;
text-align: center;
background-color:#D3D3D3;
}
.separator-line {
    display: block;
    width: 100%; /* 或者您需要的宽度 */
    height: 200%; /* 保持图片的纵横比 */
    margin: 10px 0; /* 添加一些边距 */
}
.black-text {
    color: white;
}
.nav-content > h3 {
    margin-bottom: 20px;   /* Increase or decrease this value to adjust spacing */
}

</style>
        
<!-- 商品信息 -->
<style type="text/css" scoped>
table, th, td {
border: solid 1px #efefef;
}
table {
width: 100%;
height:40px;
margin: auto;
border-collapse: collapse;
text-align: center;
border-radius: 6px;
}
td,th{
padding: 10px;
}
tr:first-child {
background-color: #efefef;
border-radius: 4px 4px 0 0;
border-bottom: solid 1px #ddd;
}
</style>

<!-- 右边显示具体信息 -->
<style type="text/css" scoped>
div.my { 
width: 80%;
height: 850px;
/*border: 1px solid red;*/
float: left;
overflow-y: auto; /* 当内容超出高度时显示滚动条 */
}
</style>