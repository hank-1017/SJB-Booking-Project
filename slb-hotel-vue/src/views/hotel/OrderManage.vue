<template>
  <div class="container-fluid pt-4 px-4">
    <div class="row g-4">
      <div class="col-sm-12">
        <div class="bg-light rounded h-100 p-4">
          <h3 class="mb-4">訂單管理</h3>
          
          <!-- Bootstrap form styling for select and input -->
          <div class="mb-3">
            <select class="form-select" @change="selectSortOption" v-model="selectedSortOption">
              <option value="orderDetails[0].detailID">按訂單編號排序</option>
              <option value="orderTime">按建立日期排序</option>
              <option value="updateTime">按更新日期排序</option>
              <option value="orderDetails[0].checkInTime">按入住排序</option>
              <option value="orderDetails[0].checkOutTime">按退房排序</option>
              <option value="orderDetails[0].uniPrice">按總金額排序</option>
            </select>
          </div>
          
          <div class="mb-3">
            <input class="form-control searchbydetail" v-model="searchKeyword" placeholder="搜索訂單編號" />
          </div>

          <router-link to="/hotel/orderAnalysis">
            <button class="btn btn-primary">查看訂單分析圖表</button>
          </router-link>

          <div v-if="isChartVisible">
            <order-analysis />
          </div>

          <!-- 添加分页按钮或组件 -->

          <table class="table table-hover table-striped" width="100%">
            <thead>
              <tr>
                <th>訂單編號</th>
                <th>顧客姓名</th>
                <th>建立日期</th>
                <th>更新日期</th>
                <th>入住</th>
                <th>退房</th>
                <th>總金額</th>
                <th>付款方式</th>
                <th>訂單狀態</th>
                <th>訂單確認</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="order in currentPageData" :key="(order as any).orderID">
                <td>{{ (order as any).orderDetails[0].detailID }}</td>
                <td>{{ (order as any).orderDetails[0].orderName }}</td>
                <td>{{ formatDate((order as any).orderTime) }}</td>
                <td>{{ formatDate((order as any).updateTime) }}</td>
                <td>{{ formatDate((order as any).orderDetails[0].checkInTime) }}</td>
                <td>{{ formatDate((order as any).orderDetails[0].checkOutTime) }}</td>
                <td>{{ (order as any).orderDetails[0].uniPrice }}</td>
                <td>{{ (order as any).orderDetails[0].payment }}</td>
                <td>{{ (order as any).orderStatus }}</td>
                <td>
                   <button @click="confirmOrder(order)" :disabled="(order as any).orderStatus.includes('已建立')||(order as any).orderStatus.includes('已取消')||(order as any).orderStatus.includes('已付款')" class="btn btn-success">確認</button>
                </td>
              </tr>
            </tbody>
          </table>

          <div class="pagination">
            <ul class="pagination">
              <li class="page-item" @click="prevPage" :class="{ 'disabled': currentPage === 1 }">
                <a class="page-link" href="#">上一頁</a>
              </li>
              <li class="page-item disabled">
                <span class="page-link">第{{ currentPage }}頁</span>
              </li>
              <li class="page-item" @click="nextPage" :class="{ 'disabled': currentPage === totalPages }">
                <a class="page-link" href="#">下一頁</a>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue';

const orders = ref([]);
const ascendingSort = ref(true);
const selectedSortOption = ref('orderDetails[0].detailID');
const searchKeyword = ref('');
const currentPage = ref(1);
const itemsPerPage = ref(10);

const isChartVisible = ref(false);

const toggleChartView = () => {
  isChartVisible.value = !isChartVisible.value;
};


onMounted(() => {
  getOrders();
});

onMounted(async () => {
  try {
    const response = await fetch('http://localhost:8080/sjb/orders/getOrdersByHotelID', {
      credentials: 'include',
    });
    const data = await response.json();
    orders.value = data;
    selectedSortOption.value = "orderDetails[0].detailID";
    ascendingSort.value = false; // 切换为降序
    sortOrders(selectedSortOption.value);
  } catch (error) {
    console.error('獲取數據時出錯：', error);
  }
});

const formatDate = (dateString: string) => {
  const date = new Date(dateString);
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  return `${year}-${month}-${day}`;
};

const sortOrders = (property: string) => {
  ascendingSort.value = !ascendingSort.value;

  switch (property) {
    case 'orderDetails[0].detailID':
      orders.value = orders.value.slice().sort((b, a) => {
        return ascendingSort.value
          ? (a as any).orderDetails[0].detailID - (b as any).orderDetails[0].detailID
          : (b as any).orderDetails[0].detailID - (a as any).orderDetails[0].detailID;
      });
      break;
    case 'orderTime':
      orders.value = orders.value.slice().sort((a, b) => {
        return ascendingSort.value
          ? new Date(a.orderTime) - new Date(b.orderTime)
          : new Date(b.orderTime) - new Date(a.orderTime);
      });
      break;
    case 'updateTime':
      orders.value = orders.value.slice().sort((a, b) => {
        return ascendingSort.value
          ? new Date(a.updateTime) - new Date(b.updateTime)
          : new Date(b.updateTime) - new Date(a.updateTime);
      });
      break;
    case 'orderDetails[0].checkInTime':
      orders.value = orders.value.slice().sort((a, b) => {
        return ascendingSort.value
          ? new Date((a as any).orderDetails[0].checkInTime) - new Date((b as any).orderDetails[0].checkInTime)
          : new Date((b as any).orderDetails[0].checkInTime) - new Date((a as any).orderDetails[0].checkInTime);
      });
      break;
    case 'orderDetails[0].checkOutTime':
      orders.value = orders.value.slice().sort((a, b) => {
        return ascendingSort.value
          ? new Date((a as any).orderDetails[0].checkOutTime) - new Date((b as any).orderDetails[0].checkOutTime)
          : new Date((b as any).orderDetails[0].checkOutTime) - new Date((a as any).orderDetails[0].checkOutTime);
      });
      break;
    case 'orderDetails[0].uniPrice':
      orders.value = orders.value.slice().sort((a, b) => {
        return ascendingSort.value
          ? (a as any).orderDetails[0].uniPrice - (b as any).orderDetails[0].uniPrice
          : (b as any).orderDetails[0].uniPrice - (a as any).orderDetails[0].uniPrice;
      });
      break;
  }

  console.log(`排序後的數組：`, orders.value);
};

const selectSortOption = () => {
  sortOrders(selectedSortOption.value);
};

const filteredOrders = computed(() => {
  if (!searchKeyword.value) {
    return orders.value;
  }

  const keyword = searchKeyword.value.toLowerCase();
  return orders.value.filter((order) => {
    const detailID = (order as any).orderDetails[0].detailID.toString().toLowerCase();
    return detailID.includes(keyword);
  });
});

const totalPages = computed(() => Math.ceil(filteredOrders.value.length / itemsPerPage.value));


const prevPage = () => {
  if (currentPage.value > 1) {
    currentPage.value -= 1;
  }
};

const nextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value += 1;
  }
};

const currentPageData = computed(() => {
  const startIndex = (currentPage.value - 1) * itemsPerPage.value;
  const endIndex = startIndex + itemsPerPage.value;
  return filteredOrders.value.slice(startIndex, endIndex);
});

const confirmOrder = (order) => {
  // 确认订单的逻辑
  // 创建用于发送请求的请求体
  const orderConfirmationRequest = {
    orderId: order.orderID, // 请根据您的数据模型来填充
  };

  // 发送确认订单的请求到后端 API
  fetch('http://localhost:8080/sjb/orders/confirmOrder', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(orderConfirmationRequest),
  })
    .then(response => {
      if (response.status === 200) {
        // 订单确认成功
        console.log('訂單已確認');
        // 刷新页面
        window.location.reload();
      } else {
        // 处理其他响应状态码，例如 404（订单不存在）等
        console.log('确认订单失败：', response.data);
        // 可以显示错误消息给用户或者执行其他逻辑
      }
    })
    .catch(error => {
      console.error('确认订单请求出错：', error);
      // 可以处理请求错误，例如网络问题
    });
};

const getOrders = async () => {
  try {
    const response = await fetch('http://localhost:8080/sjb/orders/getOrdersByHotelID', {
      credentials: 'include',
    });
    const data = await response.json();
    orders.value = data;
    selectedSortOption.value = "orderDetails[0].detailID";
    ascendingSort.value = false;
    sortOrders(selectedSortOption.value);
  } catch (error) {
    console.error('獲取數據時出錯：', error);
  }
};
</script>

<style scoped>
/* Additional styling for the search input if needed */
.searchbydetail {
  border-radius: 10px;
  /* Add any other custom styles as needed */
}
</style>
