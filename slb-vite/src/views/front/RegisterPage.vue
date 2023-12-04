<template>
  <br>
  <br>
  <br>
  <div class="container mt-5">
    <h1 class="mb-4">註冊頁面</h1>

    <div class="card">
      <div class="card-header">註冊</div>
      <div class="card-body">
        <form @submit.prevent="validateAndSubmit" id="register">

          <div class="row mb-3">
            <div class="col-6">
              <label class="form-label">選擇大頭照</label>
              <input type="file" @change="handleFileChange" id="headshot" name="headshot" accept="image/*"
                class="form-control">
            </div>
            <div class="col-6" id="imagePreview">
              <div class="wraper" style="margin-bottom: 20px;">
                <div class="image-container">
                  <img :src="user.avatar" v-if="user.avatar" alt="上傳的圖片" class="rounded mx-auto d-block"
                    style="max-width: 100%; max-height: 100%;">
                </div>
              </div>
            </div>
          </div>

          <div class="row mb-3">
            <div class="col-6">
              <label class="form-label">電子信箱</label>
              <input v-model="user.email" name="email" @blur="checkDuplicateEmail" id="email" class="form-control">
              <span v-if="emailMessage" class="error">{{ emailMessage }}</span>
            </div>

            <div class="col-6">
              <label class="form-label">密碼</label>
              <input v-model="user.password" name="password" @blur="checkPassword" id="password" type="password"
                class="form-control">
              <span v-if="passwordHelpBlock" class="error">{{ passwordHelpBlock }}</span>
            </div>
          </div>

          

          <div class="mb-3">
            <label class="form-label">姓名</label>
            <input name="customerName" class="form-control">
          </div>

          <div class="col-md-1">
            <label class="form-label">性別</label>
            <div class="col-12" style="padding: 0px;">
              <input :value="user.gender" name="sex" id="gender" class="form-control" style="margin-bottom: 5px;"
                readonly>
              <button type="button" @click="showGenderSelect" id="selectSexButton" class="button"
                style="padding: 5px;">選擇性别</button>
              <!-- btn btn-primary btn btn-outline-secondary -->
            </div>
          </div>

          <div class="mb-3" style="padding-top: 20px;">
            <label class="form-label">出生日期</label>
            <input name="birthday" type="date" class="form-control">
          </div>

          <div class="mb-3">
            <label class="form-label">電話</label>
            <input name="phone" class="form-control">
          </div>

          <!-- Add the rest of your form fields here -->

          <div class="city-selector-set">
            <div class="form-group">
              <label>國家</label>
              <select class="form-select country" id="country" name="country">
                <option value="台灣">台灣</option>
              </select>
            </div>
            <br>
            <div class="form-group">
              <label>城市</label>
              <select class="form-select county" id="city" name="city" />
            </div>
            <br>
            <div class="form-group">
              <label>區域</label>
              <select class="form-select district" id="region" name="region" />
            </div>
            <br>
            <br>
            <div class="form-group mb-3">
              <label class="form-label">郵遞區號</label>
              <input class="zipcode form-control" type="text" size="3" placeholder="郵遞區號" name="postalCode" />
            </div>
            <div class="mb-3">
              <label class="form-label">詳細地址</label>
              <input class="form-control" name="street" />
            </div>
          </div>
          <button type="submit" class="button">提交</button>
        </form>

      </div>
    </div>

    <p v-if="submitError" class="error mt-3">{{ submitError }}</p>
  </div>
</template>

<script setup up>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';
import Swal from 'sweetalert2';
import TwCitySelector from 'tw-city-selector';

const URLL = import.meta.env.VITE_API_JAVAURL;
const router = useRouter();
const user = ref({
  email: '',
  password: '',
  gender: '',
  avatar: '',
});
const emailMessage = ref('');
const emailcheck = ref(false);
const passwordHelpBlock = ref('');
const passwordcheck = ref(false);
const submitError = ref(null);
const inputHeadshot = ref(null);




new TwCitySelector({
  el: '.city-selector-set',
  elCounty: '.county', // 在 el 裡查找 element
  elDistrict: '.district', // 在 el 裡查找 element
  elZipcode: '.zipcode', // 在 el 裡查找 element
  countyFieldName: 'city', // 在 el 裡查找 element
  districtFieldName: 'region', // 在 el 裡查找 element
  zipcodeFieldName: 'postalCode', // 在 el 裡查找 element
});


const checkDuplicateEmail = async () => {
  const emailPattern = /^[A-Za-z0-9+_.-]+@(.+)$/;
  if (emailPattern.test(email.value)) {
    console.log(email.value);
    // 有效的email格式
    // 继续进行后续的重复检查
    emailMessage.value = "";
    console.log("123123123123123456")
    const formData = new FormData();
    formData.append('email', user.value.email);
    console.log(user.value.email);
    try {
      const response = await axios.post(`${URLL}checkEmail`, formData, { withCredentials: true });
      if (response.data === 'duplicate') {
        emailMessage.value = '此Email已使用。';

      } else {
        emailMessage.value = '此Email可以使用';
        emailcheck.value = true;
      }
    } catch (error) {
      console.error('检查重複Email時出錯:', error);
    }
  } else {
    // 无效的email格式
    emailMessage.value = '請輸入有效的Email格式';
  }
};


const checkPassword = () => {
  const passwordPattern = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,20}$/;
  if (!passwordPattern.test(password.value)) {
    // 无效的密码格式
    passwordHelpBlock.value = "密碼格式設置錯誤，長度必須為 8-20 個字符，包含字母和數字，且不得包含空格及表情符號。";
  } else {
    // 有效的密码格式
    passwordHelpBlock.value = "合理的密碼";
    passwordcheck.value = true;
  }
};

const showCustomAlert = () => {
  Swal.fire({
    icon: 'error',
    title: '錯誤',
    text: '請確認信箱或密碼格式是否有誤。',
    confirmButtonColor: '#3085d6',
    confirmButtonText: '確認',
  });
};

const inputOptions = {
  '男': '男',
  '女': '女',
  '其他': '其他'
};

const showGenderSelect = async () => {

  const { value: selectedGender } = await Swal.fire({
    title: '選擇性別',
    input: 'radio',
    inputOptions: inputOptions,
    inputValidator: (value) => {
      if (!value) {
        return '請選擇性別!';
      }
    }
  });
  console.log(selectedGender);

  if (selectedGender !== undefined && selectedGender != null) {
    user.value.gender = selectedGender;
  }
};

// const openFileInput = (event) => {
//     event.preventDefault();
//     document.getElementById('headshot').click();
// };

const handleFileChange = (event) => {
  const file = event.target.files[0];
  if (file) {
    const reader = new FileReader();
    reader.onload = (e) => {
      Swal.fire({
        title: '您上傳的圖片',
        imageUrl: e.target.result,
        imageAlt: '上傳的圖片'
      });
    };
    reader.readAsDataURL(file);
    user.value.avatar = URL.createObjectURL(file); // 更新用户头像
    inputHeadshot.value = file;
    console.log(user.value.avatar);
  }
};

const validateAndSubmit = async () => {
  // event.preventDefault();
  await checkDuplicateEmail();
  checkPassword();
  const form = document.getElementById('register');
  const formData = new FormData(form);
  formData.append('headshot', inputHeadshot.value);

  if (emailcheck.value && passwordHelpBlock.value) {
    try {
      // 使用 Axios 將 FormData 對象發送到後端
      console.log(formData);
      console.log(user.value.password);
      const response = await axios.post(`${URLL}user/register`, formData, { withCredentials: true },
      );
      console.log(response.status.valueOf);

      if (response.status === 200) {
        alert("註冊成功")
        goToLogin();
        // 註冊成功
        // 可能導向登錄頁面或顯示成功訊息
      } else {
        alert("註冊失敗")
        // 註冊失敗
        // 可能顯示錯誤訊息
      }
    } catch (error) {
      alert("系統錯誤")
      console.error('發送到後端時出現錯誤:', error);
    }
  } else {
    // 驗證失敗，顯示錯誤訊息
    if (!emailMessage.value) {
      emailMessage.value = "請輸入有效的Email格式";
    } else {
      emailMessage.value = "";
    }

    if (!passwordHelpBlock.value) {
      passwordHelpBlock.value = "密碼設置錯誤，長度必須為 8-20 個字元，包含字母和數字，且不得包含空格及表情符號。";
    } else {
      passwordHelpBlock.value = "";
    }
    showCustomAlert();
  }
};

const goToLogin = () => {
  router.push('/login');
};

// const submitUser = async{

// };
</script>

<style scoped>
.error {
  color: red;
  margin-top: 10px;
  /* 在错误消息上方添加一些垂直间距 */
}

.image-container {
  height: 200px;
  /* 根据您希望的高度调整 */
  max-width: 100%;
  /* 图像最大宽度为其容器宽度 */
  max-height: 100%;
  /* 图像最大高度为其容器高度 */
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;
}

.image-container img {
  object-fit: contain;
  /* 图像等比例缩放以适应容器 */
  max-width: 100%;
  /* 图像最大宽度为100% */
  max-height: 100%;
  /* 图像最大高度为100% */
}
</style>