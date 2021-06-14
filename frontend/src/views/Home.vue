<template>
<div class="home">
  <section class="hero is-dark">
    <div class="hero-body">
      <div class="container">
        <h1 class="title">
          Welcome to the LIT Library          
        </h1>
        <h2 class="subtitle">
          Make sure to check out recent publications below!
        </h2>
        <!-- get rid of this useless button, it's in the way for now -->
        <!-- <div class="button-block"> -->
          <!-- <button class="button is-xl is-dark">Click here to make a new entry</button> -->
        <!-- </div> -->
      </div>
      <b-button
      @click="getInbox"
    >
      GET your Inbox
    </b-button>

<!-- <table class="GetInboxReturn">
    <thead>
        <tr>
            
            Add a column header for each field
            you want to display In this example
            it will display the ID, Filename,
            Status and UploadedDate
            
            <th>Author</th>
            <th>Title</th>
            <th>Journal</th>
            <th>Year</th>
        </tr>
    </thead>
    <tbody id="rows"></tbody>
</table> -->

    </div>
  </section>
  <ServerComGetUserObjects ref="inbox" v-on:requestResponse="setRequestResponse"></ServerComGetUserObjects>
  <p v-text="getResponse"></p>
  <EventsList />
</div>
</template>
<script>
import EventsList from '../components/EventsList';
import ServerComGetUserObjects from "@/components/ServerComGetUserObjects.vue";

export default {
  name: 'home',
  data() {
    return {
      requestResponse: "",
    }
  },

  components: {
    EventsList,
    ServerComGetUserObjects
  },
  methods: {
    getInbox: function () {
      // Get the inbox by triggering function in ServerComGetInbox components.
      this.$refs.inbox.triggerGetObjects();
    },
    setRequestResponse: function (response) {
      // Handle the event triggered by the ServerComGetInbox component.
      this.requestResponse = response;
      alert(this.requestResponse);
    },
  },
  computed: {
    getResponse: function () {
      // Facilitate print of the Inbox.
      if (this.requestResponse === "") {
        return "";
      };
      var myObjects = JSON.parse(this.requestResponse.responseText);
      // var result = myObjects.slice(0,-1);
      // var finite = "'" + result + "'";
      // alert(finite[1].author);
      // author = myObjects.orderedItems[0].
      var author = myObjects.orderedItems[0].author;
      var title = myObjects.orderedItems[0].title;
      var journal = myObjects.orderedItems[0].journal;
      var year = myObjects.orderedItems[0].year;
      var returnObjects = [author, title, journal, year];

      return returnObjects.join(', ');
    },
  }

}
</script>

<style lang="scss" scoped>
  .GetInboxReturn{
    color: rgb(255, 255, 255);
  }
  .hero {    
    text-align: center;
    background-image: url('https://images.unsplash.com/photo-1528893583363-e8a0c1ddde72?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop');
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