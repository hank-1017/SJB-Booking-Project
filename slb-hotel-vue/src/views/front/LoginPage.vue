<template>
  <br>
  <br>
  <br>
  <br>
  <h1 style="text-align: center;">登入頁面</h1>
  <div class="card" style="width: 40%; margin-left: auto; margin-right: auto; ">
    <div class="card-header" style="font-size: large;">登入</div>
    <div class="card-body">
      <form @submit.prevent="submitForm" style="box-sizing: border-box;">
        <div class="input-group" style="width: 120%;">
          <label style="padding: 5px 0px;">帳號：</label>
          <input class="form-control" name="email" id="email" v-model="email" style="flex: 0 1 70%; margin-left: 10px;"
            required />
        </div>

        <br>
        <div class="input-group" style="width: 120%;">
          <label style="padding: 5px 0px;">密碼：</label>
          <input class="form-control" name="password" id="password" v-model="password" type="password"
            style="flex: 0 1 70%;  margin-left: 10px" required />
        </div>
        <div class="d-flex align-items-center justify-content-between mb-2"
          style="box-sizing: border-box; padding-top: 20px">
          <div>
            <input type="checkbox" class="form-check-input" id="rememberMe" name="rememberMe" v-model="rememberMe">
            <label class="form-check-label" for="rememberMe">記住帳號</label>
          </div>
          <div>
            <router-link to="/restPwd">
              <a>忘記密碼</a>
            </router-link>
            <span class="divider" style="display: ;"></span>
            <router-link to="/register">
              <a>立即註冊</a>
            </router-link>
            <br>

          </div>
        </div>
        <button type="submit" class="button container"
          style="padding: 5px 10px; margin-top: 30px; border-radius: 5px;">送出</button>
      </form>
    </div>
  </div>
</template>

<script setup>
import axios from "axios";
import { ref } from "vue";

const email = ref('');
const password = ref('');
const URL = import.meta.env.VITE_API_JAVAURL;
const rememberMe = ref(false);
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

    const response = await axios.post(`${URL}user/login`, formData, { withCredentials: true });

    console.log("Login result: " + response.data);
    if (response.data === "Y") {
      alert("登入成功")
      await getLoginID();
      window.location.href = `/`;
    }
    if (response.data === "N") {
      alert("帳號或密碼錯誤")
    }
  } catch (error) {
    console.error('AJAX error:', error);
  }
};

const redirectToResetPassword = () => {
  window.location.href = '/RestPwd'; // 將用戶重定向到更新密碼的頁面
};

const getLoginID = async () => {
  const res = await axios.get(`${URL}message/getSessionCustomerID`, { withCredentials: true });
  sessionStorage.setItem('CustomerMessageID', res.data)
}
</script>

<style>
#forgetpwd {
  float: right;
}

.divider {
  display: inline-block;
  margin: 0 5px;
  /* 調整間距 */
  border-right: 3px solid #000;
  /* 顏色及邊框 */
  height: 15px;
  /* 線的高度 */
}

.card-body {
  margin-top: 10px;
  /* 調整上邊距 */
  padding-bottom: 80px;
  /* 調整下邊距 */
}
</style>