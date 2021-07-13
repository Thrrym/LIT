<template>
  <div>
    <b-jumbotron bg-variant="light"
    >
      <template #header>Follow users</template>

      <template #lead>
        Discover entries by other users federation wide.
      </template>

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
      this.$emit("followSuccess");
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
