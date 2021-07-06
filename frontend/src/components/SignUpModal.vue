<template>
  <div>
    <b-modal
      ref="modal-1"
      v-bind:title="getModalTitle"
      @ok="sendSignUpToServer"
    >
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
              <b-form-text v-if="gotError" v-text="errorMessage" text-variant="primary"></b-form-text>
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
    <ServerComSignUp
      ref="ServerComSignUp"
      v-on:sign-up-success="signUpSuccess"
      v-on:signUpFailureUsernameTaken="usernameTaken"
    ></ServerComSignUp>
    <b-modal ref="welcomeModal" title="Welcome to Lit" @ok="directToSignIn">
      Hello {{ form.username }}, you are now signed up to Lit.
    </b-modal>
  </div>
</template>

<script>
import ServerComSignUp from "@/components/ServerComSignUp";

export default {
  name: "SignUpModal",
  components: { ServerComSignUp },
  data() {
    return {
      modalTitle: "Sign up to LIT",
      form: {
        username: "",
        password: "",
      },
      errorMessage: "",
      error: false,
    };
  },

  props: {},

  methods: {
    showSignUpModal: function () {
      // Trigger the modal.
      this.$refs["modal-1"].show();
    },
    sendSignUpToServer: function (okEvent) {
      okEvent.preventDefault();
      this.$refs.ServerComSignUp.triggerSignUp(this.form);
    },
    signUpSuccess: function () {
      // Close sign up modal and trigger event to show the sign in modal.
      this.$refs["modal-1"].hide();
      this.$refs.welcomeModal.show();
    },
    usernameTaken: function () {
      this.errorMessage = "The username is already taken.";
      this.error = true;
    },
    directToSignIn: function () {


      /*this.$refs.welcomeModal.close();*/
      //this.$refs.welcomeModal.hide();
      console.log("Modal ok");
      this.pushSignInModal();
    },
    pushSignInModal: function () {
      this.$emit("sign-up-success");
    },
  },

  computed: {
    getModalTitle: function () {
      // Generate title of the modal.
      return this.modalTitle;
    },
    gotError: function () {
      return this.error;
    },
  },
};
</script>

<style></style>
