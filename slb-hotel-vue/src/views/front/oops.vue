<template lang="">
<main class="oops">
        <section class="oops section" id="oops">
            <div class="oops__container grid">
                <img src="@/assets/img/oops.jpg" alt="">
                <div class="oops__data">
                    <h1 class="section__title oops_titile">請稍候</h1>
                    <p class="oops__description" style="text-align:center;">系統忙碌中，等一下下吧。</p>
                </div>
            </div>
        </section>
</main>
</template>
<script setup>

</script>
<style scoped>
.oops__container {
    display: flex;
    /* height: 300px; */
}

img {
    width: 50%;
    height: auto;
    position: relative;
}

.oops__data {
    position: relative;
    top: 0;
    left: -120px;
}
</style>