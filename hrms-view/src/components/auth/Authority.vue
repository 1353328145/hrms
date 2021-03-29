<template>
  <el-row style="height: 100%;width: 100%">
        <el-row>
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item><a href="/">权限管理</a></el-breadcrumb-item>
          </el-breadcrumb>
        </el-row>
        <el-row style="margin-top: 20px">
          <el-tabs type="border-card">
            <el-tab-pane label="权限管理">
              <el-row class="content" :gutter="20">
                <el-col :span="12" style="height: 100%">
                  <el-card shadow="never" class="left">
                    <el-row>
                      <el-form :inline="true" :model="userQueryInfo" class="demo-form-inline">
                        <el-form-item label="工号">
                          <el-input v-model="userQueryInfo.uid" placeholder="工号" size="mini"></el-input>
                        </el-form-item>
                        <el-form-item label="姓名">
                          <el-input v-model="userQueryInfo.name" placeholder="姓名" size="mini"></el-input>
                        </el-form-item><br>
                        <el-form-item label="按照角色查找">
                          <el-select v-model="userQueryInfo.roleId" filterable placeholder="请选择" size="mini">
                            <el-option
                              v-for="item in role"
                              :key="item.rid"
                              :label="item.roleName +'    '+ item.roleVal"
                              :value="item.rid">
                            </el-option>
                          </el-select>
                        </el-form-item>
                        <el-form-item>
                          <el-button type="primary" @click="onSubmit" size="small">查询</el-button>
                        </el-form-item>
                      </el-form>
                    </el-row>
                    <el-row>
                      <el-table
                        :data="userList"
                        style="width: 100%"
                        height="350">
                        <el-table-column type="expand">
                          <template slot-scope="props">
                            <el-form label-position="left" inline class="demo-table-expand">
                              <el-form-item label="工号:">
                                <span>{{ props.row.uid }}</span>
                              </el-form-item>
                              <el-form-item label="性别:">
                                <span>{{ props.row.sex === 0?'男':'女' }}</span>
                              </el-form-item>
                              <el-form-item label="联系地址:">
                                <span>{{ props.row.address }}</span>
                              </el-form-item>
                              <el-form-item label="民族:">
                                <span>{{ props.row.national }}</span>
                              </el-form-item>
                              <el-form-item label="身份证号:">
                                <span>{{ props.row.idNumber }}</span>
                              </el-form-item>
                              <el-form-item label="最高学历:">
                                <span>{{ props.row.culture }}</span>
                              </el-form-item>
                              <el-form-item label="毕业学校:">
                                <span>{{ props.row.school }}</span>
                              </el-form-item>
                              <el-form-item label="email:">
                                <span>{{ props.row.email }}</span>
                              </el-form-item>
                              <el-form-item label="合同期限:">
                                <span>{{ props.row.contract }}</span>
                              </el-form-item>
                              <el-form-item label="生日:">
                                <span>{{ props.row.birthday }}</span>
                              </el-form-item>
                              <el-form-item label="聘用来源:">
                                <span>{{ props.row.employFrom }}</span>
                              </el-form-item>
                              <el-form-item label="角色职位名:" v-if="props.row.role !== null">
                                <span>{{ props.row.role.roleName }}</span>
                              </el-form-item>
                              <el-form-item label="角色等级:" v-if="props.row.role !== null">
                                <span>{{ props.row.role.roleVal }}</span>
                              </el-form-item>
                            </el-form>
                          </template>
                        </el-table-column>
                        <el-table-column
                          label="部门"
                          width="110">
                          <template slot-scope="scope">
                            <span>{{ scope.row.department === null?"暂无部门":scope.row.department.name }}</span>
                          </template>
                        </el-table-column>
                        <el-table-column
                          label="姓名"
                          prop="name"
                          width="100">
                        </el-table-column>
                        <el-table-column
                          label="电话"
                          prop="phone"
                          width="120">
                        </el-table-column>
                        <el-table-column label="操作" >
                          <template slot-scope="scope">
                            <el-button @click.native.prevent="bindingRole(scope.row)" type="text" size="small">
                              绑定角色
                            </el-button>
                            <el-button @click.native.prevent="selectEmploy(scope.row)" type="text" size="small" v-if="scope.row.role!=null && scope.row.role.roleVal === 2">
                              绑定权限
                            </el-button>
                          </template>
                        </el-table-column>
                      </el-table>
                    </el-row>
                    <el-row style="margin-top: 5px">
                      <el-pagination
                        @current-change="handleEmployChange"
                        :current-page.sync="employTable.pageNum"
                        background
                        layout="total,prev, pager, next,jumper"
                        :pager-count="5"
                        :total="employTable.total"
                        :page-size="30">
                      </el-pagination>
                    </el-row>
                  </el-card>
                </el-col>
                <el-col :span="12" style="height: 100%">
<!--                  权限分配-->
                  <el-row v-if="isSelected">
                    <el-row>
                      <el-button round size="small" @click="isSelected = false">关闭</el-button>
                      <el-button type="primary" round size="small" @click="saveAuth()">保存</el-button>
                    </el-row>
                    <el-row :gutter="20" style="margin-top: 5px">
                      <el-col :span="12">
                        <el-card class="box-card" shadow="never" >
                          <div slot="header" class="clearfix">
                            <span>用户:{{ currentEmploy.name }}</span>
                          </div>
                          <div v-for="item in resMenuList" class="text item">
                            {{ item.info }}
                            <el-button type="danger" icon="el-icon-delete" circle style="float: right;" size="mini" @click="left(item.mid)"></el-button>
                          </div>
                        </el-card>
                      </el-col>
                      <el-col :span="12">
                        <el-card class="box-card" shadow="never" >
                          <div slot="header" class="clearfix">
                            <span>所有权限</span>
                          </div>
                          <div v-for="item in menuList" class="text item">
                            {{ item.info }}
                            <el-button size="mini" style="float: right;" @click="right(item.mid)">添加</el-button>
                          </div>
                        </el-card>
                      </el-col>
                    </el-row>
                  </el-row>
                </el-col>
              </el-row>
            </el-tab-pane>
            <el-tab-pane label="角色管理">
              <el-row>
                <el-form :inline="true" :model="roleQueryInfo" class="demo-form-inline">
                  <el-form-item label="角色名">
                    <el-input v-model="roleQueryInfo.roleName" placeholder="角色名" size="mini"></el-input>
                  </el-form-item>
                  <el-form-item label="角色等级权限">
                    <el-select v-model="roleQueryInfo.roleVal" filterable placeholder="请选择" size="mini">
                      <el-option
                        v-for="item in roleValList"
                        :key="item.id"
                        :label="item.val"
                        :value="item.id">
                      </el-option>
                    </el-select>
                  </el-form-item>
                  <el-form-item>
                    <el-button type="primary" @click="onSubmitRole" size="small">查询</el-button>
                    <el-button type="info" @click="help" size="small">角色等级说明</el-button>
                  </el-form-item>
                </el-form>
              </el-row>
              <el-row :gutter="20">
                <el-col :span="15">
                  <el-table
                    :data="roleList"
                    height="470"
                    style="width: 100%">
                    <el-table-column
                      prop="roleName"
                      label="角色名称"
                      width="200">
                    </el-table-column>
                    <el-table-column
                      prop="roleVal"
                      label="角色等级"
                      width="100">
                    </el-table-column>
                    <el-table-column
                      label="操作">
                      <template slot-scope="scope">
                        <el-button
                          size="mini"
                          @click="handleRoleEdit(scope.row)">编辑</el-button>
                        <el-button
                          size="mini"
                          type="danger"
                          @click="handleRoleDelete(scope.row)">删除</el-button>
                      </template>
                    </el-table-column>
                  </el-table>
                  <el-row style="margin-top: 5px">
                    <el-pagination
                      @current-change="handleRoleChange"
                      :current-page.sync="roleTable.pageNum"
                      background
                      layout="total,prev, pager, next,jumper"
                      :pager-count="5"
                      :total="roleTable.total"
                      :page-size="30">
                    </el-pagination>
                  </el-row>
                </el-col>
                <el-col :span="9">
                  <el-row>
                    增加一个新的角色类型
                  </el-row>
                  <el-row style="margin-top: 10%">
                    <el-form label-position="top" label-width="80px" :model="roleInsert">
                      <el-form-item label="角色名称/职位">
                        <el-input v-model="roleInsert.roleName"></el-input>
                      </el-form-item>
                      <el-form-item label="角色权限等级">
                        <el-select v-model="roleInsert.roleVal" filterable placeholder="请选择">
                          <el-option
                            v-for="item in roleValList2"
                            :key="item.id"
                            :label="item.val"
                            :value="item.id">
                          </el-option>
                        </el-select>
                      </el-form-item>
                      <el-form-item>
                        <el-button type="primary" @click="insertRole">保存这个角色类型</el-button>
                      </el-form-item>
                    </el-form>
                  </el-row>
                </el-col>
              </el-row>
            </el-tab-pane>
          </el-tabs>
        </el-row>

    <el-dialog
      title="修改信息"
      :visible.sync="dialog"
      width="30%"
      :before-close="handleClose">
      <span>
         <el-row>
                    <el-form label-position="top" label-width="80px" :model="roleUpdate">
                      <el-form-item label="角色名称/职位">
                        <el-input v-model="roleUpdate.roleName"></el-input>
                      </el-form-item>
                      <el-form-item label="角色权限等级">
                        <el-select v-model="roleUpdate.roleVal" filterable placeholder="请选择">
                          <el-option
                            v-for="item in roleValList2"
                            :key="item.id"
                            :label="item.val"
                            :value="item.id">
                          </el-option>
                        </el-select>
                      </el-form-item>
                    </el-form>
                  </el-row>
      </span>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialog = false">取 消</el-button>
    <el-button type="primary" @click="updateRole">保 存</el-button>
  </span>
    </el-dialog>
    <el-dialog
      title="角色绑定"
      :visible.sync="roleDialog"
      width="30%"
      :before-close="roleDialogHandleClose">
      <span>
                    <el-form label-position="top" label-width="80px" :model="binding_role">
                      <el-form-item label="角色绑定">
                        <el-select v-model="binding_role.roleId" filterable placeholder="请选择">
                          <el-option
                            v-for="item in role"
                            :key="item.rid"
                            :label="item.roleName + '    ' + item.roleVal"
                            :value="item.rid">
                          </el-option>
                        </el-select>
                      </el-form-item>
                    </el-form>
      </span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="roleDialog = false">取 消</el-button>
        <el-button type="primary" @click="saveRoleId">保 存</el-button>
      </span>
    </el-dialog>

  </el-row>
</template>

<script>
export default {
  name: "Authority",
  data(){
    return {
      roleDialog:false,
      dialog:false,
      binding_role:{
        uid:'',
        roleId:''
      },
      menuList:[
      ],
      resMenuList:[

      ],
      userQueryInfo:{
        uid:'',
        name:'',
        roleId:''
      },
      roleQueryInfo:{
        roleName:'',
        roleVal:''
      },
      roleUpdate:{
        rid:'',
        roleName:'',
        roleVal:''
      },
      roleInsert:{
        roleName:'',
        roleVal: ''
      },
      userList:[],
      role:[],
      roleList:[],
      roleValList:[
        {id:-1,val:'空'},
        {id:1,val:'1'},
        {id:2,val:'2'},
        {id:3,val:'3'}
      ],
      roleValList2:[
        {id:1,val:'1'},
        {id:2,val:'2'},
        {id:3,val:'3'}
      ],
      employTable:{
        total:0,
        pageNum:1
      },
      roleTable:{
        total:0,
        pageNum:1
      },
      currentEmploy:{},
      isSelected:false
    }

  },
  methods:{
    /**
     * 角色绑定
     */
    saveRoleId(){
      this.$confirm('该操作可能会改变员工角色类型, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http.put("authority/roleChange",this.binding_role).then(res =>{
          if (res.data.flag){
            this.$message.success("修改成功");
            this.employTable.pageNum = 1;
            this.loadAllEmploy();
            this.roleDialog = false;
          } else{
            this.$message.success("修改失败");
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        });
      });
    },
    roleDialogHandleClose(done){
      this.binding_role.roleId = '';
      this.binding_role.uid ='';
      done();
    },
    bindingRole(data){
      this.binding_role.uid = data.uid;
      this.binding_role.roleId = data.roleId;
      this.loadAllRole();
      this.roleDialog = true;
    },
    handleClose(done){
      this.roleUpdate.roleName='';
      this.roleUpdate.roleVal ='';
      done();
    },
    //角色
    onSubmitRole(){
      this.roleTable.pageNum = 1;
      this.loadAllRoleByInfo();
    },
    handleRoleChange(num){
      this.roleTable.pageNum = num;
      this.loadAllRoleByInfo();
    },
    async loadAllRoleByInfo() {
      const res = await this.$http.get("authority/loadAllRoleByQueryInfo/"+this.roleTable.pageNum,{
        params:this.roleQueryInfo
      });
      if (res.data.flag){
        this.roleList = res.data.extend.result;
        this.roleTable.total = res.data.extend.total;
      }else{
        this.$message.warning("请检查网络连接");
      }
    },
    handleRoleEdit(data){
      this.roleUpdate.rid = data.rid;
      this.roleUpdate.roleName = data.roleName;
      this.roleUpdate.roleVal = data.roleVal;
      this.dialog = true;
    },
    handleRoleDelete(data){
      this.$confirm('将永久删除该角色类型, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http.delete("authority/role/"+data.rid).then(res=>{
          if (res.data.flag){
            this.loadAllRoleByInfo();
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
          }else{
            this.$message({
              type: 'error',
              message: '删除失败!请检查是否还有员工绑定该角色类型'
            });
          }
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    insertRole(){
        if (!this.roleInsert.roleVal||!this.roleInsert.roleName){
          this.$message.warning("请完善表单数据!");
          return;
        }
      this.$confirm('将保存该角色类型, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http.post("authority/role",this.roleInsert).then(res=>{
          if (res.data.flag){
            this.loadAllRoleByInfo();
            this.roleInsert.roleName="";
            this.roleInsert.roleVal ="";
            this.$message({
              type: 'success',
              message: '保存成功'
            });
          } else{
            this.$message({
              type: 'error',
              message: '保存失败'
            });
          }
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消保存'
        });
      });
    },
    updateRole(){
      this.$http.put("authority/role",this.roleUpdate).then(res=>{
        if (res.data.flag){
          this.loadAllRoleByInfo();
          this.$message.success("修改成功");
          this.dialog = false;
        } else {
          this.$message.error("修改失败");
        }
      })
    },
    help(){
      this.$notify.info({
        title: '说明',
        dangerouslyUseHTMLString: true,
        message:
          '等级1用户不能访问后台管理系统<br><br>' +
          '等级2用户可以访问后台管理系统,但是管理员需要分配权限<br><br>' +
          '等级3用户有操作后台管理系统的所有权限',
        duration: 0
      });

    },
    //用户
    handleEmployChange(num){
      this.employTable.pageNum = num;
      this.loadAllEmploy();
    },
    async loadAllEmploy() {
      let roleId = this.userQueryInfo.roleId;
      const query = {
        uid:this.userQueryInfo.uid,
        name:this.userQueryInfo.name,
        roleId: !roleId || roleId === ''?-1:roleId
      };
      const res = await this.$http.get("employ/loadEmployForAuth/"+this.employTable.pageNum,{
        params:query
      });
      if (res.data.flag){
        this.userList = res.data.extend.result;
        this.employTable.total = res.data.extend.total;
      }else{
        this.$message.warning("请检查网络连接");
      }
    },
    async loadAllRole() {
      const res = await this.$http.get("authority/loadAllRole");
      if (res.data.flag){
        this.role =[
          {rid:-1,roleName:'空',roleVal:''}
        ];
        for (let i =0;i < res.data.extend.role.length; i++)
        this.role.push(res.data.extend.role[i])
      }else {
        this.$message.error("请检查网络连接")
      }
    },
    onSubmit(){
      this.employTable.pageNum = 1;
      this.loadAllEmploy();
    },
    //权限
    loadMenuInfo(){
      this.$http.get("authority/loadAllMenuDirect/"+this.currentEmploy.uid).then(res=>{
        //请求自己菜单
        let userMenu = res.data.extend.menus_uid;
        this.resMenuList =userMenu;
        this.menuList = [];
        this.$http.get("authority/loadMenuList").then(res =>{
          let allMenu = res.data.extend.menus;
          for (let i = 0; i < allMenu.length;i++){
            let flag = true;
            for (let j = 0; j < userMenu.length;j++){
              if (allMenu[i].mid === userMenu[j].mid){
                flag = false;
                break;
              }
            }
            if (flag){
              this.menuList.push(allMenu[i]);
            }
          }
        })
      })
    },
    right(target){
      let that =this;
      this.menuList.forEach(function(item, index, arr) {
        if(item.mid === target) {
          that.resMenuList.push(item);
          arr.splice(index, 1)
        }
      });
    },
    left(target){
      let that =this;
      this.resMenuList.forEach(function(item, index, arr) {
        if(item.mid === target) {
          that.menuList.push(item);
          arr.splice(index, 1)
        }
      });
    },
    selectEmploy(data){
      this.currentEmploy = data;
      this.loadMenuInfo();
      this.isSelected = true;
    },
    saveAuth(){
      this.$http.put("authority/saveMenuByUid",{
        menus:this.resMenuList,
        uid:this.currentEmploy.uid
      }).then(res =>{
        if (res.data.flag){
          this.isSelected = false;
          this.$message.success("保存成功")
        }else{
          this.$message.warning("保存失败")
        }
      });
    }
  },
  created() {
    this.loadAllEmploy();
    this.loadAllRole();
    this.loadAllRoleByInfo();
  }
}
</script>

<style scoped>
.left{
  height: 550px;
}
.content{
  margin-top: 20px;
  height: 100%;
}
.text {
  font-size: 14px;
}

.item {
  padding: 10px 0;
}

/*.box-card {*/
/*  width: 480px;*/
/*}*/
</style>
