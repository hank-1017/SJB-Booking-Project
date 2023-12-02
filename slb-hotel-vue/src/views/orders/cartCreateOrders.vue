<script setup>
import { ref, onMounted, watch } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
import { useCartStore } from '@/stores/cart.js';
import Swal from 'sweetalert2';

const URL = import.meta.env.VITE_API_JAVAURL;
const orderDetails = ref([]);
const router = useRouter();
const cartStore = useCartStore();
const pictureURL = `${URL}room/images/`;

orderDetails.value = cartStore.cartItems;
console.log(orderDetails.value);
let totalPrice = ref(0);

if (orderDetails.value.length == 0) {
    router.push({
        name: 'cart',
    });
}

onMounted(() => {
    try {
        for (const orderDetail of orderDetails.value) {
            totalPrice.value += orderDetail.totalPrice;
            // console.log(orderDetail.totalPrice);
            // console.log(totalPrice.value);
        }
    } catch (error) {
        console.log(error);
    }
})


const createOrder = async () => {

    const confirmCreateOrder = confirm('是否確認結帳?');
    
    if (confirmCreateOrder) {

        const loadingAlert = Swal.fire({
          title: '結帳中',
          html: '<i class="fas fa-spinner fa-spin"></i> 請稍候...', // 使用FontAwesome圖標
          showConfirmButton: false,
        });

        const orderDetailsArray = []; // 用于存储每个对象的 FormData

        for (const detail of orderDetails.value) {
            const orderDetailObj = {
                'productID': detail.productID,
                'uniPrice': detail.totalPrice,
                'payment': orderDetails.value.payment,
                'numPeople': orderDetails.value.numPeople,
                'evaluation': detail.evaluation,
                'comment': detail.comment,
                'orderName': orderDetails.value.orderName,
                'orderPhone': orderDetails.value.orderPhone,
                'quantity': detail.quantity,
                'checkInTime': detail.checkInDate,
                'checkOutTime': detail.checkOutDate,
            }

            orderDetailsArray.push(orderDetailObj);
        }
        try {
            const responseOrder = await axios.post(`${URL}orders/createFromCart`, orderDetailsArray, {
                headers: {
                    "Content-Type": "application/json"
                }, withCredentials: true
            });
            if (responseOrder.status == 200) {
                loadingAlert.close();
                Swal.fire({
                    icon: 'success',
                    title: '訂單創建成功',
                    text: '訂單細節寄往您的Email',
                    confirmButtonText: '確認'
                })
                // alert('訂單創建成功');

                for (const orderDetail of orderDetails.value) {
                    await axios.delete(`${URL}cart/delete?cartID=${orderDetail.cartID}`, { withCredentials: true });
                }
                
                router.push({ name: 'findOrderByCustomer' });

            }

        } catch (error) {
            loadingAlert.close()
            console.log(error);
        }

    }
}

</script>

<template>
    <div class="home">
        <div class="banner">
            <span class="banner-logo">
                <img src="@/assets/homepage_logo.svg" alt="">
            </span>
        </div>
    </div>
    <div>
        <div class="about-us" style="background-color: #F0F0F0; margin-top: 10px; padding: 15px;">
            <h1>訂單填寫</h1>
            <hr>
            <div class="about-us__container">
                <div class="row">

                    <div class="col">
                        <div class="mb-3">
                            <label for="orderName" class="form-label">姓名:</label>
                            <input v-model="orderDetails.orderName" id="orderName" name="orderName" type="text"
                                class="form-control" />
                        </div>
                    </div>
                    <div class="col">
                        <div class="mb-3">
                            <label for="orderPhone" class="form-label">電話:</label>
                            <input v-model="orderDetails.orderPhone" id="orderPhone" name="orderPhone" type="text"
                                class="form-control" />
                        </div>
                    </div>
                    <div class="col">
                        <div class="mb-3">
                            <label for="payment" class="form-label">支付方式:</label>
                            <select v-model="orderDetails.payment" id="payment" name="payment" class="form-select">
                                <option value="credit_card">信用卡</option>
                                <option value="cash">現金支付</option>
                            </select>
                        </div>
                    </div>
                    <label class="form-label">訂單項目:</label>

                    <div v-for="cartitem in orderDetails">
                        <div class="card mb-3" style="width: 98%; height: auto;margin: auto; ">
                            <!-- <div class="row g-0"> -->
                            <div>
                                <div>
                                    <div id="item" class="alignment">
                                        <!-- <img src="@/assets/img/sponsors3.png" class="img-fluid rounded-start" alt="..." style="max-height: 72px; max-width: 107px;"> -->
                                        <div class="img" style="">
                                            <!-- <div v-if="cartitem.imgId" class="img"> -->
                                            <img v-if="cartitem.imgId" :src="pictureURL + cartitem.imgId"
                                                :key="cartitem.imgId" class="img-fluid rounded-start" alt="roomPicture"
                                                style="max-width: 97px;max-height: 73px;">

                                            <img v-else src="@/assets/img/roomPictureDefalt.jpg"
                                                class="img-fluid rounded-start" alt="roomPictureDefalt"
                                                style="max-height: 73px; max-width: 107px;">
                                            <!-- <img src="@/assets/img/roomPictureDefalt.jpg" class="img-fluid rounded-start"
                                            alt="roomPictureDefalt" style="max-height: 73px; max-width: 107px;"> -->
                                        </div>

                                        <div id="productName">
                                            <p class="card-text">{{ cartitem.productName }}</p>
                                        </div>
                                        <div id="roomType">
                                            <p class="card-text">{{ cartitem.productType }}</p>
                                        </div>
                                        <div id="date" style="display: flex; flex-direction: column;">
                                            <p class="card-text" style="margin: 0px;">{{ cartitem.checkInDate }}</p>
                                            <p class="card-text" style="margin: 0px;">{{ " ~ " }}</p>
                                            <p class="card-text" style="margin: 0px;">{{ cartitem.checkOutDate }}</p>
                                        </div>
                                        <div id="unitPrice" style="">
                                            <p class="card-text">{{ "$ " + cartitem.price }}</p>
                                        </div>
                                        <div id="quantity">
                                            <div class="alignment">
                                                <p class="card-text">{{ cartitem.quantity }}</p>
                                            </div>
                                        </div>
                                        <div id="totalPrice">
                                            <p class="card-text">{{ "$ " + cartitem.totalPrice }}</p>
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="comment">備註</label>
                        <input v-model="orderDetails.comment" id="comment" name="comment" type="text" />
                    </div>
                    <div style="display: flex; justify-content:center; margin-top: 20px; font-size: large;">
                        <label>訂單總金額 :</label>
                        <p class="card-text">{{ " $ " + totalPrice }}</p>
                    </div>

                    <button type="button" class="button" @click="createOrder">Create Order</button>

                </div>
            </div>
        </div>
    </div>
</template>

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
}

.alignment {
    position: relative;
    display: flex;
    margin: 16px;
}

.alignment .button {
    padding: 7px 15px;
}

#item div {
    margin: auto 20px;
    width: 10%;
    text-align: center;
    display: flex;
    justify-content: center
}

.img {
    display: flex;
}

.orderCheckout {
    float: right;
    margin: 1.5%;

}

.container {
    position: absolute;
    top: 22px;
    right: -5px;
    width: 25px;
    height: 25px;
    padding: 0px;
    margin: 0px;
    cursor: pointer;
}
</style>