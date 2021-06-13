<template>
  <div>
    <b-button
      @click="getInbox"
    >
      GET Inbox
    </b-button>
    <b-form @submit="onSubmit">
      <b-form-group label="Object URL" label-for="input-1">
        <b-form-input id="input-1" v-model="objectUrl" requiered placeholder="Object">
        </b-form-input>
      <b-button type="submit" variant="primary">GET Object</b-button>
      </b-form-group>
    </b-form>
    <ServerComGetInbox ref="inbox" v-on:requestResponse="setRequestResponse"></ServerComGetInbox>
    <ServerComGetObject ref="object" v-on:requestResponse="setRequestResponse"></ServerComGetObject>
    <p v-text="getResponse"></p>
  </div>
</template>

<script>
import ServerComGetInbox from "@/components/ServerComGetInbox.vue";
import ServerComGetObject from "@/components/ServerComGetObject.vue";

export default {
  name: "Debug",
  data() {
    return {
      requestResponse: "",
      objectUrl: "",
    }
  },

  components: {
    ServerComGetInbox,
    ServerComGetObject,
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
    onSubmit: function() {
      this.getObject();
    },
    getObject: function() {
      this.$refs.object.triggerGetObject(this.objectUrl);
    }

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