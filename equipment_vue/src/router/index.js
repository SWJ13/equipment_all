import Vue from 'vue'
import VueRouter from 'vue-router'
import UserIndex from "../views/UserIndex";
import UserIntroduction from "../views/UserIntroduction";
import UserReservation from "../views/UserReservation";
import UserSubscribe from "../views/UserSubscribe";
import UserDuty from "../views/UserDuty";
import UserLog from "../views/UserLog";
import UserNotices from "../views/UserNotices";
import AdminIndex from "../views/AdminIndex";
import ApplicationsManage from "../views/ApplicationsManage";
import ReservationManage from "../views/ReservationManage";
import InstrumentsManage from "../views/InstrumentsManage";
import NoticesManage from "../views/NoticesManage";
import LogManage from "../views/LogManage";
import ReportsManage from "../views/ReportsManage";
import Login from "../views/Login";
// 为了触发Vercel重新部署，配置后端接口地址
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/userIntroduction',
    name: 'UserIntroduction',
    component: UserIntroduction
  },
  {
    path: '/userIndex',
    name: 'UserIndex',
    component: UserIndex
  },
  {
    path: '/userReservation',
    name: 'UserReservation',
    component: UserReservation
  },
  {
    path: '/userSubscribe',
    name: 'UserSubscribe',
    component: UserSubscribe
  },
  {
    path: '/userDuty',
    name: 'UserDuty',
    component: UserDuty
  },
  {
    path: '/userLog',
    name: 'UserLog',
    component: UserLog
  },
  {
    path: '/userNotices',
    name: 'UserNotices',
    component: UserNotices
  },
  {
    path: '/login',
    name: '登录',
    component: Login
  },
  {
    path: '/adminIndex',
    name: 'AdminIndex',
    component: AdminIndex,
    redirect: '/adminIndex/applicationsManage',
    children:[
      {
        path: 'applicationsManage',
        name: '申购管理',
        component: ApplicationsManage
      },
      {
        path: 'reservationManage',
        name: '预约管理',
        component: ReservationManage
      },
      {
        path: 'instrumentsManage',
        name: '仪器管理',
        component: InstrumentsManage
      },
      {
        path: 'noticesManage',
        name: '公告管理',
        component: NoticesManage
      },
      {
        path: 'logManage',
        name: '日志管理',
        component: LogManage
      },
      {
        path: 'reportsManage',
        name: '周报管理',
        component: ReportsManage
      }
    ]
  }
]

const router = new VueRouter({
  mode: 'hash',
  base: process.env.BASE_URL,
  routes
})

export default router
