<template>
  <div>
    <TypeSelector :entryTypes="entryTypes" v-on:selectedType="setSelectedType">
    </TypeSelector>
    <NewEntryForm :formContent="getForm"></NewEntryForm>
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
      entryTypes: [
        { type: "article" },
        { type: "book" },
        { type: "incollection" },
      ],
      formContent: {
        article: {
          author: "",
          title: "",
        },
      },
    };
  },
  computed: {
    getForm: function () {
      if (this.selectedType === "") return "s";
      else {
        return this.formContent[this.selectedType];
      };
    },
  },
  methods: {
    setSelectedType: function (type) {
      // Type as selected by the selector. To be set as variable in the NewEntry component.
      this.selectedType = type;
    },
  },
};
</script>
<style></style>
