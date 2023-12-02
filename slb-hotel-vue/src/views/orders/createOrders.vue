<template>
  <div class="container-fluid mt-5">
    <h1 class="mb-4">訂單填寫</h1>

    <div class="card">
      <div class="card-header">訂單資訊</div>
      <div class="card-body">
        <form @submit.prevent="createOrder">

          <div class="mb-3">
            <label for="orderName" class="form-label">姓名:</label>
            <input v-model="orderDetails.orderName" id="orderName" name="orderName" type="text" class="form-control" />
          </div>

          <div class="mb-3">
            <label for="orderPhone" class="form-label">電話:</label>
            <input v-model="orderDetails.orderPhone" id="orderPhone" name="orderPhone" type="text" class="form-control" />
          </div>

          <!-- Other input fields -->

          <input type="hidden" v-model="orderDetails.productID" id="productID" name="productID" />

          <div class="mb-3">
            <label for="uniPrice" class="form-label">金額:</label>
            <input v-model="calculatedUniPrice" id="uniPrice" name="uniPrice" readonly class="form-control" />
          </div>

          <div class="mb-3">
            <label for="quantity" class="form-label">房間數:</label>
            <input v-model.number="orderDetails.quantity" id="quantity" name="quantity" type="number" class="form-control" />
          </div>

          <div class="mb-3">
            <label for="payment" class="form-label">付款方式:</label>
            <select v-model="orderDetails.payment" id="payment" name="payment" class="form-select">
              <option value="credit_card">信用卡</option>
              <option value="cash">現金支付</option>
            </select>
          </div>

          <div class="mb-3">
            <label for="numPeople" class="form-label">人數:</label>
            <input v-model.number="orderDetails.numPeople" id="numPeople" name="numPeople" type="number" class="form-control" />
          </div>

          <div class="mb-3">
            <label for="checkInDate" class="form-label">入住時間:</label>
            <input v-model="orderDetails.checkInTime" id="checkInDate" name="checkInTime" type="date" readonly class="form-control" />
          </div>

          <div class="mb-3">
            <label for="checkOutDate" class="form-label">退房時間:</label>
            <input v-model="orderDetails.checkOutTime" id="checkOutDate" name="checkOutTime" type="date" readonly class="form-control" />
          </div>

          <div class="mb-3">
            <label for="comment" class="form-label">備註: </label>
            <input v-model="orderDetails.comment" id="comment" name="comment" type="text" class="form-control" />
          </div>

          <button type="submit" class="btn btn-primary">訂房</button>
        </form>

        <div id="result" class="mt-3">{{ creationResult }}</div>
      </div>
    </div>
  </div>
</template>
<script>
import { ref } from 'vue';
import moment from 'moment';
import axios from 'axios';
import Swal from 'sweetalert2';

const Url = ref(import.meta.env.VITE_API_JAVAURL);

export default {
  data() {
    return {
      orderDetails: {
        productID: this.$route.params.productID,
        uniPrice: this.$route.params.price,
        quantity: 1,
        payment: "credit_card",
        numPeople: 1,
        checkInTime: this.$route.params.startDate,
        checkOutTime: this.$route.params.endDate,
        orderName: "張曉明",
        orderPhone: "0982882394",
        comment: "",
        // 这里添加其他订单细节字段
      },
      creationResult: ""
    };
  },
  methods: {
    async createOrder() {
      this.orderDetails.checkInTime = moment(this.orderDetails.checkInTime).format("YYYY-MM-DD");
      this.orderDetails.checkOutTime = moment(this.orderDetails.checkOutTime).format("YYYY-MM-DD");

      try {
        const orderCreationRequest = {
          orderDetails: [this.orderDetails]
          // 這裡添加其他訂單細節對象，如果有的話
        };

        const response = await axios.post(`${Url.value}orders/create`, orderCreationRequest, {
          headers: {
            "Content-Type": "application/json"
          },
          withCredentials: true
        });

        if (response.status === 200) {
          if (response.headers['content-type'].includes('application/json')) {
            this.creationResult = "Order created: " + JSON.stringify(response.data);

            // 在订单成功创建后触发 SweetAlert 成功消息
            Swal.fire({
              icon: 'success',
              title: '訂單創建成功',
              text: '訂單細節寄往',
              confirmButtonText: '確認'
            }).then((result) => {
              if (result.isConfirmed) {
                window.location.href = 'http://localhost:5173/hotel/orders/findOrderByCustomer';
              }
            });


          } else {
            this.creationResult = "Order created successfully";
          }
        } else {
          this.creationResult = "Error: " + response.data;
        }

      } catch (error) {
        // 使用 SweetAlert 顯示錯誤訊息
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: '出現錯誤: 用戶未登入'
        });
      }
    }
  },
  computed: {
    calculatedUniPrice() {
      // 根據數量計算價格，你可以根據實際需求进行调整
      return this.orderDetails.uniPrice * this.orderDetails.quantity;
    }
  },
};
</script>

<style scoped lang="scss">
@import "@/assets/all.scss";
@import "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css";

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

.order-form {
  padding: 20px;
  background-color: #f0f0f0;
  border: 1px solid #ccc;
  border-radius: 5px;
  max-width: 400px;
  margin: 0 auto;
}

.form-group-horizontal {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;

  .form-group {
    flex: 1;
    margin: 0 5px;
  }
}

label {
  display: block;
  font-weight: bold;
  margin-bottom: 5px;
}

input[type="text"],
input[type="number"],
select,
input[type="date"] {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 16px;
}

button[type="submit"] {
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 5px;
  padding: 10px 20px;
  font-size: 18px;
  cursor: pointer;
  margin-top: 10px;
}

#result {
  font-weight: bold;
  text-align: center;
  margin-top: 10px;
}</style>