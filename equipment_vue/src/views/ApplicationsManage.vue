<template>
    <div style="margin-top: 60px;margin-left:80px;border: 0px solid red;" >
        <el-form label-width="100px" class="demo-ruleForm">
            <el-form-item label="审核类型：" prop="keyWord">
                <el-radio v-model="status" label="未审核" style="position: relative;left: 0px;">未审核</el-radio>
                <el-radio v-model="status" label="已审核" style="position: relative;left: 0px;">已审核</el-radio>
                <el-button type="primary" icon="el-icon-search" style="position: relative;left: 30px;" @click="search()">搜索</el-button>
            </el-form-item>
        </el-form>

        <el-table
                :data="tableData"
                border
                stripe
                style="width: 100%">
            <el-table-column property="id" label="编号" width="80" />
            <el-table-column property="name" label="名称" width="100" />
            <el-table-column property="type" label="类型" width="100" />
            <el-table-column property="quantity" label="数量" width="100" />
            <el-table-column property="budget" label="预算" width="90" />
            <el-table-column property="urgency" label="等级" width="100">
                <template slot-scope="scope">
                    <el-rate
                            v-model="scope.row.urgency"
                            disabled
                            show-score
                            text-color="#ff9900">
                    </el-rate>
                </template>
            </el-table-column>
            <el-table-column property="reason" label="原因" width="160" />
            <el-table-column property="tname" label="申请人" width="80" />
            <el-table-column property="status" label="状态" width="100" />
            <el-table-column property="date" label="日期" />
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button
                            size="medium"
                            type="primary"
                            :disabled="scope.row.status == '已审核'"
                            @click="examine(scope.row)">审核</el-button>
                </template>
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
                _this.axios.get('/applications/list?page='+currentPage+'&size='+_this.pageSize).then(function (response) {
                    _this.tableData = response.data.data
                    _this.pageSize = response.data.size
                    _this.total = response.data.total
                })
            },
            examine(row){
                const _this = this
                this.$confirm('确认审核【'+row.name+'】吗？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    _this.axios.post('/applications/examine/'+row.id).then(function (resp) {
                        if(resp.data == true){
                            _this.$alert('申购【'+row.name+'】已审核', '', {
                                confirmButtonText: '确定',
                                callback: action => {
                                    location.reload()
                                }
                            });
                        }
                    });
                })
            },
            search() {
                const _this = this
                //让翻页复原
                _this.currentPage = 1
                _this.axios.get('/applications/list?page=1&size='+_this.pageSize+'&status='+_this.status).then(function (response) {
                    _this.tableData = response.data.data
                    _this.pageSize = response.data.size
                    _this.total = response.data.total
                })
            },
        },
        created() {
            const _this = this
            _this.axios.get('/applications/list?page=1&size='+_this.pageSize).then(function (response) {
                _this.tableData = response.data.data
                _this.pageSize = response.data.size
                _this.total = response.data.total
            })
        }
    }
</script>

<style scoped>

</style>
