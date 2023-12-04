<template>
  <div class="home">
   
  </div>
    <div>
      <div class="carousel slide" data-bs-ride="carousel" id="roomCarousel">
    <div class="carousel-indicators">
      <button
        v-for="(picture, index) in roomDetails.roomPictures"
        :key="index"
        type="button"
        :data-bs-target="'#roomCarousel'"
        :data-bs-slide-to="index"
        :class="{ active: index === 0 }"
        :aria-label="'Slide ' + (index + 1)"
      ></button>
    </div>
    <div class="carousel-inner">
      <div
          v-for="(picture, index) in roomDetails.roomPictures"
          :key="index"
          class="carousel-item"
          :class="{ active: index === 0 }"
        >
        <img
          :src="pictureURL + picture.imgID"
          class="d-block w-100 fixed-image-size"
          :alt="'Slide ' + (index + 1)"
        />
      </div>
      
    </div>
    <button class="carousel-control-prev no-style-button" type="button" data-bs-target="#roomCarousel" data-bs-slide="prev">
  <span class="carousel-control-prev-icon" aria-hidden="true"></span>
  <span class="visually-hidden">Previous</span>
</button>
<button class="carousel-control-next no-style-button" type="button" data-bs-target="#roomCarousel" data-bs-slide="next">
  <span class="carousel-control-next-icon" aria-hidden="true"></span>
  <span class="visually-hidden">Next</span>
</button>
  </div>
    
  </div>
  <div class="room-content">
      <div class="room-info">
        <div class="info-amenities">
          <ul>
            <div class="amenity">
            <img src="@/assets/icon_Air-Conditioner.svg" alt="">Air-Conditioner
          </div>
          <div class="amenity">
            <img src="@/assets/icon_bath.svg" alt="">Bath
          </div>
          <div class="amenity">
            <img src="@/assets/icon_bed.svg" alt="">Bed</div>
            <div class="amenity">
            <img src="@/assets/icon_Breakfast.svg" alt="">Breakfast</div>
            <div class="amenity">
            <img src="@/assets/icon_Child-Friendly.svg" alt="">Child-Friendly</div>
            <div class="amenity">
            <img src="@/assets/icon_Mini-Bar.svg" alt="">Mini-Bar</div>
            <div class="amenity">
            <img src="@/assets/icon_Pet-Friendly.svg" alt="">Pet-Friendly</div>
            <div class="amenity">
            <img src="@/assets/icon_Wi-Fi.svg" alt="">Wi-Fi</div>
       
          </ul>
        </div>
    <h2>Room Details</h2>

    <div class="room-info">
      <h5>Room Name: {{ roomDetails.productName }}</h5>
      <p>Price: ${{ roomDetails.price }}</p>
      <!-- 其他房间属性 -->

      <div class="date-picker">
        <label for="check-in-date">Check-in Date:</label>
        <input type="date" id="check-in-date" v-model="pickDate[0]" @change="calculateTotalPrice" />
      </div>

      <div class="date-picker">
        <label for="check-out-date">Check-out Date:</label>
        <input type="date" id="check-out-date" v-model="pickDate[1]" @change="calculateTotalPrice" />
      </div>
    </div>

    <div class="price-block">
      <p>Total Price: ${{ totalPrice }}</p>
      <button @click="navigateToCreateOrder">下單</button>
    </div>
  </div>
  </div>
</template>

<script setup>
import axios from 'axios';
import { ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const route = useRoute();
const startDate = route.params.startDate;
const endDate = route.params.endDate;

const roomDetails = ref({});
const pickDate = ref([startDate, endDate]);
const totalPrice = ref(0);
const URL = import.meta.env.VITE_API_JAVAURL;
const pictureURL = `${URL}room/images/`;

const calculateTotalPrice = () => {
  if (pickDate.value[0] && pickDate.value[1]) {
    const startDate = new Date(pickDate.value[0]);
    const endDate = new Date(pickDate.value[1]);
    const timeDiff = endDate.getTime() - startDate.getTime();
    const nightCount = Math.ceil(timeDiff / (1000 * 3600 * 24));
    totalPrice.value = nightCount * roomDetails.value.price;
  }
};

const navigateToCreateOrder = () => {
  router.push({
    name: 'createOrders',
    params: {
      productID: roomDetails.value.productID,
      price: totalPrice.value,
      startDate: pickDate.value[0],
      endDate: pickDate.value[1],
      hotelID: roomDetails.value.hotelID,
    },
  });
};

const router = useRouter();
const roomID = router.currentRoute.value.params.roomID;

axios
  .get(`http://localhost:8080/sjb/search/room/getRoomById`, {
    params: {
      productID: roomID,
    },
  })
  .then((response) => {
    roomDetails.value = response.data;
    calculateTotalPrice();
  })
  .catch((error) => {
    console.error('請求失敗，錯誤：', error);
  });
</script>

<style scoped lang="scss">

.info-amenities ul {
  width: 100vw; /* 100% 页面宽度 */
  list-style: none;
  background-color: rgba(248, 248, 248, 0.71);
  display: flex;
  flex-wrap: wrap;
  padding: 2rem 0.5rem;
  overflow: hidden;
}

.amenity {
  display: flex;
  align-items: center;
  margin-right: 10px; /* 调整间距大小 */
}
body {
  margin: 0; /* 去掉 body 的默认 margin */
}

/* 如果有其他容器，确保它们也不受限制 */
.container {
  max-width: none;
}
.room-details {
  padding: 20px;
  background-color: #f0f0f0;
}

 .room-content {
    display: flex;
    max-width: 1200px;
    margin: 0 auto;
    padding: 1.2rem 1.2rem;
    .room-info {
      width: 55%;
      .info-title {
        width: 100%;
      }
      .info-short {
        display: flex;
        flex-wrap: wrap;
        &>:nth-child(-n+3) {
          margin: 0 1.6rem 0 0;
        }
        img {
          vertical-align: middle;
        }
      }
      .info-short, .body-description, .info-body, .info-amenities{
        margin-bottom: 1.8rem;
      }
    }
    .room-booking {
      width: 45%;
    }
  }

.price-block {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: white;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
  width: 60%;
}

button {
  background-color: #0077A2;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
}

.date-picker {
  display: flex;
  flex-direction: column;
  margin-bottom: 20px;
}

.date-picker label {
  margin-bottom: 5px;
}

.date-picker input[type="date"] {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}
.carousel-control-prev {
  background-color:black!important;
}
.no-style-button {
  background-color: transparent; /* 设置为透明背景色 */
  color: initial; /* 恢复默认文字颜色 */
  border: none;
  padding: 0; /* 去掉按钮内边距 */
  cursor: pointer;
  border-radius: 0; /* 去掉按钮的圆角 */
}

.no-style-button:focus {
  outline: none; /* 去掉按钮的焦点样式 */
}
.fixed-image-size {
  width: 800px; /* 设置为所需的宽度 */
  height: 600px; /* 设置为所需的高度 */
}
</style>
