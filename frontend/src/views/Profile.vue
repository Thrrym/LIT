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
            noAuthors.length
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
          <span class="badge bg-danger rounded-pill">{{ getFollowers }}</span>
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
          <span class="badge bg-danger rounded-pill">{{ getFollowing }}</span>
        </li>
      </ul>
    </div>
    <div class="profile">
<!--      <b-button @click="refreshObjects">
        <b-icon icon="arrow-counterclockwise" font-scale="1"></b-icon>
      </b-button>

      <h3>My Entries</h3>
      <b-card-group deck class="col-md-10">
        <b-card
          v-for="entry in noAuthors"
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
      </b-card-group>-->

<!--      <ServerComGetUserObjects
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
      <HomeModal ref="modal"></HomeModal>-->
      <h4 v-if="showMyEntries">My Entries
        <!--      <b-button @click="refreshObjects">
                <b-icon icon="arrow-counterclockwise" font-scale="1"></b-icon>
              </b-button>-->
      </h4>

      <div>
        <b-card-group deck class="col-md-10">
          <b-card
              v-for="entry in getFilteredObjectsByUser"
              v-bind:key="entry.id"
              tag="article"
              style="max-width: 20rem"
              class="mb-2"
              align="center"
          >
            <b-card-title v-text="entry.title"></b-card-title>
            <b-card-sub-title v-text="entry.type"></b-card-sub-title>
            <b-card-text v-text="entry.year"></b-card-text>

            <template #footer>
              <small class="text-muted">
                <b-button href="#" variant="primary-outline" v-if="showLikes(entry.likes)" v-b-tooltip.hover title="Likes">
                  <b-icon icon="bookmark-heart"></b-icon>
                  {{ entry.likes }}
                </b-button>
                <b-button href="#" variant="primary-outline" v-b-tooltip.hover title="Edit">
                  <b-icon icon="pencil-square" v-on:click="editObject(entry.id)"></b-icon>
                </b-button>
                <b-button href="#" variant="primary-outline" v-b-tooltip.hover title="Delete">
                  <b-icon icon="trash" v-on:click="deleteEntry(entry)"></b-icon>
                </b-button>
                <b-button href="#" variant="primary-outline" v-b-tooltip.hover title="Show details of lit entry">
                  <b-icon icon="chevron-double-up" v-on:click="showModal(entry)"></b-icon>
                </b-button>
              </small>
            </template>
          </b-card>
        </b-card-group>
      </div>

      <h4 v-if="userHasRelevantObjects">Activityfeed</h4>
      <h5 v-if="userHasRelevantObjects">Activities by people you follow.</h5>
      <b-card-group deck class="col-md-10">
        <b-card
            v-for="entry in getFilteredObjectsRelevantToUser"
            v-bind:key="entry.id"
            tag="article"
            style="max-width: 20rem"
            class="mb-2"
        >
          <b-card-title v-text="entry.title"></b-card-title>
          <b-card-sub-title v-text="entry.type"></b-card-sub-title>
          <b-card-text v-text="entry.year"></b-card-text>

          <template #footer>
            <small class="text-muted">
              <b-button href="#" variant="primary-outline" v-if="postCanBeLiked(entry)" v-b-tooltip.hover title="Like entry">
                <b-icon
                    icon="bookmark-heart-fill"
                    v-on:click="likePost(entry.id)"
                ></b-icon>
              </b-button>
              <b-button href="#" variant="primary-outline" v-else v-b-tooltip.hover title="Already liked">
                <b-icon
                    icon="bookmark-heart"
                ></b-icon> {{ entry.likes }}
              </b-button>
              <b-button href="#" variant="primary-outline" v-b-tooltip.hover title="Send offer with changes">
                <b-icon icon="pencil-square" v-on:click="offerObject(entry.id)"></b-icon>
              </b-button>
              <b-button href="#" variant="primary-outline" v-b-tooltip.hover title="Show details of lit entry">
                <b-icon
                    icon="chevron-double-up"
                    v-on:click="showModal(entry)"
                ></b-icon>
              </b-button>
            </small
            ></template>
        </b-card>
      </b-card-group>

      <h4 v-if="hasOffers">New Offers</h4> <!-- v-if is coming as soon as newOffers is implemented -->
      <h5 v-if="hasOffers">Other users send you changes to your entries.</h5>
      <b-card-group deck class="col-md-10">
        <b-card
            v-for="entry in getOffers"
            v-bind:key="entry.id"
            tag="article"
            style="max-width: 20rem"
        >
          <!--        <b-card-title v-text="entry.author"></b-card-title>
                  <b-card-sub-title v-text="entry.journal"></b-card-sub-title>-->
          <!--        <b-card-text v-text="entry.object.title"></b-card-text>-->
          <b-card-title v-text="entry.object.title"></b-card-title>
          <b-card-sub-title v-text="entry.object.type"></b-card-sub-title>
          <b-card-text v-text="entry.object.year"></b-card-text>

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
              <b-button variant="primary-outline" v-b-tooltip.hover title="Reject offer">
                <b-icon
                    icon="x-circle"
                    v-on:click="rejectOffer(entry)"
                ></b-icon>
              </b-button>
              <b-button variant="primary-outline" v-b-tooltip.hover title="Accept offer">
                <b-icon
                    icon="check"
                    v-on:click="acceptOffer(entry)"
                ></b-icon>
              </b-button>
              <b-button variant="primary-outline" v-b-tooltip.hover title="Show details of offer">
                <b-icon
                    icon="chevron-double-up"
                    v-on:click="showModal(entry.object)"
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
      <HomeModal ref="modal" v-on:refresh="refreshObjects"></HomeModal>
      <UpdateModal ref="updateModal" v-on:updateSuccess="refreshObjects"></UpdateModal>
      <OfferModal ref="offerModal"></OfferModal>
      <GetOffers ref="getOffers" v-on:getOffersSuccess="setOffers"></GetOffers>
      <AcceptOffer ref="acceptOffer" v-on:acceptSuccess="refreshObjects"></AcceptOffer>
      <RejectOffer ref="rejectOffer" v-on:rejectSuccess="refreshObjects"></RejectOffer>
      <Delete ref="delete" v-on:deleteSuccess="refreshObjects"></Delete>

      <FollowJumbotron v-on:followSuccess="refreshObjects"></FollowJumbotron>
      <GetFollowersFollowing ref="getFF" v-on:followers="setFollowers" v-on:following="setFollowing"></GetFollowersFollowing>
    </div>

  </div>
</template>
<script>
//import EventsList from '../components/EventsList';
import ServerComGetUserObjects from "@/components/ServerComGetUserObjects.vue";
import ServerComGetUserRelevantObjects from "@/components/ServerComGetUserRelevantObjects.vue";
import ServerComLikePost from "@/components/ServerComLikePost.vue";
import HomeModal from "@/components/HomeModal";
import FollowJumbotron from "@/components/FollowJumbotron";
import GetFollowersFollowing from "@/components/GetFollowersFollowing";
import UpdateModal from "@/components/UpdateModal";
import OfferModal from "@/components/OfferModal";
import GetOffers from "@/components/GetOffers";
import AcceptOffer from "@/components/AcceptOffer";
import Delete from "@/components/Delete";
import RejectOffer from "@/components/RejectOffer";
export default {
  name: "Profile",
  data() {
    return {
      counter: 0,
      requestResponse: "",
      objectsByUser: [],
      objectsRelevantToUser: [],
      following: 0,
      followers: 0,
      offers: [],
    };
  },

  components: {
    // EventsList,
    ServerComGetUserObjects,
    ServerComGetUserRelevantObjects,
    ServerComLikePost,
    HomeModal,
    FollowJumbotron,
    GetFollowersFollowing,
    UpdateModal,
    OfferModal,
    GetOffers,
    AcceptOffer,
    Delete,
    RejectOffer,
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
    setRequestResponseUserObjectsError: function () {
      this.objectsByUser = [];
    },
    setRequestResponseRelevantUserObjects: function (response) {
      this.setRequestResponse(response);
      this.objectsRelevantToUser = JSON.parse(this.getResponse).orderedItems;
      console.log("objectsByUser", this.objectsByUser);
    },
    setRequestResponseRelevantUserObjectsError: function () {
      this.objectsRelevantToUser = [];
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
        this.$refs.getFF.trigger();
      }
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
      // console.log("HIER", entry);
      if (entry.likedBy.includes(this.$store.state.backendUrl + this.$store.getters.getUser)) {
        console.log("liked", entry.likedBy.includes(this.$store.state.backendUrl + this.$store.getters.getUser))
        return false;
      }
      if (entry.attributedTo.includes(this.$store.state.backendUrl + this.$store.getters.getUser)) {
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
    setFollowers: function (i) {
      this.followers = i;
    },
    setFollowing: function (i) {
      this.following = i;
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
      return this.getFilteredObjectsRelevantToUser.length !== 0;
    },
    noAuthors: function()
    {
      return this.objectsByUser.filter
      (
          function(elem)
          {
            if (elem.type !== "Author")
            {
              return true;
            }
          }
      );
    },
    getFollowers: function () {
      return this.followers;
    },
    getFollowing: function () {
      return this.following;
    },
    getFilteredObjectsByUser: function () {
      return this.objectsByUser.filter(function (elem) {
        if (elem.type !== "Author") return true;
      });
    },
    getFilteredObjectsRelevantToUser: function () {
      const component = this;
      return this.objectsRelevantToUser.filter(function (elem) {
        if (elem.type !== "Author" && elem.generator !== component.getThisUser) return true;
      });
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
    getThisUser: function () {
      return this.$store.state.backendUrl + this.$store.getters.getUser;
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
