<template>
  <div class="container" v-if="isLoggedIn">
    <h1>上传多个商品</h1>
    <!-- 表单和输入字段 -->
    <div>
      <div class="form-group">
        <label for="goodName">商品名称：</label>
        <input type="text" v-model="goodName" placeholder="请输入商品名称" required>
        <span v-if="errors.goodName" class="error">{{ errors.goodName }}</span>
      </div>
      <div class="form-group">
        <label for="price">商品价格：</label>
        <input type="text" v-model="price" placeholder="请输入商品价格" required>
        <span v-if="errors.price" class="error">{{ errors.price }}</span>
      </div>

      <div class="form-group">
        <label for="stock">商品数量：</label>
        <input type="text" v-model="stock" placeholder="请输入商品库存" required>
        <span v-if="errors.stock" class="error">{{ errors.stock }}</span>
      </div>

      <div class="form-group">
        <label for="description">商品描述：</label>
        <input type="text" v-model="description" placeholder="请输入商品描述" required>
        <span v-if="errors.description" class="error">{{ errors.description }}</span>
      </div>
      <div class="form-group">
          <label for="kind">商品大类：</label>
          <select v-model="selectedKind" @change="updateSubcategories">
            <option v-for="kind in kinds" :key="kind.value" :value="kind.value">{{ kind.text }}</option>
          </select>
      </div>
      <div class="form-group">
        <label for="subkind">商品子类：</label>
        <select v-model="selectedSubkind">
          <option v-for="subkind in subkinds" :key="subkind" :value="subkind">{{ subkind }}</option>
        </select>
      </div>
      <label for="kind">商品图片：</label>
      <div id="preview">
          <!-- 预览窗口 -->
          <div v-if="selectedFiles.length > 0">
            <div v-for="(file, index) in selectedFiles" :key="index" v-show="index === currentPreviewIndex">
              <img v-if="file.isImage" :src="file.url" alt="预览图">
              <video v-else controls :src="file.url"></video>
              <button @click="removeFile(index)">X</button>
            </div>
          </div>
          <div v-else>
            没有选择文件
          </div>
      </div>
      <button type="button" @click="prevImage">＜</button>&nbsp;
      <button type="button" @click="nextImage">＞</button>

      <!-- 底部是一个上传按钮，在上传图片为空的时候时它显示的是“上传图片/视频”，在有内容的时候为“继续上传”-->
      <input type="file" :disabled="isFileInputDisabled" ref="fileInput" @change="handleFileChange" accept="image/png, image/jpeg, video/mp4" multiple style="display: none;">
      <!-- 添加自定义的上传按钮 -->
      <button type="button" @click="triggerFileInput">上传图片/视频</button>
      <!-- 图片错误提示 -->
      <span class="error" v-if="errors.pictureError">{{ errors.pictureError }}</span><br>
      <button @click="addProduct">添加商品</button>
    </div>

    <!-- 商品列表展示 -->
    <table>
      <thead>
        <tr>
            <th>商品名称</th>
            <th>价格</th>
            <th>数量</th>
            <th>描述</th>
            <th>展示内容</th>
            <th>商品类别</th>
            <th>子类别</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(product, index) in productList" :key="index">
        <td>{{ product.goodName }}</td>
        <td>{{ product.price }}</td>
        <td>{{ product.stock }}</td>
        <td>{{ product.description }}</td>
        <td>
          <div v-for="(file, fileIndex) in product.mediaFiles" :key="fileIndex">
            <img v-if="file.isImage" :src="file.url" alt="Image Preview" style="max-width: 100px; max-height: 100px; object-fit: contain;">
            <video v-else controls :src="file.url" style="max-width: 100px; max-height: 100px;"></video>
          </div>
        </td>
        <td>{{ product.kind }}</td>
        <td>{{ product.subkind }}</td>
        </tr>
      </tbody>
    </table>
    <!-- 确认发布所有商品按钮 -->
    <button @click="submitProducts">确认发布所有商品</button>
  </div>
  <div v-else>
    您还未登录，请先<a href="/login">登录</a>
  </div>
</template>

<script>
export default {
  data() {
    return {
      products: [], // 用于存储商品的数组
      isLoggedIn: true,
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
      productList: [],
      errors: {
        pictureError: '',
        goodName: '',
        price: '',
        stock: '',
        description: ''
      },
    };
  },
  methods: {
    addProduct() {
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

      // 创建新商品对象
      const newProduct = {
        goodName: this.goodName,
        price: this.price,
        stock: this.stock,
        description: this.description,
        kind: this.selectedKind,
        subkind: this.selectedSubkind,
        mediaFiles: this.selectedFiles.map(f => ({ url: f.url, isImage: f.isImage }))
      };

      // 添加商品到列表
      this.productList.push(newProduct);

      // 清空输入
      this.goodName = '';
      this.price = '';
      this.stock = '';
      this.description = '';
      this.selectedFiles = [];

      alert("商品已添加"); // 提示用户
    },
    isImage(file) {
      return !(file && file.type) || file && file.type && file.type.startsWith('image/');
    },
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
    handleFileChange(event) {
      const files = Array.from(event.target.files);

      // 检查文件类型
      const validTypes = ['image/png', 'image/jpeg', 'video/mp4'];
      const validFiles = files.filter(file => validTypes.includes(file.type));

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

      // 创建 URL 并更新 selectedFiles 数组
      const selectedFilesWithUrls = validFiles.map(file => ({
        ...file,
        url: URL.createObjectURL(file),
        isImage: file.type.startsWith('image/')
      }));

      // 更新selectedFiles数组，只包含到3个文件的限制
      this.selectedFiles = [...this.selectedFiles, ...selectedFilesWithUrls];

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
      URL.revokeObjectURL(this.selectedFiles[index].url); // 释放内存中的URL
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
    submitProducts() {
      console.log('提交的产品:', this.productList);

      // 模拟异步请求
      new Promise((resolve) => {
        setTimeout(() => {
          resolve('提交成功');
        }, 2000); // 模拟2秒的网络延迟
      })
      .then((message) => {
        console.log(message);
        // 这里可以执行提交成功后的操作
        this.productList = []; // 清空产品列表
        alert('所有商品已成功发布！');
      })
      .catch((error) => {
        console.error('模拟错误:', error);
        // 错误处理
        alert('发布商品时发生错误。');
      });
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
    // 其他方法
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

<style scoped>
        .container {
            max-width: 600px;
            margin: 0 auto;
            padding: 20px;
            background-color: #f5f5f5;
            border: 1px solid #ddd;
            border-radius: 5px;
            overflow-y: auto;
        }
        h1 {
            color: #333;
            text-align: center;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        input[type="text"],
        input[type="file"] {
            width: 50%;
            padding: 10px;
            margin: 5px 0;
            border: 1px solid #ccc;
            border-radius: 3px;
        }
        button {
            background-color: #007BFF;
            color: #fff;
            border: none;
            padding: 10px 20px;
            border-radius: 3px;
            cursor: pointer;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        
        th, td {
            padding: 10px;
            text-align: left;
            border: 1px solid #dddd; /* 添加边距 */
        }
        
        th {
            background-color: #007BFF;
            color: #fff;
        }
        img {
            max-width: 100px;
            max-height: 100px;
             object-fit: contain;
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
