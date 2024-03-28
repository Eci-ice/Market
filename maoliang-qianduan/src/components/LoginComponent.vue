<template>
    <ul class="daohang">
      <li><h2>"喵咪美食坊"欢迎您!</h2></li>
      <div class="cat-background"></div>
    </ul>

    <div v-if="!isLoggedIn" class="login">
      <form @submit.prevent="handleLogin">
        <div class="input">
          <img src="~@/assets/img/account.png" alt="账号" style="height: 25px; vertical-align: middle;"> 
          <input type="text" name="username" placeholder="账号" v-model="credentials.username">
        </div>
        <div class="input">
          <img src="~@/assets/img/password.png" alt="密码" style="height: 25px; vertical-align: middle;"> 
          <input type="password" name="password" placeholder="密码" v-model="credentials.password">
        </div>
        <router-link to="/forgot-password" class="forgot-password">忘记密码?</router-link>
        <div class="image-buttons">
          <button class="butt-1" type="submit">登录</button>
          <router-link to="/register" class="butt-1">注册</router-link>
          <router-link to="/buyer" class="butt-1">游客访问</router-link>
        </div>
      </form>
    </div>
    <div v-else class="else">
      您已登录，无需再登录!!!
      <router-link :to="adminRoute">返回</router-link>
    </div>
    <div id="b">
        <div v-if="error" class="error">{{ error }}</div>
    </div>
</template>



<script>
import axios from 'axios';
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

export default {
  data() {
    return {
      credentials: {
        username: '',
        password: ''
      },
      error: '',
      mockAdminData: null,
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
    adminRoute() {
      // 根据用户权限返回相应的路由
      return this.isSeller ? '/seller' : '/buyer';
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

        return true;
      } catch (error) {
        console.error('获取用户数据错误:', error);
        return false;
      }
    },
    async handleLogin() {
      try {
        const credentials = {
          username: this.credentials.username,
          password: this.credentials.password
        };

        const response = await fetch('/usr/login-control', {
          method: 'POST',
          body: JSON.stringify(credentials),
          headers: {
            'Content-Type': 'application/json'
          }
        });

        const responseData = await response.json();

        if (responseData.page === '/error') {
          // 重定向到错误页面，并将错误消息和重定向目标作为参数传递
          this.$router.push({ path: '/error', query: { err: responseData.msg, to: responseData.data }})
        } else if (responseData.page === '/success') {
          // 重定向到成功界面，并将成功消息和重定向目标作为参数传递
          this.$router.push({ path: '/success', query: { message: responseData.msg, to: responseData.data }})
        } else if (responseData.page === null) {
          console.log("未知页面类型");
        } else {
          this.$router.push({ path: responseData.page });
        }
      } catch (error) {
        console.error("登录错误:", error);
        this.error = '登录失败';
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
  },
}
</script>



<style type="text/css" scoped>
html {
    height: 100%;
}
.else{
position:absolute;
top:40%;
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

<!-- 背景 -->
<style scoped>
body{
margin:0px;
padding:10px;
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
.cat-background {
    position: absolute;
    top: 25%; /* 调整这个值可以控制图片在页面中的高度位置 */
    left: 50%; /* 这会让图片水平居中 */
    transform: translate(-50%, -50%);
    background-image: url('~@/assets/img/catbackground.png'); /* 请确保路径正确 */
    background-repeat: no-repeat;
    background-size: cover; /* 这会让背景图片覆盖整个元素区域 */
    width: 150px; /* 设置图片显示区域的宽度 */
    height: 150px; /* 设置图片显示区域的高度 */
    z-index: -1; /* 这会让图片在其他内容下方显示 */
}
</style>

<!-- 导航 -->

<style scoped>
#b{
    text-align:center;
    color: red;
}
</style>
<style type="text/css" scoped>
ul{
width:100%;
height:80px;
padding:10px;
list-style-type:none;
background-color:rgba(255,250,250,0.5);
display:block;
}
li{
float:left;
}
h2{
	padding-left:20px;
	font-family:KaiTi;
}
</style>

<!-- 登录框 -->
<style scoped>
.login{
position:absolute;
top:65%;
left:50%;
transform:translate(-50%,-50%);
width:400px;
padding:30px;
padding-bottom:20px;
background: rgba(224,224,224,.8);
box-sizing:border-box;
box-shadow: 0px 15px 25px rgba(0,0,0,.5);
border-radius:20px;
align-items: center; /* 子元素水平居中 */
}
.login h4{
margin:0 0 30px;
padding:0;
color:#696969;
text-align:center;
}
.login .input{
position:relative;
}
.login .input input{
width:100%;
padding:10px 0;
font-size:16px;
color:#696969;
letter-spacing:1px;
margin-bottom:10px;
border:none;
border-bottom:1px solid #fff;
outline:none;
background:transparent;
}
.login .input label{
position:absolute;
top:5px;
left:0px;
padding:10px 0;
font-size:16px;
color:#696969;
pointer-events:none;
transition:.5s;
}
.login .input input:focus ~ label,
.login .input input:valid ~ label
{
top:-18px;
left:0;
color:#696969;
font-size:14px;
}
/* .login input[type="submit"]{
border:none;
outline:none;
left:60%;
font-size:20px;
color:#000000;
background:#C0C0C0;
padding:10px 20px;
cursor:pointer;
border-radius:10px;
} */
.image-buttons {
    display: flex;
    width:100%;
    flex-direction: column;
    align-items: center;
    justify-content: space-between;
    margin: 0px 0;
}

.image-buttons img {
    width: 350px;  /* 您可以根据需要调整图片大小 */
    height: 40px;
    cursor: pointer;
    transition: 0.3s;

    /* 如果需要，您可以添加一些边框或其他样式 */
    border-radius: 0px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}
.input-image-button {
    width: 350px;
    height: 40px;
}
.butt-1 {
  position: relative;
  border-radius: 10px;
  margin:10px;
  margin-left:20px;
  border:1px;
  width:300px;
  background-color: orange;
  font-size: 22px;
  padding: 10px 20px; /* 调整字体与边框的距离 */
  cursor: pointer; /* 设置悬停时的鼠标样式为手型 */
  color:white;
  text-align: center;
}

</style>