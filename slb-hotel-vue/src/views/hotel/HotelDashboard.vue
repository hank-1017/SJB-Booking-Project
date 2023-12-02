<template>
  <!-- Sale & Revenue Start -->
  <div class="container-fluid pt-4 px-4">
    <div class="row g-4">

      <div class="col-sm-6 col-xl-3">
        <div class="bg-light rounded d-flex align-items-center justify-content-between p-4">
          <i class="fa fa-chart-line fa-3x text-primary"></i>
          <div class="ms-3">
            <p class="mb-2">今日入住人數</p>
            <h6 class="mb-0">$1234</h6>
          </div>
        </div>
      </div>
      <div class="col-sm-6 col-xl-3">
        <div class="bg-light rounded d-flex align-items-center justify-content-between p-4">
          <i class="fa fa-chart-bar fa-3x text-primary"></i>
          <div class="ms-3">
            <p class="mb-2">總營收</p>
            <h6 class="mb-0">$1234</h6>
          </div>
        </div>
      </div>
      <div class="col-sm-6 col-xl-3">
        <div class="bg-light rounded d-flex align-items-center justify-content-between p-4">
          <i class="fa fa-chart-area fa-3x text-primary"></i>
          <div class="ms-3">
            <p class="mb-2">今日營收</p>
            <h6 class="mb-0">$1234</h6>
          </div>
        </div>
      </div>
      <div class="col-sm-6 col-xl-3">
        <div class="bg-light rounded d-flex align-items-center justify-content-between p-4">
          <i class="fa fa-chart-area fa-3x text-primary"></i>
          <div class="ms-3">
            <p class="mb-2">今日營收</p>
            <h6 class="mb-0">$1234</h6>
          </div>
        </div>
      </div>

    </div>
  </div>
  <!-- Sale & Revenue End -->


  <!-- Sales Chart Start -->
  <div class="container-fluid pt-4 px-4">
    <div class="row g-4">
      <div class="col-sm-12 col-xl-6">
        <div class="bg-light text-center rounded p-4">
          <div class="d-flex align-items-center justify-content-between mb-4">
            <h6 class="mb-0">Worldwide Sales</h6>
            <a href="">Show All</a>
          </div>
          <canvas id="worldwide-sales"></canvas>
        </div>
      </div>
      <div class="col-sm-12 col-xl-6">
        <div class="bg-light text-center rounded p-4">
          <div class="d-flex align-items-center justify-content-between mb-4">
            <h6 class="mb-0">Salse & Revenue</h6>
            <a href="">Show All</a>
          </div>
          <canvas id="salse-revenue"></canvas>
        </div>
      </div>
    </div>
  </div>
  <!-- Sales Chart End -->


  <!-- Recent Sales Start -->
  <div class="container-fluid pt-4 px-4">
    <div class="bg-light text-center rounded p-4">
      <div class="d-flex align-items-center justify-content-between mb-4">
        <h6 class="mb-0">近期訂單</h6>
        <router-link to="/hotel/orderManagement">
          <a href="">顯示全部訂單</a>
        </router-link>
      </div>
      <div class="table-responsive">
        <table class="table text-start align-middle table-bordered table-hover mb-0">
          <thead>
          <tr class="text-dark">
            <th scope="col"><input class="form-check-input" type="checkbox"></th>
            <th scope="col">Date</th>
            <th scope="col">Invoice</th>
            <th scope="col">Customer</th>
            <th scope="col">Amount</th>
            <th scope="col">Status</th>
            <th scope="col">Action</th>
          </tr>
          </thead>
          <tbody>
          <tr>
            <td><input class="form-check-input" type="checkbox"></td>
            <td>01 Jan 2045</td>
            <td>INV-0123</td>
            <td>Jhon Doe</td>
            <td>$123</td>
            <td>Paid</td>
            <td><a class="btn btn-sm btn-primary" href="">Detail</a></td>
          </tr>
          <tr>
            <td><input class="form-check-input" type="checkbox"></td>
            <td>01 Jan 2045</td>
            <td>INV-0123</td>
            <td>Jhon Doe</td>
            <td>$123</td>
            <td>Paid</td>
            <td><a class="btn btn-sm btn-primary" href="">Detail</a></td>
          </tr>
          <tr>
            <td><input class="form-check-input" type="checkbox"></td>
            <td>01 Jan 2045</td>
            <td>INV-0123</td>
            <td>Jhon Doe</td>
            <td>$123</td>
            <td>Paid</td>
            <td><a class="btn btn-sm btn-primary" href="">Detail</a></td>
          </tr>
          <tr>
            <td><input class="form-check-input" type="checkbox"></td>
            <td>01 Jan 2045</td>
            <td>INV-0123</td>
            <td>Jhon Doe</td>
            <td>$123</td>
            <td>Paid</td>
            <td><a class="btn btn-sm btn-primary" href="">Detail</a></td>
          </tr>
          <tr>
            <td><input class="form-check-input" type="checkbox"></td>
            <td>01 Jan 2045</td>
            <td>INV-0123</td>
            <td>Jhon Doe</td>
            <td>$123</td>
            <td>Paid</td>
            <td><a class="btn btn-sm btn-primary" href="">Detail</a></td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
  <!-- Recent Sales End -->


  <!-- Widgets Start -->
  <div class="container-fluid pt-4 px-4">
    <div class="row g-4">

      <div class="col-sm-12 col-md-6 col-xl-4">
        <div class="h-100 bg-light rounded p-4">
          <div class="d-flex align-items-center justify-content-between mb-2">
            <h6 class="mb-0">Messages</h6>
            <a href="">Show All</a>
          </div>
          <div class="d-flex align-items-center border-bottom py-3">
            <img class="rounded-circle flex-shrink-0" :src=userImg alt=""
                 style="width: 40px; height: 40px;">
            <div class="w-100 ms-3">
              <div class="d-flex w-100 justify-content-between">
                <h6 class="mb-0">Jhon Doe</h6>
                <small>15 minutes ago</small>
              </div>
              <span>Short message goes here...</span>
            </div>
          </div>
          <div class="d-flex align-items-center border-bottom py-3">
            <img class="rounded-circle flex-shrink-0" :src=userImg alt=""
                 style="width: 40px; height: 40px;">
            <div class="w-100 ms-3">
              <div class="d-flex w-100 justify-content-between">
                <h6 class="mb-0">Jhon Doe</h6>
                <small>15 minutes ago</small>
              </div>
              <span>Short message goes here...</span>
            </div>
          </div>
          <div class="d-flex align-items-center border-bottom py-3">
            <img class="rounded-circle flex-shrink-0" :src=userImg alt=""
                 style="width: 40px; height: 40px;">
            <div class="w-100 ms-3">
              <div class="d-flex w-100 justify-content-between">
                <h6 class="mb-0">Jhon Doe</h6>
                <small>15 minutes ago</small>
              </div>
              <span>Short message goes here...</span>
            </div>
          </div>
          <div class="d-flex align-items-center pt-3">
            <img class="rounded-circle flex-shrink-0" :src=userImg alt=""
                 style="width: 40px; height: 40px;">
            <div class="w-100 ms-3">
              <div class="d-flex w-100 justify-content-between">
                <h6 class="mb-0">Jhon Doe</h6>
                <small>15 minutes ago</small>
              </div>
              <span>Short message goes here...</span>
            </div>
          </div>
        </div>
      </div>


      <div class="col-sm-12 col-md-6 col-xl-4">
        <div class="h-100 bg-light rounded p-4">
          <div class="d-flex align-items-center justify-content-between mb-4">
            <h6 class="mb-0">Calender</h6>
            <a href="">Show All</a>
          </div>
          <div id="calender"></div>
        </div>
      </div>

<!--    通知欄位-->
      <div class="col-sm-12 col-md-6 col-xl-4">
        <div class="h-100 bg-light rounded p-4">
          <div class="d-flex align-items-center justify-content-between mb-4">
            <h6 class="mb-0">通知</h6>
            <router-link to="/hotel/hotelNotify">
              <a href="">顯示全部通知</a>
            </router-link>
          </div>

          <div v-for="{ hotelNoticeID,content, status,noticeTime} in notify">
            <div class="alert" :class=" {'alert-success': status === '未讀'},{'alert-secondary': status === '已讀'}"
                 role="alert">

                <a href="#" class="dropdown-item" @click="changeNotifyState(hotelNoticeID)">
                  <div class="d-flex justify-content-between">
                    <div><h6 class="fw-normal mb-0"> <i class="fa fa-exclamation-circle me-2"></i>{{ content }}</h6> <small>{{ noticeTime }}</small></div>
                    <h6 class="fw-normal mb-0">{{ status }}</h6>
                  </div>
                </a>

            </div>
          </div>

        </div>
      </div>


    </div>
  </div>
  <!-- Widgets End -->

</template>

<script setup>
import {onMounted, onUnmounted, ref} from "vue";
import axios from "axios";
import userImg from '@/assets/img/user.png'
const URL = import.meta.env.VITE_API_JAVAURL;
const notify = ref([]);

const getAllNotify = async () => { //取得前4筆通知
  const notifyAPI = `${URL}hotel/getTop4Notify`;
  try {
    const responseH = await axios.get(notifyAPI, {withCredentials: true});
    console.log('getAllNotify: ' + responseH.data)
    notify.value = responseH.data;
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

onMounted(async () => {
  // 設定計時器每30秒呼叫一次getAllNotify
  setInterval(getAllNotify, 30000);
  await getAllNotify();
});

onUnmounted(() => {
  clearInterval(getAllNotify);
})
</script>

