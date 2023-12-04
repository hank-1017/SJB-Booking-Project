<template>
  <div class="container-fluid pt-4 px-4">
    <div class="row g-4">
      <div class="col-sm-12">
        <div class="bg-light rounded h-100 p-4">
          <h3 class="mb-4">後台通知</h3>

          <div v-for="{ hotelNoticeID,content, status,noticeTime} in notify.slice().reverse()">
            <div class="alert alert-dismissible fade show" :class=" {'alert-success': status === '未讀'},{'alert-secondary': status === '已讀'}" role="alert">
                <a href="#" @click="changeNotifyState(hotelNoticeID)" >

                  <div class="d-flex justify-content-between">
                    <div><h6 class="fw-normal mb-0"> <i class="fa fa-exclamation-circle me-2"></i>{{ content }}</h6> <small>{{ noticeTime }}</small></div>
                    <h6 class="fw-normal mb-0">{{ status }}</h6>
                  </div>
                </a>
              <button type="button" class="btn-close" data-bs-dismiss="alert"  @click="deleteNotify(hotelNoticeID)"></button>
            </div>
          </div>

        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import axios from "axios";
import {ref, onMounted} from 'vue';

const URL = import.meta.env.VITE_API_JAVAURL;
const notify = ref([]);

onMounted(async () => {
  await getAllNotify();
});

const getAllNotify = async () => {
  const notifyAPI = `${URL}hotel/getAllNotify`;
  try {
    const responseH = await axios.get(notifyAPI, {withCredentials: true});
    notify.value = responseH.data;
    // Object.assign(hotelInfo.value, responseH.data);
  } catch (error) {
    console.error("Failed to load notifyAPI: ", error);
  }
}

const changeNotifyState = async (hotelNoticeID) => {
  const setHotelNoticeID = `${URL}hotel/changeNotifyState?hotelNoticeID=${hotelNoticeID}`;
  const res = await axios.get(setHotelNoticeID, {withCredentials: true});
  if (res.status === 200) {
    console.log('setHotelNoticeID: ' + res.data);
    await getAllNotify();
  } else {
    console.error('Failed to fetch setHotelNoticeID:', res.status);
    alert('系統錯誤，請稍後再試')
  }
}

const deleteNotify = async (hotelNoticeID) => {
  const deleteNotifyAPI = `${URL}hotel/deleteNotify?hotelNoticeID=${hotelNoticeID}`;
  const res = await axios.get(deleteNotifyAPI, {withCredentials: true});
  if (res.status === 200) {
    console.log('deleteNotify: ' + res.data);
    // await getAllNotify();
  } else {
    console.error('Failed to fetch deleteNotify:', res.status);
    alert('系統錯誤，請稍後再試')
  }
}
</script>


<style scoped>

</style>