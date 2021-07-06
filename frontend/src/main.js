import Vue from "vue";
import Vuex from "vuex";
import App from "./App.vue";

// Import the router for vue.
import router from "./router";

// Import Bootstrap.
import { BootstrapVue, BootstrapVueIcons } from "bootstrap-vue";

Vue.use(BootstrapVue);
Vue.use(BootstrapVueIcons);
Vue.use(Vuex);

Vue.config.productionTip = false;

// Using Vuex: Global variables to switch between user and backend.
const store = new Vuex.Store({
  state: {
    // The active "loggend in" user.
    currentUser: "testuser01/",
    // URL of the backend server the frontend wants to talk to. Expect the backend to run on the next port.
    backendUrl: "http://localhost:" + String(parseInt(location.port) + 1) + "/",
    // The URL of the proxy between the frontend and backend. Proxy runs on the frontend server.
    // Need to set the correct proxy handle: "/apiPORT" with PORT=port of the backend.
    proxyBackendUrl:
      "http://localhost:" +
      location.port +
      "/api" +
      String(parseInt(location.port) + 1) +
      "/",
    proxyUrl: "http://localhost:" + location.port,
    //"/api" //+
    //String(parseInt(location.port) + 1) +
    //"/",

    // Bearer token storage:
    loginStatus: "loggedOut",
    token: "",
    user: "",
  },
  mutations: {
    setUser01(state) {
      state.currentUser = "testuser01/";
    },
    setUser02(state) {
      state.currentUser = "testuser02/";
    },
    //setBackend01 (state) {
    //  state.backendUrl = "http://localhost:8080/";
    //  state.proxyBackendUrl = "http://localhost:" + location.port + "/api8080/";
    //},
    //setBackend02 (state) {
    //  state.backendUrl = "http://localhost:8081/";
    //  state.proxyBackendUrl = "http://localhost:" + location.port + "/api8081/";
    //},
    authSuccess(state) {
      state.user = localStorage.getItem("user");
      state.token = localStorage.getItem("token");
      state.loginStatus = "loggedIn";
    },
    logOut(state) {
      localStorage.removeItem("user");
      state.user = "";
      localStorage.removeItem("token")
      state.token = "";
      state.loginStatus = "loggedOut";
    }
  },
  methods: {
    setUser01() {
      this.$store.commit("setUser01");
    },
    setUser02() {
      this.$store.commit("setUser02");
    },
    authSuccess() {
      this.$store.commit("authSuccess");
    },
    logOut() {
      this.$store.commit("logout");
    }
    /*setBackend01() {
      this.$store.commit('setBackend01');
    },
    setBackend02() {
      this.$store.commit('setBackend02');
    },*/
  },
});

new Vue({
  router,
  store: store,
  render: (h) => h(App),
}).$mount("#app");
