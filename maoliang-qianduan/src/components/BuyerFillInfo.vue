<template>
  <body style="background-color: #FFF9F1; ">
    <hr class="cline" color=#BBBBBB width=1 size=900>
    <div class="left">
      <a href="/buyer-shop">
        <img src="~@/assets/img/buyer/arrow.png" alt="" width="40" title="返回商品界面">
      </a>
    
      <table class="good-2">
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
      <form @submit.prevent="confirmPurchase" class="buy-imf">
        <label class="username">
        <div class="form-group">
          <label>购买数量：</label>
          <input type="number" v-model.number="purchase.number" min="1" :max="selectedProduct.number" required style="width:200px;height:66.4px;">
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
      purchase: {
        quantity: 1,
        buyerName: 'buyer', // 设置默认姓名
        telephone: '12345678901', // 设置默认电话号码
        address: '`123`' // 设置默认地址
      }
    };
  },
  created() {
    this.fetchGoodFromSession();
    this.fetchUsrFromSession();
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
    confirmPurchase() {
      // 验证表单数据
      if (this.validateForm()) {
        // 发送购买请求
        // 例如：this.$axios.post('/api/purchase', this.purchase)
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
/* .goodname{
    text-decoration: none;
    color: rgba(0, 0, 0,1);
    font-size: 60px;
    font-family: "微软雅黑";
}
.price,.state{color: #000;font-size: 30px;} */
.cline{
    /* 竖线 */
    position: absolute;
    top: 30px;
    left: 810px;
}
.good-1{
    position: absolute;
    top: 100px;
    left: 100px;
    border-style: none;
    /* background-color: rgba(121, 115, 115, 0.5); */
    width: 700px;/*格子宽度*/
    height: 600px;/*格子高度*/
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

.buy-imf{
    /* 右侧表单 */
    position: absolute;
    top: 140px;
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
