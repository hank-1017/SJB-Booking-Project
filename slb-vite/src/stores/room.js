import { defineStore } from 'pinia';

export const useRoomStore = defineStore({
  id: 'room',
  state: () => ({
    hotelID: null,
    productID: null,
  }),
  actions: {
    setRoomIds(ids) {
      this.hotelID = ids.hotelID;
      this.productID = ids.productID;
    },
  },
});