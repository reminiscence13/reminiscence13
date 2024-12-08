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
          <span class="user-id">欢迎，商家ID：{{ userId }}</span>
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
          <el-menu-item index="/GoodsManagement">
            <i class="el-icon-setting"></i>
            <span slot="title">商品管理</span>
          </el-menu-item>
          <el-menu-item index="/OrderManagement">
            <i class="el-icon-goods"></i>
            <span slot="title">订单管理</span>
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
  name: "Management",
  data() {
    return {
      activeMenu: "/GoodsManagement", // 默认激活的菜单
    };
  },
  computed: {
    userId() {
      // 从 Vuex 获取用户 ID
      return this.$store.state.userId;
    },
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

<style> /*去除衔接处毛边*/
.el-menu{
  border-right: none !important;
}
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