<template>
    <div v-if="isLoggedIn">
        <!-- 模态窗口 -->
        <div v-if="showModal" class="modal" style="position: fixed; z-index: 1; left: 0; top: 0; width: 100%; height: 100%; overflow: auto; background-color: rgba(0,0,0,0.4);">
            <span class="close-button" @click="closeModal" style="position: absolute; top: 20px; right: 30px; color: #f1f1f1; font-size: 35px; font-weight: bold; cursor: pointer;">&times;</span>
            <img v-if="modalContentType === 'image'" :src="modalContentSrc" style="margin: auto; display: none; width: 80%; max-width: 700px;">
            <video v-if="modalContentType === 'video'" :src="modalContentSrc" controls style="margin: auto; display: none; width: 80%; max-width: 700px;"></video>
        </div>
        <div id="a">
            <div class="container">
                <div class="centered-container">
                <h2>全部商品信息</h2><br>
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
                    <th>库存</th>
                    <th>单价</th>
                    <th>展示内容</th>
                    <th>状态</th>
                    <th>操作</th>
                </tr>
                <tr v-for="good in paginatedGoods" :key="good.goodid">
                    <td>{{ good.goodid }}</td>
                    <td>{{ good.goodname }}</td>
                    <td>{{ good.description }}</td>
                    <td :class="{ 'low-stock': good.number < 5 }">{{ good.number }}</td>
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
                    <td>{{ stateText(good.state) }}</td>
                    <td>
                    <button @click="confirmDelete(good.goodid)">下架</button> | 
                    <button @click="openPriceModal(good)">修改价格</button>
                    </td>
                </tr>
                </table>

                <!-- 分页 -->
                <div class="pagination">
                <button @click="goToPrevPage" class="prev" :disabled="currentPage === 1">上一页</button>
                <span id="page-info">第 {{ currentPage }} / {{ totalPages }} 页</span>
                <button @click="goToNextPage" class="next" :disabled="currentPage === totalPages">下一页</button>
                </div>
                <!-- 价格修改弹窗 -->
                <PriceModal
                    :good="selectedGood"
                    :isVisible="showPriceModal"
                    @close="showPriceModal = false"
                    @update="handleUpdatePrice"
                />
            </div>
        </div>
    </div>
  <div v-else class="else">
    <span>您还未登录，请先 <router-link to="/">登录</router-link></span>
  </div>

</template>

<script>
import PriceModal from './PriceModal.vue';

export default {
    components: {
        PriceModal
    },
    data() {    
    return {
      isLoggedIn: true, // 这应该来自 Vuex store 或父组件
      searchQuery: '', // 默认选中的分类
      showPriceModal: false, // 控制 PriceModal 的显示
      selectedGood: null, // 当前选中的商品对象
      showModal: false,  // 初始化为 false 或根据需要设置初始值
      selectedCategory: '猫咪主粮', // 初始化为空字符串或其他初始值
      currentPage: 1,
      pageSize: 1, // 每页显示的商品数量
      filteredGoods: [],
      isHistory: '0', // 这个值可以根据需要在data中定义，或者在方法中直接使用
      showSuccessMessage: false,
      successMessage: '',
      goods: [
        {
            goodid: 1,
            goodname: '猫粮',
            description: '营养全面',
            number: 10,
            price: 100.0,
            state: 0,   
            kind: '猫咪主粮',
            mediaFiles: [
                { url: require('@/assets/img/buyer/food-1.jpg'), isActive: true },
                { url: require('@/assets/img/buyer/food-2.jpg'), isActive: false },
                // ... 其他媒体文件 ...
            ],
        },
        {
            goodid: 2,
            goodname: '猫砂',
            description: '吸水性强',
            number: 20,
            price: 50.0,
            state: 0,
            kind: '猫咪日用',
            mediaFiles: [
                { url: require('@/assets/img/buyer/food-2.jpg'), isActive: true },
                { url: require('@/assets/img/buyer/food-1.jpg'), isActive: false },
                // ... 其他媒体文件 ...
            ],
            }
        // ...更多商品数据
        ],
    };
  },
  methods: {
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
    setActiveMedia(good, index) {
        good.mediaFiles.forEach((media, idx) => {
        media.isActive = idx === index;
        console.log(`Media at index ${idx} active status: `, media.isActive);
        });
    },
    isVideo(media) {
        const isMediaVideo = media.url.endsWith('.mp4');
        console.log(`Is media a video: ${isMediaVideo}`);
        return isMediaVideo;
    },
    showSuccessModal(message) {
      this.successMessage = message;
      this.showSuccessMessage = true;
    },
    searchGoods() {
        console.log('搜索已执行');
        this.filteredGoods = this.goods.filter((good) => {
            const matchesQuery = this.searchQuery ? good.goodname.toLowerCase().includes(this.searchQuery.toLowerCase()) : true;
            const matchesCategory = this.selectedCategory ? good.kind === this.selectedCategory : true;
            this.resetPage();
            return matchesQuery && matchesCategory;
        });
        // 如果需要，您可以在这里添加额外的逻辑，例如记录搜索事件等
    },
    // 调用此方法来关闭成功消息弹窗
    closeSuccessModal() {
      this.showSuccessMessage = false;
    },
    confirmDelete(goodId) {
        // 弹出确认对话框
        if (confirm('确定要删除这个商品吗？')) {
        this.deleteGood(goodId);
        }
    },
    // TODO: 实现删除商品的逻辑
    deleteGood(goodId) {
        console.log('删除商品，商品ID:', goodId);
        // 模拟API调用，直接从列表中移除商品
        // 在实际应用中，这里会是一个调用后端API的异步操作
        this.filteredGoods = this.filteredGoods.filter(good => good.goodid !== goodId);
        // 显示删除成功的消息，这里可以替换为更复杂的通知系统
        alert('商品删除成功');
        // 调用后端API删除商品
        // axios.delete('/api/goods/' + goodId)
        // .then(() => {
        // // 删除成功后从列表中移除该商品
        // this.filteredGoods = this.filteredGoods.filter(good => good.goodid !== goodId);
        // // 可以显示一个成功的提示信息
        // this.showSuccessModal('商品删除成功');
        // })
        // .catch(error => {
        // // 错误处理，显示错误信息
        // console.error('删除失败', error);
        // });
    },
    // TODO: 实现打开价格修改模态窗口的逻辑
    openPriceModal(good) {
        console.log("打开价格修改窗口!");
      this.selectedGood = good;
      this.showPriceModal = true;
    },
    handleUpdatePrice({ goodId, newPrice }) {
      // 在这里处理价格更新逻辑
      // 例如，更新商品数组中对应商品的价格
      const good = this.goods.find(g => g.goodid === goodId);
      if (good) {
        good.price = newPrice;
      }
      this.showPriceModal = false;
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
    stateText(value) {
        const stateMap = { 0: '上架', 1: '冻结', 2: '售出' };
        return stateMap[value] || '未知';
    },
  },
  computed: {
    // 计算当前页的商品
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
  watch: {
    // 监视搜索结果的变化
    filteredGoods(newValue, oldValue) {
        if (newValue !== oldValue) {
            this.resetPage();
        }
    }
  },
  mounted() {
    console.log('ShowGoods 组件已挂载');
    // 示例：假设从后端获取成功消息
    // 在实际应用中，您可能需要在某个操作成功后调用 showSuccessModal
    this.filteredGoods = this.goods; // 初始时显示所有商品
    this.showSuccessModal('价格修改成功！');
  }
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

    .centered-container {
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .low-stock {
        font-weight: bold;
        color: red;
    }
</style>
