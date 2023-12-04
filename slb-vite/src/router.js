import {createRouter, createWebHistory} from 'vue-router'
import NotFoundPage from '@/views/hotel/NotFoundPage.vue';
import HotelLoginPage from '@/views/hotel/LoginPage.vue';
import ChooseHotel from '@/views/hotel/ChooseHotel.vue';
import HotelDashboard from '@/views/hotel/HotelDashboard.vue';
import HotelSetaccount from '@/views/hotel/HotelSetaccount.vue';
import HotelUpdate from '@/views/hotel/HotelUpdate.vue';
import HotelStartRegister from '@/views/hotel/HotelStartRegister.vue';
import HotelForgetPwd from '@/views/hotel/ForgetPWD.vue';
import HotelRestPwd from '@/views/hotel/ResetPwdPage.vue';
import HotelCreateAccount from '@/views/hotel/HotelCreateAccount.vue';
import HotelAdd from '@/views/hotel/HotelAdd.vue';
import HotelService from "@/views/hotel/HotelService.vue";
import AllHotel from '@/views/hotel/AllHotel.vue';
import HotelDetail from '@/views/hotel/HotelDetail.vue';
import OrderManage from '@/views/hotel/OrderManage.vue';
import LayoutVue from '@/views/hotel/Layout.vue';
import LoginPageClass from '@/views/hotel/LoginPageClass.vue'
import OrderAnalysis from '@/views/hotel/OrderAnalysis.vue';

import roomManagementChooseHotel from '@/views/room/roomManagementChooseHotel.vue'
import roomManagement from '@/views/room/roomManagement.vue'
import insertRoom from '@/views/room/insertRoom.vue'
import editRoom from '@/views/room/editRoom.vue'
import roomPictureManagement from '@/views/room/roomPictureManagement.vue'
import roomPictureUpload from '@/views/room/roomPictureUpload.vue'

import FrontNavbar from '@/views/front/FrontLayout.vue';
import HomePage from '@/views/front/FrontIndex.vue';
import Register from '@/views/front/RegisterPage.vue';
import Login from '@/views/front/LoginPage.vue';
import aboutUs from '@/views/front/aboutUs.vue';
import UserInfo from '@/views/front/UserInformation.vue';
import cart from '@/views/front/cart.vue';
import privacyPolicy from '@/views/front/privacyPolicy.vue';
import FAQ from '@/views/front/FAQ.vue';
import contactUs from '@/views/front/contactUs.vue';
import oops from '@/views/front/oops.vue';
import customerCenter from '@/views/front/customerCenter.vue';
import philosophy from '@/views/front/philosophy.vue';
import thanks from '@/views/front/thanks.vue';

import createOrders from '@/views/orders/createOrders.vue'
import findOrderByCustomer from '@/views/orders/findOrderByCustomer.vue'
import orderDetail from '@/views/orders/orderDetail.vue'
import searchHotelByCheck from '@/views/front/searchHotelByCheck.vue'
import roomsByHotel from '@/views/front/roomsByHotel.vue'
import roomsDetail from '@/views/front/roomsDetail.vue'
import PaymentResult from "@/views/orders/PaymentResult.vue";
import cartCreateOrders from '@/views/orders/cartCreateOrders.vue'
import HotelNotify  from "@/views/hotel/HotelNotify.vue";
import HotelMessage from "@/views/hotel/HotelMessage.vue";
import CustomerMessage from "@/views/front/CustomerMessage.vue";
import RestPwd from "@/views/front/ResetPwdPage.vue"



const routes = [
    {
        path: '/',
        component: HomePage,
    },
    {
        path: '/',
        component: FrontNavbar,
        children: [
            {
                path: 'register',
                component: Register,
            },
            {
                path: 'login',
                name: 'login',
                component: Login,
            },
            {
                path: 'restPwd',
                component: RestPwd
            },
            {
                path: 'userInfo',
                component: UserInfo,
            },
            {
                path: 'aboutUs',
                component: aboutUs,
            },
            {
                path: 'contactUs',
                name: 'contactUs',
                component: contactUs,
            },
            {
                path: 'oops',
                name: 'oops',
                component: oops,
            },
            {
                path: 'thanks',
                name: 'thanks',
                component: thanks,
            },
            {
                path: 'customerCenter',
                component: customerCenter,
            },
            {
                path: 'philosophy',
                component: philosophy,
            },
            {
                path: 'orders/paymentResult',
                component: PaymentResult,
            },
            {
                path: 'message',
                component: CustomerMessage,
                // props: true,
            },
            {
                path: 'cart',
                name: 'cart',
                component: cart,
            },
            {
                path: '/searchHotelByCheck',
                name: 'searchHotelByCheck',
                component: searchHotelByCheck,
            },
            {
                path: '/hotel-details/:hotelID/:startDate/:endDate',
                name: 'hotel-details',
                component: roomsByHotel,
            },
            {
                path: '/roomsDetail/:roomID/:startDate/:endDate',
                name: 'roomsDetail',
                component: roomsDetail,
                props: true,
            },
            {
                path: '/hotel/orders/orderDetail/:orderID',
                name: 'orderDetail',
                component: orderDetail,
                props: true,
            },
            {
                path: '/hotel/orders/findOrderByCustomer',
                name: 'findOrderByCustomer',
                component: findOrderByCustomer,
            },
            {
                path: '/hotel/orders/createOrders/:hotelID/:productID/:price/:startDate/:endDate',
                name: 'createOrders',
                component: createOrders,
                props: true
            },
            {
                path: 'privacyPolicy',
                name: 'privacyPolicy',
                component: privacyPolicy,
            },
            {
                path: 'FAQ',
                name: 'FAQ',
                component: FAQ,
            },{
                path: '/hotel/orders/cartCreateOrders',
                name: 'cartCreateOrders',
                component: cartCreateOrders,
            },

        ]
    },
    {
        path: '/hotel/',
        component: LoginPageClass,
        children: [
            {
                path: 'login',
                component: HotelLoginPage
            },
            {
                path: 'startRegister',
                component: HotelStartRegister
            },
            {
                path: 'forgetPwd',
                component: HotelForgetPwd
            },
            {
                path: 'resetPwd',
                component: HotelRestPwd
            },
            {
                path: 'register',
                component: HotelCreateAccount
            },
            {
                path: 'chooseHotel',
                component: ChooseHotel,
            },
        ],
    },

    {
        path: '/hotel/',
        component: LayoutVue,// 導航欄组件
        children: [// 需要導航列的頁面
            {
                path: 'dashboard',
                component: HotelDashboard,
            },
            {
                path: 'setaccount',
                component: HotelSetaccount,
            },
            {
                path: 'updatehotel',
                component: HotelUpdate,
            },
            {
                path: 'addHotel',
                component: HotelAdd,
            },
            {
                path: 'allHotel',
                component: AllHotel,
            },
            {
                path: 'hotelDetail',
                component: HotelDetail,
            },
            {
                path: 'hotelService',
                component: HotelService,
            },
            {
                path: 'hotelNotify',
                component: HotelNotify,
            },
            {
                path: 'message',
                component: HotelMessage,
            },
            {
                path: 'orderManagement',
                component: OrderManage,
            },
            {
                path: 'orderAnalysis', // 子路由路径
                component: OrderAnalysis, // 子路由对应的组件
            },
            {
                path: 'dashboard/chooseHotel',
                component: roomManagementChooseHotel,
            },
            {
                path: 'room/roomManagement',
                name: 'rooms',
                component: roomManagement,
            },
            {
                path: 'room/insertRoom',
                name: 'insertRoom',
                component: insertRoom,
            },
            {
                path: 'room/editRoom',
                name: 'editRoom',
                component: editRoom,
            },
            {
                path: 'room/roomPictureManagement',
                name: 'roomPictureManagement',
                component: roomPictureManagement,
            },
            {
                path: 'room/roomPictureUpload',
                name: 'roomPictureUpload',
                component: roomPictureUpload,
            },
        ],
    },
    {
        path: '/:catchAll(.*)', // 404頁面
        component: NotFoundPage,
    },
]


const router = createRouter({
    history: createWebHistory(),
    routes
})


export default router