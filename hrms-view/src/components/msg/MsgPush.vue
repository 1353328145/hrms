<template>
  <div>
    <el-row>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item><a href="/">公告管理</a></el-breadcrumb-item>
      </el-breadcrumb>
    </el-row>
    <el-card style="margin-top: 20px">
      <el-row :gutter="20">
        <el-col :span="6">
          <el-row style="padding: 18px">
            <h3>添加公告</h3>
          </el-row>
          <el-row style="padding: 18px">
            <el-form ref="noticeInsertRef" :model="noticeInsertInfo" class="demo-form-inline" label-position="top" :rules="rules">
              <el-form-item label="通知标题" prop="title">
                <el-input v-model="noticeInsertInfo.title" placeholder="通知标题"></el-input>
              </el-form-item>
              <el-form-item label="通知内容" prop="content">
                <el-input v-model="noticeInsertInfo.content" type="textarea" placeholder="通知内容"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="insert" size="small">添加</el-button>
              </el-form-item>
            </el-form>
          </el-row>
        </el-col>
        <el-col :span="18">
          <el-row>
            <el-form :inline="true" :model="noticeQueryInfo" class="demo-form-inline">
              <el-form-item label="通知标题">
                <el-input v-model="noticeQueryInfo.title" placeholder="通知标题" size="mini"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="onSubmit()" size="small">查询</el-button>
              </el-form-item>
            </el-form>
          </el-row>
          <el-row>
            <el-table
              :data="noticeList"
              height="470"
              style="width: 100%">
              <el-table-column
                label="创建者"
                width="200">
                <template slot-scope="scope">
                  {{ scope.row.employ.name }} &nbsp;
                  <el-tag size="mini">{{scope.row.employRoleName }}</el-tag>
                </template>
              </el-table-column>
              <el-table-column
                prop="title"
                label="标题"
                width="120">
              </el-table-column>
              <el-table-column
                prop="createTime"
                label="时间"
                width="170">
              </el-table-column>
              <el-table-column
                prop="content"
                label="内容"
                width="200">
              </el-table-column>
              <el-table-column
                label="操作" width="250">
                <template slot-scope="scope">
                  <el-button
                    size="mini"
                    @click="handleNoticeEdit(scope.row)">修改</el-button>
                  <el-button
                    size="mini"
                    type="danger"
                    @click="handleNoticeDelete(scope.row)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-row>
          <el-row>
            <el-pagination
              @current-change="handleChange"
              :current-page.sync="noticeTable.pageNum"
              background
              layout="total,prev, pager, next,jumper"
              :pager-count="5"
              :total="noticeTable.total"
              :page-size="30">
            </el-pagination>
          </el-row>
        </el-col>
      </el-row>
    </el-card>

    <el-dialog
      title="提示"
      :visible.sync="updateDialog"
      width="30%"
      :before-close="updateDialogClose">
      <span>
        <el-form ref="noticeUpdateRef" :model="noticeUpdateInfo" :rules="rules">
              <el-form-item label="通知标题" prop="title">
                <el-input v-model="noticeUpdateInfo.title" placeholder="通知标题"></el-input>
              </el-form-item>
              <el-form-item label="通知内容" prop="content">
                <el-input v-model="noticeUpdateInfo.content" type="textarea" placeholder="通知内容"></el-input>
              </el-form-item>
            </el-form>
      </span>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="update">保 存</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "MsgPush",
  data(){
    return{
      updateDialog:false,
      noticeQueryInfo:{
        title:'',
      },
      noticeInsertInfo:{
        uid:'',
        title:'',
        content:'',
      },
      noticeUpdateInfo:{
        nid:'',
        title:'',
        content:'',
      },
      noticeList:[],
      noticeTable:{
        pageNum:1,
        total:0
      },
      rules:{
        title:[
          { required: true, message: '请输入内容', trigger: 'blur' },
          { max: 15, message: '最多 15 个字符', trigger: 'blur' }
        ],
        content:[
          { required: true, message: '请输入内容', trigger: 'blur' },
          { max: 240, message: '最多 240 个字符', trigger: 'blur' }
        ],
      }
    }
  },
  methods:{
    handleNoticeEdit(data){
      this.noticeUpdateInfo.nid = data.nid;
      this.noticeUpdateInfo.title = data.title;
      this.noticeUpdateInfo.content = data.content;
      this.updateDialog = true;
    },
    update(){
      this.$confirm('将修改这个通知, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http.put("notice/crud",this.noticeUpdateInfo).then(res =>{
          if (res.status===200){
            if (res.data.flag){
              this.$message.success("修改成功");
              this.updateDialog = false;
              this.loadNoticeList();
            }else{
              this.$message.success("修改成功");
            }
          }else{
            this.$message.error("请求出现"+res.status)
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        });
      });
    },
    updateDialogClose(done){
      done();
    },
    handleNoticeDelete(data){
      this.$confirm('将删除这个通知, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http.delete("notice/crud/"+data.nid).then(res =>{
          if (res.status===200){
            if (res.data.flag){
              this.$message.success("删除成功");
              this.noticeTable.pageNum =1;
              this.loadNoticeList();
            }else{
              this.$message.success("删除成功");
            }
          }else{
            this.$message.error("请求出现"+res.status)
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        });
      });
    },
    onSubmit(){
      this.noticeTable.pageNum = 1;
      this.loadNoticeList();
    },
    handleChange(num){
      this.noticeTable.pageNum = num;
      this.loadDpList();
    },
    async loadNoticeList() {
      const res = await this.$http.get('notice/crud/' + this.noticeTable.pageNum, {
        params: this.noticeQueryInfo
      });
      if (res.status === 200){
        if (res.data.flag){
          this.noticeList = res.data.extend.result;
          this.noticeTable.total = res.data.extend.total
        }
      }else{
        this.$message.error("请求出现"+res.status)
      }
    },
    clearInsertInfo(){
      this.noticeInsertInfo.title = ''
      this.noticeInsertInfo.content = ''
      this.noticeInsertInfo.uid = ''
    },
    insert(){
      this.$refs.noticeInsertRef.validate((valid) => {
        if (valid) {
          this.noticeInsertInfo.uid = sessionStorage.getItem("uid");
          if (!this.noticeInsertInfo.uid){
            this.$message.error("请重新登陆")
            return;
          }
          this.$confirm('将发布这个通知, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.$http.post("notice/crud",this.noticeInsertInfo).then(res =>{
              if (res.status ===200){
                if (res.data.flag){
                  this.$message.success("保存成功")
                  this.clearInsertInfo();
                  this.noticeTable.pageNum = 1;
                  this.loadNoticeList();
                }else{
                  this.$message.warning("保存失败")
                }
              }else{
                this.$message.warning("请求出现" + res.status)
              }
            })
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消'
            });
          });
        } else {
         this.$message.warning("请输入正确的格式")
          return false;
        }
      });
    },
  },
  created() {
    this.loadNoticeList();
  }
}
</script>

<style scoped>

</style>
