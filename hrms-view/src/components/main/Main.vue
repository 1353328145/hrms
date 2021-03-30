<template>
  <el-row style="height: 100%;width: 100%">
    <el-card shadow="never" style="height: 600px">
      <el-row :gutter="20" style="height: 100%">
        <el-col :span="12" style="height: 100%;">
          <el-card id="leftTop" shadow="never">
            <div slot="header" class="clearfix">
              <span>欢迎你</span>
            </div>
            <EmployInfoInline :employName="currentEmploy.name" :url="currentEmploy.imgKey" :role="currentEmploy.role"></EmployInfoInline>
            <div class="item">
              部门:{{ currentEmploy.department === null? '暂无部门':currentEmploy.department.name }}
            </div>
            <div class="item">
              邮箱:{{ currentEmploy.email}}
            </div>
            <div class="item">
              电话:{{ currentEmploy.phone }}
            </div>
          </el-card>
          <div id="info">

          </div>
        </el-col>
        <el-col :span="12" style="height: 100%">
          <el-row>
            <el-switch
              v-model="reverse"
              active-text="倒序排列"
              inactive-text="正序排列">
            </el-switch>
          </el-row>
          <div style="margin-top: 20px">
            <h3>通知</h3>
          </div>
          <div id="notice">
            <el-timeline :reverse="reverse" style="margin-top: 10px">
                  <el-timeline-item
                    v-for="(item, index) in noticeList"
                    :key="index"
                    :timestamp="item.createTime">
                    <EmployInfoInline
                      :url="item.employ.imgKey"
                      :employName="item.employ.name"
                      :roleName="item.employRoleName">
                    </EmployInfoInline>
                    {{item.content}}
                  </el-timeline-item>
            </el-timeline>
          </div>
          <el-row style="margin-top: 10px">
            <el-pagination
              @current-change="handleChange"
              :current-page.sync="noticeTable.pageNum"
              background
              layout="total,prev, pager, next,jumper"
              :pager-count="5"
              :total="noticeTable.total"
              :page-size="10">
            </el-pagination>
          </el-row>
        </el-col>
      </el-row>
    </el-card>
  </el-row>
</template>

<script>
import EmployInfoInline from "../EmployInfoInline";
export default {
  name: "Main",
  components: {EmployInfoInline},
  data(){
    return {
      reverse:false,
      currentEmploy:{
        imgKey:'',
        name:'',
        role:{
          roleName:''
        },
        department:null
      },
      noticeList:[],
      noticeTable:{
        pageNum:1,
        total:0
      },
    }
  },
  methods:{
    handleChange(num){
      this.noticeTable.pageNum = num;
      this.loadNoticeList();
    },
    /**
     * 图表
     * */
    infoInit(){
      // 基于准备好的dom，初始化echarts实例
      let left = this.$echarts.init(document.getElementById('info'));
      this.$http.get("employ/CountGroupByCulture").then(res =>{
        let arr = res.data.extend.info;
        let name = [];
        let value = [];
        for (let i = 0; i < arr.length; i++) {
          name.push(arr[i].name);
          value.push(arr[i].value)
        }
        left.setOption({
          title: {
            text: '员工学历分布',
            left: 'center',
            top:20,
            textStyle: {
              color: '#ccc'
            }
          },
          xAxis: {
            type: 'category',
            boundaryGap: false,
            data: name
          },
          yAxis: {
            type: 'value'
          },
          series: [{
            data: value,
            type: 'line',
            areaStyle: {}
          }]
        });
      });
    },
    loadEmployData(){
      let uid = sessionStorage.getItem("uid");
      this.$http.get("employ/findByUid/"+uid).then(res =>{
        if (res.status === 200){
          this.currentEmploy = res.data.extend.employ;
        } else{
          this.$message.warning("请求出现"+res.status)
        }
      })
    },
    loadNoticeList(){
      this.$http.get("notice/crud/"+this.noticeTable.pageNum).then(res=>{
          if (res.status === 200){
            this.noticeList = res.data.extend.result;
            this.noticeTable.total = res.data.extend.total;
          } else{
            this.$message.warning("请求出现"+res.status)
          }
      })
    }
  },
  created(){
    this.loadEmployData();
    this.loadNoticeList();
  },
  mounted() {
    this.infoInit();
  }
}
</script>

<style scoped>
  .item{
    padding: 5px
  }
  #leftTop{
    height: 270px;
  }
  #info{
    height: 300px
  }
  #notice{
    overflow: auto;
    height: 400px;
  }
</style>
