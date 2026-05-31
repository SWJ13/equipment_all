<template>
    <div class="introduction-page">
        <headers></headers>
        <!-- 头部 -->
        <header class="headerIntroduction">
            <h1>多设备动态科研自适应实验室管理系统</h1>
            <p>为科研工作提供高效、智能、个性化的实验室管理服务</p>
        </header>

        <!-- 主要内容 -->
        <main class="main-content">
            <!-- 平台简介 -->
            <section class="section intro-section">
                <h2>平台简介</h2>
                <p>
                    多设备动态科研自适应实验室管理系统是一个专门为科研实验室打造的综合性管理平台。
                    我们整合了各类科研设备的管理功能，通过先进的智能算法为科研人员提供个性化的设备使用规划和实验安排，助力科研工作提升效率和质量。
                </p>
            </section>

            <!-- 动态数据展示 -->
            <section class="section stats-section">
                <h2>我们的成果</h2>
                <div class="stats-container">
                    <div class="stat-item">
                        <span class="stat-number">{{ stats.registeredResearchers }}</span>
                        <span class="stat-label">注册科研人员</span>
                    </div>
                    <div class="stat-item">
                        <span class="stat-number">{{ stats.equipmentsAvailable }}</span>
                        <span class="stat-label">可用设备</span>
                    </div>
                    <div class="stat-item">
                        <span class="stat-number">{{ stats.yearsOfOperation }}</span>
                        <span class="stat-label">运营年限</span>
                    </div>
                </div>
            </section>

            <!-- 动态图片展示 -->
            <section class="section gallery-section">
                <h2>我们的故事</h2>
                <div class="gallery-container">
                    <div
                            v-for="(image, index) in galleryImages"
                            :key="index"
                            class="gallery-item"
                            :style="{ backgroundImage: `url(${image})` }"
                    ></div>
                </div>
            </section>
        </main>
        <bottoms></bottoms>
    </div>
</template>

<script>
    import headers from "../components/header"
    import bottoms from "../components/bottom"
    // 导入图片
    import RentSectionImage1 from "@/assets/image/sys1.png";
    import RentSectionImage2 from "@/assets/image/sys2.png";
    import RentSectionImage3 from "@/assets/image/sys3.png";
    import RentSectionImage4 from "@/assets/image/sys4.png";

    export default {
        components:{
            headers,
            bottoms
        },
        data() {
            return {
                // 动态数据
                stats: {
                    registeredResearchers: 0,
                    equipmentsAvailable: 0,
                    yearsOfOperation: 0,
                },
                // 图片库
                galleryImages: [
                    RentSectionImage1,
                    RentSectionImage2,
                    RentSectionImage3,
                    RentSectionImage4,
                ],
            };
        },
        mounted() {
            this.updateStats();
        },
        methods: {
            // 动态更新数据
            updateStats() {
                const targetStats = {
                    registeredResearchers: 500,
                    equipmentsAvailable: 300,
                    yearsOfOperation: 2,
                };

                const interval = setInterval(() => {
                    if (this.stats.registeredResearchers < targetStats.registeredResearchers) {
                        this.stats.registeredResearchers += 10;
                    }
                    if (this.stats.equipmentsAvailable < targetStats.equipmentsAvailable) {
                        this.stats.equipmentsAvailable += 5;
                    }
                    if (this.stats.yearsOfOperation < targetStats.yearsOfOperation) {
                        this.stats.yearsOfOperation += 1;
                    }
                    if (
                        this.stats.registeredResearchers >= targetStats.registeredResearchers &&
                        this.stats.equipmentsAvailable >= targetStats.equipmentsAvailable &&
                        this.stats.yearsOfOperation >= targetStats.yearsOfOperation
                    ) {
                        clearInterval(interval);
                    }
                }, 100);
            },
        },
    };
</script>

<style scoped>
    .introduction-page {
        font-family: Arial, sans-serif;
        color: #333;
        background-color: #f9f9f9;
        padding: 20px;
        max-width: 1200px;
        margin: 0 auto;
        margin-top: -80px;
    }

    .headerIntroduction {
        text-align: center;
        padding: 20px;
        background-color: #4caf50;
        color: white;
        border-radius: 10px;
        margin-bottom: 20px;
    }

    .main-content {
        max-width: 1200px;
        margin: 0 auto;
        padding: 20px;
    }

    .section {
        background-color: white;
        padding: 20px;
        border-radius: 10px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        margin-bottom: 20px;
        animation: slideUp 0.5s ease-in-out;
    }

    .section h2 {
        font-size: 2rem;
        margin-bottom: 15px;
        color: #4caf50;
    }

    .section p {
        font-size: 1.1rem;
        line-height: 1.6;
    }

    .stats-container {
        display: flex;
        justify-content: space-around;
        flex-wrap: wrap;
    }

    .stat-item {
        text-align: center;
        padding: 20px;
        background-color: #f1f1f1;
        border-radius: 10px;
        margin: 10px;
        flex: 1 1 200px;
        transition: transform 0.3s ease;
    }

    .stat-item:hover {
        transform: translateY(-10px);
    }

    .stat-number {
        font-size: 2rem;
        font-weight: bold;
        color: #4caf50;
    }

    .stat-label {
        font-size: 1.1rem;
        color: #666;
    }

    .gallery-container {
        display: flex;
        flex-wrap: wrap;
        gap: 10px;
    }

    .gallery-item {
        flex: 1 1 calc(25% - 10px);
        height: 200px;
        background-size: cover;
        background-position: center;
        border-radius: 10px;
        transition: transform 0.3s ease;
    }

    .gallery-item:hover {
        transform: scale(1.05);
    }

    @keyframes fadeIn {
        from {
            opacity: 0;
        }
        to {
            opacity: 1;
        }
    }

    @keyframes slideUp {
        from {
            transform: translateY(20px);
            opacity: 0;
        }
        to {
            transform: translateY(0);
            opacity: 1;
        }
    }

    .header123 {
        width: 100%;
        height: 80px;
        display: flex;
        align-items: center;
        justify-content: space-around;
        font-family: "黑体";
    }
    .header1 {
        display: flex;
    }
    .active {
        color: #00816f;
    }
    .menu-item {
        font-size: 14px;
        margin-left: 30px;
        cursor: pointer;
    }
    .header2 {
        display: flex;
    }
    .header3 {
        width: 130px;
        height: 40px;
        display: flex;
        justify-content: center;
        align-items: center;
        border: 1px solid #fea501;
        border-radius: 10px;
        margin-right: 20px;
        cursor: pointer;
    }
    .header4 {
        width: 130px;
        height: 40px;
        display: flex;
        justify-content: center;
        align-items: center;
        background-color: #fea501;
        border-radius: 10px;
        cursor: pointer;
    }
</style>
