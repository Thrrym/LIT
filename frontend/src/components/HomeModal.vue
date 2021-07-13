<template>
  <b-modal
    ref="modal-1"
    v-bind:title="getTitle"
    size="lg"
    ok-only
    v-on:hide="onClosedModal"
  >
    <div v-for="item in getContents" v-bind:key="item.key">
      <p v-text="item.label + ': ' + item.value"></p>
    </div>
  </b-modal>
</template>

<script>
import newEntryFormContent from "@/js_files/newEntryFormContent.js";
export default {
  name: "HomeModal",

  data() {
    return {
      contents: {},
      entry: {},
      formContent: newEntryFormContent.allTypes,
    };
  },

  methods: {
    showHomeModal: function (entry) {
      // Trigger the modal.
      this.contents = {};
      this.entry = entry;
      if (entry.type === "bibtex_article") this.prepBibtexArticleContent();
      else if (entry.type === "Paper") this.prepPaperContent();
      else if (entry.type === "Book") this.prepBookContent();
      this.$refs["modal-1"].show();
    },
    prepBibtexArticleContent: function () {
      for (let key in this.formContent.bibtex_article) {
        const index = this.formContent.bibtex_article[key];
        this.contents[key] = {
          label: index.label,
          value: this.entry[index.name],
        }
      }
    },
    prepPaperContent: function () {
      for (let key in this.formContent.Paper) {
        const index = this.formContent.Paper[key];
        this.contents[key] = {
          label: index.label,
          value: this.entry[index.name],
        }
      }
    },
    prepBookContent: function () {
      const book = this.formContent.Book;
      for (let key in book) {
        const index = book[key];
        this.contents[key] = {
          label: index.label,
          value: this.entry[index.name],
        }
      }
    },
    reset: function () {
      this.contents = {};
      this.entry = {};
    },
    onClosedModal: function () {
      this.$emit("refresh");
    }
  },

  computed: {
    getContents: function () {
      return this.contents;
    },
    getTitle: function () {
      let title = "Title"
      for (const i in this.getContents) {
        if (this.getContents[i].label === "Title") title = this.getContents[i].value
      }
      return title;
      /*return "Title"*/
    },
  },
}
</script>

<style>
</style>