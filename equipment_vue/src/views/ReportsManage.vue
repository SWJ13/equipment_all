<template>
    <div style="margin-top: 60px;margin-left:80px;border: 0px solid red;" >
        <el-table
                :data="tableData"
                border
                stripe
                style="width: 100%">
            <el-table-column property="id" label="编号" width="130" />
            <el-table-column property="content" label="内容" width="690" />
            <el-table-column property="time" label="日期" width="180" />
            <el-table-column property="teacherName" label="作者">
            </el-table-column>
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
                pageSize:5,
                total:'',
                currentPage:1,
            }
        },
        methods:{
            page(currentPage){
                const _this = this
                _this.axios.get('/reports/list?page='+currentPage+'&size='+_this.pageSize).then(function (response) {
                    _this.tableData = response.data.data
                    _this.pageSize = response.data.size
                    _this.total = response.data.total
                })
            }
        },
        created() {
            const _this = this
            _this.axios.get('/reports/list?page=1&size='+_this.pageSize).then(function (response) {
                _this.tableData = response.data.data
                _this.pageSize = response.data.size
                _this.total = response.data.total
            })
        }
    }
</script>

<style scoped>

</style>
