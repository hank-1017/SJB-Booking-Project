<template>
  <div class="d-flex align-items-center justify-content-between mb-3">
    <a href="#">
      <h3 class="text-primary">SJB.com</h3>
    </a>
    <h3>後台登入</h3>
  </div>
  <form id="loginForm" @submit.prevent="submitForm">
    <div class="form-floating mb-3">
      <input type="email" name="email" class="form-control" id="floatingInput"
             placeholder="name@example.com"
             required v-model="email">
      <label for="floatingInput">帳號</label>
    </div>
    <div class="form-floating mb-4">
      <input type="password" name="password" class="form-control" id="floatingPassword"
             placeholder="Password"
             required v-model="password">
      <label for="floatingPassword">密碼</label>
    </div>
    <div class="d-flex align-items-center justify-content-between mb-4">
      <div class="form-check">
        <input type="checkbox" class="form-check-input" id="rememberMe" name="rememberMe"
               v-model="rememberMe">
        <label class="form-check-label" for="rememberMe">記住帳號</label>
      </div>
      <router-link to="/hotel/forgetPwd">
        <a>忘記密碼</a>
      </router-link>

    </div>
    <input type="hidden" name="userType" value="hotel">
    <button class="btn btn-primary py-3 w-100 mb-4" type="submit">登入</button>
  </form>
  <!-- 第三方登入 Google -->
  <!--  <div style="text-align: center;">-->
  <!--    <GoogleLogin :callback="callback" prompt auto-login/>-->
  <!--  </div>-->
  <!-- 第三方登入 Google END -->

  <br>
  <!-- Register buttons -->
  <div class="text-center">
    <p>使用社群帳號登入: </p>
    <!--    <button type="button" class="btn btn-secondary btn-floating mx-1">-->
    <!--      <i class="fab fa-facebook-f"></i>-->
    <!--    </button>-->

    <GoogleLogin :callback="callback" prompt auto-login/>
    <!--      <button class="btn btn-light  btn-lg btn-lg-square mx-1"><i class="fab fa-google"></i></button>-->


    <!--    <button type="button" class="btn btn-light  btn-lg btn-lg-square mx-1">-->
    <!--      <i class="fab fa-line"></i>-->
    <!--    </button>-->
  </div>

  <!-- 登入錯誤提示 -->
  <p style="color: red; text-align: center;" id="errMsg">{{ errMsg }}</p>
  <p class="text-center mb-0">沒有帳號嗎?
    <router-link to="/hotel/startRegister">
      <a>註冊新帳號</a>
    </router-link>
  </p>

  <br>

  <p class="text-center mb-0">
    <router-link to="/">
      <a style="text-decoration: underline">回首頁</a>
    </router-link>
  </p>

  <div style="text-align: right; color: rgba(13,172,240,0.3)">
    <a @click="demoAccount()">DEMO </a>
  </div>

</template>

<script setup>

import {ref, onMounted, watch} from 'vue';
import axios from 'axios';
import {useRouter} from 'vue-router';
import {decodeCredential} from "vue3-google-login"
import Swal from "sweetalert2";

const router = useRouter();
const errMsg = ref('');
const email = ref('');
const password = ref('');
const rememberMe = ref(false);
const URL = import.meta.env.VITE_API_JAVAURL;
const demoAccount = () => {
  email.value = 'sjb.com.service@gmail.com';
  password.value = 'sjbhotelbooking';
}

watch(rememberMe, (newValue) => {
  if (newValue) {
    localStorage.setItem('rememberMe', 'true');
  } else {
    localStorage.removeItem('rememberMe');
  }
})

const callback = (response) => {
  const userData = decodeCredential(response.credential)
  console.log("Handle the userData", userData)
  googleLogin(userData)
}

const checkLoginStatus = async () => {
  const response = await axios.get(`${URL}hotel/checkLoginStatus`, {withCredentials: true});
  if (response.data === 'Y') {
    await router.push('/hotel/chooseHotel');
  }
}

const googleLogin = async (googleInfo) => {
  console.log(googleInfo.sub)
  const response = await axios.post(`${URL}hotel/dashboard`, googleInfo, {withCredentials: true});

  if (response.data === 'Y') {
    await router.push('/hotel/chooseHotel');
    errMsg.value = '';
  }
  if (response.data === 'E') {//信箱已被註冊
    const res = confirm(`信箱(${googleInfo.email})已有帳號，是否進行綁定`);
    if (res) {
      const response = await axios.post(`${URL}hotel/bindGoogleID`, googleInfo, {withCredentials: true});

      if (response.data === 'E') {
        await router.push('/hotel/chooseHotel');
      }
      if (response.data === 'N') {
      }
    }

  }
  if (response.data === 'N') {
    // errMsg.value = `此帳號(${googleInfo.name})尚未綁定SJB.com \n請先註冊後登入綁定`;
    const response = await axios.post(`${URL}hotel/RegisterByGoogle`, googleInfo, {withCredentials: true});
    if (response.data === 'Y') {
      await router.push('/hotel/register');
    } else {
      alert('註冊失敗，請聯絡客服');
    }

  }
}

onMounted(async () => {
  await checkLoginStatus();
})

const submitForm = async () => {
  try {
    const formData = new FormData();
    formData.append('email', email.value);
    formData.append('password', password.value);

    if (rememberMe.value) {
      localStorage.setItem('savedEmail', email.value);
    } else {
      localStorage.removeItem('savedEmail');
    }

    // 使用 axios POST
    const response = await axios.post(`${URL}hotel/login`, formData, {withCredentials: true});

    console.log("Login result: " + response.data);
    if (response.data === "Y") {
      errMsg.value = '';
      window.location.href = `/hotel/chooseHotel`;
    }
    if (response.data === "N") {
      errMsg.value = '用戶帳號或密碼錯誤';
    }
  } catch (error) {
    console.error('AJAX error:', error);
  }
};


</script>

<style scoped>
</style>

