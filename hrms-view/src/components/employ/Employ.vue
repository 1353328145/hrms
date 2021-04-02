<template>
  <div>
    <el-row>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/home/main' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>员工信息管理</el-breadcrumb-item>
      </el-breadcrumb>
    </el-row>
      <el-row style="margin-top: 5px">
        <el-form :inline="true" :model="userQueryInfo" class="demo-form-inline">
          <el-form-item label="工号">
            <el-input v-model="userQueryInfo.uid" placeholder="工号" size="mini"></el-input>
          </el-form-item>
          <el-form-item label="姓名">
            <el-input v-model="userQueryInfo.name" placeholder="姓名" size="mini"></el-input>
          </el-form-item>
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
          <el-form-item>
            <el-button type="success" @click="employInsert" size="small">新增员工</el-button>
          </el-form-item>
        </el-form>
      </el-row>
      <el-row>
        <el-card>
          <el-table
            :data="userList"
            style="width: 100%;"
            height="530">
            <el-table-column
              label="工号"
              prop="uid"
              width="200">
            </el-table-column>
            <el-table-column
              label="部门"
              width="150">
              <template slot-scope="scope">
                {{ scope.row.department ===null? '暂无部门': scope.row.department.name}}
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
              width="200">
            </el-table-column>
            <el-table-column label="详情" width="100">
              <template slot-scope="scope">
                <el-button @click.native.prevent="look(scope.row)" type="text">
                  详细信息
                </el-button>
              </template>
            </el-table-column>
            <el-table-column label="密码重置" width="100">
              <template slot-scope="scope">
                <el-button size="small" @click="resetPassword(scope.row)">
                  重置
                </el-button>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="300">
              <template slot-scope="scope">
                <el-button size="mini" @click="setDepartment(scope.row)">
                  设置
                </el-button>
                <el-button size="mini" type="danger" @click="deleteEmploy(scope.row)">
                  注销
                </el-button>
                <el-button size="mini" type="primary" @click="img(scope.row)">
                  照片信息
                </el-button>
              </template>
            </el-table-column>
          </el-table>
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
      </el-row>

    <el-dialog
      title="用户"
      :visible.sync="employInfoDialog"
      width="30%">
      <span>
        <EmployInfo :currentEmploy="currentEmploy"></EmployInfo>
      </span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="employInfoDialog = false">离 开</el-button>
      </span>
    </el-dialog>
    <el-dialog
      title="用户新增"
      :visible.sync="employInsertDialog"
      width="30%">
      <span>
        <el-form label-position="left" ref="employInsertForm" label-width="80px" :model="employInsertInfo" :rules="rules">
          <el-form-item label="姓名" prop="name">
            <el-input v-model="employInsertInfo.name"></el-input>
          </el-form-item>
          <el-form-item label="性别" prop="sex">
            <el-radio v-model="employInsertInfo.sex" :label="0">男</el-radio>
            <el-radio v-model="employInsertInfo.sex" :label="1">女</el-radio>
          </el-form-item>
          <el-form-item label="手机号码" prop="phone">
            <el-input v-model="employInsertInfo.phone"></el-input>
          </el-form-item>
          <el-form-item label="地址" prop="address">
            <el-input v-model="employInsertInfo.address"></el-input>
          </el-form-item>
          <el-form-item label="民族" prop="national">
            <el-input v-model="employInsertInfo.national"></el-input>
          </el-form-item>
          <el-form-item label="身份证号" prop="idNumber">
            <el-input v-model="employInsertInfo.idNumber"></el-input>
          </el-form-item>
          <el-form-item label="文化程度" prop="culture">
            <el-select v-model="employInsertInfo.culture" placeholder="请选择">
              <el-option
                v-for="item in cultureOptions"
                :key="item"
                :label="item"
                :value="item">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="毕业学校" prop="school">
            <el-input v-model="employInsertInfo.school"></el-input>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="employInsertInfo.email" type="email"></el-input>
          </el-form-item>
          <el-form-item label="所属部门">
            <el-select v-model="employInsertInfo.departmentId" placeholder="请选择">
              <el-option
                v-for="item in departmentList"
                :key="item.did"
                :label="item.name"
                :value="item.did">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="合同日期" prop="contract">
              <el-date-picker
                v-model="employInsertInfo.contract"
                type="date"
                placeholder="选择日期">
              </el-date-picker>
          </el-form-item>
          <el-form-item label="出生日期" prop="birthday">
              <el-date-picker
                v-model="employInsertInfo.birthday"
                type="date"
                placeholder="选择日期">
              </el-date-picker>
          </el-form-item>
          <el-form-item label="招聘来源" prop="employFrom">
            <el-input v-model="employInsertInfo.employFrom"></el-input>
          </el-form-item>
        </el-form>
      </span>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="saveEmploy()">保 存</el-button>
      </span>
    </el-dialog>
    <el-dialog
      title="设置部门"
      :visible.sync="employUpdateDialog"
      width="30%">
      <span>
        <el-form label-position="left" label-width="80px" :model="updateDepartment" :rules="updateRules" ref="employUpdateFormX">
          <el-form-item label="联系地址" prop="address">
            <el-input type="text" v-model="updateDepartment.address"></el-input>
          </el-form-item>
          <el-form-item label="身份证号" prop="idNumber">
            <el-input type="text" v-model="updateDepartment.idNumber"></el-input>
          </el-form-item>
          <el-form-item label="最高学历" prop="culture">
            <el-select v-model="updateDepartment.culture" placeholder="请选择">
              <el-option
                v-for="item in cultureOptions"
                :key="item"
                :label="item"
                :value="item">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="毕业学校">
            <el-input type="text" v-model="updateDepartment.school"></el-input>
          </el-form-item>
          <el-form-item label="合同日期" prop="contract">
              <el-date-picker
                v-model="updateDepartment.contract"
                type="date"
                placeholder="选择日期">
              </el-date-picker>
          </el-form-item>
          <el-form-item label="聘用形式" prop="employFrom">
            <el-input type="text" v-model="updateDepartment.employFrom"></el-input>
          </el-form-item>
          <el-form-item label="部门" prop="departmentId">
            <el-select v-model="updateDepartment.departmentId" filterable placeholder="请选择" >
              <el-option
                v-for="item in departmentList"
                :key="item.did"
                :label="item.name"
                :value="item.did">
              </el-option>
            </el-select>
          </el-form-item>
        </el-form>
      </span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="updateEmploy">保 存</el-button>
      </span>
    </el-dialog>

    <el-drawer
      title="照片查看与上传"
      :visible.sync="imageDrawer"
      :with-header="false">
      <span>
        <el-row style="margin-top: 10%" class="img">
          <el-image
            style="width: 300px; height: 300px"
            :src="url"
            fit="contain">
             <div slot="error" class="image-slot">
              还没有上传图片
            </div>
          </el-image>
        </el-row>
        <el-row class="img">
            <el-upload
              ref="imgUploadRef"
              :action="imgUploadUrl"
              :before-upload="beforeUpload"
              :before-remove="beforeRemove"
              :headers="importHeaders"
              multiple
              :limit="1"
              :on-exceed="handleExceed"
              :on-success="handleUploadSuccess"
              :on-error="handleUploadError"
              ::file-list="fileList"
              name="file"
              :auto-upload="false">
                <el-button size="small" type="primary">点击上传</el-button>
                <div slot="tip" class="el-upload__tip">只能上传jpg文件，且不超过2M</div>
              </el-upload>
        </el-row>
        <el-row class="img">
          <el-button type="success" @click="uploadImg">
              提交图片
            </el-button>
        </el-row>
      </span>
    </el-drawer>
  </div>
</template>

<script>
import EmployInfo from "../EmployInfo";
export default {
  name: "Employ",
  components: {EmployInfo},
  data(){
    return{
      fileList:[],
      url:'',
      imgUploadUrl:'http://localhost:8080/employ/img',
      importHeaders: {
        token:window.sessionStorage.getItem("token")
      },
      imageDrawer:false,
      drawerInfo:{
        uid:'',
        imgKey:'',
      },
      rules:{
        name:[
          { required: true, message: '请输入内容', trigger: 'blur' },
          {  max: 8, message: '8 个字符以内', trigger: 'blur' }
        ],
        sex:[
          {required: true, message: '请选择性别', trigger: 'change' }
        ],
        phone:[
          { required: true, message: '请输入手机号码', trigger: 'blur' },
          { min:11,max: 11, message: '手机号码格式有误', trigger: 'blur' }
        ],
        address:[
          { required: true, message: '请输入内容', trigger: 'blur' },
          {  max: 25, message: '25 个字符以内', trigger: 'blur' }
        ],
        national:[
          { required: true, message: '请输入内容', trigger: 'blur' },
          {  max: 8, message: '8 个字符以内', trigger: 'blur' }
        ],
        idNumber:[
          { required: true, message: '请输入内容', trigger: 'blur' },
          {  min:18 ,max: 18, message: '请输入正确的身份证号', trigger: 'blur' }
        ],
        culture:[
          { required: true, message: '请选择内容', trigger: 'blur' },
        ],
        contract:[
          { type: 'date', required: true, message: '请选择日期', trigger: 'change' }
        ],
        birthday:[
          { type: 'date', required: true, message: '请选择日期', trigger: 'change' }
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          { type: "email", message: "请输入正确的邮箱！", trigger: "blur" }
        ],
        employFrom:[
          { required: true, message: '请输入内容', trigger: 'blur' },
          {  max: 100, message: '100 个字符以内', trigger: 'blur' }
        ],
      },
      employUpdateDialog:false,
      employInsertDialog:false,
      employInfoDialog:false,
      userQueryInfo:{
        uid:'',
        name:'',
        roleId:'',
        departmentId:'',
      },
      employInsertInfo:{
        sex:0,
        name:'',
        phone:'',
        address:'',
        national:'',
        idNumber:'',
        culture:'',
        school:'',
        email:'',
        contract :'',
        birthday :'',
        employFrom :'',
        departmentId:-1,
      },
      cultureOptions:[
        '其他','高中','专科','本科','研究生','硕士','博士'
      ],
      currentEmploy:{},
      role:[],
      userList:[],
      departmentList:[],
      employTable:{
        total:0,
        pageNum:1
      },
      updateDepartment:{
        uid:'',
        departmentId:'',
        address:'',
        idNumber:'',
        culture:'',
        school:'',
        contract:'',
        employFrom:''
      },
      updateRules:{
        address:[
          { required: true, message: '请输入内容', trigger: 'blur' },
          {  max: 25, message: '25 个字符以内', trigger: 'blur' }
        ],
        idNumber:[
          { required: true, message: '请输入内容', trigger: 'blur' },
          {  min:18 ,max: 18, message: '请输入正确的身份证号', trigger: 'blur' }
        ],
        culture:[
          { required: true, message: '请选择内容', trigger: 'blur' },
        ],
        contract:[
          { required: true, message: '请选择日期', trigger: 'change' }
        ],
        employFrom:[
          { required: true, message: '请输入内容', trigger: 'blur' },
          {  max: 100, message: '100 个字符以内', trigger: 'blur' }
        ],
        departmentId:[
          { required: true, message: '请输入内容', trigger: 'blur' },
        ]
      }
    }
  },
  methods:{
    //删除之前
    beforeRemove(){
      return true;
    },
    //上传之前
    beforeUpload(file){
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size < 1024 * 1024 * 2;
      if (!isJPG) {
        this.$message.error('上传商品图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传商品图片大小不能超过 2M');
      }
      return isJPG && isLt2M;
    },
    //文件超出个数限制时的钩子
    handleExceed(){
      this.$message.error("只能选择1个图片上传");
    },
    handleUploadSuccess(res){
      if (res.flag){
        this.drawerInfo.imgKey = res.extend.key;
        this.$http.put('employ/updateImgKey',this.drawerInfo).then(res =>{
          if (res.status === 200){
            this.imageDrawer = false;
            this.loadAllEmploy();
            this.$message.success("上传成功")
          }else{
            this.$message.warning("上传失败")
          }
        })
      }else{
        this.$message.warning("上传失败")
      }
    },
    //上传失败
    handleUploadError(){
      this.$message.error("上传出现问题...");
    },
    uploadImg(){
      this.$refs.imgUploadRef.submit();
    },
    img(data){
      this.fileList = [];
      this.$http.get("employ/getImg?key="+data.imgKey).then(res =>{
        if (res.data.flag){
          this.url = res.data.extend.img;
        }
      })
      this.drawerInfo.uid = data.uid
      this.imageDrawer = true;
    },
    setDepartment(data){
      this.updateDepartment.uid = data.uid;
      this.updateDepartment.departmentId = data.departmentId === 0?-1:data.departmentId;
      this.updateDepartment.address = data.address;
      this.updateDepartment.idNumber = data.idNumber;
      this.updateDepartment.culture = data.culture;
      this.updateDepartment.school = data.school;
      this.updateDepartment.contract = data.contract;
      this.updateDepartment.employFrom = data.employFrom;
      this.employUpdateDialog = true;
    },
    updateEmploy(){
      this.$confirm('该操作会改变用户部门, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
          this.$http.put("employ/em",this.updateDepartment).then(res =>{
            if (res.status===200){
              if (res.data.flag){
                this.loadAllEmploy();
                this.employUpdateDialog = false;
                this.$message.success("修改成功")
              }else{
                this.$message.warning("修改失败")
              }
            }else{
              this.$message.warning("网络出现错误")
            }
          })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        });
      });
    },
    saveEmploy(){
      this.$refs.employInsertForm.validate((valid) => {
        if (valid) {
          this.$http.post("authority/register",this.employInsertInfo).then(res =>{
            if (res.status === 200){
              this.loadAllEmploy();
              this.employInsertDialog =false;
              this.$message({
                type: 'success',
                duration:0,
                showClose:true,
                message: '添加成功,uid:'+res.data.extend.uid
              });
            }else{
              this.$message.warning("网络出现了问题")
            }
          })
        } else {
          this.$message.warning("格式输入有问题，请检查格式")
          return false;
        }
      });
    },
    employInsert(){
      this.employInsertDialog = true;
    },
    resetPassword(data){
      this.$confirm('该用户密码会被重置为6个1, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http.put("employ/em",{
          uid:data.uid,
          password:'111111'
        }).then(res =>{
          if (res.status===200){
            if (res.data.flag){
              this.$message.success("重置成功,密码6个1")
            }else{
              this.$message.warning("修改失败")
            }
          }else{
            this.$message.warning("网络出现错误")
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消重置'
        });
      });
    },
    deleteEmploy(data){
      this.$confirm('该用户账户将会被永久注销, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http.delete("employ/em/"+data.uid,{
          uid:data.uid,
        }).then(res =>{
          if (res.status===200){
            if (res.data.flag){
              this.loadAllEmploy();
              this.$message.success("注销成功")
            }else{
              this.$message.warning("注销失败,该用户可能是某部门管理员,请解除关系后重试")
            }
          }else{
            this.$message.warning("网络出现错误")
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
      this.employInfoDialog = true;
      this.currentEmploy = data;
    },
    onSubmit(){
      this.employTable.pageNum = 1;
      this.loadAllEmploy();
    },
    async loadAllEmploy() {
      let roleId = this.userQueryInfo.roleId;
      const query = {
        uid:this.userQueryInfo.uid,
        name:this.userQueryInfo.name,
        departmentId: this.userQueryInfo.departmentId,
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
    loadDp(){
       this.$http.get("department/all").then(res =>{
         this.departmentList =[
           {did:-1,name:'空'}
         ];
         for (let i =0;i < res.data.extend.dp.length; i++){
           this.departmentList.push(res.data.extend.dp[i])
         }
       })
    },
    handleEmployChange(num){
      this.employTable.pageNum = num;
      this.loadAllEmploy();
    },
  },
  created() {
    this.loadAllEmploy();
    this.loadAllRole();
    this.loadDp();
  }
}
</script>

<style scoped>
.img{
  margin-left: 30px;
  margin-top: 30px;
}
</style>
