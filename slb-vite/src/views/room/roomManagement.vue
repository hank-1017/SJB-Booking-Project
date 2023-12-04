<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
import { useRoomStore } from '@/stores/room.js';
import Swal from 'sweetalert2';

const URL = import.meta.env.VITE_API_JAVAURL;
const rooms = ref([]);
const router = useRouter();
const pictureURL = `${URL}room/images/`;

onMounted(async () => {
    try {
        const loadingAlert = Swal.fire({
            title: '查詢中',
            html: '<i class="fas fa-spinner fa-spin"></i> 請稍候...', // 使用FontAwesome圖標
            showConfirmButton: false,
        });
        const response = await axios.get(`${URL}room/findAllRoom`, { withCredentials: true });

        if (response.status === 200) {
            rooms.value = response.data;
            for (let room of rooms.value) {

                if (room.productDescription && room.productDescription.length > 45) {
                    // 截取前 45 个字符并添加省略号
                    room.productDescription = room.productDescription.substring(0, 45) + '...';
                }
            }
        }
        loadingAlert.close();
    } catch (error) {
        console.error(error); // 处理错误
        alert('發生問題，請稍後再試一次');
    }
});

const insertRooms = () => {
    router.push({ name: 'insertRoom' });
}

const editRoom = (hotelID, productID) => {
    const roomStore = useRoomStore();
    const ids = { hotelID, productID }
    roomStore.setRoomIds(ids);
    router.push({ name: 'editRoom' });
}

const deleteRoom = async (hotelID, productID) => {
    const deleteConfirmed = confirm("是否確認刪除?");
    if (deleteConfirmed) {
        const deleteRoomAPI = `${URL}room/delete?hotelID=${hotelID}&productID=${productID}`;
        await axios.delete(deleteRoomAPI, { withCredentials: true });
        try {
            const response = await axios.get(`${URL}room/findAllRoom`, { withCredentials: true });

            if (response.status === 200) {
                rooms.value = response.data;
                for (let room of rooms.value) {

                    if (room.productDescription && room.productDescription.length > 45) {
                        // 截取前 45 个字符并添加省略号
                        room.productDescription = room.productDescription.substring(0, 45) + '...';
                    }
                }
            }
        } catch (error) {
            console.error(error); // 处理错误
            alert('發生問題，請稍後再試一次');
        }
    }
}
const uploadPhoto = (hotelID, productID) => {
    const roomStore = useRoomStore();
    const ids = { hotelID, productID }
    roomStore.setRoomIds(ids);
    router.push({ name: 'roomPictureManagement' });
}
</script>

<template>
    <div class="container-fluid pt-4 px-4">
        <div class="row g-4">
            <div class="col-sm-12 ">
                <div class=" bg-light rounded h-100 p-4">
                    <h3 class="mb-4">房間管理</h3>
                    <div class="cardAlignment">
                        <div
                            v-for="{ hotelID, productID, productName, price, enableSwitch, roomPictures, productDescription } in rooms">
                            <div class="card">
                                <div id="image-container">

                                    <!-- <img src="@/assets/img/roomPictureDefalt.jpg" class="card-img-top"> -->


                                    <img v-if="roomPictures.length === 0" src="@/assets/img/roomPictureDefalt.jpg"
                                        class="card-img-top picsize">
                                    <img v-else :src="pictureURL + roomPictures[0].imgID" :key="roomPictures[0].imgID"
                                        class="card-img-top picsize" alt="roomPicture">

                                </div>
                                <div class="card-body">
                                    <h5 class="card-title">{{ productName }}</h5>
                                    <div class="form-check form-switch">
                                        <input class="form-check-input" type="checkbox" role="switch" v-bind="enableSwitch"
                                            name="enableSwitch" id="enableSwitch" :checked="enableSwitch" disabled>
                                        <label class="form-check-label" for="enableSwitch" id="enableSwitchLabel">{{
                                            enableSwitch ? '已上架' : '未上架' }}</label>
                                    </div>
                                    <p class="card-text">{{ '$' + price }}</p>

                                    <hr style="margin-top: 35px; margin-bottom: 5px;">
                                    <p class="card-text">{{ productDescription }}</p>

                                    <div style="display: flex; justify-content: center;">
                                        <div class="btn-group">
                                            <button type="submit" class="btn btn-outline-primary"
                                                @click="editRoom(hotelID, productID)">編輯</button>

                                            <!-- <form id="deleteForm" th:action="@{/room/delete}" method="post">
                                                <input type="hidden" name="hotelID" th:value="${room.hotelID}" />
                                                <input type="hidden" name="productID"
                                                    th:value="${room.productID}" />
                                                <input type="hidden" name="_method" value="delete" /> -->
                                            <button id="deleteBtn" type="submit" class="btn btn-outline-danger"
                                                @click="deleteRoom(hotelID, productID)">刪除</button>
                                            <!-- </form> -->

                                            <!-- <form th:action="@{/room/addphoto}" method="post" enctype="multipart/form-data" id="upload-form">
                                                <label class="btn btn-primary"> 
                                                <input type="hidden" name="hotelID" th:value="${room.hotelID}" /> 
                                                <input type="hidden" name="productID" th:value="${room.productID}" />
                                                <input type="file" class="btn btn-primary btn-sm" name="photofile" id="photofile" required multiple style="display: none;"> 上傳圖片</label>
                                            </form> -->
                                            <label class="btn btn-primary">
                                                <!-- <input type="file" class="btn btn-primary btn-sm" name="photofile"
                                                    id="photofile" multiple @change="uploadPhoto(hotelID, productID)"
                                                    style="display: none;"> 上傳圖片 -->
                                                <input type="buttom" class="btn btn-primary btn-sm"
                                                    @click="uploadPhoto(hotelID, productID)" style="display: none;"> 上傳圖片
                                            </label>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>


                        <div class="card backcolor" @click="insertRooms">
                            <div class="center-div">
                                <h4 class="card-title">建立新客房</h4>

                                <img style="width: 25%; " src="@/assets/img/add.png" alt="+">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.card {
    background-color: white;
    width: 19rem;
    margin: 10px;
    height: 30rem;
}

.cardAlignment {
    margin: auto;
    padding: auto;
    display: flex;
    flex-wrap: wrap;
}

.backcolor {
    background-color: #F0F0F0;
    cursor: pointer
}

.center-div {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 100%;
    /* 设置高度以充满父容器 */
}

.card:hover {
    border: #ADADAD 3px solid;
}

.picsize {
    height: 201px;
    width: 100%;
}
</style>