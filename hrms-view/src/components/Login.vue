<template>
    <div class="login_container">
      <div class="login_box">
        <el-form ref="loginFormRef" :model="form"  class="login_form" :rules="rules">
          <el-form-item >
            <span class="font_title">后台管理登录</span>
          </el-form-item>
          <el-form-item prop="uid">
            <el-input v-model="form.uid" prefix-icon="el-icon-user-solid" ></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input v-model="form.password" prefix-icon="el-icon-lock" type="password"></el-input>
          </el-form-item>
          <el-form-item class="btns">
            <el-button type="primary" @click="login()">登录</el-button>
            <el-button type="warning" @click="reset()">重置</el-button>
          </el-form-item>
        </el-form>

      </div>
    </div>
</template>

<script>
    export default {
        name: "Login",
        data(){
          return {
            form:{
              uid :"",
              password: ""
            },
            rules:{
              uid: [
                { required: true, message: '请输入用户名', trigger: 'change' },
              ],
              password: [
                { required: true, message: '请输入密码', trigger: 'change' },
                { min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'change' }
              ],
            },
          }
        },
        methods:{
          //重置表单
          reset:function () {
            this.$refs.loginFormRef.resetFields();
          },
          login:function (){

          }
        }
    }
</script>

<style scoped>
.font_title{
  font-family: "Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","微软雅黑",Arial,sans-serif bold;
  font-size: 25px;

}
.login_form{
  width: 100%;
  position: absolute;
  bottom: 0;
  padding: 0 20px;
  box-sizing: border-box;
}
.btns{
  display: flex;
  justify-content: flex-end;
}
.login_container{
  background-color: bisque;
  height: 100%;
}
.login_box{
  width: 500px;
  height: 300px;
  background-color: beige;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%,-50%);
  border-radius: 30px;
}
.avatar_box{
  height: 130px;
  width: 130px;
  border: 2px solid #eee;
  border-radius: 50%;
  padding: 10px;
  box-shadow: 0 0 10px #ddd;
  position: absolute;
  left: 50%;
  transform: translate(-50%,-50%);
  background-color: #dddddd;
}
#user{
  width: 100%;
  height: 100%;
  border-radius: 50%;
}
</style>
