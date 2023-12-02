<template>
  <div class="background-image">
    <div class="container-fluid position-relative d-flex p-0">
      <!-- Start -->
      <div class="container-fluid">
        <div class="row h-100 align-items-center justify-content-center" style="min-height: 100vh;">
          <div class="col-12 col-sm-8 col-md-6 col-lg-5 col-xl-4">
            <div class="bg-light rounded p-4 p-sm-5 my-4 mx-3">
              <router-view />
            </div>
          </div>
        </div>
      </div>
      <!-- End -->
    </div>
  </div>
</template>

<script >


export default {

  mounted() {
    const link = document.createElement('link')
    link.href = '/src/assets/css/style.css'
    link.rel = 'stylesheet'
    document.head.appendChild(link)
  }
};
</script>


<style scoped>
.background-image {
  /* 使用require引入背景图像 */
  background-image: url('@/assets/img/HotelBg.jpg');
  /* 设置背景尺寸、重复等样式 */
  background-size: cover;
  background-repeat: no-repeat;
}
</style>