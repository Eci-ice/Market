<template>
  <!-- <div class="modal" v-if="isVisible"  style="display: block;"> -->
    <div class="modal" v-if="isVisible">
    <div class="modal-content price-modal-content">
      <span class="close" @click="closeModal">&times;</span>
      <h2><strong>修改商品价格</strong></h2>
     
  <div class="product-info">
    <p><strong>商品ID:</strong> {{ good.goodid }}</p>
    <p><strong>商品名称:</strong> {{ good.goodname }}</p>
    <p><strong>原价格:</strong> {{ good.price }}</p>
  </div>

  <div>
    <label for="newPrice"><strong>修改后价格:</strong></label>
    <input type="number" v-model="newPrice" placeholder="输入新价格" />
  </div>

  <button @click="updatePrice">确认修改</button>
</div>
  </div>
</template>

<script>
export default {
  props: {
    good: Object,
    isVisible: Boolean,
  },
data() {
return {
newPrice: '', // 新价格
};
},
methods: {
closeModal() {
this.$emit('close'); // 触发父组件的关闭事件
},
updatePrice() {
// 这里添加更新价格的逻辑
// 通常是发起一个API请求，然后通知父组件更新
this.$emit('update', { goodId: this.good.goodid, newPrice: this.newPrice });
},
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
	
    .modal {
        display: block; /* 默认隐藏 */
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
    background-image: url('~@/assets/img/a.jpg'); /* 更改为您自己的图片路径 */
    background-size: cover; /* 调整背景图片大小以填充整个容器 */
    background-position: center; /* 居中显示背景图片 */
    opacity: 1; /* 调整透明度（0.8表示80%的不透明度） */
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