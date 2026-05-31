<template>
  <div>
    <div class="bottom">
      <div class="bottom1">
        <div class="bottom2" @click="toPage('userIndex')">
          <i class="el-icon-house"></i><span style="margin-left:10px">首页</span>
        </div>
        <div class="bottom3" @click="toPage('userIntroduction')">
          <i class="el-icon-monitor"></i><span style="margin-left:10px">简介</span>
        </div>
        <div class="bottom2" @click="toPage('userReservation')">
          <i class="el-icon-position"></i><span style="margin-left:10px">仪器预约</span>
        </div>
        <div class="bottom3" @click="toPage('userSubscribe')">
          <i class="el-icon-postcard"></i><span style="margin-left:10px">耗材申购</span>
        </div>
        <div class="bottom3" @click="toPage('userDuty')">
          <i class="el-icon-data-line"></i><span style="margin-left:10px">卫生值日</span>
        </div>
        <div class="bottom3" @click="toPage('userLog')">
          <i class="el-icon-data-line"></i><span style="margin-left:10px">日志与周报提交</span>
        </div>
      </div>
    </div>
    <div class="bottom11">
      Multi device dynamic research adaptive laboratory management system
    </div>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        // 这里可以定义其他数据，如果有的话
      };
    },
    methods: {
      toPage(name) {
        if (name === "userIndex") {
          this.$router.push("/userIndex");
          location.reload();
        } else if (name === "userIntroduction") {
          this.$router.push("/userIntroduction");
        } else if (name === "userReservation") {
          this.$router.push("/userReservation");
        } else if (name === "userSubscribe") {
          this.$router.push("/userSubscribe");
        } else if (name === "userDuty") {
          this.$router.push("/userDuty");
        } else if (name === "userLog") {
          this.$router.push("/userLog");
        }
      }
    }
  };
</script>


<style scoped>
  .bottom {
    box-shadow: rgba(100, 100, 111, 0.2) 0px 7px 29px 0px;
    width: 100%;
    height: 300px;
    display: flex;
    justify-content: center;
    background-color: #161D27;
    align-items: center;
    font-family: '黑体';
    margin-top: 30px;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  }
  .bottom1 {
    width: 80%;
    height: 100%;
    display: flex;
    flex-wrap: wrap;
    justify-content: space-around;
    align-items: center;
  }
  .bottom2 {
    width: 23%;
    height: 80px;
    background-color: #F79F1A;
    border-radius: 10px;
    display: flex;
    justify-content: center;
    align-items: center;
    cursor: pointer;
    font-size: 20px;
    color: #ffffff;
  }
  .bottom2 {
    width: 30%;
    height: 80px;
    background-color: #F79F1A;
    border-radius: 10px;
    display: flex;
    justify-content: center;
    align-items: center;
    cursor: pointer;
    font-size: 20px;
    color: #ffffff;
  }
  .bottom3 {
    width: 30%;
    height: 80px;
    background-color: #00816F;
    border-radius: 10px;
    display: flex;
    justify-content: center;
    align-items: center;
    cursor: pointer;
    font-size: 20px;
    color: #ffffff;
  }
  .bottom11 {
    height: 40px;
    text-align: center;
    font-size: 20px;
  }
</style>
