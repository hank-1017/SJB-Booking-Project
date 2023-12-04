<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRoute, useRouter } from 'vue-router';
import { useRoomStore } from '@/stores/room.js';

const URL = import.meta.env.VITE_API_JAVAURL;
const room = ref({});
const route = useRoute()
const router = useRouter();
let hotelID = ref(0);
let productID = ref('');
const roomStore = useRoomStore();
const pictureURL = `${URL}room/images/`;

let roomPicture = ref([]);


onMounted(async () => {

    if (route.meta.isRefreshed) {
        router.push({
            name: 'roomManagement'
        })
    }

    hotelID.value = roomStore.hotelID;
    productID.value = roomStore.productID;

    // console.log(hotelID.value);
    // console.log(productID.value);

    try {
        const response = await axios.get(`${URL}room/findRoomById?hotelID=${hotelID.value}&productID=${productID.value}`, { withCredentials: true });

        if (response.status === 200) {
            room.value = response.data;
            console.log(response.data);
            roomPicture.value = room.value.roomPictures;
        }

    } catch (error) {
        console.error(error);
        // alert('發生問題，請稍後再試一次');
        router.push({ name: 'rooms' });
    }
});


const insertPicture = () => {
    const roomStore = useRoomStore();
    const ids = { hotelID, productID }
    roomStore.setRoomIds(ids);
    router.push({ name: 'roomPictureUpload' });
};

const deletePicture = async (id) => {
    let deleteConfirm = confirm("是否確認刪除?");
    if (deleteConfirm) {
        try {
            const response = await axios.delete(`${URL}room/images/delete?imgID=${id}`, { withCredentials: true });
            if (response.status == 200) {
                alert("刪除成功");

                try {
                    const response = await axios.get(`${URL}room/findRoomById?hotelID=${hotelID.value}&productID=${productID.value}`, { withCredentials: true });

                    if (response.status === 200) {
                        room.value = response.data;
                        console.log(response.data);
                        roomPicture.value = room.value.roomPictures;
                    }

                } catch (error) {
                    console.error(error);
                    alert('發生問題，請稍後再試一次');
                }

            }
        } catch (error) {

        }
    }
}

const goBack = () => {
    router.push({ name: 'rooms' });
}

</script>

<template>
    <div class="container-fluid pt-4 px-4">
        <div class="row g-4">
            <div class="col-sm-12 ">
                <div class=" bg-light rounded h-100 p-4">
                    <h3 class="mb-4">圖片管理</h3>
                    <div class="alignment">

                        <div class="card">
                            <div id="image-container">
                                <img src="@/assets/img/roomPictureDefalt.jpg" class="card-img-top">
                            </div>
                            <div class="card-body">
                                <h5 class="card-title">{{ room.productName }}</h5>
                                <div class="form-check form-switch">
                                    <input class="form-check-input" type="checkbox" role="switch" v-bind="room.enableSwitch"
                                        name="enableSwitch" id="enableSwitch" :checked="room.enableSwitch" disabled>
                                    <label class="form-check-label" for="enableSwitch" id="enableSwitchLabel">{{
                                        room.enableSwitch ? '已上架' : '未上架' }}</label>
                                </div>
                                <p class="card-text">{{ '$' + room.price }}</p>

                                <div style="display: flex; justify-content: center;">
                                    <button type="button" class="btn btn-primary" @click="insertPicture" style="margin-right: 15px;">新增圖片</button>
                                    <button type="button" class="btn btn-outline-secondary" @click="goBack">回上一頁</button>
                                </div>

                            </div>
                        </div>

                        <div id="showArea" class="showArea">
                            <div v-for="image in roomPicture" :key="image.imgID">

                                <div class="image-container">

                                    <img :src="pictureURL + image.imgID" :key="image.imgID" class="room-image" />

                                    <img src="@/assets/img/delete.png" alt="cancel" class="delete-icon"
                                        @click="deletePicture(image.imgID)" />
                                </div>

                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.alignment {
    position: relative;
    display: flex;
}

.card {
    background-color: white;
    width: 19rem;
    margin: 10px;
    margin-right: 5px;
    height: 30rem;
}

.showArea {
    width: 100%;
    height: 30rem;
    margin-top: 10px;
    display: flex;
    flex-wrap: wrap;
    overflow-y: auto;
    background-color: #F0F0F0;
    border-radius: 5px;
}

.picture {
    height: 236px;
    width: 236px;
    overflow: hidden;
}

.resideButton {
    position: absolute;
    bottom: 20px;
    right: 25px;
}

.image-container {
    position: relative;
    display: inline-block;
}

.room-image {
    height: 180px;
    width: 288px;
    margin-top: 10px;
    margin-left: 10px;
}

.room-image:hover {
    border: #ADADAD 3px solid;
}

.delete-icon {
    position: absolute;
    top: 15px;
    right: 5px;
    height: 25px;
    width: 25px;
    cursor: pointer;
}
</style>