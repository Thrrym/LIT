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
      v-on:entryToBeCreated="setEntryToBeCreated"
    >
    </NewEntryForm>
    <NewEntryModal
      ref="modal"
      v-bind:newEntry="newEntry"
      v-bind:requestResponse="getRequestResponse"
      v-bind:selectedType="selectedType"
    ></NewEntryModal>
    <ServerCom
      ref="com"
      v-bind:selectedType="selectedType"
      v-bind:newEntry="newEntry"
      v-on:requestResponse="setRequestResponse"
    ></ServerCom>
  </div>
</template>

<script>
import TypeSelector from "@/components/TypeSelector.vue";
import NewEntryForm from "@/components/NewEntryForm.vue";
import NewEntryModal from "@/components/NewEntryModal.vue";
import ServerCom from "@/components/ServerCom.vue";

//import { postNewEntry } from "@/js_files/serverCom.js";

import newEntryFormContent from "@/js_files/newEntryFormContent.js"; // Import the form contents from seperate JS file.

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
      newEntry: "",
      formContent: newEntryFormContent.allTypes,
      showModal: false,
      requestResponse: Object(),
      //requestResponseState: "",
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
      // No type seleceted -> false.
      if (this.selectedType === "") return false;
      else return true;
    },
    getRequestResponse: function () {
      return this.requestResponse;
    },    
  },

  methods: {
    setSelectedType: function (type) {
      // Type as selected by the selector. To be set as variable in the NewEntry component.
      this.selectedType = type;
    },
    setEntryToBeCreated: function (newEntry) {
      this.newEntry = newEntry;
      this.sendEntryToBackend();
      this.triggerModal();
    },
    sendEntryToBackend: function () {
      //postNewEntry(this.selectedType, this.newEntry);
      this.$refs.com.triggerServerCom();
    },
    triggerModal: function () {
      // Show the modal. For user feedback.
      console.log("Trigger the modal.")
      this.$refs.modal.showNewEntryModal();
    },
    setRequestResponse: function(response) {
      this.requestResponse = response;
      this.triggerModal();
    },
  },
};
</script>
<style></style>
