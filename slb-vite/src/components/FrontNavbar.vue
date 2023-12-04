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
            <a href="/#about" class="nav__link" :class="{ 'active-link': activePage === 'hotel' }"  style="color: #14505c"
               @click="updateActivePage('hotel')">飯店資訊</a>
          </li>


          <li class="nav-item" v-if="!loggedIn">
            <router-link class="nav__link " to="/register" style="color: #14505c"
                         :class="{ 'active-link': activePage === 'register' }"
                         @click="updateActivePage('register')">註冊
            </router-link>
          </li>
          <li class="nav-item" v-if="!loggedIn">
            <!-- <router-link  id="loginLink" class="nav__link " to="/login" style="color: #14505c"
                          :class="{ 'active-link': activePage === 'login' }"
                          data-bs-toggle="modal" data-bs-target="#exampleModal">登入 -->
                          
            <!-- </router-link> -->
            <router-link id="loginLink" style="color: #14505c" class="nav__link " to="/login"
              :class="{ 'active-link': activePage === 'login' }">登入
            </router-link>
          </li>
          <li class="nav-item dropdown" v-else>
            <a href="#" class="nav__link dropdown-toggle" data-bs-toggle="dropdown">
              <img class="rounded-circle me-lg-2" :src="headshotURL" alt="" style="width: 25px; height: 25px;">
              <span class="d-none d-lg-inline-flex" style="color: #14505c">您好 {{userInfo.customerName}}</span>
            </a>
            <div class="dropdown-menu dropdown-menu-end bg-light border-0 rounded-0 rounded-bottom m-0">
              <router-link to="/cart">
                <a href="" class="dropdown-item" @click="updateActivePage('cart')"><i class="fa fa-shopping-cart"/> 購物車</a>
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
<!--            <router-link class="nav__link " :to="'/customer/' + userId" v-if="userId"-->
<!--                         :class="{ 'active-link': activePage === 'user' }"-->
<!--                         @click="updateActivePage('user')">個人資訊-->
<!--            </router-link>-->
<!--          </li>-->

        </ul>

<!--              <div class="nav__dark">-->
<!--                &lt;!&ndash; Theme change button &ndash;&gt;-->
<!--                <span class="change-theme-name">Dark mode</span>-->
<!--                <i class="ri-moon-line change-theme" id="theme-button"></i>-->
<!--              </div>-->

              <i class="ri-close-line nav__close" id="nav-close"></i>
      </div>


      <div class="nav__toggle" id="nav-toggle">
        <i class="ri-function-line"></i>
      </div>
    </nav>

  </header>

  <div v-if="!loggedIn" class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">會員登入</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <form>
            <div class="mb-3">
              <label for="recipient-name" class="col-form-label">帳號:</label>
              <input type="text" class="form-control" v-model="email" id="recipient-name">
            </div>
            <div class="mb-3">
              <label for="recipient-name" class="col-form-label">密碼:</label>
              <input type="text" class="form-control" v-model="password" id="recipient-name">
            </div>
            <div class="d-flex align-items-center justify-content-between mb-4">
              <div class="form-check">
                <input type="checkbox" class="form-check-input" id="rememberMe" name="rememberMe"
                       v-model="rememberMe">
                <label class="form-check-label" for="rememberMe">記住帳號</label>
              </div>
              <a href="/register" class="">忘記密碼</a>
            </div>



            <div class="text-center">
              <p>使用社群帳號登入: </p>
              <GoogleLogin :callback="callback" />
            </div>

          </form>
          <!-- 登入錯誤提示 -->
          <p style="color: red; text-align: center;" id="errMsg">{{ errMsg }}</p>
          <p class="text-center mb-0">沒有帳號嗎?
            <a href="/register">註冊新帳號</a>
          </p>
        </div>
        <div class="modal-footer">
          <button type="button" class="button" data-bs-dismiss="modal">Close</button>
          <button type="button" class="button" @click="userLogin">登入</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref, onMounted} from 'vue';
import axios from 'axios';
import Cookies from 'js-cookie';
import {decodeCredential,googleAuthCodeLogin} from "vue3-google-login";

const userInfo = ref({
  customerName: '',
  loginID: '',
});
const loggedIn = ref(false);
const userId = ref(null);
const URL = import.meta.env.VITE_API_JAVAURL;
const headshotURL = ref(`${URL}user/headshot`);
const activePage = ref('home');
const email = ref('');
const password = ref('');
const errMsg = ref('');
const rememberMe = ref(false);


onMounted(async () => {
  // 获取 Cookie
  const savedEmail = Cookies.get('savedEmail');
  console.log('savedEmail: ' + savedEmail)
  if (savedEmail) {
    email.value = savedEmail;
    rememberMe.value = true;
  }
  const link = await document.createElement('link')
  link.href = '/src/assets/css/styles.css'
  link.rel = 'stylesheet'
  await document.head.appendChild(link)
  await checkLoginStatus();
  await getUserData();
});


const getUserData = async () => {
  try {
    if (loggedIn) {
      const res = await axios.get(`${URL}user/getLoginInfo`, {withCredentials: true});
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


const checkLoginStatus = () => {
  // 调用检查登录状态的API
  axios.get(`${URL}checkLoginStatus`, {withCredentials: true})
      .then(response => {
        if (response.data === 'loggedIn') {
          loggedIn.value = true;
          // 如果需要userId,可以在这里获取
        } else {
          loggedIn.value = false;
        }
      });
};

const userLogin = async () => {
  try {
    const formData = new FormData();
    formData.append('email', email.value);
    formData.append('password', password.value);

    if (rememberMe.value) {
      // 设置 Cookie
      Cookies.set('savedEmail', email.value, {expires: 7});
      // localStorage.setItem('savedEmail', email.value);
    } else {
      // 删除 Cookie
      Cookies.remove('savedEmail');
      // localStorage.removeItem('savedEmail');
    }

    const response = await axios.post(`${URL}user/login`, formData, {withCredentials: true});

    console.log("Login result: " + response.data);
    if (response.data === "Y") {
      alert("登入成功")
      errMsg.value = '';
      await checkLoginStatus();
      await getUserData();
      sessionStorage.setItem('CustomerMessageID', userInfo.value.loginID);
    }
    if (response.data === "N") {
      errMsg.value = '用戶帳號或密碼錯誤';
      // alert("帳號或密碼錯誤")
    }
  } catch (error) {
    console.error('AJAX error:', error);
  }
};

const updateActivePage = (page) => {
  activePage.value = page;
};


const logout = async () => {
  const response = await axios.get(`${URL}user/logout`, {withCredentials: true});
  console.log('logout result: ' + response.data);
  sessionStorage.clear();
  await checkLoginStatus();
  await getUserData();
  // goToIndex();
};

const goToIndex = () => {
  window.location.href = '/';
};



const callback = (response) => {
  const userData = decodeCredential(response.credential)
  // console.log("Handle the userData", userData)
  googleLogin(userData)
}

const googleLogin = async (googleInfo) => {
  console.log(googleInfo.sub)
  const response = await axios.post(`${URL}googleLogin`, googleInfo, {withCredentials: true});

  if (response.data === 'Y') {
    errMsg.value = '';
    await checkLoginStatus();
    await getUserData();
    // window.location.reload();
  }
  if (response.data === 'E') {//信箱已被註冊
    const res = confirm(`信箱(${googleInfo.email})已有帳號，是否進行綁定`);
    if (res) {
      const response = await axios.post(`${URL}bindGoogleID`, googleInfo, {withCredentials: true});

      if (response.data === 'E') {
        // window.location.reload();
        await checkLoginStatus();
        await getUserData();
      }
      if (response.data === 'N') {
      }
    }

  }
  if (response.data === 'N') {
    // errMsg.value = `此帳號(${googleInfo.name})尚未綁定SJB.com \n請先註冊後登入綁定`;
    const response = await axios.post(`${URL}registerByGoogle`, googleInfo, {withCredentials: true});
    if (response.data === 'Y') {
      await router.push('/register');
    } else {
      alert('註冊失敗，請聯絡客服');
    }

  }
}
</script>

<style scoped>

</style>