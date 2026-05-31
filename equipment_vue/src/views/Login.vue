<template>
    <div class="login-container">
        <h1 class="beautified-title">实验室一站式预约管理</h1>
        <div class="login-wrapper">
            <el-form :model="ruleForm" :rules="rules"
                     status-icon
                     ref="ruleForm"
                     label-position="left"
                     label-width="0px"
                     class="demo-ruleForm login-page">
                <h3 class="title">系统登录</h3>
                <el-form-item prop="username">
                    <el-input type="text"
                              v-model="ruleForm.username"
                              auto-complete="off"
                              placeholder="用户名"
                              :prefix-icon="usernameIcon"
                    ></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input type="password"
                              v-model="ruleForm.password"
                              auto-complete="off"
                              placeholder="密码"
                              :prefix-icon="passwordIcon"
                    ></el-input>
                </el-form-item>

                <el-form-item>
                    <el-radio v-model="ruleForm.type" label="admin" border>管理员</el-radio>
                    <el-radio v-model="ruleForm.type" label="teacher" border>教师</el-radio>
                </el-form-item>

                <el-form-item style="width:100%;">
                    <el-button type="primary" style="width:100%;" @click="handleSubmit" :loading="logining">登录</el-button>
                </el-form-item>
                <el-form-item style="width:100%;text-align:center;">
                    <el-button type="primary" plain style="width:100%;" @click="registerDialogVisible = true">没有账号？立即注册</el-button>
                </el-form-item>
            </el-form>
        </div>

        <el-dialog title="用户注册" :visible.sync="registerDialogVisible" width="520px" @close="resetRegisterForm">
            <el-form ref="registerFormRef" :model="registerForm" :rules="registerRules" label-width="80px">
                <el-form-item label="姓名" prop="name">
                    <el-input v-model="registerForm.name" placeholder="请输入姓名"></el-input>
                </el-form-item>
                <el-form-item label="用户名" prop="username">
                    <el-input v-model="registerForm.username" placeholder="请输入用户名"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input v-model="registerForm.password" placeholder="请输入密码" show-password></el-input>
                </el-form-item>
                <el-form-item label="用户类型" prop="type">
                    <el-radio-group v-model="registerForm.type">
                        <el-radio label="teacher">教师</el-radio>
                        <el-radio label="admin">管理员</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="年龄" prop="age">
                    <el-input v-model="registerForm.age" placeholder="可选，填写年龄"></el-input>
                </el-form-item>
                <el-form-item label="性别" prop="gender">
                    <el-select v-model="registerForm.gender" placeholder="请选择性别" clearable>
                        <el-option label="男" value="男"></el-option>
                        <el-option label="女" value="女"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="地址" prop="address">
                    <el-input v-model="registerForm.address" placeholder="可选，填写联系地址"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="registerDialogVisible = false" style="width: 100px;">取 消</el-button>
                <el-button type="primary" :loading="registerLoading" @click="handleRegister" style="width: 100px;">注 册</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "Login",
        data() {
            return {
                ruleForm: {
                    username: 'teacher1',
                    password: '123123',
                    type: 'teacher'
                },
                rules: {
                    username: [{required: true, message: '请输入用户名', trigger: 'blur'}],
                    password: [{required: true, message: '请输入密码', trigger: 'blur'}]
                },
                logining: false,
                usernameIcon: 'el-icon-user',
                passwordIcon: 'el-icon-lock',
                registerDialogVisible: false,
                registerLoading: false,
                registerForm: {
                    name: '',
                    username: '',
                    password: '',
                    type: 'teacher',
                    age: '',
                    gender: '',
                    address: ''
                },
                registerRules: {
                    name: [{required: true, message: '请输入姓名', trigger: 'blur'}],
                    username: [{required: true, message: '请输入用户名', trigger: 'blur'}],
                    password: [{required: true, message: '请输入密码', trigger: 'blur'}],
                    type: [{required: true, message: '请选择用户类型', trigger: 'change'}]
                }
            }
        },
        methods: {
            handleSubmit() {
                this.$refs.ruleForm.validate((valid) => {
                    if (valid) {
                        this.logining = true
                        let _this = this
                        _this.axios.get('/login', {params: _this.ruleForm}).then(function (response) {
                            _this.logining = false
                            if (response.data.code == -1) {
                                _this.$alert(response.data.msg, '提示', {
                                    confirmButtonText: '确定'
                                })
                            } else {
                                if (_this.ruleForm.type == 'admin') {
                                    localStorage.setItem('user', JSON.stringify(response.data.data));
                                    _this.$router.replace({path: '/adminIndex'})
                                } else {
                                    localStorage.setItem('user', JSON.stringify(response.data.data));
                                    _this.$router.replace({path: '/userIndex'})
                                }
                            }
                        })
                    } else {
                        console.log('error submit!');
                        return false;
                    }
                })
            },
            handleRegister() {
                this.$refs.registerFormRef.validate((valid) => {
                    if (!valid) {
                        return
                    }
                    this.registerLoading = true
                    this.axios.post('/register', this.registerForm).then((response) => {
                        this.registerLoading = false
                        if (response.data.code === 0) {
                            this.$message.success('注册成功，请使用新账号登录')
                            this.registerDialogVisible = false
                            this.resetRegisterForm()
                        } else {
                            this.$message.error(response.data.msg || '注册失败')
                        }
                    }).catch(() => {
                        this.registerLoading = false
                        this.$message.error('注册失败，请稍后再试')
                    })
                })
            },
            resetRegisterForm() {
                if (this.$refs.registerFormRef) {
                    this.$refs.registerFormRef.resetFields()
                }
            }
        }
    }
</script>

<style scoped>
    * {
        box-sizing: border-box;
    }

    .beautified-title {
        font-family: 'Times New Roman', 'SimSun', serif;
        font-size: 64px;
        color: #ffffff;
        text-shadow: 
            0 0 20px rgba(255, 255, 255, 0.8),
            0 0 40px rgba(100, 181, 246, 0.5),
            0 4px 8px rgba(0, 0, 0, 0.3);
        transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
        text-align: center;
        padding: 20px;
        animation: fadeInUp 1.2s cubic-bezier(0.4, 0, 0.2, 1) both;
        margin-top: -30px;
        letter-spacing: 2px;
        font-weight: 700;
        position: relative;
    }

    .beautified-title::before {
        content: '';
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        width: 120%;
        height: 120%;
        background: radial-gradient(circle, rgba(100, 181, 246, 0.2) 0%, transparent 70%);
        border-radius: 50%;
        opacity: 0;
        transition: opacity 0.4s ease;
        pointer-events: none;
    }

    .beautified-title:hover {
        transform: scale(1.05);
        text-shadow: 
            0 0 30px rgba(255, 255, 255, 1),
            0 0 60px rgba(100, 181, 246, 0.7),
            0 6px 12px rgba(0, 0, 0, 0.4);
    }

    .beautified-title:hover::before {
        opacity: 1;
    }

    @keyframes fadeInUp {
        from {
            opacity: 0;
            transform: translateY(30px);
        }
        to {
            opacity: 1;
            transform: translateY(0);
        }
    }

    @keyframes slideIn {
        from {
            opacity: 0;
            transform: translateY(20px) scale(0.95);
        }
        to {
            opacity: 1;
            transform: translateY(0) scale(1);
        }
    }

    @keyframes shimmer {
        0% {
            background-position: -1000px 0;
        }
        100% {
            background-position: 1000px 0;
        }
    }

    .login-container {
        width: 100%;
        min-height: 100vh;
        height: 100vh;
        background-image: url('../assets/image/background.png');
        background-size: cover;
        background-position: center;
        background-attachment: fixed;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        margin: 0;
        padding: 0;
        position: relative;
        overflow: hidden;
    }

    .login-container::before {
        content: '';
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background: linear-gradient(135deg, rgba(30, 136, 229, 0.1) 0%, rgba(100, 181, 246, 0.1) 100%);
        animation: shimmer 20s linear infinite;
        pointer-events: none;
    }

    .login-wrapper {
        background: rgba(255, 255, 255, 0.15);
        backdrop-filter: blur(20px) saturate(180%);
        -webkit-backdrop-filter: blur(20px) saturate(180%);
        border-radius: 24px;
        border: 1px solid rgba(255, 255, 255, 0.3);
        box-shadow: 
            0 20px 60px rgba(0, 0, 0, 0.3),
            0 0 0 1px rgba(255, 255, 255, 0.1) inset,
            0 1px 0 rgba(255, 255, 255, 0.2) inset,
            0 0 0 1px rgba(255, 255, 255, 0.2);
        padding: 45px 40px;
        animation: slideIn 0.8s cubic-bezier(0.4, 0, 0.2, 1) both;
        position: relative;
        z-index: 1;
        transition: transform 0.3s ease, box-shadow 0.3s ease;
    }

    .login-wrapper:hover {
        transform: translateY(-2px);
        box-shadow: 
            0 25px 70px rgba(0, 0, 0, 0.35),
            0 0 0 1px rgba(255, 255, 255, 0.2) inset,
            0 1px 0 rgba(255, 255, 255, 0.3) inset;
    }

    .login-page {
        width: 380px;
        padding: 0;
    }

    .title {
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        font-size: 28px;
        font-weight: 600;
        color: #90caf9;
        text-shadow: 0 2px 10px rgba(144, 202, 249, 0.7), 0 0 20px rgba(144, 202, 249, 0.5), 0 0 30px rgba(144, 202, 249, 0.3);
        margin-bottom: 35px;
        text-align: center;
        letter-spacing: 0.5px;
        position: relative;
        padding-bottom: 15px;
    }

    .title::after {
        content: '';
        position: absolute;
        bottom: 0;
        left: 50%;
        transform: translateX(-50%);
        width: 60px;
        height: 3px;
        background: linear-gradient(90deg, transparent, #64b5f6, transparent);
        border-radius: 2px;
    }

    .el-form-item {
        margin-bottom: 24px;
    }

    .el-form-item:last-of-type {
        margin-bottom: 0;
    }

    /* 输入框样式优化 */
    .el-input {
        border-radius: 12px;
        border: 1.5px solid rgba(220, 223, 230, 0.6);
        height: 48px;
        font-size: 15px;
        transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
        background: rgba(255, 255, 255, 0.9);
        backdrop-filter: blur(10px);
    }

    .el-input:hover {
        border-color: rgba(100, 181, 246, 0.6);
        box-shadow: 0 2px 8px rgba(100, 181, 246, 0.15);
    }

    .el-input.is-focus {
        border-color: #64b5f6;
        box-shadow: 0 0 0 3px rgba(100, 181, 246, 0.15), 0 4px 12px rgba(100, 181, 246, 0.2);
    }

    .el-input__inner {
        background: transparent !important;
        border: none !important;
        padding-left: 40px;
        font-size: 15px;
        color: #333;
    }

    .el-input__inner::placeholder {
        color: #999;
    }

    .el-input__prefix {
        color: #64b5f6;
        font-size: 18px;
        left: 12px;
        transition: color 0.3s ease;
    }

    .el-input:hover .el-input__prefix {
        color: #1e88e5;
    }

    /* 单选框样式优化 */
    .el-radio {
        margin-right: 24px;
        font-size: 15px;
        font-weight: 500;
        transition: all 0.3s ease;
    }

    .el-radio__input.is-checked .el-radio__inner {
        background-color: #64b5f6;
        border-color: #64b5f6;
        box-shadow: 0 0 0 3px rgba(100, 181, 246, 0.2);
    }

    .el-radio__label {
        color: #ffffff !important;
        font-weight: 700;
        padding-left: 8px;
        text-shadow: 
            0 0 4px rgba(255, 255, 255, 0.8),
            0 0 8px rgba(255, 255, 255, 0.6),
            0 2px 8px rgba(0, 0, 0, 0.6),
            0 0 15px rgba(255, 255, 255, 0.4);
        filter: brightness(1.2);
    }

    .el-radio__input.is-checked + .el-radio__label {
        color: #e3f2fd !important;
        text-shadow: 0 2px 10px rgba(227, 242, 253, 0.8), 0 0 20px rgba(144, 202, 249, 0.6), 0 0 30px rgba(144, 202, 249, 0.4);
    }

    .el-radio.is-bordered {
        border-radius: 10px;
        padding: 10px 16px;
        border: 1.5px solid rgba(255, 255, 255, 0.5);
        transition: all 0.3s ease;
        background: rgba(255, 255, 255, 0.15);
        backdrop-filter: blur(2px);
    }

    .el-radio.is-bordered:hover {
        border-color: #64b5f6;
        box-shadow: 0 2px 8px rgba(100, 181, 246, 0.15);
    }

    .el-radio.is-bordered.is-checked {
        border-color: #64b5f6;
        background: rgba(100, 181, 246, 0.1);
    }

    /* 按钮样式优化 */
    .el-button {
        width: 100%;
        height: 48px;
        border-radius: 12px;
        font-size: 16px;
        font-weight: 600;
        letter-spacing: 0.5px;
        transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
        border: none;
        position: relative;
        overflow: hidden;
    }

    .el-button--primary {
        background: linear-gradient(135deg, #1e88e5 0%, #64b5f6 100%);
        box-shadow: 0 4px 15px rgba(100, 181, 246, 0.4);
    }

    .el-button--primary::before {
        content: '';
        position: absolute;
        top: 0;
        left: -100%;
        width: 100%;
        height: 100%;
        background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.3), transparent);
        transition: left 0.5s ease;
    }

    .el-button--primary:hover {
        transform: translateY(-2px);
        box-shadow: 0 6px 20px rgba(100, 181, 246, 0.5);
        background: linear-gradient(135deg, #1976d2 0%, #42a5f5 100%);
    }

    .el-button--primary:hover::before {
        left: 100%;
    }

    .el-button--primary:active {
        transform: translateY(0);
        box-shadow: 0 2px 10px rgba(100, 181, 246, 0.4);
    }

    .el-button.is-plain {
        background: rgba(255, 255, 255, 0.9);
        border: 1.5px solid #64b5f6;
        color: #64b5f6;
        backdrop-filter: blur(10px);
    }

    .el-button.is-plain:hover {
        background: rgba(100, 181, 246, 0.1);
        border-color: #1e88e5;
        color: #1e88e5;
        transform: translateY(-2px);
        box-shadow: 0 4px 12px rgba(100, 181, 246, 0.2);
    }

    /* 对话框样式优化 */
    .el-dialog {
        border-radius: 20px;
        overflow: hidden;
    }

    .el-dialog__header {
        background: linear-gradient(135deg, #1e88e5 0%, #64b5f6 100%);
        padding: 20px 24px;
        margin: 0;
    }

    .el-dialog__title {
        color: #ffffff;
        font-weight: 600;
        font-size: 18px;
    }

    .el-dialog__body {
        padding: 30px 24px;
        background: #fafafa;
    }

    .el-dialog__footer {
        padding: 20px 24px;
        background: #fafafa;
        border-top: 1px solid #e4e7ed;
    }

    .dialog-footer {
        display: flex;
        justify-content: center;
        gap: 12px;
    }

    .dialog-footer .el-button {
        width: 110px;
        height: 42px;
        border-radius: 10px;
        font-weight: 500;
    }

    .dialog-footer .el-button:not(.el-button--primary) {
        background: #ffffff;
        border: 1.5px solid #dcdfe6;
        color: #606266;
    }

    .dialog-footer .el-button:not(.el-button--primary):hover {
        border-color: #64b5f6;
        color: #64b5f6;
    }

    /* 表单输入框在对话框中的样式 */
    .el-dialog .el-input {
        background: #ffffff;
    }

    .el-dialog .el-input__inner {
        background: #ffffff !important;
    }

    /* 响应式设计 */
    @media (max-width: 768px) {
        .beautified-title {
            font-size: 48px;
        }

        .login-wrapper {
            padding: 35px 30px;
            margin: 20px;
        }

        .login-page {
            width: 100%;
            max-width: 380px;
        }
    }
</style>
