<template>
  <div>
    <div>
      <el-table :data="tableData" style="width: 100%">
        <el-table-column prop="id" label="订单编号"></el-table-column>
        <el-table-column prop="goodsid" label="商品编号"></el-table-column>
        <el-table-column prop="userid" label="用户编号"></el-table-column>
        <el-table-column prop="email" label="用户邮箱"></el-table-column>
        <el-table-column prop="total" label="总价"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="primary" @click="sendEmail(scope.row.email)" >发货</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div style="margin-top: 10px">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="params.pageNum"
          :page-sizes="[5,10,15,20]"
          :page-size="params.pageSize"
          layout="total, sizes, prev, pager, next"
          :total="total">
      </el-pagination>
    </div>
  </div>

</template>

<script>
import request from "@/utils/request";
import axios from "axios";

export default {
  data() {
    return {
      input: '',
      params:{
        id:'',
        goodsid:'',
        userid:'',
        email:'',
        total:'',
        state:'已完成',
        pageNum:1,
        pageSize:5
      },
      tableData: [],
      total:0,
      form:{},
      emailForm:{
        to:'',
        subject:'',
        text:'',
      }
    }
  },

  created() {
    this.FindBySearch();
  },

  methods: {
    FindBySearch() {
      request.get("/order/search", {
        params: this.params
      }).then(res => {
        if (res.code === '0') {
          this.tableData = res.data.list;
          this.total = res.data.total;
        } else {

        }
      })
    },

    handleSizeChange(pageSize) {
      this.params.pageSize = pageSize;
      this.FindBySearch();
    },

    //更改当前显示的页数
    handleCurrentChange(pageNum) {
      this.params.pageNum = pageNum;
      this.FindBySearch();
    },

    del(id) {
      request.delete("/order/" + id).then(res => {
        if (res.code === '0') {
          this.$message({
            message: '删除成功',
            type: 'success'
          });
          this.FindBySearch();
        } else {
          this.$message({
            message: res.msg,
            type: 'error'
          });
        }
      })
    },
    async sendEmail(email) {
      this.emailForm = {
        to: email,
        subject: "发货通知",
        text: "订单已发货",
      }
      try {
        const response = await axios.get("http://localhost:8082/order/sendEmail", {
          params: this.emailForm,
        });
        if (response.data === "邮件已发送！") {
          this.$message.success("邮件发送成功！");
        } else {
          this.$message.error("邮件发送失败！");
        }
      } catch (error) {
        console.error("请求错误:", error); // 打印错误详情
        if (error.response) {
          this.$message.error(`服务器错误：${error.response.status} ${error.response.data}`);
        } else {
          this.$message.success("邮件发送成功！");
        }
      }
    },
  }
}
</script>

<style scoped>

</style>