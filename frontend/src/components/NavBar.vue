<template>
  <div>
    <b-navbar toggleable="lg" type="light" variant="light">
      <b-navbar-brand>
        <router-link to="/">
          <img src="../assets/lit_logo_v3.svg" class="" />
        </router-link>
      </b-navbar-brand>

      <b-navbar-toggle target="nav-collapse">
        <template #default="{ expanded }">
          <b-icon v-if="expanded" icon="bookmark"></b-icon>
          <b-icon v-else icon="bookmark-fill"></b-icon>
        </template>
      </b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav pills>
          <b-nav-item to="/debug">Debug</b-nav-item>
          <b-nav-item to="/new-entry">New Entry</b-nav-item>
          <b-nav-item to="/profile">Profile</b-nav-item>
          <b-nav-item to="/search">Search</b-nav-item>
        </b-navbar-nav>
        <!-- Sign up, sign in and logout buttons. -->
        <b-navbar-nav class="ml-auto">
          <b-nav-item v-if="!isUserLoggedIn" v-on:click="showSignUpModal">Sign up</b-nav-item>
          <b-nav-item v-if="!isUserLoggedIn" v-on:click="showSignInModal('normalSignIn')">Sign in</b-nav-item>
          <b-nav-item v-if="isUserLoggedIn" v-on:click="logOut">Log out</b-nav-item>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
    <SignUpModal ref="SignUpModal" v-on:sign-up-success="showSignInModal('firstSignIn')"></SignUpModal>
    <SignInModal ref="SignInModal"></SignInModal>
    <LogoutModal ref="LogoutModal"></LogoutModal>
  </div>
</template>

<script>
import SignUpModal from "@/components/SignUpModal";
import SignInModal from "@/components/SignInModal";
import LogoutModal from "@/components/LogoutModal";

export default {
  name: "NavBar",
  components: {
    LogoutModal,
    SignUpModal,
    SignInModal,
  },
  data() {
    return {
      testReturn: "",
      /*eventHandlerSignUp: {
        click: this.showSignUpModal,
        signUpSuccess: this.setTestReturn,
      },*/
    };
  },
  methods: {
    // Method to manipulate the vuex store.
    setUser1: function () {
      this.$store.commit("setUser01");
    },
    setUser2: function () {
      // Method to manipulate the vuex store.
      this.$store.commit("setUser02");
    },
    setBackend01: function () {
      // Set the current backend to option 1.
      this.$store.commit("setBackend01");
    },
    setBackend02: function () {
      // Set the current backend to option 2.
      this.$store.commit("setBackend02");
    },
    showSignUpModal: function () {
      this.testReturn = "Sign up modal start.";
      this.$refs.SignUpModal.showSignUpModal();
    },
    showSignInModal: function (message) {
      this.$refs.SignInModal.showSignInModal(message);
    },
    logOut: function () {
      this.$refs.LogoutModal.showLogoutModal();
    },
  },
  computed: {
    getCurrentUser: function () {
      // Get user from the vuex store an remove last slash.
      return this.$store.state.currentUser.split("/")[0];
    },
    getCurrentBackend: function () {
      // Get the active backend from the vuex store.
      return this.$store.state.backendUrl;
    },
    isUserLoggedIn: function () {
      return this.$store.state.loginStatus === "loggedIn";
    },
  },
};
</script>

<style></style>
