<template>
    <div class="index">
        <headers />
        <el-card class="announcement-board">
            <template slot="header">
                <span class="header-title" style="position: relative;left: -460px">公告栏</span>
            </template>
            <el-timeline>
                <el-timeline-item
                        v-for="item in tableData"
                        :key="item.id"
                        placement="top"
                        class="timeline-item"
                >
                    <span style="position: relative;left: -480px;top:-10px;color: gray" >{{ item.date }}</span>
                    <h5 class="announcement-id">公告编号: {{ item.id }}</h5>
                    <el-card class="announcement-card">
                        <h4 class="announcement-title">{{ item.title }}</h4>
                        <p class="announcement-content">{{ item.content }}</p>
                    </el-card>
                </el-timeline-item>
            </el-timeline>
        </el-card>
        <bottoms></bottoms>
    </div>

</template>

<script>
    import headers from '/src/components/header.vue'
    import bottoms from '/src/components/bottom.vue'

    export default {
        components: {
            headers,
            bottoms
        },
        data() {
            return {
                tableData: []
            };
        },
        created() {
            const _this = this
            _this.axios.get('/notices/index').then((response) => {
                _this.tableData = response.data;
            });
        }
    };
</script>

<style scoped>
    .index {
        max-width: 1200px;
        margin: 0 auto;
        padding: 20px;
        margin-top: -80px;
    }

    .announcement-board {
        margin: 30px;
        background-color: #f5f7fa;
        border-radius: 10px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }

    .header-title {
        font-size: 1.5rem;
        font-weight: bold;
        color: #2c3e50;
        text-align: left;
    }

    .add-button {
        position: relative;
        left: 50px;
        background-color: #409eff;
        color: white;
        border: none;
    }

    .add-button:hover {
        background-color: #66b1ff;
    }

    .timeline-item {
        padding: 10px 0;
    }

    .announcement-card {
        background-color: #ffffff;
        border-radius: 10px;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        transition: transform 0.3s, box-shadow 0.3s;
        text-align: left;
    }

    .announcement-card:hover {
        transform: translateY(-5px);
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
    }

    .announcement-id {
        font-size: 1rem;
        color: #666;
        margin-bottom: 10px;
        text-align: left;
    }

    .announcement-title {
        font-size: 1.25rem;
        color: #2c3e50;
        margin-bottom: 10px;
        text-align: left;
    }

    .announcement-content {
        font-size: 1rem;
        color: #7f8c8d;
        margin-bottom: 10px;
        text-align: left;
        height: 60px;
    }

    .action-buttons {
        display: flex;
        gap: 10px;
    }

    .custom-dialog {
        border-radius: 10px;
        text-align: left;
    }

    .custom-dialog .el-form-item__label {
        font-weight: bold;
        color: #2c3e50;
        text-align: left;
    }

    .custom-dialog .el-input,
    .custom-dialog .el-textarea {
        border-radius: 5px;
    }

    /* 新增的样式，使 timestamp 靠左 */
    .el-timeline__item__timestamp {
        text-align: left;
    }
</style>
