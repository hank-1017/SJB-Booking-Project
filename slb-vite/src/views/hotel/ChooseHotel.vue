<script setup>
import {ref, onMounted} from 'vue';
import axios from 'axios';
import {useRouter} from 'vue-router';
import Swal from 'sweetalert2';

const URL = import.meta.env.VITE_API_JAVAURL;
const hotels = ref([]);
const router = useRouter();

onMounted(async () => {
  const loadingAlert = Swal.fire({
            title: '查詢中',
            html: '<i class="fas fa-spinner fa-spin"></i> 請稍候...', // 使用FontAwesome圖標
            showConfirmButton: false,
        });
  const hotelsAPI = `${URL}hotel/findAllhoteljson`;
  try {
    const responseH = await axios.get(hotelsAPI, {withCredentials: true});
    hotels.value = responseH.data;
    loadingAlert.close();
  } catch (error) {
    console.error("Failed to load hotels: ", error);
  }
});

const linkToRooms = async (hotelID) => {
  const setHotelID = `${URL}hotel/setHotelID?hotelID=${hotelID}`;
  const response = await axios.get(setHotelID, {withCredentials: true});
  if (response.status === 200) {
    sessionStorage.setItem('HotelMessageID',hotelID)
    window.location.href = '/hotel/dashboard';
  }
}
</script>

<template>
  <div class="d-flex align-items-center justify-content-between mb-3">
    <a href="#">
      <h3 class="text-primary">SJB.com</h3>
    </a>
    <h3>選擇住宿</h3>
  </div>


  <div v-for="{ hotelID, hotelName, tel, country, city, region, street, postalCode, state } in hotels">

    <div class="card mb-3" style="width: 100%; height: 112px;" @click="linkToRooms(hotelID)">
      <div class="row g-0">
        <div class="col-md-4">
          <img src="@/assets/img/hoteltag.jpg" alt="" style="width: 112px;  height: auto;">
        </div>
        <div class="col-md-8">
          <div class="card-body">
            <h5 class="card-title">{{ hotelName }}</h5>
            <p class="card-text">{{ postalCode + ' ' + city + ' ' + region + street }}</p>
          </div>
        </div>
      </div>
    </div>

  </div>

</template>

<style scoped>
.card {
  background-color: white;
  width: 19rem;
  margin: 10px;
  cursor: pointer
}

.cardAlignment {
  margin: auto;
  padding: auto;
  display: flex;
  flex-wrap: wrap;
}

.card:hover {
  border: #ADADAD 3px solid;
}
</style>