<template>
  <el-row style="height: 100%;width: 100%">
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
                  style="width: 100%">
                  <el-table-column type="expand">
                    <template slot-scope="props">
                      <el-form label-position="left" inline class="demo-table-expand">
                        <el-form-item label="性别">
                          <span>{{ props.row.sex === 0?'男':'女' }}</span>
                        </el-form-item>
                        <el-form-item label="联系地址">
                          <span>{{ props.row.address }}</span>
                        </el-form-item>
                        <el-form-item label="民族">
                          <span>{{ props.row.national }}</span>
                        </el-form-item>
                        <el-form-item label="身份证号">
                          <span>{{ props.row.idNumber }}</span>
                        </el-form-item>
                        <el-form-item label="最高学历">
                          <span>{{ props.row.culture }}</span>
                        </el-form-item>
                        <el-form-item label="毕业学校">
                          <span>{{ props.row.school }}</span>
                        </el-form-item>
                        <el-form-item label="email">
                          <span>{{ props.row.email }}</span>
                        </el-form-item>
                        <el-form-item label="合同期限">
                          <span>{{ props.row.contract }}</span>
                        </el-form-item>
                        <el-form-item label="生日">
                          <span>{{ props.row.birthday }}</span>
                        </el-form-item>
                        <el-form-item label="聘用来源">
                          <span>{{ props.row.employFrom }}</span>
                        </el-form-item>
                        <el-form-item label="角色职位名">
                          <span>{{ props.row.role.roleName }}</span>
                        </el-form-item>
                        <el-form-item label="角色等级">
                          <span>{{ props.row.role.roleVal }}</span>
                        </el-form-item>
                      </el-form>
                    </template>
                  </el-table-column>
                  <el-table-column
                    label="工号"
                    prop="uid">
                  </el-table-column>
                  <el-table-column
                    label="姓名"
                    prop="name">
                  </el-table-column>
                  <el-table-column
                    label="电话"
                    prop="phone">
                  </el-table-column>
                </el-table>
              </el-row>
              <el-row>
                <el-pagination
                  @current-change="handleEmployChange"
                  background
                  layout="prev, pager, next"
                  :total="employTable.total"
                  :page-size="30">
                </el-pagination>
              </el-row>
            </el-card>
          </el-col>
          <el-col :span="12">
            3424
          </el-col>
        </el-row>
  </el-row>
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
      userList:[],
      role:[],
      employTable:{
        total:0,
        pageNum:1
      }

    }
  },
  methods:{
    handleEmployChange(num){
      this.employTable.pageNum = num;
      this.loadAllEmploy();
    },
    async loadAllEmploy() {
      const res = await this.$http.get("authority/loadEmployForAuth",this.userQueryInfo);
      // if (res.data.flag){
      //   this.userList = res.data.extend.employs;
      // }
    },
    async loadAllRole() {
      const res = await this.$http.get("authority/loadAllRole");
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
