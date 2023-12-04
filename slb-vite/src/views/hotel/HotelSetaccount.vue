(
<template>
  <div class="container-fluid pt-4 px-4">
    <div class="row g-4">

      <div class="col-sm-12 ">
        <div class="bg-light rounded h-100 p-4">
          <h3 class="mb-4">帳號設定</h3>

          <nav>
            <div class="nav nav-tabs" id="nav-tab" role="tablist">
              <button class="nav-link active" id="nav-home-tab" data-bs-toggle="tab" data-bs-target="#nav-home"
                      type="button"
                      role="tab" aria-controls="nav-home" aria-selected="true">用戶資料
              </button>
<!--              <button class="nav-link" id="nav-profile-tab" data-bs-toggle="tab" data-bs-target="#nav-profile"-->
<!--                      type="button" role="tab" aria-controls="nav-profile" aria-selected="false">Profile-->
<!--              </button>-->
              <button class="nav-link " id="nav-contact-tab" data-bs-toggle="tab" data-bs-target="#nav-contact"
                      type="button" role="tab" aria-controls="nav-contact" aria-selected="false">社群帳號綁定
              </button>
            </div>
          </nav>

          <div class="tab-content pt-3" id="nav-tabContent">

            <div class="tab-pane fade active show" id="nav-home" role="tabpanel" aria-labelledby="nav-home-tab">
              <div v-if="changePwdB">
                <div class="row mb-3">

                  <div class="col-sm-5" style="text-align: center">
                    <a @click="insertHeadshot">
                      <img :src='headshotURL' width="200" height="200" class="rounded-circle" alt="">
                      <br>
                      <br>
                      <small>點擊變更頭貼</small>
                    </a>
                  </div>

                  <div class="col-sm-5">
                    <br>

                    <h5>用戶: {{ userInfo.loginName }}
                      <button class="btn btn-outline-primary m-2" @click="changeName" :disabled="isButtonDisabled">
                        變更名稱
                      </button>
                    </h5>
                    <h5>帳號: {{ userInfo.loginEmail }}</h5>
                    <h5>密碼:
                      <button class="btn btn-outline-primary m-2" @click="sendCode" :disabled="isButtonDisabled">
                        變更密碼
                      </button>
                    </h5>

                  </div>
                </div>
              </div>

              <form v-if="changePwdA" @submit.prevent="changePwdSubmit">
                <div class="row mb-3">
                  <div class="">

                    <div class="form-floating mb-3">
                      <input readonly type="email" class="form-control" id="floatingInput"
                             :value="userInfo.loginEmail"/>
                      <label for="floatingInput">Email address</label>
                    </div>
                    <p style="color: green; text-align: center;" v-if="mailMsg">{{ mailMsg }}</p>
                    <div class="form-floating mb-3">
                      <input type="text" class="form-control" id="verificationCode" v-model="verificationCode"/>
                      <label for="verificationCode">驗證碼</label>
                    </div>

                    <div class="form-floating mb-3">
                      <input type="password" class="form-control" id="password" v-model="inputNewPwd"/>
                      <label for="password">新密碼</label>
                    </div>

                    <div class="form-floating mb-3">
                      <input type="password" class="form-control" id="repassword" v-model="inputNewPwd"/>
                      <label for="repassword">確認密碼</label>
                    </div>

                  </div>
                </div>
                <input type="submit" class="btn btn-primary w-100 " value="送出"/>
                <br>
                <br>
                <button class="btn btn-secondary  w-100 " @click="goToSet">返回</button>
              </form>

              <!-- 提示 -->
              <br>
              <p style="color: red; text-align: center;" v-if="errMsg">{{ errMsg }}</p>
            </div>

<!--            <div class="tab-pane fade" id="nav-profile" role="tabpanel" aria-labelledby="nav-profile-tab">-->
<!--              Sanctus vero sit kasd sea gubergren takimata consetetur elitr elitr, consetetur sadipscing takimata ipsum-->
<!--              dolores. Accusam duo accusam et labore ea elitr ipsum tempor sit, dolore aliquyam ipsum sit amet sit. Et-->
<!--              dolore ipsum labore invidunt rebum. Sed dolore gubergren sanctus vero diam lorem rebum elitr, erat diam-->
<!--              dolor clita.-->
<!--            </div>-->

            <div class="tab-pane fade " id="nav-contact" role="tabpanel" aria-labelledby="nav-contact-tab">

              <div class="row-cols-sm-auto">
                <br>
                <table style="margin-bottom: 300px;">
                  <!-- Google -->
                  <tr>
                    <td>
                      <i class="bi bi-google"></i>
                      綁定Google帳號
                    </td>
                    <td>
                      <!--綁定google-->
                      <div v-if="bind_google">
                        <GoogleLogin :callback="callback"/>
                      </div>
                      <!--解除綁定-->
                      <div v-if="deactivate_google">
                        <!--                      <form id="clearGoogleIDForm">-->
                        <button @click="clearGoogleIDBtm" id="deactivate-google-btn"
                                class="btn btn-danger btn-sm rounded-pill m-2">
                          解除綁定
                        </button>
                        <!--                      </form>-->
                      </div>
                    </td>
                  </tr>
                  <tr>

                  </tr>
                  <!-- LINE -->
                  
                </table>
                <!-- 提示 -->
                <p class="text-success" id="bind_sucess_Msg"></p>
                <p class="text-danger" id="bind_danger_Msg"></p>
              </div>
            </div>

          </div>
        </div>

      </div>



    </div>
  </div>
</template>

<script setup>
import axios from "axios";
import {onMounted, ref} from 'vue';
import {decodeCredential} from "vue3-google-login"
import Swal from "sweetalert2";

const userInfo = ref({
  loginId: '',
  loginName: '',
  loginEmail: '',
});
const loggedIn = ref(false);
const changePwdB = ref(true);
const changePwdA = ref(false);
const mailMsg = ref('');
const errMsg = ref('');
const verificationCode = ref('');
const inputNewPwd = ref('');
const isButtonDisabled = ref(false);
const bind_google = ref();
const deactivate_google = ref();
const deactivate_line = ref(true);
let headshot = ref(null);
const URL = import.meta.env.VITE_API_JAVAURL;
const headshotURL = ref(`${URL}hotel/getHeadshot`);


const getLoginInfo = async () => {
  try {
    const response = await axios.post(`${URL}hotel/getLoginInfo`,null, {withCredentials: true});
    if (response.status === 200) {
      loggedIn.value = true;
      Object.assign(userInfo.value, response.data);
    } else {
      loggedIn.value = false;
    }
  } catch (error) {
    console.error('Error fetching login info:', error);
    loggedIn.value = false;
  }
}

onMounted(async () => {
  await getLoginInfo();
  await checkGoogleID();
})


const checkGoogleID = async () => {
  const response = await axios.get(`${URL}hotel/checkGoogleID`, {withCredentials: true});
  console.log('checkGoogleID: ' + response.data);
  if (response.data === 'Y') {//已綁定
    bind_google.value = false;
    deactivate_google.value = true;
  }
  if (response.data === 'N') {//未綁定
    bind_google.value = true;
    deactivate_google.value = false;
  }
}

const goToSet = () => {
  changePwdB.value = true;
  changePwdA.value = false;
}

const sendCode = async () => {
  isButtonDisabled.value = true;
  const formData = new FormData();
  formData.append('email', userInfo.value.loginEmail);
  const res = await axios.post(`${URL}hotel/forget_password`, formData, {withCredentials: true});
  if (res.data === 'Y') {
    isButtonDisabled.value = false;
    changePwdB.value = false;
    changePwdA.value = true;
    mailMsg.value = '已寄送驗證碼至信箱';
  } else {
    isButtonDisabled.value = false;
    errMsg.value = '驗證碼取得錯誤';
  }
}

const insertHeadshot = async () => {

  const {value: headshotInput} = await Swal.fire({
    title: '請選擇大頭貼',
    text: "注意: 圖檔大小需小於1MB",
    input: 'file',
    inputAttributes: {
      'accept': 'image/*',
      'aria-label': 'Upload your profile picture'
    }
  })

  if (headshotInput === null) {
    return;
  }

  try {
    const formData = new FormData();
    formData.append('headshot', headshotInput);
    const res = await axios.post(`${URL}hotel/updateHeadshot`, formData, {withCredentials: true});
    if (res.data === 'Y') {
      if (headshotInput) {
        const reader = new FileReader()
        reader.onload = (e) => {
          Swal.fire({
            title: '頭貼更新成功',
            imageUrl: e.target.result,
            imageAlt: 'The uploaded picture'
          })
        }
        reader.readAsDataURL(headshotInput)
      }
      // alert('頭貼更新成功');
      window.location.reload();
    } else {
      await Swal.fire(
          '頭貼更新失敗',
          '',
          'error'
      )
      // alert('頭貼更新失敗');
    }

  } catch (error) {
    await Swal.fire(
        '頭貼更新失敗',
        '',
        'error'
    )
    console.error('Error fetching login info:', error);
  }

}

const changeName = () => {
  Swal.fire({
    title: '請輸入新名稱',
    input: 'text',
    inputAttributes: {
      autocapitalize: 'off'
    },
    showCancelButton: true,
    confirmButtonText: '確定變更',
    cancelButtonText: '取消',
    showLoaderOnConfirm: true,
    preConfirm: async (InputloginNmae) => {
      const formData = new FormData();
      formData.append('loginName', InputloginNmae);
      const res = await axios.put(`${URL}hotel/updateLoginName`, formData, {withCredentials: true});
      if (res.data === 'Y') {
        await Swal.fire(
            '名稱更新成功',
            '',
            'success'
        )
        window.location.reload();
      } else {
        await Swal.fire(
            '名稱更新失敗',
            '',
            'error'
        )
      }
    }
  })
}


const changePwdSubmit = async () => {
  const formData = new FormData();
  formData.append('verificationCode', verificationCode.value);
  formData.append('newPwd', inputNewPwd.value);
  const res = await axios.post(`${URL}hotel/reset_password`, formData, {withCredentials: true});

  if (res.data === 'Y') {
    errMsg.value = '';
    alert('密碼重置成功')
    goToSet();
  } else if (res.data === 'N') {
    errMsg.value = '驗證碼錯誤';
  }
}

const callback = (response) => {
  const userData = decodeCredential(response.credential)
  console.log("Handle the userData", userData)
  googleBind(userData)
}

const googleBind = async (googleInfo) => {
  console.log("綁定ID: " + googleInfo.sub)
  const response = await axios.post(`${URL}hotel/bindGoogleID`, googleInfo, {withCredentials: true});


  if (response.data === 'Y') {
    bind_google.value = false;
    deactivate_google.value = true;
    await Swal.fire(
        '綁定成功',
        '你的帳號已綁定Google帳號',
        'success'
    )
  }
  if (response.data === 'N') {
    bind_google.value = true;
    deactivate_google.value = false;
    await Swal.fire(
        '綁定失敗',
        '此帳號已被使用，請使用其他帳號綁定',
        'error'
    )
  }
}

const clearGoogleIDBtm = () => {

  Swal.fire({
    title: '是否解除綁定Google帳號?',
    text: "",
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#3085d6',
    cancelButtonColor: '#d33',
    confirmButtonText: '確認',
    cancelButtonText: '取消'
  }).then(async (result) => {
    if (result.isConfirmed) {
      const res = await axios.get(`${URL}hotel/clearGoogleID`, {withCredentials: true});
      if (res.data === 'Y') {
        await Swal.fire(
            '已解除綁定 !',
            '你的帳號已解除Google綁定',
            'success'
        )
        bind_google.value = true;
        deactivate_google.value = false;
        // alert('解除綁定成功');
      } else {
        await Swal.fire(
            '解除綁定失敗 !',
            '系統錯誤，請稍後再試',
            'error'
        )
        bind_google.value = false;
        deactivate_google.value = true;
        alert('解除綁定失敗');
      }

    }
  })


}

</script>

<style>

</style>
  