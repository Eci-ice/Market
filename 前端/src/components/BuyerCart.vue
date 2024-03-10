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
                <div v-for="(media, index) in item.mediaFiles" :key="index" v-show="media.isActive">
                    <img v-if="!media.isVideo" :src="media.url" alt="商品图片" v-show="media.isActive">
                    <video v-if="media.isVideo" :src="media.url" controls v-show="media.isActive"></video>
                </div>
          </div>
          <div class="media-navigation">
              <button @click="showPrevMedia(item)">＜</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <button @click="showNextMedia(item)">＞</button>
          </div>
            <div v-if="item.state !== 0" class="overlay">商品不可购买</div>
        </div>
        </div>
        </td>

          <td class="table-column"><div class="centered-container">{{ item.name }}</div></td>
          <td class="table-column">
            <div class="centered-container">
            <button v-if="isEditing" @click="decrementQuantity(item)">-</button>
            <input type="number" v-model.number="item.quantity" :disabled="!isEditing" style="width:64.6px;">
            <button v-if="isEditing" @click="incrementQuantity(item)">+</button>
            </div>
          </td>
          <td class="table-column"><div class="centered-container">{{ item.price * item.quantity }}</div></td>
          <td class="table-column">
            <div class="centered-container">
            <button @click="addToFavorites(item.id)">收藏</button>
            <button v-if="isEditing" @click="removeFromCart(item.id)">删除</button>
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
        <h3>总计: {{ selectedTotalPrice  }}</h3>
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
import { mapGetters, mapActions } from 'vuex';
export default {
  data() {
    return {
        items: [
        {
          id: 1,
          name: "猫咪粮食",
          price: 99,
          quantity: 2,
          maxQuantity: 10,
          state: 0,
          mediaFiles: [
            { url: require('@/assets/img/buyer/food-1.jpg'), isActive: true, isVideo: false },
            { url: require('@/assets/img/buyer/food-2.jpg'), isActive: false, isVideo: false },
          ]
        },
        {
          id: 2,
          name: "猫咪玩具",
          price: 49,
          quantity: 3,
          maxQuantity: 15,
          state: 0,
          mediaFiles: [
            { url: require('@/assets/img/buyer/food-2.jpg'), isActive: true, isVideo: false },
            { url: require('@/assets/img/buyer/food-1.jpg'), isActive: false, isVideo: false },
          ]
        },
        {
          id: 3,
          name: "猫咪窝",
          price: 299,
          quantity: 1,
          maxQuantity: 5,
          state: 0,
          mediaFiles: [
            { url: require('@/assets/img/buyer/food-1.jpg'), isActive: true, isVideo: false },
            { url: require('@/assets/img/buyer/food-2.jpg'), isActive: false, isVideo: false },
          ]
        }
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
      isEditing: false,
    };
  },
  computed: {
    ...mapGetters(['isLoggedIn', 'isSeller', 'isBuyer']),
    visibleItems() {
      const start = (this.currentPage - 1) * this.itemsPerPage;
      const end = start + this.itemsPerPage;
      return this.items.slice(start, end);
    },
    username() {
      // 从 Vuex store 获取用户名
      return this.$store.state.admin ? this.$store.state.admin.username : '未登录';
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
        const item = this.cartItems.find(item => item.id === Number(itemId));
        if (item && this.selectedItems[itemId]) {
            return total + item.price * item.quantity;
        }
        return total;
        }, 0);
    },
    editButtonText() {
      return this.isEditing ? '查看' : '编辑';
    },
  },
  methods: {
    ...mapActions(['logout']),
    isItemVisible(index) {
      const start = (this.currentPage - 1) * this.itemsPerPage;
      const end = start + this.itemsPerPage;
      return index >= start && index < end;
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
    fetchCartItems() {
      // AJAX 请求获取购物车数据
      // this.cartItems = fetch('/api/cart').then(response => response.json());
    },
    toggleEdit() {
      this.isEditing = !this.isEditing;
      if (!this.isEditing) {
        this.updateCart(); // 更新购物车
      }
    },
    incrementQuantity(item) {
      if (item.quantity < item.maxQuantity) {
        item.quantity++;
      }
    },
    decrementQuantity(item) {
      if (item.quantity > 1) {
        item.quantity--;
      }
    },
    removeFromCart(itemId) {
      // 删除购物车中的商品
      // 过滤掉要删除的商品
      this.items = this.items.filter(item => item.id !== itemId);

      // TODO: 如果有后端API，这里可以发送请求来更新服务器上的数据
      alert("选中的订单已删除！");
    },
    addToFavorites(itemId) {
      // 添加商品到收藏夹
      // 找到要添加到收藏夹的商品
      const favoriteItem = this.items.find(item => item.id === itemId);

      if (favoriteItem) {
        // 假设您有一个存储收藏商品的数组
        // 检查是否已经在收藏夹中
        if (!this.favorites.some(item => item.id === itemId)) {
          this.favorites.push(favoriteItem);
          alert("商品已添加到收藏夹！");
        } else {
          alert("商品已在收藏夹中！");
        }
      }
    },
    submitSelectedOrders() {
        this.items = this.items.filter(item => !this.selectedItems[item.id]);
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
        const currentIndex = item.mediaFiles.findIndex(media => media.isActive);
        console.log('Current active index:', currentIndex);

        if (currentIndex >= 0) {
        const prevIndex = (currentIndex - 1 + item.mediaFiles.length) % item.mediaFiles.length;
        console.log('Previous index:', prevIndex);
        this.setActiveMedia(item, prevIndex);
        }
    },
    showNextMedia(item) {
        const currentIndex = item.mediaFiles.findIndex(media => media.isActive);
        console.log('Current active index:', currentIndex);

        if (currentIndex >= 0) {
        const nextIndex = (currentIndex + 1) % item.mediaFiles.length;
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
  created() {
    this.cartItems = this.items;
    this.filteredItems = this.cartItems; // 确保 filteredItems 已被初始化
    this.fetchCartItems(); // 当组件创建时获取购物车数据
    this.fetchCartItems(); // 当组件创建时获取购物车数据
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
