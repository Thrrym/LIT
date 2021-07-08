<template>
  <b-modal
    ref="modal-1"
    v-bind:title="getModalTitle"
    ok-only
  >
    <div v-if="wasRequestSuccess">
      <p>
        <b-icon icon="bookmark-check-fill" font-scale="1"></b-icon>
        Your entry was added.
      </p>
    </div>
    <div v-else>
      <p>
        <b-icon icon="exclamation-triangle-fill" font-scale="1"></b-icon>
        We could not add your entry.
      </p>
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
        return "Success";
      }
      return "Error";
      },
  },
}
</script>

<style>
</style>