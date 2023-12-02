<template>
  <div class="home">
    <div class="banner">
      <span class="banner-logo">
        <img src="@/assets/homepage_logo.svg" alt="">
      </span>
    </div>
  </div>
  <nav class="navbar navbar-light bg-light">
    <div class="container-fluid">
    </div>
  </nav>

  <div class="container-fluid">
    <a class="navbar-brand"></a>
    <form class="d-flex" @submit.prevent="searchHotels">
      <input v-model="city" class="form-control me-2" type="search" placeholder="輸入城市" aria-label="Search"
             id="city">
      <input v-model="requiredCapacity" class="form-control me-2" type="number" placeholder="1" aria-label="Search"
             id="requiredCapacity">
      <input v-model="startDate" class="form-control me-2" type="date" placeholder="Search" aria-label="Search"
             id="startDate">
      <input v-model="endDate" class="form-control me-2" type="date" placeholder="Search" aria-label="Search"
             id="endDate">
      <button class="btn btn-outline-success" type="submit">Search</button>
    </form>
    <div class="filter-container">
      <label>
        <input v-model="filters.cleaningService" type="checkbox"> 每日打掃
      </label>
      <label>
        <input v-model="filters.expressCheckin" type="checkbox"> 快速入住
      </label>
      <!-- 添加其他复选框，例如： -->
      <label>
        <input v-model="filters.counter24hr" type="checkbox"> 24小時櫃檯服務
      </label>
      <label>
        <input v-model="filters.freeWiFi" type="checkbox"> 免費WI-FI
      </label>
      <label>
        <input v-model="filters.roomCard" type="checkbox"> 電子房卡
      </label>
      <label>
        <input v-model="filters.noSmoking" type="checkbox"> 禁菸
      </label>
      <label>
        <input v-model="filters.petFriendly" type="checkbox"> 寵物友善
      </label>
    </div>
    <div v-if="noHotelsFound">
    </div>
    <div v-else-if="hotels.length > 0">
      <!-- 如果有酒店信息，显示搜索结果 -->


      <ul class="horizontal-list">
        <li v-for="hotel in filteredHotels" :key="hotel.hotelID" class="hotel-card">
          <!-- 显示酒店信息 -->
          <div class="card">
            <div class="card-body">
              <h5 class="card-title">{{ hotel.hotelName }}</h5>
              <p class="card-text">星級: {{ hotel.hotelStar }}</p>
              <div class="star-rating">
  <span v-for="i in parseInt(hotel.hotelStar)" :key="i">
    <span class="fas fa-star" :style="{ color: getStarColor(hotel.hotelStar) }"></span>
  </span>
              </div>

              <!-- 其他酒店信息字段 -->
              <p class="card-text">類型: {{ hotel.hotelType }}</p>

              <p class="card-text">電話: {{ hotel.tel }}</p>
              <p class="card-text">國家: {{ hotel.country }}</p>
              <p class="card-text">城市: {{ hotel.city }}</p>
              <p class="card-text">區域: {{ hotel.region }}</p>
              <p class="card-text">街道: {{ hotel.street }}</p>
              <p class="card-text">郵政編碼: {{ hotel.postalCode }}</p>
              <p class="card-text">狀態: {{ hotel.state ? '開放' : '關閉' }}</p>
              <a href="#roomsDetail" class="btn btn-primary"
                 @click="viewHotelDetails(hotel.hotelID)">查看客房供應狀況</a>
            </div>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>


<script>
import axios from 'axios';
import Swal from 'sweetalert2';

export default {
  data() {
    return {
      city: "",
      requiredCapacity: 1,
      startDate: "",
      endDate: "",
      hotels: [],
      showHotelDetails: false,
      hotelDetails: {},
      noHotelsFound: false,
      filters: {
        cleaningService: false,
        expressCheckin: false,
        counter24hr: false,
        freeWiFi: false,
        roomCard: false,
        noSmoking: false,
        petFriendly: false,
      }
    };
  },
  computed: {
    filteredHotels() {
      return this.hotels.filter(hotel => {
        // 使用筛选条件，例如：
        if (this.filters.counter24hr && !hotel.hotelDetail.counter24hr) {
          return false;
        }
        if (this.filters.freeWiFi && !hotel.hotelDetail.freeWiFi) {
          return false;
        }

        if (this.filters.cleaningService && !hotel.hotelDetail.cleaningService) {
          return false;
        }
        if (this.filters.roomCard && !hotel.hotelDetail.roomCard) {
          return false;
        }
        if (this.filters.noSmoking && !hotel.hotelDetail.noSmoking) {
          return false;
        }
        if (this.filters.petFriendly && !hotel.hotelDetail.petFriendly) {
          return false;
        }
        if (this.filters.expressCheckin && !hotel.hotelDetail.expressCheckin) {
          return false;
        }
        // 添加其他筛选条件
        return true;
      });
    }
  },
  methods: {
    // 根據酒店星級評分返回對應的顏色
    getStarColor(hotelStar) {

      if (hotelStar >= 4.5) {
        return '#FFD700'; // 金黄色
      } else if (hotelStar >= 3.5) {
        return 'red'; // 红色
      } else {
        return 'gray'; // 灰色
      }
    },
    searchHotels() {
      this.showHotelDetails = false;
      axios
          .get(`http://localhost:8080/sjb/search/hotelByDate`, {
            params: {
              city: this.city,
              requiredCapacity: this.requiredCapacity,
              startDate: this.startDate,
              endDate: this.endDate
            }
          })
          .then((response) => {
            if (response.data.length === 0) {
              this.noHotelsFound = true;
              Swal.fire({
                icon: 'error',
                title: '查無此時段飯店',
                text: '抱歉，沒有可用的飯店房間。'
              });
            } else {
              this.hotels = response.data;
              this.noHotelsFound = false;
            }
          })
          .catch((error) => {
            console.error("請求失敗，錯誤：", error);
          });
    },
    viewHotelDetails(hotelID) {
      this.$router.push({
        name: 'hotel-details',
        params: {
          hotelID: hotelID,
        },
      });
    }
  }
};
</script>


<style lang="scss" scoped>
@import "@/assets/all.scss";
@import "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css";

.horizontal-list {
  display: flex;
  flex-wrap: nowrap; /* 不允许换行，使卡片在一行内水平排列 */
  overflow-x: auto; /* 添加水平滚动条以容纳更多卡片 */
}

.horizontal-list li {
  flex: 0 0 calc(25% - 10px); /* 25%是四個卡片的寬度，10px是間距，根據需要調整 */
  margin-right: 10px; /* 可以根據需要調整間距 */
  margin-bottom: 10px; /* 添加底部間距，使第五个卡片进入下一行 */
  list-style: none; /* 去掉列表標記 */
}

.hotel-card {
  flex: 1; /* 卡片均匀占据可用水平空间 */
  margin-right: 10px; /* 可以根据需要调整间距 */
}

.banner {
  width: 100%;
  height: 50vh;
  position: relative;
  background: bottom url('@/assets/homepage_banner.svg');
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
}

.filter-container {
  border: 2px solid #ccc; /* 添加 2px 宽度、灰色边框 */
  border-radius: 10px;
  padding: 10px; /* 添加内边距以增加边框和复选框之间的距离 */
  margin: 20px 0; /* 上下各添加 20px 的外边距 */
}


</style>