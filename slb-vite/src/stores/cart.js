import { defineStore } from 'pinia';

export const useCartStore = defineStore({
    id: 'cart',
    state: () => ({
        cartItems: [],
    }),
    actions: {
        addCartItem(cartItem) {
            this.cartItems.push(cartItem);
        },
        removeCartItem(cartItem) {
            const index = this.cartItems.indexOf(cartItem);
            if (index !== -1) {
                this.cartItems.splice(index, 1);
            }
        },
        clearCart() {
            this.cartItems = [];
        },
    },
});