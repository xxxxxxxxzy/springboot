<template>
<div>


  <div style="padding: 10px;">
    <el-input style="width: 300px;" placeholder="请输入内容" suffix-icon="el-icon-search"
              v-model="username"></el-input>
    <el-input class="ml-5" style="width: 300px;" placeholder="请输入内容" suffix-icon="el-icon-search"
              v-model="phone"></el-input>
    <el-button class="ml-5" type="primary" @click="load">查询</el-button>
    <el-button type="warning" @click="reset">重置</el-button>
  </div>
  <div style="margin: 10px;">
    <el-button class="ml-5" type="primary" @click="handleAdd">新增<i class="el-icon-circle-plus"
                                                                     style="margin-left: 6px;"></i></el-button>
    <el-popconfirm confirm-button-text='确定' cancel-button-text='我再想想' icon="el-icon-info" icon-color="red"
                   title="你确定删除吗？" @confirm="delBatch">
      <el-button class="ml-5" type="danger" slot="reference">批量删除<i class="el-icon-remove" style="margin-left: 6px;"></i></el-button>
    </el-popconfirm>
    <el-upload action="http://localhost:9090/user/import" :show-file-list="false" accept="'xlsx" :on-success="handleExcelImportSuccess" style="display: inline-block">
    <el-button class="ml-5" type="primary">导入<i class="el-icon-download" style="margin-left: 6px;"></i></el-button>
    </el-upload>
    <el-button class="ml-5" type="primary"@click="exp">导出<i class="el-icon-upload2" style="margin-left: 6px;"></i></el-button>
  </div>

  <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'" @selection-change="handleSelectionChange">
    <el-table-column type="selection" width="55">
    </el-table-column>
    <el-table-column prop="id" label="ID" width="50">
    </el-table-column>
    <el-table-column prop="username" label="姓名" width="100">
    </el-table-column>
    <el-table-column prop="email" label="邮箱" width="200">
    </el-table-column>
    <el-table-column prop="qq" label="QQ" width="150">
    </el-table-column>
    <el-table-column prop="wechat" label="微信" width="150">
    </el-table-column>
    <el-table-column prop="phone" label="电话号码" width="150">
    </el-table-column>
    <el-table-column label="操作">
      <template slot-scope="scope">
        <el-button type="warning" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit"
                                                                        style="margin-left: 5px"></i></el-button>
        <el-popconfirm confirm-button-text='好的' cancel-button-text='我再想想' icon="el-icon-info" icon-color="red"
                       title="你确定删除吗？" @confirm="del(scope.row.id)">
          <el-button type="danger" slot="reference" class="ml-5">删除<i class="el-icon-close"
                                                                        style="margin-left: 5px"></i></el-button>
        </el-popconfirm>
      </template>
    </el-table-column>
  </el-table>
  <div style="padding: 10px;">
    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageNum"
                   :page-sizes="[2, 5, 10, 20]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper"
                   :total="total">
    </el-pagination>
  </div>
  <el-dialog title="用戶信息" :visible.sync="dialogFormVisible" width="40%">
    <el-form label-width="70px">
      <el-form-item label="用戶名">
        <el-input v-model="form.username" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="form.email" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="QQ">
        <el-input v-model="form.qq" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="微信">
        <el-input v-model="form.wechat" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="电话号码">
        <el-input v-model="form.phone" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogFormVisible = false">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>
</div>
</template>

<script>
export default {
  name: "User",
  data() {
    return{
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      username: "",
      phone: "",
      dialogFormVisible: false,
      form: {},
      multipleSelection: [],
      // headerBg:'headerBg'
    }
  },
  created() {
    this.load()
  },
  methods :{
    // collapse() {       //点击触发
    //   this.isCollapse = !this.isCollapse
    //   if (this.isCollapse) {            //收缩
    //     this.sideWidth = 64
    //     this.collapseBtnClass = 'el-icon-s-unfold'
    //     this.logoTextShow = false
    //   } else {//展开
    //     this.sideWidth = 200
    //     this.collapseBtnClass = 'el-icon-s-fold'
    //     this.logoTextShow = true
    //   }
    // }
    load() {
      this.request.get("/user/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          username: this.username,
          phone: this.phone
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.records
        this.total = res.total

      })

    },
    reset() {
      this.username = ""
      this.phone = ""
      this.load()
    },
    handleAdd() {
      this.dialogFormVisible = true
      this.form = {}
    },
    save() {
      this.request.post("/user", this.form).then(res => {
        if (res) {
          this.$message.success("保存成功")
          this.dialogFormVisible = false
          this.load()
        } else {
          this.$message.error("保存失败")
        }
      })
    },
    handleEdit(row) {
      this.form = Object.assign({}, row)
      this.dialogFormVisible = true

    },
    del(id) {
      this.request.delete("/user/" + id).then(res => {
        if (res) {
          this.$message.success("删除成功")
          this.load()
        } else {
          this.$message.error("删除失败")
        }
      })
    },
    handleSelectionChange(val){
      console.log(val)
      this.multipleSelection = val
    },
    delBatch(){
      let ids = this.multipleSelection.map(v => v.id)
      this.request.post("/user/del/batch" ,ids).then(res => {
        if (res) {
          this.$message.success("批量删除成功")
          this.load()
        } else {
          this.$message.error("批量删除失败")
        }
      })
    },
    // .then(res => res.json()).then(res => {
    // console.log(res)
    // this.total = res.total
    // this.tableData = res.records
    // })
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.load()
    },
    exp(){
      window.open("http://localhost:9090/user/export")
    },
    handleExcelImportSuccess(){
      this.$message.success("文件导入成功")
      this.load()
    }
  }
}
</script>

<style>
  .headerBg {
    background: #eee!important;
  }
</style>