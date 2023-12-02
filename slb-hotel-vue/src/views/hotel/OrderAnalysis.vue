<template>
  <form class="container-fluid pt-4 px-4 bg-light rounded">
    <div class="row g-4">
      <div class="col-sm-12">
        <div class="p-4">
          <h1 class="mb-4 text-center">訂單分析</h1>
          <div class="chart-container">
            <canvas ref="barChart"></canvas>
          </div>
          <button @click="goBack" class="btn btn-primary mt-3">返回訂單管理</button>
        </div>
      </div>
    </div>
  </form>
</template>

<style scoped>
.chart-container {
  max-width: 600px;
  margin: auto;
}

/* Additional styling for the form if needed */
form {
  margin-top: 20px; /* Adjust as needed */
  padding: 20px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}
</style>
<script setup>
import { ref, onMounted } from 'vue';
import Chart from 'chart.js/auto';
import { useRouter } from 'vue-router';

const barChart = ref(null);
const router = useRouter();
onMounted(async () => {
  try {
    // 调用后端API获取订单数据
    const response = await fetch('http://localhost:8080/sjb/orders/getOrdersByHotelID', {
      credentials: 'include',
    });

    if (response.ok) {
      const orders = await response.json();

      // 提取月份信息并统计每个月份的订单数量
      const monthlyData = {};
      orders.forEach(order => {
        const checkInDate = new Date(order.orderDetails[0].checkInTime);
        const month = checkInDate.getMonth() + 1; // 月份是从0开始的，所以要加1

        if (!monthlyData[month]) {
          monthlyData[month] = 1;
        } else {
          monthlyData[month]++;
        }
      });

      // 补充缺失的月份，将其值设为 0
      for (let i = 1; i <= 12; i++) {
        if (!monthlyData[i]) {
          monthlyData[i] = 0;
        }
      }

      // 绘制柱形图
      drawBarChart(monthlyData);
    } else {
      console.error('获取订单数据失败:', response.status);
    }
  } catch (error) {
    console.error('获取订单数据时发生错误:', error);
  }
});

const drawBarChart = (data) => {
  const labels = Object.keys(data);
  const values = Object.values(data);

  const ctx = barChart.value.getContext('2d');

  new Chart(ctx, {
    type: 'bar',
    data: {
      labels: labels,
      datasets: [{
        label: '訂單數量',
        data: values,
        backgroundColor: 'rgba(75, 192, 192, 0.2)',
        borderColor: 'rgba(75, 192, 192, 1)',
        borderWidth: 1
      }]
    },
    options: {
      scales: {
        y: {
          beginAtZero: true
        }
      }
    }
  });
};

const goBack = () => {
  // 使用 router 的 push 方法返回到订单管理页面
  router.push('orderManagement');
};
</script>
