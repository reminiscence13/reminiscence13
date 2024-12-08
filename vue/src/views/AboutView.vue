<template>
  <div>
    <div style="margin-bottom: 15px">
      <el-input placeholder="请输入姓名" v-model="params.name" clearable style="width:200px"></el-input>
      <el-input placeholder="请输入电话" v-model="params.phone" clearable style="width:200px; margin-left: 5px"></el-input>
      <el-button type="warning" style="margin-left: 10px" @click="FindBySearch()">查询</el-button>
      <el-button type="primary" style="margin-left: 10px" @click="add()">新增</el-button>
    </div>
    <div>
      <el-table :data="tableData" style="width: 100%">
        <el-table-column prop="name" label="姓名"></el-table-column>
        <el-table-column prop="phone" label=电话></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="primary" @click="edit(scope.row)">编辑</el-button>
            <el-popconfirm title="确定删除吗？" @confirm="del(scope.row.id)">
              <el-button slot="reference" type="danger" style="margin-left: 5px" >删除</el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div>
      <el-dialog title="填写信息" :visible.sync="dialogFormVisible" width="30%">
        <el-form :model="form">
          <el-form-item label="姓名" label-width="15%">
            <el-input v-model="form.name" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="电话" label-width="15%">
            <el-input v-model="form.phone" autocomplete="off" style="width: 90%"></el-input>
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
        name:'',
        phone:'',
        pageNum:1,
        pageSize:5
      },
      tableData: [],
      total:0,
      dialogFormVisible: false,
      form:{}
    }
  },

  //页面在加载的时候在create里面做一些事情
  created() {
    this.FindBySearch();
  },

  //定义一些在页面上控件出发的事件调用的方法
  methods:{
    load(){
      request.get("/user").then(res =>{
        if (res.code === "0"){
          this.tableData = res.data.list;

        } else{

        }
      })
    },

    //查询
    FindBySearch(){
      request.get("/user/search",{
        params: this.params
      }).then(res=>{
        if (res.code === '0'){
          this.tableData = res.data.list;
          this.total = res.data.total;
        } else{

        }
      })
    },

    //更改一页显示的条数
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

    //新增信息
    submit(){
      request.post("/user",this.form).then(res=>{
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
      request.delete("/user/"+id).then(res=>{
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