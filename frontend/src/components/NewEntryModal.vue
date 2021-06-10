<template>
  <b-modal
    ref="modal-1"
    v-bind:title="getModalTitle"
    ok-only
    @ok="resetRequestState">
    <div v-if="requestSuccess">
      <b-iconstack>
        <b-icon stacked icon="square"></b-icon>
        <b-icon stacked icon="check"></b-icon>
      </b-iconstack>
      <!-- <p v-text="getResponseRequest.responseText"></p> -->
    </div>
    <!-- <div><p v-text="getResponseRequest.responseText"></p></div> -->
    
  </b-modal>
</template>

<script>
export default {
  name: "NewEntryModal",

  data() {
    return {
      requestSuccess: false,
      requestFailure: false,
    }
  },

  props: {
    showModal: {
      // required: true,
      type: Boolean,
    },
    selectedType: {
      required: true,
    },
    newEntry: {
      required: true,
    },
    requestResponse: {
      required: true,
      type: Object
    }
  },

  methods: {
    showNewEntryModal: function () {
      if (this.getRequestResponse.status == 201) {
        this.requestSuccess = true;
        this.requestFailure = false;
      } else {
        this.requestFailure = true;
        this.requestSuccess = false;
      }
      
      // console.log("Test" + "Test" +"Test");
      //console.log(this.requestResponse);
      // Show the modal.
      this.$refs["modal-1"].show();
    },
    resetRequestState: function () {
      this.requestSuccess = false;
      this.requestFailure = false;
    }
  },

  computed: {
    showSuccessMessage: function() {
      //console.log(this.requestResponse.readystate);
      //if (this.requestResponse.readystate === 4 && this.requestResponse.status === 201) {
      //  return 4;
      //}
      //else {
        return "5";
      //}
    },
    getRequestResponse: function() {
      return this.requestResponse;
    },
    getModalTitle: function() {
      if (this.requestSuccess === true) {
        return "Your entry was added";
      }
      return "We could not add your entry";
    }
  }
}
</script>

<style>

</style>