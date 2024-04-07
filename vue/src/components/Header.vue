<template>
  <div style="font-size: 12px;line-height: 60px;display: flex">
    <div style="flex: 1;font-size: 18px;">
        <span :class="collapseBtnClass" style="cursor: pointer" @click="collapse"></span>
        <el-breadcrumb separator="/" style="display: inline-block;margin-left: 8px;">
    <el-breadcrumb-item :to="'/'">首页</el-breadcrumb-item>
    <el-breadcrumb-item>{{ currentPathName }}</el-breadcrumb-item>
  </el-breadcrumb>
      </div>
      <el-dropdown style="width: 80px;cursor: pointer">
        <span>王小虎</span><i class="el-icon-arrow-down" style="margin-left: 5px;"></i>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>个人中心</el-dropdown-item>
          <el-dropdown-item>
            <router-link to="/login" style="text-decoration: none;">退出登录</router-link>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
  </div>
</template>
  
  <script>
import { Collapse } from 'element-ui';



  export default {
    name: "Header",
    props: {
        collapseBtnClass: String,
        collapse: Boolean,
    },

    computed :{
      currentPathName () {
        return this.$store.state.currentPathName;
      }
    },
    watch: {          //监听路由变化
      currentPathName (newVal,oldVal) {
        console.log(newVal)       //取出route里面的当前路由信息
      }
    },

    data(){
    return{
      // currentPathName: ''
    }
  },
  // created() {
  //   console.log(this.$route);
  // }
  }
  </script>
  
  <style scoped>
  
  </style>