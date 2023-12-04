<template>
  <div class="container-fluid pt-4 px-4">

    <form id="hotelServiceForm" @submit.prevent="sendForm">
      <div class=" bg-light rounded h-100 p-4">

        <div class="row">
          <div class="col">

            <h3 class="mb-4">住宿設施服務
              <button v-if="updateBtm" class="btn btn-warning" @click="updateForm">修改</button>
              <button v-if="sendBtm" class="btn btn-danger" @click="cancelForm">取消</button>
              <button type="submit" v-if="sendBtm" class="btn btn-primary">送出更改</button>
            </h3>

          </div>
        </div>

        <div class="row">

          <div class="col-3">
            <div class="mb-3 text-dark">
              <div class="form-check form-switch">
                <input class="form-check-input" type="checkbox" id="cleanService"
                       v-model="hotelDetail.cleaningService" @change="updateCleanService()" :disabled="updateBtm"/>
                <label class="form-check-label" for="cleanService">每日清潔服務</label>
              </div>
              <div class="form-check form-switch">
                <input class="form-check-input" type="checkbox" id="expressCheckin"
                       v-model="hotelDetail.expressCheckin" :disabled="updateBtm"/>
                <label class="form-check-label" for="expressCheckin">快速入住</label>
              </div>
              <div class="form-check form-switch">
                <input class="form-check-input" type="checkbox" id="counter24hr"
                       v-model="hotelDetail.counter24hr" :disabled="updateBtm"/>
                <label class="form-check-label" for="counter24hr">24 小時接待櫃檯</label>
              </div>
              <div class="form-check form-switch">
                <input class="form-check-input" type="checkbox" id="freeWiFi"
                       v-model="hotelDetail.freeWiFi" :disabled="updateBtm"/>
                <label class="form-check-label" for="freeWiFi">免費WiFi</label>
              </div>
              <div class="form-check form-switch">
                <input class="form-check-input" type="checkbox" id="roomCard"
                       v-model="hotelDetail.roomCard" :disabled="updateBtm"/>
                <label class="form-check-label" for="roomCard">房卡進出</label>
              </div>
              <div class="form-check form-switch">
                <input class="form-check-input" type="checkbox" id="noSmoking"
                       v-model="hotelDetail.noSmoking" :disabled="updateBtm"/>
                <label class="form-check-label" for="noSmoking">全面禁菸</label>
              </div>
              <div class="form-check form-switch">
                <input class="form-check-input" type="checkbox" id="petFriendly"
                       v-model="hotelDetail.petFriendly" :disabled="updateBtm"/>
                <label class="form-check-label" for="petFriendly">可攜帶寵物</label>
              </div>
            </div>
          </div>

          <div class="col-4">
            <div v-if="hotelDetail.petFriendly">
              <div class="mb-4">
                <label for="petDeposit">寵物押金</label>
                <input type="number" name="petDeposit" class="form-control small-input text-dark" id="petDeposit"
                       v-model="hotelDetail.petDeposit" :disabled="updateBtm"/>
              </div>
              <div class="mb-3">
                <label for="petCleaningFee">寵物清潔費</label>
                <input type="number" name="petCleaningFee" class="form-control small-input text-dark"
                       id="petCleaningFee" v-model="hotelDetail.petCleaningFee" :disabled="updateBtm"/>
              </div>
            </div>
          </div>

        </div>

        <br>

        <div class="row">
          <div class="col ">
            <h3 class="mb-4">預訂須知</h3>
            <div class="form-floating">
          <textarea class="form-control text-dark" placeholder="Leave a comment here" id="floatingTextarea"
                    style="height: 150px" v-model="hotelDetail.reservationNotice" :disabled="updateBtm"
                    required></textarea>
            </div>
          </div>
        </div>

      </div>


    </form>
  </div>


</template>

<script setup>
import {ref, onMounted} from 'vue';
import axios from "axios";
import Swal from "sweetalert2";

const URL = import.meta.env.VITE_API_JAVAURL;
const sendBtm = ref(false);
const updateBtm = ref(true);

onMounted(() => {

})

const hotel = ref({
  hotelName: '',
  hotelID: 0, // Initialize with the actual value
});

const hotelDetail = ref({
  cleaningService: false, // Initialize with the actual value
  expressCheckin: false, // Initialize with the actual value
  counter24hr: false, // Initialize with the actual value
  freeWiFi: false, // Initialize with the actual value
  roomCard: false, // Initialize with the actual value
  noSmoking: false, // Initialize with the actual value
  petFriendly: false, // Initialize with the actual value
  petDeposit: 0, // Initialize with the actual value
  petCleaningFee: 0, // Initialize with the actual value
  reservationNotice: '',
});


const petDepositError = ref('');
const petCleaningFeeError = ref('');

const isValidInteger = (value) => {
  return /^-?\d+$/.test(value);
};

const updateForm = () => {
  sendBtm.value = true;
  updateBtm.value = false;
}

const cancelForm = () => {
  sendBtm.value = false;
  updateBtm.value = true;
  getHotelDetail();
};

const updateCleanService = async () => {
  const formData = new FormData();
  formData.append('cleanService', hotelDetail.value.cleaningService);
  console.log('cleanService: ' + hotelDetail.value.cleaningService);
  const res = await axios.post(`${URL}hotelDetail/cleanService`, formData, {withCredentials: true});
  if (res.status === 200) {
    alert('更新成功');
  } else {
    alert('更新失敗');
  }
}

const validatepetDeposit = () => {
  const inputValue = hotelDetail.petDeposit.toString();
  if (!isValidInteger(inputValue) || parseInt(inputValue) > 50000) {
    petDepositError.value = '請輸入金額。金額上限50000';
    return false;
  } else {
    petDepositError.value = '';
    return true;
  }
};

const validatepetCleaningFee = () => {
  const inputValue = hotelDetail.petCleaningFee.toString();
  if (!isValidInteger(inputValue) || parseInt(inputValue) > 50000) {
    petCleaningFeeError.value = '請輸入金額。金額上限50000';
    return false;
  } else {
    petCleaningFeeError.value = '';
    return true;
  }
};

const submitPetMoney = () => {
  if (validatepetDeposit() && validatepetCleaningFee()) {
    // Implement your AJAX request and logic here
  }
};

const containsSensitiveWords = (text) => {
  const sensitiveWords = ['幹', '幹您娘', 'fuck'];
  for (const word of sensitiveWords) {
    if (text.toLowerCase().includes(word)) {
      return true;
    }
  }
  return false;
};

const updateReservationNotice = () => {
  if (containsSensitiveWords(hotelDetail.reservationNotice)) {
    alert('包含敏感詞彙 請重新輸入');
  } else {
    // Implement your AJAX request and logic here
  }
};

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

const sendForm = async () => {
  Swal.fire({
    title: '是否送出?',
    text: "",
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#3085d6',
    cancelButtonColor: '#d33',
    confirmButtonText: '確認',
    cancelButtonText: '取消'
  }).then(async (result) => {
    if (result.isConfirmed) {
      try {
        // const form = document.getElementById('hotelServiceForm');
        const formData = new FormData();
        formData.append('cleanService', hotelDetail.value.cleaningService);
        formData.append('expressCheckin', hotelDetail.value.expressCheckin);
        formData.append('counter24hr', hotelDetail.value.counter24hr);
        formData.append('freeWiFi', hotelDetail.value.freeWiFi);
        formData.append('roomCard', hotelDetail.value.roomCard);
        formData.append('noSmoking', hotelDetail.value.noSmoking);
        formData.append('petFriendly', hotelDetail.value.petFriendly);
        formData.append('reservationNotice', hotelDetail.value.reservationNotice);
        if (hotelDetail.value.petFriendly) {
          formData.append('petCleaningFee', hotelDetail.value.petCleaningFee);
          formData.append('petDeposit', hotelDetail.value.petDeposit);
        } else {
          formData.append('petCleaningFee', 0);
          formData.append('petDeposit', 0);
        }

        const response = await axios.put(`${URL}hotel/updateHotelService`, formData, {withCredentials: true})

        if (response.status === 200) {
          await Swal.fire(
              '更新服務成功',
              '',
              'success'
          );
          console.log('hotelService update: ' + response.data)
          sendBtm.value = false;
          updateBtm.value = true;
          await getHotelDetail();
        } else {
          await Swal.fire(
              '修改失敗，請聯絡客服',
              '',
              'error'
          );
        }
      } catch (error) {
        alert('服務端錯誤');
        console.error('An error occurred:', error);
      }
    }
  })
}


onMounted(() => {
  getHotelDetail();
});
</script>
