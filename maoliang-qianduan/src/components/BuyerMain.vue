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
            <div class="show-1" v-for="item in paginatedItems" :key="item.goodid">
              <div @click="postToBuyerShop(item.goodid)">
                <div class="media-container">
                    <div v-for="(media, index) in item.mediaFiles" :key="index" v-show="media.isActive">
                      <img v-if="!isVideo(media)" :src="media.url" alt="商品图片" v-show="media.isActive">
                      <video v-else :src="media.url" controls v-show="media.isActive"></video>
                    </div>
                </div>
              </div>
                <div class="media-navigation">
                    <button @click="showPrevMedia(item)">＜</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <button @click="showNextMedia(item)">＞</button>
                </div>
              <div @click="postToBuyerShop(item.goodid)">
                <p>商品名：{{ item.goodname }}<br>超值价：{{ item.price }}元</p>
              </div>
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
            <div class="show-1" v-for="item in paginatedItems" :key="item.goodid">
              <div @click="postToBuyerShop(item.goodid)">
                <div class="media-container">
                    <div v-for="(media, index) in item.mediaFiles" :key="index" v-show="media.isActive">
                      <img v-if="!isVideo(media)" :src="media.url" alt="商品图片" v-show="media.isActive">
                      <video v-else :src="media.url" controls v-show="media.isActive"></video>
                    </div>
                </div>
              </div>
                <div class="media-navigation">
                    <button @click="showPrevMedia(item)">＜</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <button @click="showNextMedia(item)">＞</button>
                </div>
              <div @click="postToBuyerShop(item.goodid)">
                <p>商品名：{{ item.goodname }}<br>超值价：{{ item.price }}元</p>
              </div>
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
      items: [ ],
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
    this.fetchgoodListSession();
  },
  async mounted() {
    console.log('BuyerMain 组件已挂载');
    await this.fetchgoodListSession(); // 等待 fetchgoodListSession 完成!!
    this.filteredItems = this.items; // 初始时显示所有商品
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
    async fetchgoodListSession() {
      try {
        // 发起 GET 请求获取商品列表
        const goodsResponse = await axios.get('/good/buyer-all-good-list-control');
        // 解析响应数据
        // console.log('goodList:', goodsResponse);
        const goodList = goodsResponse.data.data;//goodsResponse的数据的data属性
        // 将商品列表添加到 products 中
        // 解析picture属性并添加mediaFiles属性
       //  console.log('goodList:', goodList);

        this.items = goodList.map( good => {
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
        });
        console.log('this.items:', this.items);
        return true;
      } catch (error) {
        console.error('获取商品列表数据错误:', error);
        return false;
      }
    },
    isVideo(media) {
      //判断是否是视频
      return media.url.endsWith('.mp4') || media.url.endsWith('.avi');
    },
    async postToBuyerShop(goodid) {
      const response = await fetch(`/good/buyer-show-good/${goodid}`, {
        method: 'POST',
      });

      this.selectedFiles = []; // 清空照片列表

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
    async handleSearch() {
      console.log('搜索已执行');
      const credentials = {
        keyword: this.searchQuery,
        kind: this.selectedCategory,
        ishistory: 0
      };

      const response = await fetch('/good/search-list-control', {
        method: 'POST',
        body: JSON.stringify(credentials),
        headers: {
          'Content-Type': 'application/json'
        }
      });

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
