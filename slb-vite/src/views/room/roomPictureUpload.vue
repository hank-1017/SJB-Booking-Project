<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
import { useRoomStore } from '@/stores/room.js';

const URL = import.meta.env.VITE_API_JAVAURL;
const room = ref({});
const router = useRouter();
let hotelID = ref(0);
let productID = ref('');
const roomStore = useRoomStore();

let roomPicture = ref([]);
const fileInput = ref(null);
let roomPictureSelected = ref([]);
let roomPictureUpload = ref([]);

onMounted(async () => {

    hotelID.value = roomStore.hotelID;
    productID.value = roomStore.productID;

    // console.log(hotelID.value);
    // console.log(productID.value);

    try {
        const response = await axios.get(`${URL}room/findRoomById?hotelID=${hotelID.value}&productID=${productID.value}`, { withCredentials: true });

        if (response.status === 200) {
            room.value = response.data;
            // console.log(room.value);
        }
    } catch (error) {
        console.error(error); // 处理错误
        // alert('發生問題，請稍後再試一次');
        router.push({ name: 'rooms' });

    }
});


const showPicture = (e) => {

    const files = fileInput.value.files;
    for (const file of files) {
        const reader = new FileReader();
        reader.onload = (e) => {
            roomPicture.value.push(e.target.result);// 秀圖片用
        };
        reader.readAsDataURL(file);
    }
    // handleFileChange(e);
    roomPictureSelected.value = e.target.files // 紀錄觸發本次事件時選擇的圖片項目
    roomPictureUpload.value.push(...roomPictureSelected.value); // 紀錄最終要上傳的照片項目
};


const uploadPhoto = async () => {
    let confirmUpload = confirm("是否確定上傳?");
    if (confirmUpload) {

        const formData = new FormData();
        formData.append('hotelID', hotelID.value);
        formData.append('productID', productID.value);


        for (const photo of roomPictureUpload.value) {
            formData.append('photofile', photo);
        }


        console.log(formData.get('photofile'));



        try {
            const response = await axios.post(`${URL}room/addphoto`, formData, {
                withCredentials: true,
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            });

            if (response.status === 200) {
                alert('新增成功');
                router.push({ name: 'roomPictureManagement' });
            } else {
                alert('新增失敗');
            }

        } catch (error) {
            console.log(error);
            alert('發生錯誤，請再試一次');
        }
    }
}

const goBack = () => {
    const confirmGoBack = confirm("確認放棄變更?");
    if(confirmGoBack){
        router.push({ name: 'rooms' });
    }
}

</script>

<template>
    <div class="container-fluid pt-4 px-4">
        <div class="row g-4">
            <div class="col-sm-12 ">
                <div class=" bg-light rounded h-100 p-4">
                    <h3 class="mb-4">新增圖片</h3>
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
                                    <label class="btn btn-outline-primary" style=" margin-right: 15px;">
                                        <input type="file" class="btn btn-outline-primary" name="pictures" id="pictures"
                                            ref="fileInput" multiple @change="showPicture" style="display: none;"> 選擇圖片
                                    </label>
                                    <button type="button" class="btn btn-outline-secondary" @click="goBack">回上一頁</button>
                                </div>
                            </div>
                        </div>

                        <div id="showArea" class="showArea">
                            <img v-for="image in roomPicture" :src="image" :key="image"
                                style="height: 180px;width: 288px;margin-top: 10px;margin-left: 10px;" />
                        </div>
                        <div>
                            <button type="button" class="btn btn-primary resideButton" @click="uploadPhoto">確認上傳</button>
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
    /* 距离底部的距离 */
    right: 25px;
    /* 距离右边的距离 */
}
</style>