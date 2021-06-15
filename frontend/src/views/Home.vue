<template>
  <div class="home">
    <section class="hero is-dark">
      <div class="hero-body">
        <div class="container">
          <h1 class="title">Welcome to the LIT Library</h1>
          <h2 class="subtitle">
            Make sure to check out recent publications below!
          </h2>
          <!-- get rid of this useless button, it's in the way for now -->
          <!-- <div class="button-block"> -->
          <!-- <button class="button is-xl is-dark">Click here to make a new entry</button> -->
          <!-- </div> -->
        </div>
      </div>
    </section>
    <b-button @click="refreshObjects"><b-icon icon="arrow-counterclockwise" font-scale="1"></b-icon></b-button>
    <b-card-group>
      <b-card
        v-for="entry in objectsByUser"
        v-bind:key="entry.id"
        tag="article"
        style="max-width: 20rem"
        class="mb-2"
      >
        <b-card-title v-text="entry.author"></b-card-title>
        <b-card-sub-title v-text="entry.journal"></b-card-sub-title>
        <b-card-text v-text="entry.title"></b-card-text>
      </b-card>
    </b-card-group>

    <b-card-group>
      <b-card
        v-for="entry in objectsRelevantToUser"
        v-bind:key="entry.id"
        tag="article"
        style="max-width: 20rem"
        class="mb-2"
      >
        <b-card-title v-text="entry.author"></b-card-title>
        <b-card-sub-title v-text="entry.journal"></b-card-sub-title>
        <b-card-text v-text="entry.title"></b-card-text>

        <b-button href="#" variant="primary">
          <b-icon
            icon="bookmark-heart"
            v-on:click="likePost(entry.id)"
          ></b-icon>
        </b-button>
      </b-card>
    </b-card-group>


    <ServerComGetUserObjects
      ref="userObjects"
      v-on:requestResponse="setRequestResponseUserObjects"
    ></ServerComGetUserObjects>
    <ServerComGetUserRelevantObjects
      ref="userRelevantObjects"
      v-on:requestResponse="setRequestResponseRelevantUserObjects"
    ></ServerComGetUserRelevantObjects>
  </div>
</template>
<script>
//import EventsList from '../components/EventsList';
import ServerComGetUserObjects from "@/components/ServerComGetUserObjects.vue";
import ServerComGetUserRelevantObjects from "@/components/ServerComGetUserRelevantObjects.vue";

export default {
  name: "home",
  data() {
    return {
      requestResponse: "",
      objectsByUser: "",
      objectsRelevantToUser: "",
    };
  },

  components: {
    // EventsList,
    ServerComGetUserObjects,
    ServerComGetUserRelevantObjects,
  },
  methods: {
    setRequestResponse: function (response) {
      // Handle the event triggered by the ServerComGetInbox component.
      this.requestResponse = response;
      //alert(this.requestResponse);
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
    refreshObjects: function () {
      this.$refs.userObjects.triggerGetObjects();
      this.$refs.userRelevantObjects.triggerGetRelevantObjects();
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

<style lang="scss" scoped>
.GetInboxReturn {
  color: rgb(255, 255, 255);
}
.hero {
  text-align: center;
  background-image: url("https://images.unsplash.com/photo-1528893583363-e8a0c1ddde72?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop");
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  height: 400px;
}
.hero-body .title {
  text-shadow: 4px 4px 4px rgb(255, 255, 255);
  padding: 40px 0 20px 0;
  font-size: 60px;
}
.subtitle {
  text-shadow: 4px 4px 4px rgb(255, 255, 255);
  font-size: 30px;
}
.button-block {
  text-align: center;
  margin-left: auto;
  margin-right: auto;
  width: 100%;
  position: absolute;
  bottom: -150px;
  .button {
    margin-right: 50px;
    padding-left: 50px;
    padding-right: 50px;
  }
  .welcome {
    width: 400px;
    padding: 10px;
    margin-left: auto;
    margin-right: auto;
  }
}
.is-xl {
  font-size: 1.7rem;
}
</style>
