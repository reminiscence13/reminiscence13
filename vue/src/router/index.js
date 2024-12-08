import Vue from 'vue'
import VueRouter from 'vue-router'
import LoginView from "@/views/LoginView";
import LayoutView from "@/views/Layout";
import RegisterView from "@/views/RegisterView";
import ShoppingCart from "@/views/ShoppingCart";
import GoodsView from "@/views/GoodsView";
import OrderView from "@/views/OrderView";
import GoodsManagementView from "@/views/GoodsManagementView";
import Management from "@/views/Management";
import OrderManagement from "@/views/OrderManagement";

Vue.use(VueRouter)

const routes = [
  {
    //二级路由，使登录界面与主页同级
    path:'/',
    name:'Layout',
    component: LayoutView,
    children:[
      {
        path: '/user',
        name: 'user',
        component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
      },
      {
        path: '/ShoppingCart',
        name: 'ShoppingCart',
        component: ShoppingCart
      },
      {
        path: '/Goods',
        name: 'Goods',
        component: GoodsView
      },
      {
        path: '/order',
        name: 'order',
        component: OrderView
      },
    ]
  },
  {
    path: '/Management',
    name: 'Management',
    component: Management,
    children: [
      {
        path: '/GoodsManagement',
        name: 'GoodsManagement',
        component: GoodsManagementView
      },
      {
        path: '/OrderManagement',
        name: 'OrderManagement',
        component: OrderManagement
      }
    ]
  },
  {
    path:'/login',
    name:'login',
    component: LoginView
  },
  {
    path:'/register',
    name:'register',
    component: RegisterView
  },
  {
    path: '*',
    redirect:'/login',
  },

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
