<template>
  <div>
    <b-button @click="getInbox">GET Inbox</b-button>
    <b-button @click="getUserObjects">GET Objects</b-button>
    <b-button @click="getUserRelevantObjects">GET Relevant Objects</b-button>

    <b-form @submit="onSubmitGetObject">
      <b-form-group label="Object URL" label-for="input-1">
        <b-form-input
          id="input-1"
          v-model="objectUrl"
          required
          placeholder="Object"
        >
        </b-form-input>
        <b-button type="submit" variant="primary">GET Object</b-button>
      </b-form-group>
    </b-form>

    <b-form @submit="onSubmitLikeObject">
      <b-form-group label="Object URL" label-for="input-2">
        <b-form-input
          id="input-2"
          v-model="objectUrl"
          required
          placeholder="Object"
        >
        </b-form-input>
        <b-button type="submit" variant="primary">Like Object</b-button>
      </b-form-group>
    </b-form>

    <ServerComGetInbox
      ref="inbox"
      v-on:requestResponse="setRequestResponse"
    ></ServerComGetInbox>
    <ServerComGetObject
      ref="object"
      v-on:requestResponse="setRequestResponse"
    ></ServerComGetObject>
    <ServerComLikePost
      ref="like"
      v-on:requestResponse="setRequestResponse"
    ></ServerComLikePost>
    <ServerComGetUserObjects
      ref="userObjects"
      v-on:requestResponse="setRequestResponseUserObjects"
    ></ServerComGetUserObjects>
    <ServerComGetUserRelevantObjects
      ref="userRelevantObjects"
      v-on:requestResponse="setRequestResponseRelevantUserObjects"
    ></ServerComGetUserRelevantObjects>
    <p v-text="getResponse"></p>

    <div>
      <b-card v-for="entry in objectsByUser" v-bind:key="entry.id"

        tag="article"
        style="max-width: 20rem"
        class="mb-2"
      >
        <b-card-title v-text="entry.author"></b-card-title>
        <b-card-sub-title v-text="entry.journal"></b-card-sub-title>
        <b-card-text v-text="entry.title"></b-card-text>

        <b-button @click="editObject(entry.id)">
          <b-icon icon="tools" font-scale="1"></b-icon>
        </b-button>

      </b-card>
    </div>

    <div>
      <b-card v-for="entry in objectsRelevantToUser" v-bind:key="entry.id"

              tag="article"
              style="max-width: 20rem"
              class="mb-2"
      >
        <b-card-title v-text="entry.author"></b-card-title>
        <b-card-sub-title v-text="entry.journal"></b-card-sub-title>
        <b-card-text v-text="entry.title"></b-card-text>

        <b-button href="#" variant="primary">
          <b-icon icon="bookmark-heart" v-on:click="likePost(entry.id)"></b-icon>
        </b-button>

      </b-card>
    </div>
    <UpdateModal ref="updateModal"></UpdateModal>

    <b-button v-on:click="showAuthorModal">New Author</b-button>
    <NewAuthorModal ref="authorModal"></NewAuthorModal>

  </div>
</template>

<script>
import ServerComGetInbox from "@/components/ServerComGetInbox.vue";
import ServerComGetObject from "@/components/ServerComGetObject.vue";
import ServerComLikePost from "@/components/ServerComLikePost.vue";
import ServerComGetUserObjects from "@/components/ServerComGetUserObjects.vue";
import ServerComGetUserRelevantObjects from "@/components/ServerComGetUserRelevantObjects.vue";
import NewAuthorModal from "@/components/NewAuthorModal";
import UpdateModal from "@/components/UpdateModal";

export default {
  name: "Debug",
  data() {
    return {
      requestResponse: "",
      objectUrl: "",
      objectsByUser: "",
      objectsRelevantToUser: "",
    };
  },

  components: {
    UpdateModal,
    ServerComGetInbox,
    ServerComGetObject,
    ServerComLikePost,
    ServerComGetUserObjects,
    ServerComGetUserRelevantObjects,
    NewAuthorModal,
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
    setRequestResponseUserObjects: function (response) {
      this.setRequestResponse(response);
      this.objectsByUser = JSON.parse(this.getResponse).orderedItems;
      console.log("objectsByUser", this.objectsByUser);
    },
    setRequestResponseRelevantUserObjects: function (response) {
      this.setRequestResponse(response);
      this.objectsRelevantToUser = JSON.parse(this.getResponse).orderedItems;
      console.log("objectsByUser", this.objectsByUser);
    },
    onSubmitGetObject: function () {
      this.getObject();
    },
    getObject: function () {
      this.$refs.object.triggerGetObject(this.objectUrl);
    },
    onSubmitLikeObject: function () {
      this.likeObject();
    },
    likeObject: function () {
      this.$refs.like.triggerLikePost(this.objectUrl);
    },
    getUserObjects: function () {
      this.$refs.userObjects.triggerGetObjects();
    },
    getUserRelevantObjects: function () {
      this.$refs.userRelevantObjects.triggerGetRelevantObjects();
    },
    likePost: function (id) {
      this.$refs.like.triggerLikePost(id);
    },
    editObject: function (objectId) {
      this.$refs.updateModal.showUpdateModal(objectId);
    },
    showAuthorModal: function () {
      this.$refs.authorModal.showNewAuthorModal();
    },
  },

  computed: {
    getResponse: function () {
      // Facilitate print of the Inbox.
      if (this.requestResponse === "") {
        return "";
      }
      return this.requestResponse.responseText;
    },
  },
};
</script>

<style></style>
