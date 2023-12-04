<script setup>
    import { ref, onMounted } from 'vue';
    import axios from 'axios';
    import { useRouter } from 'vue-router';

    const URL = import.meta.env.VITE_API_JAVAURL;
    const hotels = ref([]);
    const router = useRouter();

    onMounted(async () => {
        const hotelsAPI = `${URL}hotel/findAllhoteljson`;
        try {
            const responseH = await axios.get(hotelsAPI, { withCredentials: true });
            hotels.value = responseH.data;
        } catch (error) {
            console.error("Failed to load hotels: ", error);
        }
    });

    const linkToRooms = async (hotelID) => {
      const setHotelID = `${URL}hotel/setHotelID?hotelID=${hotelID}`;
      const res = await axios.get(setHotelID, {withCredentials: true});
      if (res.status === 200) {
        console.log('setHotelID: ' + res.data);
        window.location.href = '/hotel/dashboard';
      } else {
        console.error('Failed to fetch hotel detail:', res.status);
        alert('系統錯誤，請稍後再試')
      }
        
    }
</script>

<template>
    <div class="container-fluid pt-4 px-4">
        <div class="row g-4">
            <div class="col-sm-12 ">
                <div class="bg-light rounded h-100 p-4">
                    <h3 class="mb-4">飯店總覽</h3>
                    <div class="cardAlignment">
                        <div v-for="{ hotelID, hotelName, tel, country, city, region, street, postalCode, state } in hotels">
                            <div class="card" style="width: 18rem;" @click="linkToRooms(hotelID)">
                                <div id="image-container">
                                    <img src="@/assets/img/hoteltag.jpg" class="card-img-top">
                                </div>
                                <div class="card-body">
                                    <h5 class="card-title">{{ hotelName }}</h5>
                                    <p class="card-text">{{ '電話：'+ tel }}</p>
                                    <p class="card-text">{{ '所在國家：'+ country }}</p>
                                    <p class="card-text">{{ postalCode + ' ' + city + ' ' + region + street }}</p>
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
.card {
    background-color: white;
    width: 19rem;
    margin: 10px;
    height: 30rem;
    cursor: pointer
}
.cardAlignment {
    margin: auto;
    padding: auto;
    display: flex;
    flex-wrap: wrap;    
}
.card:hover {
    border: #ADADAD 3px solid;
}
</style>