<template>
  <div style="height: 100%;width: 100%">
        <el-row>
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item><a href="/">权限管理</a></el-breadcrumb-item>
          </el-breadcrumb>
        </el-row>
        <el-row>

        </el-row>
        <el-row class="content" :gutter="20">
          <el-col :span="12" style="height: 100%">
            <el-card class="left">
              <el-row>
                <el-form :inline="true" :model="userQueryInfo" class="demo-form-inline">
                  <el-form-item label="工号">
                    <el-input v-model="userQueryInfo.uid" placeholder="工号"></el-input>
                  </el-form-item>
                  <el-form-item label="姓名">
                    <el-input v-model="userQueryInfo.name" placeholder="工号"></el-input>
                  </el-form-item>
                  <el-form-item label="角色名称查找">
                    <el-select v-model="userQueryInfo.roleId" filterable placeholder="请选择">
                      <el-option
                        v-for="item in role"
                        :key="item.rid"
                        :label="item.roleName"
                        :value="item.rid">
                      </el-option>
                    </el-select>
                  </el-form-item>
                  <el-form-item>
                    <el-button type="primary" @click="onSubmit">查询</el-button>
                  </el-form-item>
                </el-form>
              </el-row>
              <el-row>
                <el-table
                  :data="userList"
                  stripe
                  style="width: 100%">
                  <el-table-column
                    prop="date"
                    label="日期"
                    width="180">
                  </el-table-column>
                </el-table>
              </el-row>
            </el-card>
          </el-col>
          <el-col :span="12">
            3424
          </el-col>
        </el-row>
  </div>
</template>

<script>
export default {
  name: "Authority",
  data(){
    return {
      userQueryInfo:{
        uid:'',
        name:'',
        roleId: -1
      },
      userList:{

      },
      role:[]
    }
  },
  methods:{
    loadAllEmploy(){

    },
    async loadAllRole() {
      const res = await this.$http.get("authority/loadAllRole")
      if (res.data.flag){
        this.role = res.data.extend.role
      }else {
        this.$message.error("请检查网络连接")
      }
    },
    onSubmit(){

    }
  },
  created() {
    this.loadAllEmploy();
    this.loadAllRole();
  }
}
</script>

<style scoped>
.left{
  height: 100%;
}
.content{
  margin-top: 20px;
  height: 100%;
}
</style>
