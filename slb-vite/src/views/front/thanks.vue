<template>
    <main class="thanks">
        <section class="thanks section" id="thanks">
            <div class="thanks__container grid">
                <img src="@/assets/img/thanks.jpg" alt="">
                <div class="thanks__data">
                    <h1 class="section__title thanks_titile">謝謝您的回饋！</h1>
                    <p class="thanks__description" style="text-align:center;">我們收到您的訊息了，您會在24小時內會收到我們的回覆。</p>
                </div>
            </div>
        </section>
    </main>
</template>
<script setup>

</script>
<style scoped>
.thanks__container {
    display: flex;
    /* height: 300px; */
}

img {
    width: 50%;
    height: auto;
    position: relative;
}

.thanks__data {
    position: relative;
    top: 0;
    left: -220px;
}
</style>