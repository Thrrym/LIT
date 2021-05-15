<template>
  <div>
    <!-- Create the form the get the information for the selected type. -->
    <b-form v-if="showForm" v-on:submit="emitNewEntry">
      <!-- Create the individual field based on the provided objects. -->
      <b-form-group
        v-for="field in formContent"
        v-bind:key="field.id"
        v-bind:label="field.label"
      >
        <b-form-input v-model="field.content" v-bind:required="field.requiered">
        </b-form-input>
      </b-form-group>
      <b-button type="submit" variant="primary">Create new entry</b-button>
    </b-form>
  </div>
</template>

<script>
export default {
  name: "NewEntryForm",

  props: {
    formContent: {
      requiered: true,
    },
    showForm: {
      type: Boolean,
      requiered: true,
    },
  },

  methods: {
    emitNewEntry: function () {
      this.$emit("entryToBeCreated", this.formContent);
    },
  },
};
</script>

<style></style>
