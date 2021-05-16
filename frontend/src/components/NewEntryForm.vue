<template>
  <div>
    <!-- Create the form the get the information for the selected type. -->
    <b-form v-if="showForm" v-on:submit="emitNewEntry">
      <b-form-group label="Requiered fields">
        <!-- Create the individual field based on the provided objects. -->
        <b-form-group
          v-for="field in getRequieredFields"
          v-bind:key="field.id"
          v-bind:label="field.label"
        >
          <b-form-input
            v-model="field.content"
            v-bind:required="field.requiered"
          ></b-form-input>
        </b-form-group>
      </b-form-group>

      <b-button
        variant="outline-primary"
        v-if="showOptionalFieldsButton"
        v-on:click="setShowOptionalFields"
      >
        Advanced
      </b-button>

      <!-- Optional Fields. -->
      <b-form-group v-if="showOptionalFields" label="Optional Fields">
        <b-form-group
          v-for="field in getOptionalFields"
          v-bind:key="field.id"
          v-bind:label="field.label"
        >
          <b-form-input
            v-model="field.content"
            v-bind:required="field.requiered"
          ></b-form-input>
        </b-form-group>
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

  data() {
    return {
      showOptionalFields: false,
    };
  },

  computed: {
    getRequieredFields: function () {
      // Get the requiered fields for the selected type.
      return this.formContent.filter(function (elem) {
        if (elem.requiered === true) return true;
      });
    },
    getOptionalFields: function () {
      // Get the fields for the form that are only optional.
      return this.formContent.filter(function (elem) {
        if (elem.requiered === false) return true;
      });
    },
    showOptionalFieldsButton: function () {
      // Are there any optional fields -> Show the button indicating optional fields and make them available.
      if (this.getRequieredFields.length === 0) return false;
      else return true;
    },
  },

  methods: {
    emitNewEntry: function () {
      let formResult = [].concat(this.getRequieredFields, this.getOptionalFields);
      this.$emit("entryToBeCreated", formResult);
    },
    setShowOptionalFields: function () {
      this.showOptionalFields = !this.showOptionalFields;
    },
  },
};
</script>

<style></style>
