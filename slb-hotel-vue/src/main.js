import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import vue3GoogleLogin from 'vue3-google-login'
import { createPinia } from 'pinia'




// import 'bootstrap/dist/css/bootstrap.min.css'
// import '@fortawesome/fontawesome-free/css/all.min.css'
// import 'bootstrap-icons/font/bootstrap-icons.css'
// import '@/assets/lib/owlcarousel/assets/owl.carousel.min.css'
// import 'bootstrap/dist/js/bootstrap.js'
// import '@/assets/lib/tempusdominus/css/tempusdominus-bootstrap-4.min.css'
// import '@/assets/lib/waypoints/waypoints.min.js'
// import 'jquery/dist/jquery.min.js'
// import '@/assets/lib/owlcarousel/owl.carousel.min.js'
// import '@/assets/lib/easing/easing.min.js'
// import 'bootstrap/dist/js/bootstrap.bundle.min.js'
// import '@/assets/lib/chart/chart.min.js'
// import '@/assets/js/main.js'
// import '@/assets/css/style.css'


// import '@/assets/lib/tempusdominus/js/moment.min.js'
// import '@/assets/lib/tempusdominus/js/moment-timezone.min.js'
// import '@/assets/lib/tempusdominus/js/tempusdominus-bootstrap-4.min.js'

const pinia = createPinia()
createApp(App).use(vue3GoogleLogin, { clientId: '61997342065-tgir9p3pmadvvutujjb7of08refefrj3.apps.googleusercontent.com' })
                         .use(router)
                         .use(pinia)
                        .mount('#app')


