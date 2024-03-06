<template>
<body style="margin: 0px;">
  <div v-if="isLoggedIn">
    <div class="left" >
    <!-- 页面头部 -->
    <table class="daohang">
      <img class="head1" src="~@/assets/img/buyer/head.png" alt="" >	
      <tr>
          <td class="head2">{{ username }}</td>
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
            <h3 @click="navigateTo('buyerHistory')" class="head4-1" style="cursor: pointer;">历史购买记录</h3>
          </td>
      </tr>
      <tr>
          <td class="head4">
            <h3 @click="navigateTo('BuyerMain')" class="head4-1" style="cursor: pointer;">返回主页</h3>
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
        <div class="container">
        <router-link :to="{ name: 'BuyerMain'}">
            返回
        </router-link>
        <div class="centered-container">
          <h2>历史下单记录</h2>
        </div>
        <table style="border-collapse: collapse; width: 100%; box-shadow: 0 2px 4px rgba(0,0,0,0.1);" border="1" align="center" cellpadding="10">
        <tr>
          <th>ID</th>
          <th>地址</th>
          <th>电话</th>
          <th>购买人姓名</th>
          <th>商品ID</th>
          <th>操作</th>
          <th>订单状态</th>
        </tr>
        <tr v-for="order in paginatedOrders" :key="order.orderid">
          <td>{{ order.orderid }}</td>
          <td>{{ order.address }}</td>
          <td>{{ order.telephone }}</td>
          <td>{{ order.buyername }}</td>
          <td>{{ order.goodid }}</td>
          <td>
            <button v-if="order.orderstate === 4" class="green-btn" @click="handleOrderAction(order.orderid, 'confirmCompletion')"  style="background-color: transparent; color: #f44336; border: 2px solid #f44336; padding: 5px 10px; border-radius: 4px; cursor: pointer;">确认交易完成</button>
            <button v-if="order.orderstate >= 0 && order.orderstate < 4" class="red-btn" @click="handleOrderAction(order.orderid, 'cancel')" style="background-color: transparent; color: #f44336; border: 2px solid #f44336; padding: 5px 10px; border-radius: 4px; cursor: pointer;">取消订单</button>
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
import { mapGetters, mapActions } from 'vuex';

export default {
  props: ['userId'],
  data() {
    return {
      isLoggedIn: true, // 根据实际登录状态设置
      orders: [
        { orderid: 1, address: "地址1", telephone: "1234567890", buyername: "user1", goodid: 101, orderstate: 4 },
        { orderid: 2, address: "地址2", telephone: "1234567891", buyername: "user2", goodid: 102, orderstate: 3 },
        { orderid: 3, address: "地址3", telephone: "1234567892", buyername: "user3", goodid: 103, orderstate: 2 },
        { orderid: 4, address: "地址4", telephone: "1234567893", buyername: "user4", goodid: 104, orderstate: 1 },
        { orderid: 6, address: "地址6", telephone: "1234567895", buyername: "user6", goodid: 106, orderstate: -1 },
        { orderid: 7, address: "地址7", telephone: "1234567896", buyername: "user7", goodid: 107, orderstate: 5 },
        { orderid: 8, address: "地址8", telephone: "1234567897", buyername: "user8", goodid: 108, orderstate: 4 },
        // ... 添加更多假数据以超过一页...
      ],
      currentPage: 1,
      pageSize: 5,
      totalItems: 8, 
    };
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
      this.$router.push({ name: 'ShowAllUsers' }); // 假设 ShowUserInfo 是用户信息列表组件的路由名称
    },
    getOrderStatus(statusCode) {
      const statusMap = {
        '-1': '交易取消',
        '0': '等待客户下单',
        '1': '等待商家确认',
        '2': '等待备货确认',
        '3': '等待发货确认',
        '4': '商家已发货，等待交易确认',
        '5': '交易成功',
        // 添加其他状态映射
      };
      return statusMap[statusCode] || '未知状态';
    },
    handleOrderAction(orderId, action) {
      // 根据 action 类型处理不同的订单操作
      switch(action) {
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
        order.orderstate += 1; // 确认订单，状态加 1
        // 在这里发送请求到服务器以更新订单状态
      }
      alert("该订单阶段确认成功！");
      // 确认订单的逻辑
      // 发送请求到服务器，例如使用 axios 或 fetch
      console.log("确认订单", orderId);
      // 更新订单状态或重新获取订单数据
    },
    cancelOrder(orderId) {
      const order = this.orders.find(o => o.orderid === orderId);
      if (order) {
        order.orderstate = -1; // 取消订单，状态设置为 -1
        // 在这里发送请求到服务器以更新订单状态
      }
      alert("该订单已取消！");
      // 取消订单的逻辑
      // 发送请求到服务器，例如使用 axios 或 fetch
      console.log("取消订单", orderId);
      // 更新订单状态或重新获取订单数据
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
    fetchOrders() {
      // 实现从服务器获取订单数据的逻辑
      // ...
    }
  },
  computed: {
    ...mapGetters(['isSeller', 'isBuyer']),
    username() {
      // 从 Vuex store 获取用户名
      return this.$store.state.admin ? this.$store.state.admin.username : '未登录';
    },
    totalPages() {
      if(1>Math.ceil(this.orders.length / this.pageSize)) return 1;
      return Math.ceil(this.orders.length / this.pageSize);
    },
    paginatedOrders() {
      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      return this.orders.slice(start, end);
    },
    isSeller() {
      // 您可以根据实际情况判断用户是否是卖家
      return this.$store.getters.isSeller;
    },
    isPrevDisabled() {
      return this.currentPage === 1;
    },

    isNextDisabled() {
      return this.currentPage === this.totalPages;
    },
  },
  mounted() {
    this.fetchOrders();
    this.filteredUsers = this.orders;
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
  color: #f0f0f0; /* 可选：改变文字颜色 */
  cursor: pointer; /* 将鼠标指针改为手形，表示可点击 */
  transition: background-color 0.3s; /* 添加背景颜色过渡效果 */
}
*{
    background-color: #FFF9F1;
    
}
body {
    display: block;
    background-color: #FFF9F1 !important;
    background-image: none;
}
.left{
    /* 买家导航 */
    width: 287px;
    height:100vh;
    background-color: rgba(61, 61, 61, 0.33);
    position: relative;
    float: left;
    align-content: center;
}
.head1{
    background-color: rgba(61, 61, 61, 0);
    position: relative;
    top: 30px;
    left: 38px;
    z-index: 1;
}
.daohang{
    background-color: rgba(0, 0, 0, 0);
    width: 200px;/*格子宽度*/
    position: relative;
    left: 30px;
}
.head2{
    background-color: rgba(61, 61, 61, 0.33);
    text-align: center;
    vertical-align: top;
    font-size:36px;
    color: white;
    height: 100px;/*格子高度*/
    position: relative;
    z-index: 2;
}
.head4{
    background-color: rgba(61, 61, 61, 0.33);
    text-align: center;
    height: 100px;/*格子高度*/
}
.head4-1{
    background-color: rgba(61, 61, 61, 0);
    text-decoration: none;
    color: #ffffff;
    font-size:28px;
    font-weight: bold;
}
.head5{
    background-color: rgba(61, 61, 61, 0.33);
    text-align: center;
    height: 100px;/*格子高度*/
    bottom: 0;
    
}
.head5-1{
    background-color: rgba(61, 61, 61, 0);
    text-decoration: none;
    color: #585655;
    font-size:28px;
    font-weight: bold;
    border: none;
}

/* 商品 */
.right{
    /* 商品显示 
    width: 1340px;*/
    height: 100vh;
    /* background-color: aquamarine; */
    position: absolute;/*绝对定位*/
    left: 350px; 
    
    float: right;
}


.goods {
    display: flex; /*使用flex布局*/
    flex-wrap: wrap; /*允许元素换行*/
    justify-content: space-between; /*元素之间留有空隙*/
    border: 1px solid #000; /*添加边框*/
}

.show-1 {
    flex: 0 0 30%; /*每个元素占用30%的宽度，这样每行就可以放3个元素*/
    border: 1px solid #000; /*添加边框*/
    margin-bottom: 10px; /*添加底部边距*/
}

.picture {
    text-align: center; /*图片居中*/
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
    .prev, .next {
      background-color: rgb(237, 196, 110);
    }
form {
    display: flex; /* 让表单内的元素在同一行显示 */
       width:600px;
    height:45px;
	}
	input[type="text"] {
      flex-grow: 1; /* 让搜索框占据剩余的空间 */
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
    .media-container img, .media-container video {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        object-fit: contain;
    }
    .media-container img.active, .media-container video.active {
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
</style>
