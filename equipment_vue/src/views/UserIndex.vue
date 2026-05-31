<template>
    <div class="index">
        <headers></headers>
        <div class="index1">
            <div class="index2">
                <div class="index3">
                    智联全域设备，动态精准调控，自适应优化管理，驱动科研高效创新！
                </div>
                <div class="index4">
                    Intelligent connected global devices, dynamic and precise control, driving efficient innovation in scientific research!
                </div>

                <h2 class="index5">热门话题推荐</h2>
                <div class="index5">
                    <div class="index6" @click="toReservation">
                        <i class="el-icon-success"></i>仪器预约
                    </div>
                    <div class="index6" style="margin-left: 20px" @click="toSubscribe">
                        <i class="el-icon-s-shop"></i>耗材申购
                    </div>
                </div>
            </div>
        </div>

        <!-- 轮播图 -->
        <el-carousel interval="3000" arrow="always" height="400px">
            <el-carousel-item v-for="(item, index) in carouselImages" :key="index">
                <img 
                    :src="item.url" 
                    :alt="item.alt || '轮播图'"
                    @error="handleImageError(index)"
                    style="width: 100%; height: 100%; object-fit: cover; display: block;"
                />
            </el-carousel-item>
        </el-carousel>

        <div class="index7">
            <div class="index8">
                <div class="index9">
                    <div class="index10">
                        <div class="section-title">公告栏</div>
                        <el-button size="small" type="primary" plain @click="toNotice()">查看更多</el-button>
                    </div>
                    <div class="index11" v-for="item in notices">
                        <div class="index12 notice-item">
                            <span>{{item.title}}</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
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
                carouselImages: [
                    {
                        url: 'https://gips1.baidu.com/it/u=3505952926,2495245322&fm=3003&app=3003&f=JPEG?w=4364&h=2970',
                        alt: '轮播图1',
                        fallback: 'https://via.placeholder.com/1200x400/2c3e50/ffffff?text=实验室设备管理'
                    },
                    {
                        url: 'https://images.unsplash.com/photo-1532619675605-1ede6c2ed2b0?w=1200&h=400&fit=crop',
                        alt: '轮播图2',
                        fallback: 'https://via.placeholder.com/1200x400/34495e/ffffff?text=科研创新平台'
                    },
                    {
                        url: 'https://biotraining.ustc.edu.cn/_upload/article/images/13/51/16d6a75348b49d16d22f4dd52f53/fe49a868-3b30-4132-a311-13dc4173d307.png',
                        alt: '轮播图3',
                        fallback: 'https://via.placeholder.com/1200x400/3498db/ffffff?text=智能设备管理'
                    }
                ],
                notices: [],
                competitions: []
            };
        },
        methods:{
            toReservation(){
                this.$router.push("/userReservation");
            },
            toSubscribe(){
                this.$router.push("/userSubscribe");
            },
            toNotice(){
                this.$router.push("/userNotices");
            },
            handleImageError(index) {
                // 当图片加载失败时，使用备用图片
                if (this.carouselImages[index] && this.carouselImages[index].fallback) {
                    this.carouselImages[index].url = this.carouselImages[index].fallback;
                } else {
                    // 如果没有备用图片，使用默认占位图
                    this.carouselImages[index].url = 'https://via.placeholder.com/1200x400/95a5a6/ffffff?text=图片加载失败';
                }
                console.warn(`轮播图第${index + 1}张加载失败，已切换到备用图片`);
            }
        },
        created() {
            const _this = this
            _this.axios.get('/notice/index').then((response) => {
                _this.notices = response.data
            });
            _this.axios.get('/competition/index').then((response) => {
                _this.competitions = response.data
            });
            _this.axios.get('/notices/list?page=1&size=3').then((response) => {
                _this.notices = response.data.data
            });
        }
    };
</script>

<style scoped>
    /* 全局样式 */
    body {
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        margin: 0;
        padding: 0;
        background-color: #f4f4f9;
    }

    .index {
        max-width: 1200px;
        margin: 0 auto;
        margin-top: -55px;
    }

    /* 顶部宣传语样式 */
    .index1 {
        background: linear-gradient(135deg, #2c3e50, #1a252f);
        color: white;
        text-align: center;
        padding: 100px 0;
        border-radius: 10px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        margin-bottom: 20px;
    }

    .index3 {
        font-size: 32px;
        font-weight: bold;
        margin-bottom: 20px;
    }

    .index4 {
        font-size: 18px;
        margin-bottom: 40px;
    }

    /* 热门话题推荐样式 */
    .index5 {
        font-size: 24px;
        font-weight: bold;
        margin-bottom: 20px;
    }

    .index6 {
        display: inline-block;
        background-color: white;
        padding: 15px 30px;
        border-radius: 5px;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        margin: 0 10px;
        cursor: pointer;
        transition: all 0.3s ease;
        position: relative;
        color: #2c3e50;
    }

    .index6:hover {
        transform: translateY(-3px);
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
    }

    .hot-container {
        position: relative;
    }

    .hot-label {
        position: absolute;
        top: -8px;
        right: -8px;
        background-color: red;
        color: white;
        font-size: 12px;
        padding: 2px 6px;
        border-radius: 4px;
        z-index: 1;
    }

    /* 轮播图样式 */
    .el-carousel {
        margin-bottom: 20px;
        border-radius: 10px;
        overflow: hidden;
    }

    /* 公告栏和竞赛报名模块公共样式 */
    .index7 {
        background-color: #ffffff;
        border-radius: 15px;
        box-shadow: 0 6px 12px rgba(0, 0, 0, 0.08);
        padding: 25px;
        margin-bottom: 25px;
        transition: all 0.3s ease;
    }

    .index7:hover {
        box-shadow: 0 8px 16px rgba(0, 0, 0, 0.12);
        transform: translateY(-3px);
    }

    .section-title {
        font-size: 22px;
        font-weight: 600;
        color: #333;
    }

    .index10 {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 20px;
    }

    .index11 {
        display: flex;
        flex-direction: column;
    }

    .notice-item,
    .competition-item {
        padding: 12px 0;
        border-bottom: 1px solid #e0e0e0;
        font-size: 16px;
        color: #555;
        transition: all 0.3s ease;
    }

    .notice-item:hover,
    .competition-item:hover {
        background-color: #f9f9f9;
        color: #2c3e50;
        cursor: pointer;
    }
</style>
