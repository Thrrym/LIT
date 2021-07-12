<template>
  <footer class="text-muted">
    <div class="container">
      <p class="float-right">
        <a href="#">Back to top</a>
      </p>
      <p>
        New to Lit? <router-link to="/">Visit the Homepage</router-link>
      </p>
      <b-card text-variant="light" style="border: none">
      <p clas="muted">Frontend server: {{ currentFrontendServer }}</p>
      <p>Backend server: {{ currentBackendServer }}</p>
      <p>Logged in user: {{ getCurrentUser }}, Login status: {{ getLoginStatus }}</p>
      </b-card>
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
