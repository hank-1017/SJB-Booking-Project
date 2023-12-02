<script setup>

import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';

const hours = [...Array(24).keys()].map(hour => String(hour).padStart(2, '0') + ':00');
const roomTypes = ['標準雙人房', '標準四人房', '標準套房', '豪華雙人房', '豪華四人房', '豪華套房', '海景雙人房', '海景四人房', '海景套房', '街景雙人房', '街景四人房', '街景套房', '家庭套房', '總統套房', '主題套房', '度假屋', '別墅', '木屋', '湖景房', '山景房', '泳池別墅', '豪華游艇套房', '沙灘小屋', '草原帳篷', '城堡套房', '露營車', '溫泉別墅', '河景小屋', '高空觀景房', '寒意小屋', '浪漫小屋', '花園別墅', '古典套房', '現代風格房', '鄉村風格房', '海灘別墅', '山莊', '城市景觀套房'];

const URL = import.meta.env.VITE_API_JAVAURL;
const router = useRouter();
let discountSwitch = ref(false);

let insertForm = ref({
    productType: "no",
    capacity: "no",
    checkInTime: "no",
    checkOutTime: "no",
    childExtraBed: false,
    privateBathroom: false,
    showerRoom: false,
    childrenPrice: 0,
    age: 0,
    discount: 1
});

const clearForm = () => {
    const form = document.getElementById('insertForm');
    form.reset();
}

const sendForm = async () => {
    const sendConfirm = confirm('是否送出?');
    if (sendConfirm) {
        const form = document.getElementById('insertForm');
        const formData = new FormData(form);

        try {
            const response = await axios.post(`${URL}room/insertRoom`, formData, { withCredentials: true });

            if (response.status === 200) {
                alert('新增成功');
                clearForm();
                router.push({ name: 'rooms' });
            } else {
                alert('新增失敗');
            }
        } catch (error) {
            console.log(error);
            alert('發生錯誤，請再試一次');
        }
    }
}
const cancel = () => {
    const sendConfirm = confirm('是否離開?');
    if (sendConfirm) {
        clearForm();
        router.push({ name: 'rooms' });
    }
}



</script>

<template>
    <div class="container-fluid pt-4 px-4">
        <div class="row g-4">

            <form id="insertForm">
                <div class="col-sm-12">
                    <div class=" bg-light rounded h-100 p-4">
                        <h3 class="mb-4">新增房間</h3>


                        <div class="row">

                            <div class="col">
                                <div class="mb-3">
                                    <label for="productType" class="form-label">客房類型:</label>
                                    <select id="productType" name="productType" class="form-select">
                                        <option value="no">請選擇</option>
                                        <option v-for="productType in roomTypes" :value="productType">{{ productType }}
                                        </option>
                                    </select>
                                </div>
                            </div>


                            <div class="col">
                                <div class="mb-3">
                                    <label for="capacity" class="form-label">成人最多可入住人數:</label>
                                    <select id="capacity" name="capacity" class="form-select">
                                        <option value="no">請選擇</option>
                                        <option v-for="i in 30" :value="i">{{ i }}</option>
                                    </select>
                                </div>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="productName" class="form-label">客房名稱:</label>
                            <input type="text" id="productName" name="productName" class="form-control" placeholder="請輸入客房名稱">
                        </div>

                        <div class="row">

                            <div class="col">
                                <div class="mb-3">
                                    <label for="quantity" class="form-label">客房數量:</label>
                                    <input type="text" id="quantity" name="quantity" class="form-control" placeholder="請輸入客房數量(阿拉伯數字)">
                                </div>
                            </div>

                            <div class="col">
                                <div class="mb-3">
                                    <label for="price" class="form-label">標準房價:</label>
                                    <input type="text" id="price" name="price" class="form-control" placeholder="請輸入標準房價(阿拉伯數字)">
                                </div>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="productDescription" class="form-label">客房描述:</label> <br>
                            <textarea id="productDescription" name="productDescription" class="form-control"
                                rows="5" placeholder="請描述您的客房特色..."></textarea>

                        </div>

                    </div>
                </div>
                <br>

                <div class="col-sm-12">
                    <div class=" bg-light rounded h-100 p-4">

                        <div class="form-check form-switch">
                            <input class="form-check-input" type="checkbox" role="switch" 
                                name="discountSwitch" id="discountSwitch" v-model="discountSwitch" >
                            <label class="form-check-label" for="discountSwitch" id="discountSwitchLabel">
                                優惠設定 </label>
                        </div>
                        <br>    
                        <div v-if="discountSwitch">
                            <div class="row">

                                <div class="col">
                                    <div class="mb-3">
                                        <label for="discount" class="form-label">優惠折數</label>
                                        <select id="discount" name="discount" class="form-select" v-model="insertForm.discount">
                                        <option value="1">請選擇</option>
                                        <option v-for="i in [...Array(99).keys()].reverse()" :value="(i + 1) / 100">{{ (i + 1) + "%" }}</option>
                                    </select>
                                    </div>
                                </div>

                                <div class="col">
                                    <div class="mb-3">
                                        <label for="dateStart" class="form-label">優惠起始日期:</label>
                                        <input type="date" id="dateStart" name="dateStart" class="form-control">
                                    </div>
                                </div>
                                <div class="col">
                                    <div class="mb-3">
                                        <label for="dateEnd" class="form-label">優惠結束日期:</label>
                                        <input type="date" id="dateEnd" name="dateEnd" class="form-control">
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col">
                                <div class="mb-3">
                                    <!-- 选择入住时间 -->
                                    <label for="checkInTimeLabel" class="form-label">Check-In Time:</label>
                                    <select id="checkInTime" name="checkInTime" class="form-select">
                                        <option value="no">請選擇</option>
                                        <option v-for="checkInTime in hours" :value="checkInTime">{{ checkInTime }}</option>
                                    </select>
                                </div>
                            </div>

                            <div class="col">
                                <div class="mb-3">
                                    <!-- 选择退房时间 -->
                                    <label for="checkOutTimeLabel" class="form-label">Check-Out Time:</label>
                                    <select id="checkOutTime" name="checkOutTime" class="form-select">
                                        <option value="no">請選擇</option>
                                        <option v-for="checkOutTime in hours" :value="checkOutTime">{{ checkOutTime }}
                                        </option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col" style="width: 50%;">
                                <div class="row">
                                    <div class="col">
                                        <div class="mb-3">
                                            <input type="checkbox" id="childExtraBed" name="childExtraBed"
                                                class="form-check-input" v-model="insertForm.childExtraBed">
                                            <label for="childExtraBed" class="form-label">可加兒童床</label>
                                        </div>
                                    </div>
                                    <div class="col">
                                        <div class="mb-3">
                                            <input type="checkbox" id="privateBathroom" name="privateBathroom"
                                                class="form-check-input" v-model="insertForm.privateBathroom">
                                            <label for="privateBathroom" class="form-label">私人衛浴</label>
                                        </div>
                                    </div>
                                    <div class="col">
                                        <div class="mb-3">
                                            <input type="checkbox" id="showerRoom" name="showerRoom"
                                                class="form-check-input" v-model="insertForm.showerRoom">
                                            <label for="showerRoom" class="form-label">公用淋浴間</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col"></div>
                        </div>

                        <div v-if="insertForm.childExtraBed">
                            <div class="row">
                                <div class="col">
                                    <div class="mb-3">
                                        <!-- 输入儿童价格 -->
                                        <label for="childrenPrice" class="form-label">加設兒童床費用:</label>
                                        <input type="text" id="childrenPrice" name="childrenPrice"
                                            v-model="insertForm.childrenPrice" class="form-control" placeholder="請輸入加設兒童床費用(阿拉伯數字)">
                                    </div>
                                </div>

                                <div class="col">
                                    <div class="mb-3">
                                        <!-- 输入年龄 -->
                                        <label for="age" class="form-label">兒童床使用限制年齡:</label>
                                        <input type="text" id="age" name="age" v-model="insertForm.age"
                                            class="form-control" placeholder="請輸入使用限制年齡(阿拉伯數字)">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <br>

                        <div class="d-grid gap-2">
                            <button type="button" class="btn btn-primary" @click="sendForm">完成</button>
                        </div>

                        <div class="d-grid gap-2 marg">
                            <button type="button" class="btn btn-outline-secondary" @click="cancel">取消</button>
                        </div>
                    </div>
                </div>


            </form>
        </div>
    </div>
</template>

<style scoped>
.marg {
    margin-top: 10px;
}

.margL {
    margin-right: 20px;
}
</style>