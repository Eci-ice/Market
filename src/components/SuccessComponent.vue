<template>
  <div class="success-page">
    <h2>成功界面</h2>
    <p v-if="message">{{ message }}</p>

    <router-link v-if="to === '/'" to="/">返回登录界面</router-link>
    <router-link v-if="to === 'buyermain'" to="/buyer">返回买家主页</router-link>
    <router-link v-if="to === 'show_goods'" to="/seller/ShowGoods">返回商品列表</router-link>
    <router-link v-if="to === 'show_pwd'" to="/">返回登录页面</router-link>

    <div v-if="isLoggedIn">
      <router-link v-if="to === 'good'" to="/good">前往商品界面</router-link>
      <router-link v-if="to === 'createorder'" to="/create-order">返回订单修改界面</router-link>

      <div v-if="isSeller">
        <router-link v-if="to === 'changepwd'" to="/seller/change-pwd">返回密码修改界面</router-link>
        <router-link v-if="to === 'order'" to="/seller/order">前往卖家后台</router-link>
        <router-link v-if="to === 'upload_goods'" to="/seller/upload-goods">返回填写界面</router-link>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
  computed: {
    ...mapGetters(['isLoggedIn', 'isSeller']),
    message() {
      // 从路由参数或 Vuex 获取消息
      return this.$route.params.message || '操作成功';
    },
    to() {
      // 从路由参数获取跳转目标
      return this.$route.params.to || '/';
    },
  },
  mounted() {
    console.log("Message:", this.message);
    console.log("To:", this.to);
    }
};
</script>

<style>

</style>
