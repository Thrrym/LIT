<template>
  <footer class="text-muted">
    <div class="container">
      <p class="float-right">
        <a href="#">Back to top</a>
      </p>
      <p>
        New to Lit? <router-link to="/">Visit the Homepage</router-link> or read
        our <router-link to="/about">About page.</router-link>
      </p>
      <p>Frontend server: {{ currentFrontendServer }}</p>
      <p>Backend server: {{ currentBackendServer }}</p>
      <p>Logged in user: {{ getCurrentUser }}, Login status: {{ getLoginStatus }}</p>
    </div>
  </footer>
</template>

<script>

export default {
  name: "Footer",
  data() {
    return {
      currentFrontendServer: this.$store.state.proxyUrl,
      currentBackendServer: this.$store.state.backendUrl,
    };
  },
  computed: {
    getCurrentUser: function () {
      return this.$store.getters.getUser;
    },
    getLoginStatus: function () {
      return this.$store.getters.loggedIn;
    },
  },
};
</script>

<style></style>
