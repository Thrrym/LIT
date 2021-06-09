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
      v-bind:selectedType="selectedType"
    ></NewEntryModal>
    <!-- <NewEntryPrep
      ref="prep"
      v-bind:newEntry="newEntry"
      v-bind:selectedType="selectedType"
    ></NewEntryPrep> -->
  </div>
</template>

<script>
import TypeSelector from "@/components/TypeSelector.vue";
import NewEntryForm from "@/components/NewEntryForm.vue";
import NewEntryModal from "@/components/NewEntryModal.vue";
// import NewEntryPrep from "@/components/NewEntryPrep.vue";

import {postNewEntry, test} from "@/js_files/serverCom.js";

import newEntryFormContent from "@/js_files/newEntryFormContent.js"; // Import the form contents from seperate JS file.

export default {
  name: "NewEntry",

  components: {
    TypeSelector,
    NewEntryForm,
    NewEntryModal,
    // NewEntryPrep,
  },

  data() {
    return {
      selectedType: "",
      newEntry: "",
      formContent: newEntryFormContent.allTypes,
      showModal: false,
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
  },

  methods: {
    setSelectedType: function (type) {
      // Type as selected by the selector. To be set as variable in the NewEntry component.
      this.selectedType = type;
    },
    setEntryToBeCreated: function (newEntry) {
      this.newEntry = newEntry;
      //this.triggerModal();
      //this.triggerPrep();
      postNewEntry(this.selectedType, this.newEntry);
      test();
    },
    triggerModal: function () {
      // Show the modal.
      // For user feedback.
      this.$refs.modal.showNewEntryModal();
    },
    triggerPrep: function () {
      this.$refs.prep.preparation();
    },
  },
};
</script>
<style></style>
