<template>
  <div>
    <!-- Create the form the get the information for the selected type. -->
    <b-form v-if="showForm" v-on:submit="emitNewEntry">
      <b-card bg-varint="light">
        <b-form-group
          label-cols-lg="3"
          label="Required fields"
          label-size="lg"
          lable-class="font-weight-bold pt-0"
        >
          <!-- Create the individual field based on the provided objects. -->
          <b-form-group
            v-for="field in getRequiredFields"
            v-bind:key="field.id"
            v-bind:label="field.label"
            label-cols-sm="3"
            label-align-sm="right"
          >
            <b-form-input
              v-model="field.content"
              v-bind:required="field.required"
            ></b-form-input>
          </b-form-group>
        </b-form-group>
      </b-card>

      <b-button
        variant="outline-primary"
        v-if="showOptionalFieldsButton"
        v-on:click="setShowOptionalFields"
      >
        Advanced
      </b-button>

      <!-- Optional Fields. -->
      <b-card bg-varint="light">
        <b-form-group
          v-if="showOptionalFields"
          label="Optional Fields"
          label-cols-lg="3"
          label-size="lg"
          lable-class="font-weight-bold pt-0"
        >
          <b-form-group
            v-for="field in getOptionalFields"
            v-bind:key="field.id"
            v-bind:label="field.label"
            label-cols-sm="3"
            label-align-sm="right"
          >
            <b-form-input
              v-model="field.content"
              v-bind:required="field.required"
            ></b-form-input>
          </b-form-group>
        </b-form-group>
      </b-card>

      <!-- Submit Button -->
      <b-button type="submit" variant="primary">Create new entry</b-button>
    </b-form>
  </div>
</template>

<script>
export default {
  name: "NewEntryForm",

  props: {
    formContent: {
      required: true,
    },
    showForm: {
      type: Boolean,
      required: true,
    },
  },

  data() {
    return {
      showOptionalFields: false,
    };
  },

  computed: {
    getRequiredFields: function () {
      // Get the required fields for the selected type.
      return this.formContent.filter(function (elem) {
        if (elem.required === true) return true;
      });
    },
    getOptionalFields: function () {
      // Get the fields for the form that are only optional.
      return this.formContent.filter(function (elem) {
        if (elem.required === false) return true;
      });
    },
    showOptionalFieldsButton: function () {
      // Are there any optional fields -> Show the button indicating optional fields and make them available.
      if (this.getOptionalFields.length === 0) return false;
      else return true;
    },
  },

  methods: {
    emitNewEntry: function () {
      // Gather the fields from the form. Required and optional fields.
      // Combine both arrays with fields.
      let formResult = [].concat(
        this.getRequiredFields,
        this.getOptionalFields
      );
      // Emit the appropiate event to superior component with the fields as content of event.
      this.$emit("entryToBeCreated", formResult);
    },
    setShowOptionalFields: function () {
      this.showOptionalFields = !this.showOptionalFields;
    },
  },
};
</script>

<style></style>
