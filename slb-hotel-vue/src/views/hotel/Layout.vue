<template>
  <div class="content">
     <Loading v-if="isLoading" />
    <Navbar v-if="showNavbar" />
    <router-view />
    <Footer v-if="showFooter" />
  </div>
</template>
    
<script>
import Navbar from '@/components/Navbar.vue';
import Footer from '@/components/Footer.vue';
import Loading from '@/components/Loading.vue';
// import '@/assets/js/main.js';

export default {
  components: {
    Loading,
    Navbar,
    Footer,
  },
  mounted() {
    const link = document.createElement('link')
    link.href = '/src/assets/css/style.css'
    link.rel = 'stylesheet'
    document.head.appendChild(link)
  },
  data() {
    return {
      showNavbar: true, // 是否顯示Navbar
      showFooter: true, // 是否顯示Footer
      isLoading: false, // 是否顯示Loading
    };
  },
};
</script>


<style scoped>

</style>