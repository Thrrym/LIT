<template>
  <b-modal
    ref="modal-1"
    v-bind:title="getModalTitle"
    ok-only
  >
    <p>Title: {{ this.title }}</p>
    <p>Type: {{this.type}}</p>
    <p>In: {{ this.journal }}</p>
    <p>Year: {{ this.year }}</p>
    <p>Volume: {{ this.volume }}</p>
    <p>By user: {{ this.attributedTo[0] }}</p>
    <p>Likes: {{ this.likes }}</p>

  </b-modal>
</template>

<script>
export default {
  name: "HomeModal",

  data() {
    return {
      attributedTo: "",
      author: "",
      generator: "",
      id: "",
      journal: "",
      likedBy: "",
      likes: "",
      title: "",
      type: "",
      volume: "",
      year: "",
    }
  },

  props: {
  },

  methods: {
    showHomeModal: function (entry) {
      // Trigger the modal.
      this.attributedTo = entry.attributedTo;
      this.author = entry.author
      this.generator= entry.generator
      this.id = entry.id
      this.journal = entry.journal;
      this.likedBy = entry.likedBy;
      this.likes = entry.likes;
      this.title = entry.title;
      this.type = entry.type;
      this.volume = entry.volume;
      this.year = entry.year;
      this.$refs["modal-1"].show();
    },
  },

  computed: {
    getModalTitle: function() {
      // Generate title of the modal.
      return this.author;
      },
  },
}
</script>

<style>
</style>