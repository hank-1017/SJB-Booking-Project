<template>
  <!-- Sidebar Start -->
  <div class="sidebar pe-4 pb-3">
    <nav class="navbar bg-light navbar-light">
      <a href="/hotel/dashboard" class="navbar-brand mx-4 mb-3">
        <h3 class="text-primary">
          <!--          <img src="../assets/img/SJB.com.png" alt=""  style="width: 45px;">-->
          SJB.com
        </h3>
      </a>

      <div class="navbar-nav w-100">
        <!--      飯店切換dropdown start-->
        <div class="nav-item dropdown">

          <!--          <a href="#" class="nav-link " data-bs-toggle="dropdown">-->
          <div class="d-flex align-items-center ms-4 ">
            <img class="rounded-circle" src="@/assets/img/hoteltag.jpg" alt="" style="width: 50px; height: 50px;">
            <div class="ms-2">
              <h6 class="fw-normal mb-0"> {{ userInfo.hotelName }}</h6>
              <a @click="">
                <small v-if="nowHotelInfo.hotel.state" style="color: green">
                  <i class="fa fa-check-circle"/> 營業中
                </small>
                <small v-if="!nowHotelInfo.hotel.state" style="color: darkred">
                  <i class="fa fa-window-close"/>停業中
                </small>
              </a>
            </div>
          </div>
          <!--          </a>-->

          <!--          <div class="dropdown-menu bg-transparent border-0">-->
          <!--            <div v-for="{ hotelID, hotelName,  city, region } in hotels">-->
          <!--              <a class="nav-item nav-link" @click="linkToRooms(hotelID)">-->
          <!--                <div class="d-flex align-items-center">-->
          <!--                  <img class="rounded-circle" src="@/assets/img/hoteltag.jpg" alt="" style="width: 40px; height: 40px;">-->
          <!--                  <div class="ms-2">-->
          <!--                    <h6 class="fw-normal mb-0">{{ hotelName }}</h6>-->
          <!--                    <small>{{ city }}{{ region }}</small>-->
          <!--                  </div>-->
          <!--                </div>-->
          <!--              </a>-->
          <!--            </div>-->
          <!--            <router-link to="/hotel/dashboard/chooseHotel">-->
          <!--              <a href="" class="dropdown-item text-center">顯示所有住宿</a>-->
          <!--            </router-link>-->

          <!--          </div>-->

        </div>
        <!--      飯店切換dropdown END-->
      </div>

      <br>
      <br>
      <br>


      <div class="navbar-nav w-100">
        <router-link to="/hotel/dashboard">
          <a class="nav-item nav-link" :class="{ 'active': activePage === 'dashboard' }"
             @click="updateActivePage('dashboard')"><i class="fa fa-tachometer-alt me-2"></i>
            後台首頁 </a>
        </router-link>

        <router-link to="/hotel/hotelDetail">
          <a class="nav-item nav-link" :class="{ 'active': activePage === 'hotelDetail' }"
             @click="updateActivePage('hotelDetail')">
            <i class="fa fa-info me-2"></i>基本資料</a>
        </router-link>

        <router-link to="/hotel/hotelService">
          <a class="nav-item nav-link" :class="{ 'active': activePage === 'hotelService' }"
             @click="updateActivePage('hotelService')">
            <i class="fa fa-utensils me-2"></i>服務設施</a>
        </router-link>

        <router-link to="/hotel/room/roomManagement">
          <a class="nav-item nav-link" :class="{ 'active': activePage === 'roomManagement' }"
             @click="updateActivePage('roomManagement')">
            <i class="fa fa-bed me-2"></i>管理房間</a>
        </router-link>

        <router-link to="/hotel/orderManagement">
          <a class="nav-item nav-link" :class="{ 'active': activePage === 'orderManagement' }"
             @click="updateActivePage('orderManagement')">
            <i class="fa fa-receipt me-2"></i>訂單管理</a>
        </router-link>

        <router-link to="/hotel/addHotel">
          <a class="nav-item nav-link" :class="{ 'active': activePage === 'addHotel' }"
             @click="updateActivePage('addHotel')">
            <i class="fa fa-plus me-2"></i>新增住宿</a>
        </router-link>

        <router-link to="/hotel/setaccount">
          <a class="nav-item nav-link" :class="{ 'active': activePage === 'set-account' }"
             @click="updateActivePage('set-account')">
            <i class="fa fa-tools me-2"></i>帳號設定</a>
        </router-link>


      </div>
    </nav>
  </div>
  <!-- Sidebar End -->

  <!-- Navbar Start column-->
  <nav class="navbar navbar-expand bg-light navbar-light sticky-top px-4 py-0">
    <a href="#" class="navbar-brand d-flex d-lg-none me-4">
      <h2 class="text-primary mb-0">
        <i class="fa fa-hashtag"></i>
      </h2>
    </a>
    <a href="#" class="sidebar-toggler flex-shrink-0">
      <i class="fa fa-bars"></i>
    </a>
    <form class="d-none d-md-flex ms-4">
      <input class="form-control border-0" type="search" placeholder="Search">
    </form>

    <div class="navbar-nav align-items-center ms-auto">
      <div class="nav-item ">
        <router-link to="/hotel/message">
        <a href="#" class="nav-link ">
          <i     class="fa fa-envelope me-lg-2"></i>
          <span class="d-none d-lg-inline-flex">訊息</span>
        </a>
        </router-link>

      </div>
      <!--      飯店通知dropdown-->
      <div class="nav-item dropdown">
        <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown" style="position: relative;">
          <i class="fa fa-bell me-lg-2"></i>
          <span v-if="unreadCount !== 0" class="badge rounded-pill badge-notification bg-danger" style="position: absolute; top: 10%; right: 50%;">{{ unreadCount }}</span>
          <span class="d-none d-lg-inline-flex">通知</span>
        </a>

        <div class="dropdown-menu dropdown-menu-end bg-light border-0 rounded-0 rounded-bottom m-0">
          <div v-for="{ hotelNoticeID,content, status,noticeTime} in notify">
            <div class="" :class=" {'alert-success': status === '未讀'},{'alert-secondary': status === '已讀'}"
                 role="alert">
              <div class="d-flex justify-content-between">
                <a href="#" class="dropdown-item" @click="changeNotifyState(hotelNoticeID)">
                  <div><h6 class="fw-normal mb-0"><i class="fa fa-exclamation-circle me-2"></i>{{ content }}</h6>
                    <small>{{ noticeTime }}</small></div>
                  <h6 class="fw-normal mb-0">{{ status }}</h6>
                </a>
              </div>
            </div>
            <hr class="dropdown-divider">
          </div>

          <router-link to="/hotel/hotelNotify">
            <a href="" class="dropdown-item text-center">顯示全部通知</a>
          </router-link>
        </div>
      </div>
      <!--      飯店通知dropdown END-->

      <!--      飯店切換dropdown-->
      <div class="nav-item dropdown">
        <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown">
          <i class="fa fa-solid fa-hotel"></i>
          <span class="d-none d-lg-inline-flex">住宿切換</span>
        </a>
        <div class="dropdown-menu dropdown-menu-end bg-light border-0 rounded-0 rounded-bottom m-0">

          <div v-for="{ hotelID, hotelName, tel, country, city, region, street, postalCode, state } in hotels">
            <a href="#" class="dropdown-item" @click="linkTohotel(hotelID)">
              <div class="d-flex align-items-center">
                <img class="rounded-circle" src="@/assets/img/hoteltag.jpg" alt="" style="width: 40px; height: 40px;">
                <div class="ms-2">
                  <h6 class="fw-normal mb-0">{{ hotelName }}</h6>
                  <small>{{ city }}{{ region }}</small>
                </div>
              </div>
            </a>
          </div>

          <router-link to="/hotel/dashboard/chooseHotel">
            <a href="" class="dropdown-item text-center">顯示所有住宿</a>
          </router-link>

        </div>

      </div>
      <!--      飯店切換dropdown END-->
      <div class="nav-item dropdown">
        <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown"> <img
            class="rounded-circle me-lg-2"
            :src="headshotURL" alt=""
            style="width: 40px; height: 40px;"> <span class="d-none d-lg-inline-flex">您好 {{
            userInfo.loginName
          }}</span>
        </a>
        <div class="dropdown-menu dropdown-menu-end bg-light border-0 rounded-0 rounded-bottom m-0">
          <router-link to="/">
            <a href="/hotel/setaccount" class="dropdown-item" @click="updateActivePage('index')">回前台首頁</a>
          </router-link>
          <router-link to="/hotel/setaccount">
            <a href="/" class="dropdown-item" @click="updateActivePage('set-account')">帳號設定</a>
          </router-link>
          <a class="dropdown-item" @click=logout>登出</a>
        </div>
      </div>
    </div>
  </nav>
  <!-- Navbar End -->

</template>

<script setup>
import axios from "axios";
import {ref, onMounted, computed,onUnmounted} from 'vue';
import {useRouter} from 'vue-router';
import Swal from "sweetalert2";

const userInfo = ref({
  loginId: '',
  loginName: '',
  loginEmail: '',
  headshot: '',
  hotelID: '',
  hotelName: '',
});
const hotelInfo = ref({});
const nowHotelInfo = ref({
  hotel: {
    hotelID: '',
    hotelName: '',
    tel: '',
    country: '',
    city: '',
    region: '',
    street: '',
    postalCode: '',
    state: '',
  },
});
const loggedIn = ref(false);
const hotels = ref([]);
const notify = ref([]);
const activePage = ref();
const URL = import.meta.env.VITE_API_JAVAURL;
const headshotURL = ref(`${URL}hotel/getHeadshot`);


const router = useRouter();
const goToLogin = () => {
  router.push('/hotel/login');
};

const unreadCount = computed(() => {
  return notify.value.filter(item => item.status === '未讀').length;
});

const updateActivePage = (page) => {
  activePage.value = page;
};

const showLoginNoice = () => {
  console.log('showLoginNoice');
  Swal.fire({
    title: '連線逾時',
    text: "您已離線，請重新登入",
    icon: 'warning',
    confirmButtonColor: '#3085d6',
    confirmButtonText: '確認',
  }).then((result) => {
    if (result.isConfirmed) {
      window.location.href = '/hotel/login';
    } else {
      window.location.href = '/hotel/login';
    }
  })

}

const intoHotelDetail = async () => {
  try {
    const response = await axios.get(`${URL}hotel/findHotel`, {
      withCredentials: true,
    });

    if (response.status === 200) {
      console.log('goToHotelDetail: ' + response.data);
    } else {
      console.error('Failed to fetch hotel detail:', response.status);
    }
  } catch (error) {
    console.error('An error occurred:', error);
  }
};

const getLoginInfo = async () => {
  try {
    const response = await axios.post(`${URL}hotel/getLoginInfo`, null, {withCredentials: true});
    if (response.status === 200) {
      loggedIn.value = true;
      Object.assign(userInfo.value, response.data);//將response.data的值複製到userInfo.value
    } else {
      loggedIn.value = false;
      showLoginNoice();
    }
  } catch (error) {
    console.error('Error fetching login info:', error);
    loggedIn.value = false;
    showLoginNoice();
  }
}

const logout = async () => {
  const response = await axios.get(`${URL}hotel/logout`, {withCredentials: true});
  console.log('logout result: ' + response.data);
  sessionStorage.clear();
  goToLogin();
}

const getAllHotel = async () => {
  const hotelsAPI = `${URL}hotel/findAllhoteljson`;
  try {
    const responseH = await axios.get(hotelsAPI, {withCredentials: true});
    hotels.value = responseH.data;
    Object.assign(hotelInfo.value, responseH.data);
  } catch (error) {
    console.error("Failed to load hotels: ", error);
  }
}

const getAllNotify = async () => { //取得前4筆通知
  const notifyAPI = `${URL}hotel/getTop4Notify`;
  try {
    const responseH = await axios.get(notifyAPI, {withCredentials: true});
    console.log('getAllNotify: ' + responseH.data)
    notify.value = responseH.data;
    // Object.assign(hotelInfo.value, responseH.data);
  } catch (error) {
    console.error("Failed to load notifyAPI: ", error);
  }
}



const changeNotifyState = async (hotelNoticeID) => {
  const setHotelNoticeID = `${URL}hotel/changeNotifyState?hotelNoticeID=${hotelNoticeID}`;
  const res = await axios.get(setHotelNoticeID, {withCredentials: true});
  if (res.status === 200) {
    console.log('setHotelNoticeID: ' + res.data);
    await getAllNotify();
  } else {
    console.error('Failed to fetch setHotelNoticeID:', res.status);
    alert('系統錯誤，請稍後再試')
  }
}

const getNowHotelInfo = async () => {
  try {
    const response = await axios.get(`${URL}hotel/getHotelDetail`, {withCredentials: true});
    if (response.status === 200) {
      console.log('getHotelDetail: ' + response.data);
      Object.assign(nowHotelInfo.value, response.data);
      sessionStorage.setItem('HotelMessageID',nowHotelInfo.value.hotel.hotelID)
    } else {
      console.error('Failed to fetch hotel detail:', response.status);
    }
  } catch (error) {
    console.error('An error occurred:', error);
  }
}
const linkTohotel = async (hotelID) => {
  const setHotelID = `${URL}hotel/setHotelID?hotelID=${hotelID}`;
  const res = await axios.get(setHotelID, {withCredentials: true});
  if (res.status === 200) {
    console.log('setHotelID: ' + res.data);
    window.location.href = '/hotel/dashboard';
  } else {
    console.error('Failed to fetch hotel detail:', res.status);
    alert('系統錯誤，請稍後再試')
  }

}

onMounted(async () => {
  // 設定計時器每30秒呼叫一次getAllNotify
  setInterval(getAllNotify, 5000);
  await getLoginInfo();
  await getAllHotel();
  await intoHotelDetail();
  await getNowHotelInfo();
  await getAllNotify();
});

onUnmounted(() => {
  clearInterval(getAllNotify);
})

</script>


