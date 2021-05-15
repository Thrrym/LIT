<template>
  <div>
    <!-- Create the form the get the information for the selected type. -->
    <b-form v-if="showForm">
      <!-- Create the individual field based on the provided objects. -->
      <b-form-group v-for="field in formContent" :key="field.id" v-bind:label="field.name">
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
};
</script>

<style></style>
