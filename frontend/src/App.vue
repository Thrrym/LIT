<template>
  <div id="app">
    <NavBar></NavBar>
    <SignIn v-if="userUnknown"></SignIn>
    <router-view v-else></router-view>
    <Footer></Footer>
  </div>
</template>

<script>
import NavBar from "@/components/NavBar.vue"
import Footer from "@/components/Footer.vue"
import SignIn from "@/components/SignIn.vue"

export default {
  components: {
    NavBar,
    Footer,
    SignIn,
  },
  data () {
    return {
      userUnknown: true,
    }
  }
}

</script>


<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>
