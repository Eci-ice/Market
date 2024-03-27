<template>
  <div style="margin: 0px;">
    <div v-if="isLoggedIn">
      <div class="container">
        <router-link :to="{ name: 'ShowAllUsers' }">返回</router-link>
        <div class="centered-container">
          <h2>用户 {{ userId }} 的购买历史</h2>
        </div>
        <table style="border-collapse: collapse; width: 80%; box-shadow: 0 2px 4px rgba(0,0,0,0.1);" border="1"
          align="center" cellpadding="10">
          <tr>
            <th>ID</th>
            <th>地址</th>
            <th>电话</th>
            <th>购买人姓名</th>
            <th>商品ID</th>
            <th>订单状态</th>
          </tr>
          <tr v-for="order in paginatedOrders" :key="order.orderid">
            <td>{{ order.orderid }}</td>
            <td>{{ order.address }}</td>
            <td>{{ order.telephone }}</td>
            <td>{{ order.buyername }}</td>
            <td>{{ order.goodid }}</td>
            <td>{{ getOrderStatus(order.orderstate) }}</td>
          </tr>
        </table>
        <div class="pagination">
          <button @click="goToPrevPage" :disabled="isPrevDisabled">上一页</button>
          <span>{{ currentPage }} / {{ totalPages }}</span>
          <button @click="goToNextPage" :disabled="isNextDisabled">下一页</button>
        </div>
      </div>
    </div>
    <div v-else>
      <span>您还未登录，请先<a href="/login">登录</a></span>
    </div>
  </div>
</template>
<script>
import { mapGetters } from 'vuex';
import axios from 'axios';

export default {
  props: ['userId'],
  data() {
    return {
      isLoggedIn: true, // 应从 Vuex 或父组件动态获取
      orders: [],
      currentPage: 1,
      pageSize: 5,
      currentUser: {}, // 将 currentUser 初始化为一个空对象
    };
  },
  computed: {
    ...mapGetters(['isSeller', 'isBuyer']), // 确保这些 getter 在 Vuex store 中定义
    totalPages() {
      return Math.max(1, Math.ceil(this.orders.length / this.pageSize));
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
      return this.currentPage >= this.totalPages;
    },
  },
  methods: {
    async fetchUsrFromSession() {
      try {
        const response = await axios.get('/now-usr');
        this.currentUser = response.data;
        if (this.currentUser) {
          this.isLoggedIn = true;
        }
      } catch (error) {
        console.error('获取用户数据错误:', error);
        this.isLoggedIn = false;
      }
    },
    async fetchOrders() {
      try {
        // 在获取订单前先获取当前用户信息
        await this.fetchUsrFromSession();

        // 获取当前用户权限
        const power = this.currentUser.power;

        // 根据用户权限和用户ID获取订单列表
        const response = await axios.get('/usr/UserOrderHistory-control', { params: { power: power, userid:this.uer} });

        if (response.data) {
          this.orders = response.data.data || [];
          console.log("获取用户历史数据列表成功")
        } else {
          console.error("获取用户历史数据列表失败");
        }
      } catch (error) {
        console.error('请求用户历史数据列表错误', error);
      }
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
      };
      return statusMap[statusCode] || '未知状态';
    },
    goToPrevPage() {
      if (this.currentPage > 1) this.currentPage--;
    },
    goToNextPage() {
      if (this.currentPage < this.totalPages) this.currentPage++;
    },
  },
  mounted() {
    // 在组件挂载时获取订单数据
    this.fetchOrders();
  },
};
</script>


<style scoped>
.centered-container {
  text-align: center;
}

* {
  /* background-color: #FFF9F1; */

}

body {
  display: block;
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
  left: 70px;
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
</style>