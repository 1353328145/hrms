<template>
      <el-container class="home-container">
        <el-header class="home-header">
          <div id="theme">
            <img src="../assets/logo.jpeg" id="logoImg"/>
            <span>人力资源后台管理系统</span>
          </div>
          <div>
          </div>

        </el-header>
        <el-container>
          <el-aside width="200px" class="home-aside">
            <el-button @click="openAndClose" style="width: 100%">{{ ocMsg }}</el-button>
            <el-menu default-active="1-4-1" class="el-menu-vertical-demo" :collapse="isCollapse" :router="true">
              <el-menu-item v-for="item in menu" :index="item.menuPath">
                <i :class="item.icon"></i>
                <span slot="title">{{ item.info }}</span>
              </el-menu-item>
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
            ocMsg:"关闭",
            menu:[]
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
        async loadMenu() {
          const res = await this.$http.get("authority/loadAllMenu");
          if (res.data.flag){
            this.menu = res.data.extend.menus;
          }else{
            this.$message.error("请检查网络设置");
          }
        },
        logout : function (){
        }
      },
      created() {
          this.loadMenu();
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
  background-color: #EAEDF1;

}
.home-main{
  background-color: #EAEDF1;
}

</style>
