<template>
  <ul class="daohang">
    <div class="title-container">
      <h2>请回答密保问题</h2>
      <router-link to="/">返回初始页面</router-link>
    </div>
    <div class="cat-background"></div>
  </ul>

  <div class="login">
    <form @submit.prevent="submitSecretAnswer">
      <div class="input">
        <h3>用户{{ storedUsr.username }}，请您回答密保问题</h3>
        <h3>{{ storedUsr.question }}</h3>
        <input type="text" v-model="secretAnswer" placeholder="请输入答案" />
        <input class="button" type="submit" value="找回密码" />
      </div>
    </form>
  </div>

  <div v-if="error" class="error-message" id="b">
    {{ error }}
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      storedUsr: JSON.parse(sessionStorage.getItem("forgetUsr") || "{}"), // 防止getItem返回null时，JSON.parse报错
      secretAnswer: "",
      error: "",
    };
  },
  methods: {
    async submitSecretAnswer() {
      try {
        const answerData = {
          answer: this.secretAnswer,
        };
        const response = await axios.post(
          "/forget/answer-control",
          answerData,
          {
            headers: {
              "Content-Type": "application/json",
            },
          }
        );
        const responseData = await response;
        if (responseData.data.msg === "rightAnswer") {
          alert(`答案正确！您的密码是：${this.storedUsr.pwd}`);
          this.$router.push("/"); // 跳转到登录页面
        } else if (responseData.data.msg === "badAnswer") {
          alert("答案错误！请重新输入密保答案！");
        } else {
          alert("System Wrong!");
        }
      } catch (err) {
        this.error = "回答密保问题时出错";
        // 可以根据错误类型决定是否跳转
      }
    },
  },
};
</script>

<!-- 未登录框 -->
<style type="text/css" scoped>
html {
  height: 100%;
}
.else {
  position: absolute;
  top: 40%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 450px;
  padding: 30px;
  background: rgba(224, 224, 224, 0.8);
  box-sizing: border-box;
  box-shadow: 0px 15px 25px rgba(0, 0, 0, 0.5);
  border-radius: 16px;
  text-align: center;
  font-family: KaiTi;
  font-size: 26px;
}
a {
  text-decoration: none;
}
</style>

<!-- 背景 -->
<style scoped>
body {
  margin: 0px;
  padding: 10px;
  font-family: sans-serif;
  background-image: url("@/assets/img/beijing2.jpg");
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
  background-image: url("@/assets/img/catbackground.png"); /* 请确保路径正确 */
  background-repeat: no-repeat;
  background-size: cover; /* 这会让背景图片覆盖整个元素区域 */
  width: 150px; /* 设置图片显示区域的宽度 */
  height: 150px; /* 设置图片显示区域的高度 */
  z-index: -1; /* 这会让图片在其他内容下方显示 */
}
</style>

<!-- 导航 -->

<style scoped>
#b {
  text-align: center;
  color: red;
}
</style>
<style type="text/css" scoped>
ul {
  width: 100%;
  height: 80px;
  padding: 10px;
  list-style-type: none;
  background-color: rgba(255, 250, 250, 0.5);
  display: block;
}
li {
  float: left;
}
h2 {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding-left: 20px;
  font-family: Heiti;
}
</style>

<!-- 登录框 -->
<style scoped>
.login {
  position: absolute;
  top: 65%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 400px;
  padding: 30px;
  padding-bottom: 20px;
  background: rgba(224, 224, 224, 0.8);
  box-sizing: border-box;
  box-shadow: 0px 15px 25px rgba(0, 0, 0, 0.5);
  border-radius: 20px;
  display: flex;
  align-items: center; /* 子元素水平居中 */
}
.login h4 {
  margin: 0 0 30px;
  padding: 0;
  color: #696969;
  text-align: center;
}
.login .input {
  position: relative;
}
.login .input input {
  width: 100%;
  padding: 10px 0;
  font-size: 16px;
  letter-spacing: 1px;
  margin-bottom: 10px;
  border: none;
  border-bottom: 1px solid #fff;
  outline: none;
  background: transparent;
}
.login .input label {
  position: absolute;
  top: 5px;
  left: 0px;
  padding: 10px 0;
  font-size: 16px;
  color: #696969;
  pointer-events: none;
  transition: 0.5s;
}
.login .input input:focus ~ label,
.login .input input:valid ~ label {
  top: -18px;
  left: 0;
  color: #696969;
  font-size: 14px;
}
.login input[type="submit"] {
  border: none;
  outline: none;
  left: 60%;
  font-size: 20px;
  color: white;
  background: orange;
  cursor: pointer;
  border-radius: 10px;
}
.image-buttons {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  margin: 0px 0;
}

.image-buttons img {
  width: 350px; /* 您可以根据需要调整图片大小 */
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
  margin-left: 650px;
  margin-top: 20px;
  text-align: center;
}

.title-container a {
  text-decoration: none;
  font-family: Heiti;
  font-size: 18px;
  color: black;
  margin-right: 30px;
  margin-top: 20px;
  font-weight: bold;
}
.butt-1 {
  display: flex;
  border-radius: 10px;
  margin: 10px;
  margin-left: 20px;
  border: 1px;
  width: 300px;
  text-align: center;
  background-color: orange;
  font-size: 25px;
  padding: 10px 20px; /* 调整字体与边框的距离 */
  cursor: pointer; /* 设置悬停时的鼠标样式为手型 */
}
</style>
