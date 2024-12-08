<template>
  <div>
    <div>
      <el-table :data="tableData" style="width: 100%">
        <el-table-column prop="id" label="编号"></el-table-column>
        <el-table-column prop="goodsid" label="商品号"></el-table-column>
        <el-table-column prop="userid" label="用户id"></el-table-column>
        <el-table-column prop="price" label=价格></el-table-column>
        <el-table-column prop="quantity" label=数量></el-table-column>
        <el-table-column prop="total" label=总价></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="danger" @click="del(scope.row.id)">删除</el-button>
            <el-button type="primary" @click="buy(scope.row)">结算</el-button>
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

export default {
  name: "ShoppingCart",
  data() {
    return {
      input: '',
      params: {
        id: '',
        goodsid: '',
        userid: '',
        price: 1,
        quantity: '',
        total: '',
        pageNum: 1,
        pageSize: 5
      },
      tableData: [],
      total: 0,
      form: {},
    }
  },

  created() {
    this.FindBySearch();
  },

  methods: {
    FindBySearch() {
      const userid = this.$store.state.userId;
      this.params.userid = userid;
      request.get("/ShoppingCart/search", {
        params: this.params
      }).then(res => {
        if (res.code === '0') {
          this.tableData = res.data.list;
          this.total = res.data.total;
        } else {

        }
      })
    },

    del(id){
      request.delete("/ShoppingCart/"+id).then(res=>{
        if (res.code ==='0'){
          this.$message({
            message:'删除成功',
            type:'success'
          });
          this.FindBySearch();
        } else {
          this.$message({
            message: res.msg,
            type:'error'
          });
        }
      })
    },

    buy(obj){
      const userid = this.$store.state.userId;
      const email = this.$store.state.email;
      this.form = {
        ...obj,
        userid,
        email,
      };
      request.post("/ShoppingCart/buy",this.form).then(res=>{
        if (res.code === '0'){
          this.$message({
            message: '操作成功',
            type: 'success'
          });
          this.FindBySearch();
        } else {
          //新增失败
          this.$message({
            message: res.msg,
            type:'error'
          });
        }
      })
    },
    handleSizeChange(pageSize){
      this.params.pageSize = pageSize;
      this.FindBySearch();
    },

    //更改当前显示的页数
    handleCurrentChange(pageNum){
      this.params.pageNum = pageNum;
      this.FindBySearch();
    },
  }
}
</script>

<style scoped>

</style>