<template>
  <div class="container">

    <FrontNavbar/>

    <main class="main">
      <router-view/>
    </main>
    <Footer/>
  </div>
</template>

<script setup>
import {ref, onMounted} from 'vue';
import FrontNavbar from '@/components/FrontNavbar.vue';
import Footer from '@/components/Footer_front.vue';
import '@/assets/js/main_front.js';



onMounted(() => {
  const link = document.createElement('link')
  link.href = '/src/assets/css/styles.css'
  link.rel = 'stylesheet'
  document.head.appendChild(link)
});

</script>


