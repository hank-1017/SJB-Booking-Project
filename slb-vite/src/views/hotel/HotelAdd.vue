<template>
  <!-- Sale & Revenue Start -->
  <div class="container-fluid pt-4 px-4">
    <div class="row g-4">
      <form id="insertHotelForm" @submit.prevent="sendForm">
        <div class="col-sm-12">
          <div class=" bg-light rounded h-100 p-4">
            <h3 class="mb-4">新增住宿
              <button type="submit" class="btn btn-primary">確認新增</button>


            </h3>

            <div class="row">
              <div class="col">
                <div class="mb-3">
                  <label for="hotelName" class="form-label">住宿名稱:</label>
                  <input type="text" class="form-control " id="hotelName" name="hotelName" placeholder="請輸入住宿名稱" required>
                </div>
              </div>
              <div class="col">
                <div class="mb-3">
                  <label for="hotelType" class="form-label">住宿類型:</label>
                  <select class="form-select mb-3" aria-label=".form-select-sm example" id="hotelType" name="hotelType"
                          required>
                    <option value="飯店" selected>飯店</option>
                    <option value="民宿">民宿</option>
                    <option value="渡假村">渡假村</option>
                  </select>
                </div>
              </div>
            </div>

            <div class="row">
              <div class="col">
                <div class="mb-3">
                  <label for="hotelStar" class="form-label">住宿星等:</label>
                  <select class="form-select mb-3" aria-label=".form-select-sm example"
                          id="hotelStar" name="hotelStar" required>
                    <option selected>請選擇住宿星等</option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                  </select>
                </div>
              </div>
              <div class="col">
                <div class="mb-3">
                  <label for="GUInumber" class="form-label">統一編號:</label>
                  <input type="text" class="form-control " id="GUInumber" name="GUInumber" placeholder="請輸入統一編號"
                         required>
                </div>
              </div>
            </div>

            <div class="row">
              <div class="col">
                <div class="mb-3">
                  <label for="businessName" class="form-label">營業負責人:</label>
                  <input type="text" class="form-control " id="businessName"
                         name="businessName" placeholder="請輸入營業負責人" required>
                </div>
              </div>
              <div class="col">
                <div class="mb-3">
                  <label for="openYear" class="form-label">開業年份:</label>
                  <input type="text" class="form-control " id="openYear" name="openYear" placeholder="請輸入開業年份"  required>
                </div>
              </div>
              <div class="col">
                <div class="mb-3">
                  <label for="tel" class="form-label">電話:</label>
                  <input type="text" class="form-control " id="tel" name="tel" placeholder="請輸入電話" required/>
                </div>
              </div>
            </div>


            <!--              tw-city-selector-->
            <div class="city-selector-set-has-value">
              <div class="row">
                <div class="col">
                  <div class="mb-3">
                    <label for="country" class="form-label">國家:</label>
                    <select class="form-select mb-3" aria-label=".form-select-sm example"
                            id="country" name="country" required>
                      <option value="台灣">台灣</option>
                    </select>
                  </div>
                </div>

                <div class="col">
                  <div class="mb-3">
                    <label for="city" class="form-label">縣市:</label>
                    <select class="form-select county" id="city" name="city" required/>
                  </div>
                </div>
                <div class="col">
                  <div class="mb-3">
                    <label for="region" class="form-label">鄉/鎮/區:</label>
                    <select class="form-select district" id="region" name="region" required/>
                  </div>
                </div>
                <div class="col">
                  <div class="mb-3">
                    <label for="postalCode" class="form-label ">郵遞區號:</label>
                    <input type="text" class="form-control zipcode" id="postalCode" name="postalCode"
                           size="3" readonly placeholder="郵遞區號"/>
                  </div>
                </div>
              </div>

            </div>
            <!--              tw-city-selector-->

            <div class="mb-3">
              <label for="street">詳細地址:</label>
              <input type="text" name="street" placeholder="請輸入詳細地址" id="street" class="form-control " required/>
            </div>


          </div>
        </div>

      </form>
    </div>
  </div>

</template>

<script setup>
import axios from "axios";
import {useRouter} from 'vue-router';
import TwCitySelector from 'tw-city-selector';

const URL = import.meta.env.VITE_API_JAVAURL;
const router = useRouter();
new TwCitySelector({
  el: '.city-selector-set-has-value',
  elCounty: '.county', // 在 el 裡查找 element
  elDistrict: '.district', // 在 el 裡查找 element
  elZipcode: '.zipcode', // 在 el 裡查找 element
  countyFieldName: 'city', // 在 el 裡查找 element
  districtFieldName: 'region', // 在 el 裡查找 element
  zipcodeFieldName: 'postalCode', // 在 el 裡查找 element
});

const sendForm = async () => {
  const sendConfirm = confirm('是否送出?');
  if (sendConfirm) {
    const form = document.getElementById('insertHotelForm');
    const formData = new FormData(form);

    try {
      const response = await axios.post(`${URL}hotel/add`, formData, {withCredentials: true});

      if (response.status === 200) {
        alert('新增成功');
        window.location.href = '/hotel/chooseHotel';
      } else {
        alert('新增失敗');
      }
    } catch (error) {
      console.log(error);
      alert('發生錯誤，請再試一次');
    }
  }
}
</script>

<style scoped>

</style>