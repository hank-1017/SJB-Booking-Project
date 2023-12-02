<script setup>

</script>

<template>
  <!--==================== FOOTER ====================-->
  <footer class="footer section">
    <div class="footer__container container grid">
      <div class="footer__content grid">
        <div class="footer__data">
          <h3 class="footer__title">Travel</h3>
          <p class="footer__description">想去哪裡呢？ <br> 讓我們提供你最難忘的 <br> 旅遊體驗.
          </p>
          <div>
            <a href="https://www.facebook.com/" target="_blank" class="footer__social">
              <i class="ri-facebook-box-fill"></i>
            </a>
            <a href="https://twitter.com/" target="_blank" class="footer__social">
              <i class="ri-twitter-fill"></i>
            </a>
            <a href="https://www.instagram.com/" target="_blank" class="footer__social">
              <i class="ri-instagram-fill"></i>
            </a>
            <a href="https://www.youtube.com/" target="_blank" class="footer__social">
              <i class="ri-youtube-fill"></i>
            </a>
          </div>
        </div>

        <div class="footer__data">
          <h3 class="footer__subtitle">About</h3>
          <ul style="padding-left: 0">
            <li class="footer__item">
              <router-link to="/aboutUs">
                <a href="" class="footer__link">關於我們</a>
              </router-link>
            </li>
            <li class="footer__item">
              <router-link to="/philosophy">
                <a href="" class="footer__link">企業理念</a>
              </router-link>
            </li>
          </ul>
        </div>

        <div class="footer__data">
          <h3 class="footer__subtitle">Company</h3>
          <ul style="padding-left: 0">
            <li class="footer__item">
              <router-link to="/aboutUs">
                <a href="" class="footer__link">我們的團隊</a>
              </router-link>
            </li>
            <li class="footer__item">
              <a href="/hotel/startRegister" class="footer__link">成為我們的一員</a>
            </li>
            <li class="footer__item">
              <a href="https://www.booking.com/index.zh-tw.html" class="footer__link">夥伴平台</a>
            </li>
          </ul>
        </div>

        <div class="footer__data">
          <h3 class="footer__subtitle">Support</h3>
          <ul style="padding-left: 0">
            <li class="footer__item">
              <router-link to="/FAQ">
                <a href="" class="footer__link">常見問題</a>
              </router-link>

            </li>
            <li class="footer__item">
              <router-link to="/customerCenter">
                <a href="" class="footer__link">客服中心</a>
              </router-link>
            </li>
            <li class="footer__item">
              <router-link to="/contactUs">
                <a href="" class="footer__link">聯絡我們</a>
              </router-link>
            </li>
          </ul>
        </div>
      </div>
      <!--==================== 進後台 ====================-->

      <div style="text-align: center">
        <a href="/hotel/login/">
          <button class="button">進入住宿後台</button>
        </a>
      </div>

      <div class="footer__rights">
        <p class="footer__copy">&#169; 2023 SJB.com All rigths reserved.</p>
        <div class="footer__terms">
          <!--          <a href="#" class="footer__terms-link">Terms & Agreements</a>-->
          <router-link to="/privacyPolicy">
            <a href="#" class="footer__terms-link">隱私權政策</a>
          </router-link>
        </div>
      </div>
    </div>
  </footer>

  <!--========== SCROLL UP ==========-->
  <a href="#" class="scrollup" id="scroll-up">
    <i class="ri-arrow-up-line scrollup__icon"></i>
  </a>
</template>

<style scoped></style>