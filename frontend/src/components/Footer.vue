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
      <p>Logged in user: {{ getCurrentUser }}</p>
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
  },
};
</script>

<style></style>
