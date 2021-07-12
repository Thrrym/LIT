<template>
  <b-modal
    ref="modal-1"
    title="T"
    ok-only
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
  },

  computed: {
    getContents: function () {
      return this.contents;
    }
  },
}
</script>

<style>
</style>