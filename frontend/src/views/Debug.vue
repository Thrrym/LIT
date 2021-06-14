<template>
  <div>
    <b-button @click="getInbox">GET Inbox</b-button>
    <b-button @click="getUserObjects">GET Objects</b-button>
    <b-button @click="getUserRelevantObjects">GET Relevant Objects</b-button>

    <b-form @submit="onSubmitGetObject">
      <b-form-group label="Object URL" label-for="input-1">
        <b-form-input id="input-1" v-model="objectUrl" required placeholder="Object">
        </b-form-input>
      <b-button type="submit" variant="primary">GET Object</b-button>
      </b-form-group>
    </b-form>

    <b-form @submit="onSubmitLikeObject">
      <b-form-group label="Object URL" label-for="input-2">
        <b-form-input id="input-2" v-model="objectUrl" required placeholder="Object">
        </b-form-input>
      <b-button type="submit" variant="primary">Like Object</b-button>
      </b-form-group>
    </b-form>

    

    <ServerComGetInbox ref="inbox" v-on:requestResponse="setRequestResponse"></ServerComGetInbox>
    <ServerComGetObject ref="object" v-on:requestResponse="setRequestResponse"></ServerComGetObject>
    <ServerComLikePost ref="like" v-on:requestResponse="setRequestResponse"></ServerComLikePost>
    <ServerComGetUserObjects ref="userObjects" v-on:requestResponse="setRequestResponse"></ServerComGetUserObjects>
    <ServerComGetUserRelevantObjects ref="userRelevantObjects" v-on:requestResponse="setRequestResponse"></ServerComGetUserRelevantObjects>
    <pre v-text="getResponse"></pre>
  </div>
</template>

<script>
import ServerComGetInbox from "@/components/ServerComGetInbox.vue";
import ServerComGetObject from "@/components/ServerComGetObject.vue";
import ServerComLikePost from "@/components/ServerComLikePost.vue";
import ServerComGetUserObjects from "@/components/ServerComGetUserObjects.vue";
import ServerComGetUserRelevantObjects from "@/components/ServerComGetUserRelevantObjects.vue";


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
    ServerComLikePost,
    ServerComGetUserObjects,
    ServerComGetUserRelevantObjects,
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
    onSubmitGetObject: function() {
      this.getObject();
    },
    getObject: function() {
      this.$refs.object.triggerGetObject(this.objectUrl);
    },
    onSubmitLikeObject: function () {
      this.likeObject();
    },
    likeObject: function() {
      this.$refs.like.triggerLikePost(this.objectUrl);
    },
    getUserObjects: function () {
      this.$refs.userObjects.triggerGetObjects();
    },
    getUserRelevantObjects: function () {
      this.$refs.userRelevantObjects.triggerGetRelevantObjects();
    }

  },

  computed: {
    getResponse: function () {
      // Facilitate print of the Inbox.
      if (this.requestResponse === "") {
        return "";
      };
      return JSON.parse(this.requestResponse.responseText);
    },
  }


}
</script>

<style>
</style>