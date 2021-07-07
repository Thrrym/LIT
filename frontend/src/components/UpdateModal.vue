<template>
  <div>
    <b-modal
      ref="updateModal"
      v-bind:title="modalTitle"
      @ok="sendUpdateToServer"
    >
      <NewEntryForm
        v-bind:formContent="getNeededForm"
        v-bind:showForm="true"
        v-bind:update="true"
        v-on:entryToBeCreated="setEntryToUpdate"
      ></NewEntryForm>
    </b-modal>

    <ServerComGetObject
      ref="ServerComGetObject"
      v-on:requestResponse="setRequestResponse"
      v-on:requestError="setRequestError"
    ></ServerComGetObject>
  </div>
</template>

<script>
import ServerComGetObject from "@/components/ServerComGetObject";
import newEntryFormContent from "@/js_files/newEntryFormContent.js";
import NewEntryForm from "@/components/NewEntryForm";

export default {
  name: "UpdateModal",

  components: {
    ServerComGetObject,
    NewEntryForm,
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
    sendUpdateToServer: function () {},
    setNeededForm: function () {
      const currentType = this.objectOriginal.type.split("_").slice(1);
      this.neededForm = this.allFormContents[currentType];
      for (const neededFormKey in this.neededForm) {
        const name = this.neededForm[neededFormKey].name;
        this.neededForm[neededFormKey].content = this.objectOriginal[name];
      }
    },
    setEntryToUpdate: function (entry) {
      this.entryToUpdate = entry;
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
