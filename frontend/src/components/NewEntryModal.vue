<template>
  <b-modal ref="modal-1" title="New Entry">
    <p class="my-4">Hello from modal!</p>
    <p>{{ selectedType }}</p>
    <p>{{ newEntry }}</p>
  </b-modal>
</template>

<script>
export default {
  name: "NewEntryModal",

  props: {
    showModal: {
      // required: true,
      type: Boolean,
    },
    selectedType: {
      required: true,
    },
    newEntry: {
      required: true,
    },
  },

  methods: {
    showNewEntryModal: function () {
      // Show the modal.
      this.$refs["modal-1"].show();
    },
  },
}
</script>

<style>

</style>