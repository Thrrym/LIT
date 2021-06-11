<template>
  <div>
    <b-button
      @click="getInbox"
    >
      GET Inbox
    </b-button>
    <ServerComGetInbox ref="inbox" v-on:requestResponse="setRequestResponse"></ServerComGetInbox>
  </div>
</template>

<script>
import ServerComGetInbox from "@/components/ServerComGetInbox.vue";

export default {
  name: "Debug",
  data() {
    return {
      requestResponse: "",
    }
  },

  components: {
    ServerComGetInbox,
  },

  methods: {
    getInbox: function () {
      this.$refs.inbox.triggerGetInbox();
    },
    setRequestResponse: function (response) {
      this.requestResponse = response;
    },
  }


}
</script>

<style>
</style>