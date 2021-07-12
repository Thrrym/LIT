<template>
  <div>
    <ServerComGetFollowing ref="ServerComGetFollowing" v-on:requestResponse="setFollowing"></ServerComGetFollowing>
    <ServerComGetFollowers ref="ServerComGetFollowers" v-on:requestResponse="setFollowers"></ServerComGetFollowers>
  </div>
</template>

<script>
import ServerComGetFollowing from "@/components/ServerComGetFollowing";
import ServerComGetFollowers from "@/components/ServerComGetFollowers";

export default {
  name: "GetFollowersFollowing",
  components: {
    ServerComGetFollowing,
    ServerComGetFollowers,
  },
  data() {
    return {
      requestResponse: {},
      responseText: "",
      responseJson: {},
      followingResponse: {},
      followersResponse: {},
    };
  },
  methods: {
    trigger: function () {
      this.$refs.ServerComGetFollowing.trigger();
    },
    setFollowing: function (response) {
      this.followingResponse = JSON.parse(response.responseText);
      this.$refs.ServerComGetFollowers.trigger();
    },
    setFollowers: function (response) {
      this.followersResponse = JSON.parse(response.responseText);
      this.returnResults();
    },
    returnResults: function () {
      const following = this.followingResponse.totalItems;
      const followers = this.followersResponse.totalItems;
      this.$emit("following", following);
      this.$emit("followers", followers);
    },
  },
  computed: {

  },
};
</script>

<style scoped></style>