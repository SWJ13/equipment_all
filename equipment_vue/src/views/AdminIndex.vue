<template>
    <el-container class="home_container">
        <el-header class="home_header">
            <div class="home_title">实验室后台管理系统</div>
            <div class="home_userinfoContainer">
                <el-avatar :src="imgUrl" class="user_avatar"></el-avatar>
                <el-dropdown style="position: relative; top: -10px; left: 10px;">
          <span class="el-dropdown-link home_userinfo">
            {{user.name}}<i class="el-icon-arrow-down el-icon--right home_userinfo"></i>
          </span>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </div>
        </el-header>
        <el-container>
            <el-aside width="200px" class="home_aside">
                <el-menu router class="home_menu">
                    <el-menu-item index="applicationsManage">
                        <div class="menu-item-content">
                            <i class="el-icon-goods"></i>申购管理
                        </div>
                    </el-menu-item>
                    <el-menu-item index="reservationManage">
                        <div class="menu-item-content">
                            <i class="el-icon-bell"></i>预约管理
                        </div>
                    </el-menu-item>
                    <el-menu-item index="instrumentsManage">
                        <div class="menu-item-content">
                            <i class="el-icon-data-analysis"></i>仪器管理
                        </div>
                    </el-menu-item>
                    <el-menu-item index="noticesManage">
                        <div class="menu-item-content">
                            <i class="el-icon-chat-line-round"></i>公告管理
                        </div>
                    </el-menu-item>
                    <el-menu-item index="logManage">
                        <div class="menu-item-content">
                            <i class="el-icon-notebook-2"></i>日志管理
                        </div>
                    </el-menu-item>
                    <el-menu-item index="reportsManage">
                        <div class="menu-item-content">
                            <i class="el-icon-notebook-1"></i>周报管理
                        </div>
                    </el-menu-item>
                </el-menu>
            </el-aside>
            <el-container>
                <el-main class="home_main">
                    <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb">
                        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                        <el-breadcrumb-item v-text="this.$router.currentRoute.name"></el-breadcrumb-item>
                    </el-breadcrumb>
                    <router-view></router-view>
                </el-main>
                <el-footer class="home_footer">@V1.0-Spring Boot + Vue</el-footer>
            </el-container>
        </el-container>
    </el-container>
</template>

<script>
    export default {
        methods: {
            logout() {
                let _this = this;
                this.$confirm('注销登录吗?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function () {
                    localStorage.removeItem('admin')
                    _this.$router.replace({ path: '/login' })
                })
            }
        },
        mounted: function () {
            this.user = JSON.parse(window.localStorage.getItem('user'))
        },
        data() {
            return {
                user: '',
                imgUrl: 'https://img1.baidu.com/it/u=3981550796,2434119925&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=800'
            }
        }
    }
</script>

<style>
    .home_container {
        height: 100%;
        position: absolute;
        top: 0px;
        left: 0px;
        width: 100%;
    }

    .home_header {
        background-color: #003333;
        color: #fff;
        text-align: center;
        display: flex;
        align-items: center;
        justify-content: space-between;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        padding: 0 20px;
    }

    .home_title {
        font-size: 24px;
        font-weight: 600;
        letter-spacing: 1px;
        margin-left: 26px;
    }

    .user_avatar {
        width: 40px;
        height: 40px;
        border-radius: 50%;
        box-shadow: 0 0 5px rgba(0, 0, 0, 0.2);
    }

    .home_userinfo {
        font-size: 16px;
        transition: color 0.3s ease;
        color: white;
    }

    .home_userinfo:hover {
        color: #e0e0e0;
    }

    .home_aside {
        background-color: #f4f4f4;
        box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
    }

    .home_menu {
        padding: 0;
    }

    .menu-item-content {
        padding: 15px 20px;
        display: flex;
        align-items: center;
        font-size: 16px;
        transition: background-color 0.3s ease;
        line-height: 30px;
    }

    .menu-item-content i {
        margin-right: 10px;
    }

    .home_main {
        background-color: #fff;
        color: #333;
        text-align: left;
        padding: 20px;
        box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
    }

    .breadcrumb {
        margin-bottom: 20px;
    }

    .home_footer {
        background-color: #FFFFE0;
        color: #888;
        font-size: 18px;
        line-height: 40px;
        text-align: center;
    }
</style>
