<template>
    <div style="margin-top: 60px;margin-left:80px;border: 0px solid red;" >
        <el-form label-width="100px" class="demo-ruleForm">
            <el-form-item>
                <el-button type="success" icon="el-icon-plus" style="position: relative;left: -100px;" @click="add()">添加公告</el-button>
            </el-form-item>
        </el-form>

        <el-table
                :data="tableData"
                border
                stripe
                style="width: 100%">
            <el-table-column property="id" label="编号" width="80" />
            <el-table-column property="title" label="标题" width="220" />
            <el-table-column property="content" label="内容" width="530" />
            <el-table-column property="date" label="日期" width="160">
            </el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button
                            size="mini"
                            @click="edit(scope.row)">编辑</el-button>
                    <el-button
                            size="mini"
                            type="danger"
                            @click="del(scope.row)">删除</el-button>
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

        <!-- 添加公告 -->
        <el-dialog title="添加公告" :visible.sync="dialogTableVisible" width="30%">
            <el-form :model="addForm" label-width="auto" style="max-width: 600px">
                <el-form-item label="标题">
                    <el-input v-model="addForm.title" />
                </el-form-item>
                <el-form-item label="内容">
                    <el-input v-model="addForm.content" />
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="addClick">添加</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

        <!-- 修改公告 -->
        <el-dialog title="修改公告" :visible.sync="dialogTableVisible2" width="30%">
            <el-form :model="updateForm" label-width="auto" style="max-width: 600px">
                <el-form-item label="ID">
                    <el-input v-model="updateForm.id" readOnly />
                </el-form-item>
                <el-form-item label="标题">
                    <el-input v-model="updateForm.title" />
                </el-form-item>
                <el-form-item label="内容">
                    <el-input v-model="updateForm.content" />
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="updateClick">修改</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

    </div>
</template>

<script>
    export default {
        name: "AdminManage",
        data(){
            return{
                tableData:'',
                type:'',
                pageSize:5,
                total:'',
                currentPage:1,
                dialogTableVisible:false,
                dialogTableVisible2:false,
                addForm:{
                    title:'',
                    content:''
                },
                updateForm:{
                    id:'',
                    title:'',
                    content:''
                }
            }
        },
        methods:{
            page(currentPage){
                const _this = this
                _this.axios.get('/notices/list?page='+currentPage+'&size='+_this.pageSize).then(function (response) {
                    _this.tableData = response.data.data
                    _this.pageSize = response.data.size
                    _this.total = response.data.total
                })
            },
            add(){
                this.dialogTableVisible = true
            },
            edit(row){
                this.dialogTableVisible2 = true
                this.updateForm.id = row.id
                this.updateForm.title = row.title
                this.updateForm.content = row.content
            },
            addClick(){
                const _this = this
                console.log(_this.addForm)
                _this.axios.post('/notices/add',_this.addForm).then((response) => {
                    if (response.data == true) {
                        _this.$alert('公告【'+_this.addForm.title+'】添加成功', '', {
                            confirmButtonText: '确定',
                            callback: action => {
                                location.reload()
                            }
                        });
                    }
                })
            },
            updateClick(){
                const _this = this
                _this.axios.put('/notices/update',_this.updateForm).then((response) => {
                    if (response.data == true) {
                        _this.$alert('公告【'+_this.updateForm.title+'】修改成功', '', {
                            confirmButtonText: '确定',
                            callback: action => {
                                location.reload()
                            }
                        });
                    }
                })
            },
            del(row){
                const _this = this
                this.$confirm('确认删除公告【'+row.title+'】吗？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    _this.axios.delete('/notices/delete/'+row.id).then(function (resp) {
                        if(resp.data == true){
                            _this.$alert('公告【'+row.title+'】已删除', '', {
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
                _this.axios.get('/instruments/list?page=1&size='+_this.pageSize+'&type='+_this.type).then(function (response) {
                    _this.tableData = response.data.data
                    _this.pageSize = response.data.size
                    _this.total = response.data.total
                })
            },
        },
        created() {
            const _this = this
            _this.axios.get('/notices/list?page=1&size='+_this.pageSize).then(function (response) {
                _this.tableData = response.data.data
                _this.pageSize = response.data.size
                _this.total = response.data.total
            })
        }
    }
</script>

<style scoped>

</style>
