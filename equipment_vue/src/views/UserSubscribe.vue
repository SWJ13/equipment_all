<template>
    <div class="container">
        <headers></headers>
        <!-- 头部标题 -->
        <div class="header">
            <h1 class="title">🧪 耗材申购</h1>
            <div class="stats">
                <div
                        class="stat-card"
                        v-for="stat in stats"
                        :key="stat.title"
                        @mouseover="hoverEffect"
                        @mouseout="hoverEffect"
                >
                    <h3>{{ stat.title }}</h3>
                    <p>{{ stat.value }}</p>
                </div>
            </div>
        </div>

        <!-- 申购表单 -->
        <el-form
                :model="form"
                :rules="rules"
                ref="formRef"
                class="form-container"
                @submit.prevent="submitForm"
                label-width="auto"
        >
            <el-row :gutter="20">
                <el-col :xs="24" :sm="12" :md="8">
                    <el-form-item label="耗材名称" prop="name">
                        <el-input
                                v-model="form.name"
                                placeholder="请输入耗材名称"
                                clearable
                        />
                    </el-form-item>
                </el-col>

                <el-col :xs="24" :sm="12" :md="8">
                    <el-form-item label="耗材类型" prop="type">
                        <el-select v-model="form.type" placeholder="请选择类型" filterable>
                            <el-option
                                    v-for="item in materialTypes"
                                    :key="item"
                                    :label="item"
                                    :value="item"
                            />
                        </el-select>
                    </el-form-item>
                </el-col>

                <el-col :xs="24" :sm="12" :md="8">
                    <el-form-item label="申购数量" prop="quantity">
                        <el-input-number
                                v-model="form.quantity"
                                :min="1"
                                :max="1000"
                                controls-position="right"
                        />
                    </el-form-item>
                </el-col>

                <el-col :xs="24" :sm="12" :md="8">
                    <el-form-item label="预算金额" prop="budget">
                        <el-input
                                v-model="form.budget"
                                placeholder="请输入预算金额"
                                clearable
                        >
                            <template #prepend>¥</template>
                        </el-input>
                    </el-form-item>
                </el-col>

                <el-col :xs="24" :sm="12" :md="8">
                    <el-form-item label="紧急程度" prop="urgency">
                        <el-rate style="margin-top: 8px"
                                v-model="form.urgency"
                                :colors="['#99A9BF', '#F7BA2A', '#FF9900']"
                                :max="3"
                                show-text
                                :texts="['普通', '紧急', '加急']"
                        />
                    </el-form-item>
                </el-col>

                <el-col :xs="24">
                    <el-form-item label="申购原因" prop="reason">
                        <el-input
                                v-model="form.reason"
                                type="textarea"
                                :rows="3"
                                placeholder="详细说明申购理由"
                                maxlength="200"
                                show-word-limit
                        />
                    </el-form-item>
                </el-col>
            </el-row>

            <div class="form-actions">
                <el-button type="primary" @click="submitForm">提交申请</el-button>
                <el-button @click="resetForm">重置表单</el-button>
            </div>
        </el-form>

        <!-- 申请记录表格 -->
        <transition-group name="list" tag="div">
            <el-table
                    :data="applications"
                    style="width: 100%"
                    key="table"
                    v-if="applications.length"
                    class="application-table"
            >
                <el-table-column prop="name" label="耗材名称" width="150" />
                <el-table-column prop="type" label="类型" width="120" />
                <el-table-column prop="quantity" label="数量" width="100" />
                <el-table-column prop="budget" label="预算" width="120">
                    <template #default="{ row }">¥{{ row.budget }}</template>
                </el-table-column>
                <el-table-column prop="urgency" label="紧急程度" width="120">
                    <template #default="{ row }">
                        <el-tag :type="urgencyTypes[row.urgency - 1].type">
                            {{ urgencyTypes[row.urgency - 1].text }}
                        </el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="reason" label="申购原因" />
                <el-table-column prop="date" label="申购时间" />
                <el-table-column label="操作" width="120">
                    <template #default="{ row }">
                        <el-button
                                type="danger"
                                size="small"
                                @click="confirmDelete(row.id)"
                        >删除</el-button>
                    </template>
                </el-table-column>
            </el-table>

            <el-empty v-else description="暂无申购记录" key="empty" />
        </transition-group>
        <bottoms></bottoms>
    </div>
</template>

<script>
    import { Message, MessageBox } from 'element-ui';
    import headers from "../components/header"
    import bottoms from "../components/bottom"

    export default {
        components:{
            headers,
            bottoms
        },
        data() {
            return {
                form: {
                    name: '',
                    type: '',
                    quantity: 1,
                    budget: '',
                    urgency: 1,
                    reason: ''
                },
                rules: {
                    name: [
                        { required: true, message: '请输入耗材名称', trigger: 'blur' }
                    ],
                    type: [
                        { required: true, message: '请选择耗材类型', trigger: 'change' }
                    ],
                    quantity: [
                        {
                            required: true,
                            type: 'number',
                            min: 1,
                            message: '数量至少为 1',
                            trigger: 'blur'
                        }
                    ],
                    budget: [
                        {
                            required: true,
                            pattern: /^\d+(\.\d{1,2})?$/,
                            message: '请输入有效金额',
                            trigger: 'blur'
                        }
                    ],
                    reason: [
                        { required: true, message: '请输入申购原因', trigger: 'blur' }
                    ]
                },
                materialTypes: [
                    '化学试剂',
                    '实验器材',
                    '防护用品',
                    '玻璃器皿',
                    '电子元件',
                    '生物样本'
                ],
                urgencyTypes: [
                    { type: '', text: '普通' },
                    { type: 'warning', text: '紧急' },
                    { type: 'danger', text: '加急' }
                ],
                applications: [],
            };
        },
        computed: {
            stats() {
                return [
                    {
                        title: '总预算',
                        value: `¥${this.applications.reduce((sum, app) => sum + Number(app.budget), 0)}`
                    },
                    { title: '申请数量', value: this.applications.length },
                    {
                        title: '紧急申请',
                        value: this.applications.filter(app => app.urgency > 1).length
                    }
                ];
            }
        },
        mounted() {
            this.getApplications();
        },
        methods: {
            submitForm() {
                this.$refs.formRef.validate((valid) => {
                    if (valid) {
                        const _this = this
                        _this.axios.post('/applications/add', this.form)
                            .then((response) => {
                                if (response.data === true) {
                                    _this.$alert('提交成功！', '', {
                                        confirmButtonText: '确定',
                                        callback: action => {
                                            location.reload()
                                        }
                                    });
                                }
                            })
                            .catch(() => {
                                Message.warning('请完善表单信息');
                            });
                    } else {
                        Message.warning('请完善表单信息');
                    }
                });
            },
            resetForm() {
                this.$refs.formRef.resetFields();
            },
            confirmDelete(id) {
                MessageBox.confirm('确认要删除该申请吗？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                })
                    .then(() => {
                        this.deleteApplication(id);
                    })
                    .catch(() => {
                        Message.info('已取消删除');
                    });
            },
            deleteApplication(id) {
                const _this = this
                _this.axios.delete('/applications/delete/'+id)
                    .then((response) => {
                        if (response.data == true) {
                            _this.$alert('删除成功！', '', {
                                confirmButtonText: '确定',
                                callback: action => {
                                    location.reload()
                                }
                            });
                        } else {
                            Message.error('删除申请失败');
                        }
                    })
                    .catch(() => {
                        Message.error('删除申请失败');
                    });
            },
            hoverEffect(e) {
                e.currentTarget.style.transform = e.type === 'mouseover' ? 'translateY(-5px)' : 'translateY(0)';
                e.currentTarget.style.transition = 'transform 0.3s ease';
            },
            getApplications() {
                this.axios.get('/applications/index')
                    .then((response) => {
                        this.applications = response.data;
                    })
                    .catch(() => {
                        Message.error('获取申请记录失败');
                    });
            }
        }
    };
</script>

<style scoped>
    .container {
        max-width: 1200px;
        margin: 0 auto;
        padding: 20px;
        margin-top: -80px;
    }

    .header {
        background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
        padding: 2rem;
        border-radius: 15px;
        margin-bottom: 2rem;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    }

    .title {
        color: #2c3e50;
        text-align: center;
        margin-bottom: 1.5rem;
        font-size: 2.5rem;
        text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.1);
    }

    .stats {
        display: grid;
        grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
        gap: 1rem;
    }

    .stat-card {
        background: white;
        padding: 1.5rem;
        border-radius: 10px;
        text-align: center;
        cursor: pointer;
        transition: all 0.3s ease;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
    }

    .stat-card:hover {
        transform: translateY(-5px);
        box-shadow: 0 6px 12px rgba(0, 0, 0, 0.1);
    }

    .form-container {
        background: white;
        padding: 2rem;
        border-radius: 15px;
        margin-bottom: 2rem;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
    }

    .form-actions {
        text-align: center;
        margin-top: 1.5rem;
    }

    .application-table {
        margin-top: 2rem;
        border-radius: 15px;
        overflow: hidden;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
    }

    .list-enter-active,
    .list-leave-active {
        transition: all 0.5s ease;
    }
    .list-enter-from,
    .list-leave-to {
        opacity: 0;
        transform: translateX(30px);
    }

    @media (max-width: 768px) {
        .header {
            padding: 1rem;
        }

        .title {
            font-size: 1.8rem;
        }

        .stats {
            grid-template-columns: 1fr;
        }
    }
</style>
