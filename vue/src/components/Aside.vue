<template>
  <el-menu :default-openeds="['1', '3']" style="min-height: 100%; overflow-x: hidden;width: 200px;"
           background-color="rgb(48,65,86)"
           text-color="white"
           :collapse-transition="false"
           :collapse="isCollapse"
           router
           @select="handleSelect"
           >
    <div style="height: 60px;line-height: 60px;text-align: center">
      <img src="../assets/logo.png" alt="" style="width: 30px;position: relative;top: 8px;margin-right: 5px;">
      <b style="color: white;" v-show="logoTextShow">徽文化服务平台后台管理系统</b>
    </div>
    <el-menu-item index="/home">
      <template slot="title">
        <i class="el-icon-house"></i>
        <span slot="title">主页</span>
      </template>
    </el-menu-item>
    <el-submenu index="2">
      <template slot="title"><i class="el-icon-menu"></i>
        <span slot="title">系统管理</span>
      </template>
      <el-menu-item index="/user">
        <i class="el-icon-s-custom"></i>
        <span slot="title">用户管理</span>
      </el-menu-item>
      <el-submenu index="3">
        <template slot="title"><i class="el-icon-more"></i>
          <span slot="title">内容管理</span>
        </template>
        <el-menu-item-group>
          <el-menu-item index="/food">徽州美食</el-menu-item>
          <el-menu-item index="2-2">徽派建筑</el-menu-item>
          <el-menu-item index="2-3">徽州名人</el-menu-item>
          <el-menu-item index="2-4">文房四宝</el-menu-item>
          <el-menu-item index="2-5">新安医学</el-menu-item>
          <el-menu-item index="2-6">徽州美术</el-menu-item>
          <el-menu-item index="2-7">新安理学</el-menu-item>
          <el-menu-item index="2-8">徽商</el-menu-item>
        </el-menu-item-group>
      </el-submenu>
    </el-submenu>
  </el-menu>
</template>

<script>
export default {
  name: "Aside",
  props: {
    isCollapse: Boolean,
    logoTextShow: Boolean
  },

  methods: {
    handleSelect(index) {
      console.log(this.$router.options.routes);

    }
  }
}
</script>

<style scoped>

</style>