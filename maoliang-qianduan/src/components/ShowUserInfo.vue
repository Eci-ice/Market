<template>
  <div v-if="isLoggedIn">
    <div id="a">
    <div class="container">
      <div class="form-group">
        <h2>当前意向订单</h2>
      </div>
      <table border="1px" align=center cellspacing="0">
        <tr>
          <th>ID</th>
          <th>地址</th>
          <th>电话</th>
          <th>购买人姓名</th>
          <th>商品ID</th>
          <th>操作</th>
          <th>订单状态</th>
        </tr>
        <tr v-for="(order, index) in paginatedItems" :key="index">
          <td>{{ order.orderid }}</td>
          <td>{{ order.address }}</td>
          <td>{{ order.telephone }}</td>
          <td>{{ order.buyername }}</td>
          <td>{{ order.goodid }}</td>
          <td>
            <button v-if="order.orderstate < 4 && order.orderstate > 0" @click="confirmOrder(order.orderid)" class="green-btn">确认完成</button>
            <button v-if=" order.orderstate <= 4 && order.orderstate > 0" @click="cancelOrder(order.orderid)" class="red-btn">取消订单</button>
            <span v-if="order.orderstate < 0 || order.orderstate > 4">无法操作订单</span>
          </td>
          <td>
            {{ getOrderStatus(order.orderstate) }}
          </td>
        </tr>
      </table>
      <div class="pagination">
        <button @click="goToPrevPage" :disabled="isPrevDisabled" class="prev">上一页</button>
        <span>第{{ currentPage }}/{{ totalPages }}页</span>
        <button @click="goToNextPage" :disabled="isNextDisabled" class="next">下一页</button>
      </div>
    </div>
    </div>
  </div>
  <div v-else class="else">
    您还未登录，请先<a href="login">登录</a>
  </div>
</template>

<script>
export default {
  data() {
    return {
      isLoggedIn: true, // 应根据实际登录逻辑设置
      orders: [
        { orderid: 1, address: '地址1', telephone: '1234567890', buyername: '买家A', goodid: 101, orderstate: -1 },
        { orderid: 2, address: '地址2', telephone: '0987654321', buyername: '买家B', goodid: 102, orderstate: 1 },
        { orderid: 3, address: '地址3', telephone: '1122334455', buyername: '买家C', goodid: 103, orderstate: 2 },
        { orderid: 4, address: '地址4', telephone: '5566778899', buyername: '买家D', goodid: 104, orderstate: 3 },
        { orderid: 5, address: '地址5', telephone: '1231231234', buyername: '买家E', goodid: 105, orderstate: 4 },
        { orderid: 6, address: '地址6', telephone: '3344556677', buyername: '买家F', goodid: 106, orderstate: 5 },
        // ... 更多数据
      ],
      currentPage: 1,
      itemsPerPage: 5,
      // 假设 totalItems 由后端提供或计算得出
      totalItems: 6, 
    };
  },
  methods: {
    getOrderStatus(state) {
      // 根据状态返回订单状态描述
      // 实际逻辑可能需要调整
      const statusMap = {
        0: '等待客户下单',
        1: '客户已确认，商家待确认',
        2: '商家已确认，等待商家备货',
        3: '备货已完成，等待商家发货',
        4: '发货已完成，等待买家确认交易完成',
        5: '交易已完成', // 假设状态 5 表示交易已完成
        // 根据需要添加更多状态
      };
      return statusMap[state] || '订单被取消';
    },
    handleOrderAction(orderId, action) {
      // 根据 action 类型处理不同的订单操作
      switch(action) {
        case 'wait':
          // 等待买家确认的逻辑
          break;
        case 'confirm':
          this.confirmOrder(orderId);
          break;
        case 'prepare':
          // 确认备货的逻辑
          break;
        case 'ship':
          // 确认发货的逻辑
          break;
        case 'cancel':
          this.cancelOrder(orderId);
          break;
        // 其他情况...
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
    totalPages() {
      return Math.ceil(this.totalItems / this.itemsPerPage);
    },
    isPrevDisabled() {
      return this.currentPage <= 1;
    },
    isNextDisabled() {
      return this.currentPage >= this.totalPages;
    },
    filteredOrders() {
      // 这里过滤掉状态为 -1 的订单，即已取消的订单
      return this.orders.filter(order => order.orderstate !== -2);
    },
    paginatedItems() {
      const start = (this.currentPage - 1) * this.itemsPerPage;
      const end = this.currentPage * this.itemsPerPage;
      return this.orders.slice(start, end);
    },
  },
  created() {
    this.fetchOrders(); // 初始加载订单数据
  },
}
</script>

<!-- 未登录框 -->
<style type="text/css" scoped>
.form-group {
  display: flex;
  justify-content: center; /* 水平居中 */
  align-items: center; /* 如果需要垂直居中也可以添加 */
  /* 其他样式... */
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
.container {
        font-family: Arial, sans-serif;
        width: 80%;
        margin: 2% auto;
        border: 1px solid #ccc;
        padding: 20px;
        box-shadow: 0 0 10px rgba(0,0,0,0.1);
    }

    table {
        width: 100%;
        border-collapse: collapse;
        margin-bottom: 20px;
    }

    th, td {
        border: 1px solid #ccc;
        padding: 10px;
        text-align: left;
    }

    th {
        background-color: #f2f2f2;
    }

    img {
        width: 50px;
        height: auto;
    }

    .pagination {
        display: flex;
        justify-content: center;
        align-items: center;
        margin-bottom: 20px;
    }

    .history-btn, .prev, .next {
        padding: 10px 20px;
        color: #fff;
        border: none;
        cursor: pointer;
        margin: 0 5px;
    }
    
    .history-btn {
      background-color: rgb(237, 137, 108);
      border-radius: 8px;
    }
    
    .prev, .next {
      background-color: rgb(237, 196, 110);
    }

    .history-btn:hover, .prev:hover, .next:hover {
        background-color: #d32f2f;
    }
    
    .history-btn a {
        text-decoration: none;
        color: white;
    }
    
    .history-btn a:hover {
        text-decoration: none; 
    }
    .left-btn-container {
      margin-right: auto;
      display: flex;
      align-items: center;
	}
    button {
            padding: 5px 10px;
            margin: 5px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
	.green-btn {
            background-color: green;
        }

    .red-btn {
            background-color: tomato;
     }
</style>
