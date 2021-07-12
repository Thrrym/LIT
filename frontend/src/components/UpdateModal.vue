<template>
  <div>
    <b-modal
      scrollable
      busy
      size="xl"
      ref="updateModal"
      v-bind:title="modalTitle"
      @ok="sendUpdateToServer"
    >
      <UpdateForm
        v-bind:formContent="getNeededForm"
        v-bind:showForm="true"
        v-on:entryToBeUpdated="setEntryToUpdate"
        v-on:cc="setEntryCc"
      ></UpdateForm>
      <template #modal-footer="{ ok }">
        <!-- Emulate built in modal footer ok and cancel button actions -->
        <b-button variant="primary" @click="ok()"> Update entry </b-button>
      </template>
    </b-modal>

    <!-- Server communication components: Get an object and update an object. -->
    <ServerComGetObject
      ref="ServerComGetObject"
      v-on:requestResponse="setRequestResponse"
      v-on:requestError="setRequestError"
    ></ServerComGetObject>
    <ServerComUpdatePost ref="ServerComUpdatePost"></ServerComUpdatePost>
  </div>
</template>

<script>
import newEntryFormContent from "@/js_files/newEntryFormContent.js";
import UpdateForm from "@/components/UpdateForm";

import ServerComGetObject from "@/components/ServerComGetObject";
import ServerComUpdatePost from "@/components/ServerComUpdatePost";

export default {
  name: "UpdateModal",

  components: {
    ServerComUpdatePost,
    ServerComGetObject,
    UpdateForm,
  },

  data() {
    return {
      modalTitle: "Update the Lit entry",
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
    showUpdateModal: function (objectId) {
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
    sendUpdateToServer: function () {
      // Send the updated entry to the backend.
      this.$refs.ServerComUpdatePost.triggerUpdatePost(this.objectUrl, this.entryToUpdate, this.cc);
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
