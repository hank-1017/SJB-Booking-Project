<template>
  <div class="home">
    <div class="banner">
      <span class="banner-logo">
        <img src="@/assets/homepage_logo.svg" alt="">
      </span>
    </div>
  </div>
  <div>

    <br>
    <div>
      <h3 class="hotel-name">飯店名稱: {{ hotelDetails.hotelName }}
        <button class="view-button justify-content-end" @click="hotelChat">聯繫飯店</button>
      </h3>
    </div>
  </div>
  <ul class="room-list">
    <li v-for="room in hotelDetails.rooms" :key="room.productID" class="room-card">
      <div class="card" style="height: 550px;">
        <div class="card-body">
          <div>
            <!-- <p>{{ room.roomPictures[0].imgID }}</p> -->
            <!-- <li> -->
            <!-- <img :src="pictureURL + room.roomPictures[0].imgID" alt="Room Image"></li> -->
            <img v-if="room.roomPictures.length > 0 && room.roomPictures[0].imgID"
              :src="pictureURL + room.roomPictures[0].imgID" alt="Room Image">
          </div>
          <div class="description-container" style="padding: 5px;">
            <h5 class="card-title room-name">{{ room.productName }}</h5>
            <p class="card-text">價格: {{ room.price }}</p>
            <p class="card-text">入住時間: {{ room.checkInTime }}</p>
            <br><br><br>
            <p class="card-text description" style="margin-bottom: 10px;">{{ room.productDescription }}</p>

            <button @click="viewRoomDetails(room.productID)" class="view-button">瞭解更多</button>

            <img src="@/assets/img/add-to-cart.png" alt="" class="addToCart" @click="addToCart(room.productID)">

          </div>
        </div>
      </div>
    </li>
  </ul>
</template>


<script setup>
import axios from 'axios';
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import Swal from "sweetalert2"; // 导入 useRoute 和 useRouter

const hotelDetails = ref([]); // 用于存储酒店详细信息
const route = useRoute(); // 访问当前路由信息
const router = useRouter(); // 访问路由对象

const hotelID = route.params.hotelID;
const startDate = route.params.startDate;
const endDate = route.params.endDate;

let formData = new FormData();
formData.append('checkInDate', startDate);
formData.append('checkOutDate', endDate);

const URL = import.meta.env.VITE_API_JAVAURL;
const pictureURL = `${URL}room/images/`;

// 使用 Axios 获取酒店详细信息
onMounted(async () => {

  await axios.get(`${URL}search/details/${hotelID}`)
    .then((response) => {
      hotelDetails.value = response.data;
      formData.append('hotelID', hotelDetails.value.hotelID);

      console.log(hotelDetails.value.rooms);
      for (let room of hotelDetails.value.rooms) {

        if (room.productDescription && room.productDescription.length > 80) {
          // 截取前 45 个字符并添加省略号
          room.productDescription = room.productDescription.substring(0, 80) + '...';
        }
      }

    
    }
    )
    .catch((error) => {
      console.error("請求失敗，錯誤：", error);
    });
});

const viewRoomDetails = (roomID) => {
  router.push({
    name: 'roomsDetail',
    params: {
      'roomID': roomID,
      'startDate': startDate,
      'endDate': endDate
    }
  });
};

const addToCart = async (productID) => {

  // 清空後再放值
  formData.delete('productID');
  formData.delete('quantity');

  formData.append('productID', productID);
  formData.append('quantity', 1);

  try {

    const addCartResponse = await axios.post(`${URL}cart/addcart`, formData, { withCredentials: true });
    if (addCartResponse.status == 200) {
      productID = '';
      alert('已加入購物車');
    }
  } catch (error) {
    console.log(error);
    if (error.response.status === 500) {
      // 服务器错误，可以访问 error.response.data 获取详细信息
      alert(`${error.response.data}`);

      router.push({ name: 'login' });
    } else {
      alert('發生錯誤，請稍後嘗試');
    }
  }

}

const hotelChat = () => {
  Swal.fire({
    title: '您將開啟與商家的即時訊息',
    text: "",
    icon: 'info',
    showCancelButton: true,
    confirmButtonColor: '#3085d6',
    cancelButtonColor: '#d33',
    confirmButtonText: '確認',
    cancelButtonText: '取消'
  }).then(async (result) => {
    if (result.isConfirmed) {
      sessionStorage.setItem('HotelMessageID', route.params.hotelID)
      sessionStorage.setItem('HotelName', hotelDetails.value.hotelName)
      await saveMessage();
      // window.location.href = '/message';
      await router.push('/message');
    }
  })

}

const saveMessage = async () => {
  // console.log("call saveMessage: " + inputMessage.value.content)
  const formData = new FormData();
  formData.append("content", '已開啟聊天');
  formData.append("loginID", sessionStorage.getItem('CustomerMessageID'));
  formData.append("hotelID", route.params.hotelID);
  formData.append("sender", 'U' + sessionStorage.getItem('CustomerMessageID'));

  const saveMessageAPI = `${URL}message/saveMessage`;
  const response = await axios.post(saveMessageAPI, formData, {withCredentials: true});
  if (response.status === 200) {
    console.log("saveMessage success")
  }
}
</script>


<style lang="scss" scoped>
@import "@/assets/all.scss";
@import "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css";

/* 其他样式规则 */
ul {
  list-style: none;
  /* 移除列表元素的标记 */
}

.card-text {
  width: 100%;
  /* 设置文本宽度为 100% */
  word-wrap: break-word;
  white-space: normal;
}

.description-container {
  background-color: transparent;
  /* 设置默认背景颜色为透明 */
  color: black;
  /* 设置默认文字颜色为黑色 */
  transition: background-color 0.3s, color 0.3s, transform 0.3s;
  /* 添加过渡效果 */
  border-radius: 10px;
  /* 使背景以圆弧方式显示 */

  /* 使用:hover伪类更改背景颜色、文字颜色和上移 */
  &:hover {
    background-color: #0077A2;
    /* 设置悬停时背景颜色为莫迪蓝色 */
    color: white;
    /* 设置悬停时文字颜色为白色 */
    transform: translateY(-10px);

    /* 上移10像素 */
    p {
      color: white;
    }

    button {
      background-color: white;
      color: #0077A2;
    }
  }
}

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
}

.room-card {
  background-color: #0077A2;
  /* 设置卡片的背景颜色 */
  color: white;
  /* 设置文本颜色为白色 */
  margin: 20px 10px;
  /* 设置上下和左右间隔 */
  width: calc(33.33% - 20px);
  /* 设置卡片宽度并考虑到左右间隔 */
  float: left;
  /* 让卡片浮动到左侧 */
  box-sizing: border-box;
  /* 让边框和内边距包含在宽度内 */
  border-radius: 10px;
}

.room-name {
  color: #FFD700;
  /* 设置房间名称的颜色为金黄色 */
}

.room-list {
  list-style: none;
  /* 移除列表元素的标记 */
  overflow: auto;
  /* 创建包含浮动元素的块级格式上下文 */
}

.view-button {
  background-color: #0077A2;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
}

img {
  border-radius: 10px;
  width: 500px;
  height: 200px;
}

.addToCart {
  width: 40px;
  height: auto;
  margin: 5px 10px;
  cursor: pointer;
  // position: absolute; 
  // bottom: 0; 
  // right: 0;
}

/* 其他样式规则 */
</style>