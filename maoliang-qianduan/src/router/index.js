import {createRouter, createWebHashHistory} from 'vue-router';
import LoginComponent from '@/components/LoginComponent.vue';
import RegisterComponent from '@/components/RegisterComponent.vue';
import ChooseRegister from '@/components/ChooseRegister.vue';
import BuyerMain from '@/components/BuyerMain.vue';
import SellerMain from '@/components/SellerMain.vue';
import UpdatePasswordComponent from '@/components/UpdatePasswordComponent.vue';
import ShowGoods from '@/components/ShowGoods'
import ShowUserInfo from '@/components/ShowUserInfo.vue';
import ShowHistoryGoods from '@/components/ShowHistoryGoods.vue';
import ShowAllUsers from '@/components/ShowAllUsers.vue';
import UploadMultipleGoods from '@/components/UploadMultipleGoods.vue';
import ForgotPasswordComponent from '@/components/ForgotPasswordComponent.vue';
import GuestComponent from '@/components/GuestComponent.vue'; 
import UserOrderHistory from '@/components/UserOrderHistory.vue';
import SuccessComponent from '@/components/SuccessComponent.vue';
import SecretQuestionComponent from '@/components/SecretQuestionComponent.vue';
import BuyerHistory from '@/components/BuyerHistory.vue';
import BuyerLikes from '@/components/BuyerLikes.vue';
import BuyerCart from '@/components/BuyerCart.vue';
import BuyerFillInfo from '@/components/BuyerFillInfo.vue'
import BuyerShop from '@/components/BuyerShop';
import ErrorComponent from "@/components/ErrorComponent.vue";
import ShowSearchGoods from "@/components/ShowSearchGoods.vue";
import ShowSearchHistoryGoods from "@/components/ShowSearchHistoryGoods.vue";
import BuyerSearch from "@/components/BuyerSearch.vue";
import UploadOneGood from "@/components/UploadOneGood.vue";

const routes = [
  { path: '/', component: LoginComponent },
  { path: '/login', component: LoginComponent },
  { path: '/error', component: ErrorComponent },
  { path: '/register', component: RegisterComponent },
  { path: '/choose-register', component: ChooseRegister },
  {
    path: '/buyer',
    name: 'BuyerMain',
    component: BuyerMain,

  },
  {
    path: '/seller',
    name: 'SellerMain',
    component: SellerMain,
    redirect: { name: 'ShowGoods' },
    children: [
      {
        path: 'ShowGoods',
        name: 'ShowGoods',
        component: ShowGoods
      },
      {
        path: 'ShowSearchGoods',
        name: 'ShowSearchGoods',
        component: ShowSearchGoods
      },
      {
        path: 'update-password',
        name: 'UpdatePassword',
        component: UpdatePasswordComponent
      },
      {
        path: 'upload-onegood',
        name: 'UploadOneGood',
        component: UploadOneGood
      },
      {
        path: 'show-userinfo',
        name: 'ShowUserInfo',
        component: ShowUserInfo
      },
      {
        path: 'user-order-history',
        name: 'UserOrderHistory',
        component: UserOrderHistory 
      },
      {
        path: 'show-historygoods',
        name: 'ShowHistoryGoods',
        component: ShowHistoryGoods
      },
      {
        path: 'show-searchhistorygoods',
        name: 'ShowSearchHistoryGoods',
        component: ShowSearchHistoryGoods
      },
      {
        path: 'show-allusers',
        name: 'ShowAllUsers',
        component: ShowAllUsers
      },
      {
        path: 'upload-multiplegoods',
        name: 'UploadMultipleGoods',
        component: UploadMultipleGoods
      },
      // ... 其他子路由
    ]
    // 可以添加其他路由配置，如 meta 数据等
  },
  {
    path: '/forgot-password',
    name: 'ForgotPasswordComponent',
    component: ForgotPasswordComponent // 替换为您的实际组件
  },
  {
    path: '/guest',
    name: 'GuestComponent',
    component: GuestComponent // 替换为您的实际组件
  },
  {
    path: '/success',
    name: 'Success',
    component: SuccessComponent,
    props: true // 启用 props 将路由参数传递到组件
  },
  {
    path: '/secret-question',
    name: 'SecretQuestion',
    component: SecretQuestionComponent
  },
  {
    path: '/buyer-history',
    name: 'buyerHistory',
    component: BuyerHistory
  },
  {
    path: '/buyer-search',
    name: 'buyerSearch',
    component: BuyerSearch
  },
  {
    path: '/likes',
    name: 'BuyerLikes',
    component: BuyerLikes,
  },
  {
    path: '/cart',
    name: 'BuyerCart',
    component: BuyerCart,
  },
  {
    path: '/buyer-fill-info',
    name: 'BuyerFillInfo',
    component: BuyerFillInfo
  },
  {
    path: '/buyer-shop',
    name: 'BuyerShop',
    component: BuyerShop
  },
  // ... 其他路由
];

const router = createRouter({
  history:  createWebHashHistory(),
  routes,
});

export default router;
