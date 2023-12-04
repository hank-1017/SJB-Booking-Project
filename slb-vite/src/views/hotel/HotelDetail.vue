<template>
  <!-- Sale & Revenue Start -->

  <div class="container-fluid pt-4 px-4">
    <div class="row justify-content-between">
      <form id="hotelDetailForm" @submit.prevent="sendForm">
        <div class="">
          <div class=" bg-light rounded h-100 p-4">
            <div class="col-10">

              <h3 class="mb-4">住宿基本資料
                <button v-if="updateBtm" class="btn btn-warning" @click="updateForm">修改</button>
                <button v-if="sendBtm" class="btn btn-danger" @click="cancelForm">取消</button>
                <button type="submit" v-if="sendBtm" class="btn btn-primary">送出更改</button>
              </h3>

              <div class="form-check form-switch ">
                <h5 class="mb-4">
                  <label class="form-check-label" for="roomCard">住宿狀態</label>
                  <input class="form-check-input" type="checkbox" id="roomCard"
                         v-model="hotelDetail.hotel.state" @change="changeState"/>
                </h5>
              </div>
            </div>


            <div class="row">
              <div class="col">
                <div class="mb-3">
                  <label for="hotelName" class="form-label">住宿名稱:</label>
                  <input type="text" class="form-control " id="hotelName" name="hotelName"
                         :value=hotelDetail.hotel.hotelName readonly>
                </div>
              </div>
              <div class="col">
                <div class="mb-3">
                  <label for="hotelType" class="form-label">住宿類型:</label>
                  <select class="form-select mb-3" aria-label=".form-select-sm example"
                          id="hotelType" name="hotelType" disabled :value=hotelDetail.hotel.hotelType required>
                    <option value="飯店" selected>飯店</option>
                    <option value="民宿">民宿</option>
                    <option value="渡假村">渡假村</option>
                  </select>
                </div>
              </div>
            </div>

            <div class="row">
              <div class="col">
                <div class="mb-3">
                  <label for="hotelStar" class="form-label">住宿星等:</label>
                  <select class="form-select mb-3" aria-label=".form-select-sm example"
                          id="hotelStar" name="hotelStar" disabled :value=hotelDetail.hotel.hotelStar required>
                    <option selected>請選擇住宿星等</option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                  </select>
                </div>
              </div>
              <div class="col">
                <div class="mb-3">
                  <label for="GUInumber" class="form-label">統一編號:</label>
                  <input type="text" class="form-control " id="GUInumber" name="GUInumber"
                         :value=hotelDetail.guinumber readonly required>
                </div>
              </div>
            </div>

            <div class="row">
              <div class="col">
                <div class="mb-3">
                  <label for="businessName" class="form-label">營業負責人:</label>
                  <input type="text" class="form-control " id="businessName"
                         name="businessName" :value=hotelDetail.businessName readonly>
                </div>
              </div>
              <div class="col">
                <div class="mb-3">
                  <label for="openYear" class="form-label">開業年份:</label>
                  <input type="text" class="form-control " id="openYear" name="openYear"
                         :value=hotelDetail.openYear readonly>
                </div>
              </div>
              <div class="col">
                <div class="mb-3">
                  <label for="tel" class="form-label">電話:</label>
                  <input type="text" class="form-control " id="tel" name="tel"
                         :value=hotelDetail.hotel.tel readonly/>
                </div>
              </div>
            </div>

            <div v-if="updateBtm">
              <div class="row">
                <div class="col">
                  <div class="mb-3">
                    <label for="country" class="form-label">國家:</label>
                    <select class="form-select mb-3" aria-label=".form-select-sm example"
                            id="country" name="country" disabled :value=hotelDetail.hotel.country>
                      <option value="台灣">台灣</option>
                    </select>
                  </div>
                </div>

                <div class="col">
                  <div class="mb-3">
                    <label for="city" class="form-label">縣市:</label>
                    <input type="text" :value=hotelDetail.hotel.city class="form-select county" disabled/>
                  </div>
                </div>
                <div class="col">
                  <div class="mb-3">
                    <label for="region" class="form-label">鄉/鎮/區:</label>
                    <input type="text" :value=hotelDetail.hotel.region class="form-select district" disabled/>
                  </div>
                </div>
                <div class="col">
                  <div class="mb-3">
                    <label for="postalCode" class="form-label ">郵遞區號:</label>
                    <input type="text" :value=hotelDetail.hotel.postalCode class="form-control zipcode" disabled/>
                  </div>
                </div>
              </div>
            </div>

            <!--              tw-city-selector-->
            <div v-if="!updateBtm">
              <div class="city-selector-set-has-value">
                <div class="row">
                  <div class="col">
                    <div class="mb-3">
                      <label for="country" class="form-label">國家:</label>
                      <select class="form-select mb-3" aria-label=".form-select-sm example"
                              id="country" name="country" :value=hotelDetail.hotel.country required>
                        <option value="台灣">台灣</option>
                      </select>
                    </div>
                  </div>

                  <div class="col">
                    <div class="mb-3">
                      <label for="city" class="form-label">縣市:</label>
                      <select :value=hotelDetail.hotel.city class="form-select county" id="city" name="city" required/>
                    </div>
                  </div>
                  <div class="col">
                    <div class="mb-3">
                      <label for="region" class="form-label">鄉/鎮/區:</label>
                      <select class="form-select district" id="region" name="region" required/>
                    </div>
                  </div>
                  <div class="col">
                    <div class="mb-3">
                      <label for="postalCode" class="form-label ">郵遞區號:</label>
                      <input type="text" class="form-control zipcode" id="postalCode" name="postalCode"
                             size="3" readonly placeholder="郵遞區號"/>
                    </div>
                  </div>
                </div>

              </div>
            </div>
            <!--              tw-city-selector-->

            <div class="mb-3">
              <label for="street">詳細地址:</label>
              <input type="text" name="street" placeholder="詳細地址" id="street"
                     class="form-control " :value=hotelDetail.hotel.street readonly required/>
            </div>


          </div>
        </div>

      </form>
    </div>
  </div>

</template>
<script setup>
import axios from "axios";
import {ref, onMounted} from 'vue';
import TwCitySelector from 'tw-city-selector';
import {useRouter} from "vue-router";
import Swal from "sweetalert2";

const router = useRouter();

const URL = import.meta.env.VITE_API_JAVAURL;
const sendBtm = ref(false);
const updateBtm = ref(true);
const hotelDetail = ref({
  businessName: '',
  openYear: '',
  guinumber: '',
  hotel: {
    hotelID: '',
    hotelName: '',
    hotelType: '',
    hotelStar: '',
    tel: '',
    country: '',
    city: '',
    region: '',
    postalCode: '',
    street: '',
    state: '',
  },
});


const updateForm = () => {
  sendBtm.value = true;
  updateBtm.value = false;
  document.getElementById("hotelName").readOnly = false;
  document.getElementById("hotelType").disabled = false;
  document.getElementById("hotelStar").disabled = false;
  document.getElementById("GUInumber").readOnly = false;
  document.getElementById("businessName").readOnly = false;
  document.getElementById("openYear").readOnly = false;
  document.getElementById("tel").readOnly = false;
  document.getElementById("street").readOnly = false;
  new TwCitySelector({
    el: '.city-selector-set-has-value',
    elCounty: '.county', // 在 el 裡查找 element
    elDistrict: '.district', // 在 el 裡查找 element
    elZipcode: '.zipcode', // 在 el 裡查找 element
    countyFieldName: 'city', // 在 el 裡查找 element
    districtFieldName: 'region', // 在 el 裡查找 element
    zipcodeFieldName: 'postalCode', // 在 el 裡查找 element
  });
};

const sendForm = async () => {
  // 驗證欄位格式
  if (!validateForm()) {
    return;
  }

  const sendConfirm = confirm('是否送出?');
  if (sendConfirm) {
    const form = document.getElementById('hotelDetailForm');
    const formData = new FormData(form);
    const response = await axios.put(`${URL}hotel/update`, formData, {withCredentials: true})

    if (response.status === 200) {
      alert('修改成功');
      console.log('hoteldetail update: ' + response.data)
      sendBtm.value = false;
      updateBtm.value = true;
      document.getElementById("hotelName").readOnly = true;
      document.getElementById("hotelType").disabled = true;
      document.getElementById("hotelStar").disabled = true;
      document.getElementById("GUInumber").readOnly = true;
      document.getElementById("businessName").readOnly = true;
      document.getElementById("openYear").readOnly = true;
      document.getElementById("tel").readOnly = true;
      document.getElementById("street").readOnly = true;
      await getHotelDetail();
    } else {
      alert('修改失敗');
    }
  }
};

const cancelForm = () => {
  sendBtm.value = false;
  updateBtm.value = true;
  document.getElementById("hotelName").readOnly = true;
  document.getElementById("hotelType").disabled = true;
  document.getElementById("hotelStar").disabled = true;
  document.getElementById("GUInumber").readOnly = true;
  document.getElementById("businessName").readOnly = true;
  document.getElementById("openYear").readOnly = true;
  document.getElementById("tel").readOnly = true;
  document.getElementById("street").readOnly = true;
};

// 驗證方法
const validateForm = () => {

  return true;

}

const getHotelDetail = async () => {
  try {
    const response = await axios.get(`${URL}hotel/getHotelDetail`, {withCredentials: true});
    if (response.status === 200) {
      console.log('getHotelDetail: ' + response.data);
      Object.assign(hotelDetail.value, response.data);
    } else {
      console.error('Failed to fetch hotel detail:', response.status);
    }
  } catch (error) {
    console.error('An error occurred:', error);
  }
};

const changeState = async () => {
  if (!hotelDetail.value.hotel.state) {
    Swal.fire({
      title: '確定要關閉您的住宿嗎?',
      text: "請注意: 關閉住房後後，住宿將無法接收訂單，且顧客無法搜尋住宿資訊",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: '確認',
      cancelButtonText: '取消'
    }).then(async (result) => {
      if (result.isConfirmed) {
        const formdata = new FormData();
        formdata.append('state', hotelDetail.value.hotel.state);
        const res = await axios.put(`${URL}hotel/checkState`, formdata, {withCredentials: true});
        if (res.status === 200) {
          await Swal.fire(
              '已關閉住宿 !',
              '您的住宿已下線',
              'success'
          )
          window.location.reload();
        } else {
          await Swal.fire(
              '關閉住宿失敗 !',
              '系統錯誤，請稍後再試',
              'error'
          )
        }
      }
    })
  } else {
    Swal.fire({
      title: '確定要啟用您的住宿嗎?',
      text: "請注意: 啟用住房前請檢查住宿資訊是否填妥",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: '確認',
      cancelButtonText: '取消'
    }).then(async (result) => {
      if (result.isConfirmed) {
        const formdata = new FormData();
        formdata.append('state', hotelDetail.value.hotel.state);
        const res = await axios.put(`${URL}hotel/checkState`, formdata, {withCredentials: true});
        if (res.status === 200) {
          await Swal.fire(
              '住宿已啟用 !',
              '您的住宿已上線',
              'success'
          )
          window.location.reload();
        } else {
          await Swal.fire(
              '住宿啟用失敗 !',
              '系統錯誤，請稍後再試',
              'error'
          )
        }
      }
    })
  }


}

onMounted(() => {
  getHotelDetail();
});

</script>

<style scoped>
.small-input {
  width: 300px;
}
</style>