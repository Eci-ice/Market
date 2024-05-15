<template>
  <body style="margin: 0;">
  <div v-if="isLoggedIn">
    <div class="left" >
      <!-- 页面头部 -->
      <table class="daohang">
        <img class="head1" src="~@/assets/img/buyer/head.png" alt="" >

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

    <div class="right">
      <h2 class="section-title">今日推荐商品</h2>
      <div class="goods-container">
        <!-- 遍历展示猫粮列表 -->
        <div v-for="item in items" :key="item.goodid" class="good-item">
          <div @click="postToBuyerShop(item.goodid)">
            <div class="picture">
              <!-- 显示猫粮图片 -->
              <img :src="getImageUrl(item.picture)" alt="猫粮图片">
            </div>
            <div class="goodname">商品名称: {{ item.goodname.trim() }}</div>
            <div class="price">价格: {{ item.price }} 元/袋</div>
            <div class="description">每日推荐饮食量: {{ item.weight }}g</div>
          </div>
        </div>
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
              <h3 class="head4-1">其他功能请登录后使用</h3>
            </td>
          </tr>
          <tr>
            <td class="head5">
              <button @click="handleLogout" class="head5-1" style="cursor: pointer;">返回登录</button>
            </td>
          </tr>
        </table>
      </div>
      <div class="right">该功能请登录后使用</div>
    </div>
  </div>
  </body>
</template>

<script>
import { mapActions } from 'vuex';
import axios from "axios";

export default {
  data() {
    return {
      items: [], // 存储猫粮数据
      username: '', // 用户名
      currentUser: null, // 当前用户信息
    };
  },
  created() {
    this.fetchUserData();
  },
  computed: {
    isLoggedIn() {
      return !!this.currentUser;
    },
    getUsername() {
      // 如果当前用户数据不为空，则返回用户名；否则返回未登录
      return this.currentUser ? this.currentUser.username : '未登录';
    }
  },
  methods: {
    ...mapActions(['logout']),
    getImageUrl(picturePath) {
      // 处理相对路径，转换为完整的图片 URL
      const imagePath = picturePath.replace(/^\.\//, '').trim(); // 去除相对路径中的 './'
      //return baseUrl + imagePath;
       return require(`~@/${imagePath}`);
    },
    async fetchUserData() {
      try {
        const response = await axios.get('/now-usr');
        const user = response.data;
        if (user) {
          this.currentUser = user;
          this.username = user.username;
          this.fetchRecommendedCatFood(); // 获取推荐猫粮数据
        }
      } catch (error) {
        console.error('获取用户数据错误:', error);
      }
    },
    async fetchRecommendedCatFood() {
      try {
        if (this.isLoggedIn && this.currentUser && this.currentUser.userid) {
          const response = await axios.get(`/cat/show-recommend/${this.currentUser.userid}`);
          const { data } = response;
          if (data && data.data) {
            this.items = data.data.slice(0, 3); // 取前三个猫粮
          }
        }
      } catch (error) {
        console.error('获取推荐猫粮数据错误:', error);
      }
    },
    handleLogout() {
      this.logout();
      this.$router.push('/');
    },
    navigateTo(routeName) {
      this.$router.push({ name: routeName });
    },
    async postToBuyerShop(goodid) {
      const response = await fetch(`/good/buyer-show-good/${goodid}`, {
        method: 'POST',
      });

      this.selectedFiles = []; // 清空照片列表

      // 解析响应数据
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
    },
  },
};
</script>

<style scoped>
body {
  margin: 0;
}

.left {
  width: 287px;
  height: 100vh;
  background-color: rgba(61, 61, 61, 0.33);
  float: left;
}

.right {
  width: calc(100% - 287px);
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}

.daohang {
  background-color: rgba(0, 0, 0, 0);
  width: 200px;
  margin-left: 30px;
}

.head1 {
  position: relative;
  top: 30px;
  left: 70px;
}

.head2 {
  text-align: center;
  vertical-align: top;
  font-size: 36px;
  color: white;
  height: 100px;
}

.head4,
.head5 {
  text-align: center;
  height: 100px;
}

.head4-1,
.head5-1 {
  text-decoration: none;
  color: #ffffff;
  font-size: 28px;
  font-weight: bold;
}

.goods-container {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(0, 1fr));
  gap: 50px;
  justify-items: center; /* 将项目在 grid 单元格内居中对齐 */
  justify-content: center; /* 将整个 grid 内容在容器内居中对齐 */
}

.good-item {
  margin: 10px;
  width: 100%; /* 宽度占满父容器 */
  padding: 20px;
  border: 1px solid #ccc;
  text-align: center;
  background: rgba(220, 220, 220, 0.37);
}

.picture img {
  width: 100%;
  max-width: 500px;
  height: auto;
}

.price,
.description {
  margin-top: 10px;
}
</style>
