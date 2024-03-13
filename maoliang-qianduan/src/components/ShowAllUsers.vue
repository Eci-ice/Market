<template>
  <div v-if="isLoggedIn">
    <div id="a">
      <div class="container">
        <div class="centered-container">
          <h2>用户信息列表</h2>
        </div>
        <div id="search_list"></div>
        <table border="1px" align="center" cellspacing="0">
          <tr>
            <th>ID</th>
            <th>用户名</th>
            <th>操作</th>
          </tr>
          <tr v-for="user in paginatedUsers" :key="user.userid">
            <td>{{ user.userid }}</td>
            <td>{{ user.username }}</td>
            <td>
              <router-link :to="{ name: 'UserOrderHistory', params: { userId: user.userid } }">
                查看购买历史
              </router-link>
            </td>
          </tr>
        </table>
        <div class="pagination">
          <button @click="goToPrevPage" class="prev">上一页</button>
          <span id="page-info">第 {{ currentPage }} 页</span>
          <button @click="goToNextPage" class="next">下一页</button>
        </div>
      </div>
    </div>
  </div>
  <div v-else class="else">
    <span>您还未登录，请先<a href="/login">登录</a></span>
  </div>
</template>

<script>
import axios from 'axios'; // 确保已经安装并导入axios

export default {
  data() {
    return {
      isLoggedIn: false, // 一开始设为false，等到验证后可能会改变
      currentPage: 1,
      users: [], // 从后端获取的用户数据将存储在这里
      pageSize: 5,
      currentUser: null,
      filteredUsers: [], // 存储过滤后的用户
    };
  },
  computed: {
    paginatedUsers() {
      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      return this.filteredUsers.slice(start, end);
    },
    totalPages() {
      return Math.ceil(this.filteredUsers.length / this.pageSize);
    },
  },
  methods: {
    async fetchUsers() { 
      try {
  var power = this.currentUser.power;
  // 使用 GET 方法发送请求，将数据作为查询参数发送

  const response = await axios.get('/usr/UserList-control', {
    params: { power: power }
  });

  if (response.data ) {
    this.users = response.data.data;
    this.filteredUsers = this.users;
    console.log("获取用户列表成功")
  } else {
    console.error("获取用户列表失败");
  }
} catch (error) {
  console.error('请求用户列表错误', error);
}}
,
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
    goToPrevPage() {
      if (this.currentPage > 1) this.currentPage--;
    },
    goToNextPage() {
      if (this.currentPage < this.totalPages) this.currentPage++;
    },
  },
  mounted() {
    this.fetchUsrFromSession().then(() => {
      if (this.isLoggedIn) {
        this.fetchUsers();
      }
    });
  },
};
</script>

<!-- 未登录框和其他样式 -->



<!-- 未登录框 -->
<style type="text/css" scoped>
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
  text-align: center;
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

	
	
    .modal {
        display: none; /* 默认隐藏 */
        position: fixed; /* 固定在页面上 */
        z-index: 1; /* 处于顶层 */
        left: 0;
        top: 0;
        width: 100%; /* 宽度为整个屏幕 */
        height: 100%; /* 高度为整个屏幕 */
        overflow: auto; /* 如果内容过多则启用滚动条 */
        background-color: rgba(0,0,0,0.4); /* 半透明的黑色背景 */
        padding-top: 60px;
    }

   .modal-content {
    background-color: #fff;
    margin: 5% auto;
    padding: 20px;
    border: 1px solid #ddd;
    width: 60%;
    border-radius: 5px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.close {
    color: #aaa;
    float: right;
    font-size: 28px;
    font-weight: bold;
    cursor: pointer;
}

.close:hover {
    color: #000;
    text-decoration: none;
}

    .close:focus {
        color: black;
        text-decoration: none;
        cursor: pointer;
    }
    
    .price-modal-content {
    background-image: url('@/assets/img/a.jpg'); /* 更改为您自己的图片路径 */
    background-size: cover; /* 调整背景图片大小以填充整个容器 */
    background-position: center; /* 居中显示背景图片 */
    opacity: 1; /* 调整透明度（0.8表示80%的不透明度） */


}
</style>
