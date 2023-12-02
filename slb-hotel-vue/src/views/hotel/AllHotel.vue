<template>
  <div class="container-fluid pt-4 px-4">
    <div class="row g-4">
      <div class="col-sm-12">
        <div class="bg-light rounded h-100 p-4">
          <h3 class="mb-4">住宿資料</h3>
          <div class="table-responsive">
            <table class="table">
              <thead>
              <tr class="text-primary">
                <th scope="col">#</th>
                <th scope="col">飯店</th>
                <th scope="col">飯店類型</th>
                <th scope="col">設施服務</th>
                <th scope="col">詳細資料</th>
                <th scope="col">飯店狀態
                </th>
                <th>啟用/停用</th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="(hotel, index) in hotels" :key="index">

                <th scope="row">{{ index + 1 }}</th>

                <td>
                  <span>{{ hotel.hotelName }}</span>
                  <input type="hidden" name="hotelName" :value="hotel.hotelName"/>
                  <input type="hidden" name="hotelID" :value="hotel.hotelID"/>
                </td>
                <td>
                  <span>{{ hotel.hotelType }}</span>
                  <input type="hidden" name="hotelType" :value="hotel.hotelType"/>
                </td>
                <td>
                  <input type="submit" value="飯店設施服務" class="btn btn-outline-primary btn-sm">
                </td>
                <td>
                  <button class="btn btn-outline-secondary btn-sm" @click="goToHotelDetail(hotel.hotelID)">詳細資料
                  </button>
                </td>
                <td>
                  <!-- <div th:if="${hotel.state}" id="openhotel" class="btn btn-success">營業中</div>
                  <div th:if="${!hotel.state}" id="stophotel" class="btn btn-danger">
                      停業中</div> -->
                  <div th:if="${hotel.state}" class="status-button btn btn-success btn-sm">營業中</div>
                  <div th:if="${!hotel.state}" class="status-button btn btn-danger btn-sm">停業中</div>

                  <!-- <div  id="openhotel2" class="btn btn-success" style="display: none;">營業中</div>
                  <div  id="stophotel2" class="btn btn-danger" style="display: none;">
                      停業中</div> -->

                </td>

                <td>
                  <!--                  <form class="checkstate" method="post">-->
                  <input type="hidden" name="hotelID" th:value="${hotel.hotelID}">
                  <div th:if="${hotel.state}">
                    <input type="hidden" name="state" value="false" id="stateInput">
                    <button type="submit" class="btn btn-danger btn-sm" id="falsebutton">停用</button>
                  </div>
                  <div th:if="${!hotel.state}">
                    <input type="hidden" name="state" value="true" id="stateInput">
                    <button type="submit" class="btn btn-success btn-sm" id="truebutton">啟用</button>
                  </div>
                  <!--                  </form>-->
                </td>

                <!--                <td>-->
                <!--                  <input type="hidden" name="_method" value="DELETE"/>-->
                <!--                  <input type="hidden" name="hotelID" :value="hotel.hotelID"/>-->
                <!--                  &lt;!&ndash;              <input class="delete" type="submit" value="刪除" name="delete" />&ndash;&gt;-->
                <!--                  <button class="btn btn-danger btn-sm" @click="deleteHotel(hotel)">刪除</button>-->
                <!--                </td>-->

              </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref, onMounted} from 'vue';
import {useRouter} from 'vue-router';
import axios from 'axios';

// Define a ref for storing the list of hotels
const hotels = ref([]);
const URL = import.meta.env.VITE_API_JAVAURL;

const router = useRouter();
const goToAbout = () => {
  // 使用 router.push 进行页面跳转
  router.push('/hotel/hotelDetail');
};

const loadHotels = async () => {
  try {
    const responseH = await axios.post(`${URL}hotel/getAllHotel`, null, {withCredentials: true});

    if (responseH.status === 200) {
      hotels.value = responseH.data;
      console.log(hotels.value);
    } else {
      console.error('Failed to fetch hotel data:', responseH.status);
    }
  } catch (error) {
    console.error('An error occurred:', error);
  }
};

const goToHotelDetail = async (hotelID) => {
  try {
    const response = await axios.get(`${URL}hotel/findHotel`, {
      withCredentials: true,
    });

    if (response.status === 200) {
      console.log('goToHotelDetail: ' + response.data);
      goToAbout();
    } else {
      console.error('Failed to fetch hotel detail:', response.status);
    }
  } catch (error) {
    console.error('An error occurred:', error);
  }
};

const deleteHotel = async (hotel) => {
  try {
    const response = await axios.delete(`${URL}hotel/deleteHotel`, {
      data: {hotelID: hotel.hotelID},
      withCredentials: true,
    });

    if (response.status === 200) {
      // 处理删除成功的逻辑
      loadHotels(); // 刷新酒店数据
    } else {
      console.error('Failed to delete hotel:', response.status);
    }
  } catch (error) {
    console.error('An error occurred:', error);
  }
};

onMounted(() => {
  loadHotels();
});
</script>

