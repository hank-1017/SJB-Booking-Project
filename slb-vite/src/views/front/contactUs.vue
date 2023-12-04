<template lang="">
 <main class="contact-us">
        <section class="contact-us section" id="contact-us">
            <div class="contact-us__container">

                <div class="contactInfo">
                    <div>
                        <h2 style="color:white;">Contact Info</h2>
                        <ul class="info">
                            <li style="display: flex;">
                                <span><img src="@/assets/img/map-pin-line.png" class="white-img" ></span>
                                <span>
                                    台南市永康區南台街一號<br>
                                    南臺科技大學<br>
                                    行政大樓 L 棟 5 樓</span>
                            </li>
                            <li>
                                <span><img src="@/assets/img/mail-line.png" class="white-img"></span>
                                <span>contact@sjb.com</span>
                            </li>
                            <li>
                                <span><img src="@/assets/img/phone-line.png" class="white-img"></span>
                                <span>02-6631-6593</span>
                            </li>
                        </ul>
                    </div>
                </div>

                

                    <div class="contactForm" >
                        <h2>給我們一些回饋吧！</h2>
                            <div class="formBox">

                                <div class="flex">
                                    <div class="inputBox w50">
                                        <span>姓名</span>
                                        <input type="text" required>
                                    </div>
                                    <div class="inputBox w50">
                                        <span>Email</span>
                                        <input type="email" required>
                                    </div>
                                </div>

                                <div class="flex">
                                    <div class="inputBox w50">
                                        <span>電話</span>
                                        <br>
                                        <input type="text" required style="margin-right:30px">
                                    </div>
                                    <div class="inputBox w50">
                                        <br>
                                        <select required>
                                            <option value="" disabled selected>請選擇訊息類別</option>
                                            <option value="訂單相關">訂單詳情</option>
                                            <option value="住宿資訊">住宿體驗</option>
                                            <option value="交通指南">特殊需求</option>
                                            <option value="其他">其他</option>
                                        </select>
                                    </div>
                                </div>

                                <div class="inputBox w100" style="margin-top:21.82px;">
                                    <textarea placeholder="在這裡輸入您的內容" required style="width:398px; height:100px"></textarea>
                                    <!-- <span>在這裡輸入您的內容</span> -->
                                </div>
                                <div class="inputBox w100">
                                    <input type="button" value="送出" class="button" @click="toThanks">
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

const toThanks = () => {
    router.push({
        name: 'thanks',
    });
}
</script>
<style scoped>
ul {
    padding-left: 0;
}

li {
    margin: 10px 0px;
}

.contact-us__container {
    display: flex;
    /* align-items: center; */
    /* justify-content: center; */
    /* padding-left: 30px; */

}

.contactInfo {
    background-color: #003E3E;
    margin: 40px 0px;
    padding: 30px;
    color: white;
    /* float: left; */
    position: absolute;
    z-index: 2;
    height: 379px;

}

.white-img {
    filter: brightness(0) invert(1);
    margin-right: 15px;
}

.contactForm {
    padding: 70px;
    background-color: #D1E9E9;
    position: relative;
    left: 141.5px;
    padding-left: 211.5px;
    z-index: 1;
    padding-right: 355px;
}

.flex {
    display: flex;
}

textarea {
    border: none;
    outline: none;
    resize: none;
}

select {
    border: none;
    outline: none;
    resize: none;
    width: 183px;
}

.button {
    padding: 5px 32px;
    margin-top: 15px;
}
</style>