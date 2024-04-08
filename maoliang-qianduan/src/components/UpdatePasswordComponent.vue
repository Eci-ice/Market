<template>
    <div v-if="isLoggedIn">
        <div style="text-align:center; margin-bottom: 0px; margin-top: 10px;">
            <img src="~@/assets/img/catbackground.png" alt="小猫" style="width:200px;height:200px;">
        </div>
        <div class="main">
            <form @submit.prevent="submitForm">
                <input type="hidden" v-model="username">
                <input type="hidden" v-model="userpwd">
                <h1>修改密码</h1>
                <div class="text-center">
                    <span>旧&nbsp;密&nbsp;码:&nbsp;</span>
                    <input type="password" v-model="oldpwd" required>
                </div><br>
                <div class="text-center">
                    <span>新&nbsp;密&nbsp;码:&nbsp;</span>
                    <input type="password" v-model="newpwd" required>
                </div><br>
                <div class="text-center">
                    <span>确认密码:&nbsp;</span>
                    <input type="password" v-model="newpwd1" required>
                </div><br>
                <div class="text-center">
                    <input type="submit" class="button" value="确认修改">
                    &nbsp;&nbsp;&nbsp;
                    &nbsp;&nbsp;&nbsp;
                    <button @click="cancelUpdate" class="button">取消修改</button>
                </div>
            </form>
        </div>
        <div v-if="message" class="alert-message" id="b">
            <p>{{ message }}</p>
        </div>
        <div v-if="error" class="alert-error" id="b">
            <p>{{ error }}</p>
        </div>
    </div>
    <div v-else class="else">
        <span>您还未登录，请先<a href="index.jsp">登录</a></span>
    </div>
</template>

<script>
export default {
    data() {
        return {
            isLoggedIn: true, // 这应该是从Vuex store或父组件获取的
            oldpwd: '',
            newpwd:'',
            newpwd1:'',
            credentials: {
            newpwd:'',
            newpwd1:''
      },
            message: null,
            error: null,
        };
    },
    methods: {
        validateForm() {
            if (this.newpwd.length > 15) {
                alert("密码不能超过15个字符！");
                return false;
            }
            // // 检查旧密码是否正确
            // if (this.oldpwd !== this.userpwd) {
            //     this.error
            //     = "旧密码错误";
            //     return false;
            // }
            // // 检查新密码是否与旧密码相同
            // if (this.oldpwd === this.newpwd) {
            //     this.error = "新密码与旧密码一致";
            //     return false;
            // }

            // // 检查新密码是否与确认密码匹配
            // if (this.newpwd !== this.newpwd1) {
            //     this.error = "新密码与确认密码不一致";
            //     return false;
            // }

            // 如果所有检查都通过，返回true
            this.error = null;
            return true;
           
        },
        async submitForm() {
            // 先进行表单验证
            const isValid = this.validateForm();
            if (!isValid) return;
            // 如果验证通过，执行密码修改逻辑
            try {
                const credentials = {
                  oldpwd: this.oldpwd,
          newpwd: this.newpwd,
          newpwd1: this.newpwd1
        };
        const response = await fetch('/usr/changedpwd-control', {
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
    }catch (error) {2
                console.error("修改密码失败:", error);
                this.error = "密码修改失败，请稍后再试。";
            }
        },
        cancelUpdate() {
            // 取消修改的逻辑
            // 例如：清空表单，跳转回上一页等
            this.oldpwd = '';
            this.newpwd = '';
            this.newpwd1 = '';
            this.error = null;
            this.message = null;
            // this.$router.push('/previous-page');
        }
    }
};
</script>

<!-- 未登录框 -->
<style type="text/css" scoped>
.text-center {
        text-align: center;
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
<style scoped>

body {
	background-image:url(~@/assets/img/beijing2.jpg);
	background-size: cover; 
	margin:0px;
	padding:0px;
}
</style>
<style scoped>
#b{
    text-align:center;
    color: red;
    font-size: 22px;
    font-family:KaiTi;
}
a{
	text-decoration:none;
	font-size:14px;
}
span{
	font-size:24px;
	font-family:KaiTi;
	color: black;
}

.main{
    max-width: 500px;
    margin: auto;
    border: none;
    margin-top: 80px;
    border-radius: 5px; 
    -moz-border-radius: 5px;
    -webkit-border-radius: 5px;
    font: 12px "Helvetica Neue", Helvetica, Arial, sans-serif;
    color: #D3D3D3;
    background: #C0C0C0;
    }
.main h1{
	font-family:KaiTi;
	text-align:center;
    padding: 8px 0px 16px 10px;
    color:black;
    border-bottom: 1px solid #444;
    }
.text1{
     margin-left: 3px;
     }
.main label>span{
     width: 15%;
     float: left;
     text-align: right;
     padding-right: 10px;
     margin-top: 10px;
     font-size:18px;
     color: black;
     }
.main input[type="text"]{
     height: 25px;
     width: 70%;
     line-height: 15px;
     padding: 5px 0px 5px 5px; 
     margin-bottom: 16px;
     margin-right: 6px;
     margin-top: 2px;
     border: none;
     border-radius:2px;
     -webkit-border-radius:2px;
     -moz-border-radius:2px;
     outline: 0 none; 
     background:  #DFDFDF;
     color: #525252;
}

.main .button{
     border: 2px solid #333;
     padding: 8px 24px 8px 24px;
     margin-bottom: 8px;
     border: none;
     border-radius: 4px;
     -moz-border-radius: 4px;
     -webkit-border-radius: 4px;
     font-weight: bold;
	text-shadow: 1px 1px 1px #FFE477;
     box-shadow: 1px 1px 1px  #3D3D3D;
     -moz-box-shadow: 1px 1px 1px  #3D3D3D;
     -webkit-box-shadow: 1px 1px 1px  #3D3D3D;
     color: #FFFFFF;
     background: #FFA500;
}
.main .button:hover{
     color:  #333;
     background-color:  #FFA500 ;
}
button{
      padding: 8px 24px 8px 24px;
     margin-bottom: 8px;
     border: none;
     border-radius: 4px;
     -moz-border-radius: 4px;
     -webkit-border-radius: 4px;
     font-weight: bold;
     text-shadow: 1px 1px 1px #FFE477;
     box-shadow: 1px 1px 1px  #3D3D3D;
     -moz-box-shadow: 1px 1px 1px  #3D3D3D;
     -webkit-box-shadow: 1px 1px 1px  #3D3D3D;
     color: #FFA500;
     background: #FFA500;
      cursor: pointer
}
button a {
    color: #FFFFFF; /* Initial color of the text */
    transition: color 0.3s ease; /* This will make the color change smoothly */
}

button:hover a {
	background: #FFA500;
    color: #333; /* Color of the text on hover */
}
</style>