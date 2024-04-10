<template>
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
            <div class="user-image">
              <img src="@/assets/img/catbackground.png" alt="用户头像" />
            </div>
            <div class="username">{{ getUsername }}</div>
            <img src="@/assets/img/line.png" alt="分割线" class="separator-line"><!-- 新增图片 -->
          </div>
          <!--子标题-->
          <div class="nav-subtitles">
            <!-- 导航链接 -->
            <h3 @click="navigateTo('BuyerCart')" class="black-text">我的购物车</h3>
            <h3 @click="navigateTo('BuyerLikes')" class="black-text">我的收藏</h3>
            <h3 @click="navigateTo('buyerHistory')" class="black-text">历史购买记录</h3>
            <h3 @click="navigateTo('BuyerShowCat')" class="black-text">查看我的猫咪信息</h3>
            <h3 @click="navigateTo('Buyeruploadcat')" class="black-text">添加我的猫咪信息</h3>
          </div>
        </div>
      </div>
    </div>
    <!--右侧显示栏-->
    <div class="right">
      <a href="/#/buyer">
        <img src="@/assets/img/buyer/arrow.png" alt="" width="40" title="返回商品界面">
      </a>
      <div class="picture-k">
        <div class="media-container" v-for="(mediaFile, index) in selectedProduct.mediaFiles" :key="index"
          v-show="mediaFile.isActive">
          <img v-if="!mediaFile.isVideo" :src="mediaFile.url" alt="商品图片" class="product-image">
          <div class="media-navigation">
            <button class="media-navigation-button prev-button" @click="handlePrevButton">＜</button>
            <button class="media-navigation-button next-button" @click="handleNextButton">＞</button>
          </div>
        </div>
      </div>
      <table class="good-2">
        <tr>
          <td class="goodname">姓名：</td>
          <td>诺诺</td>
        </tr>
        <tr>
          <td class="price">年龄：</td>
          <td>1岁</td>
        </tr>
        <tr>
          <td class="goodname">性别：</td>
          <td>妹妹</td>
        </tr>
        <tr>
          <td class="price">体重：</td>
          <td>2.5kg</td>
        </tr>
        <tr>
          <td class="goodname">绝育：</td>
          <td>是</td>
        </tr>
        <tr>
          <td class="goodname">疫苗：</td>
          <td>一针</td>
        </tr>
        <tr>
          <td class="goodname">品种：</td>
          <td>中华田园猫</td>
        </tr>
        <tr>
          <td class="goodname">性格：</td>
          <td>活泼好动</td>
        </tr>
      </table>
      <div class="media-navigation">
        <button class="media-navigation-button prev-button" @click="handlePrevButton">＜</button>
        <button class="media-navigation-button next-button" @click="handleNextButton">＞</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "MyCatInformationCheck",
  methods: {
    handleLogout() {
      // 处理退出登录逻辑
    },
    navigateTo(page) {
      // 导航到指定页面的逻辑
    },
    handlePrevButton() {
      // 处理向前按钮点击事件的逻辑
    },
    handleNextButton() {
      // 处理向后按钮点击事件的逻辑
    }
  },
  data() {
    return {
      getUsername: "User" // 替换为你的用户名变量
    };
  }
}
</script>
<style scoped>
.else {
  position: absolute;
  top: 1040%;
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

.daohang {
  background-color: rgba(0, 0, 0, 0);
  width: 100%;
  /* 修改以适应全宽 */
  position: relative;
  padding: 10px;
  /* 增加内边距 */
}

.content {
  display: flex;
  /* 使用Flex布局 */
  width: 100%;
  height: 100%;
}

.content-left {
  width: 20%;
  background-color: rgba(61, 61, 61, 0.33);
}

.nav-info,
.user-image,
.username {
  display: flex;
  align-items: center;
  justify-content: center;
}

.user-image img,
.separator-line {
  width: 50px;
  height: 50px;
}

.nav-subtitles h3 {
  cursor: pointer;
  color: #FFF;
  /* 修正为白色文字 */
  margin: 10px 0;
  /* 增加间距 */
}

.right {
  width: 80%;
  /* 占据剩余空间 */
  position: relative;
  /* 调整定位 */
  padding: 20px;
  /* 增加内边距 */
}

.picture-k,
.product-image {
  width: 100%;
  height: auto;
}

.good-2 td {
  padding: 8px;
  /* 增加单元格内边距 */
  font-size: 16px;
  /* 调整字体大小 */
}
</style>
