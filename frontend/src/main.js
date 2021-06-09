import Vue from "vue";
import App from "./App.vue";

// Import the router for vue.
import router from "./router";

// Import Bootstrap.
import { BootstrapVue, BootstrapVueIcons } from "bootstrap-vue";

Vue.use(BootstrapVue);
Vue.use(BootstrapVueIcons);

Vue.config.productionTip = false;

Vue.prototype.$backendUrl = "http://localhost:8080/"
Vue.prototype.$currentUser = 'testuser01';

new Vue({
  router,
  render: (h) => h(App),
}).$mount("#app");
