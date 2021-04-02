<template>
  <el-row style="height: 100%;width: 100%">
    <el-row>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/home/main' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>公司信息管理</el-breadcrumb-item>
      </el-breadcrumb>
    </el-row>
    <el-row style="margin-top: 20px">
      <el-tabs type="border-card">
        <el-tab-pane label="公司部门信息概要">
          <el-row :gutter="20" style="height: 550px">
            <el-col :span="13" >
              <div id="left" style="height: 550px;width: 500px">
              </div>
            </el-col>
            <el-col :span="11" style="height: 100%">
              <el-row>
                <el-card class="infoBox" shadow="never">
                  <div slot="header" class="clearfix">
                    <span>公司信息</span>
                  </div>
                  <div class="item">
                    公司名称: {{ company===null?'加载中':company.name }}
                  </div>
                  <div class="item">
                    公司成立日期: {{ company===null?'加载中':company.createDate }}
                  </div >
                  <div class="item">
                    公司法定代表人: {{ company===null?'加载中':company.legal }}
                  </div>
                  <div class="item">
                    公司地址: {{ company===null?'加载中':company.address }}
                  </div>
                  <div class="item">
                    公司简介: {{ company===null?'加载中':company.info }}
                  </div>
                </el-card>
              </el-row>
              <el-row :gutter="20" style="margin-top: 5%">
                <el-col :span="12">
                  <el-card  shadow="never">
                    <div slot="header" class="clearfix">
                      <span>部门数量</span>
                    </div>
                    <div  class="text">
                      {{ dpSum }}
                    </div>
                  </el-card>
                </el-col>
                <el-col :span="12">
                  <el-card  shadow="never">
                    <div slot="header" class="clearfix">
                      <span>总员工数</span>
                    </div>
                    <div class="text">
                      {{ emSum }}
                    </div>
                  </el-card>
                </el-col>
              </el-row>
            </el-col>
          </el-row>
        </el-tab-pane>
        <el-tab-pane label="公司部门信息管理">
          <el-row>
            <el-form :inline="true" :model="dpQueryInfo" class="demo-form-inline">
              <el-form-item label="部门名称">
                <el-input v-model="dpQueryInfo.name" placeholder="部门名称" size="mini"></el-input>
              </el-form-item>
              <el-form-item label="管理员工号">
                <el-input v-model="dpQueryInfo.adminEmploy" placeholder="管理员工号" size="mini"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="onSubmit" size="small">查询</el-button>
                <el-button type="success" @click="insertDp" size="small">新增部门</el-button>
              </el-form-item>
            </el-form>
          </el-row>
          <el-row>
            <el-table
              :data="dpList"
              height="470"
              style="width: 100%">
              <el-table-column
                prop="name"
                label="部门名称"
                width="150">
              </el-table-column>
              <el-table-column
                label="管理员工号"
                width="300">
                <template slot-scope="scope">
                    <el-button
                      v-if="scope.row.adminEmploy !== 0"
                      type="text"
                      @click="lookAdmin(scope.row)" >{{ scope.row.adminEmploy }}</el-button>
                    <div v-if="scope.row.adminEmploy === 0 ">还没有管理员</div>
                </template>
              </el-table-column>
              <el-table-column
                prop="description"
                label="部门职能"
                width="300">
              </el-table-column>
              <el-table-column
                label="操作">
                <template slot-scope="scope">
                  <el-button
                    size="mini"
                    @click="handleDpEdit(scope.row)">设置</el-button>
                  <el-button
                    size="mini"
                    type="danger"
                    @click="handleDpDelete(scope.row)">删除</el-button>
                  <el-button
                    size="mini"
                    type="primary"
                    @click="handleAdmin(scope.row)">设置管理员</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-row>
          <el-row>
            <el-pagination
              @current-change="handleChange"
              :current-page.sync="dpTable.pageNum"
              background
              layout="total,prev, pager, next,jumper"
              :pager-count="5"
              :total="dpTable.total"
              :page-size="30">
            </el-pagination>
          </el-row>
        </el-tab-pane>
      </el-tabs>
    </el-row>
    <el-dialog
      title="新增一个部门"
      :visible.sync="insertDialog"
      width="30%"
      :before-close="handleInsertClose">
      <span>
        <el-form ref="dpInsertForm" :model="dpInsert" label-width="80px" :rules="rules">
          <el-form-item label="部门名称" prop="name">
            <el-input v-model="dpInsert.name"></el-input>
          </el-form-item>
          <el-form-item label="部门简介" prop="description">
            <el-input type="textarea" v-model="dpInsert.description"></el-input>
          </el-form-item>
        </el-form>
      </span>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="insertDpSubmit">保 存</el-button>
      </span>
    </el-dialog>
    <el-dialog
      title="修改部门信息"
      :visible.sync="updateDialog"
      width="30%"
      :before-close="handleUpdateClose">
      <span>
        <el-form ref="dpUpdateForm" :model="dpUpdate" label-width="80px" :rules="rules">
          <el-form-item label="部门名称" prop="name">
            <el-input v-model="dpUpdate.name"></el-input>
          </el-form-item>
          <el-form-item label="部门简介" prop="description">
            <el-input type="textarea" v-model="dpUpdate.description"></el-input>
          </el-form-item>
        </el-form>
      </span>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="updateDpSubmit">保 存</el-button>
      </span>
    </el-dialog>
    <el-dialog
      title="绑定新的管理员"
      :visible.sync="adminDialog"
      width="30%"
      :before-close="handleAdminClose">
      <span>
        <el-form ref="dpAdminForm" :model="dpSetEmploy" label-width="80px" :rules="rules" label-position="top">
          <el-form-item label="新管理uid" prop="adminEmploy">
            <el-input v-model="dpSetEmploy.adminEmploy"></el-input>
          </el-form-item>
          <el-form-item >
            <el-button type="danger" @click="test">检验</el-button>
          </el-form-item>
        </el-form>
      </span>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="adminSubmit">保 存</el-button>
      </span>
    </el-dialog>
    <el-dialog
      title="管理员信息"
      :visible.sync="look"
      width="30%">
      <span>
        <EmployInfo :currentEmploy="currentEmploy"></EmployInfo>
      </span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="look = false">离 开</el-button>
      </span>
    </el-dialog>
  </el-row>
</template>

<script>
    import EmployInfo from "../EmployInfo";
    export default {
      name: "Info",
      components: {EmployInfo},
      data(){
        return{
          company:null,
          dpSum:0,
          emSum:0,
          ec:'',
          look:false,
          currentEmploy:{},
          adminDialog:false,
          insertDialog:false,
          updateDialog:false,
          dpInsert:{
            name:'',
            description:'',
          },
          dpUpdate:{
            did:'',
            name:'',
            description:'',
          },
          dpSetEmploy:{
            did:'',
            adminEmploy:''
          },
          uidTest:false,
          rules: {
            name: [
              { required: true, message: '请输入部门名称', trigger: 'blur' },
              { min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }
            ],
            description: [
              { required: true, message: '请输入部门职能或简介', trigger: 'blur' },
              { max: 100, message: '100字以内', trigger: 'blur' }
            ],
            adminEmploy: [
              { required: true, message: '输入uid', trigger: 'blur' },
            ],
          },
          dpQueryInfo:{
            name:'',
            adminEmploy:'',
          },
          dpList:[],
          dpTable:{
            pageNum:1,
            total:0
          },
        }
      },
      methods:{
        loadCompany(){
          this.$http.get("department/company").then(res =>{
            if (res.status ===200){
              this.company = res.data.extend.company;
            }
          })
        },
        loadCount(){
          this.$http.get("department/getDpCount").then(res =>{
            this.dpSum = res.data.extend.dpCount;
            this.emSum = res.data.extend.emCount;
          })
        },
        /**
         * 图表
         * */
        infoInit(){
          // 基于准备好的dom，初始化echarts实例
          let left = this.$echarts.init(document.getElementById('left'));
          this.$http.get("department/getCountByDp").then(res =>{
            let arr = res.data.extend.info;
            let list = [];
            for (let i = 0; i < arr.length; i++) {
              if (!arr[i].name){
                arr[i].name = '暂无部门'
              }
              list.push(arr[i].name)
            }
            left.setOption({
              title: {
                text: '各部门人数分布',
                left: 'center'
              },
              tooltip: {
                trigger: 'item',
                formatter: '{a} <br/>{b}: {c} ({d}%)'
              },
              legend: {
                orient: 'vertical',
                left: 10,
                data: list
              },
              series: [
                {
                  name: '部门信息',
                  type: 'pie',
                  radius: ['50%', '70%'],
                  avoidLabelOverlap: false,
                  label: {
                    show: false,
                    position: 'center'
                  },
                  emphasis: {
                    label: {
                      show: true,
                      fontSize: '30',
                      fontWeight: 'bold'
                    }
                  },
                  data: arr
                }
              ]
            });
          });

        },
        /**
         * 查看
         */
        lookAdmin(data){
          this.$http.get("employ/findByUid/"+data.adminEmploy).then(res =>{
              if (res.data.flag){
                this.currentEmploy = res.data.extend.employ;
                this.look = true;
              }else{
                this.$message.warning("该用户不存在")
              }
          })
        },
        /**
         * 增加
         */
        insertDp(){
          this.dpInsert.name = '';
          this.dpInsert.description = '';
          this.insertDialog = true;
        },
        insertDpSubmit(){
          this.$refs.dpInsertForm.validate(async (valid)=>{
            if (valid){
              this.$confirm('将保存这个部门实例, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
              }).then(() => {
                  this.$http.post("department/dp",this.dpInsert).then(res =>{
                    if (res.status === 200){
                      if (res.data.flag){
                        this.loadDpList();
                        this.$message.success("保存成功");
                        this.insertDialog = false;
                        this.dpInsert.name = '';
                        this.dpInsert.description = '';
                      } else{
                        this.$message.warning("保存失败")
                      }
                    } else{
                      this.$message.error("请求出现了问题")
                    }
                  });
              }).catch(() => {
                this.$message({
                  type: 'info',
                  message: '已取消保存'
                });
              });
            }else{
              this.$message.warning("格式不正确")
            }
          });
        },
        handleInsertClose(done){
          this.dpInsert.name = '';
          this.dpInsert.description = '';
          done();
        },
        /**
         * 查询
         * @param num
         */
        handleChange(num){
          this.dpTable.pageNum = num;
          this.loadDpList();
        },
        onSubmit(){
          this.dpTable.pageNum = 1;
          this.loadDpList();
        },
        /**
         * 修改
         */
        handleDpEdit(data){
          this.dpUpdate.did = data.did;
          this.dpUpdate.name=data.name;
          this.dpUpdate.description=data.description;
          this.updateDialog = true;
        },
        handleUpdateClose(done){
          this.dpUpdate.did ='';
          this.dpUpdate.name='';
          this.dpUpdate.description='';
          this.updateDialog = false;
          done();
        },
        updateDpSubmit(){
          this.$refs.dpUpdateForm.validate(async (valid)=>{
            if (valid){
              this.$confirm('将修改这个部门实例, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
              }).then(() => {
                this.$http.put("department/dp",this.dpUpdate).then(res =>{
                  if (res.status === 200){
                    if (res.data.flag){
                      this.loadDpList();
                      this.$message.success("修改成功");
                      this.updateDialog = false;
                    } else{
                      this.$message.warning("修改失败")
                    }
                  } else{
                    this.$message.error("请求出现了问题")
                  }
                });
              }).catch(() => {
                this.$message({
                  type: 'info',
                  message: '已取消保存'
                });
              });
            }else{
              this.$message.warning("格式不正确")
              return false;
            }
          });
        },
        /**
         * 删除
         */
        handleDpDelete(data){
          this.$confirm('将注销这个部门实例,该部门下员工的部门信息会被设为空, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.$http.delete("department/dp/"+ data.did).then(res =>{
              if (res.status === 200){
                if (res.data.flag){
                  this.loadDpList();
                  this.$message.success("注销成功");
                } else{
                  this.$message.warning("注销失败,请重试")
                }
              } else{
                this.$message.error("请求出现了问题")
              }
            })
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消删除'
            });
          });
        },
        async loadDpList() {
          const res = await this.$http.get('department/dp/' + this.dpTable.pageNum, {
            params: this.dpQueryInfo
          });
          if (res.status === 200){
            if (res.data.flag){
              this.dpList = res.data.extend.result;
              this.dpTable.total = res.data.extend.total
            }
          }else{
            this.$message.error("请求出现"+res.status)
          }
        },
        /**
         * 绑定管理
         */
        test(){
          if (this.dpSetEmploy.adminEmploy) {
            this.$http.get("employ/uidTest/"+this.dpSetEmploy.adminEmploy).then(res =>{
              if (res.data.flag){
                this.uidTest = true;
                this.$message.success("校验成功!员工:"+res.data.extend.name);
              } else{
                this.$message.warning("校验失败");
              }
            })
          }else{
            this.$message.warning("请完善后在校验");
          }
        },
        handleAdmin(data){
          this.uidTest = false;
          this.dpSetEmploy.did =data.did;
          this.adminDialog =true;
        },
        handleAdminClose(done){
          this.uidTest = false;
          this.dpSetEmploy.did = '';
          this.dpSetEmploy.adminEmploy='';
          done();
        },
        adminSubmit(){
          if (this.uidTest){
            this.$http.put("department/dp",this.dpSetEmploy).then(res =>{
              if (res.status === 200){
                if (res.data.flag){
                  this.loadDpList();
                  this.$message.success("修改成功");
                  this.adminDialog = false;
                } else{
                  this.$message.warning("修改失败")
                }
              } else{
                this.$message.error("请求出现了问题")
              }
            });
          } else{
            this.$message.warning("检验后再提交");
          }
        }
      },
      created() {
        this.loadDpList();
        this.loadCount();
        this.loadCompany();
      },
      mounted() {
        this.infoInit()
      }
    }
</script>

<style scoped>
.text{
  color: slategray;
  font-weight: bolder;
  font-size: 35px;
}
  .item{
    font-size: 14px;
    margin-bottom: 15px;
  }
.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both
}
</style>
