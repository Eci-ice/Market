<template>
  <div>
    <h2>错误界面</h2>
    <div v-if="errorMessage">
      {{ errorMessage }}
    </div>
    <div v-if="redirectTo">
      <router-link :to="redirectTo">返回页面</router-link>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      errorMessage: '',
      redirectTo: null
    };
  },
  created() {
    // 从路由参数中获取错误信息和重定向路径
    this.errorMessage = this.$route.query.err || '';
    this.redirectTo = this.$route.query.to || '/';
  }
};
</script>

<style>
/* 添加您的样式 */
</style>
