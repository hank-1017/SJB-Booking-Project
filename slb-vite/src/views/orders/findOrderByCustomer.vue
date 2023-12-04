<template>
  <div class="home">
    <div class="banner">
      <span class="banner-logo">
        <img src="@/assets/homepage_logo.svg" alt="">
      </span>
    </div>
  </div>
  <div>
    <h1>訂單列表</h1>
    <div v-if="loading">
      <div id="loading-swal"></div>
    </div>
    <div v-else>
      <div v-if="userLoggedIn">
        <table class="table">
          <thead>
            <tr>
              <th scope="col">訂單編號</th>
              <th scope="col">姓名</th>
              <th scope="col">電話</th>
              <th scope="col">狀態</th>
              <th scope="col">訂單明細</th>
              <th scope="col">取消</th>
              <th scope="col">付款</th>
            </tr>
          </thead>
          <tbody v-for="order in paginatedOrders" :key="order.orderID">

            <tr v-for="detail in order.orderDetails" :key="detail.detailID">
              <th scope="row">{{ detail.detailID }}</th>
              <td>{{ detail.orderName }}</td>
              <td>{{ detail.orderPhone }}</td>
              <td>
                {{ order.orderStatus }}
              </td>
              <td><button @click="navigateToOrderDetail(order.orderID)">訂單明細</button></td>
              <td><button @click="cancelOrder(order.orderID)" :disabled="order.orderStatus === '已取消'">取消訂單</button></td>
              <td>
                <button @click="goToPaymentPage"
                  :disabled="detail.payment === 'cash' || order.orderStatus.includes('已付款') || order.orderStatus === '已取消'">前往付款
                </button>
              </td>
            </tr>
          </tbody>
        </table>
        <div class="pagination">
          <button @click="prevPage" :disabled="currentPage === 1">上一頁</button>
          <span>第 {{ currentPage }} 頁 / 共 {{ totalPages }} 頁</span>
          <button @click="nextPage" :disabled="currentPage === totalPages">下一頁</button>
        </div>
      </div>
      <div v-else>
        <p>User not logged in</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router'; // 导入router

import Swal from 'sweetalert2';

const router = useRouter(); // 创建router对象
const orders = ref([]);
const loading = ref(true);
const userLoggedIn = ref(false);

const currentPage = ref(1);
const itemsPerPage = 10;

const paginatedOrders = computed(() => {
  const startIndex = (currentPage.value - 1) * itemsPerPage;
  const endIndex = startIndex + itemsPerPage;
  return orders.value.slice(startIndex, endIndex);
});

const nextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value += 1;
  }
};

const prevPage = () => {
  if (currentPage.value > 1) {
    currentPage.value -= 1;
  }
};

const totalPages = computed(() => Math.ceil(orders.value.length / itemsPerPage));

const URL = import.meta.env.VITE_API_JAVAURL;
const pictureURL = `${URL}room/images/`;

const goToPaymentPage = () => {
  window.location.href = 'http://localhost:8080/sjb/orders/goECPay';
};

const cancelOrder = async (orderID) => {
  const result = await Swal.fire({
    title: '確認取消訂單?',
    text: '您確定要取消此訂單嗎？',
    icon: 'warning',
    showCancelButton: true,
    confirmButtonText: '確定',
    cancelButtonText: '取消',
  });

  if (result.isConfirmed) {
    // 顯示 SweetAlert 的退款中 loading 彈窗
    const loadingAlert = Swal.fire({
      title: '退款中',
      html: '<i class="fas fa-spinner fa-spin"></i> 請稍候...', // 使用FontAwesome圖標
      showConfirmButton: false,
    });

    try {
      // 模擬異步操作（這裡使用 setTimeout 來模擬）
      setTimeout(async () => {
        await axios.patch(`http://localhost:8080/sjb/orders/cancel/${orderID}`, { withCredentials: true });
        loadingAlert.close(); // 關閉 loading 彈窗
        Swal.fire('取消成功', '訂單已取消', 'success');
        // window.location.reload();

        try {
          const response = await axios.get('http://localhost:8080/sjb/orders/ordersByCustomerID', { withCredentials: true });
          orders.value = response.data;
          userLoggedIn.value = true;
        } catch (error) {
          console.error('Error fetching data:', error);
          userLoggedIn.value = false;
        } finally {
          loading.value = false;
        }
        
      }, 5000); // 等待5秒
    } catch (error) {
      loadingAlert.close(); // 关闭 loading 弹窗
      console.error('Error canceling order:', error);
      Swal.fire('取消失敗', '取消訂單時出現錯誤', 'error');
    }
  }
};

const navigateToOrderDetail = (orderID) => {
  router.push({ name: 'orderDetail', params: { orderID } });
};

onMounted(async () => {
  try {
    const response = await axios.get('http://localhost:8080/sjb/orders/ordersByCustomerID', { withCredentials: true });
    orders.value = response.data;
    userLoggedIn.value = true;
  } catch (error) {
    console.error('Error fetching data:', error);
    userLoggedIn.value = false;
  } finally {
    loading.value = false;
  }
});
</script>


<style scoped lang="scss">
@import "@/assets/all.scss";
@import "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css";

.horizontal-list {
  display: flex;
  flex-wrap: nowrap;
  /* 不允許换行，使卡片在一行内水平排列 */
  overflow-x: auto;
  /* 添加水平滾動條以容納更多卡片 */
}

.horizontal-list li {
  flex: 0 0 calc(25% - 10px);
  /* 25%是四個卡片的寬度，10px是間距，根據需要調整 */
  margin-right: 10px;
  /* 可以根據需要調整間距 */
  margin-bottom: 10px;
  /* 添加底部間距，使第五個卡片進入下一行 */
  list-style: none;
  /* 去掉列表標記 */
}

.hotel-card {
  flex: 1;
  /* 卡片均匀佔據可用水平空間 */
  margin-right: 10px;
  /* 可以根據需要調整間距 */
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

.filter-container {
  border: 2px solid #ccc;
  /* 添加 2px 寬度、灰色邊框 */
  border-radius: 10px;
  padding: 10px;
  /* 添加内邊距以增加邊框和複選框之間的距離 */
  margin: 20px 0;
  /* 上下各添加 20px 的外邊距 */
}

button {
  background-color: #0077A2;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
}

button:disabled {
  background-color: #ccc;
  /* 灰色背景色 */
  color: #999;
  /* 灰色文本颜色 */
  cursor: not-allowed;
  /* 鼠標樣式設置为禁用 */
  /* 你可以跟據需要進一步自定義樣式，比如邊框颜色等 */
}</style>