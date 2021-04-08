<template>
    <div>
      <el-row>
        <el-breadcrumb>
          <el-breadcrumb-item :to="{ path: '/home/main' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>考勤管理</el-breadcrumb-item>
        </el-breadcrumb>
      </el-row>
      <el-card shadow="never" style="height: 100%;margin-top: 10px">
        <el-row :gutter="20">
          <el-col :span="6">
            <el-card shadow="never">
              <div slot="header" class="clearfix">
                <span>打卡规则</span>
              </div>
              <div class="item">
                签到时间:{{ signRule.in }}
              </div >
              <div class="item">
                签退时间:{{ signRule.out }}
              </div>
              <div class="item">
                休息日:
                <span v-for="item in freeDay">
                  {{ item }} &nbsp;
                </span>
              </div>
            </el-card>
            <el-card shadow="never" style="margin-top: 30px">
              <div slot="header" class="clearfix">
                <span>今日打卡人数</span>
              </div>
              <div>
                <span class="text">{{ countTodaySignInNumber }}</span>
              </div>
            </el-card>
          </el-col>
          <el-col :span="18">
            <el-row>
              <el-form :inline="true" :model="userQueryInfo">
                <el-form-item label="工号">
                  <el-input v-model="userQueryInfo.uid" placeholder="工号" size="mini"></el-input>
                </el-form-item>
                <el-form-item label="姓名">
                  <el-input v-model="userQueryInfo.name" placeholder="姓名" size="mini"></el-input>
                </el-form-item><br>
                <el-form-item label="部门">
                  <el-select v-model="userQueryInfo.departmentId" filterable placeholder="请选择" size="mini">
                    <el-option
                      v-for="item in departmentList"
                      :key="item.did"
                      :label="item.name"
                      :value="item.did">
                    </el-option>
                  </el-select>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="onSubmit" size="mini">查询</el-button>
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
                  width="150">
                </el-table-column>
                <el-table-column
                  label="电话"
                  prop="phone"
                  width="150">
                </el-table-column>
                <el-table-column label="操作" width="200">
                  <template slot-scope="scope">
                     <div>
                       <el-button size="small" @click="open(scope.row)">查询考勤</el-button>
                     </div>
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
          </el-col>

          <el-drawer
            :title="currentEmploy.name + '的考勤信息'"
            :visible.sync="drawer"
            direction="ltr"
            show-close
            size="70%">
            <div style="margin-left: 20px">
                <el-row>
                  <el-form inline>
                    <el-form-item label="请选择月份">
                      <el-date-picker
                        v-model="queryInfo.createTime"
                        type="month"
                        placeholder="选择月">
                      </el-date-picker>
                    </el-form-item>
                    <el-form-item>
                      <el-button @click="loadSignByMonth">查询</el-button>
                    </el-form-item>
                  </el-form>

                </el-row>
              <el-row>
                <el-table
                  :data="signInfo"
                  style="width: 100%">
                  <el-table-column
                    prop="signIn"
                    label="签到时间"
                    width="150">
                  </el-table-column>
                  <el-table-column
                    label="签到图片"
                    width="90">
                    <template slot-scope="scope">
                      <div v-if="scope.row.signInImg == null">
                        暂无图片
                      </div>
                      <div v-if="scope.row.signInImg != null">
                        <el-button type="text" @click="look(scope.row.signInImg)">查看图片</el-button>
                      </div>
                    </template>
                  </el-table-column>
                  <el-table-column
                    label="签退时间"
                    width="150">
                    <template slot-scope="scope">
                      <div v-if="scope.row.signOut===null">
                        没有签退
                      </div>
                      <div v-if="scope.row.signOut!=null">
                        {{ scope.row.signOut }}
                      </div>
                    </template>
                  </el-table-column>
                  <el-table-column
                    label="签退图片"
                    width="90">
                    <template slot-scope="scope">
                      <div v-if="scope.row.signOutImg == null">
                        暂无图片
                      </div>
                      <div v-if="scope.row.signOutImg != null">
                        <el-button type="text" @click="look(scope.row.signOutImg)">查看图片</el-button>
                      </div>
                    </template>
                  </el-table-column>
                  <el-table-column
                    prop="createTime"
                    label="记录日期"
                    width="150">
                  </el-table-column>
                  <el-table-column
                    prop="status"
                    label="状态判定"
                    width="120">
                  </el-table-column>
                  <el-table-column
                    width="120">
                    <template slot-scope="scope">
                      <el-button type="danger" size="mini" @click="leave(scope.row)">设为请假</el-button>
                    </template>
                  </el-table-column>
                </el-table>
              </el-row>
            </div>
          </el-drawer>

          <el-dialog
            title="图片查看"
            :visible.sync="dialogVisible"
            width="30%">
            <span>
              <el-image
                style="width: 100%; height: 100%"
                :src="url"
                  fit="contain"></el-image>
            </span>
            <span slot="footer" class="dialog-footer">
              <el-button @click="dialogVisible = false">离 开</el-button>
            </span>
          </el-dialog>
        </el-row>
      </el-card>
    </div>
</template>

<script>
    export default {
        name: "Sign",
        data(){
          return{
            baseUrl:'http://localhost:8080/employ/img',
            url:'',
            dialogVisible:false,
            drawer:false,
            userQueryInfo:{
              uid:'',
              name:'',
              departmentId:'',
            },
            userList:[],
            departmentList:[],
            employTable:{
              total:0,
              pageNum:1
            },
            countTodaySignInNumber:0,
            signRule:{
            },
            freeDay:[],
            currentEmploy:{},
            queryInfo:{
              createTime: new Date()
            },
            signInfo:[]
          }
        },
      methods: {
        leave(data){
          this.$confirm('设置该签到记录状态为请假, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.$http.put("sign/leave",{
              sid:data.sid
            }).then(res=>{
              if (res.data.flag){
                this.$message.success("设置成功")
                this.loadSignByMonth();
              }else{
                this.$message.success("设置失败,请重试")
              }
            })
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消'
            });
          });
        },
        look(data){
          this.url = this.baseUrl + "?key=" + data
          this.dialogVisible = true;
        },
        dateToString (date){
          let  year = date.getFullYear();
          let  month =(date.getMonth() + 1).toString();
          let  day = (date.getDate()).toString();
          if  (month.length == 1) {
            month =  "0"  + month;
          }
          if  (day.length == 1) {
            day =  "0"  + day;
          }
          var  dateTime = year +  "-"  + month +  "-"  + day;
          return  dateTime;
        },
        loadSignByMonth(){
          let date = this.queryInfo.createTime
          date = this.dateToString(date);
          this.$http.get("sign/loadByMonth/"+this.currentEmploy.uid+"?createTime="+date).then(res=>{
            if (res.status === 200){
              if (res.data.flag){
                this.signInfo = res.data.extend.sign
              }else{
                this.$message.warning("参数异常")
              }
            }else{
              this.$message.warning("请求出现"+res.status)
            }
          })
        },
        open(data){
          this.currentEmploy = data
          this.drawer = true;
          this.queryInfo.createTime = new Date();
          this.loadSignByMonth();
        },
        loadDp() {
          this.$http.get("department/all").then(res => {
            this.departmentList = [
              {did: -1, name: '空'}
            ];
            for (let i = 0; i < res.data.extend.dp.length; i++) {
              this.departmentList.push(res.data.extend.dp[i])
            }
          })
        },
        onSubmit() {
          this.employTable.pageNum = 1;
          this.loadAllEmploy();
        },
        async loadAllEmploy() {
          let departmentId = this.userQueryInfo.departmentId;
          const query = {
            uid: this.userQueryInfo.uid,
            name: this.userQueryInfo.name,
            departmentId: !departmentId || departmentId === '' ? -1 : departmentId
          };
          const res = await this.$http.get("employ/loadEmployForAuth/" + this.employTable.pageNum, {
            params: query
          });
          if (res.data.flag) {
            this.userList = res.data.extend.result;
            this.employTable.total = res.data.extend.total;
          } else {
            this.$message.warning("请检查网络连接");
          }
        },
        handleEmployChange(num) {
          this.employTable.pageNum = num;
          this.loadAllEmploy();
        },
        loadCount() {
          this.$http.get("sign/countToday").then(res => {
            if (res.status === 200) {
              this.countTodaySignInNumber = res.data.extend.count;
            } else {
              this.$message.warning("网络出现" + res.status)
            }
          })
        },
        loadRule() {
          this.$http.get("sign/rule").then(res => {
            if (res.status === 200) {
              this.signRule = res.data.extend.rule;
              let s = this.signRule.free;
              for(let char of s){
                this.freeDay.push(this.match(char))
              }
            } else {
              this.$message.warning("网络出现" + res.status)
            }
          })
        },
        match(c){
          if (c === '1'){
            return "星期日"
          }else if (c==='2'){
            return "星期一"
          }else if (c==='3'){
            return "星期二"
          }else if (c==='4'){
            return "星期三"
          }else if (c==='5'){
            return "星期四"
          }else if (c==='6'){
            return "星期五"
          }else if (c==='7'){
            return "星期六"
          }
        }
      },
      created() {
        this.loadDp();
        this.loadAllEmploy();
        this.loadCount()
        this.loadRule()
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
  padding: 15px;
}
</style>
