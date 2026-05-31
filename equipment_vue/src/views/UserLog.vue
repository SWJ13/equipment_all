<template>
    <div class="lab-container">
        <headers></headers>
        <div style="background: linear-gradient(135deg, #1a1a2e 0%, #16213e 100%)">
            <!-- 头部 -->
            <div class="header">
                <h1 class="title">日志与周报提交</h1>
                <div class="week-control">
                    <el-button @click="prevWeek" icon="ArrowLeftBold" circle />
                    <span class="current-week">第 {{ currentWeek }} 周</span>
                    <el-button @click="nextWeek" icon="ArrowRightBold" circle />
                </div>
            </div>

            <!-- 主要内容区域 -->
            <div class="main-content">
                <!-- 日志提交 -->
                <el-card class="log-card" shadow="hover">
                    <template #header>
                        <div class="card-header">
                            <span>📝 实验日志提交</span>
                        </div>
                    </template>
                    <el-form>
                        <el-input
                                v-model="logContent"
                                type="textarea"
                                :rows="3"
                                placeholder="请输入今日实验日志..."
                                class="input-box"
                        />
                        <el-button @click="submitLog" type="primary" class="submit-btn">
                            提交日志
                        </el-button>
                    </el-form>
                    <transition-group name="list" tag="div">
                        <div v-for="log in logs" :key="log.id" class="log-item">
                            <p class="content" style="text-align: left">{{ log.content }}</p>
                        </div>
                    </transition-group>
                </el-card>

                <!-- 周报提交 -->
                <el-card class="report-card" shadow="hover">
                    <template #header>
                        <div class="card-header">
                            <span>📑 实验周报提交</span>
                        </div>
                    </template>
                    <el-form>
                        <el-input
                                v-model="reportContent"
                                type="textarea"
                                :rows="4"
                                placeholder="请输入本周实验总结..."
                                class="input-box"
                        />
                        <el-button type="success" @click="submitReport" class="submit-btn">
                            提交周报
                        </el-button>
                    </el-form>
                    <transition-group name="list" tag="div">
                        <div v-for="report in reports" :key="report.id" class="report-item">
                            <p class="content">{{ report.content }}</p>
                        </div>
                    </transition-group>
                </el-card>

                <!-- 值日表 -->
                <el-card class="duty-card" shadow="hover">
                    <template #header>
                        <div class="card-header">
                            <span class="duty-title">📅 第 {{ currentWeek }} 周值日表</span>
                        </div>
                    </template>
                    <div class="duty-grid">
                        <div
                                v-for="(day, index) in dutySchedule"
                                :key="index"
                                class="duty-day"
                        >
                            <div class="day-header">{{ day.name }}</div>
                            <div class="day-content">
                                <el-tag effect="dark" type="info">{{ day.person }}</el-tag>
                            </div>
                        </div>
                    </div>
                </el-card>
            </div>
        </div>
        <bottoms></bottoms>
    </div>
</template>

<script>
    import { Message } from 'element-ui';
    import headers from "../components/header"
    import bottoms from "../components/bottom"

    export default {
        components:{
            headers,
            bottoms
        },
        data() {
            return {
                currentWeek: 10,
                logContent: '',
                reportContent: '',
                logs: [],
                reports: [],
                dutySchedule: [
                    { name: "周一", person: "李工" },
                    { name: "周二", person: "张博" },
                    { name: "周三", person: "王硕" },
                    { name: "周四", person: "赵工" },
                    { name: "周五", person: "陈博" },
                    { name: "周六", person: "林硕" },
                    { name: "周日", person: "吴工" },
                ],
            };
        },
        created() {
            this.axios.get("/logs/index").then((response) => {
                this.logs = response.data
            }).catch((error) => {
                console.error(error);
            });

            this.axios.get("/reports/index").then((response) => {
                this.reports = response.data
            }).catch((error) => {
                console.error(error);
            });
        },
        mounted() {
            this.fetchLogsAndReports();
        },
        methods: {
            submitLog() {
                if (!this.logContent.trim()) {
                    Message.warning("日志内容不能为空！");
                } else {
                    const _this = this
                    _this.axios
                        .post("/logs/add?content="+this.logContent)
                        .then((response) => {
                            if (response.data === true) {
                                _this.$alert('日志提交成功！', '', {
                                    confirmButtonText: '确定',
                                    callback: action => {
                                        location.reload()
                                    }
                                });
                            } else {
                                Message.error("日志提交失败，请稍后重试。");
                            }
                        })
                        .catch((error) => {
                            Message.error("日志提交失败，请稍后重试。");
                        });
                }
            },
            async fetchLogsAndReports() {
                try {
                    const logResponse = await this.axios.get(
                        "/logs/list?page=1&size=100"
                    );
                    this.logs = logResponse.data.data || [];
                    const reportResponse = await this.axios.get(
                        "/reports/list?page=1&size=100"
                    );
                    this.reports = reportResponse.data.data || [];
                } catch (error) {
                    console.error("数据加载失败:", error);
                    // Message.error("数据加载失败！");
                }
            },
            async submitReport() {
                if (!this.reportContent.trim()) {
                    Message.warning("周报内容不能为空！");
                } else {
                    const _this = this
                    axios
                        .post("/reports/add?content="+this.reportContent)
                        .then((response) => {
                            if (response.data === true) {
                                _this.$alert('周报提交成功！', '', {
                                    confirmButtonText: '确定',
                                    callback: action => {
                                        location.reload()
                                    }
                                });
                            } else {
                                Message.error("周报提交失败，请稍后重试。");
                            }
                        })
                        .catch((error) => {
                            Message.error("周报提交失败，请稍后重试。");
                        });
                }
            },
            prevWeek() {
                if (this.currentWeek > 1) this.currentWeek--;
            },
            nextWeek() {
                this.currentWeek++;
            },
        },
    };
</script>

<style scoped>
    .lab-container {
        min-height: 100vh;
        padding: 2rem;
        color: #fff;
        max-width: 1200px;
        margin: 0 auto;
        padding: 20px;
        margin-top: -80px;

        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 2rem;
            padding: 1rem 2rem;
            background: rgba(255, 255, 255, 0.05);
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);

            .title {
                font-size: 2.5rem;
                font-weight: bold;
                -webkit-text-fill-color: transparent;
                text-shadow: 0 1px 2px rgb(0, 0, 0);
            }

            .week-control {
                display: flex;
                align-items: center;
                gap: 1rem;

                .current-week {
                    font-size: 1.5rem;
                    color: #90e0ef;
                    font-weight: bold;
                }
            }
        }

        .main-content {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(350px, 1fr));
            gap: 2.5rem;

            .log-card,
            .report-card {
                background: rgba(255, 255, 255, 0.08);
                border: 1px solid rgba(255, 255, 255, 0.15);
                backdrop-filter: blur(15px);
                border-radius: 15px;
                box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
                padding: 1.5rem;

                .card-header {
                    font-size: 1.5rem;
                    color: #90e0ef;
                    font-weight: bold;
                    margin-bottom: 1rem;
                }

                .input-box {
                    margin-bottom: 1.5rem;

                    :deep(.el-textarea__inner) {
                        background: rgba(255, 255, 255, 0.05);
                        color: #fff;
                        border: 1px solid rgba(255, 255, 255, 0.1);
                        border-radius: 8px;
                        padding: 1rem;
                        font-size: 1rem;
                        transition: all 0.3s ease;

                        &:focus {
                            border-color: #00b4d8;
                            box-shadow: 0 0 8px rgba(0, 180, 216, 0.4);
                        }
                    }
                }

                .submit-btn {
                    width: 100%;
                    margin-top: 1.5rem;
                    transition: all 0.3s ease;
                    padding: 0.8rem 1rem;
                    font-size: 1rem;
                    font-weight: bold;

                    &:hover {
                        transform: translateY(-3px);
                        box-shadow: 0 4px 12px rgba(0, 180, 216, 0.6);
                    }
                }

                .log-item,
                .report-item {
                    padding: 1.5rem;
                    margin: 1.5rem 0;
                    background: rgba(0, 180, 216, 0.15);
                    border-radius: 10px;
                    transition: all 0.3s ease;

                    .content {
                        margin: 0;
                        color: #e6f7ff;
                        font-size: 1.1rem;
                    }

                    &:hover {
                        transform: translateX(8px);
                        background: rgba(0, 180, 216, 0.25);
                    }
                }
            }

            .duty-card {
                background: rgba(255, 255, 255, 0.08);
                border: 1px solid rgba(255, 255, 255, 0.15);
                border-radius: 15px;
                box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
                padding: 1.5rem;

                .card-header .duty-title {
                    color: #00b4d8; /* 让值日表标题颜色更明显 */
                }

                .duty-grid {
                    display: grid;
                    grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
                    gap: 1.5rem;

                    .duty-day {
                        text-align: center;
                        padding: 1.5rem;
                        background: rgba(255, 255, 255, 0.05);
                        border-radius: 10px;
                        transition: all 0.3s ease;

                        .day-header {
                            color: #90e0ef;
                            margin-bottom: 1rem;
                            font-size: 1.2rem;
                            font-weight: bold;
                        }

                        &:hover {
                            transform: translateY(-8px);
                            box-shadow: 0 8px 20px rgba(0, 180, 216, 0.4);
                        }
                    }
                }
            }
        }
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
</style>
