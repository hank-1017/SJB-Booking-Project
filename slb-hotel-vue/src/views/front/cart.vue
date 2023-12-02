<script setup>
import { ref, onMounted, watch } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
import { useCartStore } from '@/stores/cart.js';
import Swal from 'sweetalert2';

const URL = import.meta.env.VITE_API_JAVAURL;
const carts = ref([]);
const room = ref({});
const hotel = ref({});
const router = useRouter();
const pictureURL = `${URL}room/images/`;


const getData = async () => {
    const loadingAlert = Swal.fire({
            title: '查詢中',
            html: '<i class="fas fa-spinner fa-spin"></i> 請稍候...', // 使用FontAwesome圖標
            showConfirmButton: false,
        });

    try {

        const cartResponse = await axios.get(`${URL}cart/findAll`, { withCredentials: true });

        if (cartResponse.status === 200) {
            carts.value = cartResponse.data;

            for (let cart of carts.value) {
                cart.itemCheck = false;
                const roomResponse = await axios.get(`${URL}room/findRoomById?hotelID=${cart.hotelID}&productID=${cart.productID}`, { withCredentials: true });

                // console.log(roomResponse.data);

                room.value = roomResponse.data;
                cart.productName = room.value.productName;
                cart.productType = room.value.productType;
                cart.price = room.value.price;

                // 由於前端跟後端有時差(差八小時) 因此在前端須轉換時區
                const timezoneDate = new Date(cart.checkInDate)
                    .toLocaleString("zh-TW", { timeZone: "Asia/Taipei" });

                const timezoneCODate = new Date(cart.checkOutDate)
                    .toLocaleString("zh-TW", { timeZone: "Asia/Taipei" });

                // 切到只剩日期                    
                const dateString = timezoneDate.split(' ')[0];
                const dateCOString = timezoneCODate.split(' ')[0];

                // 拆分年月日
                let [yyyy, mm, dd] = dateString.split('/');
                if (mm.length < 2) {
                    mm = '0' + mm;
                }
                if (dd.length < 2) {
                    dd = '0' + dd;
                }

                let formatDateString = `${yyyy}-${mm}-${dd}`;
                cart.checkInDate = formatDateString;

                [yyyy, mm, dd] = dateCOString.split('/');
                if (mm.length < 2) {
                    mm = '0' + mm;
                }
                if (dd.length < 2) {
                    dd = '0' + dd;
                }

                formatDateString = `${yyyy}-${mm}-${dd}`;
                cart.checkOutDate = formatDateString;


                // 算住宿天數

                const checkInDate = new Date(cart.checkInDate);
                const checkOutDate = new Date(cart.checkOutDate);

                const timeDifference = checkOutDate - checkInDate;
                const daysDifference = timeDifference / (1000 * 60 * 60 * 24);

                // 存入cart裡面
                cart.daysDifference = daysDifference;

                cart.totalPrice = Math.round((room.value.price) * (room.value.discount) * (cart.quantity) * (cart.daysDifference));

                if (room.value.roomPictures.length != 0) {
                    cart.imgId = room.value.roomPictures[0].imgID;
                    // console.log(room.value.roomPictures[0].imgID);
                }
            }
            loadingAlert.close();
        }
    } catch (error) {
        console.error(error);
        alert('發生問題，請稍後再試一次');
    }
}

onMounted(getData);

const deleteItem = async (cartID) => {
    let confirmDelete = confirm("是否確認刪除?");
    if (confirmDelete) {
        try {
            const deleteResponse = await axios.delete(`${URL}cart/delete?cartID=${cartID}`, { withCredentials: true });
            if (deleteResponse.status == 200) {
                alert("刪除成功");
                getData();
            } else {
                alert('發生錯誤，請再試一次');
                getData();
            }
        } catch (error) {
            alert('發生錯誤，請再試一次');
            console.log(error);
        }
    }
}

const reduceQuantity = async (cartitem) => {
    if (cartitem.quantity > 1) {
        cartitem.quantity -= 1;

        const formData = new FormData();
        formData.append('cartID', cartitem.cartID);
        formData.append('hotelID', cartitem.hotelID);
        formData.append('productID', cartitem.productID);
        formData.append('quantity', cartitem.quantity);
        formData.append('checkInDate', cartitem.checkInDate);
        formData.append('checkOutDate', cartitem.checkOutDate);

        await axios.put(`${URL}cart/updatePost`, formData, { withCredentials: true });
        getData();
    } else {
        deleteItem(cartitem.cartID);
    }
}

const addQuantity = async (cartitem) => {
    // if(cartitem.quantity < room){

    // }
    cartitem.quantity += 1;
    const formData = new FormData();
    formData.append('cartID', cartitem.cartID);
    formData.append('hotelID', cartitem.hotelID);
    formData.append('productID', cartitem.productID);
    formData.append('quantity', cartitem.quantity);
    formData.append('checkInDate', cartitem.checkInDate);
    formData.append('checkOutDate', cartitem.checkOutDate);
    await axios.put(`${URL}cart/updatePost`, formData, { withCredentials: true });
    getData();
}

const changeQuentity = async (cartitem) => {
    const formData = new FormData();
    formData.append('cartID', cartitem.cartID);
    formData.append('hotelID', cartitem.hotelID);
    formData.append('productID', cartitem.productID);
    formData.append('quantity', cartitem.quantity);
    formData.append('checkInDate', cartitem.checkInDate);
    formData.append('checkOutDate', cartitem.checkOutDate);

    await axios.put(`${URL}cart/updatePost`, formData, { withCredentials: true });
    getData();
}

const orderCheckout = () => {

    // 暫存進pinia
    const cartStore = useCartStore();
    // let submitOrderCheckout = [];
    cartStore.clearCart();
    
    for (const cart of carts.value) {
        if (cart.itemCheck) {
            // submitOrderCheckout.push(cart);
            cartStore.addCartItem(cart);
        }
    }
    // console.log(cartStore.cartItems.length);

    if (cartStore.cartItems.length < 1) {
        alert('請選擇結帳項目');
    } else {
        router.push({
            name: 'cartCreateOrders',
        });
    }

}


</script>

<template>
    <div class="about-us">
        <section class="about-us section" id="about-us">
            <div class="alignment">
                <img src="@/assets/img/shopping-cart.png" alt="" style="height: 30px; margin: 0px 15px;">
                <h3>購物車</h3>
            </div>
            <hr>
            <div class="about-us__container">
                <div v-for="cartitem in carts">

                    <div class="card mb-3" style="width: 98%; height: auto;margin: auto;">
                        <!-- <div class="row g-0"> -->
                        <div>
                            <div>
                                <div id="item" class="alignment">

                                    <input type="checkbox" name="itemCheck" id="itemCheck"
                                        style="margin: 0px 6px; display: flex" v-model="cartitem.itemCheck">

                                    <!-- <img src="@/assets/img/sponsors3.png" class="img-fluid rounded-start" alt="..." style="max-height: 72px; max-width: 107px;"> -->
                                    <div class="img" style="">
                                        <!-- <div v-if="cartitem.imgId" class="img"> -->
                                        <img v-if="cartitem.imgId" :src="pictureURL + cartitem.imgId" :key="cartitem.imgId"
                                            class="img-fluid rounded-start" alt="roomPicture"
                                            style="max-width: 97px;max-height: 73px;">

                                        <img v-else src="@/assets/img/roomPictureDefalt.jpg" class="img-fluid rounded-start"
                                            alt="roomPictureDefalt" style="max-height: 73px; max-width: 107px;">
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
                                        <!-- <p class="card-text">{{ "$ " + cartitem.price }}</p> -->
                                        <p class="card-text">{{ "$ " + cartitem.price }}</p>
                                    </div>
                                    <div id="quantity">
                                        <div class="alignment">
                                            <button type="button" class="button"
                                                @click="reduceQuantity(cartitem)">–</button>
                                            <input type="text" name="quantity" id="quantity"
                                                style="width: 40px; text-align: center;" v-model="cartitem.quantity"
                                                @change="changeQuentity(cartitem)">
                                            <button type="button" class="button" @click="addQuantity(cartitem)">+</button>
                                        </div>
                                    </div>
                                    <div id="totalPrice">
                                        <p class="card-text">{{ "$ " + cartitem.totalPrice }}</p>
                                    </div>

                                    <img src="@/assets/img/cartDelete.png" alt="delete" class="container"
                                        @click="deleteItem(cartitem.cartID)">

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div>
                    <button id="orderCheckout" type="button" class="button orderCheckout" @click="orderCheckout">結帳</button>
                </div>

            </div>
        </section>
        <!--========== SCROLL UP ==========-->
        <a href="#" class="scrollup" id="scroll-up">
            <i class="ri-arrow-up-line scrollup__icon"></i>
        </a>
    </div>
</template>

<style scoped>
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