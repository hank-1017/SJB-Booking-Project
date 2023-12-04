<template>
  <div class="about-us">
    <section class="about-us section" id="about-us">
      <div class="about-us__container grid">
        <div class="about-us__data">
          <h2 class="section__title about-us_titile text-start">帳號設定</h2>
          <div>
            <!-- <div class="container"> -->
            <h1 class="text-start">用戶個人資訊</h1>
            <form id="userInfo">
              <div v-if="customer" class="text-start shadow-none p-3 mb-5 bg-light rounded">
                <div class="text-center">
                  <label for="">大頭照</label>
                  <img :src="headshotURL" name="headshot" id="headshot" style="width: 30%;">
                  <input type="file" name="headshot" id="headshotupdate" hidden @change="onHeadshotChange">
                </div>
                <div class="mb-3">
                  <label for="email" class="form-label">用戶信箱：</label>
                  <input type="text" class="form-control" name="email" id="email" :value=customer.email disabled />
                </div>

                <div class="row">
                  <div class="col">
                    <label for="customerName" class="form-label">用戶姓名：</label>
                    <input class="form-control" type="text" id="customerName" name="customerName"
                      :value=customer.customerName disabled />
                  </div>

                  <div class="col">
                    <div v-if="!birthdaychange">
                      <label for="birthday">生日：</label>
                      <input class="form-control" type="text" name="birthday" id="birthday" :value=customer.birthday
                        disabled />
                    </div>
                    <div v-else>
                      <label for="birthday">生日：</label>
                      <input class="form-control" type="date" name="birthday" id="birthday" :value=customer.birthday
                        disabled />
                    </div>
                  </div>
                </div>

                <div class="row">
                  <div class="col">
                    <label for="phone">電話：</label>
                    <input type="text" class="form-control" name="phone" id="phone" :value=customer.phone disabled />
                  </div>

                  <div class="col">
                    <div v-if="sexchange">
                      <label for="sex">性別：</label>
                      <!-- <input type="text" name="sex" id="sex" :value=customer.sex disabled /> -->
                      <input type="text" class="form-control" :value="customer.sex" name="sex" id="sex"
                        :disabled="!isEditing" />
                    </div>
                    <div v-else="!sexchange">
                      <label for="sex">性別：</label>
                      <input type="text" class="form-control" :value="customer.sex" name="sex" id="sex"
                        :disabled="!isEditing" />
                      <button type="button" @click="showGenderSelect" id="selectSexButton" v-if="isEditing">選擇性别</button>
                    </div>
                  </div>
                </div>


                <div v-if="updateBtm" class="row">
                  <div class="col">
                    <label for="country">國家：</label>
                    <!-- <input type="text" name="country" id="country" :value=customer.country disabled /> -->
                    <select class="form-select mb-3" name="country" id="country" :value="customer.country" disabled>
                      <option value="台灣">台灣</option>
                    </select>
                  </div>
                  <div class="col">
                    <label for="city">縣市：</label>
                    <input class="form-select county" type="text" name="city" id="city" :value=customer.city disabled
                      required />
                    <!-- <select class="form-select county" name="city" id="city" :value=customer.city disabled /> -->
                  </div>
                  <div class="col">
                    <label for="region">區域：</label>
                    <!-- <select class="form-select district" name="region" id="region" :value=customer.region disabled /> -->
                    <input type="text" :value=customer.region class="form-select district" name="region" id="region"
                      disabled required />
                  </div>
                  <div class="col">
                    <label for="postalCode">郵遞區號：</label>
                    <input class="form-control zipcode" id="postalCode" name="postalCode" type="text"
                      :value=customer.postalCode disabled />
                  </div>
                </div>


                <div v-if="!updateBtm" class="row">
                  <div class="city-selector-set">
                    <div class="col">
                      <label for="country" class="form-label">國家:</label>
                      <select class="form-select mb-3" id="country" name="country" :value=customer.country required>
                        <option value="台灣">台灣</option>
                      </select>
                    </div>

                    <div class="col">
                      <label for="city" class="form-label">縣市:</label>
                      <select :value=customer.city class="form-select county" id="city" name="city" required />
                    </div>
                    <div class="col">
                      <label for="region" class="form-label">區域:</label>
                      <select class="form-select district" id="region" name="region" :value=customer.region required />
                    </div>
                    <div class="col">
                      <label for="postalCode" class="form-label ">郵遞區號:</label>
                      <input type="text" class="form-control zipcode" id="postalCode" name="postalCode" size="3"
                        placeholder="郵遞區號" required />
                    </div>
                  </div>
                </div>
                <div class="mb-3">
                  <label for="street">地址：</label>
                  <input type="text" name="street" id="street" class="form-control" :value=customer.street disabled />
                </div>
                <h5 class="mb-4">
                  <button v-if="updateBtm" class="btn btn-warning" @click="updateForm">修改</button>
                  <button v-if="cancelBtm" class="btn btn-danger" @click="cancelForm">取消</button>
                  <button type="submit" v-if="cancelBtm" class="btn btn-primary" @click="userForm">送出更改</button>
                </h5>
              </div>
              <div v-else>
                <p>未找到用戶資訊或用戶未登入。 </p>
              </div>
            </form>
            <!-- </div> -->
          </div>
        </div>
      </div>
    </section>

    <!--========== SCROLL UP ==========-->
    <a href="#" class="scrollup" id="scroll-up">
      <i class="ri-arrow-up-line scrollup__icon"></i>
    </a>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import Swal from 'sweetalert2';
import TwCitySelector from 'tw-city-selector';

const URL = import.meta.env.VITE_API_JAVAURL;

const cancelBtm = ref(false);
const updateBtm = ref(true);
const birthdaychange = ref(true);
const isEditing = ref(false);
const sexchange = ref(false);

const customer = ref({
  customerName: '',
  email: '',
  sex: '',
  birthday: '',
  phone: '',
  headshot: '',
  country: '',
  city: '',
  region: '',
  street: '',
  postalCode: '',
});



const headshotURL = `${URL}user/headshot`;
let headshotChanged = false;

onMounted(() => {
  getUserInfo();
});


// new TwCitySelector({
//   el: '.city-selector-set-has-value',
//   elCounty: '.county',
//   elDistrict: '.district',
//   elZipcode: '.zipcode',
//   countyFieldName: 'city',
//   districtFieldName: 'region',
//   zipcodeFieldName: 'postalCode',
// });

const inputOptions = {
  '男': '男',
  '女': '女',
  '其他': '其他'
};

const showGenderSelect = async () => {

  const { value: selectedGender } = await Swal.fire({
    title: '選擇性別',
    input: 'radio',
    inputOptions: inputOptions,
    showCloseButton: false,
    inputValidator: (value) => {
      if (!value) {
        return '請選擇性別!';
      }
    }
  });
  // console.log(selectedGender);

  if (selectedGender !== undefined && selectedGender != null) {
    customer.value.sex = selectedGender;
  }
};


const getUserInfo = async () => {
  try {
    const response = await axios.get(`${URL}user/getLoginInfo`, { withCredentials: true });
    if (response.status === 200) {
      // console.log('getLoginInfo: ' + response.data);
      Object.assign(customer.value, response.data);
      // console.log(customer.value.birthday);
      const bdDate = new Date(customer.value.birthday).toLocaleString("zh-TW", { timeZone: "Asia/Taipei" })
      const dateString = bdDate.split(' ')[0];
      // console.log("22222222222222" + dateString);
      let [yyyy, mm, dd] = dateString.split('/');
      if (mm.length < 2) {
        mm = '0' + mm;
      }
      if (dd.length < 2) {
        dd = '0' + dd;
      }

      let formatDateString = `${yyyy}-${mm}-${dd}`;
      customer.value.birthday = formatDateString;
      // console.log(customer.value.birthday)
    } else {
      console.error('Failed to find LoginInfo:', response.status);
    }
  } catch (error) {
    console.error('An error occurred:', error);
  }
};

const onHeadshotChange = (event) => {
  // 大頭照變更
  const newHeadshotFile = event.target.files[0];
  if (newHeadshotFile) {
    headshotChanged = true;
    // 新大頭照
  } else {
    headshotChanged = false;
    // 未選擇新的大頭照
  }
}


const updateForm = () => {
  cancelBtm.value = true;
  updateBtm.value = false;
  birthdaychange.value = true;
  sexchange.value = false;
  isEditing.value = true;
  document.getElementById("headshot").disabled = false;
  document.getElementById("headshotupdate").hidden = false;
  document.getElementById("customerName").disabled = false;
  document.getElementById("birthday").disabled = false;
  document.getElementById("phone").disabled = false;
  document.getElementById("sex").disabled = false;
  document.getElementById("country").disabled = false;
  document.getElementById("city").disabled = false;
  document.getElementById("region").disabled = false;
  document.getElementById("street").disabled = false;
  document.getElementById("postalCode").disabled = false;
  new TwCitySelector({
    el: '.city-selector-set',
    elCounty: '.county', // 在 el 裡查找 element
    elDistrict: '.district', // 在 el 裡查找 element
    elZipcode: '.zipcode', // 在 el 裡查找 element
    countyFieldName: 'city', // 在 el 裡查找 element
    districtFieldName: 'region', // 在 el 裡查找 element
    zipcodeFieldName: 'postalCode', // 在 el 裡查找 element
  });
};

const userForm = async () => {
  // 驗證欄位格式
  if (!validateForm()) {
    return;
  };

  const sendConfirm = confirm('是否送出?');
  if (sendConfirm) {
    const form = document.getElementById('userInfo');
    const formData = new FormData(form);

    if (!headshotChanged) {
      // 如果未改變大頭照，刪除 formData 中的 headshot
      formData.delete('headshot');
    }

    const response = await axios.put(`${URL}customer/update`, formData, { withCredentials: true })

    if (response.status === 200) {
      alert('修改成功');
      // console.log('userInfo update: ' + response.data)
      cancelBtm.value = false;
      updateBtm.value = true;
      document.getElementById("headshot").disabled = true;
      document.getElementById("headshotupdate").hidden = true;
      document.getElementById("customerName").disabled = true;
      document.getElementById("birthday").disabled = true;
      document.getElementById("birthday").value;
      document.getElementById("phone").disabled = true;
      document.getElementById("sex").disabled = true;
      document.getElementById("country").disabled = true;
      document.getElementById("city").disabled = true;
      document.getElementById("region").disabled = true;
      document.getElementById("street").disabled = true;
      document.getElementById("postalCode").disabled = true;
      await getUserInfo();
      // window.location.reload();
    } else {
      alert('修改失敗');
    }
  }
};

const cancelForm = () => {
  updateBtm.value = true;
  document.getElementById("headshot").disabled = true;
  document.getElementById("customerName").disabled = true;
  document.getElementById("email").disabled = true;
  document.getElementById("birthday").disabled = true;
  document.getElementById("phone").disabled = true;
  document.getElementById("sex").disabled = true;
  document.getElementById("country").disabled = true;
  document.getElementById("city").disabled = true;
  document.getElementById("region").disabled = true;
  document.getElementById("street").disabled = true;
  document.getElementById("postalCode").disabled = true;
};

const validateForm = () => {
  return true;
}
</script>


<style scoped></style>