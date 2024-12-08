<template>
  <div>
      <div style="margin-bottom: 15px">
        <el-input placeholder="请输入名称" v-model="params.goodsname" clearable style="width:200px"></el-input>
        <el-button type="warning" style="margin-left: 10px" @click="FindBySearch()">查询</el-button>
        <el-button type="primary" style="margin-left: 10px" @click="add()">新增</el-button>
      </div>
    <div>
      <el-table :data="tableData" style="width: 100%">
        <el-table-column prop="goodsname" label="商品名"></el-table-column>
        <el-table-column prop="description" label="描述"></el-table-column>
        <el-table-column prop="shopname" label="店铺名"></el-table-column>
        <el-table-column prop="price" label=价格></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="primary" @click="edit(scope.row)">编辑</el-button>
            <el-popconfirm title="确定删除吗？" @confirm="del(scope.row.goodsid)">
              <el-button slot="reference" type="danger" style="margin-left: 5px" >删除</el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div>
      <el-dialog title="填写信息" :visible.sync="dialogFormVisible" width="60%">
        <el-form :model="form">
          <el-form-item label="商品名" label-width="15%">
            <el-input v-model="form.goodsname" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="描述" label-width="15%">
            <el-input v-model="form.description" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
            <el-form-item label="店铺名" label-width="15%">
              <el-input v-model="form.shopname" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="价格" label-width="15%">
            <el-input v-model="form.price" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false,FindBySearch()">取 消</el-button>
          <el-button type="primary" @click="submit()">确 定</el-button>
        </div>
      </el-dialog>
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
      form:{},
    }
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

    handleSizeChange(pageSize){
      this.params.pageSize = pageSize;
      this.FindBySearch();
    },

    //更改当前显示的页数
    handleCurrentChange(pageNum){
      this.params.pageNum = pageNum;
      this.FindBySearch();
    },

    add() {
      this.form = {};
      this.dialogFormVisible = true;
    },

    submit(){
      request.post("/Goods/add",this.form).then(res=>{
        if (res.code === '0'){
          this.$message({
            message: '操作成功',
            type: 'success'
          });
          this.dialogFormVisible = false;
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

    edit(obj){
      this.form = obj;
      this.dialogFormVisible = true;
    },

    del(id){
      request.delete("/Goods/"+id).then(res=>{
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
    }
  }
}
</script>