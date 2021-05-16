<template>
  <div>
    <TypeSelector :entryTypes="entryTypes" v-on:selectedType="setSelectedType">
    </TypeSelector>
    <NewEntryForm
      v-bind:formContent="getForm"
      v-bind:showForm="true"
      v-on:entryToBeCreated="setEntryToBeCreated"
    ></NewEntryForm>
  </div>
</template>

<script>
import TypeSelector from "@/components/TypeSelector.vue";
import NewEntryForm from "@/components/NewEntryForm.vue";
import newEntryFormContent from "@/js_files/newEntryFormContent.js"; // Import the form contents from seperate JS file.

export default {
  name: "NewEntry",
  components: {
    TypeSelector,
    NewEntryForm,
  },
  data() {
    return {
      selectedType: "",
      newEntry: "",

      entryTypes: [
        { type: "article" },
        { type: "book" },
        { type: "incollection" },
      ],

      formContent: newEntryFormContent.allTypes,
    };
  },
  computed: {
    getForm: function () {
      if (this.selectedType === "") return "s";
      else {
        return this.formContent[this.selectedType];
      }
    },
  },
  methods: {
    setSelectedType: function (type) {
      // Type as selected by the selector. To be set as variable in the NewEntry component.
      this.selectedType = type;
    },
    setEntryToBeCreated: function (newEntry) {
      this.newEntry = newEntry;
    },
  },
};
</script>
<style></style>
