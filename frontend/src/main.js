import Vue from "vue";
import App from "./App.vue";

// Import the router for vue.
import router from "./router";

// Import and use bootstrap and icons.
import { BootstrapVue, BootstrapVueIcons } from "bootstrap-vue";
Vue.use(BootstrapVue);
Vue.use(BootstrapVueIcons);

// Import the Vuex store for global functions and variables.
import store from "./store.js";

Vue.config.productionTip = false;

new Vue({
  router,
  store: store,
  render: (h) => h(App),
}).$mount("#app");
