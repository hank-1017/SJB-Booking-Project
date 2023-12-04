<template>
  <section class="section">
    <div class="container py-5">
      <h2>商家訊息</h2>
      <div class="row">
        <div class="col-md-12">

          <div class="card" id="chat3" style="border-radius: 15px;">
            <div class="card-body">

              <div class="row">
                <div class="col-md-6 col-lg-5 col-xl-4 mb-4 mb-md-0">

                  <h6>您的ID: U{{ inputMessage.loginID }}</h6>
                  <div class="p-3">
                    <div class="input-group rounded mb-3">
                      <div class="input-group-prepend">
                        <span class="input-group-text" id="search-addon">H</span>
                      </div>
                      <input v-model="newChatID" type="number" class="form-control" placeholder="請輸入商家ID"
                             aria-label="Search"
                             aria-describedby="search-addon"/>
                      <span class="input-group-text border-0" id="search-addon">
                      <button @click="newChat"><i class="fas fa-plus"></i></button>
                    </span>
                    </div>

                    <div data-mdb-perfect-scrollbar="true" style="position: relative; height: 400px;overflow-y: auto">
                      <ul class="list-unstyled mb-0">


                        <div v-for="msg in messageList">
                          <li class="p-2 border-bottom">
                            <a href="#!" @click="changeChat(msg)" class="d-flex justify-content-between">
                              <div class="d-flex flex-row">
                                <div>
                                  <img
                                      :src="hotelHeadshotURL+msg.hotelID"
                                      alt="avatar" class=" rounded-circle me-lg-2" style="width: 50px; height: 50px;">
                                  <span class="badge bg-success badge-dot"></span>
                                </div>
                                <div class="pt-1">
                                  <p class="fw-bold mb-0">{{ msg.hotelName }}</p>
                                  <p class="small text-muted">H{{ msg.hotelID }}(商家)</p>
                                </div>
                              </div>
                              <div class="pt-1">
                                <p class="small text-muted mb-1">現在</p>
                                <span class="badge bg-danger rounded-pill float-end">new</span>
                              </div>
                            </a>
                          </li>
                        </div>


                      </ul>
                    </div>

                  </div>

                </div>


                <div class="col-md-6 col-lg-7 col-xl-8">
                  <h6>To: {{ inputMessage.hotelName }} (H{{ inputMessage.hotelID }})</h6>

                  <div class="pt-3 pe-3 message-container" data-mdb-perfect-scrollbar="true"
                       style="position: relative; height: 500px;overflow-y: auto">

                    <!--資料庫訊息-->
                    <div v-if="oldMessages" v-for="msg in oldMessages">
                      <div class="d-flex flex-row"
                           :class="msg.sender[0] === 'U' ? 'justify-content-end' : 'justify-content-start'">
                        <img v-if="msg.sender[0] === 'H'" :src="hotelHeadshotURL+msg.hotelID"
                             alt="avatar 1" style="width: 40px; height: 40px" class=" rounded-circle me-lg-2">
                        <div style="margin-right: 20px;">
                          <p class="small p-2 ms-3 mb-1 rounded-3" style=""
                             :class="msg.sender[0] === 'U' ? 'text-white' : ''"
                             :style="msg.sender[0] === 'U' ? 'background-color: dodgerblue;' : 'background-color: #f5f6f7;'">
                            {{ msg.content }}</p>
                          <p class="small ms-3 mb-3 rounded-3 text-muted float-end"> {{ msg.messageTime }}</p>
                        </div>
                        <img v-if="msg.sender[0] === 'U'" :src="customerHeadshotURL+msg.loginID"
                             alt="avatar 1" style="width: 40px; height: 40px" class=" rounded-circle me-lg-2">
                      </div>
                    </div>
                    <!--資料庫訊息 end-->
                    <!--websocket訊息-->
                    <div v-for="msg in messages">


                      <div class="d-flex flex-row" v-if="msg"
                           :class="msg.type === 'U' ? 'justify-content-end' : 'justify-content-start'">

                        <img v-if="msg.type === 'H'" :src="hotelHeadshotURL+inputMessage.hotelID"
                             alt="avatar 1" style="width: 40px; height: 40px" class=" rounded-circle me-lg-2">


                        <div style="margin-right: 20px;">
                          <p class="small p-2 ms-3 mb-1 rounded-3" style=""
                             :class="msg.type === 'U' ? 'text-white' : ''"
                             :style="msg.type === 'U' ? 'background-color: dodgerblue;' : 'background-color: #f5f6f7;'">
                            {{ msg.message }}</p>
                          <p class="small ms-3 mb-3 rounded-3 text-muted float-end">{{ msg.time }} </p>
                        </div>

                        <img v-if="msg.type === 'U'" :src="customerHeadshotURL+inputMessage.loginID"
                             alt="avatar 1" style="width: 40px; height: 40px" class=" rounded-circle me-lg-2">
                      </div>
                    </div>
                    <!--websocket訊息 end-->
                  </div>

                  <div class="text-muted d-flex justify-content-start align-items-center pe-3 pt-3 mt-2">
                    <img :src="customerHeadshotURL+inputMessage.loginID"
                         alt="avatar 3" style="width: 40px; height: 40px" class=" rounded-circle me-lg-2">
                    <input type="text" @keydown="enterSend" class="form-control form-control-lg"
                           id="exampleFormControlInput2"
                           placeholder="說點什麼吧" v-model="inputMessage.content">
                    <a class="ms-1 text-muted" href="#!"><i class="fas fa-paperclip"></i></a>
                    <a class="ms-3 text-muted" href="#!"><i class="fas fa-smile"></i></a>
                    <a class="ms-3" href="#!" @click="addMessage"><i class="fas fa-paper-plane"></i></a>
                  </div>

                </div>
              </div>

            </div>
          </div>

        </div>
      </div>

    </div>
    <!--========== SCROLL UP ==========-->
    <a href="#" class="scrollup" id="scroll-up">
      <i class="ri-arrow-up-line scrollup__icon"></i>
    </a>
  </section>


</template>

<script setup>
import {onMounted, onUnmounted, ref, watch} from 'vue';
import axios from 'axios';
import {useRouter} from 'vue-router'
import Swal from "sweetalert2";


const router = useRouter()
const URL = import.meta.env.VITE_API_JAVAURL;
const wsURL = import.meta.env.VITE_API_WSURL;
const messages = ref([]);
const oldMessages = ref([]);
const messageList = ref([]);
const newChatID = ref(null);
const inputMessage = ref({
  content: "",
  hotelID: sessionStorage.getItem("HotelMessageID"),
  hotelName: sessionStorage.getItem("HotelName"),
  loginID: sessionStorage.getItem("CustomerMessageID"),
});
const hotelHeadshotURL = ref(`${URL}hotel/getHeadshot/`);
const customerHeadshotURL = ref(`${URL}getheadshot/`);
const getLoginID = async () => {
  try {
    const res = await axios.get(`${URL}message/getSessionCustomerID`, {withCredentials: true});

    if (res.data === null) {
      await Swal.fire(
          '請先登入',
          '',
          'error'
      )
      window.location.href = '/';
    }
    sessionStorage.setItem('CustomerMessageID', res.data)
  } catch {
    await Swal.fire(
        '請先登入',
        '',
        'error'
    )
    window.location.href = '/';
  }

}

onMounted(async () => {
  await getLoginID();
  await getMessagesList();
  await getMessagesSave();
  await initWebSocket();
  scrollToBottom();
});

onUnmounted(() => {
  socket.close();
})


let socket;
const initWebSocket = async () => {
  const webSocket = new WebSocket(`${wsURL}1/${inputMessage.value.loginID}/${inputMessage.value.hotelID}`);
  // console.log("initWebSocket"+`${wsURL}1/${inputMessage.value.loginID}/${inputMessage.value.hotelID}`)
  //開啟
  webSocket.onopen = function () {
    webSocketOpen();
  };
  //接收訊息
  webSocket.onmessage = function (e) {
    webSocketOnMessage(e);
  };
  //關閉
  webSocket.onclose = function (e) {
    webSocketOnClose(e);
  };
  //連線失敗
  webSocket.onerror = function (e) {
    webSocketonError(e);
  };

  socket = webSocket;

}

const checkSocketState = () => {
  const socketState = socket.readyState
  console.log("socketState: " + socketState)
}

const webSocketOpen = () => {
  const socketState = socket.readyState
  console.log("socketState: " + socketState)
  if (socketState !== 1) {
    Swal.fire(
        '連線失敗，請稍後在試',
        '連線發生問題',
        'error'
    )
    window.location.href = '/';
  }
  scrollToBottom();
};

const webSocketOnMessage = (event) => {
  console.log("webSocketOnMessage: " + event.data)
  const parts = event.data.split(': '); // ['U10000003', 'AFASDFAD#2023/11/11 12:22:22']
  const type = parts[0][0]; // 'U'
  const id = parts[0].substring(1); // '10000003'
  const message = parts[1].split('#')[0]; // 'AFASDFAD'
  const time = parts[1].split('#')[1]; // '2023/11/11 12:22:22'
  const msg = {
    type: type,
    id: id,
    message: message,
    time: time
  }
  messages.value.push(msg);
  scrollToBottom();

}

const webSocketonError = (event) => {
  messages.value.push(event.data)
  scrollToBottom();
}

const webSocketOnClose = (event) => {
  messages.value.push(event.data)
  scrollToBottom();
};


const newChat = async () => {
  console.log("call newChat")
  const hotelID = newChatID.value;
  if (hotelID !== null) {
    const isValid = /^\d{8}$/.test(hotelID.toString());
    if (!isValid) {
      await Swal.fire(
          '顧客ID格式不正確',
          '輸入的格式不符規定',
          'error'
      )
      newChatID.value = null; // 清空输入框
      return;
    }
  }
  const res = await axios.get(`${URL}hotel/checkHotelExist/${hotelID}`, {withCredentials: true});
  if (res.data === false) {
    await Swal.fire(
        '查無此帳號',
        '請重新輸入',
        'error'
    )
    return;
  }
  const hotelName = await getHotelName(hotelID);
  const msg = {
    hotelID: hotelID,
    hotelName: hotelName,
  }

  await changeChat(msg);
  inputMessage.value.content = "已開始新的對話";
  await saveMessage();
  inputMessage.value.content = "";
  scrollToBottom();
}

const getHotelName = async (id) => {
  const hotelName = await axios.get(`${URL}hotel/getHotelName/${id}`, {withCredentials: true});
  console.log("hotelName: " + hotelName.data)
  return hotelName.data;
}

const changeChat = async (id) => {
  console.log("call changeChat")
  sessionStorage.setItem('HotelMessageID', id.hotelID)
  sessionStorage.setItem('HotelName', id.hotelName)
  inputMessage.value.hotelID = id.hotelID;
  inputMessage.value.hotelName = id.hotelName;
  messages.value = [];
  socket.close();
  console.log('socketState: ' + socket.readyState)
  await getMessagesSave();
  await initWebSocket();
  scrollToBottom();
  // window.location.reload();
}


const getMessagesList = async () => {
  const getMessagesAPI = `${URL}message/getMessageListForCustomer`;
  const response = await axios.get(getMessagesAPI, {withCredentials: true});
  if (response.status === 200) {
    if (response.data.length === 0) {
      console.log("messageList: nulllllll")
      await Swal.fire(
          '您目前沒有任何訊息',
          '',
          'info'
      )
      return;
    }
    messageList.value = response.data;
    // sessionStorage.setItem('HotelMessageID', messageList.value[0].hotelID)
    // inputMessage.value.hotelID = messageList.value[0].hotelID;
  }
}

const saveMessage = async () => {
  console.log("call saveMessage: " + inputMessage.value.content)
  const formData = new FormData();
  formData.append("content", inputMessage.value.content);
  formData.append("loginID", inputMessage.value.loginID);
  formData.append("hotelID", inputMessage.value.hotelID);
  formData.append("sender", 'U' + inputMessage.value.loginID);

  const saveMessageAPI = `${URL}message/saveMessage`;
  const response = await axios.post(saveMessageAPI, formData, {withCredentials: true});
  if (response.status === 200) {
    console.log("saveMessage success")
  }
}
const getMessagesSave = async () => {
  const getMessagesAPI = `${URL}message/getByCustomer/${inputMessage.value.hotelID}`;
  const response = await axios.get(getMessagesAPI, {withCredentials: true});
  if (response.status === 200) {
    oldMessages.value = response.data;
  }
}

const scrollToBottom = () => {
  const messageContainer = document.querySelector('.message-container')
  messageContainer.scrollTop = messageContainer.scrollHeight
}

const enterSend = async (e) => {
  if (e.keyCode === 13) {//enter
    await addMessage()
    scrollToBottom();
  }
}

const addMessage = async () => {
  console.log("call addMessage")
  if (inputMessage.value.content === "") {
    return;
  }
  const msg = {
    type: "U",
    id: inputMessage.value.loginID,
    message: inputMessage.value.content,
    time: new Date().toLocaleString()
  }
  messages.value.push(msg);
  await saveMessage();
  socket.send(inputMessage.value.content);
  scrollToBottom();
  inputMessage.value.content = "";
};

</script>

<style scoped>
.status-left {
  text-align: left;
}

.status-right {
  text-align: right;
}

h6 {
  margin-top: 0;
  margin-bottom: 0;
}


</style>