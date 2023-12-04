<template>
  <!-- Sign In Start -->
  <div class="d-flex align-items-center justify-content-between mb-3">
    <a href="#">
      <h3 class="text-primary">SJB.com</h3>
    </a>
    <h3>密碼重置</h3>
  </div>

  <form @submit.prevent="submitForm">
    <div class="form-floating mb-3">
      <input type="text" v-model="verificationCode" class="form-control" id="floatingInput1" required/>
      <label for="floatingInput1">驗證碼</label>
    </div>

    <div class="form-floating mb-3">
      <input type="password" v-model="newPwd" class="form-control" id="newPassword" required/>
      <label for="newPassword">新密碼</label>
    </div>

    <div class="form-floating mb-3">
      <input type="password" v-model="rePwd" class="form-control" id="confirmPassword" required/>
      <label for="confirmPassword">請確認密碼</label>
    </div>

    <button type="submit" class="btn btn-primary py-3 w-100 mb-4" :disabled="isButtonDisabled">送出</button>
  </form>

  <!-- 提示 -->
  <p style="color:green;text-align: center;" v-if="resetMail">驗證碼已發送至 {{ resetMail }}</p>
  <p style="color:green;text-align: center;" v-if="mailMsg">{{ mailMsg }}</p>
  <p style="color:red;text-align: center;" id="errMsg">{{ errMsg }}</p>

  <p class="text-center mb-0"> 返回
    <router-link to="/hotel/login">
      <a>登入</a>
    </router-link>
  </p>

  <p class="text-center mb-0"> 沒有驗證碼?
    <router-link to="/hotel/forgetPwd">
      <a>取得驗證碼</a>
    </router-link>
  </p>
  <!-- Sign In End -->
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';

const URL = import.meta.env.VITE_API_JAVAURL;
const mailMsg = ref('');
const errMsg = ref('');
const verificationCode = ref('');
const newPwd = ref('');
const rePwd = ref('');
const resetMail = ref('');

const isButtonDisabled = computed(() => {
  return !verificationCode.value || !newPwd.value || !rePwd.value || newPwd.value !== rePwd.value;
});

const router = useRouter();

const getResetMail = async()=>{
  const response = await axios.get(`${URL}hotel/getResetMail`,{withCredentials: true});
  resetMail.value=response.data;
  console.log('getResetMail: '+resetMail.value);
}

const submitForm = async () => {
  try {
    const formData = new FormData();
    formData.append('verificationCode', verificationCode.value);
    formData.append('newPwd', newPwd.value);

    const response = await axios.post(`${URL}hotel/reset_password`, formData,{withCredentials: true});

    if (response.data === 'Y') {
      errMsg.value = '';
      alert('密碼重置成功')
      await router.push('/hotel/login');
    } else if (response.data === 'N') {
      errMsg.value = '密碼重置失敗';
    }
  } catch (error) {
    console.error('AJAX error:', error);
  }
};

  getResetMail();


</script>

<style scoped>
</style>
