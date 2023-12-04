<template>

  <div class="d-flex align-items-center justify-content-between mb-3">
    <a href="#">
      <h3 class="text-primary">SJB.com</h3>
    </a>
    <h3>註冊帳號</h3>
  </div>

  <div v-if="checkEmailState">
    <label>請輸入您的電子信箱</label>
    <div class="form-floating mb-3">
      <input type="email" name="email" class="form-control" id="floatingInput1"
             placeholder="name@example.com" v-model=inputMail required>
      <label for="floatingInput1">Email</label>
    </div>
    <button class="btn btn-primary py-3 w-100 mb-4" @click="checkEmail" :disabled="checkEmailButtonDisabled">送出
    </button>
    <div class="text-center">
      <p>使用社群帳號註冊: </p>
      <GoogleLogin :callback="callback" prompt auto-login/>
    </div>

  </div>


  <div v-if="checkCodeState">
    <label>請輸入驗證碼</label>
    <div class="form-floating mb-3">
      <input type="text" name="verificationCode" class="form-control" id="floatingInput" placeholder="123456"
             v-model=verificationCode required>
      <label for="floatingInput">驗證碼</label>
    </div>
    <button class="btn btn-primary py-3 w-100 mb-4" @click="checkCode">送出</button>
  </div>

  <!-- 提示 -->
  <p style="color:green;text-align: center;" v-if="mailMsg">{{ mailMsg }}</p>
  <p style="color:red;text-align: center;" id="errMsg">{{ errMsg }}</p>

  <p class="text-center mb-0"> 返回
    <router-link to="/hotel/login">
      <a>登入</a>
    </router-link>
  </p>

  <div v-if="checkEmailState" style="text-align: right; color: rgba(13,172,240,0.3)">
    <a @click="demoAccount()">DEMO </a>
  </div>

</template>

<script setup>
import {ref, onMounted} from 'vue';
import axios from 'axios';
import {useRouter} from 'vue-router';
import {decodeCredential} from "vue3-google-login"

const URL = import.meta.env.VITE_API_JAVAURL;
const mailMsg = ref('');
const errMsg = ref('');
const inputMail = ref('');
const verificationCode = ref('');
const router = useRouter();
const checkEmailState = ref(true);
const checkCodeState = ref(false);
const checkEmailButtonDisabled = ref(false);
const goToRegister = () => {
  router.push('/hotel/register');
};
const demoAccount = () => {
  inputMail.value = 'sjb.com.service@gmail.com';
}


const checkEmail = async () => {
  try {
    checkEmailButtonDisabled.value = true;
    const formData = new FormData();
    formData.append('email', inputMail.value);

    const responseH = await axios.post(`${URL}hotel/startRegister`, formData, {withCredentials: true});

    if (responseH.data === 'Y') {
      errMsg.value = '';
      checkEmailState.value = false;
      checkCodeState.value = true;
      mailMsg.value = '驗證碼已寄送至' + inputMail.value;
      console.log('checkEmail: ' + responseH.data);
    }
    if (responseH.data === 'N') {
      errMsg.value = '此信箱無法使用';
      checkEmailState.value = true;
      checkCodeState.value = false;
      checkEmailButtonDisabled.value = false;
      console.log('checkEmail: ' + responseH.data);
      // console.error('Failed to fetch hotel data:', responseH.status);
    }
  } catch (error) {
    console.error('An error occurred:', error);
  }
};

const checkCode = async () => {
  try {
    const formData = new FormData();
    formData.append('verificationCode', verificationCode.value);

    const responseH = await axios.post(`${URL}hotel/registerCode`, formData, {withCredentials: true});

    if (responseH.data === 'Y') {
      errMsg.value = '';
      mailMsg.value = '驗證成功';
      console.log('checkCode: ' + responseH.data);
      goToRegister();
    }
    if (responseH.data === 'N') {
      console.log('checkEmail: ' + responseH.data);
      errMsg.value = '驗證碼錯誤';
      // console.error('Failed to fetch hotel data:', responseH.status);
    }
  } catch (error) {
    console.error('An error occurred:', error);
  }
};

const callback = (response) => {
  const userData = decodeCredential(response.credential)
  console.log("Handle the userData", userData)
  checkGoogleID(userData);
}
const checkGoogleID = async (googleData) => {
  const response = await axios.post(`${URL}hotel/RegisterByGoogle`, googleData, {withCredentials: true});
  console.log('checkGoogleID: ' + response.data);
  if (response.data === 'Y') {//可使用
    goToRegister();
  }
  if (response.data === 'E') {//信箱已被註冊
    alert(`(${googleData.email})已被註冊，請於登入後綁定`);
  }
  if (response.data === 'N') {//已被註冊
    const res = confirm(`帳號(${googleData.name})已註冊，是否進行登入?`);
    if (res) {
      const response = await axios.post(`${URL}hotel/dashboard`, googleData, {withCredentials: true});
      if (response.data === 'Y') {
        await router.push('/hotel/chooseHotel');
      } else {
        alert('登入失敗，請聯絡客服')
      }
    }
    // errMsg.value = `此帳號(${googleData.name})已綁定其他帳號，請前往登入`;
  }

}


</script>

<style>

</style>