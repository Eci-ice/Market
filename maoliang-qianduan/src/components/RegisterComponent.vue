<template>
  <div>
    <ul class="daohang">
      <div class="title-container">
        <h2 class="registration-title">请您注册！</h2>
        <router-link to="/">返回初始页面</router-link>
      </div>
    </ul>
    
    <div class="login" style="top:53%;">
      <h2 v-if="isSeller">请您注册卖家账户！</h2>
      <h2 v-else>请您注册买家账户！</h2>

      <form @submit.prevent="submitForm">
        <input type="hidden" v-model="form.userType">
<!--id密码-->
        <div class="input">
          <label for="newusername" style="top: -30px; font-size: 20px; font-weight: bold;">用户名</label>
          <input id="newusername" type="text" v-model="form.username" required>
        </div>
        <div class="input">
          <label for="newpwd" style="top: -30px; font-size: 20px; font-weight: bold;">密码</label>
          <input id="newpwd" type="password" v-model="form.password" required>
        </div>
<!--密保问题-->
        <div class="input">
          <label for="newquestion" style="top: -30px; font-size: 20px; font-weight: bold;">设置密保问题</label>
          <input id="newquestion" type="text" v-model="form.securityQuestion" required>
        </div>
        <div class="input">
          <label for="newanswer" style="top: -30px; font-size: 20px; font-weight: bold;">设置答案</label>
          <input id="newanswer" type="text" v-model="form.securityAnswer" required>
        </div>

        <!-- 用户类型选择 -->
        <div class="input">
          <label>我是：</label>
          <div>
            <input type="radio" id="buyer" value="buyer" v-model="userType" @change="updateUserType('buyer')"> 买家
            <input type="radio" id="seller" value="seller" v-model="userType" @change="updateUserType('seller')"> 卖家
          </div>
        </div>

<!--        隐藏内容-->
        <div class="input" v-if="!isSeller" >
          <label for="newaddress" style="top: -30px; font-size: 20px; font-weight: bold;">默认收货地址</label>
          <input id="newaddress" type="text" v-model="form.address" required>
        </div>
        <div class="input" v-if="!isSeller">
          <label for="newphone" style="top: -30px; font-size: 20px; font-weight: bold;">联系电话</label>
          <input id="newphone" type="text" v-model="form.phone" required>
        </div>

        <div class="image-buttons" style="margin-bottom: 25px;">
          <input class="input-image-button" type="image" :src="require('@/assets/img/register.png')" alt="登录" title="登录" name="action" value="登录"/>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
// import axios from 'axios';

export default {
  data() {
    return {

      //先用个默认值
      isSeller: false,
      form: {
        userType: '',
        username: 'testuser',
        phone: '1234567890',
        address: '测试地址',
        password: 'password123',
        securityQuestion: '我的生日？',
        securityAnswer: '1月1日'
      }

      // isSeller: false,
      // form: {
      //   userType: '',
      //   username: '',
      //   phone: '',
      //   address: '',
      //   password: '',
      //   securityQuestion: '',
      //   securityAnswer: ''
      // }
    };
  },
  methods: {
    submitForm() {

      // 模拟成功响应
      const mockResponse = { success: true, message: '注册成功' };
      console.log('Form submitted:', this.form);
      console.log('Mock Response:', mockResponse);
      
      alert("注册成功！");
      // 模拟注册成功后的重定向
      if (mockResponse.success) {
        this.$router.push('/choose-register'); // 这会导航回 ChooseRegister.vue
      }

      // TODO: 移除下面的注释并实现真正的服务器请求
      // axios.post('/registerservlet', this.form)
      //   .then(response => {
      //     console.log(response.data);
      //     // 处理成功响应，比如跳转到登录页面或显示成功信息
      //   })
      //   .catch(error => {
      //     console.error(error);
      //     // 处理错误，比如显示错误信息
      //   });
    },
    updateUserType(userType) {
      this.isSeller = userType === 'seller';
      this.form.userType = userType;
    }
  },
  created() {
    this.isSeller = this.$route.query.registeruser === '1';
  },

};
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
.input-row {
  //width: 20%;
  position: absolute;
  //display: flex; /* 启用flex布局 */
  //align-items: center; /* 确保所有内容垂直居中 */
  gap: 20px; /* 在项目之间提供一些间隙 */
}
ul{
height:80px;
list-style-type:none;
background-color:rgba(255,250,250,0.5);
display:block;
}
li{
float:left;
}
h2{
	display: flex;
    flex-direction: column;
    align-items: center;
	padding-left:20px;
	font-family:Heiti;
}
.registration-title {
            text-align: center;
            font-family: Heiti;
            margin: 0;
            padding: 0;
            font-size: 26px;
            margin-top: 20px;
        }

</style>

<!-- 登录框 -->
<style scoped>


.login{
top: 50%;
left: 50%;
transform: translate(-50%, -50%);
position:absolute;
top:55%;
left:50%;
transform:translate(-50%,-50%);
width:500px;
padding:30px;
padding-bottom:20px;
background: rgba(224,224,224,.8);
box-sizing:border-box;
box-shadow: 0px 15px 25px rgba(0,0,0,.5);
border-radius:20px;
}
.login h3{
margin:0px;
color:#696969;
}

.login h4{
margin:0 0 30px;
padding:0;
color:#696969;
text-align:center;
}
.login .input{
margin-bottom: 35px;
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
.login input[type="submit"]{
border:none;
outline:none;
left:60%;
font-size:20px;
color:#000000;
background:#C0C0C0;
padding:10px 20px;
cursor:pointer;
border-radius:10px;
}
.image-buttons {
    display: flex;
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
.title-container {
    display: flex;
    align-items: center;
    justify-content: space-between;
}

.title-container h2 {
    font-family: Heiti;
    margin: 0;
    padding: 0;
    font-size: 26px;
    display:flex;
    margin-top:20px;
    text-align: center;
    justify-content: center;
}



.title-container a {
    text-decoration: none;
    font-family: Heiti;
    font-size: 18px;
    color: black;
    margin-right: 30px;
    margin-top:20px;
    font-weight: bold;
}

.radio-group {
  align-items: center; /* 垂直居中 */
  margin-bottom: 20px; /* 提供一些底部间距 */
}

.radio-group label {
  margin-right: 10px; /* 在标签和单选按钮之间提供一些右侧间距 */
  font-weight: bold;
  font-size: 20px;
}

.radio-group input[type="radio"] {
  margin-right: 5px; /* 在单选按钮和标签之间提供一些右侧间距 */
}

</style>
