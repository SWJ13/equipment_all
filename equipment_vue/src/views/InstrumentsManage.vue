<template>
    <div style="margin-top: 60px;margin-left:80px;border: 0px solid red;" >
        <el-form label-width="100px" class="demo-ruleForm">
            <el-form-item label="仪器类型：" prop="keyWord">
                <el-select v-model="type">
                    <el-option label="分析仪器" value="分析仪器" />
                    <el-option label="实验设备" value="实验设备" />
                    <el-option label="测量仪器" value="测量仪器" />
                </el-select>
                <el-button type="primary" icon="el-icon-search" style="position: relative;left: 30px;" @click="search()">搜索</el-button>
                <el-button type="success" icon="el-icon-plus" style="position: relative;left: 60px;" @click="add()">添加仪器</el-button>
            </el-form-item>
        </el-form>

        <el-table
                :data="tableData"
                border
                stripe
                style="width: 100%">
            <el-table-column property="id" label="编号" width="80" />
            <el-table-column property="name" label="名称" width="220" />
            <el-table-column property="type" label="类型" width="180" />
            <el-table-column property="status" label="状态" width="160">
                <template slot-scope="scope">
                    <el-switch
                            disabled
                            v-model="scope.row.statusVO"
                            active-color="#13ce66"
                            inactive-color="#ff4949">
                    </el-switch>
                    <span style="margin-left: 20px">{{scope.row.statusText}}</span>
                </template>
            </el-table-column>
            <el-table-column property="description" label="描述" />
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

        <!-- 添加仪器 -->
        <el-dialog title="添加仪器" :visible.sync="dialogTableVisible" width="30%">
            <el-form :model="addForm" label-width="auto" style="max-width: 600px">
                <el-form-item label="名称">
                    <el-input v-model="addForm.name" />
                </el-form-item>
                <el-form-item label="类型">
                    <el-select v-model="addForm.type">
                        <el-option label="分析仪器" value="分析仪器"/>
                        <el-option label="实验设备" value="实验设备"/>
                        <el-option label="测量仪器" value="测量仪器"/>
                    </el-select>
                </el-form-item>
                <el-form-item label="可用">
                    <el-switch
                            v-model="addForm.status"
                            active-color="#13ce66"
                            inactive-color="#ff4949">
                    </el-switch>
                </el-form-item>
                <el-form-item label="描述">
                    <el-input v-model="addForm.description" />
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="addClick">添加</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

        <!-- 修改仪器 -->
        <el-dialog title="修改仪器" :visible.sync="dialogTableVisible2" width="30%">
            <el-form :model="updateForm" label-width="auto" style="max-width: 600px">
                <el-form-item label="ID">
                    <el-input v-model="updateForm.id" readOnly />
                </el-form-item>
                <el-form-item label="名称">
                    <el-input v-model="updateForm.name" />
                </el-form-item>
                <el-form-item label="类型">
                    <el-select v-model="updateForm.type">
                        <el-option label="分析仪器" value="分析仪器"/>
                        <el-option label="实验设备" value="实验设备"/>
                        <el-option label="测量仪器" value="测量仪器"/>
                    </el-select>
                </el-form-item>
                <el-form-item label="可用">
                    <el-switch
                            v-model="updateForm.status"
                            active-color="#13ce66"
                            inactive-color="#ff4949">
                    </el-switch>
                </el-form-item>
                <el-form-item label="描述">
                    <el-input v-model="updateForm.description" />
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
                    name:'',
                    type:'',
                    status:true,
                    description:''
                },
                updateForm:{
                    id:'',
                    name:'',
                    type:'',
                    status:true,
                    description:''
                }
            }
        },
        methods:{
            page(currentPage){
                const _this = this
                _this.axios.get('/instruments/list?page='+currentPage+'&size='+_this.pageSize).then(function (response) {
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
                this.updateForm.name = row.name
                this.updateForm.type = row.type
                this.updateForm.status = row.statusVO
                this.updateForm.description = row.description
            },
            addClick(){
                const _this = this
                if(_this.addForm.status){
                    _this.addForm.status = "available"
                } else {
                    _this.addForm.status = "unavailable"
                }
                _this.axios.post('/instruments/add',_this.addForm).then((response) => {
                    if (response.data == true) {
                        _this.$alert('仪器【'+_this.addForm.name+'】添加成功', '', {
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
                if(_this.updateForm.status){
                    _this.updateForm.status = "available"
                } else {
                    _this.updateForm.status = "unavailable"
                }
                _this.axios.put('/instruments/update',_this.updateForm).then((response) => {
                    if (response.data == true) {
                        _this.$alert('仪器【'+_this.updateForm.name+'】修改成功', '', {
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
                this.$confirm('确认删除仪器【'+row.name+'】吗？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    _this.axios.delete('/instruments/delete/'+row.id).then(function (resp) {
                        if(resp.data == true){
                            _this.$alert('仪器【'+row.name+'】已删除', '', {
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
            _this.axios.get('/instruments/list?page=1&size='+_this.pageSize).then(function (response) {
                _this.tableData = response.data.data
                _this.pageSize = response.data.size
                _this.total = response.data.total
            })
        }
    }
</script>

<style scoped>

</style>
