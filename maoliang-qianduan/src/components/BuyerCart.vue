<template>

  <body style="margin: 0px;">
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

    <div class="right" style="width: 60%; margin: 0px;">
      <div class="container">
        <div class="centered-container">
          <h2>我的购物车</h2>
        </div>
        <button id="editall-button" @click="toggleEdit">{{ editButtonText }}</button>

        <table border="1px" align=center cellspacing="0" style="width:100%;">
          <tr>
            <th class="table-column">展示内容</th>
            <th class="table-column">名称</th>
            <th class="table-column">购买数量</th>
            <th class="table-column">小计</th>
            <th class="table-column">操作</th>
            <th class="table-column">选择</th>
          </tr>
          <tr v-for="item in visibleItems" :key="item.id">

            <td class="table-column">
              <div style="display: flex; justify-content: center; align-items: center;">
                <div style="text-align: center;">
                  <div class="media-container" :class="{ 'sold-out': item.state !== 0 }">
                    <div v-show="item.MEDIAFILES !== ''">
                      <img :src="item.MEDIAFILES" alt="商品图片">
                    </div>
                  </div>
                  <!-- <div class="media-navigation">
                    <button @click="showPrevMedia(item)">＜</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <button @click="showNextMedia(item)">＞</button>
                  </div> -->
                  <div v-if="item.state !== 0" class="overlay">商品不可购买</div>
                </div>
              </div>
            </td>
            <td class="table-column">
              <div class="centered-container">{{ item.NAME }}</div>
            </td>
            <td class="table-column">
              <div class="centered-container">
                <button v-if="isEditing" @click="decrementQuantity(item)">-</button>
                <input type="number" v-model.number="item.quantity" :disabled="!isEditing" style="width:64.6px;">
                <button v-if="isEditing" @click="incrementQuantity(item)">+</button>
              </div>
            </td>
            <td class="table-column">
              <div class="centered-container">{{ item.PRICE * item.quantity }}</div>
            </td>
            <td class="table-column">
              <div class="centered-container">
                <button @click="addToFavorites(item.ID, 0)">收藏</button>
                <button v-if="isEditing" @click="removeFromCart(item.ID)">删除</button>
              </div>
            </td>
            <td class="table-column">
              <div class="centered-container">
                <input type="checkbox" v-model="selectedItems[item.id]">
              </div>
            </td>
          </tr>
        </table>
        <div>
          <h3>总计: {{ selectedTotalPrice }}</h3>
          <button @click="submitSelectedOrders">提交选中的订单</button>
          <button @click="addToFavoritesSelectedItems">一键收藏选中商品</button>
        </div>
        <div class="pagination">
          <button @click="goToPrevPage" :disabled="isPrevDisabled" class="prev">上一页</button>
          <span>{{ currentPage }} / {{ totalPages }}</span>
          <button @click="goToNextPage" :disabled="isNextDisabled" class="next">下一页</button>
        </div>
      </div>
    </div>
  </div>
  <div v-else class="else">
    您还未登录，请先<a href="login">登录</a>
  </div>
  </body>
</template>

<script>
import axios from 'axios';
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
import { mapGetters, mapActions } from 'vuex';
export default {
  data() {
    return {
      items: [
        // ...其他购物车商品
      ],
      favorites: [], // 初始化空的收藏数组
      searchQuery: '',
      selectedCategory: '猫咪主粮', // 设置初始值为“猫咪主粮”
      search: {
        keyword: '',
        kind: '猫咪主粮'
      },
      isLoggedIn: true, // 应根据实际逻辑设置
      selectedItems: {}, // 选中的商品ID
      currentPage: 1,
      itemsPerPage: 2,
      isEditing: true,
      currentUser: null
    };
  },
  computed: {
    ...mapGetters(['isLoggedIn', 'isSeller', 'isBuyer']),
    visibleItems() {
      const start = (this.currentPage - 1) * this.itemsPerPage;
      const end = start + this.itemsPerPage;
      return this.items.slice(start, end);
    },
    getUsername() {
      // 如果当前用户数据不为空，则返回用户名；否则返回未登录
      return this.currentUser ? this.currentUser.username : '未登录';
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
    selectedTotalPrice() {
      return Object.keys(this.selectedItems).reduce((total, itemId) => {
        const item = this.cartItems.find(item => item.ID === Number(itemId));
        if (item && this.selectedItems[itemId]) {
          return total + item.PRICE * item.quantity;
        }
        return total;
      }, 0);
    },
    editButtonText() {
      return this.isEditing ? '查看' : '编辑';
    },
  },
  created() {
    this.cartItems = this.items;
    this.filteredItems = this.cartItems; // 确保 filteredItems 已被初始化
    this.fetchCartItems(); // 当组件创建时获取购物车数据
    this.fetchUsrFromSession();
  },
  methods: {
    ...mapActions(['logout']),
    isItemVisible(index) {
      const start = (this.currentPage - 1) * this.itemsPerPage;
      const end = start + this.itemsPerPage;
      return index >= start && index < end;
    },
    async fetchUsrFromSession() {
      try {
        // 发起 GET 请求到后端接口
        const response = await axios.get('/now-usr');

        // 解析响应数据
        const usr = response.data;

        // 更新组件的 currentUser 数据
        this.currentUser = usr;
        this.getLIst(this.currentUser.userid)
        return true;
      } catch (error) {
        console.error('获取用户数据错误:', error);
        return false;
      }
    },
    getLIst(userid) {
      // 调用接口，获取详细信息
      axios.get('/cart/allCart?userId=' + userid).then(res => {
        // 处理返回的详细信息
        this.items = res.data;
        // this.isEditing = res.data.MAXQUANTITY > 0 ? true : false;
      }).catch(error => {
        // 处理请求错误
        console.error(error);
      });
    },
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
    async fetchCartItems() {
      try {
        // 发起 GET 请求获取商品列表
        const goodsResponse = await axios.get('/cart/allCart');
        // 解析响应数据
        // console.log('goodList:', goodsResponse);
        const goodList = goodsResponse.data.data;//goodsResponse的数据的data属性
        // 将商品列表添加到 products 中
        // 解析picture属性并添加mediaFiles属性
        //  console.log('goodList:', goodList);

        this.goods = goodList.map( good => {
          //    console.log('Before trimming:', good.picture); // 添加调试语句
          const trimmedPicture = good.picture.trim();
          //     console.log('After trimming:', trimmedPicture); // 添加调试语句
          const paths = trimmedPicture.split(',');
          const mediaFiles = paths.map((path, i) => {
            return {
              url: path,
              isActive: i === 0 // 默认第一个是true，其他是false
            };
          });
          return {
            ...good,
            mediaFiles,
            // 保留原始属性
            goodname: good.goodname.trim(),
            description: good.description.trim(),
            price: good.price,
            number: good.number,
            kind: good.kind,
            subkind: good.subkind
          };
        });
        console.log('this.goods:', this.goods);
        return true;
      } catch (error) {
        console.error('获取商品列表数据错误:', error);
        return false;
      }
    },
    toggleEdit() {
      this.isEditing = !this.isEditing;
      if (!this.isEditing) {
        this.updateCart(); // 更新购物车
      }
    },
    incrementQuantity(item) {
      if (item.quantity < item.MAXQUANTITY) {
        item.quantity++;
      }
    },
    decrementQuantity(item) {
      if (item.quantity > 1) {
        item.quantity--;
      }
    },
    removeFromCart(itemId) {
      // 调用接口，获取详细信息
      axios.post('/cart/delete-cart-item', { buyingid: itemId, userid: this.currentUser.userid })
          .then(res => {
            // 处理返回的详细信息
            this.items = res.data;
            this.getLIst(this.currentUser.userid)
            alert("选中的订单已删除！");
          })
          .catch(error => {
            // 处理请求错误
            console.error(error);
          });
    },
    addToFavorites(itemId, iscancel) {
      // 调用接口，获取详细信息
      axios.post('/cart/addLike', { goodid: itemId, iscancel: iscancel, userid: this.currentUser.userid }).then(res => {
        alert("商品已添加到收藏夹！");
        console.log(res)
        this.getLIst(this.currentUser.userid)
        // 处理返回的详细信息
      }).catch(error => {
        // 处理请求错误
        console.error(error);
      });
    },
    submitSelectedOrders() {
      this.items = this.items.filter(item => !this.selectedItems[item.ID]);
      this.selectedItems = {};
      alert("选中的订单已提交！");
    },
    addToFavoritesSelectedItems() {
      // 收藏选中的商品
      alert("选中的商品已收藏！");
      // 重置选中状态
      this.selectedItems = {};
    },
    changePage(newPage) {
      this.currentPage = newPage;
    },
    updateCart() {
      // 更新购物车数据到服务器
    },
    showPrevMedia(item) {
      const currentIndex = 0;
      console.log('Current active index:', currentIndex);

      if (currentIndex >= 0) {
        const prevIndex = (currentIndex - 1 + item.MEDIAFILES.length) % item.MEDIAFILES.length;
        console.log('Previous index:', prevIndex);
        this.setActiveMedia(item, prevIndex);
      }
    },
    showNextMedia(item) {
      const currentIndex = 0;
      console.log('Current active index:', currentIndex);

      if (currentIndex >= 0) {
        const nextIndex = (currentIndex + 1) % item.MEDIAFILES.length;
        console.log('Next index:', nextIndex);
        this.setActiveMedia(item, nextIndex);
      }
    },
    setActiveMedia(item, index) {
      item.mediaFiles.forEach((media, idx) => {
        media.isActive = idx === index;
        console.log(`Media at index ${idx} active status: `, media.isActive);
      });
    },
    goToPrevPage() {
      // 实现翻页逻辑
      if (this.currentPage > 1) {
        this.currentPage--;
        console.log("Current page after prev:", this.currentPage);
      }
    },
    goToNextPage() {
      // 实现翻页逻辑
      if (this.currentPage < this.totalPages) {
        this.currentPage++;
        console.log("Current page after next:", this.currentPage);
      }
    },
    // 每次搜索结果变化后重置当前页码
    resetPage() {
      this.currentPage = 1;
    },
  },

  watch: {
    // 监视搜索结果的变化
    filteredItems(newValue, oldValue) {
      if (newValue !== oldValue) {
        this.resetPage();
      }
    }
  },
  mounted() {
    // 示例：假设从后端获取成功消息
    // 在实际应用中，您可能需要在某个操作成功后调用 showSuccessModal
    this.filteredItems = this.items; // 初始时显示所有商品
  },
}
</script>

<style scoped>
/* .table-column {
    width: 153.6px;
    text-align: center;
} */
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
</style>
