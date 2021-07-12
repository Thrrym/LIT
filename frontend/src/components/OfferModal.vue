<template>
  <div>
    <b-modal
      scrollable
      busy
      ref="updateModal"
      v-bind:title="modalTitle"
      @ok="sendOfferToServer"
    >
      <UpdateForm
        v-bind:formContent="getNeededForm"
        v-bind:showForm="true"
        v-on:entryToBeUpdated="setEntryToUpdate"
        v-on:cc="setEntryCc"
      ></UpdateForm>
      <template #modal-footer="{ ok }">
        <!-- Emulate built in modal footer ok and cancel button actions -->
        <b-button variant="primary" @click="ok()">Offer update</b-button>
      </template>
    </b-modal>

    <!-- Server communication components: Get an object and update an object. -->
    <ServerComGetObject
      ref="ServerComGetObject"
      v-on:requestResponse="setRequestResponse"
      v-on:requestError="setRequestError"
    ></ServerComGetObject>
    <ServerComOfferPost ref="ServerComOfferPost"></ServerComOfferPost>
  </div>
</template>

<script>
import newEntryFormContent from "@/js_files/newEntryFormContent.js";
import UpdateForm from "@/components/UpdateForm";

import ServerComGetObject from "@/components/ServerComGetObject";
import ServerComOfferPost from "@/components/ServerComOfferPost";

export default {
  name: "OfferModal",

  components: {
    ServerComOfferPost,
    ServerComGetObject,
    UpdateForm,
  },

  data() {
    return {
      modalTitle: "Offer an update to an Lit entry by users",
      requestResponse: "",
      objectOriginal: "",
      error: false,
      form: "",
      objectUrl: "",
      allFormContents: newEntryFormContent.allTypes,
      neededForm: "",
      entryToUpdate: "",
      cc: "",
    };
  },

  methods: {
    showOfferModal: function (objectId) {
      this.objectUrl = objectId;
      this.$refs.ServerComGetObject.triggerGetObject(objectId);
    },
    setRequestResponse: function (response) {
      this.requestResponse = response;
      this.objectOriginal = JSON.parse(response.responseText);
      this.setNeededForm();
      this.triggerModal();
    },
    setRequestError: function () {
      this.error = true;
    },
    triggerModal: function () {
      // Trigger the modal.
      this.$refs.updateModal.show();
    },
    sendOfferToServer: function () {
      // Send the updated entry to the backend.
      this.$refs.ServerComOfferPost.triggerOfferPost(this.objectUrl, this.entryToUpdate, this.cc);
    },
    setNeededForm: function () {
      //const currentType = this.objectOriginal.type.split("_").slice(1);
      const currentType = this.objectOriginal.type;
      this.neededForm = this.allFormContents[currentType];
      for (const neededFormKey in this.neededForm) {
        const name = this.neededForm[neededFormKey].name;
        this.neededForm[neededFormKey].content = this.objectOriginal[name];
      }
    },
    setEntryToUpdate: function (entry) {
      this.entryToUpdate = entry;
    },
    setEntryCc: function (cc) {
      this.cc = cc;
    },
  },

  computed: {
    gotError: function () {
      return this.error;
    },
    getNeededForm: function () {
      return this.neededForm;
    },
  },
};
</script>

<style></style>
