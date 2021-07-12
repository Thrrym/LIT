<template>
  <div>
    <TypeSelector
      :entryTypes="getEntryTypes"
      v-on:selectedType="setSelectedType"
    >
    </TypeSelector>
    <NewEntryForm
      v-bind:formContent="getForm"
      v-bind:showForm="activateForm"
      v-bind:update="false"
      v-on:entryToBeCreated="setEntryToBeCreated"
      v-on:cc="setEntryCc"
    >
    </NewEntryForm>
    <NewEntryModal
      ref="modal"
      v-bind:newEntry="newEntry"
      v-bind:requestResponse="getRequestResponse"
      v-bind:selectedType="selectedType"
    ></NewEntryModal>
    <ServerCom ref="com" v-on:requestResponse="setRequestResponse"></ServerCom>
  </div>
</template>

<script>
import TypeSelector from "@/components/TypeSelector.vue";
import NewEntryForm from "@/components/NewEntryForm.vue";
import NewEntryModal from "@/components/NewEntryModal.vue";
import ServerCom from "@/components/ServerCom.vue";

import newEntryFormContent from "@/js_files/newEntryFormContent.js"; // Import the form contents from separate JS file.

export default {
  name: "NewEntry",

  components: {
    TypeSelector,
    NewEntryForm,
    NewEntryModal,
    ServerCom,
  },

  data() {
    return {
      selectedType: "",
      newEntry: [],
      formContent: newEntryFormContent.allTypes,
      showModal: false,
      requestResponse: Object(),
      cc: "",
    };
  },

  computed: {
    getForm: function () {
      if (this.selectedType === "") return [];
      else {
        return this.formContent[this.selectedType];
      }
    },
    getEntryTypes: function () {
      var entryTypes = [];
      for (let i in this.formContent) {
        entryTypes.push(i);
      }
      return entryTypes;
    },
    activateForm: function () {
      // Determine if the form for the selected can be shown.
      // No type selected -> false.
      if (this.selectedType === "") return false;
      else return true;
    },
    getRequestResponse: function () {
      return this.requestResponse;
    },
    getNewEntry: function () {
      return this.newEntry;
    },
    getSelectedType: function () {
      return this.selectedType;
    },
  },

  methods: {
    setSelectedType: function (type) {
      // Type as selected by the selector. To be set as variable in the NewEntry component.
      this.selectedType = type;
    },
    setEntryCc: function (cc) {
      this.cc = cc;
    },
    setEntryToBeCreated: function (entry) {
      // Using the form, the user created a new entry. Send the entry to the backend.
      this.newEntry = entry;
      console.log(this.newEntry);
      this.sendEntryToBackend();
    },
    sendEntryToBackend: function () {
      // Trigger the ServerCom component to send the new entry to the backend.
      this.$refs.com.triggerServerCom(
        this.newEntry,
        this.getSelectedType,
        this.cc
      );
    },
    triggerModal: function () {
      // Show the modal. For user feedback.
      this.$refs.modal.showNewEntryModal();
    },
    setRequestResponse: function (response) {
      // Event of ServerCom component shows HTTP result. Trigger modal for user feedback.
      this.requestResponse = response;
      this.triggerModal();
    },
  },
};
</script>
<style></style>
