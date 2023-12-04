<template>
<main class="customer-center">
        <section class="customer-center section" id="customer-center">
            <div class="customer-center">
            <div>
                    <h2 class="section__title customer-center_titile">客服中心</h2>
                    <p class="about-us__description textAlign">需要什麼協助呢？我們一天24小時都在。
                    </p>
                    <div>
                        <div class="box" >
                            <div class="border" @click="toContactUs">
                                <!-- <a href="../html/contact_us.html" class="grid-container"> -->
                                    <div class="grid-container__icon">
                                        <!-- <i class="ri-question-answer-line"></i> -->
                                    </div>
                                    <img src="@/assets/img/chat.png" alt="" class="icon">
                                    <div class="grid__text">
                                        <h3 class="grid__title textAlign">發送訊息給我們</h3>
                                        <p class="grid__body">歡迎聯繫本站客服詢問訂單相關問題，我們會盡快回覆。</p>
                                    </div>
                                <!-- </a> -->
                            </div>

                            <div class="border" @click="toOops">
                                <!-- <a href="../html/oops.html" class="grid-container"> -->
                                    <div class="grid-container__icon">
                                    </div>
                                    <img src="@/assets/img/telephone.png" alt="" class="icon">
                                    <div class="grid__text">
                                        <h3 class="grid__title textAlign">致電客服</h3>
                                        <p class="grid__body">如有緊急狀況，您可撥打基本上 24 小時全年無休的當地或國際電話號碼。</p>
                                    </div>
                                <!-- </a> -->
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </section>
</main>
</template>
<script setup>
import { useRouter } from 'vue-router';

const router = useRouter();

const toContactUs = () => {
    router.push({ name: 'contactUs' });
}


const toOops = () => {
    router.push({ name: 'oops' });
}

</script>
<style scoped>
.customer-center {
    display: flex;
    justify-content: center;
    align-items: center;
}

.textAlign {

    text-align: center;
}

.box {
    display: flex;
}

.ri-question-answer-line ::before {
    width: 30px;
}

.border {
    border: 1px solid black;
    cursor: pointer;
    box-shadow: 1px 1px 10px #D0D0D0;
    margin: 0px 30px;
    width: 360px;
    padding: 15px;

    align-items: center;
    /* 將子元素垂直置中 */
    text-align: center;
    /* 將文字置中 */
}

.border:hover {
    box-shadow: 1px 1px 10px #6C6C6C;
}

.icon {
    width: 30px;
    height: 30px;
    margin-bottom: 15px;
}
</style>