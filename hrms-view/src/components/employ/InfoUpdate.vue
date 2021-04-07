<template>
  <el-card shadow="never">
    <el-row :gutter="20">
      <el-col :span="12">
        <el-row>
          <el-form label-position="left" label-width="80px" :model="password" :rules="rules2" ref="updatePasswordForm">
            <el-form-item label="修改密码" prop="newPassword">
              <el-input type="password" v-model="password.newPassword" size="mini"></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="ackPassword">
              <el-input type="password" v-model="password.ackPassword" size="mini"></el-input>
            </el-form-item>
            <el-form-item >
              <el-button type="success" plain size="mini" @click="updatePassword">
                <i class="el-icon-circle-check">保存</i>
              </el-button>
            </el-form-item>
          </el-form>
        </el-row>
        <hr>
        <el-row>
          <el-form label-position="left" ref="employUpdateForm" label-width="80px" :model="employForSubmit" :rules="rules">
            <el-form-item label="头像修改">
              <el-button type="primary" icon="el-icon-picture-outline" size="small" @click="openDrawer">头像上传</el-button>
            </el-form-item>
            <el-form-item label="姓名" prop="name">
              <el-input v-model="employForSubmit.name"></el-input>
            </el-form-item>
            <el-form-item label="性别" prop="sex">
              <el-radio v-model="employForSubmit.sex" :label="0">男</el-radio>
              <el-radio v-model="employForSubmit.sex" :label="1">女</el-radio>
            </el-form-item>
            <el-form-item label="手机号码" prop="phone">
              <el-input v-model="employForSubmit.phone"></el-input>
            </el-form-item>
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="employForSubmit.email" type="email"></el-input>
            </el-form-item>
            <el-form-item label="民族" prop="national">
              <el-input v-model="employForSubmit.national"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="success" plain @click="otherInfoUpdate">
                <i class="el-icon-circle-check">保存</i>
              </el-button>
            </el-form-item>
          </el-form>
        </el-row>
      </el-col>
      <el-col :span="12">
        <el-card shadow="never" style="height: 560px">
          <div slot="header" class="clearfix">
            <span>以下信息无法修改,如有需求请联系管理</span>
          </div>
          <div class="text item">
            工号:{{employ.uid}}
          </div>
          <div class="text item">
            部门:{{employ.department === null || employ.department == undefined? '暂无部门':employ.department.name}}
          </div>
          <div class="text item">
            身份证号:{{employ.idNumber}}
          </div>
          <div class="text item">
            最高学历:{{employ.culture}}
          </div>
          <div class="text item">
            毕业学校:{{employ.school}}
          </div>
          <div class="text item">
            合同期限:{{employ.contract}}
          </div>
          <div class="text item">
            聘用形式:{{employ.employFrom}}
          </div>
          <div class="text item">
            生日:{{employ.birthday}}
          </div>
          <div class="text item">
            加入时间:{{employ.createTime}}
          </div>
        </el-card>
      </el-col>
    </el-row>

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
              :file-list="fileList"
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
  </el-card>
</template>

<script>
export default {
  name: "InfoUpdate",
  data(){
    return{
      imageDrawer:false,
      imgUploadUrl:'http://localhost:8080/employ/img',
      importHeaders: {
        token:window.sessionStorage.getItem("token")
      },
      url:'',
      fileList:[],
      headImg:{
        uid:'',
        imgKey:''
      },
      employ:{},
      employForSubmit:{
        uid:'',
        name:'',
        sex:0,
        phone:'',
        email:'',
        national:''
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
        national:[
          { required: true, message: '请输入内容', trigger: 'blur' },
          {  max: 8, message: '8 个字符以内', trigger: 'blur' }
        ],
        birthday:[
          { type: 'date', required: true, message: '请选择日期', trigger: 'change' }
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          { type: "email", message: "请输入正确的邮箱！", trigger: "blur" }
        ],
      },
      rules2:{
        newPassword:[
          { required: true, message: '请输入内容', trigger: 'change' },
          {  max: 15, message: '15 个字符以内', trigger: 'change' }
        ],
        ackPassword:[
          { required: true, message: '请输入内容', trigger: 'change' },
          {  max: 15, message: '15 个字符以内', trigger: 'change' }
        ]
      },
      password:{
        newPassword:'',
        ackPassword:''
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
        this.$message.error('上传图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传图片大小不能超过 2M');
      }
      return isJPG && isLt2M;
    },
    //文件超出个数限制时的钩子
    handleExceed(){
      this.$message.error("只能选择1个图片上传");
    },
    handleUploadSuccess(res){
      if (res.flag){
        this.headImg.imgKey = res.extend.key;
        this.$http.put('employ/updateImgKey',this.headImg).then(res =>{
          if (res.status === 200){
            this.imageDrawer = false;
            this.$message.success("上传成功")
            this.loadEmployData();
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
    openDrawer(){
      this.fileList = [];
      this.$http.get("employ/getImg?key="+this.employ.imgKey).then(res =>{
        if (res.data.flag){
          this.url = res.data.extend.img;
        }
      })
      this.imageDrawer = true;
      this.headImg.uid = this.employ.uid;
    },
    updatePassword(){
      this.$refs.updatePasswordForm.validate((valid) => {
        if (valid) {
          if (this.password.newPassword != this.password.ackPassword){
            this.$message.warning("两次输入不一致");
            this.newPassword = '';
            this.ackPassword = '';
            return;
          }
          this.$confirm('该操作会改变修改你的密码, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.$http.put("employ/em",{
              uid:sessionStorage.getItem("uid"),
              password:this.password.newPassword
            }).then(res =>{
              if (res.status === 200){
                if (res.data.flag){
                  this.$message.success("修改成功")
                  this.$router.push("/login")
                }else{
                  this.$message.warning("修改失败")
                }
              }else{
                this.$message.warning("网络出现了问题")
              }
            })
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消'
            });
          });

        } else {
          this.$message.warning("格式输入有问题，请检查格式")
          return false;
        }
      });
    },
    loadEmployData(){
      let uid = sessionStorage.getItem("uid");
      this.$http.get("employ/findByUid/"+uid).then(res =>{
        if (res.status === 200){
          this.employ = res.data.extend.employ;
          this.employForSubmit.name = this.employ.name;
          this.employForSubmit.sex = this.employ.sex;
          this.employForSubmit.phone = this.employ.phone;
          this.employForSubmit.email = this.employ.email;
          this.employForSubmit.national = this.employ.national;
          this.employForSubmit.uid = this.employ.uid;
        } else{
          this.$message.warning("请求出现"+res.status)
        }
      })
    },
    otherInfoUpdate(){
      this.$refs.employUpdateForm.validate((valid) => {
        if (valid) {
          this.$confirm('该操作会改变修改你的信息, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.$http.put("employ/em",this.employForSubmit).then(res =>{
              if (res.status === 200){
                if (res.data.flag){
                  this.$message.success("修改成功")
                  this.loadEmployData();
                }else{
                  this.$message.warning("修改失败")
                }
              }else{
                this.$message.warning("网络出现了问题")
              }
            })
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消'
            });
          });
        } else {
          this.$message.warning("格式输入有问题，请检查格式")
          return false;
        }
      });
    }
  },
  created() {
    this.loadEmployData();
  }
}
</script>

<style scoped>
.text {
  font-size: 14px;
}

.item {
  padding: 16px;
}

.img{
  margin-left: 30px;
  margin-top: 30px;
}
</style>
