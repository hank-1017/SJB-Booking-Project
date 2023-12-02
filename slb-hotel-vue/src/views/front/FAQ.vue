<template>
  <main class="faqs">
    <!--==================== FAQS ====================-->
    <section class="faqs section" id="faqs_section">
      <h2 class="section__title faqs_titile">常見問題</h2>
      <div v-for="(faq, index) in faqs" :key="index" class="faq">
        <div @click="toggleAnswer(index)">

          <div class="question">
            <h5 style="color: 	#003E3E;">{{ faq.question }}</h5>
            <!-- <svg width="20" height="15" viewbox="0 0 42 25">
              <path d="M3 3L21 21L39 3" stroke="white" stroke-width="7" stroke-linecap="round" />
            </svg> -->
            <img v-if="!faq.showAnswer" class="FAQpointer" src="@/assets/img/FAQ_left.png" alt="">
            <img v-else class="FAQpointer" src="@/assets/img/FAQ_down.png" alt="">
          </div>
          <transition name="slide">
            <div v-if="faq.showAnswer" class="answer">
              <p v-html="faq.answer" class="FAQans"></p>
            </div>
          </transition>
          <hr>
        </div>
      </div>
    </section>
  </main>
</template>


  
<script setup>
import { ref } from 'vue';

// Variables
const faqs = ref([
  {
    question: '如何加入會員？',
    answer: '請點選網站右上角的「Login/Signup」文字，進入畫面後點選「註冊」按鍵，即可成為會員。',
    showAnswer: false,
  },
  {
    question: '如何在訂房網站上找到最優惠的住宿價格？',
    answer:
      '・訂閱郵件通知：訂閱訂房網站的郵件通知，以獲得促銷碼和特價優惠的信息。<br>・使用網站的獨家優惠：不定期提供僅限網站或應用程式使用的特別優惠碼。<br>・彈性日期：如果您的旅行日期具有彈性，可以嘗試在淡季或平日入住，通常價格較低。',
    showAnswer: false,
  },
  {
    question: '是否可以在訂房網站上找到特殊需求住宿，例如無障礙設施或寵物友好的住宿？',
    answer: '可以在搜索選項中設定特殊需求，以篩選出適合您需求的住宿選項。',
    showAnswer: false,
  },
  {
    question: '如何確保我訂到的住宿是符合描述的？',
    answer:
      '確保預訂的住宿符合描述的方法包括：<br>・確認評論和評分：查看其他旅客的評論和評分，以了解他們的實際體驗。<br>・查看照片：仔細查看住宿的照片，以確保它們與描述相符。<br>・與房東或飯店聯繫：如果您有任何疑慮或問題，可以與房東或飯店聯繫，以獲得進一步的信息。',
    showAnswer: false,
  },
  {
    question: '訂房網站上的價格是否包括所有費用？',
    answer:
      '訂房網站上顯示的價格通常包括住宿費用，但不一定包括所有其他費用，如稅金、服務費、設施使用費等。在預訂前，應仔細檢查價格明細，以確保您了解所有額外費用，以避免不必要的驚喜。',
    showAnswer: false,
  },
]);

// Functions
const toggleAnswer = (index) => {
  faqs.value[index].showAnswer = !faqs.value[index].showAnswer;
};
</script>
  
<style>
/* 添加由上往下的滑動效果 */
.slide-enter-active,
.slide-leave-active {
  transition: height 0.s ease;
}

.slide-enter,
.slide-leave-to {
  max-height: 0;
  overflow: hidden;
}

.answer {
  white-space: pre-wrap;
}

.question {
  display: flex;
}

.FAQpointer {
  height: 15px;
  width: auto;
  margin-left: auto;
  margin-top: 7px;
}

.faq {

  cursor: pointer;
}
</style>
  