<template>
    <div style="height: 100%">
      <el-row>
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>组织架构管理</el-breadcrumb-item>
        </el-breadcrumb>
      </el-row>
      <el-row style="margin-top: 20px;height: 530px" :gutter="20">
        <el-col :span="12" style="height: 100%">
          <el-card style="height: 100%">
            <h2>公司架构树形图</h2>
            <el-tree :data="tree" :props="defaultProps" @node-click="handleNodeClick">
            </el-tree>
          </el-card>
        </el-col>
        <el-col :span="12" style="height: 100%">
          <el-card style="height: 100%" shadow="never">
            <el-row v-if="currentDepartment != null ">
              <el-row>
                <el-button type="primary" plain @click="openDependDialog">变更部门依赖关系</el-button>
              </el-row>
              <div style="text-align: center;font-size: 20px;font-weight: bolder;color: slategray"> {{ currentDepartment.name }} </div>
              <div class="title">部门职能:</div>
              <div class="info"> {{ currentDepartment.description }} </div>
              <div class="title">部门管理员工号</div>
              <div class="info"> {{ currentDepartment.adminEmploy === 0?'暂无管理员':currentDepartment.adminEmploy }}</div>
              <div style="float: right" v-if="currentDepartment.adminEmploy !== 0"><el-button type="success" plain @click="look">查看管理员详细信息</el-button></div>
            </el-row>
            <el-row v-if="currentDepartment == null ">
              单击选择组件查看部门信息
            </el-row>
          </el-card>
        </el-col>
      </el-row>
      <el-dialog
        title="管理员信息"
        :visible.sync="adminDialog"
        width="30%">
      <span>
        <EmployInfo :currentEmploy="currentAdmin"></EmployInfo>
      </span>
        <span slot="footer" class="dialog-footer">
        <el-button @click="adminDialog = false">离 开</el-button>
      </span>
      </el-dialog>

      <el-dialog
        title="变更依赖"
        :visible.sync="dependDialog"
        width="30%">
      <span>
        <el-form>
          <el-form-item label="请选择父部门">
            <el-select v-model="departmentUpdate.pdid" placeholder="请选择">
              <el-option
                v-for="item in departments"
                :key="item.did"
                :label="item.name"
                :value="item.did">
              </el-option>
            </el-select>
          </el-form-item>
        </el-form>
      </span>
        <span slot="footer" class="dialog-footer">
        <el-button @click="save()">保 存</el-button>
      </span>
      </el-dialog>
    </div>
</template>

<script>
    import EmployInfo from "../EmployInfo";
    export default {
      name: "Framework",
      components: {EmployInfo},
      data() {
        return {
          dependDialog:false,
          adminDialog:false,
          tree:[],
          defaultProps: {
            children: 'children',
            label: 'label'
          },
          departments:[],
          currentDepartment:null,
          currentAdmin:null,
          departmentUpdate:{
            did:'',
            pdid: null
          }
        };
      },
      methods:{
        save(){
          if (!this.departmentUpdate.did||!this.departmentUpdate.pdid){
            return;
          }
          this.$http.put("department/dp",this.departmentUpdate).then(res=>{
            if (res.status===200){
              if (res.data.flag){
                this.$message.success("修改成功");
                this.dependDialog = false;
                this.getTree();
              }else{
                this.$message.warning("修改失败")
              }
            } else{
              this.$message.warning("请求出现问题")
            }
          })
        },
        openDependDialog(){
          if (this.currentDepartment == null){
            this.$message.warning("请选择部门");
            return;}
          this.departmentUpdate.did = this.currentDepartment.did;
          this.departmentUpdate.pdid = this.currentDepartment.pdid;
          if (this.departmentUpdate.pdid === 0 || this.departmentUpdate.pdid==null || this.departmentUpdate.pdid === -1){
            this.departmentUpdate.pdid =this.departmentUpdate.did;
          }
          this.dependDialog = true;
          this.$http.get("department/all").then(res=>{
            if (res.status===200){
              this.departments = res.data.extend.dp;
            }else{tshi.$message.error("请求出现问题")}
          })
        },
        look(){
          this.$http.get('employ/findByUid/'+this.currentDepartment.adminEmploy).then(res =>{
            if (res.status === 200){
              this.currentAdmin = res.data.extend.employ;
              this.adminDialog = true
            } else{
              this.$message.error("请求失败")
            }
          });
        },
        handleNodeClick(data){
          this.currentDepartment = data.department;
        },
        getTree(){
          this.$http.get("department/getTree").then(res =>{
            if (res.status === 200){
              this.tree = res.data.extend.tree;
            }
          })
        }
      },
      created() {
          this.getTree();
      }
    }
</script>

<style scoped>
.info{
  font-size: 20px;
  margin-top: 10px;
  color: brown;
  margin-left: 30px;
}
  .title{
    margin-top: 30px;
    font-weight: bolder;
  }
</style>
