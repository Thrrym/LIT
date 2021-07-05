<template>

  <b-navbar toggleable="lg" type="light" variant="light">

    <b-navbar-brand>
      <router-link to="/">
        <img src="../assets/lit_logo_v3.svg" class="">
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
      <!-- <b-nav-item><router-link to="/">Lit</router-link></b-nav-item> | -->
      <b-nav-item to="/about">About</b-nav-item>
      <b-nav-item to="/debug">Debug</b-nav-item>
      <b-nav-item to="/new-entry">New Entry</b-nav-item>
      <b-nav-item to="/signin">Login</b-nav-item>
      <b-nav-item to="/profile">Profile</b-nav-item>
      <b-nav-item to="/search">Search</b-nav-item>
    </b-navbar-nav>
    <b-navbar-nav class="ml-auto">
<!--      <b-nav-item-dropdown right>
        &lt;!&ndash; Using 'button-content' slot &ndash;&gt;
        <template #button-content>
          <em v-text="getCurrentBackend"></em>
        </template>
        <b-dropdown-item v-on:click="setBackend01" href="#">8080</b-dropdown-item>
        <b-dropdown-item v-on:click="setBackend02" href="#">8081</b-dropdown-item>
      </b-nav-item-dropdown>-->

      <b-nav-item-dropdown right>
        <!-- Using 'button-content' slot -->
        <template #button-content>
          <em v-text="getCurrentUser"></em>
        </template>
        <b-dropdown-item v-on:click="setUser1" href="#">testuser01</b-dropdown-item>
        <b-dropdown-item v-on:click="setUser2" href="#">testuser02</b-dropdown-item>
      </b-nav-item-dropdown>

    </b-navbar-nav>
    </b-collapse>
  </b-navbar>

</template>

<script>
export default {
    name: "NavBar",
    data() {
      return {
      }
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
        this.$store.commit('setBackend01')
      },
      setBackend02: function () {
        // Set the current backend to option 2.
        this.$store.commit('setBackend02')
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
      }
    },
}
</script>

<style>

</style>