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
      <b-nav-item to="/new-entry">+</b-nav-item>
      <b-nav-item to="/signin">Login</b-nav-item>
      <b-nav-item to="/profile">Profile</b-nav-item>
      <b-nav-item to="/search">Search</b-nav-item>
    </b-navbar-nav>

  </b-navbar>

</template>

<script>
export default {
    name: "NavBar",
}
</script>

<style>

</style>