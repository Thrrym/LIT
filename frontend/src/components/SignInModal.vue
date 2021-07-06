<template>
  <div>
    <b-modal ref="modal-1" v-bind:title="modalTitle" @ok="sendSignInToServer">
      <b-card
        style="max-width: 20rem"
        class="mb-2"
        no-body
        border-variant="light"
      >
        <img
          src="../assets/lit_logo_v3.svg"
          alt="Lit Logo"
          class="card-img card-img-top img-fluid"
        />
        <b-card-body>
          <b-form id="login-form">
            <!-- Input for Username. -->
            <b-form-group
              id="input-group-1"
              label="Your Username:"
              label-for="input-1"
            >
              <b-form-input
                id="input-1"
                v-model="form.username"
                placeholder="Enter Username"
                required
              ></b-form-input>
            </b-form-group>

            <!-- Input for password. -->
            <b-form-group
              id="input-group-2"
              label="Your password:"
              label-for="input-2"
            >
              <b-form-input
                id="input-2"
                v-model="form.password"
                placeholder="Enter Password"
                type="password"
                required
              ></b-form-input>
            </b-form-group>

            <!-- Login Button. -->
            <!--          <b-button type="submit" variant="primary">Login</b-button>-->
          </b-form>
        </b-card-body>
      </b-card>
    </b-modal>

    <ServerComSignIn
      ref="ServerComSignIn"
      v-on:signInSuccess="signInSuccess"
    ></ServerComSignIn>

    <b-modal ref="welcomeModal" title="Welcome to Lit" ok-only>
      Hello {{ form.username }}, you are now logged in.
    </b-modal>
  </div>
</template>

<script>
import ServerComSignIn from "@/components/ServerComSignIn";

export default {
  name: "SignInModal",
  components: { ServerComSignIn },
  data() {
    return {
      modalTitle: "Sign in to LIT",
      form: {
        username: "",
        password: "",
      },
    };
  },

  props: {},

  methods: {
    showSignInModal: function (type) {
      // Trigger the modal and modify the title if this is the first login of the user.
      if (type === "normalSignIn") {
        this.modalTitle = "Sign in to LIT";
      } else {
        this.modalTitle = "Welcome, please sign in for the first time";
      }
      this.$refs["modal-1"].show();
    },
    sendSignInToServer: function () {
      this.$refs.ServerComSignIn.triggerSignIn(this.form);
    },
/*    signUpSuccess: function () {
      // Close sign up modal and trigger event to show the sign in modal.
      this.$refs["modal-1"].hide();
      this.$emit("signUpSuccess");
    },*/
    signInSuccess: function (message) {
      // Update local storage and the vuex store.
      localStorage.setItem("token", message.accessToken);
      localStorage.setItem("user", this.form.username)
      this.$store.commit("authSuccess");

      // Show the welcome message.
      this.$refs.welcomeModal.show();
      },
  },

  computed: {
    getModalTitle: function () {
      // Generate title of the modal.
      return this.author;
    },
  },
};
</script>

<style></style>
