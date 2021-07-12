<template>
  <div class="Profile">
    <div>
      <ul class="list-group d-inline-flex">
        <li
          class="
            list-group-item
            d-flex
            justify-content-between
            align-items-center
          "
        >
          Username:
          <span class="badge bg-light light-pill">{{
            this.$store.getters.getUser.split("/")[0]
          }}</span>
        </li>
        <li
          class="
            list-group-item
            d-flex
            justify-content-between
            align-items-center
          "
        >
          Publications:
          <span class="badge bg-danger rounded-pill">{{
            objectsByUser.length
          }}</span>
        </li>
        <li
          class="
            list-group-item
            d-flex
            justify-content-between
            align-items-center
          "
        >
          Followers:
          <span class="badge bg-danger rounded-pill">{{ 0 }}</span>
        </li>
        <li
          class="
            list-group-item
            d-flex
            justify-content-between
            align-items-center
          "
        >
          Following:
          <span class="badge bg-danger rounded-pill">{{ 0 }}</span>
        </li>
      </ul>
    </div>
    <div class="profile">
      <b-button @click="refreshObjects">
        <b-icon icon="arrow-counterclockwise" font-scale="1"></b-icon>
      </b-button>

      <h3>My Entries</h3>
      <b-card-group deck>
        <b-card
          v-for="entry in objectsByUser"
          v-bind:key="entry.id"
          tag="bibtex_article"
          style="max-width: 20rem"
          class="mb-2"
        >
          <b-card-title v-text="entry.author"></b-card-title>
          <b-card-sub-title v-text="entry.journal"></b-card-sub-title>
          <b-card-text v-text="entry.title"></b-card-text>

          <template #footer>
            <small class="text-muted">
              <b-button
                href="#"
                variant="primary-outline"
                v-if="showLikes(entry.likes)"
              >
                <b-icon icon="bookmark-heart"></b-icon>
                {{ entry.likes }}
              </b-button>
              <b-button href="#" variant="primary-outline">
                <b-icon
                  icon="chevron-double-up"
                  v-on:click="showModal(entry)"
                ></b-icon>
              </b-button>
            </small>
          </template>
        </b-card>
      </b-card-group>

      <h3 v-if="userHasRelevantObjects">Activityfeed</h3>
      <b-card-group deck>
        <b-card
          v-for="entry in objectsRelevantToUser"
          v-bind:key="entry.id"
          tag="bibtex_article"
          style="max-width: 20rem"
          class="mb-2"
        >
          <b-card-title v-text="entry.author"></b-card-title>
          <b-card-sub-title v-text="entry.journal"></b-card-sub-title>
          <b-card-text v-text="entry.title"></b-card-text>

          <template #footer>
            <small class="text-muted">
              <b-button href="#" variant="primary" v-if="postCanBeLiked(entry)">
                <b-icon
                  icon="bookmark-heart"
                  v-on:click="likePost(entry.id)"
                ></b-icon>
              </b-button>
              <b-button href="#" variant="primary-outline" v-else>
                <b-icon icon="bookmark-heart"></b-icon> {{ entry.liked }}
              </b-button>
              <b-button href="#" variant="primary-outline">
                <b-icon
                  icon="chevron-double-up"
                  v-on:click="showModal(entry)"
                ></b-icon>
              </b-button> </small
          ></template>
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
      <ServerComLikePost
        ref="like"
        v-on:requestResponse="setRequestResponseLike"
      ></ServerComLikePost>
      <HomeModal ref="modal"></HomeModal>
    </div>
  </div>
</template>
<script>
//import EventsList from '../components/EventsList';
import ServerComGetUserObjects from "@/components/ServerComGetUserObjects.vue";
import ServerComGetUserRelevantObjects from "@/components/ServerComGetUserRelevantObjects.vue";
import ServerComLikePost from "@/components/ServerComLikePost.vue";
import HomeModal from "@/components/HomeModal";

export default {
  name: "home",
  data() {
    return {
      counter: 0,
      requestResponse: "",
      objectsByUser: "",
      objectsRelevantToUser: "",
    };
  },

  components: {
    // EventsList,
    ServerComGetUserObjects,
    ServerComGetUserRelevantObjects,
    ServerComLikePost,
    HomeModal,
  },
  methods: {
    setRequestResponse: function (response) {
      // Handle the event triggered by the ServerComGetInbox component.
      this.requestResponse = response;
      console.log(this.requestResponse);
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
    setRequestResponseLike: function (response) {
      this.setRequestResponse(response);
      this.refreshObjects();
    },
    refreshObjects: function () {
      this.$refs.userObjects.triggerGetObjects();
      this.$refs.userRelevantObjects.triggerGetRelevantObjects();
    },
    likePost: function (url) {
      this.$refs.like.triggerLikePost(url);
    },
    showLikes: function (likes) {
      if (likes !== 0) return true;
      return false;
    },
    postCanBeLiked: function (entry) {
      if (!Object.prototype.hasOwnProperty.call(entry, "likedBy")) {
        return true;
      }
      console.log("HIER", entry);
      if (
        entry.likedBy.includes(
          this.$store.state.backendUrl + this.$store.state.currentUser
        )
      ) {
        console.log(
          "liked",
          entry.likedBy.includes(
            this.$store.state.backendUrl + this.$store.state.currentUser
          )
        );
        return false;
      }
      if (
        entry.attributedTo.includes(
          this.$store.state.backendUrl + this.$store.state.currentUser
        )
      ) {
        return false;
      }
      return true;
    },
    showModal: function (entry) {
      this.$refs.modal.showHomeModal(entry);
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
    userHasObjects: function () {
      if (this.objectsByUser === "") return false;
      return true;
    },
    userHasRelevantObjects: function () {
      if (this.objectsRelevantToUser === "") return false;
      if (this.objectsRelevantToUser.length === 0) return false;
      return true;
    },
  },
  mounted: function () {
    this.refreshObjects();
  },
};
</script>

<style lang="scss" scoped>
.GetInboxReturn {
  color: rgb(255, 255, 255);
}
.hero {
  text-align: center;
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
.list-group {
  font-family: "Monaco", Monospace;
  display: flex;
  align-items: stretch;
  margin-bottom: 0;
  flex-wrap: wrap;
  padding: 15px;
  margin: 1em;
  justify-content: center;
  width: 30%;
  margin-top: 2em;
  font-family: "Monaco", Monospace;
}
</style>
