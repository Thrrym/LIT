<template>
  <div>
    <b-jumbotron
      header="Follow users"
      lead="Discover entries by other users federation wide."
    >
      <b-form v-on:submit="triggerFollow">
        <b-input-group>
          <b-form-input type="url" v-model="userToFollow" placeholder="Enter User URI"></b-form-input>
          <b-button type="submit" variant="primary">Follow</b-button>
        </b-input-group>
      </b-form>
    </b-jumbotron>

    <b-modal ref="successModal" title="Success" ok-only>
      You follow now {{ this.getUserToFollow }}.
    </b-modal>

    <ServerComFollow ref="ServerComFollow" v-on:requestResponse="followSuccess"></ServerComFollow>
  </div>
</template>

<script>
import ServerComFollow from "@/components/ServerComFollow";

export default {
  name: "FollowJumbotron",
  data() {
    return {
      userToFollow: "",
    };
  },
  components: {
    ServerComFollow,
  },
  methods: {
    triggerFollow: function () {
      this.$refs.ServerComFollow.triggerFollow(this.getUserToFollow);
    },
    followSuccess: function () {
      this.$refs.successModal.show();
    }
  },
  computed: {
    getUserToFollow: function () {
      return this.userToFollow;
    },
  },
};
</script>

<style scoped></style>
