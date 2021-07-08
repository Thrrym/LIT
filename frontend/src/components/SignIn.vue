<template>
  <b-card
    
    style="max-width: 20rem"
    class="mb-2"
    no-body
  >
    <img src="../assets/lit_logo_v3.svg" alt="Lit Logo" class="card-img card-img-top img-fluid">
    <b-card-body
      title="Login to Lit"
    >
      <b-form
        id="login-form"
        @submit="returnUserPassword">

        <!-- Input for E-Mail (Username). -->
        <b-form-group id="input-group-1" label="Your E-Mail:" label-for="input-1">
          <b-form-input
            id="input-1"
            v-model="form.loginusername"
            placeholder="Enter E-Mail"
            type="email"
            required
          ></b-form-input>
        </b-form-group>

        <!-- Input for password. -->
        <b-form-group id="input-group-2" label="Your E-Mail:" label-for="input-2">
          <b-form-input
            id="input-2"
            v-model="form.password"
            placeholder="Enter Password"
            type="password"
            required
          ></b-form-input>
        </b-form-group>

        <!-- Login Button. -->
        <b-button type="submit" variant="primary">Login</b-button>

      </b-form>
    </b-card-body>
  </b-card>
</template>

<script>
export default {
    name: "SignIn",
    data() {
      return {
        // Saves the username and password here.
        form: {
          loginusername: '',
          password: '',
        },
      }
    },
    methods: {
      returnUserPassword() {
        this.$emit("user-and-password", this.form);
        return;
      }
    },
}
</script>

<style>
</style>