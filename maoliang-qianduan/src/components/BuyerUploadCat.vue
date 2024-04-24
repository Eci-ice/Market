<template>

  <div class="main">
    <h1>请您添加猫咪信息！</h1>

    <div class="container">
      <div class="left-div" style="height: 300px;">
        <!-- 左侧div -->
        <!-- 顶部是一个返回按钮
        <button type="button" @click="goBack">返回</button>
        <button type="button" @click="goBack">返回</button>
         中间是一个固定尺寸的预览窗口，用于显示用户上传的图片或视频，最多三个,其中每个图片都支持右上角显示x删除 -->

        <div id="preview">
          <!-- 预览窗口 -->
          <div v-if="selectedFiles.length > 0">

            <img src="@/assets/img/catbackground.png" style="max-width: 50%" alt="测试图">

            <button >X</button>

          </div>
          <div v-else>
            请上传猫咪照片！
          </div>
        </div>
        <button type="button" >＜</button>&nbsp;
        <button type="button" >＞</button><br>

      </div>
      <div class="right-div">
        <!-- 右侧div -->

        <div class="form-group">
          <label for="goodName">猫咪姓名：</label>
          <input type="text" v-model="goodName" placeholder="请输入猫咪的姓名" required><br><br>

        </div>
        <div class="form-group">
          <label for="price">猫咪年龄：</label>
          <input type="number" v-model="price" placeholder="请输入猫咪年龄" required><br><br>

        </div>
        <div class="form-group">
          <label for="kind">猫咪性别：</label>
          <select v-model="selectedKind" @change="updateSubcategories">
            <!--<option v-for="kind in kinds" :key="kind.value" :value="kind.value">{{ kind.text }}</option>-->
          </select><br><br>
        </div>
        <div class="form-group">
          <label for="price">猫咪体重：</label>
          <input type="number" v-model="price" placeholder="请输入猫咪体重" required><br><br>
        </div>
        <div class="form-group">
          <label for="kind">是否绝育：</label>
          <select v-model="selectedKind" @change="updateSubcategories">
            <!--<option v-for="kind in kinds" :key="kind.value" :value="kind.value">{{ kind.text }}</option>-->
          </select><br><br>
        </div>
        <div class="form-group">
          <label for="kind">疫苗：</label>
          <select v-model="selectedKind" @change="updateSubcategories">
            <!--<option v-for="kind in kinds" :key="kind.value" :value="kind.value">{{ kind.text }}</option>-->
          </select><br><br>
        </div>
        <div class="form-group">
          <label for="goodName">猫咪品种：</label>
          <input type="text" v-model="goodName" placeholder="请输入猫咪品种" required><br><br>

        </div>
        <div class="form-group">
          <label for="goodName">猫咪性格：</label>
          <input type="text" v-model="goodName" placeholder="请描述猫咪性格" required><br><br>

        </div>

        <div class="form-group">
          <button type="submit" class="submit-button-container">确认发布</button>
        </div>
        <div class="form-group">
          <button type="submit" class="submit-button-container">取消发布</button>
        </div>
      </div>

    </div>

  </div>

</template>

<script>
export default {
  name: "BuyerUploadCat"
}
</script>

<style type="text/css" scoped>
.container {
  display: flex; /* 使用flex布局 */
}
.submit-button-container {/* 确认发布的按钮 */
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
#preview { /* 展示设置预览窗口的大小和样式 */
  width: 200px;
  height: 200px;
  border: 1px solid #ccc;
  position: relative;
  overflow: hidden;
}
#preview img { /* 设置图片的大小和位置 */
  width: 100%;
  height: 100%;
  position: absolute;
  top: 0;
  left: 0;
}
main button {
  background-color: #FFD700;  /* 黄色按钮 */
}

.main .button
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

.left-div, .right-div {
  background: #C5E1FF;
  flex: 1; /* 不伸展，不收缩，基础宽度为210px */
  padding: 20px; /* 添加一些内边距 */
}

</style>