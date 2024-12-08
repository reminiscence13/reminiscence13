<template>
  <div>
    <el-container>
      <el-header height="60px" class="header">
        <div class="header-left">
          <!-- 网站标题或 Logo -->
          <span class="logo">购物网站</span>
        </div>
        <div class="header-right">
          <!-- 用户信息 -->
          <span class="user-id">欢迎，用户ID：{{userId}}</span>
          <el-button type="danger" size="mini" @click="onLogout">退出登录</el-button>
          <el-popconfirm title="确定注销吗？" @confirm="logout()">
            <el-button slot="reference" type="warning" style="margin-left: 5px" >注销</el-button>
          </el-popconfirm>
        </div>
      </el-header>
    </el-container>


    <el-container>
      <el-aside width="200px" class="aside">
        <el-menu
            :default-active="activeMenu"
            class="el-menu-vertical-demo"
            @select="handleMenuSelect"
            router
        >
          <el-menu-item index="/goods">
            <i class="el-icon-goods"></i>
            <span slot="title">商品</span>
          </el-menu-item>
          <el-menu-item index="/ShoppingCart">
            <i class="el-icon-shopping-cart-full"></i>
            <span slot="title">购物车</span>
          </el-menu-item>
          <el-menu-item index="/order">
            <i class="el-icon-document"></i>
            <span slot="title">订单</span>
          </el-menu-item>
        </el-menu>
      </el-aside>

      <el-main>
        <router-view/>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
name: "Layout",
  data() {
    return {
      activeMenu: "/", // 默认激活的菜单
    };
  },
  computed: {
    userId() {
      // 从 Vuex 获取用户 ID
      return this.$store.state.userId;
    },
    Email(){
      return this.$store.state.email;
    }
  },
  methods: {
    onLogout() {
      // 清除用户信息并跳转到登录页
      this.$store.commit("clearUser");
      this.$router.push("/login");
    },
    handleMenuSelect(index) {
      // 处理菜单选择
      this.activeMenu = index;
    },
    logout(){
      this.del(this.userId)
      this.$store.commit("clearUser");
      this.$router.push("/login");
    },
    del(id){
      request.delete("/user/"+id).then(res=>{
        if (res.code ==='0'){
          this.$message({
            message:'已注销',
            type:'success'
          });
        } else {
          this.$message({
            message: res.msg,
            type:'error'
          });
        }
      })
    }
  },
};
</script>

<style>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
  background-color: #409eff; /* Element UI 主色 */
  color: white;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.header-left .logo {
  font-size: 18px;
  font-weight: bold;
}

.header-right {
  display: flex;
  align-items: center;
}

.header-right .user-id {
  margin-right: 20px;
  font-size: 14px;
}
</style>