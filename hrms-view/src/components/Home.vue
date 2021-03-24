<template>
      <el-container class="home-container">
        <el-header class="home-header">
          <div id="theme">
            <img src="../assets/logo.jpeg" id="logoImg"/>
            <span>人力资源后台管理系统</span>
          </div>
          <div>

            <el-button type="info" @click="logout">退出</el-button>
          </div>

        </el-header>
        <el-container>
          <el-aside width="200px" class="home-aside">
            <el-button @click="openAndClose" style="width: 100%">{{ ocMsg }}</el-button>

            <el-menu default-active="1-4-1" class="el-menu-vertical-demo" :collapse="isCollapse" :router="true">
              <el-menu-item index="/home/main">
                <i class="el-icon-s-opportunity"></i>
                <span slot="title">首页</span>
              </el-menu-item>
              <el-submenu index="userCenter">
                <template slot="title">
                  <i class="el-icon-user"></i>
                  <span slot="title">个人中心</span>
                </template>
                <el-submenu index="user">
                  <span slot="title">用户</span>
                  <el-menu-item index="/home/user_show">个人主页</el-menu-item>
                  <el-menu-item index="/home/user_collection">收藏</el-menu-item>
                  <el-menu-item index="/home/user_message">消息</el-menu-item>
                </el-submenu>
                <el-submenu index="goods">
                  <span slot="title">我的商铺</span>
                  <el-menu-item index="/goods/goods_info">上架详情</el-menu-item>
                  <el-menu-item index="1-2-2">数据分析</el-menu-item>
                </el-submenu>
              </el-submenu>
            </el-menu>
          </el-aside>
          <el-main class="home-main">
            <router-view></router-view>
          </el-main>
        </el-container>
      </el-container>
</template>

<script>
    export default {
        name: "Home",
        data(){
          return {
            isLogin:false,
            isCollapse:false,
            ocMsg:"关闭"
          }
        },
      methods:{
        openAndClose:function(){
          this.isCollapse=!this.isCollapse;
          if (this.isCollapse){
            this.ocMsg = '打开';
          }else{
            this.ocMsg = '关闭';
          }
        },
        logout : function (){
        }
      },
      created() {
      }
    }
</script>

<style scoped>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
}
#logoImg{
  height: 65px;
  width: 65px;
}
#theme{
  display: flex;
  align-items: center;
}
.home-container{
  height: 100%;
}
.home-header{
  background-color: #6E93AD;
  display: flex;
  justify-content: space-between;
  padding: 0;
  align-items: center;
  color: #eeeeee;
  font-size: 20px;
}
.home-aside{
  /*background-color: #6E9390;*/
}
.home-main{
  background-color: #EAEDF1;
}

</style>
