<template>
  <div>
    <div>
      <el-table :data="tableData" style="width: 100%">
        <el-table-column prop="goodsname" label="商品名"></el-table-column>
        <el-table-column prop="description" label="描述"></el-table-column>
        <el-table-column prop="shopname" label="店铺名"></el-table-column>
        <el-table-column prop="price" label=价格></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="primary" @click="Addtocart(scope.row)">加入购物车</el-button>
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
  data() {
    return {
      input: '',
      params:{
        goodsid:'',
        goodsname:'',
        description:'',
        shopname:'',
        price:1,
        pageNum:1,
        pageSize:5
      },
      tableData: [],
      total:0,
      form:{
        goodsid:'',
        userid:'',
        price:''
      },
    }
  },
  computed: {
    userId() {
      // 从 Vuex 获取用户 ID
      return this.$store.state.userId;
    },
  },

  created() {
    this.FindBySearch();
  },

  methods:{
    FindBySearch(){
      request.get("/Goods/search",{
        params: this.params
      }).then(res=>{
        if (res.code === '0'){
          this.tableData = res.data.list;
          this.total = res.data.total;
        } else{

        }
      })
    },

    Addtocart(obj){
      const userid = this.$store.state.userId;
      const email = this.$store.state.email;
      this.form = {
        ...obj,
        userid,
        email,
      };
        request.post("/ShoppingCart/addtocart",this.form).then(res=>{
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
