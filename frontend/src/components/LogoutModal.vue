<template>
  <div>
    <b-modal ref="logout-modal" v-bind:title="getModalTitle" style="">
      <!-- <component-to-re-render :key="componentKey"/> -->
      Hello {{ username }}, you are now logged out.
<!--    <button v-on:click="refreshPage">
      <b-icon icon="house" font-scale="1"></b-icon>
    </button>-->
      <template #modal-footer="{ }">
        <!-- Emulate built in modal footer ok and cancel button actions -->
<!--        <b-button variant="primary" @click="ok()"> Update entry </b-button>-->
        <router-link to="/">
          <b-icon icon="house-fill" font-scale="1" v-on:click="closeModal"></b-icon>
        </router-link>
      </template>
    
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
      // componentKey:0, 
    };
  },

  methods: {
    showLogoutModal: function () {
      // Trigger the modal and modify the title if this is the first login of the user.
      this.username = this.$store.state.user;
      this.$store.commit("logOut");
      this.$refs["logout-modal"].show();
    },
     refreshPage: function(){
      return this.$router.go();
    },
    closeModal: function () {
      this.$refs["logout-modal"].hide();
    },
    //   forceRerender() {
    //   this.componentKey += 1;
    // }
  },
  computed: {
    getModalTitle: function () {
      return this.modalTitle;
    }
  }
};
</script>

<style scoped></style>
