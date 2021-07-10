<template>
  <div>
    <b-container>
      <!-- Create the form the get the information for the selected type. -->
      <b-form v-on:input="emitNewEntry">
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
          <b-icon icon="caret-down-square" aria-hidden="true"></b-icon> Advanced
        </b-button>

        <!-- Optional Fields. -->
        <b-card bg-varint="light" v-if="showOptionalFields">
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

        <b-button variant="outline-primary" v-on:click="setShowCcField">
          <b-icon icon="caret-down-square" aria-hidden="true"></b-icon> CC
        </b-button>

        <!-- CC Field. -->
        <b-card bg-varint="light" v-if="showCcField">
          <b-form-group
            v-if="showCcField"
            label="Send to"
            label-cols-lg="3"
            label-size="lg"
            lable-class="font-weight-bold pt-0"
          >
            <b-form-group label="CC" label-cols-sm="3" label-align-sm="right">
              <b-form-input v-model="ccContent" type="url"></b-form-input>
            </b-form-group>
          </b-form-group>
        </b-card>
      </b-form>
    </b-container>
  </div>
</template>

<script>
export default {
  name: "UpdateForm",

  props: {
    formContent: {
      required: true,
    },
  },

  watch: {
    formContent: function () {
      // Watch prop with content of the form for change.
      // On change: Reset the Button for optional fields.
      this.resetOptionalFieldsButton();
    },
  },

  data() {
    return {
      showOptionalFields: false,
      showCcField: false,
      ccContent: "",
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
      this.$emit("cc", this.ccContent);
      this.$emit("entryToBeUpdated", formResult);
    },
    setShowOptionalFields: function () {
      this.showOptionalFields = !this.showOptionalFields;
    },
    resetOptionalFieldsButton: function () {
      this.showOptionalFields = false;
    },
    setShowCcField: function () {
      this.showCcField = !this.showCcField;
    },
  },
};
</script>

<style></style>
