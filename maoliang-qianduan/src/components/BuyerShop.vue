<template>
<body style="margin: 0px;">
  <div v-if="isLoggedIn">
    <div class="left" >
      <!-- 页面头部 -->
      <table class="daohang">
        <img class="head1" src="~@/assets/img/buyer/head.png" alt="">	
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

  <div class="right" style="width:100%;">
    <a href="/#/buyer">
      <img src="~@/assets/img/buyer/arrow.png" alt="" width="40" title="返回商品界面">
    </a>
    <div class="picture-k">
      <div class="media-container" v-for="(mediaFile, index) in selectedProduct.mediaFiles" :key="index"  v-show="mediaFile.isActive">
        <img v-if="!mediaFile.isVideo" :src="mediaFile.url" alt="商品图片" class="product-image" style="width: 415.8px;
        height: 504.9px;">
        <video v-else :src="mediaFile.url" controls class="product-video" style="width:200px;"></video>
        <div class="media-navigation">
          <button class="media-navigation-button prev-button" @click="showPrevMedia(selectedProduct)">＜</button>
        <button class="media-navigation-button next-button" @click="showNextMedia(selectedProduct)">＞</button>
      </div>
      </div>
      
    </div>
    <table class="good-2">
      <tr><td colspan="2" class="goodname">{{ selectedProduct.name }}</td></tr>
      <tr><td colspan="2" class="description">{{ selectedProduct.description }}</td></tr>
      <tr><td class="price">价格：</td><td class="price">{{ selectedProduct.price }}</td></tr>
      <tr><td class="price">剩余库存量：</td><td class="price">{{ selectedProduct.number }}</td></tr>
    </table>
    <div class="centered-container">
    <button @click="buyProduct(selectedProduct.id)" class="butt-1" style="cursor: pointer;">购买该商品</button>&nbsp;&nbsp;
    <button @click="addToCart(selectedProduct.id)" class="butt-1" style="cursor: pointer;">加入购物车</button>&nbsp;&nbsp;
    <button @click="addToFavorites(selectedProduct.id)" class="butt-1" style="cursor: pointer;">收藏商品</button>
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
   <div class="right" style="width:100%;">
    <a href="/buyer">
      <img src="~@/assets/img/buyer/arrow.png" alt="" width="40" title="返回商品界面">
    </a>
    <div class="picture-k">
      <div class="media-container" v-for="(mediaFile, index) in selectedProduct.mediaFiles" :key="index"  v-show="mediaFile.isActive">
        <img v-if="!mediaFile.isVideo" :src="mediaFile.url" alt="商品图片" class="product-image" style="width: 415.8px;
        height: 504.9px;">
        <video v-else :src="mediaFile.url" controls class="product-video" style="width:200px;"></video>
        <div class="media-navigation">
          <button class="media-navigation-button prev-button" @click="showPrevMedia(selectedProduct)">＜</button>
        <button class="media-navigation-button next-button" @click="showNextMedia(selectedProduct)">＞</button>
      </div>
      </div>
      
    </div>
    <table class="good-2">
      <tr><td colspan="2" class="goodname">{{ selectedProduct.goodname }}</td></tr>
      <tr><td colspan="2" class="description">{{ selectedProduct.description }}</td></tr>
      <tr><td class="price">价格：</td><td class="price">{{ selectedProduct.price }}</td></tr>
      <tr><td class="price">剩余库存量：</td><td class="price">{{ selectedProduct.number }}</td></tr>
    </table>
    <div class="centered-container">
    <button @click="buyProduct(selectedProduct.goodid)" class="butt-1" style="cursor: pointer;">购买该商品</button>&nbsp;&nbsp;
    <button @click="addToCart(selectedProduct.goodid)" class="butt-1" style="cursor: pointer;">加入购物车</button>&nbsp;&nbsp;
    <button @click="addToFavorites(selectedProduct.goodid)" class="butt-1" style="cursor: pointer;">收藏商品</button>
    </div>
  </div>
    </div>
  </div>
</body>
</template>

<script>
import { mapActions } from 'vuex';
import axios from "axios";

export default {
  data() {
    return {
      items: [],
     searchQuery: '',
      selectedProduct: [],
      currentUser:'',
     selectedCategory: '猫咪主粮', // 设置初始值为“猫咪主粮”
     filteredItems: [], // 添加这个新数组
      currentPage: 1,
      itemsPerPage: 2,
      search: {
        keyword: '',
        kind: '猫咪主粮'
      }
    };
  },
  created() {
    this.fetchUsrFromSession();
    this.fetchGoodFromSession();
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
    }
  },

  methods: {
    ...mapActions(['logout']),
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
        return true;
      } catch (error) {
        console.error('获取用户数据错误:', error);
        return false;
      }
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
    showPrevMedia(product) {
        const currentIndex = product.mediaFiles.findIndex(media => media.isActive);
        console.log('Current active index:', currentIndex);

        if (currentIndex >= 0) {
        const prevIndex = (currentIndex - 1 + product.mediaFiles.length) % product.mediaFiles.length;
        console.log('Previous index:', prevIndex);
        this.setActiveMedia(product, prevIndex);
        }
    },
    showNextMedia(product) {
        const currentIndex = product.mediaFiles.findIndex(media => media.isActive);
        console.log('Current active index:', currentIndex);

        if (currentIndex >= 0) {
        const nextIndex = (currentIndex + 1) % product.mediaFiles.length;
        console.log('Next index:', nextIndex);
        this.setActiveMedia(product, nextIndex);
        }
    },
    setActiveMedia(item, index) {
        item.mediaFiles.forEach((media, idx) => {
        media.isActive = idx === index;
        console.log(`Media at index ${idx} active status: `, media.isActive);
        });
    },
    buyProduct(productId) {
      // 处理购买逻辑
      this.$router.push({ name: 'BuyerFillInfo', params: { productId } });
    },
    addToCart() {
      alert("该商品加入购物车成功！");
      // 加入购物车逻辑
    },
    addToFavorites() {
      alert("该商品收藏成功！");
      // 收藏商品逻辑
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
button {
  padding: 5px 10px;
  width: 150px;
  height: 50px;
  margin: 5px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}
    .butt-1 {
  /* 购买按钮 */
  position: relative;
  background-color: orange;
  font-size: 20px;
  border: none;
  border-radius:5px;
}
.picture-k {
  width: 462px;
  height: 561px;
  background-repeat: no-repeat;
  background-image: url("~@/assets/img/buyer/Square.png");
  position: relative;
  justify-content: center;
  left: 100px;
  top: 10px;
  background-size: 100% 100%;
  border-style: none;
}
    *{
        background-color: #FFF9F1;
        
    }
    body {
        display: block;
        background-color: #FFF9F1 !important;
        background-image: none;
        height: 1000px;
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
        width: 1340px; */
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
        .product-image, .product-video {
          width: 100%;
          height: 100%;
          object-fit: cover; /* 或者 'contain' 根据您的需求 */
        }
        .media-navigation-button {
          position: absolute; /* 绝对定位相对于它的父元素 .media-container */
          top: 50%; /* 垂直居中 */
          transform: translateY(-50%); /* Y轴方向上向上移动50%的自己的高度，实现完全居中对齐 */
          background: #85C46EB3; /* 透明背景 */
          border: none; /* 无边框 */
          cursor: pointer; /* 手形光标 */
          width: 30px;
          height: 50px;
        }

.prev-button {
  left: 0; /* 放在左边 */
}

.next-button {
  right: 0; /* 放在右边 */
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
    
<style scoped>

*{
    background-color: #FFF9F1;
    
}
body {
    display: block;
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
    left: 70px;
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
    .good-2 {
  position: absolute;
  top: 100px;
  left: 650px;
  float: right;
  border-style: none;
  /* background-color: rgba(121, 115, 115, 0.5); */
  width: 700px;
  height: 600px;
}
.goodname {
  height: 150px;
  font-size: 55px;
}
.description {
  height: 200px;
  background-color: rgba(255, 255, 255, 0.5);
  font-size: 30px;
}
.price {
  font-size: 24px;
  width: 300px;
  height: 100px;
}
button {
  padding: 5px 10px;
  width: 150px;
  height: 50px;
  margin: 5px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}
.butt-1 {
  /* 购买按钮 */
  position: relative;
  background-color: orange;
  font-size: 20px;
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
        width: 90%;
        height: 90%;
        margin-left:10px;
        margin-top:100px;
        overflow: hidden;
    }
    .media-container img, .media-container video {
        width: 100%;
        height: 100%;
        object-fit: contain;
    }
    .media-container img.active, .media-container video.active {
        display: block;
        width: 415.8px;
        height: 504.9px;
    }
    .media-container button {
        position: absolute;
        top: 50%;
        width: 30px;
        transform: translateY(-50%);
        background: rgba(133, 196, 110, 0.7);
    }
    .media-container .prev-button {
        left: 10px;
    }
    .media-container .next-button {
        right: 10px;
    }
</style>