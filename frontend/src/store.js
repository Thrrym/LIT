// Using Vuex: Global variables to switch between user and backend.
import Vuex from "vuex";
import Vue from "vue";

Vue.use(Vuex);

const store = new Vuex.Store({
  state: {
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
    // Bearer token storage:
    loginStatus: "loggedOut",
    token: "",
    user: "",
  },
  mutations: {
    authSuccess(state) {
      state.user = localStorage.getItem("user");
      state.token = localStorage.getItem("token");
      state.loginStatus = "loggedIn";
    },
    logOut(state) {
      localStorage.removeItem("user");
      state.user = "";
      localStorage.removeItem("token");
      state.token = "";
      state.loginStatus = "loggedOut";
    },
  },
  methods: {
    authSuccess() {
      this.$store.commit("authSuccess");
    },
    logOut() {
      this.$store.commit("logout");
    },
  },
  getters: {
    loggedIn: (state) => {
      return state.loginStatus === "loggedIn";
    },
    getUser: (state) => {
      return state.user + "/";
    },
    getToken: (state) => {
      return "Bearer " + state.token;
    },
  },
});

export default store;
