import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import vue3GoogleLogin from 'vue3-google-login'
import { createPinia } from 'pinia'


const pinia = createPinia()
createApp(App).use(vue3GoogleLogin, { clientId: '61997342065-tgir9p3pmadvvutujjb7of08refefrj3.apps.googleusercontent.com' })
                         .use(router)
                         .use(pinia)
                        .mount('#app')


