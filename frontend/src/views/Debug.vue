<template>
  <div>
    <b-button
      @click="getInbox"
    >
      GET Inbox
    </b-button>
    <ServerComGetInbox ref="inbox" v-on:requestResponse="setRequestResponse"></ServerComGetInbox>
    <p v-text="getResponse"></p>
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
      // Get the inbox by triggering function in ServerComGetInbox components.
      this.$refs.inbox.triggerGetInbox();
    },
    setRequestResponse: function (response) {
      // Handle the event triggered by the ServerComGetInbox component.
      this.requestResponse = response;
    },
  },

  computed: {
    getResponse: function () {
      // Facilitate print of the Inbox.
      if (this.requestResponse === "") {
        return "";
      };
      return this.requestResponse.responseText
    }
  }


}
</script>

<style>
</style>