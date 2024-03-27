<template>
  <div v-if="isLoggedIn">
    <div id="a">
    <div class="container">
      <div class="centered-container">
        <h2>搜索商品信息</h2><br>
      </div>
      <div class="centered-container">
      <div class="search-bar">
        <input type="text" v-model="searchQuery" class="custom-input" placeholder="搜索商品">
        <select v-model="selectedCategory"  id="search_kind">
            <option value="猫咪主粮">猫咪主粮</option>
            <option value="猫咪零食">猫咪零食</option>
            <option value="猫咪日用">猫咪日用</option>
        </select>
        <button @click="searchGoods" style="width:130px" class="custom-button">搜索</button>
      </div>
      </div>

      <table border="1px" align=center cellspacing="0">
        <tr>
          <th>ID</th>
          <th>名称</th>
          <th>描述</th>
          <th>单价</th>
          <th>展示内容</th>
          <th>类别</th>
          <th>子类别</th>
          <th>创建日期</th>
        </tr>
        <tr v-for="good in paginatedGoods" :key="good.goodid">
            <td>{{ good.goodid }}</td>
            <td>{{ good.goodname }}</td>
            <td>{{ good.description }}</td>
            <td>{{ good.price }}</td>
            <td>
              <div class="media-container">
                <div v-for="(media, index) in good.mediaFiles" :key="index" v-show="media.isActive">
                  <img v-if="!isVideo(media)" :src="media.url" alt="商品图片" v-show="media.isActive">
                  <video v-if="isVideo(media)" :src="media.url" controls v-show="media.isActive"></video>
                </div>
              </div>
              <div class="media-navigation">
                <button @click="showPrevMedia(good)">＜</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <button @click="showNextMedia(good)">＞</button>
              </div>
            </td>
            <td>{{ good.kind }}</td>
            <td>{{ good.subkind }}</td>
            <td>{{ good.createdate }}</td>
        </tr>
      </table>

      <!-- 分页 -->
      <div class="pagination">
        <button @click="goToPrevPage" class="prev" :disabled="currentPage === 1">上一页</button>
        <span id="page-info">第 {{ currentPage }} / {{ totalPages }} 页</span>
        <button @click="goToNextPage" class="next" :disabled="currentPage === totalPages">下一页</button>
      </div>
    </div>
    </div>
  </div>
  <div v-else class="else">
    您还未登录，请先<a href="/login">登录</a>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      products: [],
      selectedGood: null, // 当前选中的商品对象
      selectedCategory: '猫咪主粮', // 初始化为空字符串或其他初始值
      currentPage: 1,
      pageSize: 6, // 每页显示的商品数量
      goods: [], // 存储从服务器获取的所有商品
      filteredGoods: [], // 存储过滤后的商品
      currentUser:null,
      isHistory: 1,
    };
  },
  created() {
    this.fetchUsrFromSession();
    this.fetchgoodListSession();
  },
  computed: {
    isLoggedIn() {
      // 根据当前用户数据判断用户是否登录
      return !!this.currentUser;
    },
    paginatedGoods() {
      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      return this.filteredGoods.slice(start, end);
    },
    // 计算总页数
    totalPages() {
      return Math.ceil(this.filteredGoods.length / this.pageSize);
    }
  },
  async mounted() {
    console.log('ShowHistoryGoods 组件已挂载');
    await this.fetchgoodListSession(); // 等待 fetchgoodListSession 完成!!
    // 示例：假设从后端获取成功消息
    // 在实际应用中，您可能需要在某个操作成功后调用 showSuccessModal
    this.filteredGoods = this.goods; // 初始时显示所有商品
    console.log('this.filteredGoods:', this.filteredGoods);
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
        const goodsResponse = await axios.get('/searchList');
        // 解析响应数据
        // console.log('goodList:', goodsResponse);
        const goodList = goodsResponse.data;//goodsResponse的数据的data属性
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
            subkind: good.subkind,
            createdate: good.createdate
          };
        });
        console.log('this.goods:', this.goods);
        return true;
      } catch (error) {
        console.error('获取商品列表数据错误:', error);
        return false;
      }
    },
    showPrevMedia(good) {
      const currentIndex = good.mediaFiles.findIndex(media => media.isActive);
      console.log('Current active index:', currentIndex);

      if (currentIndex >= 0) {
        const prevIndex = (currentIndex - 1 + good.mediaFiles.length) % good.mediaFiles.length;
        console.log('Previous index:', prevIndex);
        this.setActiveMedia(good, prevIndex);
      }
    },
    showNextMedia(good) {
      const currentIndex = good.mediaFiles.findIndex(media => media.isActive);
      console.log('Current active index:', currentIndex);

      if (currentIndex >= 0) {
        const nextIndex = (currentIndex + 1) % good.mediaFiles.length;
        console.log('Next index:', nextIndex);
        this.setActiveMedia(good, nextIndex);
      }
    },
    isVideo(media) {
      const isMediaVideo = media.url.endsWith('.mp4');
      console.log(`Is media a video: ${isMediaVideo}`);
      return isMediaVideo;
    },
    async searchGoods() {
      console.log('搜索已执行');
      const credentials = {
        keyword: this.searchQuery,
        kind: this.selectedCategory,
        ishistory: 1
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
    goToPrevPage() {
      // 实现翻页逻辑
      if (this.currentPage > 1) {
        this.currentPage--;
      }
    },
    goToNextPage() {
      // 实现翻页逻辑
        if (this.currentPage < this.totalPages) {
            this.currentPage++;
        }
    },
    // 每次搜索结果变化后重置当前页码
    resetPage() {
        this.currentPage = 1;
    },
    fetchProducts() {
      // 从后端获取产品列表
    }
  },

  watch: {
    // 监视搜索结果的变化
    filteredGoods(newValue, oldValue) {
        if (newValue !== oldValue) {
            this.resetPage();
        }
    }
  },

};
</script>

<!-- 未登录框 -->
<style type="text/css" scoped>
.custom-input {
    margin-right: 10px;
    width: 407.49px; /* 设置宽度 */
    height: 45px; /* 设置高度 */
    background-color: #FFFFFF; /* 设置背景颜色 */
    padding: 10px; /* 设置内边距 */
    border: 1px solid #ccc; /* 设置边框，如果需要的话 */
    box-sizing: border-box; /* 保证总宽度包括了内边距和边框 */
    /* 添加其他任何需要的样式 */
}
#search_kind {
    margin-right: 10px;
    width: 76.8px; /* 设置宽度 */
    height: 45px; /* 设置高度 */
    background-color: #FFFFFF; /* 设置背景颜色为白色 */
    color: #000000; /* 设置文字颜色为黑色 */
    font-size: 13.333px; /* 设置字体大小 */
    font-family: Arial, sans-serif; /* 设置字体为Arial，备用字体为sans-serif */
    border: 1px solid #ccc; /* 如果需要，可以设置边框 */
    box-sizing: border-box; /* 内容、内边距和边框的总宽度和高度 */
}
.custom-button {
    width: 89.04px; /* 设置宽度 */
    height: 45px; /* 设置高度 */
    background-color: #F0F0F0; /* 设置背景颜色为浅灰色 */
    padding: 1px 6px; /* 垂直内边距1px，水平内边距6px */
    border: none; /* 假设您想去掉默认边框 */
    font-family: Arial, sans-serif; /* 假设您想要的字体是Arial，备用字体是sans-serif */
    color: #000000; /* 文字颜色，假设您想要黑色，与您的选择框类似 */
    box-sizing: border-box; /* 内边距和边框包含在元素的总宽度和高度内 */
    cursor: pointer; /* 更改光标样式，表示这个元素是可点击的 */
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
.centered-container {
        display: flex;
        justify-content: center;
        align-items: center;
}
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
		top: 140px;
		left: 12%;
		width: 70%;
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
        display: none;
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