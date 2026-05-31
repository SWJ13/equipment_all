<template>
  <div class="headerTotal">
    <img style="width:90px" src="../assets/image/logo.png">
    <div class="header1">
      <div @click="toPage('/userIndex')" :class="$route.path == '/userIndex'?'active menu-item':'menu-item'" class="menu-item">首页</div>
      <div @click="toPage('/userIntroduction')" :class="$route.path == '/userIntroduction'?'active menu-item':'menu-item'" class="menu-item">简介</div>
      <div @click="toPage('/userReservation')" :class="$route.path == '/userReservation'?'active menu-item':'menu-item'" class="menu-item">仪器预约</div>
      <div @click="toPage('/userSubscribe')" :class="$route.path == '/userSubscribe'?'active menu-item':'menu-item'" class="menu-item">耗材申购</div>
      <div @click="toPage('/userDuty')" :class="$route.path == '/userDuty'?'active menu-item':'menu-item'" class="menu-item">卫生值日</div>
      <div @click="toPage('/userLog')" :class="$route.path == '/userLog'?'active menu-item':'menu-item'" class="menu-item">日志与周报提交</div>
      <div @click="toPage('/userNotices')" :class="$route.path == '/userNotices'?'active menu-item':'menu-item'" class="menu-item">公告栏</div>
    </div>
    <div class="header2">
      <div class="header3">
        {{user.name}}
      </div>
      <div class="header4" @click="loginOut">
        退出登录
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    data() {
      return{
        user: {}
      }
    },
    methods: {
      toPage(name) {
        this.$router.push(name);
      },
      loginOut() {
        let _this = this;
        this.$confirm('注销登录吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(function () {
          localStorage.removeItem('user')
          _this.$router.replace({path: '/login'})
        })
      }
    },
    created() {
      const userStr = window.localStorage.getItem('user')
      if (userStr) {
        try {
          this.user = JSON.parse(userStr)
        } catch (e) {
          this.user = {}
        }
      } else {
        this.user = {}
      }
    }
  }
</script>

<style scoped>
  .headerTotal {
    width: 100%;
    height: 80px;
    display: flex;
    align-items: center;
    justify-content: space-around;
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    background-color: #333;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    position: sticky;
    top: 0;
    z-index: 100;
    margin-bottom: 5px;
    margin-top: 5px;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  }
  .header1 {
    display: flex;
    color: white;
  }
  .active {
    color: #FEA501;
    border-bottom: 2px solid #FEA501;
    padding-bottom: 5px;
  }
  .menu-item {
    font-size: 16px;
    margin-left: 30px;
    cursor: pointer;
    transition: color 0.3s ease;
  }
  .menu-item:hover {
    color: #FEA501;
  }
  .header2 {
    display: flex;
    gap: 20px;
  }
  .header3 {
    width: 130px;
    height: 40px;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: rgba(255, 255, 255, 0.1);
    border-radius: 10px;
    cursor: pointer;
    color: white;
    transition: background-color 0.3s ease;
  }
  .header3:hover {
    background-color: rgba(255, 255, 255, 0.2);
  }
  .header4 {
    width: 130px;
    height: 40px;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: #FEA501;
    border-radius: 10px;
    cursor: pointer;
    color: white;
    font-weight: 600;
    transition: background-color 0.3s ease;
  }
  .header4:hover {
    background-color: #ffb732;
  }
</style>
