<template>

  <b-navbar toggleable="lg" type="light" variant="light">

    <b-navbar-brand>
      <router-link to="/">
        <img src="../assets/lit_logo_v3.svg" class="">
      </router-link>
    </b-navbar-brand>

    <b-navbar-nav pills> 
      <!-- <b-nav-item><router-link to="/">Lit</router-link></b-nav-item> | -->
      <b-nav-item to="/about">About</b-nav-item>
      <b-nav-item to="/debug">Debug</b-nav-item>
      <b-nav-item to="/new-entry">New Entry</b-nav-item>
      <b-nav-item to="/signin">Login</b-nav-item>
      <b-nav-item to="/profile">Profile</b-nav-item>
      <b-nav-item to="/search">Search</b-nav-item>

      <b-nav-item-dropdown right>
        <!-- Using 'button-content' slot -->
        <template #button-content>
          <em v-text="getCurrentUser"></em>
        </template>
        <b-dropdown-item v-on:click="setUser1" href="#">testuser01</b-dropdown-item>
        <b-dropdown-item v-on:click="setUser2" href="#">testuser02</b-dropdown-item>
      </b-nav-item-dropdown>
    </b-navbar-nav>

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
      setUser1: function () {
        this.$store.commit("setUser01");
      },
      setUser2: function () {
        this.$store.commit("setUser02");
      },
    },
    computed: {
      getCurrentUser: function () {
        return this.$store.state.currentUser;
      },
    },
}
</script>

<style>

</style>