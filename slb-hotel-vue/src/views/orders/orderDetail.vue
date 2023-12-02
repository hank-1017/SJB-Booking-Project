<template>
  <div class="home">
    <div class="banner">
      <span class="banner-logo">
        <img src="@/assets/homepage_logo.svg" alt="" />
      </span>
    </div>
  </div>
  <div class="order-detail">
  <h1>訂單明細 </h1>
  <div v-if="orders">
    <p>訂單時間: {{ formatOrderTime(orders.orderTime) }}</p>

    <div v-for="orderDetail in orders.orderDetails" :key="orderDetail.detailID" class="order-detail-item">
      <p>姓名: {{ orderDetail.orderName }}</p>
      <p>電話: {{ orderDetail.orderPhone }}</p>

      <!-- 在這裡添加更多訂單明細屬性 -->

      <div v-for="room in orders.rooms" :key="room.productID" class="room-item">
         <!-- 僅顯示第一張圖片 -->
        <img :src="pictureURL + room.roomPictures[0].imgID" :alt="room.productName" />
        <p>房間名稱: {{ room.productName }}</p>
        <p>房間價格: ${{ room.price }}</p>
        <p>人數: {{ orderDetail.numPeople }}</p>
        <p>備註: {{ orderDetail.comment }}</p>

      </div>

    </div>
    <!-- 在這裡添加更多訂單明細 -->
  </div>
</div>


</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';


const URL = import.meta.env.VITE_API_JAVAURL;
const pictureURL = `${URL}room/images/`;

const props = defineProps({
  orderID: String,
});

const orders = ref([]);

onMounted(() => {
  fetchOrders(props.orderID, orders);
});

const fetchOrders = async (orderID, orders) => {
  try {
    const response = await axios.get(`http://localhost:8080/sjb/orders/get/${orderID}`);
    orders.value = response.data;
  } catch (error) {
    console.error('獲取訂單時發生錯誤:', error);
  }
};

const formatOrderTime = (rawOrderTime) => {
  if (rawOrderTime) {
    const orderTime = new Date(rawOrderTime);
    if (!isNaN(orderTime)) {
      return orderTime.toISOString().split('T')[0] + ' ' + orderTime.toTimeString().split(' ')[0];
    } else {
      return '無效的日期時間';
    }
  } else {
    return '日期時間未提供';
  }
};
</script>

<style scoped lang="scss">
.banner {
  width: 100%;
  height: 50vh;
  position: relative;
  background: bottom url('@/assets/img/index/taiwan-home2.jpg');
  background-size: cover;
  .banner-logo {
    position: absolute;
    top: 35%;
    left: 50%;
    transform: translate(-50%, -50%);
    max-width: 80%;
    img {
      width: 100%;
    }
  }
}.order-detail {
  h1 {
    font-size: 24px;
    margin-bottom: 20px;
  }

  p {
    font-size: 16px;
  }

  .order-detail-item {
    margin-top: 20px;
    padding: 10px;
    border: 1px solid #ccc;

    .room-item {
      margin-top: 20px;
      padding: 10px;
      border: 1px solid #eee;
      display: flex;
      align-items: center;

      p {
        flex: 1;
        margin: 0;
      }

      img {
        max-width: 100px;
        max-height: 100px;
        object-fit: cover;
        margin-left: 20px;
        border-radius: 10px;
        margin-right: 50px;
      }
    }
  }
}

/* 添加更多樣式，根據需要自定義 */
</style>
