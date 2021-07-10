<template>
  <div>
    <b-modal ref="logout-modal" v-bind:title="getModalTitle" ok-only>
      Hello {{ username }}, you are now logged out.
    </b-modal>
  </div>
</template>

<script>
export default {
  name: "LogoutModal",
  data() {
    return {
      modalTitle: "Logout from LIT",
      username: "",
    };
  },

  methods: {
    showLogoutModal: function () {
      // Trigger the modal and modify the title if this is the first login of the user.
      this.username = this.$store.state.user;
      this.$store.commit("logOut");
      this.$refs["logout-modal"].show();
    },
  },
  computed: {
    getModalTitle: function () {
      return this.modalTitle;
    }
  }
};
</script>

<style></style>
