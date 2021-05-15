<template>
  <div>
    <TypeSelector :entryTypes="entryTypes" v-on:selectedType="setSelectedType">
    </TypeSelector>
    <NewEntryForm v-bind:formContent="getForm" v-bind:showForm="true" v-on:entryToBeCreated="setEntryToBeCreated"></NewEntryForm>
  </div>
</template>

<script>
import TypeSelector from "@/components/TypeSelector.vue";
import NewEntryForm from "@/components/NewEntryForm.vue";

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

      formContent: {
        article: [
          {
            id: 0,
            name: "author",
            label: "Author",
            content: "",
            requiered: true,
          },
          {
            id: 1,
            name: "title",
            label: "Title",
            content: "",
            requiered: true,
          },
        ],
      },
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
    }
  },
};
</script>
<style></style>
