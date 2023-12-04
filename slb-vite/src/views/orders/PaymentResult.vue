<template>
  <div class="about-us">
    <section class="about-us section" id="about-us">
      <div class="about-us__container grid">
        <div style="">
          <h2 class=" about-us_titile">付款結果</h2>
          <p>訂單編號: {{ payResult.orderID }}</p>
          <p>付款時間: {{ payResult.paymentTime }}</p>
          <p>付款金額: {{ payResult.TradeAmt }}元</p>
          <p>訂單結果: {{ payResult.orderStatus }}</p>
        </div>
      </div>
    </section>
    <!--========== SCROLL UP ==========-->
    <a href="#" class="scrollup" id="scroll-up">
      <i class="ri-arrow-up-line scrollup__icon"></i>
    </a>
  </div>
</template>

<script setup>
import {ref, onMounted} from 'vue';
import {useRouter} from 'vue-router';
import axios from 'axios';

const URL = import.meta.env.VITE_API_JAVAURL;
const payResult = ref({
  orderID: '',
  orderStatus: '',
  paymentTime: '',
  TradeAmt: '',
})

onMounted(async () => {
  try {
    const res = await axios.get(`${URL}orders/paymentResult`, {withCredentials: true})
    console.log('call paymentResult')
    if (res.status === 200) {
      payResult.value = res.data;
      console.log(res.data)
    } else {
      alert("連線逾時，請重新操作")
    }
  } catch (error) {
    alert("連線逾時，請重新操作")
    // console.error('AJAX error:', error);
  }
})
</script>


<style scoped>

</style>