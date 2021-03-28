<template>
  <el-card class="box-card" shadow="never">
    <div class="text item">
      姓名:{{currentEmploy.name}}
    </div>
    <div class="text item">
      工号:{{currentEmploy.uid}}
    </div>
    <div class="text item">
      部门:{{currentEmploy.department === null? '暂无部门':currentEmploy.department.name}}
    </div>
    <div class="text item">
      民族:{{currentEmploy.national}}
    </div>
    <div class="text item">
      身份证号:{{currentEmploy.idNumber}}
    </div>
    <div class="text item">
      性别:{{currentEmploy.sex === 0?'男':'女'}}
    </div>
    <div class="text item">
      手机:{{currentEmploy.phone}}
    </div>
    <div class="text item">
      联系地址:{{currentEmploy.address}}
    </div>
    <div class="text item">
      最高学历:{{currentEmploy.culture}}
    </div>
    <div class="text item">
      毕业学校:{{currentEmploy.school}}
    </div>
    <div class="text item">
      邮件:{{currentEmploy.email}}
    </div>
    <div class="text item">
      合同期限:{{currentEmploy.contract}}
    </div>
    <div class="text item">
      聘用形式:{{currentEmploy.employFrom}}
    </div>
    <div class="text item">
      生日:{{currentEmploy.birthday}}
    </div>
    <div class="text item">
      加入时间:{{currentEmploy.createTime}}
    </div>
  </el-card>
</template>

<script>
    export default {
      name: "EmployInfo",
      data(){
        return{
          user:this.currentEmploy
        }
      },
      props:['currentEmploy']
    }
</script>

<style scoped>
  .text {
    font-size: 14px;
  }

  .item {
    padding: 8px;
  }
</style>
