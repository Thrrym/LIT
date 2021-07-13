<template>
  <div class="home">
    <section class="hero is-dark">
      <div class="hero-body">
        <div class="container">
          <h1 class="title">Welcome to the LIT Library</h1>
          <h2 class="subtitle">
            Make sure to sign up and login!
          </h2>
          <!-- get rid of this useless button, it's in the way for now -->
          <!-- <div class="button-block"> -->
          <!-- <button class="button is-xl is-dark">Click here to make a new entry</button> -->
          <!-- </div> -->
        </div>
      </div>
    </section>

    <h3 v-if="showMyEntries">My Entries
<!--      <b-button @click="refreshObjects">
        <b-icon icon="arrow-counterclockwise" font-scale="1"></b-icon>
      </b-button>-->
    </h3>

    <div> 
    <b-card-group deck class="col-md-10">
      <b-card
        v-for="entry in noAuthors"
        v-bind:key="entry.id"
        tag="article"
        style="max-width: 20rem"
        class="mb-2"
        align="center"
      >
        <b-card-sub-title v-text="entry.journal"></b-card-sub-title>
        <b-card-text v-text="entry.title"></b-card-text>

        <template #footer>
          <small class="text-muted">
            <b-button href="#" variant="primary-outline" v-if="showLikes(entry.likes)">
              <b-icon icon="bookmark-heart"></b-icon>
              {{ entry.likes }}
            </b-button>
            <b-button href="#" variant="primary-outline">
              <b-icon icon="pencil-square" v-on:click="editObject(entry.id)"></b-icon>
            </b-button>
            <b-button href="#" variant="primary-outline">
              <b-icon icon="trash" v-on:click="deleteEntry(entry)"></b-icon>
            </b-button>
            <b-button href="#" variant="primary-outline">
              <b-icon icon="chevron-double-up" v-on:click="showModal(entry)"></b-icon>
            </b-button>
          </small>
        </template>
      </b-card>
    </b-card-group>
    </div>

    <h3 v-if="userHasRelevantObjects">Activityfeed</h3>
    <h4 v-if="userHasRelevantObjects">Activities by people you follow.</h4>
    <b-card-group deck class="col-md-10">
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

        <template #footer>
          <small class="text-muted">
            <b-button href="#" variant="primary" v-if="postCanBeLiked(entry)">
              <b-icon
                icon="bookmark-heart"
                v-on:click="likePost(entry.id)"
              ></b-icon>
            </b-button>
            <b-button href="#" variant="primary-outline" v-else>
              <b-icon
                  icon="bookmark-heart"
              ></b-icon> {{entry.liked}}
            </b-button>
            <b-button href="#" variant="primary-outline">
              <b-icon icon="pencil-square" v-on:click="offerObject(entry.id)"></b-icon>
            </b-button>
            <b-button href="#" variant="primary-outline">
              <b-icon
                  icon="chevron-double-up"
                  v-on:click="showModal(entry)"
              ></b-icon>
            </b-button>
          </small
        ></template>
      </b-card>
    </b-card-group>

<h3 v-if="hasOffers">New Offers</h3> <!-- v-if is coming as soon as newOffers is implemented -->
    <b-card-group deck class="col-md-10">
      <b-card
        v-for="entry in getOffers"
        v-bind:key="entry.id"
        tag="article"
        style="max-width: 20rem"
      >
<!--        <b-card-title v-text="entry.author"></b-card-title>
        <b-card-sub-title v-text="entry.journal"></b-card-sub-title>-->
        <b-card-text v-text="entry.object.title"></b-card-text>

        <template #footer>
          <small class="text-muted">
<!--            <b-button href="#" variant="primary" v-if="postCanBeLiked(entry)">
              <b-icon
                icon="bookmark-heart"
                v-on:click="likePost(entry.id)"
              ></b-icon>
            </b-button>-->
<!--            <b-button href="#" variant="primary-outline" v-else>
              <b-icon
                  icon="bookmark-heart"
              ></b-icon> {{entry.liked}}
            </b-button>-->
<!--            <b-button href="#" variant="primary-outline">
              <b-icon
                  icon="chevron-double-up"
                  v-on:click="showModal(entry)"
              ></b-icon>
            </b-button>-->
            <b-button variant="primary-outline">
              <b-icon
                  icon="x-circle"
                  v-on:click="rejectOffer(entry)"
              ></b-icon>
            </b-button>
            <b-button variant="primary-outline">
              <b-icon
                  icon="check"
                  v-on:click="acceptOffer(entry)"
              ></b-icon>
            </b-button>
            <b-button variant="primary-outline">
              <b-icon
                  icon="chevron-double-up"
                  v-on:click="showModal(entry)"
              ></b-icon>
            </b-button>
          </small
        ></template>
      </b-card>
    </b-card-group>

    <ServerComGetUserObjects
      ref="userObjects"
      v-on:requestResponse="setRequestResponseUserObjects"
      v-on:requestError="setRequestResponseUserObjectsError"
    ></ServerComGetUserObjects>

    <ServerComGetUserRelevantObjects
      ref="userRelevantObjects"
      v-on:requestResponse="setRequestResponseRelevantUserObjects"
      v-on:requestError="setRequestResponseRelevantUserObjectsError"
    ></ServerComGetUserRelevantObjects>

    <ServerComLikePost
        ref="like"
        v-on:requestResponse="setRequestResponseLike"
    ></ServerComLikePost>
    <HomeModal ref="modal"></HomeModal>
    <UpdateModal ref="updateModal" v-on:updateSuccess="refreshObjects"></UpdateModal>
    <OfferModal ref="offerModal"></OfferModal>
    <GetOffers ref="getOffers" v-on:getOffersSuccess="setOffers"></GetOffers>
    <AcceptOffer ref="acceptOffer" v-on:acceptSuccess="refreshObjects"></AcceptOffer>
    <RejectOffer ref="rejectOffer" v-on:rejectSuccess="refreshObjects"></RejectOffer>
    <Delete ref="delete" v-on:deleteSuccess="refreshObjects"></Delete>

  </div>
</template>
<script>
//import EventsList from '../components/EventsList';
import ServerComGetUserObjects from "@/components/ServerComGetUserObjects.vue";
import ServerComGetUserRelevantObjects from "@/components/ServerComGetUserRelevantObjects.vue";
import ServerComLikePost from "@/components/ServerComLikePost.vue";
import HomeModal from "@/components/HomeModal";
import UpdateModal from "@/components/UpdateModal";
import OfferModal from "@/components/OfferModal";
import GetOffers from "@/components/GetOffers";
import AcceptOffer from "@/components/AcceptOffer";
import RejectOffer from "@/components/RejectOffer";
import Delete from "@/components/Delete";

export default {
  name: "home",
  data() {
    return {
      requestResponse: "",
      objectsByUser: [],
      objectsRelevantToUser: "",
      offers: [],
    };
  },

  components: {
    RejectOffer,
    // EventsList,
    ServerComGetUserObjects,
    ServerComGetUserRelevantObjects,
    ServerComLikePost,
    HomeModal,
    UpdateModal,
    OfferModal,
    GetOffers,
    AcceptOffer,
    Delete,
  },
  methods: {
    setRequestResponse: function (response) {
      // Handle the event triggered by the ServerComGetInbox component.
      this.requestResponse = response;
      console.log(this.requestResponse);
    },
    setRequestResponseUserObjects: function (response) {
      //this.setRequestResponse(response);
      this.objectsByUser = JSON.parse(response.responseText).orderedItems;
      console.log("objectsByUser", this.getUserObjects);
    },
    setRequestResponseUserObjectsError: function () {
      this.objectsByUser = [];
    },
    setRequestResponseRelevantUserObjects: function (response) {
      //this.setRequestResponse(response);
      this.objectsRelevantToUser = JSON.parse(response.responseText).orderedItems;
      console.log("objectsRelevantToUser", this.objectsByUser);
    },
    setRequestResponseRelevantUserObjectsError: function () {
      this.objectsRelevantToUser = {};
    },
    setRequestResponseLike: function (response) {
      this.setRequestResponse(response);
      this.refreshObjects();
    },
    refreshObjects: function () {
      if (this.$store.getters.loggedIn) {
        this.$refs.userObjects.triggerGetObjects();
        this.$refs.userRelevantObjects.triggerGetRelevantObjects();
        this.$refs.getOffers.triggerGetOffers();
      }
    },
    likePost: function(url) {
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
      if (entry.likedBy.includes(this.$store.state.backendUrl + this.$store.state.currentUser)) {
        console.log("liked", entry.likedBy.includes(this.$store.state.backendUrl + this.$store.state.currentUser))
        return false;
      }
      if (entry.attributedTo.includes(this.$store.state.backendUrl + this.$store.state.currentUser)) {
        return false;
      }
      return true;
    },
    showModal: function (entry) {
      this.$refs.modal.showHomeModal(entry);
    },
    editObject: function (objectId) {
      this.$refs.updateModal.showUpdateModal(objectId);
    },
    offerObject: function (objectId) {
      this.$refs.offerModal.showOfferModal(objectId);
    },
    setOffers: function (offers) {
      this.offers = offers;
    },
    rejectOffer: function (offer) {
      console.log(offer.id)
      this.$refs.rejectOffer.triggerRejectOffer(offer.id);
    },
    acceptOffer: function (offer) {
      this.$refs.acceptOffer.triggerAcceptOffer(offer.id);
    },
    deleteEntry: function(entry) {
      this.$refs.delete.trigger(entry.id);
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
      if (this.objectsByUser.length === 0) return false;
      return true;
    },
    userHasRelevantObjects: function () {
      if (this.objectsRelevantToUser === "") return false;
      if (this.objectsRelevantToUser.length === 0) return false;
      return true
    },
    noAuthors: function()
    {
      return this.objectsByUser.filter(function (elem) {
          if (elem.type !== "Author")
          {
            return true;  
          }
        }
      );
    },
    getOffers: function () {
      return this.offers;
    },
    hasOffers: function () {
      return this.getOffers.length !== 0;
    },
    getUserObjects: function () {
      return this.objectsByUser;
    },
    isSignedIn: function() {
      return this.$store.getters.loggedIn;
    },
    showMyEntries: function() {
      return this.isSignedIn && this.userHasObjects;
    },
  },
  mounted: function () {
    if (this.$store.getters.loggedIn) this.refreshObjects();
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
  height: 700px;
}
.hero-body .title {
  // text-shadow: 4px 4px 4px rgb(255, 255, 255);
  color: white;
  background-color: #ff4040ff;
  padding: 40px 0 20px 0;
  font-size: 60px;
}
.subtitle {
  // text-shadow: 4px 4px 4px rgb(255, 255, 255);
  color: white;
  background-color: #ff4040ff;
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
.b-icon{
  color: #ff4040ff;
}
.is-xl {
  font-size: 1.7rem;
}
</style>
