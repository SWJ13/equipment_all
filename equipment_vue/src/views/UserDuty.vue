<template>
    <div class="duty-container">
        <headers></headers>
        <!-- 周次导航 -->
        <div class="week-nav">
            <button @click="prevWeek" class="nav-btn">◀</button>
            <h2>第{{ currentWeek }}周值日表 ({{ weekRange }})</h2>
            <button @click="nextWeek" class="nav-btn">▶</button>
        </div>

        <!-- 值日表 -->
        <transition-group name="list" tag="div" class="duty-grid">
            <div
                    v-for="day in weekSchedule"
                    :key="day.date"
                    class="duty-card"
                    :class="{ 'today': day.isToday }"
            >
                <div class="date-header">
                    <div class="weekday">{{ day.weekday }}</div>
                    <div class="date">{{ day.formattedDate }}</div>
                </div>

                <div class="duty-info">
                    <transition name="fade" mode="out-in">
                        <div v-if="day.duty" key="assigned">
                            <div class="duty-person">
                                <span class="icon">🧹</span>
                                {{ day.duty.person }}
                            </div>
                            <div class="status-tag" :class="day.duty.status">
                                {{ day.duty.status }}
                            </div>
                        </div>
                        <div v-else key="unassigned" class="unassigned">
                            暂未安排值日
                        </div>
                    </transition>
                </div>

                <div class="additional-info">
                    <div v-if="day.duty" class="lab-area">
                        <span class="icon">📍</span>
                        负责区域：{{ day.duty.area }}
                    </div>
                    <div v-if="day.duty" class="notes">
                        <div class="notes">
                            <span class="icon">📝</span>
                            {{ day.duty.notes }}
                        </div>
                    </div>
                </div>
            </div>
        </transition-group>
        <bottoms></bottoms>
    </div>
</template>

<script>
    import headers from "../components/header"
    import bottoms from "../components/bottom"
    export default {
        components:{
            headers,
            bottoms
        },
        data() {
            return {
                currentWeek: 1,
                scheduleData: this.generateScheduleData()
            };
        },
        computed: {
            weekSchedule() {
                const days = [];
                const today = new Date();
                const currentDate = new Date(today);
                currentDate.setDate(currentDate.getDate() + (this.currentWeek - 1) * 7 - currentDate.getDay() + 1);

                for (let i = 0; i < 7; i++) {
                    const date = new Date(currentDate);
                    date.setDate(date.getDate() + i);

                    const dateString = date.toISOString().split('T')[0];
                    const isToday = date.toDateString() === today.toDateString();

                    days.push({
                        date: dateString,
                        formattedDate: `${date.getMonth() + 1}月${date.getDate()}日`,
                        weekday: ['日','一','二','三','四','五','六'][date.getDay()],
                        isToday,
                        duty: this.scheduleData[dateString]
                    });
                }
                return days;
            },
            weekRange() {
                if (this.weekSchedule.length === 0) return '';
                const start = this.weekSchedule[0].formattedDate;
                const end = this.weekSchedule[this.weekSchedule.length - 1].formattedDate;
                return `${start} - ${end}`;
            }
        },
        methods: {
            generateScheduleData() {
                const data = {};
                const today = new Date();
                const teamMembers = ['李明', '王丽', '赵强', '孙悦', '周涛', '吴芳', '郑伟'];
                const labAreas = [
                    '基因测序室',
                    '蛋白质分析室',
                    '细胞成像室',
                    '无菌操作间',
                    '标本储存库',
                    '数据分析室'
                ];
                const actionNotes = [
                    '仔细检查仪器设备的运行状态',
                    '认真清理实验台面的污渍和杂物',
                    '严格按照规范处理实验废弃物',
                    '定期对实验室的通风系统进行维护',
                    '及时补充实验所需的常用试剂和耗材'
                ];
                const recorders = ['李老师', '王管理员', '赵技术员'];

                // 生成四周数据
                for (let i = -14; i < 28; i++) {
                    const date = new Date(today);
                    date.setDate(date.getDate() + i);
                    const dateKey = date.toISOString().split('T')[0];

                    // 周末不安排值日
                    if (date.getDay() === 0 || date.getDay() === 6) continue;

                    // 随机生成值日数据
                    if (Math.random() > 0.2) { // 80%几率生成值日记录
                        // alert(labAreas[Math.floor(Math.random() * labAreas.length)])
                        data[dateKey] = {
                            person: teamMembers[Math.floor(Math.random() * teamMembers.length)],
                            status: date < today ? '已完成' : (date.getDate() === today.getDate() ? '进行中' : '待开始'),
                            area: labAreas[Math.floor(Math.random() * labAreas.length)],
                            // area:'333',
                            notes: this.generateNotes(date, actionNotes, recorders)
                        };
                    }
                }
                return data;
            },
            generateNotes(date, actionNotes, recorders) {
                const today = new Date();
                const diffDays = Math.floor((date - today) / (1000 * 60 * 60 * 24));

                if (diffDays < 0) { // 过去日期
                    return `已执行：${actionNotes[Math.floor(Math.random() * actionNotes.length)]}，记录人：${recorders[Math.floor(Math.random() * recorders.length)]}`;
                }
                if (diffDays === 0) { // 当天
                    return '今日需重点关注仪器设备的校准工作';
                }
                return `计划进行：${actionNotes[Math.floor(Math.random() * actionNotes.length)]}`;
            },
            prevWeek() {
                this.currentWeek--;
            },
            nextWeek() {
                this.currentWeek++;
            }
        }
    };
</script>

<style scoped>
    .duty-container {
        max-width: 1200px;
        margin: 0 auto;
        padding: 20px;
        margin-top: -80px;
    }

    .week-nav {
        display: flex;
        align-items: center;
        justify-content: center;
        gap: 3rem;
        margin-bottom: 3rem;
        padding: 1.5rem;
        background: #f0f7ff;
        border-radius: 20px;
        box-shadow: 0 6px 12px rgba(0, 0, 0, 0.08);
    }

    .nav-btn {
        padding: 1rem 1.5rem;
        border: none;
        border-radius: 12px;
        background: #007BFF;
        color: white;
        cursor: pointer;
        transition: all 0.3s ease;
        font-size: 1.2rem;
    }

    .nav-btn:hover {
        background: #0056b3;
        transform: scale(1.08);
    }

    .duty-grid {
        display: grid;
        grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
        gap: 2rem;
    }

    .duty-card {
        background: white;
        border-radius: 25px;
        padding: 2rem;
        box-shadow: 0 8px 16px rgba(0, 0, 0, 0.07);
        transition: all 0.4s ease;
        position: relative;
    }

    .duty-card:hover {
        transform: translateY(-10px);
        box-shadow: 0 12px 24px rgba(0, 0, 0, 0.15);
    }

    .duty-card.today::before {
        content: '今日';
        position: absolute;
        top: -15px;
        right: -15px;
        background: #ffc107;
        color: white;
        padding: 0.5rem 1rem;
        border-radius: 20px;
        font-weight: bold;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }

    .date-header {
        text-align: center;
        margin-bottom: 2rem;
        padding-bottom: 1.5rem;
        border-bottom: 3px solid #e9ecef;
    }

    .weekday {
        font-size: 1.5em;
        color: #777;
    }

    .date {
        font-size: 2.2em;
        font-weight: bold;
        color: #333;
    }

    .duty-info {
        min-height: 100px;
        margin: 2rem 0;
        text-align: center;
    }

    .duty-person {
        font-size: 1.6em;
        font-weight: 600;
        color: #212529;
        margin-bottom: 1rem;
    }

    .status-tag {
        display: inline-block;
        padding: 0.6rem 1.2rem;
        border-radius: 25px;
        font-size: 1em;
        font-weight: 500;
    }

    .status-tag.已完成 {
        background: #28a745;
        color: white;
    }

    .status-tag.进行中 {
        background: #ffc107;
        color: #212529;
    }

    .status-tag.待开始 {
        background: #f8f9fa;
        color: #6c757d;
    }

    .unassigned {
        color: #999;
        font-style: italic;
        font-size: 1.1em;
    }

    .additional-info {
        border-top: 2px solid #e9ecef;
        padding-top: 1.5rem;
    }

    .lab-area {
        color: #666;
        margin-bottom: 1rem;
        font-size: 1.1em;
    }

    .notes {
        color: #888;
        font-size: 1em;
    }

    .icon {
        margin-right: 0.8rem;
    }

    /* 动画效果 */
    .list-move {
        transition: all 0.8s ease;
    }

    .fade-enter-active, .fade-leave-active {
        transition: opacity 0.4s;
    }
    .fade-enter-from, .fade-leave-to {
        opacity: 0;
    }

    @media (max-width: 768px) {
        .duty-grid {
            grid-template-columns: 1fr;
        }

        .week-nav {
            flex-direction: column;
            gap: 1.5rem;
        }
    }
</style>
