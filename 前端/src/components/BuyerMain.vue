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
            <h3 @click="navigateTo('buyerHistory')" class="head4-1" style="cursor: pointer;">历史购买记录</h3>
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
      <div class="right">
        <div class="centered-container">
          <h2>欢迎来到猫咪美食坊！</h2>
        </div>
        <div class="centered-container">
          <form @submit.prevent="handleSearch">
            <input type="text" v-model="searchQuery" class="custom-input" placeholder="搜索商品">&nbsp;&nbsp;&nbsp;
            <select v-model="selectedCategory">
                <option value="猫咪主粮">猫咪主粮</option>
                <option value="猫咪零食">猫咪零食</option>
                <option value="猫咪日用">猫咪日用</option>
              </select><br><br>&nbsp;&nbsp;&nbsp;
            <input type="submit" value="搜索" style="width:130px;cursor: pointer;">
          </form>
          <div id="search_list"></div>
        </div><br>
          <div class="goods">
            <div class="show-1" v-for="item in paginatedItems" :key="item.id">
              <div class="media-container">
                  <div v-for="(media, index) in item.mediaFiles" :key="index" v-show="media.isActive">
                  <img v-if="!media.isVideo" :src="media.url" alt="商品图片" v-show="media.isActive">
                  <video v-if="media.isVideo" :src="media.url" controls v-show="media.isActive"></video>
                  </div>
              </div>
              <div class="media-navigation">
                  <button @click="showPrevMedia(item)">＜</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <button @click="showNextMedia(item)">＞</button>
              </div>
              <a href="/buyer-shop">
                <p>商品名：{{ item.name }}<br>超值价：{{ item.price }}元</p>
              </a>
            </div>
          </div>
          <div class="pagination">
            <button @click="goToPrevPage" class="prev" :disabled="currentPage === 1">上一页</button>
            <span id="page-info">第 {{ currentPage }} / {{ totalPages }} 页</span>
            <button @click="goToNextPage" class="next" :disabled="currentPage === totalPages">下一页</button>
          </div>
      </div>
    
  </div>
   <div v-else>
    <!-- 用户未登录时显示的内容 -->
    <div class="else">
      <div class="left" >
    <!-- 页面头部 -->
    <table class="daohang">
      <img class="head1" src="~@/assets/img/buyer/head.png" alt="" >	
      <tr>
          <td class="head2">游客</td>
      </tr>
      <tr>
          <td class="head4">
            <h3 class="head4-1">其他功能后登录使用</h3>
          </td>
      </tr>
      <tr>
        <td class="head5">
          <button @click="handleLogout" class="head5-1" style="cursor: pointer;">返回登录</button>
        </td>
      </tr>
    </table>
  </div>

      <!-- 右侧内容区域 -->
      <div class="right">
        <div class="centered-container">
          <h2>欢迎来到猫咪美食坊！</h2>
        </div>
        <div class="centered-container">
          <form @submit.prevent="handleSearch">
            <input type="text" v-model="searchQuery" class="custom-input" placeholder="搜索商品">&nbsp;&nbsp;&nbsp;
            <select v-model="selectedCategory">
                <option value="猫咪主粮">猫咪主粮</option>
                <option value="猫咪零食">猫咪零食</option>
                <option value="猫咪日用">猫咪日用</option>
              </select><br><br>&nbsp;&nbsp;&nbsp;
            <input type="submit" value="搜索" style="width:130px;cursor: pointer;">
          </form>
          <div id="search_list"></div>
        </div><br>
          <div class="goods">
            <div class="show-1" v-for="item in paginatedItems" :key="item.id">
              <div class="media-container">
                  <div v-for="(media, index) in item.mediaFiles" :key="index" v-show="media.isActive">
                  <img v-if="!media.isVideo" :src="media.url" alt="商品图片" v-show="media.isActive">
                  <video v-if="media.isVideo" :src="media.url" controls v-show="media.isActive"></video>
                  </div>
              </div>
              <div class="media-navigation">
                  <button @click="showPrevMedia(item)">＜</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <button @click="showNextMedia(item)">＞</button>
              </div>
              <a href="/buyer-shop">
                <p>商品名：{{ item.name }}<br>超值价：{{ item.price }}元</p>
              </a>
            </div>
          </div>
          <div class="pagination">
            <button @click="goToPrevPage" class="prev" :disabled="currentPage === 1">上一页</button>
            <span id="page-info">第 {{ currentPage }} / {{ totalPages }} 页</span>
            <button @click="goToNextPage" class="next" :disabled="currentPage === totalPages">下一页</button>
          </div>
      </div>
    
    </div>
  </div>
</body>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      items: [
      {
        id: 1,
        name: "猫咪粮食",
        price: "99",
        description: "高营养猫粮",
        kind: '猫咪主粮',
        mediaFiles: [
          { url: require('@/assets/img/buyer/food-1.jpg'), isActive: true, isVideo: false },
          { url: require('@/assets/img/buyer/food-2.jpg'), isActive: false, isVideo: false },
        ],
      },
      {
        id: 2,
        name: "猫咪玩具",
        price: "49",
        description: "好玩的猫咪玩具",
        kind: '猫咪日用',
        mediaFiles: [
          { url: require('@/assets/img/buyer/food-2.jpg'), isActive: true, isVideo: false },
          { url: require('@/assets/img/buyer/food-1.jpg'), isActive: false, isVideo: false },
        ],
      },
      {
        id: 3,
        name: "猫咪窝",
        price: "299",
        description: "舒适的猫咪小窝",
        kind: '猫咪主粮',
        mediaFiles: [
          { url: require('@/assets/img/buyer/food-1.jpg'), isActive: true, isVideo: false },
          { url: require('@/assets/img/buyer/food-2.jpg'), isActive: false, isVideo: false },
        ],
      },
      // ...更多商品
     ],
     searchQuery: '',
     selectedCategory: '猫咪主粮', // 设置初始值为“猫咪主粮”
     filteredItems: [], // 添加这个新数组
      currentPage: 1,
      itemsPerPage: 2,
      search: {
        keyword: '',
        kind: '猫咪主粮'
      },
      currentUser :null
    };
  },

  created() {
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
    }
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
    async handleLogout() {
      try {
        // 向后端发送登出请求
        await axios.get('/logout-control');
        // 跳转到指定路由
        this.$router.push('/');
      } catch (error) {
        console.error('登出失败:', error);
        // 可选：处理登出失败的情况
      }
    },
    navigateTo(routeName) {
      this.$router.push({ name: routeName });
    },
    beforeMount() {
      if (!this.isLoggedIn) {
        this.$router.push('/');
      }
    },
    handleSearch() {
      this.filteredItems = this.items.filter(item => {
          const matchesKeyword = this.searchQuery ? item.name.toLowerCase().includes(this.searchQuery.toLowerCase()) : true;
          const matchesCategory = this.selectedCategory ? item.kind === this.selectedCategory : true;

          console.log(`Item: ${item.name}, Keyword Match: ${matchesKeyword}, Category Match: ${matchesCategory}`);

          return matchesKeyword && matchesCategory;
      });
      console.log("Filtered items count:", this.filteredItems.length);
      // 其他逻辑
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
