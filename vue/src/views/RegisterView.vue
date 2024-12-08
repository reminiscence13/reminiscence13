<template>
  <div>
    <div style="width: 400px; height: 350px; margin: 15px auto; background-color:rgba(152,207,248,0.5); border-radius: 10px">
      <div style="width: 100%; height: 100px; font-size: 30px; line-height: 100px; text-align: center; color: #1e38d5">欢迎注册</div>
      <div style="margin-top: 25px;text-align: center; height: 320px;">
        <el-form :model="user">
          <el-form-item>
            <el-input v-model="user.name" prefix-icon="el-icon-user" style="width: 80%" placeholder="清输入用户名"></el-input>
          </el-form-item>
          <el-form-item>
            <el-input v-model="user.password" prefix-icon="el-icon-lock" style="width: 80%" placeholder="清输入密码"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button style="width: 80%;margin-top: 10px" type="primary" @click="register()">注册</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>

import request from "@/utils/request";

export default {
  name:"Register",
  data() {
    return {
      user:{}
    }
  },

  //页面在加载的时候在create里面做一些事情
  created() {
  },

  //定义一些在页面上控件出发的事件调用的方法
  methods:{


  }
}
</script>