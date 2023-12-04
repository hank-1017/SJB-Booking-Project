<template>

  <div class="d-flex align-items-center justify-content-between mb-3">
    <a href="#">
      <h3 class="text-primary">SJB.com</h3>
      <h6 class="text-primary">步驟{{ stepNum }}/4</h6>
    </a>
    <h3>註冊帳號</h3>
  </div>

  <!--步驟1-->
  <transition name="slide-fade">
    <div v-if="step1State">
      <label>您的註冊帳號</label>
      <div class="form-floating mb-3">
        <input type="email" v-model="registerMail" class="form-control" id="floatingInput1" readonly/>
        <label for="floatingInput1">電子信箱</label>
      </div>

      <label>請輸入密碼</label>
      <div class="form-floating mb-3">
        <input type="password" v-model="newPwd" class="form-control" id="newPassword" required/>
        <label for="newPassword">新密碼</label>
      </div>

      <label>請確認密碼</label>
      <div class="form-floating mb-3">
        <input type="password" v-model="rePwd" class="form-control" id="confirmPassword" required/>
        <label for="confirmPassword">確認密碼</label>
      </div>

      <button class="btn btn-primary py-3 w-100 mb-4" @click="goToStep2">下一步</button>
    </div>
  </transition>

  <!--步驟2-->
  <transition name="slide-fade">
    <div v-if="step2State">
      <label>請輸入用戶名稱</label>
      <div class="form-floating mb-3">
        <input type="text" name="verificationCode" class="form-control" id="floatingInput" placeholder="SJB集團"
               v-model=loginName required>
        <label for="floatingInput">用戶名稱</label>
      </div>

      <label>請輸入飯店名稱</label>
      <div class="form-floating mb-3">
        <input type="text" name="verificationCode" class="form-control" id="floatingInput" placeholder="SJB集團"
               v-model=hotelName required>
        <label for="floatingInput">飯店名稱</label>
      </div>

      <label>請選擇飯店類型</label>
      <div class="form-floating mb-3">
        <select v-model=hotelType name="hotelType" id="hotelType" class="form-select"
                aria-label="Floating label select example" required>
          <option value="民宿">民宿</option>
          <option value="星級飯店">星級飯店</option>
          <option value="渡假村">渡假村</option>
        </select>
        <label for="floatingInput">飯店類型</label>
      </div>

      <label for="hotelStar">請選擇飯店等級</label>
      <div class="form-floating mb-3">
        <select v-model=hotelStar name="hotelStar" id="hotelStar" class="form-select"
                aria-label="Floating label select example" required>
          <option value="1">1星級</option>
          <option value="2">2星級</option>
          <option value="3">3星級</option>
          <option value="4">4星級</option>
          <option value="5">5星級</option>
        </select>
        <label for="floatingInput">飯店等級</label>
      </div>

      <div class="text-center">
        <button class="btn btn-lg btn-secondary m-2" @click="goToStep1">上一步</button>
        <button class="btn btn-lg btn-primary m-2" @click="goToStep3">下一步</button>
      </div>
    </div>
  </transition>


  <!--步驟3-->
  <transition name="slide-fade">
    <div v-if="step3State">
      <label>請選擇飯店所在國家</label>
      <div class="form-floating mb-3">
        <select v-model=country name="country" id="country" class="form-select"
                aria-label="Floating label select example"
                required>
          <option value="台灣" selected>台灣</option>
        </select>
        <label for="floatingInput">國家</label>
      </div>
      <div class="city-selector-set">
        <label>請輸入飯店所在地址</label>
        <div class="form-floating mb-3">
          <select name="city" id="city" class="form-select county" v-model="inputcity" required/>
          <label for="city">縣市</label>
        </div>

        <div class="form-floating mb-3">
          <select name="region" id="region" class="form-select district" v-model="inputregion" required/>
          <label for="region">鄉/鎮/區</label>
        </div>

        <div class="form-floating mb-3">
          <input type="text" class="form-control zipcode" id="postalCode" name="postalCode" size="3" @change="changePostalCode" readonly/>
          <label for="postalCode">郵遞區號</label>
        </div>
      </div>

      <div class="form-floating mb-3">
        <input type="text" class="form-control" id="direct" placeholder="詳細地址" v-model="inputdirect"/>
        <label for="direct">詳細地址</label>
      </div>

      <div class="form-floating mb-3">
        <input type="text" class="form-control" id="tel" placeholder="手機號碼/市內電話" v-model="inputtel"/>
        <label for="tel">電話</label>
      </div>

      <div class="text-center">
        <button class="btn btn-lg btn-secondary m-2" @click="goToStep2">上一步</button>
        <button class="btn btn-lg btn-primary m-2" @click="goToStep4">下一步</button>
      </div>
    </div>
  </transition>

  <!--步驟4-->
  <transition name="slide-fade">
    <div v-if="step4State">

      <div class="form-floating mb-3" style="text-align: center">
        <img :src='showHeadshot' width="150" height="150" class="rounded-circle" alt="">
      </div>

      <label>請上傳大頭照</label>
      <div class="form-floating mb-3">
        <input type="file" class="form-control form-control-lg" id="headshot" ref="headshot"
               @change="handleFileChange"/>
        <label for="headshot">大頭照</label>
      </div>

      <label>請輸入統一編號</label>
      <div class="form-floating mb-3">
        <input v-model=inputGUInumber name="GUInumber" id="GUInumber" class="form-control" required/>
        <label for="floatingInput">統一編號</label>
      </div>

      <label>請輸入營業負責人姓名</label>
      <div class="form-floating mb-3">
        <input type="text" name="businessName" id="businessName" class="form-control" v-model="inputbusinessName"
               required/>
        <label for="businessName">飯店負責人姓名</label>
      </div>

      <label>請輸入飯店開業年分</label>
      <div class="form-floating mb-3">
        <input type="text" name="openYear" id="openYear" class="form-control" v-model="inputopenYear" required/>
        <label for="openYear">開業年分</label>
      </div>


      <div class="text-center">
        <button class="btn btn-lg btn-secondary m-2" @click="goToStep3">上一步</button>
        <button class="btn btn-lg btn-primary m-2 " @click="submitForm" :disabled="isButtonDisabled">送出</button>
      </div>
    </div>
  </transition>

  <!-- 提示 -->
  <p style="color:green;text-align: center;" v-if="mailMsg">{{ mailMsg }}</p>
  <p style="color:red;text-align: center;" id="errMsg" v-if="errMsg">{{ errMsg }}</p>

  <p class="text-center mb-0">
    <br>
    <router-link to="/hotel/login">
      <a>取消註冊</a>
    </router-link>
  </p>

</template>

<script setup>
import {ref, onMounted} from 'vue';
import axios from 'axios';
import {useRouter} from 'vue-router';
import TwCitySelector from "tw-city-selector";
import defaultHeadshot from '@/assets/img/headshot.png';
import Swal from "sweetalert2";

const URL = import.meta.env.VITE_API_JAVAURL;
const stepNum = ref("1");
const mailMsg = ref('');
const isButtonDisabled = ref(false);
const errMsg = ref('');
const router = useRouter();
const step1State = ref(true);
const step2State = ref(false);
const step3State = ref(false);
const step4State = ref(false);
const registerMail = ref('a123456@mail.com');
const newPwd = ref("789789");
const rePwd = ref("789789");
const loginName = ref("SuperMen");
const hotelName = ref("南台大飯店");
const hotelType = ref("飯店");
const hotelStar = ref("5");
const country = ref("台灣");

const inputcity = ref('');
const inputregion = ref('');
const inputpostalCode = ref('');
const inputdirect = ref('中正路200號');
const inputtel = ref('0678787878');

const inputGUInumber = ref('82584359');
const inputbusinessName = ref('沈孟儒');
const inputopenYear = ref('2023');
const showHeadshot = ref('http://localhost:5173/src/assets/img/headshot.png');

onMounted(() => {
  getRegisterMail();
});


const handleFileChange = (event) => {
  const file = event.target.files[0];

  const reader = new FileReader();
  reader.onload = (e) => {
    showHeadshot.value = e.target.result;
  };
  reader.readAsDataURL(file);
};

const changePostalCode=()=>{
  console.log('call changePostalCode');
  inputpostalCode.value = document.getElementById('postalCode').value;
}
const goToLogin = () => {
  router.push('/hotel/login');
};

const goToStep1 = () => {
  step1State.value = true;
  step2State.value = false;
  step3State.value = false;
  step4State.value = false;
  errMsg.value = '';
  stepNum.value = "1";
}
const goToStep2 = () => {
  if (newPwd.value === rePwd.value) {
    step1State.value = false;
    step2State.value = true;
    step3State.value = false;
    step4State.value = false;
    errMsg.value = '';
    stepNum.value = "2";
  } else {
    errMsg.value = '密碼不一致';
  }
}

const goToStep3 = () => {
  if (loginName.value === '' || hotelName.value === '' || hotelType.value === '' || hotelStar.value === '') {
    errMsg.value = '資料有誤，請填寫完整資料';
    return;
  }
  errMsg.value = '';
  step1State.value = false;
  step2State.value = false;
  step3State.value = true;
  step4State.value = false;
  stepNum.value = "3";

  new TwCitySelector({
    el: '.city-selector-set',
    elCounty: '.county', // 在 el 裡查找 element
    elDistrict: '.district', // 在 el 裡查找 element
    elZipcode: '.zipcode', // 在 el 裡查找 element
    countyFieldName: 'city', // 在 el 裡查找 element
    districtFieldName: 'region', // 在 el 裡查找 element
    zipcodeFieldName: 'postalCode', // 在 el 裡查找 element
  });
}

const goToStep4 = () => {
  changePostalCode();
  if (inputcity.value === '' || inputregion.value === '' || inputpostalCode.value === '' || inputdirect.value === '' || inputtel.value === '') {
    errMsg.value = '資料有誤，請填寫完整資料';
    return;
  }
  errMsg.value = '';
  step1State.value = false;
  step2State.value = false;
  step3State.value = false;
  step4State.value = true;
  stepNum.value = "4";
}


const submitForm = async () => {
  if (inputGUInumber.value.length !== 8) {
    errMsg.value = '統一編號長度錯誤';
    return;
  }
// 獲取今年年分
  const currentYear = new Date().getFullYear();
// 檢查開業年分長度及數值
  if (inputopenYear.value.length !== 4) {
    errMsg.value = '開業年分長度錯誤';
    return;
  }
  if (parseInt(inputopenYear.value) > currentYear) {
    errMsg.value = '開業年分不可大於今年';
    return;
  }
  if (inputbusinessName.value.length > 10 || inputbusinessName.value.length < 1) {
    errMsg.value = '負責人姓名長度錯誤';
    return;
  }

  isButtonDisabled.value = true;
  try {
    const formData = new FormData();
    formData.append('email', registerMail.value);
    formData.append('pass', newPwd.value);
    formData.append('loginName', loginName.value);
    formData.append('hotelName', hotelName.value);
    formData.append('hotelType', hotelType.value);
    formData.append('hotelStar', hotelStar.value);
    formData.append('tel', inputtel.value);
    formData.append('country', country.value);
    formData.append('city', inputcity.value);
    formData.append('region', inputregion.value);
    formData.append('street', inputdirect.value);
    formData.append('postalCode', inputpostalCode.value);
    formData.append('GUInumber', inputGUInumber.value);
    formData.append('businessName', inputbusinessName.value);
    formData.append('openYear', inputopenYear.value);
    if (document.getElementById('headshot').files[0] === undefined) {
      formData.append('headshot', null);
    } else {
      console.log('getElementById(headshot).files[0] !== undefined');
      formData.append('headshot', document.getElementById('headshot').files[0]);
    }
    // formData.append('headshot', document.getElementById('headshot').files[0]);

    const res = await axios.post(`${URL}hotel/register`, formData, {withCredentials: true});
    if (res.data === 'Y') {
      await Swal.fire(
          '註冊成功',
          '',
          'success'
      )
      goToLogin();
    } else {
      errMsg.value = '資料有誤，請填寫完整資料';
      isButtonDisabled.value = false;
    }
  } catch (error) {
    errMsg.value = '服務端錯誤，請稍後再試';
    isButtonDisabled.value = false;
    console.error("An error occurred:", error);
  }
}

//取得session註冊信箱
const getRegisterMail = async () => {
  try {
    const res = await axios.get(`${URL}hotel/getRegisterMail`, {withCredentials: true});
    registerMail.value = res.data;
  } catch (error) {
    console.error("An error occurred:", error);
  }
}

</script>

<style>
.slide-fade-enter-active {
  transition: all 0.3s ease-out;
}


.slide-fade-enter-from,
.slide-fade-leave-to {
  transform: translateX(20px);
  opacity: 0;
}
</style>