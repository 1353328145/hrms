<template>
  <div>
    <el-image
      class="img"
      :src="headUrl + url">
    </el-image>
    <span class="text"> {{ employName }} </span>
    <el-tag v-if="role != null" class="text"> {{ role.roleName }} </el-tag>
    <el-tag v-if="roleName != null" class="text"> {{ roleName }} </el-tag>
  </div>
</template>

<script>
    export default {
      name: "EmployInfoInline",
      data(){
          return{
            headUrl:'http://localhost:8080/employ/img?key='
          }
      },
      props:['employName','url','role','roleName']
    }
</script>

<style scoped>
  .img{
    width: 30px;
    height: 30px;
    border-radius: 100px
  }
  .text{
    font-weight: bolder;
    display: inline-block;
    vertical-align: middle;
    margin-bottom: 20px;
    margin-left: 10px;
  }
</style>
