import Vue from "vue";
import Vuex from 'vuex'
import App from "./App.vue";

// Import the router for vue.
import router from "./router";

// Import Bootstrap.
import { BootstrapVue, BootstrapVueIcons } from "bootstrap-vue";

Vue.use(BootstrapVue);
Vue.use(BootstrapVueIcons);
Vue.use(Vuex);

Vue.config.productionTip = false;

// Vue.prototype.$backendUrl = "http://localhost:8080/"
// Vue.prototype.$currentUser = 'testuser01';

// Using Vuex: Global variables to switch between user.
const store = new Vuex.Store({
  state: {
    currentUser: "testuser01/",
    backendUrl: "http://localhost:8080/",
  },
  mutations: {
    setUser01 (state) {
      state.currentUser = "testuser01/";
    },
    setUser02 (state) {
      state.currentUser = "testuser02/";
    },
  },
  methods: {
    setUser01() {
      this.$store.commit('setUser01')
    },
    setUser02() {
      this.$store.commit('setUser02')
    }
  },
})


new Vue({
  router,
  store: store,
  render: (h) => h(App),
}).$mount("#app");
