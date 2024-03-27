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
              <router-link :to="{ name: 'UserOrderHistory', props:[user.userid]}">
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
export default {
  data() {
    return {
      isLoggedIn: true, // 假设您从某处获取登录状态
      currentPage: 1,
      users: [
        { userid: 1, username: "user1" },
        { userid: 2, username: "user2" },
        { userid: 3, username: "user3" },
        { userid: 4, username: "user4" },
        { userid: 5, username: "user5" },
        { userid: 6, username: "user6" },
        { userid: 7, username: "user7" },
        { userid: 8, username: "user8" },
        { userid: 9, username: "user9" },
        { userid: 10, username: "user10" },
        { userid: 11, username: "user11" },
        { userid: 12, username: "user12" },
        { userid: 13, username: "user13" },
        { userid: 14, username: "user14" },
        { userid: 15, username: "user15" },
      ], // 假设您从后端获取用户数据
      pageSize: 5,
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
    }
  },
  methods: {
    goToPrevPage() {
      if (this.currentPage > 1) this.currentPage--;
    },
    goToNextPage() {
      if (this.currentPage < this.totalPages) this.currentPage++;
    },
    fetchUsers() {
      // 在这里写 AJAX 请求获取用户数据
    },
  },
  mounted() {
    // 示例：假设从后端获取成功消息
    // 在实际应用中，您可能需要在某个操作成功后调用 showSuccessModal
    this.filteredUsers = this.users; // 初始时显示所有用户
    this.fetchUsers();
  },
};
</script>

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
