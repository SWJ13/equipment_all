<template>
    <div class="lab-container">
        <headers></headers>
        <!-- 仪器搜索筛选 -->
        <div class="controls" style="margin-top: 30px">
            <input
                    v-model="searchQuery"
                    placeholder="搜索仪器..."
                    class="search-input"
            />
            <select v-model="selectedType" class="type-select">
                <option value="">所有类型</option>
                <option v-for="type in instrumentTypes" :key="type">{{ type }}</option>
            </select>
        </div>

        <!-- 仪器列表 -->
        <transition-group name="list" tag="div" class="instrument-grid">
            <div
                    v-for="instrument in filteredInstruments"
                    :key="instrument.id"
                    class="instrument-card { selected: selectedInstrument?.id === instrument.id }"
                    @click="handleInstrumentClick(instrument)"
            >
                <h3>{{ instrument.name }}</h3>
                <p class="type-badge" :style="typeStyle(instrument.type)">
                    {{ instrument.type }}
                </p>
                <p class="status" :class="instrument.status">
          <span v-if="instrument.status === 'available'">
            <i class="icon icon-check" /> 可用
          </span>
                    <span v-else> <i class="icon icon-x" /> 不可用 </span>
                </p>
                <p class="description">{{ instrument.description }}</p>
            </div>
        </transition-group>

        <!-- 预约弹窗 -->
        <transition name="modal">
            <div v-if="showBooking" class="modal-mask">
                <div class="modal-container">
                    <div class="modal-header">
                        <h2>预约 {{ selectedInstrument.name }}</h2>
                        <button @click="showBooking = false" class="close-btn">
                            &times;
                        </button>
                    </div>

                    <div class="modal-body">
                        <div class="time-selection" style="margin-left: -310px">
                            <label>选择日期：</label>
                            <input
                                    type="date"
                                    v-model="bookingDate"
                                    :min="new Date().toISOString().split('T')[0]"
                            />
                        </div>

                        <div class="time-slots">
                            <button
                                    v-for="time in availableTimes"
                                    :key="time"
                                    @click="selectedTime = time"
                                    :class="{ selected: selectedTime === time }"
                            >
                                {{ time }}
                            </button>
                        </div>

                        <div class="user-info">
                            <input v-model="userName" placeholder="姓名" required />
                            <input
                                    v-model="userEmail"
                                    type="email"
                                    placeholder="邮箱"
                                    required
                            />
                            <input v-model="userInstitution" placeholder="单位/机构" required />
                        </div>
                    </div>

                    <button
                            class="submit-btn"
                            @click="submitBooking"
                            :disabled="!isFormValid"
                    >
                        提交预约
                    </button>
                </div>
            </div>
        </transition>
        <bottoms style="margin-top: 50px"></bottoms>
    </div>
</template>

<script>
    import headers from "../components/header"
    import bottoms from "../components/bottom"
    import { Message } from 'element-ui';

    export default {
        components:{
            headers,
            bottoms
        },
        data() {
            return {
                searchQuery: "",
                selectedType: "",
                selectedInstrument: null,
                showBooking: false,
                bookingDate: "",
                selectedTime: "",
                userName: "",
                userEmail: "",
                userInstitution: "",
                instruments: [],
            };
        },
        computed: {
            instrumentTypes() {
                return [...new Set(this.instruments.map((i) => i.type))];
            },
            filteredInstruments() {
                return this.instruments.filter((instrument) => {
                    const matchesSearch = instrument.name
                        .toLowerCase()
                        .includes(this.searchQuery.toLowerCase());
                    const matchesType =
                        !this.selectedType || instrument.type === this.selectedType;
                    return matchesSearch && matchesType;
                });
            },
            availableTimes() {
                return ["09:00", "10:00", "11:00", "14:00", "15:00"];
            },
            isFormValid() {
                return (
                    this.bookingDate &&
                    this.selectedTime &&
                    this.userName &&
                    this.userEmail &&
                    this.userInstitution
                );
            },
        },
        methods: {
            typeStyle(type) {
                const colors = {
                    分析仪器: "#4a90e2",
                    实验设备: "#7ed321",
                    测量仪器: "#f5a623",
                };
                return { backgroundColor: colors[type] || "#999" };
            },
            handleInstrumentClick(instrument) {
                this.resetForm();
                if (instrument.status === "available") {
                    this.selectedInstrument = instrument;
                    this.showBooking = true;
                } else {
                    Message.error("预约已满！");
                }
            },
            submitBooking() {
                if (this.isFormValid) {
                    const bookingInfo = {
                        instrumentId: this.selectedInstrument.id,
                        userName: this.userName,
                        userEmail: this.userEmail,
                        userInstitution: this.userInstitution,
                        bookingDate: this.bookingDate,
                        bookingTime: this.selectedTime,
                    };
                    const _this = this
                    _this.axios
                        .post("/bookings/add", bookingInfo)
                        .then((response) => {
                            if (response.data === true) {
                                _this.$alert('预约成功！', '', {
                                    confirmButtonText: '确定',
                                    callback: action => {
                                        location.reload()
                                    }
                                });
                            } else {
                                Message.error("预约失败，请稍后重试。");
                            }
                        })
                        .catch((error) => {
                            Message.error("预约失败，请稍后重试。");
                        });
                }
            },
            resetForm() {
                this.bookingDate = "";
                this.selectedTime = "";
                this.userName = "";
                this.userEmail = "";
                this.userInstitution = "";
            },
        },
        mounted() {
            this.axios.get("/instruments/index").then((response) => {
                    this.instruments = response.data
                }).catch((error) => {
                    console.error(error);
                });
        },
    };
</script>

<style scoped>
    /* 基础样式 */
    :root {
        --primary: #2c3e50;
        --secondary: #42b983;
        --accent: #35495e;
    }

    .lab-container {
        max-width: 1200px;
        margin: 0 auto;
        padding: 20px;
        margin-top: -80px;
    }

    /* 搜索栏样式 */
    .controls {
        display: flex;
        gap: 20px;
        margin-bottom: 30px;
    }

    .search-input,
    .type-select {
        padding: 12px;
        border: 2px solid #eee;
        border-radius: 8px;
        width: 250px;
        transition: all 0.3s;
    }

    .search-input:focus,
    .type-select:focus {
        border-color: var(--secondary);
        box-shadow: 0 2px 8px rgba(66, 185, 131, 0.2);
    }

    /* 仪器卡片 */
    .instrument-grid {
        display: grid;
        grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
        gap: 20px;
    }

    .instrument-card {
        background: white;
        border-radius: 12px;
        padding: 20px;
        cursor: pointer;
        transition: all 0.3s;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    }

    .instrument-card:hover {
        transform: translateY(-5px);
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
    }

    .instrument-card.selected {
        border: 2px solid var(--secondary);
    }

    .type-badge {
        display: inline-block;
        padding: 4px 8px;
        border-radius: 4px;
        color: white;
        font-size: 0.9em;
    }

    .status {
        color: #666;
        font-weight: bold;
    }

    .status.available {
        color: #7ed321;
    }

    .status.unavailable {
        color: #ff0000;
    }

    .icon {
        margin-right: 5px;
    }

    .icon-check::before {
        content: "✔";
    }

    .icon-x::before {
        content: "✖";
    }

    /* 弹窗样式 */
    .modal-mask {
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: rgba(0, 0, 0, 0.5);
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .modal-container {
        background: white;
        border-radius: 12px;
        padding: 30px;
        width: 90%;
        max-width: 500px;
        position: relative;
    }

    .modal-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 20px;
    }

    .close-btn {
        font-size: 24px;
        background: none;
        border: none;
        cursor: pointer;
        transition: color 0.3s;
    }

    .close-btn:hover {
        color: var(--secondary);
    }

    .time-slots {
        display: flex;
        gap: 10px;
        flex-wrap: wrap;
        margin: 15px 0;
    }

    .time-slots button {
        padding: 8px 15px;
        border: 1px solid #ddd;
        border-radius: 6px;
        transition: all 0.3s;
    }

    /* 修改选择时间按钮选中状态样式 */
    .time-slots button.selected {
        background: #000;
        color: white;
        border-color: #000;
    }

    .user-info {
        display: grid;
        gap: 10px;
        margin-top: 20px;
    }

    .user-info input {
        padding: 10px;
        border: 1px solid #ddd;
        border-radius: 6px;
    }

    /* 修改提交预约按钮颜色为黑色 */
    .submit-btn {
        width: 100%;
        padding: 12px;
        background: #000;
        color: white;
        border: none;
        border-radius: 6px;
        margin-top: 20px;
        cursor: pointer;
        transition: opacity 0.3s;
    }

    .submit-btn:disabled {
        opacity: 0.6;
        cursor: not-allowed;
    }

    /* 过渡动画 */
    .list-enter-active,
    .list-leave-active {
        transition: all 0.5s ease;
    }
    .list-enter-from,
    .list-leave-to {
        opacity: 0;
        transform: translateY(30px);
    }

    .modal-enter-active,
    .modal-leave-active {
        transition: all 0.3s ease;
    }
    .modal-enter-from,
    .modal-leave-to {
        opacity: 0;
        transform: translateY(-20px);
    }

    @media (max-width: 768px) {
        .controls {
            flex-direction: column;
        }

        .search-input,
        .type-select {
            width: 100%;
        }
    }
</style>
