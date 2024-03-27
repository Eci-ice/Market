<template>
  <div class="success-page">
    <h2>成功界面</h2>
    <div v-if="SuccessMessage">
      {{ SuccessMessage }}
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
      SuccessMessage: '',
      redirectTo: null
    };
  },
  created() {
    // 从路由参数中获取错误信息和重定向路径
    this.SuccessMessage = this.$route.query.message || '操作成功';
    this.redirectTo = this.$route.query.to || '/';
  }
};
</script>

<style>

</style>
