<template>
  <el-card class="box-card" shadow="never">
    <div class="text item">
      姓名:{{currentEmploy.name}}
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
