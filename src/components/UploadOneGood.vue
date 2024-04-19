<template>
  <div v-if="isLoggedIn">
    <div class="main">
      <h1>请发布商品</h1>
      <form @submit.prevent="submitForm">
      <div class="container">
      <div class="left-div" style="height: 300px;">
        <!-- 左侧div -->
        <!-- 顶部是一个返回按钮 -->
        <button type="button" @click="goBack">返回</button>
        <!-- 中间是一个固定尺寸的预览窗口，用于显示用户上传的图片或视频，最多三个,其中每个图片都支持右上角显示x删除 -->

        <div id="preview">
            <!-- 预览窗口 -->
            <div v-if="selectedFiles.length > 0">
              <div v-for="(file, index) in selectedFiles" :key="index" v-show="index === currentPreviewIndex">
                <img v-if="isImage(file)" :src="getURL(file)" alt="预览图">
                <video v-else controls :src="getURL(file)"></video>
                <button @click="removeFile(index)">X</button>
              </div>
            </div>
            <div v-else>
              没有选择文件
            </div>
        </div>
        <button type="button" @click="prevImage">＜</button>&nbsp;
        <button type="button" @click="nextImage">＞</button><br>

        <!-- 底部是一个上传按钮，在上传图片为空的时候时它显示的是“上传图片/视频”，在有内容的时候为“继续上传”-->
        <input type="file" :disabled="isFileInputDisabled" ref="fileInput" @change="handleFileChange" accept="image/png, image/jpeg, video/mp4" multiple style="display: none;">
        <!-- 添加自定义的上传按钮 -->
        <button type="button" @click="triggerFileInput">上传图片/视频</button>
      </div>
      <div class="right-div">
            <!-- 右侧div -->
            <div class="form-group">
                <label for="kind">商品大类：</label>
                <select v-model="selectedKind" @change="updateSubcategories">
                  <option v-for="kind in kinds" :key="kind.value" :value="kind.value">{{ kind.text }}</option>
                </select><br><br>
            </div>
            <div class="form-group">
              <label for="subkind">商品子类：</label>
              <select v-model="selectedSubkind">
                <option v-for="subkind in subkinds" :key="subkind" :value="subkind">{{ subkind }}</option>
              </select><br><br>
            </div>
            <div class="form-group">
              <label for="goodName">商品名称：</label>
              <input type="text" v-model="goodName" placeholder="请输入商品名称" required><br><br>
              <span v-if="errors.goodName" class="error">{{ errors.goodName }}</span>
            </div>
            <div class="form-group">
              <label for="price">商品价格：</label>
              <input type="number" v-model="price" placeholder="请输入商品价格" required><br><br>
              <span v-if="errors.price" class="error">{{ errors.price }}</span>
            </div>

            <div class="form-group">
              <label for="stock">商品库存：</label>
              <input type="number" v-model="stock" placeholder="请输入商品库存" required><br><br>
              <span v-if="errors.stock" class="error">{{ errors.stock }}</span>
            </div>

            <div class="form-group">
              <label for="description">商品描述：</label>
              <input type="text" v-model="description" placeholder="请输入商品描述" required><br><br>
              <span v-if="errors.description" class="error">{{ errors.description }}</span>
            </div>
            <div class="form-group">
              <button type="submit" class="submit-button-container">确认发布</button>
            </div>
        </div>
        
      </div>
      </form>
  </div>
  </div>
  <div v-else class="else">
    您还未登录，请先<a href="login">登录</a>
  </div>
</template>

<script>
export default {
  data() {
    return {
      products: [], // 用于存储商品的数组
      isLoggedIn: true, // 这里应从Vuex store或父组件获取
      goodName: '',
      price: '',
      stock: '',
      description: '',
      selectedFiles: [],
      selectedKind: '猫咪主粮',  // 设置默认的商品大类
      selectedSubkind: '',       // 商品子类的初始值将在mounted中设置
      currentPreviewIndex: 0,    // 当前预览文件的索引
      kinds: [
        { value: '猫咪主粮', text: '猫咪主粮' },
        { value: '猫咪零食', text: '猫咪零食' },
        { value: '猫咪日用', text: '猫咪日用' }
      ],
      subkinds: [],
      errors: {
        goodName: null,
        price: null,
        stock: null,
        description: null
      },
      currentUser:null,
    };
  },
  methods: {

    updateSubcategories() {
      // 根据selectedKind更新subkinds数组
      if (this.selectedKind === '猫咪主粮') {
          this.subkinds = ['猫干粮', '猫湿粮'];
        } else if (this.selectedKind === '猫咪零食') {
          this.subkinds = ['饼干', '罐头', '猫条'];
        } else if (this.selectedKind === '猫咪日用') {
          this.subkinds = ['猫砂盆', '猫小窝', '猫沙发', '清洁除味'];
        }
        // 设置选中的子类为新数组的第一个元素
        if (this.subkinds.length > 0) {
          this.selectedSubkind = this.subkinds[0];
        } else {
          this.selectedSubkind = '';
      }
    },
    async submitForm() {
      // 在这里处理表单提交逻辑
      // 验证商品名称长度
      if (this.goodName.length > 20) {
        alert("商品名称不能超过20个字符");
        return;
      }

      // 验证价格为数字
      if (isNaN(this.price)) {
        alert("价格需要输入数字");
        return;
      }

      // 验证库存为数字
      if (isNaN(this.stock)) {
        this.errors.stock = "库存需要输入数字";
        return;
      }

      // 验证商品描述长度
      if (this.description.length > 100) {
        alert("商品描述不能超过100个字符");
        return;
      }

      // 验证至少上传一个文件
      if (this.selectedFiles.length === 0) {
        alert("请上传至少一个文件");
        return;
      }

      const formData = new FormData();

      // 添加newProduct数据到FormData对象
      formData.append('goodname', this.goodName);
      formData.append('price', this.price);
      formData.append('number', this.stock);
      formData.append('description', this.description);
      formData.append('kind', this.selectedKind);
      formData.append('subkind', this.selectedSubkind);

      // 添加MultipartFile文件数据到FormData对象
      this.selectedFiles.forEach(file => {
        formData.append('mediaFiles', file);
      //  console.log(file);
      });

  //    console.log(this.selectedFiles.length);
   //   console.log(formData);

      // 发起fetch请求
      const response = await fetch('/good/upload-good', {
        method: 'POST',
        body: formData
      });

      this.selectedFiles = []; // 清空照片列表

      // 解析响应数据
      const responseData = await response.json();

      if (responseData.page === 'error') {
        // 重定向到错误页面，并将错误消息和重定向目标作为参数传递
        this.$router.push({ path: '/error', query: { err: responseData.msg, to: responseData.data }})
      } else if (responseData.page === 'success') {
        // 重定向到成功界面，并将成功消息和重定向目标作为参数传递
        this.$router.push({ path: '/success', query: { message: responseData.msg, to: responseData.data }})
      } else if (responseData.page === 'upload-multiplegoods') {// 处理其他情况，例如重定向等
        this.$router.push({ name: 'UploadMultipleGoods' });
      } else if (responseData.page === 'upload-good') {
        this.$router.push({ name: 'UploadOneGood' });
      } else {
        // 如果返回的是原始页面，处理消息并执行相应操作
        console.log("未知页面类型");
      }
    },
    isImage(file) {
      return file && file.type && file.type.startsWith('image/');
    },
    getURL(file){
      return URL.createObjectURL(file);
    },
    handleFileChange(event) {
      const files = Array.from(event.target.files);
      // console.log(files);
      // 检查文件类型
      const validTypes = ['image/png', 'image/jpeg', 'video/mp4'];
      const validFiles = files.filter(file => validTypes.includes(file.type));
      console.log(validFiles);
      // 如果有不支持的文件类型，提醒用户并返回
      if (validFiles.length < files.length) {
        alert("不支持的文件格式。请上传png、jpg或mp4格式的文件。");
        return;
      }

      // 限制selectedFiles数组的大小不超过3
      const remainingSlots = 3 - this.selectedFiles.length;
      const newFiles = validFiles.slice(0, remainingSlots);
      const maxFiles = 3;
      const maxSize = 10485760; // 10MB limit

      // 检查文件数量
      if (files.length > maxFiles) {
        alert(`您只能上传最多 ${maxFiles} 个文件。`);
        return;
      }

      // 检查文件大小
      const isAnyFileTooLarge = files.some(file => file.size > maxSize);
      if (isAnyFileTooLarge) {
        alert("所有文件必须小于10MB，请重新选择文件。");
      return;
      }
      
      // 如果选择的文件超过3个，提醒用户
      if (this.selectedFiles.length + newFiles.length > 3) {
        alert("最多只能上传3个文件。");
        return;
      }

    //  console.log(validFiles);

      // 更新selectedFiles数组，只包含到3个文件的限制
      this.selectedFiles = [...this.selectedFiles, ...validFiles];
   //   console.log(this.selectedFiles);

      // 更新input file控件以反映文件的更改
      if (this.selectedFiles.length === 3) {
        event.target.disabled = true;
      } else {
        event.target.disabled = false;
        event.target.value = ''; // 重置input file控件，以便用户可以重新选择文件
      }

      // 清除 input 的已选择文件
      if (this.$refs.fileInput) {
        this.$refs.fileInput.value = '';
      }

      // 如果选择的文件少于3个，则确保input不是disabled
      if (this.selectedFiles.length < 3) {
        event.target.disabled = false;
      }
    },
    goBack() {
      this.$router.go(-1);
    },
    removeFile(index) {
      URL.revokeObjectURL(this.selectedFiles[index]); // 释放内存中的URL
      this.selectedFiles.splice(index, 1); // 从数组中移除选定文件

      // 如果现在少于3个文件，确保input是可用的
      if (this.selectedFiles.length < 3 && this.$refs.fileInput) {
        this.$refs.fileInput.disabled = false;
      }
      // 重置input的value
      if (this.$refs.fileInput) {
        this.$refs.fileInput.value = '';
      }
    },
    triggerFileInput() {
      // 使用 ref 访问文件输入并触发点击事件
      if (this.$refs.fileInput) {
        this.$refs.fileInput.click();
      } else {
        console.error('未找到文件输入框。');
      }
    },
    prevImage() {
      // 显示上一张图片的逻辑
      if (this.selectedFiles.length > 0) {
        this.currentPreviewIndex = (this.currentPreviewIndex - 1 + this.selectedFiles.length) % this.selectedFiles.length;
      }
    },
    nextImage() {
      // 显示下一张图片的逻辑
      if (this.selectedFiles.length > 0) {
        this.currentPreviewIndex = (this.currentPreviewIndex + 1) % this.selectedFiles.length;
      }
    }
    // 其他方法...
  },
  mounted() {
    // 初始化默认选项
    this.updateSubcategories();
  },
  computed: {
    currentFile() {
      return this.selectedFiles[this.currentPreviewIndex];
    },
    isFileInputDisabled() {
      return this.selectedFiles.length >= 3;
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
#a{

    width:100%;
    height:200px;
    text-align:center;
}
#b{
    text-align:center;
    color: red;
    font-size: 22px;
    font-family:KaiTi;
}
a{
	text-decoration:none;
}
body {
	background-image:url(~@/assets/img/beijing2.jpg);
	font-family: KaiTi;
	background-size: cover; 
	margin:0px;
	padding:0px;
}

h2{
	text-align:center;
	font-family:KaiTi;
}

.main{
    justify-content: space-between;
    max-width: 500px;
    margin: 50px auto;
    border: 2px solid #00BFFF;
    border-radius: 15px; 
    background-color: white;
    padding: 30px;
    -moz-border-radius: 5px;
    -webkit-border-radius: 5px;
    background: #C0C0C0;
    }
.main h1{
	font-size: 24px;
	font-family:KaiTi;
	text-align:center;
    padding: 8px 0px 16px 10px;
    color:black;
    border-bottom: 30px;
    }
.text1{
     margin-left: 3px;
     }
.main label>span{
     width: 30%;
     display: inline-block;
     text-align: left;
     padding-right: 10px;
     margin-bottom: 10px;
     }
.main input[type="text"],
.main input[type="file"]{
     width: 65%;  /* 调整宽度 */
     padding: 10px;  /* 增加内边距 */
     border: 1px solid #DCDCDC;
     border-radius: 5px; 
}

.main .button
.main button{
    width: 65%;  /* 调整宽度 */
        padding: 10px;  /* 增加内边距 */
        border: 1px solid #DCDCDC;
        border-radius: 5px; 
}
.main button {
    background-color: #FFD700;  /* 黄色按钮 */
}
.main .button:hover{
     color:  #333;
     background-color:  #EBEBEB ;
}
button{
     padding: 8px 24px 8px 24px;
     margin-top: 1px;
     margin-bottom: 8px;
     border: none;
     border-radius: 4px;
     -moz-border-radius: 4px;
     -webkit-border-radius: 4px;
     font-weight: bold;
     text-shadow: 1px 1px 1px #FFE477;
     box-shadow: 1px 1px 1px  #3D3D3D;
     -moz-box-shadow: 1px 1px 1px  #3D3D3D;
     -webkit-box-shadow: 1px 1px 1px  #3D3D3D;
     color: #585858;
     background: #f6ff0a;
      cursor: pointer
}
.container {
    display: flex; /* 使用flex布局 */
}
.left-div, .right-div {
    flex: 1; /* 不伸展，不收缩，基础宽度为210px */
    padding: 20px; /* 添加一些内边距 */
}
#pictureInput {
    bottom: 0px;
    left: 0%;
    border: none;
}

#imagePreview {
    width: 100%; /* 设置图片宽度为100% */
    object-fit: contain; /* 保证图片始终在边框内且保持其原始的宽高比 */
    display: block;  
    position: relative;
    opacity: 0;
}
.left-div center {
    width: 240px; /* 您可以根据需要调整此值 */
    height: 220px;
    position: relative;
    display: flex;
    align-items: center;
    justify-content: center;
}
.submit-button-container {
    margin-bottom: 200px;  /* adjust this value to achieve desired positioning */
    margin-left: 70px;
    padding: 8px 24px 8px 24px;
     margin-bottom: 8px;
     border: none;
     border-radius: 4px;
     -moz-border-radius: 4px;
     -webkit-border-radius: 4px;
     font-weight: bold;
     text-shadow: 1px 1px 1px #FFE477;
     box-shadow: 1px 1px 1px  #3D3D3D;
     -moz-box-shadow: 1px 1px 1px  #3D3D3D;
     -webkit-box-shadow: 1px 1px 1px  #3D3D3D;
     color: #585858;
     background-color: rgb(255, 215, 0);
      cursor: pointer;
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
    background-image: url('~@/assets/img/a.jpg'); /* 更改为您自己的图片路径 */
    background-size: cover; /* 调整背景图片大小以填充整个容器 */
    background-position: center; /* 居中显示背景图片 */
    opacity: 1; /* 调整透明度（0.8表示80%的不透明度） */
}
#preview {
    /* 设置预览窗口的大小和样式 */
    width: 200px;
    height: 200px;
    border: 1px solid #ccc;
    position: relative;
    overflow: hidden;
}

#preview img {
    /* 设置图片的大小和位置 */
    width: 100%;
    height: 100%;
    position: absolute;
    top: 0;
    left: 0;
}

#preview button {
    /* 设置删除按钮的样式和位置 */
    position: absolute;
    top: 0;
    right: 0;
    background: rgba(255, 255, 255, 0.5);
    border: none;

}

</style>
