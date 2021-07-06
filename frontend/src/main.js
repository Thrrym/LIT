import Vue from "vue";
// import Vuex from "vuex";
import App from "./App.vue";


// Import the router for vue.
import router from "./router";

// Import Bootstrap.
import { BootstrapVue, BootstrapVueIcons } from "bootstrap-vue";

Vue.use(BootstrapVue);
Vue.use(BootstrapVueIcons);
//Vue.use(Vuex);
import store from "./store.js";

Vue.config.productionTip = false;



new Vue({
  router,
  store: store,
  render: (h) => h(App),
}).$mount("#app");
