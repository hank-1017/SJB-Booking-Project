<template>
  <header class="header" id="header">
    <nav class="nav container ">
      <router-link to="/">
        <a class="navbar-brand">
          <img class="nav__logo" src="@/assets/img/home_Logo.png" alt="" width="125" height="24">
        </a>
      </router-link>
      <!--      <a href="#" class="nav__logo">SJB.com</a>-->

      <div class="nav__menu" id="nav-menu">
        <ul class="nav__list">

          <li class="nav-item">
            <a href="/#filter" class="nav__link" :class="{ 'active-link': activePage === 'filter' }"  style="color: #14505c"
               @click="updateActivePage('filter')">立即訂房</a>
          </li>

          <li class="nav-item">
            <a href="/#about" class="nav__link" :class="{ 'active-link': activePage === 'hotel' }"
              @click="updateActivePage('hotel')">飯店資訊</a>
          </li>

          <li class="nav-item" v-if="!loggedIn">
            <router-link class="nav__link " to="/register" :class="{ 'active-link': activePage === 'register' }"
              @click="updateActivePage('register')">註冊
            </router-link>
          </li>

          <li class="nav-item" v-if="!loggedIn">
            <router-link id="loginLink" class="nav__link " to="/login"
              :class="{ 'active-link': activePage === 'login' }">登入
            </router-link>
          </li>
          <li class="nav-item dropdown" v-else>
            <a href="#" class="nav__link dropdown-toggle" data-bs-toggle="dropdown">
              <img class="rounded-circle me-lg-2" :src="headshotURL" alt="" style="width: 25px; height: 25px;">
              <span class="d-none d-lg-inline-flex">您好 {{ userInfo.customerName }}</span>
            </a>
            <div class="dropdown-menu dropdown-menu-end bg-light border-0 rounded-0 rounded-bottom m-0">
              <router-link to="/cart">
                <a href="" class="dropdown-item" @click="updateActivePage('cart')"><i class="fa fa-shopping-cart" />
                  購物車</a>
              </router-link>
              <router-link to="/message">
                <a href="" class="dropdown-item" @click="updateActivePage('message')">商家訊息</a>
              </router-link>
              <router-link to="/hotel/orders/findOrderByCustomer">
                <a href="" class="dropdown-item" @click="updateActivePage('myOrder')">我的訂單</a>
              </router-link>
              <router-link to="/userInfo">
                <a href="" class="dropdown-item" @click="updateActivePage('set-account')">帳號設定</a>
              </router-link>
              <a href="#" class="dropdown-item" @click=logout>登出</a>
            </div>
          </li>

          <!--          <li class="nav-item">-->
          <!--            <router-link class="nav__link " :to="/userInfo" v-if="userId"-->
          <!--                         :class="{ 'active-link': activePage === 'user' }"-->
          <!--                         @click="updateActivePage('user')">個人資訊-->
          <!--            </router-link>-->
          <!--          </li>-->

        </ul>


        <!--                      <div class="nav__dark">-->
        <!--                        &lt;!&ndash; Theme change button &ndash;&gt;-->
        <!--                        <span class="change-theme-name">Dark mode</span>-->
        <!--                        <i class="ri-moon-line change-theme" id="theme-button"></i>-->
        <!--                      </div>-->

        <i class="ri-close-line nav__close" id="nav-close"></i>
      </div>


      <div class="nav__toggle" id="nav-toggle">
        <i class="ri-function-line"></i>
      </div>
    </nav>

  </header>



  <!--==================== HOME ====================-->
  <section class="home" id="home">
    <img src="@/assets/img/index/taiwan-home2.jpg" alt="" class="home__img">

    <div class="home__container container grid">
      <div class="home__data">
        <span class="home__data-subtitle">想去哪裡呢？</span>
        <h1 class="home__data-title">展開一趟 <br> <b>深刻而難忘的 <br> 旅程</b></h1>
        <a href="#filter" class="button">開始探索</a>

      </div>

      <div class="home__social">
        <a href="https://www.facebook.com/" target="_blank" class="home__social-link">
          <i class="ri-facebook-box-fill"></i>
        </a>
        <a href="https://www.instagram.com/" target="_blank" class="home__social-link">
          <i class="ri-instagram-fill"></i>
        </a>
        <a href="https://twitter.com/" target="_blank" class="home__social-link">
          <i class="ri-twitter-fill"></i>
        </a>
      </div>

      <div class="home__info">
        <div>
          <span class="home__info-title">5間不能不吃的水煎包</span>
          <a href="#place" class="button button--flex button--link home__info-button">
            More <i class="ri-arrow-right-line"></i>
          </a>
        </div>

        <div class="home__info-overlay">
          <img src="@/assets/img/index/taiwan-home1.jpg" alt="" class="home__info-img">
        </div>

      </div>
    </div>
  </section>

  <!--==================== FILTER ====================-->
  <div class="container-fluid">
    <a class="navbar-brand"></a>

    <section class="form-container" id="filter">
      <form class="d-flex" @submit.prevent="searchHotels">
        <div class="inputBox">
          <span>目的地　 </span>
          <input v-model="city" class="form-control me-2" type="search" placeholder="輸入城市" aria-label="Search" id="city">
        </div>
        <div class="inputBox">
          <span>入住日期</span>
          <div class="input-container">
            <input v-model="startDate" class="form-control me-2" type="date" placeholder="Search" aria-label="Search"
              id="startDate">
          </div>
        </div>
        <div class="inputBox">
          <span>退房日期</span>
          <div class="input-container">
            <input v-model="endDate" class="form-control me-2" type="date" placeholder="Search" aria-label="Search"
              id="endDate">
          </div>
        </div>
        <div class="inputBox">
          <span>人數</span>
          <div class="input-container">
            <input v-model="requiredCapacity" class="form-control me-2" type="number" placeholder="1" aria-label="Search"
              id="requiredCapacity">
          </div>
        </div>

        <button class="btn btn-outline-success" type="submit">立刻訂房</button>

      </form>
    </section>
    <!-- <div class="filter-container">

   <label>
    <input v-model="filters.cleaningService" type="checkbox"> 每日打掃
   </label>
   <label>
    <input v-model="filters.expressCheckin" type="checkbox"> 快速入住
    </label>
   添加其他複選框，例如：
    <label>
    <input v-model="filters.counter24hr" type="checkbox"> 24小時櫃檯服務
    </label>
    <label>
    <input v-model="filters.freeWiFi" type="checkbox"> 免費WI-FI
   </label>
   <label>
    <input v-model="filters.roomCard" type="checkbox"> 電子房卡
   </label>
   <label>
    <input v-model="filters.noSmoking" type="checkbox"> 禁菸
   </label>
    <label>
    <input v-model="filters.petFriendly" type="checkbox"> 寵物友善
   </label>
    </div> -->
    <div v-if="noHotelsFound">
    </div>
    <div v-else-if="hotels.length > 0">
      <!-- 如果有酒店信息，顯示搜索結果 -->



      <ul class="horizontal-list">
        <li v-for="hotel in filteredHotels" :key="hotel.hotelID" class="hotel-card">
          <!-- 顯示酒店信息 -->
          <div class="card">
            <div class="card-body">
              <h5 class="card-title">{{ hotel.hotelName }}</h5>
              <p class="card-text">星級: {{ hotel.hotelStar }}</p>
              <div class="star-rating">
                <span v-for="i in parseInt(hotel.hotelStar)" :key="i">
                  <span class="fas fa-star" :style="{ color: getStarColor(hotel.hotelStar) }"></span>
                </span>
              </div>

              <!-- 其他酒店信息字段 -->
              <img v-if="hotel.rooms[0] && hotel.rooms[0].roomPictures[0]"
                :src="pictureURL + hotel.rooms[0].roomPictures[0].imgID" alt="Room Image"
                style="width: 300px; height: 150px; border-radius: 10px;">

              <p class="card-text">類型: {{ hotel.hotelType }}</p>

              <p class="card-text">電話: {{ hotel.tel }}</p>
              <p class="card-text">國家: {{ hotel.country }}</p>
              <p class="card-text">地址: {{ hotel.city }}{{ hotel.region }}{{ hotel.street }}</p>

              <p class="card-text">郵政編碼: {{ hotel.postalCode }}</p>
              <a href="#roomsDetail" class="btn btn-primary" @click="viewHotelDetails(hotel.hotelID)">查看客房供應狀況</a>

            </div>
          </div>
        </li>
      </ul>
    </div>

  </div>
  <!--==================== ABOUT ====================-->
  <section class="about section" id="about">
    <div class="about__container container grid">
      <div class="about__data">
        <h2 class="section__title about__title">更多訂房資訊 </h2>
        <p class="about__description">無論想去哪裡，我們都能提供價格實惠的飯店或民宿，讓你在旅途中更貼近在地生活的節奏，享受不同的體驗。
        </p>
        <a href="#" class="button">這次要住在哪裡呢？</a>
      </div>

      <div class="about__img">
        <div class="about__img-overlay">
          <img src="@/assets/img/index/about1.jpg" alt="" class="about__img-one">
        </div>

        <div class="about__img-overlay">
          <img src="@/assets/img/index/about2.jpg" alt="" class="about__img-two">
        </div>
      </div>
    </div>
  </section>

  <!--==================== DISCOVER ====================-->
  <section class="discover section" id="discover">
    <h2 class="section__title">探索台灣的優美風光</h2>

    <div class="discover__container container swiper-container">
      <div class="swiper-wrapper">
        <!--==================== DISCOVER 1 ====================-->
        <div class="discover__card swiper-slide">
          <img src="@/assets/img/index/discover1.jpg" alt="" class="discover__img">
          <div class="discover__data">
            <h2 class="discover__title">台南</h2>
            <span class="discover__description">24個推薦行程</span>
          </div>
        </div>

        <!--==================== DISCOVER 2 ====================-->
        <div class="discover__card swiper-slide">
          <img src="@/assets/img/index/discover2.jpg" alt="" class="discover__img">
          <div class="discover__data">
            <h2 class="discover__title">澎湖</h2>
            <span class="discover__description">7個推薦行程</span>
          </div>
        </div>

        <!--==================== DISCOVER 3 ====================-->
        <div class="discover__card swiper-slide">
          <img src="@/assets/img/index/discover3.jpg" alt="" class="discover__img">
          <div class="discover__data">
            <h2 class="discover__title">台東</h2>
            <span class="discover__description">18個推薦行程</span>
          </div>
        </div>

        <!--==================== DISCOVER 4 ====================-->
        <!-- <div class="discover__card swiper-slide">
          <img src="@/assets/img/index/discover4.jpeg" alt="" class="discover__img">
          <div class="discover__data">
            <h2 class="discover__title">台中</h2>
            <span class="discover__description">7個推薦行程</span>
          </div>
        </div> -->
      </div>
    </div>
  </section>

  <!--==================== PLACES ====================-->
  <section class="place section" id="place">
    <h2 class="section__title">吃吃水煎包</h2>

    <div class="place__container container grid">
      <!--==================== PLACES CARD 1 ====================-->
      <div class="place__card">
        <img src="@/assets/img/index/台南大王水煎包.jpg" alt="" class="place__img">

        <div class="place__content">
          <span class="place__rating">
            <i class="ri-star-line place__rating-icon"></i>
            <span class="place__rating-number">4,8</span>
          </span>

          <div class="place__data">
            <h3 class="place__title">大王水煎包</h3>
            <span class="place__subtitle">Tianan</span>
            <span class="place__price">$20</span>
          </div>
        </div>

        <button class="button button--flex place__button">
          <a href="https://eattnn.com/0930-678-535/" target="_blank">
            <i class="ri-arrow-right-line"></i>
          </a>
        </button>
      </div>

      <!--==================== PLACES CARD 2 ====================-->
      <div class="place__card">
        <img src="@/assets/img/index/大甲一品香水煎包.jpg" alt="" class="place__img">

        <div class="place__content">
          <span class="place__rating">
            <i class="ri-star-line place__rating-icon"></i>
            <span class="place__rating-number">5,0</span>
          </span>

          <div class="place__data">
            <h3 class="place__title">一品香水煎包</h3>
            <span class="place__subtitle">Taichung</span>
            <span class="place__price">$13</span>
          </div>
        </div>

        <button class="button button--flex place__button">
          <a href="https://lyes.tw/33212128-一品香水煎包/" target="_blank">
            <i class="ri-arrow-right-line"></i>
          </a>
        </button>
      </div>

      <!--==================== PLACES CARD 3 ====================-->
      <div class="place__card">
        <img src="@/assets/img/index/新北三重國小天橋下水煎包.jpg" alt="" class="place__img">

        <div class="place__content">
          <span class="place__rating">
            <i class="ri-star-line place__rating-icon"></i>
            <span class="place__rating-number">4,9</span>
          </span>

          <div class="place__data">
            <h3 class="place__title">三重國小水煎包</h3>
            <span class="place__subtitle">New Taipei</span>
            <span class="place__price">$13</span>
          </div>
        </div>

        <button class="button button--flex place__button">
          <a href="https://ireneslife.com/blog/post/fried-buns" target="_blank">
            <i class="ri-arrow-right-line"></i>
          </a>
        </button>
      </div>

      <!--==================== PLACES CARD 4 ====================-->
      <div class="place__card">
        <img src="@/assets/img/index/基隆遠東賴家水煎包.jpg" alt="" class="place__img">

        <div class="place__content">
          <span class="place__rating">
            <i class="ri-star-line place__rating-icon"></i>
            <span class="place__rating-number">4,8</span>
          </span>

          <div class="place__data">
            <h3 class="place__title">賴家水煎包</h3>
            <span class="place__subtitle">Keelung</span>
            <span class="place__price">$12</span>
          </div>
        </div>

        <button class="button button--flex place__button">
          <a href="https://nash.tw/laibun2022/" target="_blank">
            <i class="ri-arrow-right-line"></i>
          </a>
        </button>
      </div>

      <!--==================== PLACES CARD 5 ====================-->
      <div class="place__card">
        <img src="@/assets/img/index/苗栗通霄王煎包.jpg" alt="" class="place__img">

        <div class="place__content">
          <span class="place__rating">
            <i class="ri-star-line place__rating-icon"></i>
            <span class="place__rating-number">4,8</span>
          </span>

          <div class="place__data">
            <h3 class="place__title">通霄王煎包</h3>
            <span class="place__subtitle">Miaoli</span>
            <span class="place__price">$20</span>
          </div>
        </div>

        <button class="button button--flex place__button">
          <a href="https://almablog.com.tw/blog/post/194101" target="_blank">
            <i class="ri-arrow-right-line"></i>
          </a>
        </button>
      </div>
    </div>
  </section>

  <!--==================== SUBSCRIBE ====================-->
  <section class="subscribe section">
    <div class="subscribe__bg">
      <div class="subscribe__container container">
        <h2 class="section__title subscribe__title">訂閱最新資訊</h2>
        <p class="subscribe__description">訂閱電子報，獲取30%優惠券
        </p>

        <form @submit.prevent="subscribeMail" class="subscribe__form">
          <input type="text" placeholder="輸入信箱" class="subscribe__input">

          <button class="button">
            訂閱
          </button>
        </form>
      </div>
    </div>
  </section>

  <!--==================== SPONSORS ====================-->
  <section class="sponsor section">
    <div class="sponsor__container container grid">
      <div class="sponsor__content">
        <img src="@/assets/img/index/sponsors1.png" alt="" class="sponsor__img">
      </div>
      <div class="sponsor__content">
        <img src="@/assets/img/index/sponsors2.png" alt="" class="sponsor__img">
      </div>
      <div class="sponsor__content">
        <img src="@/assets/img/index/sponsors3.png" alt="" class="sponsor__img">
      </div>
      <div class="sponsor__content">
        <img src="@/assets/img/index/sponsors4.png" alt="" class="sponsor__img">
      </div>
      <div class="sponsor__content">
        <img src="@/assets/img/index/sponsors5.png" alt="" class="sponsor__img">
      </div>
    </div>
  </section>

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

<script setup>
import { ref, onMounted, computed } from 'vue';
import axios from 'axios';
import { decodeCredential } from "vue3-google-login";
import { useRouter } from "vue-router";
import '@/assets/js/main_front.js';
import Cookies from "js-cookie";
import Swal from 'sweetalert2';

const router = useRouter();
const loggedIn = ref(false);
const userInfo = ref({
  customerName: '',
  loginID: '',
});
const search = ref({
  city: "",
  requiredCapacity: 1,
  startDate: '',
  endDate: '',
  hotels: [],
  showHotelDetails: false,
  hotelDetails: {},
  noHotelsFound: false,
  filters: {
    cleaningService: false,
    expressCheckin: false,
    counter24hr: false,
    freeWiFi: false,
    roomCard: false,
    noSmoking: false,
    petFriendly: false,
  }
})
const URL = import.meta.env.VITE_API_JAVAURL;
const activePage = ref('home');
const email = ref('');
const password = ref('');
const errMsg = ref('');
const rememberMe = ref(false);
const headshotURL = ref(`${URL}user/headshot`);
const city = ref("");
const requiredCapacity = ref(1);
const startDate = ref("");
const endDate = ref("");
const hotels = ref([]);
const showHotelDetails = ref(false);
const hotelDetails = ref({});
const noHotelsFound = ref(false);
const pictureURL = `${URL}room/images/`;
const filters = ref({
  cleaningService: false,
  expressCheckin: false,
  counter24hr: false,
  freeWiFi: false,
  roomCard: false,
  noSmoking: false,
  petFriendly: false,
});

const link = document.createElement('link')
link.href = '/src/assets/css/styles.css'
link.rel = 'stylesheet'
document.head.appendChild(link)

onMounted(async () => {
  // 獲取 Cookie
  const savedEmail = Cookies.get('savedEmail');
  console.log('savedEmail: ' + savedEmail)
  if (savedEmail) {
    email.value = savedEmail;
    rememberMe.value = true;
  }
  await document.head.appendChild(link)
  await checkLoginStatus();
  await getUserData();
});

// Methods
const getStarColor = (hotelStar) => {
  if (hotelStar >= 4.5) {
    return '#FFD700'; // 金黄色
  } else if (hotelStar >= 3.5) {
    return 'red'; // 红色
  } else {
    return 'gray'; // 灰色
  }
};
const filteredHotels = computed(() => {
  console.log("Filters:", filters.value);
  const filtered = hotels.value.filter((hotel) => {
    if (filters.value.counter24hr && !hotel.hotelDetail.counter24hr) {
      return false;
    }
    if (filters.value.freeWiFi && !hotel.hotelDetail.freeWiFi) {
      return false;
    }

    if (filters.value.cleaningService && !hotel.hotelDetail.cleaningService) {
      return false;
    }
    if (filters.value.roomCard && !hotel.hotelDetail.roomCard) {
      return false;
    }
    if (filters.value.noSmoking && !hotel.hotelDetail.noSmoking) {
      return false;
    }
    if (filters.value.petFriendly && !hotel.hotelDetail.petFriendly) {
      return false;
    }
    if (filters.value.expressCheckin && !hotel.hotelDetail.expressCheckin) {
      return false;
    }
    // 添加其他筛选条件
    return true;
  });

  console.log("Filtered Hotels:", filtered);
  return filtered;
}); 


const searchHotels = () => {
  showHotelDetails.value = false;
  const loadingAlert = Swal.fire({
            title: '查詢中',
            html: '<i class="fas fa-spinner fa-spin"></i> 請稍候...', // 使用FontAwesome圖標
            showConfirmButton: false,
        });
  axios
    .get(`http://localhost:8080/sjb/search/hotelByDate`, {
      params: {
        city: city.value,
        requiredCapacity: requiredCapacity.value,
        startDate: startDate.value,
        endDate: endDate.value
      }
    })
    .then((response) => {
      if (response.data.length === 0) {
        noHotelsFound.value = true;
        loadingAlert.close();
        Swal.fire({
          icon: 'error',
          title: '查無此時段飯店',
          text: '抱歉，沒有可用的飯店房間。'
        });
      } else {
        hotels.value = response.data;
        noHotelsFound.value = false;
        console.log(response.data);
        loadingAlert.close();
      }
    })
    .catch((error) => {
      console.error("請求失敗，錯誤：", error);
      loadingAlert.close();
    });
};



const viewHotelDetails = (hotelID) => {
  router.push({
    name: 'hotel-details',
    params: {
      hotelID: hotelID,
      startDate: startDate.value,
      endDate: endDate.value
    },
  });
};

const checkLoginStatus = () => {
  // 调用检查登录状态的API
  axios.get(`${URL}checkLoginStatus`, { withCredentials: true })
    .then(response => {
      if (response.data === 'loggedIn') {
        loggedIn.value = true;
        // 如果需要userId,可以在这里获取
      } else {
        loggedIn.value = false;
      }
    });
};

const getUserData = async () => {
  try {
    if (loggedIn) {
      const res = await axios.get(`${URL}user/getLoginInfo`, { withCredentials: true });
      if (res.status === 200) {
        userInfo.value = res.data;
        console.log('User Login: ' + userInfo.value.customerName);
      } else {
        console.log('getUserData error');
      }
    }
    console.log('getUserData : loggedIn=false')
  } catch (error) {
    // console.error('AJAX error:', error);
    console.log('getUserData fail')
  }
}



const updateActivePage = (page) => {
  activePage.value = page;
};


const logout = async () => {
  const response = await axios.get(`${URL}user/logout`, { withCredentials: true });
  console.log('logout result: ' + response.data);
  await checkLoginStatus();
  await getUserData();
  sessionStorage.clear();
  // goToIndex();
};

const goToIndex = () => {
  window.location.href = '/';
};

const subscribeMail = async () => {
  const email = document.querySelector('.subscribe__input');
  const formData = new FormData();
  formData.append('email', email.value);
  const res = await axios.post(`${URL}subscribe`, formData, { withCredentials: true });
  if (res.data === 'Y') {
    alert('訂閱成功');
  } else {
    alert('訂閱失敗');
  }
};



</script>


<style>
.horizontal-list {
  display: flex;
  flex-wrap: nowrap;
  /* 不允许换行，使卡片在一行内水平排列 */
  overflow-x: auto;
  /* 添加水平滚动条以容纳更多卡片 */
}

.horizontal-list li {
  flex: 0 0 calc(25% - 10px);
  /* 25%是四個卡片的寬度，10px是間距，根據需要調整 */
  margin-right: 10px;
  /* 可以根據需要調整間距 */
  margin-bottom: 10px;
  /* 添加底部間距，使第五个卡片进入下一行 */
  list-style: none;
  /* 去掉列表標記 */
}

.hotel-card {
  flex: 1;
  /* 卡片均匀占据可用水平空间 */
  margin-right: 10px;
  /* 可以根据需要调整间距 */
}

.filter-container {
  border: 2px solid #ccc;
  /* 添加 2px 宽度、灰色边框 */
  border-radius: 10px;
  padding: 10px;
  /* 添加内边距以增加边框和复选框之间的距离 */
  margin: 20px 0;
  /* 上下各添加 20px 的外边距 */
}
</style>