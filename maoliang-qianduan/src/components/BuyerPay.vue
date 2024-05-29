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
    <div class="right" style="width: 75%;">
      <router-link :to="{ name: 'BuyerMain' }">
        返回
      </router-link>

        <table style="border-collapse: collapse; width: 100%; box-shadow: 0 2px 4px rgba(0,0,0,0.1);" border="1"
               align="center" cellpadding="10">
          <tr>
            <th>ID</th>
            <th>地址</th>
            <th>电话</th>
            <th>购买人姓名</th>
            <th>商品展示内容</th>
            <th>商品名称</th>
            <th>商品单价</th>
            <th>购买数量</th>
            <th>商品总价</th>
            <th>操作</th>
            <th>订单状态</th>
          </tr>
          <tr v-for="order in paginatedOrders" :key="order.orderid">
            <td>{{ order.orderid }}</td>
            <td>{{ order.address }}</td>
            <td>{{ order.telephone }}</td>
            <td>{{ order.buyername }}</td>
            <td>
              <div class="media-container">
                <div v-for="(media, index) in order.mediaFiles" :key="index" v-show="media.isActive">
                  <img v-if="!isVideo(media)" :src="getImageUrl(media.url)" alt="商品图片" v-show="media.isActive">
                  <video v-if="isVideo(media)" :src="getImageUrl(media.url)" controls v-show="media.isActive"></video>
                </div>
              </div>
              <div>
                <button @click="showPrevMedia(good)">＜</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <button @click="showNextMedia(good)">＞</button>
              </div>
            </td>
            <td>{{ order.goodname }}</td>
            <td>{{ order.price }}</td>
            <td>{{ order.number }}</td>
            <td>{{ order.price*order.number }}</td>
            <td>
              <button v-if="order.orderstate === 2" class="green-btn"
                      @click="handlePendingPayment(order.orderid)"
                      style="background-color: transparent; color: #f44336; border: 2px solid #f44336; padding: 5px 10px; border-radius: 4px; cursor: pointer;">待支付</button>
              <button v-if="order.orderstate === 5" class="green-btn"
                      @click="handleOrderAction(order.orderid, 'confirmCompletion')"
                      style="background-color: transparent; color: #f44336; border: 2px solid #f44336; padding: 5px 10px; border-radius: 4px; cursor: pointer;">确认交易完成</button>
              <button v-if="order.orderstate >= 0 && order.orderstate < 5" class="red-btn"
                      @click="handleOrderAction(order.orderid, 'cancel')"
                      style="background-color: transparent; color: #f44336; border: 2px solid #f44336; padding: 5px 10px; border-radius: 4px; cursor: pointer;">取消订单</button>
              <span v-if="order.orderstate > 4 || order.orderstate === -1">无法操作订单</span>
            </td>
            <td>{{ getOrderStatus(order.orderstate) }}</td>
          </tr>
        </table>
        <div class="pagination">
          <button @click="goToPrevPage" :disabled="isPrevDisabled" class="prev">上一页</button>
          <span>{{ currentPage }} / {{ totalPages }}</span>
          <button @click="goToNextPage" :disabled="isNextDisabled" class="next">下一页</button>
        </div>
      <div class="container">
        <div v-if="showPaymentModal" class="payment-modal">
          <button @click="closePaymentModal" class="close-btn">关闭</button>
          <div style="text-align: center;">
            <h2>请选择支付方式</h2>
          </div>
          <div style="margin-bottom: 20px;">
            <label for="orderNumber">订单编号:</label>
            <span id="orderNumber" style="margin-left: 10px;">{{ orderNumber }}</span>
          </div>
          <div style="margin-bottom: 20px;">
            <label for="orderPrice">订单价格:</label>
            <span id="orderPrice" style="margin-left: 10px;">{{ orderPrice }}</span>
          </div>
          <div style="display: flex; justify-content: center;">
            <button class="alipay" @click="aliPayment(orderNumber,orderPrice)">支付宝支付</button>
            <button class="unionpay" @click="unionPayment(orderNumber, orderPrice)">银行卡支付</button>
          </div>
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
  </body>
</template>

<script>
import {  mapActions } from 'vuex';
import axios from 'axios'; // 确保已经安装并导入axios
export default {
  data() {
    return {
      orders: [
      ],
      currentPage: 1,
      pageSize: 5,
      totalItems: 8,
      currentUser:null,
      showPaymentModal: false,
      orderNumber: '', // 存储订单编号
      orderPrice: '' // 存储订单价格
    };
  },
  mounted() {
    this.fetchOrders();
  },
  created() {
    this.fetchUsrFromSession();
    this.isPayed();
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
    },
    totalPages() {
      if (1 > Math.ceil(this.orders.length / this.pageSize)) return 1;
      return Math.ceil(this.orders.length / this.pageSize);
    },
    paginatedOrders() {
      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      return this.orders.slice(start, end);
    },
    isPrevDisabled() {
      return this.currentPage === 1;
    },

    isNextDisabled() {
      return this.currentPage === this.totalPages;
    },
  },
  methods: {
    ...mapActions(['logout']),
    handleLogout() {
      this.logout();
      this.$router.push('/');
    },
    navigateTo(routeName) {
      this.$router.push({ name: routeName });
    },
    beforeMount() {
      if (!this.isLoggedIn) {
        this.$router.push('/');
      }
    },
    goBack() {
      this.$router.push({ name: 'BuyerMain' });
    },
    getOrderStatus(statusCode) {
      const statusMap = {
        '-1': '交易取消',
        '0': '等待客户下单',
        '1': '等待商家确认',
        '2': '等待客户付款',
        '3': '等待备货确认',
        '4': '等待发货确认',
        '5': '商家已发货，等待交易确认',
        '6': '交易成功',
        // 添加其他状态映射
      };
      return statusMap[statusCode] || '未知状态';
    },
    handleOrderAction(orderId, action) {
      // 根据 action 类型处理不同的订单操作
      switch (action) {
        case 'confirmCompletion':
          this.confirmOrder(orderId);
          break;
        case 'cancel':
          this.cancelOrder(orderId);
          break;
          // 可以根据需要添加其他操作
        default:
          console.log('无效的操作');
      }
    },
    confirmOrder(orderId) {
      const order = this.orders.find(o => o.orderid === orderId);
      if (order) {
        // 这里将状态加 1 来模拟确认订单
        let updatedOrderState = order.orderstate + 1;
        // 发送异步请求到服务器以更新订单状态
        axios.post('/order/buyerhistoryconfirmorder-control', { orderid: orderId, orderstate: updatedOrderState })
            .then(response => {
              if (response.data && response.data.msg === '确认订单成功') {
                // 如果成功，更新本地订单状态
                order.orderstate = updatedOrderState;
                alert("该订单阶段确认成功！");
                // 可能还需要重新获取订单列表
              } else {
                alert("该订单阶段确认失败！");
              }
            })
            .catch(error => {
              console.error('确认订单出错:', error);
            });
      }

    },
    handlePendingPayment(orderId) {
      axios.get('/delete-now-TradeNo');
      const currentOrder = this.orders.find(order => order.orderid === orderId);

      if (currentOrder) {
        // 将订单编号和价格填写到输入框中
        this.orderNumber = currentOrder.orderid;
        this.orderPrice = currentOrder.price * currentOrder.number;

      }
      // 显示支付弹窗
      this.showPaymentModal = true;
      // 执行处理“待支付”逻辑
      //this.handleOrderAction(orderId, 'confirmCompletion');
    },
    closePaymentModal() {
      this.showPaymentModal = false; // 设置 showPaymentModal 为 false，隐藏模态框
    },
    cancelOrder(orderId) {
      const order = this.orders.find(o => o.orderid === orderId);
      if (order) {
        // 发送异步请求到服务器以更新订单状态
        axios.post('/order/deleteorder-control', { orderid: orderId,orderstate:-1})
            .then(response => {
              if (response.data && response.data.msg === '取消订单成功') {
                // 如果成功，更新本地订单状态
                order.orderstate = -1;
                alert("该订单取消成功！");
                // 可能还需要重新获取订单列表
              } else {
                alert("该订单取消失败！");
              }
            })
            .catch(error => {
              console.error('取消订单出错:', error);
            });
      }
    },
    goToPrevPage() {
      if (this.currentPage > 1) {
        this.currentPage--;
        this.fetchOrders(); // 实现该方法以从服务器获取订单
      }
    },
    goToNextPage() {
      if (this.currentPage < this.totalPages) {
        this.currentPage++;
        this.fetchOrders(); // 实现该方法以从服务器获取订单
      }
    },
    async isPayed() {
      try {
        // 发起 GET 请求到后端接口
        const response = await axios.get('/now-TradeNo');
        const responseData = response.data;

        // 根据下划线 "_" 进行拆分，获取 subject 和 tradeNo
        const splitData = responseData.split('Z');
        const subject = splitData[0];

        const response2 = await axios.get(`/order/aliPaymentStatus/${responseData}`);
        console.log("response2:"+response2.data);

        if(response2.data === "已支付"){
          // 发送异步请求到服务器以更新订单状态
          axios.post('/order/buyerhistoryconfirmorder-control', { orderid: subject , orderstate: 3 })
              .then(response3 => {
                if (response3.data && response3.data.msg === '确认订单成功') {
                  alert("订单"+response.data+"已支付！");
                  axios.get('/delete-now-TradeNo');
                  // 可能还需要重新获取订单列表
                  this.$router.push({ path: '/buyer-pay'});
                } else {
                  alert("不存在订单！");
                }
              })
              .catch(error => {
                console.error('确认订单出错:', error);
              });
        }
        else{
          alert("订单"+response.data+"未支付，请检查是否交易成功！");
        }

      } catch (error) {
        console.error('获取用户数据错误:', error);
        return false;
      }
    },
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
    async aliPayment(orderNumber,orderPrice){
        await axios.get(`/order/alipay`, {
          params: {
            orderid:orderNumber,
            money: orderPrice
          }
        }).then((resp)=>{
          // 添加之前先删除一下，如果单页面，页面不刷新，添加进去的内容会一直保留在页面中，二次调用form表单会出错
          const divForm = document.getElementsByTagName("div");
          if (divForm.length) {
            document.body.removeChild(divForm[0]);
          }
          const div = document.createElement("div");
          div.innerHTML = resp.data; // data就是接口返回的form 表单字符串
          document.body.appendChild(div);
          //document.forms[0].setAttribute("target", "_blank"); // 新开窗口跳转
          document.forms[0].submit();
        });


    },
    async unionPayment(orderNumber, orderPrice) {
      try {
        const response = await axios.get(`/order/unionpay`, {
          params: {
            orderid: orderNumber,
            money: orderPrice
          }
        });
        const divForm = document.createElement('div');
        divForm.innerHTML = response.data; // 银联支付接口返回的表单字符串
        document.body.appendChild(divForm);
        document.forms[0].submit();
      } catch (error) {
        console.error('银联支付出错:', error);
      }
    },
    async fetchOrders() {
      await this.fetchUsrFromSession();

      try {
        const response = await axios.get('/order/showbuyerorderinfo-control', {
          params: {
            name: this.currentUser.username
          }
        });

        if (response.data && response.data.data) {
          this.orders = response.data.data; // 假设这是包含所有订单的数组
          for (const order of this.orders) {
            await this.fetchGoodSession(order);
          }
          console.log("获取意向订单数据列表成功");
        } else {
          console.error("获取意向订单数据列表失败");
        }
      } catch (error) {
        console.error('获取意向订单数据列表错误', error);
      }
    },

    async fetchGoodSession(order) {
      try {
        const response = await axios.get(`/good/buyer-get-good/${order.goodid}`);
        const good = response.data.data;
        console.log(good);
        const trimmedPicture = good.picture.trim();
        const paths = trimmedPicture.split(',');
        const mediaFiles = paths.map((path, i) => {
          return {
            url: path,
            isActive: i === 0
          };
        });

        // 更新订单对象的属性
        order.goodname = good.goodname.trim();
        order.description = good.description.trim();
        order.price = good.price;
        order.mediaFiles = mediaFiles;

      } catch (error) {
        console.error('获取商品信息错误:', error);
      }
    },
    getImageUrl(picturePath) {
      // 处理相对路径，转换为完整的图片 URL
      const imagePath = picturePath.replace(/^\.\//, '').trim(); // 去除相对路径中的 './'
      //return baseUrl + imagePath;
      return require(`~@/${imagePath}`);
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
    setActiveMedia(good, index) {
      good.mediaFiles.forEach((media, idx) => {
        media.isActive = idx === index;
        console.log(`Media at index ${idx} active status: `, media.isActive);
      });
    },
    isVideo(media) {
      const isMediaVideo = media.url.endsWith('.mp4');
      console.log(`Is media a video: ${isMediaVideo}`);
      return isMediaVideo;
    },

  },



};
</script>

<style scoped>
.centered-container {
  display: flex;
  justify-content: center;
  align-items: center;
}

.logout-button {
  color: #f0f0f0;
  /* 可选：改变文字颜色 */
  cursor: pointer;
  /* 将鼠标指针改为手形，表示可点击 */
  transition: background-color 0.3s;
  /* 添加背景颜色过渡效果 */
}

* {
  background-color: #FFF9F1;

}

body {
  display: block;
  background-color: #FFF9F1 !important;
  background-image: none;
}

.left {
  /* 买家导航 */
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
  /*格子宽度*/
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
  /*格子高度*/
  position: relative;
  z-index: 2;
}

.head4 {
  background-color: rgba(61, 61, 61, 0.33);
  text-align: center;
  height: 100px;
  /*格子高度*/
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
  /*格子高度*/
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

/* 商品 */
.right {
  /* 商品显示
    width: 1340px;*/
  height: 100vh;
  /* background-color: aquamarine; */
  position: absolute;
  /*绝对定位*/
  left: 350px;

  float: right;
}


.goods {
  display: flex;
  /*使用flex布局*/
  flex-wrap: wrap;
  /*允许元素换行*/
  justify-content: space-between;
  /*元素之间留有空隙*/
  border: 1px solid #000;
  /*添加边框*/
}

.show-1 {
  flex: 0 0 30%;
  /*每个元素占用30%的宽度，这样每行就可以放3个元素*/
  border: 1px solid #000;
  /*添加边框*/
  margin-bottom: 10px;
  /*添加底部边距*/
}

.picture {
  text-align: center;
  /*图片居中*/
}

.price {
  font-size: 20px;
  height: 20px;
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 20px;
}

.prev,
.next {
  background-color: rgb(237, 196, 110);
}

form {
  display: flex;
  /* 让表单内的元素在同一行显示 */
  width: 600px;
  height: 45px;
}

input[type="text"] {
  flex-grow: 1;
  /* 让搜索框占据剩余的空间 */
}

input[name="keyword"] {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

#search_list {
  position: fixed;
  top: 110px;
  left: 350px;
  width: 400px;
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

.media-container img,
.media-container video {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: contain;
}

.media-container img.active,
.media-container video.active {
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
.payment-modal {
  position: fixed;
  width: 80%;
  left: 50%;
  transform: translate(-50%, -50%);
  border: 1px solid blue;
  padding: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  z-index: 9999;
}
.alipay{
  display: flex;
  padding: 5px 10px;
  justify-content: center;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 20px;
}
.unionpay{
  display: flex;
  padding: 5px 10px;
  justify-content: center;
  background-color: #ff8c00;
  color: white;
  border: none;
  border-radius: 20px;
}
</style>
