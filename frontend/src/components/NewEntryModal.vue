<template>
  <b-modal
    ref="modal-1"
    v-bind:title="getModalTitle"
    ok-only
  >
    <div v-if="wasRequestSuccess">
      <b-iconstack>
        <b-icon stacked icon="square"></b-icon>
        <b-icon stacked icon="check"></b-icon>
      </b-iconstack>
      <!-- <p v-text="getResponseRequest.responseText"></p> -->
    </div>
    <!-- <div><p v-text="getResponseRequest.responseText"></p></div> -->
    <div v-else>
      <b-iconstack>
        <b-icon stacked icon="exclamation-triangle-fill"></b-icon>
      </b-iconstack>
      
    </div>
    
  </b-modal>
</template>

<script>
export default {
  name: "NewEntryModal",

  data() {
    return {
    }
  },

  props: {
    selectedType: {
      required: true,
    },
    newEntry: {
      required: true,
    },
    requestResponse: {
      required: true,
    }
  },

  methods: {
    showNewEntryModal: function () {
      // Trigger the modal.
      this.$refs["modal-1"].show();
    },
    wasRequestSuccess: function () {
      // Was the HTTP request a success? Return boolean.
      if (this.requestResponse.readyState === this.requestResponse.DONE) {
        let status = this.requestResponse.status;
        if ((status === 0 || (status >= 200 && status < 400))) {
          return true;
        }
      }
      return false;
    },      
  },

  computed: {
    getModalTitle: function() {
      // Generate title of the modal.
      if (this.wasRequestSuccess()) {
        return "Your entry was added";
      }
      return "Error, we could not add your entry";
      },
  },
}
</script>

<style>
</style>