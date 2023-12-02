<template>
    <br>
    <br>
    <br>
    <br>
    <h1>密碼重新設定</h1>
    <div class="card">
        <div v-if="verifymail" style="margin: 10px;">
            <div class="col-md-4">
                <label class="form-label">請輸入您的電子信箱</label>
                <input type="email" class="form-control" v-model=inputEmail required />

            </div>
            <button class="btn btn-primary btn-sm" style="padding: 0px;" type="button" id="btnout" :disabled="mailButton"
                @click="sendemail">送出</button>
        </div>

        <div v-else style="padding-left: 10px;">
            <div class="col-md-7">
                <p style="background-color: azure;font-size: x-large;">
                    <strong>要驗證的電子信箱：{{ inputEmail }}</strong>
                </p>
            </div>
            <form class="row">
                <div class="col-md-4">
                    <label class="form-label">驗證碼：</label>
                    <input type="text" class="form-control" v-model="verificationCode" required>
                    <label class="form-label">新密碼：</label>
                    <input type="password" class="form-control" v-model="newPwd" required id="newpwd">
                    <label class="form-label">確認密碼：</label>
                    <input type="password" class="form-control" v-model="rePwd" required id="repwd">
                    <span>{{ pwdMessage }}</span>
                </div>

                <div>
                    <button class="btn btn-primary btn-sm col-md-1" style="padding: 0px;" type="button" id="btnreset"
                        @click="resetpwd" :disabled="isButtonDisabled">送出</button>
                </div>
                <p class="form-label" style="padding-top: 20px;"> 返回
                    <router-link to="/login">
                        <a>登入</a>
                    </router-link>
                </p>

                <p class="form-label"> 沒有驗證碼?
                    <a @click="setPWD" href="">取得驗證碼</a>
                </p>
            </form>
        </div>
        <span class="" style="padding-top: 10px; padding-left: 10px; color: red;">{{ emailMessage }}</span>

    </div>
</template>
    
<script setup>
import { ref, computed } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
import Swal from 'sweetalert2';

const URLL = import.meta.env.VITE_API_JAVAURL;
const router = useRouter();
const verifymail = ref(true);
const inputEmail = ref('');
const emailMessage = ref('');
const verificationCode = ref('');
const newPwd = ref('');
const rePwd = ref('');
const pwdMessage = computed(() => (passwordsMatch.value ? '新密碼和再次輸入的密碼一致' : '新密碼和再次輸入的密碼不一致'));
const passwordsMatch = computed(() => newPwd.value === rePwd.value);
const mailButton = ref(false);

const sendemail = async () => {
    mailButton.value = true;
    const emailPattern = /^[A-Za-z0-9+_.-]+@(.+)$/;
    if (emailPattern.test(inputEmail.value)) {
        console.log(inputEmail.value);

        emailMessage.value = "";
        const formData = new FormData();
        formData.append('email', inputEmail.value);
        console.log(inputEmail.value);
        try {
            const response = await axios.post(`${URLL}customer/forget_password`, formData, { withCredentials: true });
            if (response.data === 'Y') {
                verifymail.value = false;
                emailMessage.value = '驗證碼已傳送至信箱'
            } else {
                mailButton.value = false;
                emailMessage.value = '此Email尚未註冊';
                verifymail.value = true;
            }
        }
        catch (error) {
            console.error('檢查時出錯:', error);
        }
    } else {
        emailMessage.value = '請輸入有效的Email格式';
        verifymail.value = true;
        mailButton.value = false;
    }
}

const isButtonDisabled = computed(() => {
    return !verificationCode.value || !newPwd.value || !rePwd.value || newPwd.value !== rePwd.value;
});

const resetpwd = async () => {
    try {
        const formData = new FormData();
        formData.append('verificationCode', verificationCode.value);
        formData.append('newPwd', newPwd.value);

        const response = await axios.post(`${URLL}customer/reset_password`, formData, { withCredentials: true });

        if (response.data === 'Y') {
            emailMessage.value = '';
            showRightAlert();
            // alert('密碼重置成功')
            await router.push('/login');
        } else if (response.data === 'N') {
            // emailMessage.value = '驗證碼錯誤';
            showErrorAlert();
        }
    } catch (error) {
        console.error('AJAX error:', error);
    }
}

const showErrorAlert = () => {
    Swal.fire({
        icon: 'error',
        title: '錯誤',
        text: '驗證碼錯誤。',
        confirmButtonColor: '#3085d6',
        confirmButtonText: '確認',
    });
};

const showRightAlert = () => {
    Swal.fire({
        icon: 'success',
        text: '密碼重置成功。'
    });
}

const setPWD = () => {
    verifymail.value = true;
}


</script>
    
<style>
#btnout {
    width: 10%;
}
</style>