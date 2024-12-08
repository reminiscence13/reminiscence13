<template>
  <div class="login-container">
    <el-card class="login-card" shadow="hover">
      <div class="tabs-container">
        <el-tabs v-model="activeTab" type="card">
          <el-tab-pane label="登录" name="login">

            <el-form :model="user" ref="user" :rules="rules" label-width="80px">
              <el-form-item label="用户名" prop="username">
                <el-input v-model="user.name" placeholder="请输入用户名" />
              </el-form-item>
              <el-form-item label="密码" prop="password">
                <el-input type="password" v-model="user.password" placeholder="请输入密码" />
              </el-form-item>
              <el-form-item label="邮箱" prop="email">
                <el-input type="email" v-model="user.email" placeholder="请输入邮箱" />
              </el-form-item>
              <el-form-item>
                <el-button type="primary" block @click="Login()">登录</el-button>
              </el-form-item>
            </el-form>
          </el-tab-pane>
          <el-tab-pane label="注册" name="register">
            <div class="user-type-switch">
              <el-radio-group v-model="registerForm.role">
                <el-radio-button  label="user">用户</el-radio-button>
                <el-radio-button  label="merchant">商家</el-radio-button>
              </el-radio-group>
            </div>
            <el-form :model="registerForm" ref="registerForm" :rules="registerRules" label-width="80px">
              <el-form-item label="用户名" prop="username">
                <el-input v-model="registerForm.name" placeholder="请输入用户名" />
              </el-form-item>
              <el-form-item label="密码" prop="password">
                <el-input type="password" v-model="registerForm.password" placeholder="请输入密码" />
              </el-form-item>
              <el-form-item label="确认密码" prop="confirmPassword">
                <el-input type="password" v-model="registerForm.confirmPassword" placeholder="请再次输入密码" />
              </el-form-item>
              <el-form-item label="邮箱" prop="email">
                <el-input type="email" v-model="registerForm.email" placeholder="请输入邮箱" />
              </el-form-item>
              <el-form-item>
                <el-button type="success" block @click="register()">注册</el-button>
              </el-form-item>
            </el-form>
          </el-tab-pane>
        </el-tabs>
      </div>
    </el-card>
  </div>

</template>

<script>

import request from "@/utils/request";

export default {
  name:"Login",
  data() {
    return {
      activeTab: 'login',
      user: {
        name: '',
        password: '',
        email:'',
      },
      registerForm: {
        role:'',
        name: '',
        password: '',
        confirmPassword: '',
        email:''
      },
      rules: {
        name: [{required: true, message: '请输入用户名', trigger: 'blur'}],
        password: [{required: true, message: '请输入密码', trigger: 'blur'}],
        email: [{required: true, message: '请输入邮箱', trigger: 'blur'}],
      },
      registerRules: {
        name: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
        confirmPassword: [
          { required: true, message: '请确认密码', trigger: 'blur' },
          { validator: (rule, value, callback) => {
              if (value !== this.registerForm.password) {
                callback(new Error('两次密码输入不一致'));
              } else {
                callback();
              }
            },
            trigger: 'blur',
          },
        ],
        email: [{required: true, message: '请输入邮箱', trigger: 'blur'}],
      },
    };
  },

  //页面在加载的时候在create里面做一些事情
  created() {
  },

  //定义一些在页面上控件出发的事件调用的方法
  methods:{
    Login(){
      request.post("/user/login",this.user).then(res =>{
        if (res.code ==='0'){
          this.$message({
            message:'登录成功',
            type:'success'
          });
          const{id,email,token,role} = res.data;
          this.$store.dispatch('login',{id,email,token,role});
          if (role === 'user') {
            this.$router.replace('/goods');
          } else if (role === 'merchant') {
            this.$router.replace('/GoodsManagement');
          }
        } else {
          this.$message({
            message:res.msg,
            type:'error'
          });

        }
      })
    },
    register(){
      request.post("/user/register",this.registerForm).then(res =>{
        if (res.code ==='0'){
          this.$message.success(`成功注册为${this.registerForm.role}！`);
          this.activeTab = 'login';
        } else {
          this.$message({
            message:res.msg,
            type:'error'
          });

        }
      })
    },
    change(role){
      this.registerForm.role = role;
    }
  },
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: linear-gradient(to right, #4e54c8, #8f94fb);
}
.login-card {
  width: 450px;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  background: #fff;
}
.tabs-container {
  margin: 20px;
}
.login-title {
  text-align: center;
  font-size: 24px;
  margin-bottom: 20px;
  color: #333;
}
.user-type-switch {
  text-align: center;
  margin-bottom: 20px;
}
</style>