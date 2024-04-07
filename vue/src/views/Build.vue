<template>
    <div>
      <div style="margin: 10px;">
        <el-button class="ml-5" type="primary" @click="handleAdd">新增<i class="el-icon-circle-plus"
                                                                         style="margin-left: 6px;"></i></el-button>
        <el-popconfirm confirm-button-text='确定' cancel-button-text='我再想想' icon="el-icon-info" icon-color="red"
                       title="你确定删除吗？" @confirm="delBatch">
          <el-button class="ml-5" type="danger" slot="reference">批量删除<i class="el-icon-remove" style="margin-left: 6px;"></i></el-button>
        </el-popconfirm>
  
      </div>
  
      <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55">
        </el-table-column>
        <el-table-column prop="id" label="ID" width="50">
        </el-table-column>
        <el-table-column prop="foodname" label="美食名称" width="100">
        </el-table-column>
        <el-table-column prop="foodintroduce" label="美食简介" width="250">
        </el-table-column>
        <el-table-column prop="foodmethod" label="美食做法" width="250">
        </el-table-column>
        <el-table-column prop="foodpicture" label="美食图片" width="250">
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="warning" @click="handleEdit(scope.row)" style="text-align: center;">编辑<i class="el-icon-edit"
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
      <el-dialog title="美食信息" :visible.sync="dialogFormVisible" width="40%">
        <el-form label-width="70px">
          <el-form-item label="美食名称">
            <el-input v-model="form.foodname" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="美食简介">
            <el-input v-model="form.foodintroduce" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="美食做法">
            <el-input v-model="form.foodmethod" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="美食图片">
            <el-input v-model="form.foodpicture" autocomplete="off"></el-input>
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
    name: "Build",
    data() {
      return {
        tableData: [],
        total: 0,
        pageNum: 1,
        pageSize: 10,
        dialogFormVisible: false,
        form: {},
        multipleSelection: [],
        // headerBg:'headerBg'
      }
    },
    created() {
      this.load()
    },
    methods: {
      load() {
        this.request.get("/food/page", {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize
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
        this.request.post("/food", this.form).then(res => {
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
        this.request.delete("/food/" + id).then(res => {
          if (res) {
            this.$message.success("删除成功")
            this.load()
          } else {
            this.$message.error("删除失败")
          }
        })
      },
      handleSelectionChange(val) {
        console.log(val)
        this.multipleSelection = val
      },
      delBatch() {
        let ids = this.multipleSelection.map(v => v.id)
        this.request.post("/food/del/batch", ids).then(res => {
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
    }
  }
  
  
  </script>
  
  
  <style scoped>
  
  </style>