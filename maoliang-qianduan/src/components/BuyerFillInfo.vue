<template>
  <body style="background-color: #FFF9F1; ">
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
    <hr class="cline" color=#BBBBBB width=1 size=900>
    <div class="middle">
      <a href="/#/buyer">
        <img src="~@/assets/img/buyer/arrow.png" alt="" width="40" title="返回商品界面">
      </a>
    
      <table class="good-2">
        <tr>
          <td colspan="2" class="title">您选择的商品</td>
        </tr>
        <tr>
            <td colspan="2" class="goodname">{{ selectedProduct.goodname }}</td>
        </tr>
        <tr>
            <td colspan="2" class="description"><br/>{{ selectedProduct.description }}</td>
        </tr>
        <tr>
            <td class="price">总价：</td>
            <td class="price">{{ selectedProduct.price }}</td>
        </tr>
        <tr>
            <td class="price">剩余库存量：</td>
            <td class="price">{{ selectedProduct.number }}</td>
        </tr>

      </table>

      <button @click="cancelPurchase"  class="butt-2">取消购买</button>
    </div>

    <div class="right">
      <div class="title">您的购买信息</div>
      <form @submit.prevent="confirmPurchase" class="buy-imf">

        <label class="username">

        <div class="form-group">
          <label>购买数量：</label>
          <input class="number-lable" type="number" v-model.number="purchase.number" min="1" :max="selectedProduct.number" required style="width:200px;height:66.4px;">
        </div>

        <div class="form-group">
          <label>姓名：</label>
          <input type="text" v-model="purchase.buyerName" required>
        </div><br>

        <div class="form-group">
          <label>电话：</label>
          <input type="text" v-model="purchase.telephone" required>
        </div><br>

        <div class="form-group">
          <label>交易地点：</label>
          <input type="text" v-model="purchase.address" required>
        </div><br>
        </label>
        <button type="submit" class="butt-2">确认购买</button>
      </form>
    </div>
  </body>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      selectedProduct: {},
      currentUser:null,
      purchase: {
        quantity: 1,
        buyerName: 'guest', // 设置默认姓名
        telephone: '12345678901', // 设置默认电话号码
        address: '`123`' // 设置默认地址
      }
    };
  },
  created() {
    this.fetchGoodFromSession();
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
    paginatedItems() {
      // 计算当前页的商品
      const start = (this.currentPage - 1) * this.itemsPerPage;
      const end = this.currentPage * this.itemsPerPage;
      return this.filteredItems.slice(start, end);
    },
    // 计算总页数
    totalPages() {
      return Math.ceil(this.filteredItems.length / this.itemsPerPage);
    },
    getUsername() {
      // 如果当前用户数据不为空，则返回用户名；否则返回未登录
      return this.currentUser ? this.currentUser.username : '未登录';
    },
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
        if (this.currentUser) {
          this.purchase.buyerName = this.currentUser.username || 'guest';
          this.purchase.telephone = this.currentUser.phone || '12345678901';
          this.purchase.address = this.currentUser.address || '123';
        }
        return true;
      } catch (error) {
        console.error('获取用户数据错误:', error);
        return false;
      }
    },
    async fetchGoodFromSession() {
      try {
        // 发起 GET 请求到后端接口
        const response = await axios.get('/now-good');

        // 解析响应数据
        const good = response.data;

        const trimmedPicture = good.picture.trim();
        const paths = trimmedPicture.split(',');
        const mediaFiles = paths.map((path, i) => {
          return {
            url: path,
            isActive: i === 0 // 默认第一个是true，其他是false
          };
        });

        this.selectedProduct = {
          ...good,
          mediaFiles,
          // 保留原始属性
          goodid: good.goodid,
          goodname: good.goodname.trim(),
          description: good.description.trim(),
          price: good.price,
          number: good.number,
          kind: good.kind,
          subkind: good.subkind,
          calorie: good.calorie,
          catkind: good.catkind,
          catweight: good.catweight,
          catage: good.catage,
          buyingid: good.buyingid,
          numbermax: good.numbermax,
          islike: good.islike
        };
        console.log('this.selectedProduct:', this.selectedProduct);
        console.log('this.selectedProduct.goodname:', this.selectedProduct.goodname);
        return true;
      } catch (error) {
        console.error('获取用户数据错误:', error);
        return false;
      }
    },
    cancelPurchase() {
      // 处理取消购买逻辑
      this.$router.push('/buyer');
    },
    async confirmPurchase() {
      // 验证表单数据
      if (this.validateForm()) {

        const orderData = {
          address: this.purchase.address,
          telephone: this.purchase.telephone,
          buyername: this.purchase.buyerName,
          goodid: this.selectedProduct.goodid,
          number: this.purchase.number
        };

// 发起 POST 请求
        const response = await fetch('/order/create-order', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json' // 指定请求体类型为 JSON
          },
          body: JSON.stringify(orderData) // 将 JavaScript 对象转换为 JSON 字符串作为请求体
        });

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
        alert('成功创建订单！');
        this.$router.push('/buyer');
      }
    },
    validateForm() {
      const phoneRegex = /^[0-9]{11}$/;
      if (!phoneRegex.test(this.purchase.telephone)) {
        alert("电话号码需要是11位数字！");
        return false;
      }
      if (this.purchase.buyerName.length > 10) {
        alert("用户名不能超过10个字符！");
        return false;
      }
      if (this.purchase.address.length > 99) {
        alert("地址不能超过99个字符！");
        return false;
      }
      return true;
    }
  }
};
</script>

<style scoped>
*{
    background-color: #FFF9F1; 

}
.left{
  /* 买家导航 */
  width: 287px;
  background-color: rgba(61, 61, 61, 0.33);
  position: relative;
  float: left;
  align-content: center;
  overflow-x: hidden;
  overflow-y: hidden;
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

.cline{
    /* 竖线 */
    position: absolute;
    top: 30px;
    left: 810px;
}
.good-2{
  margin-left: 400px;
}
.goodname{
    height: 100px;/*格子高度*/
    font-size:60px;
}
.price{
    font-size:24px;
    width: 200px;
    height: 50px;
}
.description{
	font-size: 30px;
}

.title{
  font-size: 25px;
  color: #EBC16B;
  font-weight: bold;
}
.number-lable{
  margin-bottom: 30px;
  background: white;
}
.butt-1{
    /* 取消购买按钮 */
    position: relative;
    top: 100px ;
    left: 200px;
    background-color: orange;
    font-size: 30px;
}
button {
            padding: 5px 10px;
            height:60px;
            margin: 5px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
  }

.right{
    /* 右侧表单 */
    position: absolute;
    top: 70px;
    left: 890px;
    font-size: 36px;
}

input[type="text"]{
    width: 200px;
    appearance: none;
    /* border: none; */
    border-color: #cccccc;
    outline: none;
    background-color: #FFFFFF;
    padding: 20px;
    font-size: 20px;
    color: #888888;

}
.butt-2{
    position: relative;
    top: 160px;
    left: 150px;
    background-color: #EBC16B;
    color: #fff;
    border: none;
    border-radius: 20px;/*设置了圆角和内边距 */
    padding:25px 55px;
    font-size: 24px;
    cursor: pointer;
    text-decoration: none;
    /* 最后使用cursor属性将光标变成手型，增强按钮的交互性。 */
}
/*.buy-btn{*/
/*    position: relative;*/
/*    !* top: 5px; *!*/
/*    top: 160px;*/
/*    left: 150px;*/
/*    background-color: #EBC16B;*/
/*    color: #fff;*/
/*    border: none;*/
/*    border-radius: 20px;!*设置了圆角和内边距 *!*/
/*    padding:25px 55px;*/
/*    font-size: 30px;*/
/*    cursor: pointer;*/
/*}*/
body {
    display: block;
    background-color: #FFF9F1 !important;
    background-image: none;
    height: 1000px;
}
</style>
