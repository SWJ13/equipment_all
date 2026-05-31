<template>
    <div style="margin-top: 60px;margin-left:80px;border: 0px solid red;" >
        <el-table
                :data="tableData"
                border
                stripe
                style="width: 100%">
            <el-table-column property="id" label="编号" width="150" />
            <el-table-column property="instrumentsName" label="仪器" width="220" />
            <el-table-column property="userName" label="用户名" width="160" />
            <el-table-column property="userEmail" label="邮箱" width="220" />
            <el-table-column property="userInstitution" label="单位" width="180" />
            <el-table-column property="bookingDate" label="日期" width="160" />
            <el-table-column property="bookingTime" label="时间" />
        </el-table>
        <el-pagination style="margin-top: 20px;float: right"
                       background
                       layout="prev, pager, next"
                       :page-size="pageSize"
                       :total="total"
                       :current-page.sync="currentPage"
                       @current-change="page">
        </el-pagination>
    </div>
</template>

<script>
    export default {
        name: "AdminManage",
        data(){
            return{
                tableData:'',
                keyWord:'',
                status:'',
                pageSize:5,
                total:'',
                currentPage:1,
            }
        },
        methods:{
            page(currentPage){
                const _this = this
                _this.axios.get('/bookings/list?page='+currentPage+'&size='+_this.pageSize).then(function (response) {
                    _this.tableData = response.data.data
                    _this.pageSize = response.data.size
                    _this.total = response.data.total
                })
            }
        },
        created() {
            const _this = this
            _this.axios.get('/bookings/list?page=1&size='+_this.pageSize).then(function (response) {
                console.log(response.data.data)
                _this.tableData = response.data.data
                _this.pageSize = response.data.size
                _this.total = response.data.total
            })
        }
    }
</script>

<style scoped>

</style>
