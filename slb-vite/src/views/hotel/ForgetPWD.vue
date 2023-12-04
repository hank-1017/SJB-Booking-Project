<template>

          <div class="d-flex align-items-center justify-content-between mb-3">
            <a href="#">
              <h3 class="text-primary">SJB.com</h3>
            </a>
            <h3>忘記密碼</h3>
          </div>

            <label>請輸入您的電子信箱</label>
            <div class="form-floating mb-3">
              <input type="email" name="email" class="form-control" id="floatingInput"  placeholder="name@example.com" v-model=inputEmail required>
              <label for="floatingInput">Email</label>
            </div>
           <button  class="btn btn-primary py-3 w-100 mb-4"  @click=checkEmail :disabled="isButtonDisabled" >送出</button>

          <!-- 提示 -->
          <p style="color:green;text-align: center;" ></p>
          <p style="color:red ;text-align: center;" id="errMsg" v-if=errMsg>{{errMsg}}</p>

          <p class="text-center mb-0"> 返回
            <router-link to="/hotel/login">
            <a>登入</a>
            </router-link>
          </p>

  <!-- Sign In End -->
</template>

<script setup>
import {ref, onMounted} from 'vue';
import axios from 'axios';
import {useRouter} from 'vue-router';

const URL = import.meta.env.VITE_API_JAVAURL;
const mailMsg = ref('');
const errMsg = ref('');
const inputEmail = ref('');
const isButtonDisabled = ref(false);

const router = useRouter();
const goToResetPwd = () => {
  router.push('/hotel/resetPwd');
};

const checkEmail = async () => {
  isButtonDisabled.value = true;
  try {
    const formData = new FormData();
    formData.append('email', inputEmail.value);

    const responseH = await axios.post(`${URL}hotel/forget_password`, formData, {withCredentials: true});

    if (responseH.data === 'Y') {
      errMsg.value = '';
      console.log('checkEmail: ' + responseH.data);
      goToResetPwd();
    }
    if (responseH.data === 'N') {
      console.log('checkEmail: ' + responseH.data);
      isButtonDisabled.value = false;
      errMsg.value='此信箱尚未註冊';
      // console.error('Failed to fetch hotel data:', responseH.status);
    }
  } catch (error) {
    console.error('An error occurred:', error);
  }
};
</script>

<style scoped>

</style>